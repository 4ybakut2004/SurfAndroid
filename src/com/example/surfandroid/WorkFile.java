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
		   int countPic = (int)(lenTer / 100);
		   // интервал через сколько записывать 
		   int lenInter = (int)(lenData / countPic);
		   
		   long sum = 0;
		   int zel = 0;
		   
		   for(int i = 0; i < lenData; i += 4)
		   {
			   if(bits == 16)
			   {
				   int c1 = audioFileStream.read();
				   c1 = c1 << 8;
				   c1 += audioFileStream.read();
				   sum += c1;
				   
				   if(countChannels == 2)
				   {
					   audioFileStream.read();
					   audioFileStream.read();
				   }
			   }
			   if(bits == 8)
			   {
				   sum += audioFileStream.read();
				   if(countChannels == 2)
				   {
					   audioFileStream.read();
				   }
			   }
			   
			   if(i / lenInter >= zel && i != 0)
			   {
				   wav.add(sum / (float)lenInter);
				   sum = 0;
				   zel ++;
			   }
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