package com.example.surfandroid;

import java.util.ArrayList;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class bonus 
{
   private float[] texture = new float[]{0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F};
   private FloatBuffer textureBuffer;
   int[] textureIds = new int[1];
   int type_bonuse;
   private FloatBuffer vertexBuffer;
   private float[] vertices = new float[]{-1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F};
   boolean visible;
   Vec3 xyz = new Vec3();

   Vec3 disFromC = new Vec3();
   public bonus(float var1, float var2, float var3) 
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
      this.visible = false;
      this.type_bonuse = 1 + (new Random()).nextInt(3);
   }

   public void draw(GL10 var1) 
   {
	  var1.glEnable(GL10.GL_ALPHA_TEST);
      var1.glEnable(GL10.GL_TEXTURE_2D);
      var1.glEnable(GL10.GL_BLEND);
      var1.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
      var1.glAlphaFunc(GL10.GL_GREATER, 0.5f);
      
      var1.glBindTexture(GL10.GL_TEXTURE_2D, this.textureIds[0]);
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      
      var1.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      var1.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.textureBuffer);

      var1.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      var1.glDisable(GL10.GL_TEXTURE_2D);
      var1.glDisable(GL10.GL_BLEND);
      var1.glDisable(GL10.GL_ALPHA_TEST);
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
}

class line_bonus 
{
   ArrayList<bonus> bMap = new ArrayList<bonus>();
   int count;
   int numb = 0;
   
   public line_bonus(ArrayList<Vec3> var1, int lenTer) 
   {
      Random var2 = new Random();
      count = (int)(lenTer / 40);
      
      for(int var3 = 0; var3 < this.count; var3 ++) 
      {
         bonus var4 = new bonus(var1.get(var3 * 40).x - (float)(1 + 3 * var2.nextInt(3)), 1.0F + var1.get(var3 * 40).y, var3 * 40);
         this.bMap.add(var4);
      }
   }

   public void init()
   {
	   for(int var8 = 0; var8 < this.count; ++var8) this.bMap.get(var8).visible = false;
	   numb = 0;
   }
   
   public int draw(GL10 var1, float var2, float var3, float var4, float var5, float var6) 
   {
	  int kon;
	  if(this.numb + 10 < this.count) kon = this.numb + 10;
	  else kon = this.count;
		  
      int var7 = 0;

      for(int var8 = numb; var8 < kon; ++var8) 
      {
         var1.glLoadIdentity();
         if(10.0F * (var4 - this.bMap.get(var8).xyz.z / 10.0F) > - 2.0f) 
         {
            this.bMap.get(var8).visible = true;
            this.numb = var8;
         }

         if((10.0F * (var2 - this.bMap.get(var8).xyz.x / 10.0F) >= 1.0F 
           || 10.0F * (var2 - this.bMap.get(var8).xyz.x / 10.0F) <= -1.0F 
           || (double)(10.0F * (var3 + this.bMap.get(var8).xyz.y / 10.0F)) >= 0.3D 
           || 10.0F * (var4 + -this.bMap.get(var8).xyz.z / 10.0F) <= -5.0F) && !this.bMap.get(var8).visible) 
         {
            var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var6))), 1.0F, 0.0F, 0.0F);
            var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var5))), 0.0F, 1.0F, 0.0F);
            var1.glTranslatef(var2 - this.bMap.get(var8).xyz.x / 10.0F, var3 + this.bMap.get(var8).xyz.y / 10.0F, var4 - this.bMap.get(var8).xyz.z / 10.0F);
            var1.glScalef(0.1F, 0.1F, 0.1F);
            this.bMap.get(var8).draw(var1);
         } 
         else if(!this.bMap.get(var8).visible) 
         {
            var7 = this.bMap.get(var8).type_bonuse;
           this.bMap.get(var8).visible = true;
         }
      }
      return var7;
   }
}