package com.example.surfandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLUtils;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

class heigthMap 
{
   public float a = 0.0F;
   public float heigth = 0.0F;
   public float pos = 0.0F;
}

public class Cube 
{
   public ArrayList<Vec3> Camera = new ArrayList<Vec3>();
   float[] ColorMap;
   ArrayList<heigthMap> HMap = new ArrayList<heigthMap>();
   ArrayList<heigthMap> RMap = new ArrayList<heigthMap>();
   float[] Border;
   private FloatBuffer vertexBufferBorder;
   int Size_x = 2;
   int Size_y = 0;
   float[] VertexMap;
   int[][] card;
   int count = 3;
   private ShortBuffer indexBuffer;
   private ShortBuffer indexBufferBorder;
   short[] masInd;
   short[] masIndBorder;
   public Vec3 posCamera = new Vec3();
   int[] textureIds = new int[1];
   int[] textureIdBorder = new int[1];
   private FloatBuffer vertexBuffer;
   
   Context gen_context;
   MediaPlayer mp;
   WorkFile genFile = new WorkFile();
   
   private float[] texture;
   private float[] textureBorder;
   private FloatBuffer textureBuffer;
   private FloatBuffer textureBufferBorder;
   public Cube(Context context) 
   {
	   	  gen_context = context;
	   	  
	      genFile.readFile();
	   	  genFile.readWave();
	      
	   	  Size_y = (int)(genFile.lenData / genFile.bitRate) * 42;
	   	  this.masInd = new short[6 * (-1 + this.Size_x) * (-1 + this.Size_y)];
	   	  this.masIndBorder = new short[6 * (-1 + Size_x) * (-1 + this.Size_y)];
	      int[] var1 = new int[]{1 + this.Size_x, 1 + this.Size_y};
	      this.card = (int[][])Array.newInstance(Integer.TYPE, var1);
	      this.VertexMap = new float[3 * this.Size_x * this.Size_y];
	      this.Border = new float[3 * Size_x * this.Size_y];
	      this.ColorMap = new float[4 * this.Size_x * this.Size_y];
	      this.Camera = new ArrayList<Vec3>();
	      this.textureIds = new int[1];
	      this.texture = new float[2 * this.Size_x * this.Size_y];
	      this.textureBorder = new float[2 * this.Size_x * this.Size_y];
	      
	      Random rnd = new Random();
	      
	      for(int i = 0; i < genFile.wav.size(); i++)
	      {
	    	  heigthMap var2 = new heigthMap();
		      var2.pos = (i + 1) * 30;
		      var2.heigth = 5 * genFile.wav.get(i) / 40.0f; 
		     
		      var2.a = 0.2F;
	    	  this.HMap.add(var2);
	      }
	      
	      heigthMap varR = new heigthMap();
		  varR.pos = 500;
		  varR.heigth = 60.0f;
		  varR.a = 0.01f;
		  this.RMap.add(varR);
	      int var14 = 0;

	      int var29;
	      for(int var15 = 0; var15 < this.Size_x; ++var15) {
	         for(int var16 = 0; var16 < this.Size_y; var14 = var29) {
	            boolean var17 = false;
	            Vec3 var18 = new Vec3();
	            int var19 = 0;

	            int var21;
	            while(true) {
	               if(var19 >= this.RMap.size()) {
	                  var21 = var14;
	                  break;
	               }

	               if((float)var16 >= this.RMap.get(var19).pos - 3.14F / this.RMap.get(var19).a && (float)var16 <= this.RMap.get(var19).pos + 3.14F / this.RMap.get(var19).a) {
	                  var21 = var14 + 1;
	                  this.VertexMap[var14] = this.RMap.get(var19).heigth * (float)Math.cos((double)(this.RMap.get(var19).a * ((float)var16 - this.RMap.get(var19).pos))) + this.RMap.get(var19).heigth + (float)var15*15;
	                  var18.x = 11 + this.RMap.get(var19).heigth * (float)Math.cos((double)(this.RMap.get(var19).a * ((float)var16 - this.RMap.get(var19).pos))) + this.RMap.get(var19).heigth + (float)var15;
	                  var17 = true;
	                  break;
	               }

	               ++var19;
	            }

	            int var22;
	            if(!var17) {
	               var22 = var21 + 1;
	               this.VertexMap[var21] = (float)(var15 * 15);
	               var18.x = (float)(var15 * 1) + 11;
	            } else {
	               var22 = var21;
	            }

	            boolean var23 = false;
	            int var24 = 0;

	            int var26;
	            while(true) {
	               if(var24 >= this.HMap.size()) {
	                  var26 = var22;
	                  break;
	               }

	               if((float)var16 >= this.HMap.get(var24).pos - 3.14F / this.HMap.get(var24).a && (float)var16 <= this.HMap.get(var24).pos + 3.14F / this.HMap.get(var24).a) {
	                  var26 = var22 + 1;
	                  this.VertexMap[var22] = this.HMap.get(var24).heigth * (float)Math.cos((double)(this.HMap.get(var24).a * ((float)var16 - this.HMap.get(var24).pos))) + this.HMap.get(var24).heigth;
	                  var18.y = this.HMap.get(var24).heigth * (float)Math.cos((double)(this.HMap.get(var24).a * ((float)var16 - this.HMap.get(var24).pos))) + this.HMap.get(var24).heigth;
	                  var23 = true;
	                  break;
	               }

	               ++var24;
	            }

	            int var27;
	            if(!var23) {
	               var27 = var26 + 1;
	               this.VertexMap[var26] = 0.0F;
	               var18.y = 0.0F;
	            } else {
	               var27 = var26;
	            }

	            var29 = var27 + 1;
	            this.VertexMap[var27] = (float)(var16 * 1);
	            if(var15 == -1 + this.Size_x) {
	               this.Camera.add(var18);
	            }

	            ++var16;
	         }
	      }

	      ByteBuffer var33 = ByteBuffer.allocateDirect(4 * this.VertexMap.length);
	      var33.order(ByteOrder.nativeOrder());
	      this.vertexBuffer = var33.asFloatBuffer();
	      this.vertexBuffer.put(this.VertexMap);
	      this.vertexBuffer.position(0);
	      int var37 = 0;

	      for(int var38 = 0; var38 < this.Size_x; var38++) 
	      {
	         for(int var39 = 0; var39 < this.Size_y; var39++) 
	         {
	        	texture[var37++] = (float)var39 / ((float)this.Size_y / (128 * this.Size_y / 1350.0f));
	        	texture[var37++] = (float)var38 / (float)(this.Size_x - 1);
	         }
	      }

	      ByteBuffer var43 = ByteBuffer.allocateDirect(4 * this.texture.length);
	      var43.order(ByteOrder.nativeOrder());
	      this.textureBuffer = var43.asFloatBuffer();
	      this.textureBuffer.put(this.texture);
	      this.textureBuffer.position(0);
	      
	      int var47 = 0;

	      for(int var48 = 0; var48 < -1 + this.Size_x; ++var48) {
	         for(int var49 = 0; var49 < -1 + this.Size_y; ++var49) {
	            this.masInd[var47++] = (short)(var49 + var48 * this.Size_y);
	            this.masInd[var47++] = (short)(1 + var49 + var48 * this.Size_y);
	            this.masInd[var47++] = (short)(var49 + (var48 + 1) * this.Size_y);
	            this.masInd[var47++] = (short)(1 + var49 + var48 * this.Size_y);
	            this.masInd[var47++] = (short)(1 + var49 + (var48 + 1) * this.Size_y);
	            this.masInd[var47++] = (short)(var49 + (var48 + 1) * this.Size_y);
	         }
	      }

	      this.indexBuffer = ShortBuffer.allocate(2 * this.masInd.length);
	      this.indexBuffer.put(this.masInd);
	      this.indexBuffer.position(0);
	      
	      // строим боковину
	      int fk = 0;
	      for(int i = 0; i < 2; i++)
	      {
	    	  for(int j = 0; j < Size_y; j++)
	    	  {
	    		  Border[fk++] = Camera.get(j).x + 3;
	    		  Border[fk++] = Camera.get(j).y + (i + 1);
	    		  Border[fk++] = j;
	    	  }
	      }
	      
	      ByteBuffer var34 = ByteBuffer.allocateDirect(4 * this.Border.length);
	      var34.order(ByteOrder.nativeOrder());
	      this.vertexBufferBorder = var34.asFloatBuffer();
	      this.vertexBufferBorder.put(this.Border);
	      this.vertexBufferBorder.position(0);
	      
	      var37 = 0;

	      for(int var38 = 0; var38 < this.Size_x; var38++) 
	      {
	         for(int var39 = 0; var39 < this.Size_y; var39++) 
	         {
	        	textureBorder[var37++] = (float)var39 / ((float)this.Size_y);
	        	textureBorder[var37++] = (float)var38 / (float)(this.Size_x - 1);
	         }
	      }

	      ByteBuffer var44 = ByteBuffer.allocateDirect(4 * this.textureBorder.length);
	      var44.order(ByteOrder.nativeOrder());
	      this.textureBufferBorder = var44.asFloatBuffer();
	      this.textureBufferBorder.put(this.texture);
	      this.textureBufferBorder.position(0);
	      
	      int var41 = 0;

	      for(int var48 = 0; var48 < -1 + Size_x; ++var48) {
	         for(int var49 = 0; var49 < -1 + this.Size_y; ++var49) {
	            this.masIndBorder[var41++] = (short)(var49 + var48 * this.Size_y);
	            this.masIndBorder[var41++] = (short)(1 + var49 + var48 * this.Size_y);
	            this.masIndBorder[var41++] = (short)(var49 + (var48 + 1) * this.Size_y);
	            this.masIndBorder[var41++] = (short)(1 + var49 + var48 * this.Size_y);
	            this.masIndBorder[var41++] = (short)(1 + var49 + (var48 + 1) * this.Size_y);
	            this.masIndBorder[var41++] = (short)(var49 + (var48 + 1) * this.Size_y);
	         }
	      }

	      this.indexBufferBorder = ShortBuffer.allocate(2 * this.masIndBorder.length);
	      this.indexBufferBorder.put(this.masIndBorder);
	      this.indexBufferBorder.position(0);
   }

   public void draw(GL10 var1) 
   {
	  var1.glEnable(GL10.GL_TEXTURE_2D);
	  var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIds[0]);
	  var1.glDisable(GL10.GL_DEPTH_TEST);
	  var1.glEnable(GL10.GL_BLEND);
	  
	  var1.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      
      var1.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      var1.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);
      
      var1.glDrawElements(GL10.GL_TRIANGLES, 6 * (-1 + this.Size_x) * (-1 + this.Size_y), GL10.GL_UNSIGNED_SHORT, this.indexBuffer);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      var1.glDisable(GL10.GL_BLEND);
      var1.glEnable(GL10.GL_DEPTH_TEST);
      var1.glDisable(GL10.GL_TEXTURE_2D);
   }
   
   public void drawBorder(GL10 var1) 
   {
	  var1.glEnable(GL10.GL_TEXTURE_2D);
	  var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIdBorder[0]);
		  
	  var1.glDisable(GL10.GL_DEPTH_TEST);
	  var1.glEnable(GL10.GL_BLEND);
	  var1.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBufferBorder);
      
      var1.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      var1.glColorPointer(4, GL10.GL_FLOAT, 0, this.textureBufferBorder);
      
      var1.glDrawElements(GL10.GL_TRIANGLES, 6 * (-1 + Size_x) * (-1 + this.Size_y), GL10.GL_UNSIGNED_SHORT, this.indexBufferBorder);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      var1.glDisable(GL10.GL_BLEND);
      var1.glEnable(GL10.GL_DEPTH_TEST);
      var1.glDisable(GL10.GL_TEXTURE_2D);
   }
   
   public void musStart()
   {
	   mp = MediaPlayer.create(gen_context, Uri.parse(genFile.path));
	   mp.start();
   }
   
   public void musStop()
   {
	   mp.stop();
   }
   
   public void musPause()
   {
	   mp.pause();
   }
   
   public void nonPause()
   {
	   mp.start();
   }
   
   public void loadGLTexture(GL10 var1, Context var2, int var3, int ind)
   {
	   Bitmap var4 = BitmapFactory.decodeResource(var2.getResources(), var3);
	   if(ind == 0){
		   var1.glGenTextures(1, this.textureIds, 0);
		   var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIds[0]);
	   }
	   else{
		   var1.glGenTextures(1, this.textureIdBorder, 0);
		   var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIdBorder[0]);
	   }
		  
	   
	   var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	   var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	   var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
	   var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	   GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, var4, 0);
	   var4.recycle();
   }
}