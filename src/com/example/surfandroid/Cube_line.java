package com.example.surfandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;

class ColorCube 
{
   public float a;
   public float b;
   public float g;
   public float r;

   public ColorCube(int var1) 
   {
      switch(var1) 
      {
	      case 0:
	         this.r = 0.4F;
	         this.b = 0.8F;
	         this.g = 0.4F;
	         this.a = 0.6F;
	         return;
	      case 1:
	         this.r = 0.4F;
	         this.b = 0.2F;
	         this.g = 0.4F;
	         this.a = 0.6F;
	         return;
	      case 2:
	         this.r = 0.8F;
	         this.b = 0.4F;
	         this.g = 0.4F;
	         this.a = 0.6F;
	         return;
	      default:
	         this.r = 0.5F;
	         this.b = 0.5F;
	         this.g = 0.5F;
	         this.a = 0.6F;
      }
   }
}


class ball_line 
{
   ArrayList<show_ball> cMap = new ArrayList<show_ball>();
   int count = 5;

   public ball_line() 
   {
      for(int var1 = 0; var1 < this.count; ++var1) 
      {
         show_ball var2 = new show_ball(-0.66F + 0.04F * (float)var1, 0.3F, 0.8F);
         this.cMap.add(var2);
      }

   }

   public void draw(GL10 var1) 
   {
      for(int var2 = 0; var2 < this.count; ++var2) 
      {
         var1.glLoadIdentity();
         var1.glTranslatef(- this.cMap.get(var2).xyz.x, this.cMap.get(var2).xyz.y, - this.cMap.get(var2).xyz.z);
         var1.glScalef(0.03F, 0.03F, 0.1F);
         this.cMap.get(var2).draw(var1);
      }

   }

   public void setBall(int var1) 
   {
      for(int var2 = 0; var2 < this.count; ++var2) 
      {
         int var3 = var1 % 10;
         var1 /= 10;
         this.cMap.get(var2).setText(var3, 0.1F);
      }

   }
}
class show_ball 
{
   private float[] texture = new float[]{0.0F, 1.0F, 0.1F, 1.0F, 0.0F, 0.0F, 0.1F, 0.0F};
   private FloatBuffer textureBuffer;
   int[] textureIds = new int[1];
   private FloatBuffer vertexBuffer;
   private float[] vertices = new float[]{-1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F};
   Vec3 xyz = new Vec3();

   public show_ball(float var1, float var2, float var3) 
   {
      ByteBuffer var4 = ByteBuffer.allocateDirect(4 * this.vertices.length);
      var4.order(ByteOrder.nativeOrder());
      this.vertexBuffer = var4.asFloatBuffer();
      this.vertexBuffer.put(this.vertices);
      this.vertexBuffer.position(0);
      ByteBuffer var8 = ByteBuffer.allocateDirect(4 * this.texture.length);
      var8.order(ByteOrder.nativeOrder());
      this.textureBuffer = var8.asFloatBuffer();
      this.textureBuffer.put(this.texture);
      this.textureBuffer.position(0);
      this.xyz.x = var1;
      this.xyz.y = var2;
      this.xyz.z = var3;
   }

   public void draw(GL10 var1) 
   {
	  var1.glDisable(GL10.GL_DEPTH_TEST); 
      var1.glEnable(GL10.GL_TEXTURE_2D);
      var1.glEnable(GL10.GL_BLEND);
      var1.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
      
      var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIds[0]);
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      
      var1.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);
      var1.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      var1.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      var1.glDisable(GL10.GL_TEXTURE_2D);
      var1.glDisable(GL10.GL_BLEND);
      var1.glEnable(GL10.GL_DEPTH_TEST);
   }

   public void loadGLTexture(GL10 var1, Context var2, int var3) 
   {
	   Bitmap var4 = BitmapFactory.decodeResource(var2.getResources(), var3);
       var1.glGenTextures(1, this.textureIds, 0);
       var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIds[0]);
       var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
       var1.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
       GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, var4, 0);
       var4.recycle();
   }

   public void setStartText(float var1, float var2) 
   {
      this.textureBuffer.position(0);
      this.texture[2] = var1;
      this.texture[6] = var1;
      this.texture[0] = var2;
      this.texture[4] = var2;
      this.textureBuffer.put(this.texture);
      this.textureBuffer.position(0);
   }

   public void setText(int var1, float var2) 
   {
      this.textureBuffer.position(0);
      this.texture[2] = var2 + var2 * (float)var1;
      this.texture[6] = var2 + var2 * (float)var1;
      this.texture[0] = 0.0F + var2 * (float)var1;
      this.texture[4] = 0.0F + var2 * (float)var1;
      this.textureBuffer.put(this.texture);
      this.textureBuffer.position(0);
   }

   public void setVertex(int var1) 
   {
      this.vertexBuffer.position(0);
      switch(var1) 
      {
	      case 0:
	         this.vertices = new float[]{-1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F};
	         break;
	      case 1:
	         this.vertices = new float[]{-1.0F, -1.0F, 2.0F, -1.0F, -1.0F, 0.0F, -1.0F, 1.0F, 2.0F, -1.0F, 1.0F, 0.0F};
	         break;
	      case 2:
	         this.vertices = new float[]{1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 2.0F};
	         break;
	      case 3:
	         this.vertices = new float[]{-1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, 1.0F, 2.0F};
	         break;
	      case 4:
	         this.vertices = new float[]{-1.0F, -1.0F, 2.0F, 1.0F, -1.0F, 2.0F, -1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F};
      }

      this.vertexBuffer.put(this.vertices);
      this.vertexBuffer.position(0);
   }
}
class cubeMap 
{
   public ColorCube color_cube;
   public CubeClass cube;
   public int type_cube;
   public boolean visible;
   Vec3 xyz = new Vec3();

   public cubeMap(int var1) 
   {
      this.color_cube = new ColorCube(var1);
      this.cube = new CubeClass(this.color_cube);
      this.type_cube = var1;
      this.visible = false;
   }
}

public class Cube_line 
{
   int ball;
   ball_line ballLine = new ball_line();
   ArrayList<Bird> bird = new ArrayList<Bird>();
   ArrayList<cubeMap> cMap = new ArrayList<cubeMap>();
   int prideCube;
   int count;
   int lenTerrin;
   public int numb;

   public Cube_line(ArrayList<Vec3> var1, int lenTer)
   {
	   lenTerrin = lenTer;
	   count = (int)(lenTerrin / 50);
	   
       Random var2 = new Random();
       int var3 = 0;
       this.cMap.clear();

       for(int var4 = 0; var4 < this.count; var3 += 50, var4++) 
       {
         cubeMap var5 = new cubeMap(var2.nextInt(4));
         var5.xyz.x = var1.get(var3).x - (float)(1 + 3 * var2.nextInt(3));
         var5.xyz.y = 1.0F + var1.get(var3).y;
         var5.xyz.z = (float)var3;
         this.cMap.add(var5);
       }
   }

   public int draw(GL10 var1, float var2, float var3, float var4, float var5, float var6, Context var7) 
   {
	   
	  int kon;
	  if(this.numb + 10 < this.count) kon = this.numb + 10;
	  else kon = this.count;
	  
      for(int var8 = this.numb; var8 < kon; ++var8) 
      {
         var1.glLoadIdentity();
         if(10.0F * (var4 - this.cMap.get(var8).xyz.z / 10.0F) > -2.0F) 
         {
            this.numb = var8;
            this.cMap.get(var8).visible = true;
         }

         if((10.0F * (var2 - this.cMap.get(var8).xyz.x / 10.0F) >= 1.0F 
          || 10.0F * (var2 - this.cMap.get(var8).xyz.x / 10.0F) <= -1.0F 
          || (double)(10.0F * (var3 + this.cMap.get(var8).xyz.y / 10.0F)) >= 0.3D 
          || 10.0F * (var4 - this.cMap.get(var8).xyz.z / 10.0F) <= -5.0F) 
          && !(this.cMap.get(var8)).visible) 
         {
            var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var6))), 1.0F, 0.0F, 0.0F);
            var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var5))), 0.0F, 1.0F, 0.0F);
            var1.glTranslatef(var2 - this.cMap.get(var8).xyz.x / 10.0F, var3 + this.cMap.get(var8).xyz.y / 10.0F, var4 -this.cMap.get(var8).xyz.z / 10.0F);
            var1.glScalef(0.1F, 0.1F, 0.1F);
            var1.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.cMap.get(var8).cube.draw(var1);
         } 
         else if(!this.cMap.get(var8).visible) 
         {
            this.cMap.get(var8).visible = true;
            int pride = prideCube;
            if(this.cMap.get(var8).type_cube > 2)
            {
            	pride = - prideCube;
            	prideCube = 1;
            }
            else prideCube += 2;
            
            if(this.ball + pride >= 0) 
            {
               this.ball += pride;
            } 
            else 
            {
               this.ball = 0;
            }

            this.ballLine.setBall(this.ball);
            Bird var9 = new Bird();
            var9.birdFl = true;
            var9.bird.loadGLTexture(var1, var7, R.drawable.burd);
            this.bird.add(var9);
         }
      }

      this.ballLine.draw(var1);

      for(int var11 = 0; var11 < this.bird.size(); ++var11) 
      {
         if(this.bird.get(var11).birdFl) 
         {
            this.bird.get(var11).draw(var1);
         }
      }

      for(int var12 = 0; var12 < this.bird.size(); ++var12) 
      {
         if(!this.bird.get(var12).birdFl) 
         {
            this.bird.remove(var12);
         }
      }

      return this.ball;
   }

   public void gray_cube(GL10 var1, int var2) 
   {
      int var3;
      if(var2 + 5 < this.count){
         var3 = var2 + 5;
      } 
      else {
         var3 = this.count;
      }

      for(int var4 = var2; var4 < var3; ++var4) {
    	  this.cMap.get(var4).cube.setColor(var1);
    	  this.cMap.get(var4).type_cube = 3;
      }

   }

   public void doublePride()
   {
	   prideCube *= 2;
   }
   
   public void init() 
   {
	   for(int var8 = 0; var8 < this.count; ++var8) this.cMap.get(var8).visible = false;
	   this.ball = 0;
       this.numb = 0;
       prideCube = 1;
       this.ballLine.setBall(this.ball);
   }
}
