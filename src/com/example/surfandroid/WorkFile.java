package com.example.surfandroid;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

class name_cool 
{
   long cool = 0L;
   String name = "";
}

public class WorkFile 
{
   final String DIR_SD = "MyFiles";
   final String FILENAME_SD = "file";
   int[] listF = new int[4];
   ArrayList<name_cool> nameCool = new ArrayList<name_cool>();
   List<Float> wav = new ArrayList<Float>();
   
   int countChannels;	
   int bits;
   int bitRate;
   int lenData;
   
   String nickName;
   String path;
   
   public WorkFile() 
   {
      for(int var1 = 0; var1 < 5; ++var1) 
      {
         name_cool var2 = new name_cool();
         var2.cool = 0L;
         var2.name = "--";
         this.nameCool.add(var2);
      }
      
      countChannels = 0;
      bits = 0;
      bitRate = 0;
      lenData = 0;
      
      nickName = "";
      path = "";
   }

   // читаем файл рекордов
   public void readFile() 
   {
	    // проверяем доступность SD
	    if (!Environment.getExternalStorageState().equals("mounted")) 
	    {
	      return;
	    }
	    
	    // получаем путь к SD
	    File sdPath = Environment.getExternalStorageDirectory();
	    // добавляем свой каталог к пути
	    sdPath = new File(sdPath.getAbsolutePath() + "/" + "MyFiles");
	    // формируем объект File, который содержит путь к файлу
	    File sdFile = new File(sdPath, "file");
	    try 
	    {
	      // открываем поток для чтения
	      BufferedReader br = new BufferedReader(new FileReader(sdFile));
	      // читаем содержимое
	      path = br.readLine();
	      nickName = br.readLine();
	      for(int i = 0; i < 5; i++)
	      {
	    	  String str = br.readLine();
	          String[] arrayOfString = str.substring(3, str.length()).split(" ");
	          nameCool.get(i).name = arrayOfString[1];
	          nameCool.get(i).cool = Integer.parseInt(arrayOfString[2]);
	      }
	      br.close();
	    }
	    catch (FileNotFoundException e) 
	    {
	      e.printStackTrace();
	    } 
	    catch (IOException e) 
	    {
	      e.printStackTrace();
	    }
   }

   //читаем аудио файл
   public void readWave() 
   {
	   if (!Environment.getExternalStorageState().equals("mounted")) 
	   {
		   return;
	   }

	   File localFile2 = new File(path);
	   
	   try
	   {
		   InputStream is = new FileInputStream(localFile2.getAbsoluteFile());
		   BufferedInputStream bis = new BufferedInputStream(is);
		   DataInputStream audioFileStream = new DataInputStream(bis); 

		   ArrayList<Integer> headMus = new ArrayList<Integer>();
		   for(int i = 0; i < 46; i++)
		   {
			   headMus.add(audioFileStream.read());
		   }
			
		   // находим число каналов
		   countChannels += headMus.get(23);
		   countChannels = countChannels << 8;
		   countChannels += headMus.get(22);
		   
		   // находим частоту выборки
		   bits += headMus.get(35);
		   bits = bits << 8;
		   bits += headMus.get(34);
		   
		   // находим число бит в секунду
		   for(int i = 0; i < 4; i++)
		   {
			   bitRate += headMus.get(31 - i);
			   if(i < 3) bitRate = bitRate << 8; 
		   }
		   //проверка: есть ли лист, или нет
		   if(headMus.get(38) == 76)
		   {
			   // считали шапку до листа! теперь ищем длину листа
			   int lenList = 0;
			   for(int i = 0; i < 4; i ++)
			   {
				   lenList += headMus.get(headMus.size() - 1 - i);
				   if(i < 3) lenList = lenList << 8;
			   }
			   
			   // находим длину листа
			   for(int i = 0; i < lenList; i++)
			   {
				   audioFileStream.read();
			   }
			   
			   // считали дату! теперь вычислям длину даты
			   for(int i = 0; i < 8; i++)
			   {
				   headMus.add(audioFileStream.read());   
			   }
		   }
		   
		   for(int i = 0; i < 4; i++)
		   {
			   lenData += headMus.get(headMus.size() - 1 - i);
			   if(i < 3) lenData = lenData << 8;
		   }

		   // длина отрисованной трассы
		   int lenTer = (int)(lenData / bitRate) * 42;
		   // количество пиков
		   int countPic = (int)(lenTer / 30);
		   // интервал через сколько записывать 
		   int lenInter = (int)(lenData / countPic);
		   
		   long sum = 0;
		   int zel = 0;
		   int writeCounter = 0;
		   int c1 = 0;
		   int period = 128;
		   float[] fft = new float[period];
		   int fftcount = 0;
		   for(int i = 0; i < lenData; i += 4)
		   {
			   if(bits == 16)
			   {
				   c1 = audioFileStream.read();
				   c1 = c1 << 8;
				   c1 += audioFileStream.read();
				   
				   if(countChannels == 2)
				   {
					   audioFileStream.read();
					   audioFileStream.read();
				   }
			   }
			   if(bits == 8)
			   {
				   c1 = audioFileStream.read();
				   if(countChannels == 2)
				   {
					   audioFileStream.read();
				   }
			   }
			   
			   fft[fftcount++] = (float)c1 / 32000.0f;
			   
			   if(fftcount == period)
			   {
				   FFT getFFT = new FFT(period);
				   float[] fftresult = new float[period];
				   getFFT.fft(fft, fftresult);
				   
				   for(int j = 0; j < period; j++)
				   {
					   writeCounter += 4;
					   if(writeCounter == lenInter)
					   {
						   wav.add(fftresult[j]);
						   writeCounter = 0;
					   }
				   }
				   
				   fftcount = 0;
			   }
			   
			   
			   /*if(i % (period - 1) == 0 && i != 0)
			   {
				   FFT getFFT = new FFT(period);
				   float[] fftresult = new float[period];
				   getFFT.fft(fft, fftresult);
				   wav.add((float)fftresult[period - 1]);
				   fftcount = 0;
			   }*/
			   
			   /*if(i / lenInter >= zel && i != 0)
			   {
				   wav.add((float)c1);
				   sum = 0;
				   zel ++;
			   }*/
		   }
		   audioFileStream.close();
	   }
	   catch(IOException e)
	   {
		   e.printStackTrace();
	   }
   }

   // пишем файл рекордов
   void writeFile() 
   {
	   if (!Environment.getExternalStorageState().equals("mounted")) 
	   {
		   return;
	   }
	   // получаем путь к SD
	   File sdPath = Environment.getExternalStorageDirectory();
	   // добавляем свой каталог к пути
	   sdPath = new File(sdPath.getAbsolutePath() + "/" + "MyFiles");
	   // формируем объект File, который содержит путь к файлу
	   File sdFile = new File(sdPath, "file");
	   
	   try 
	   {
		   BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(sdFile));
		   
		   localBufferedWriter.write(path + "\n");
		   localBufferedWriter.write(nickName + "\n");
		   
		   for(int i = 0; i < 5; i++)
		   {
			   localBufferedWriter.write(" " + (i + 1) + ". " + this.nameCool.get(i).name + " " + this.nameCool.get(i).cool + "\n");
		   }
		   localBufferedWriter.close();
	   } 
	   catch (FileNotFoundException e) 
	    {
	      e.printStackTrace();
	    } 
	    catch (IOException e) 
	    {
	      e.printStackTrace();
	    }
   }
}

class FFT {

	  int n, m;

	  // Lookup tables. Only need to recompute when size of FFT changes.
	  float[] cos;
	  float[] sin;

	  public FFT(int n) {
	      this.n = n;
	      this.m = (int) (Math.log(n) / Math.log(2));

	      // Make sure n is a power of 2
	      if (n != (1 << m))
	          throw new RuntimeException("FFT length must be power of 2");

	      // precompute tables
	      cos = new float[n / 2];
	      sin = new float[n / 2];

	      for (int i = 0; i < n / 2; i++) {
	          cos[i] = (float)Math.cos(-2 * Math.PI * i / n);
	          sin[i] = (float)Math.sin(-2 * Math.PI * i / n);
	      }

	  }

	  public void fft(float[] x, float[] y) {
	      int i, j, k, n1, n2, a;
	      float c, s, t1, t2;

	      // Bit-reverse
	      j = 0;
	      n2 = n / 2;
	      for (i = 1; i < n - 1; i++) {
	          n1 = n2;
	          while (j >= n1) {
	              j = j - n1;
	              n1 = n1 / 2;
	          }
	          j = j + n1;

	          if (i < j) {
	              t1 = x[i];
	              x[i] = x[j];
	              x[j] = t1;
	              t1 = y[i];
	              y[i] = y[j];
	              y[j] = t1;
	          }
	      }

	      // FFT
	      n1 = 0;
	      n2 = 1;

	      for (i = 0; i < m; i++) {
	          n1 = n2;
	          n2 = n2 + n2;
	          a = 0;

	          for (j = 0; j < n1; j++) {
	              c = cos[a];
	              s = sin[a];
	              a += 1 << (m - i - 1);

	              for (k = j; k < n; k = k + n2) {
	                  t1 = c * x[k + n1] - s * y[k + n1];
	                  t2 = s * x[k + n1] + c * y[k + n1];
	                  x[k + n1] = x[k] - t1;
	                  y[k + n1] = y[k] - t2;
	                  x[k] = x[k] + t1;
	                  y[k] = y[k] + t2;
	              }
	          }
	      }
	  }
	}