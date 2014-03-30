package com.example.surfandroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

class Vec3{
	float x;
	float y;
	float z;
	
	public Vec3(){
		x = 0;
		y = 0;
		z = 0;
	}
}
public class line_cool 
{
   float[] color = new float[]{1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F};
   private FloatBuffer colorBuffer;
   private FloatBuffer vertexBuffer;
   private float[] vertices = new float[]{-1.0F, -0.25F, 0.0F, -0.9F, -0.25F, 0.0F, -1.0F, 0.25F, 0.0F, -0.9F, 0.25F, 0.0F};
   
   Vec3 disFromC = new Vec3();
   Vec3 xyz = new Vec3();
   
   boolean coolFl;

   public line_cool() 
   {
	  xyz.x = 0.6F;
	  xyz.y = 0.3F;
	  xyz.z = 0.8F;
	  
	  coolFl = false;
	  
      ByteBuffer var1 = ByteBuffer.allocateDirect(4 * this.vertices.length);
      var1.order(ByteOrder.nativeOrder());
      this.vertexBuffer = var1.asFloatBuffer();
      this.vertexBuffer.put(this.vertices);
      this.vertexBuffer.position(0);
      
      ByteBuffer var5 = ByteBuffer.allocateDirect(4 * this.color.length);
      var5.order(ByteOrder.nativeOrder());
      this.colorBuffer = var5.asFloatBuffer();
      this.colorBuffer.put(this.color);
      this.colorBuffer.position(0);
   }

   public void draw(GL10 var1) 
   {
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      
      var1.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);
      var1.glEnableClientState(GL10.GL_COLOR_ARRAY);
      
      var1.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_COLOR_ARRAY);
   }

   public void drawTer(GL10 var1) 
   {
	  var1.glDisable(GL10.GL_DEPTH_TEST);
	  var1.glEnable(GL10.GL_BLEND);
	  var1.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_DST_ALPHA);
	  
      var1.glFrontFace(GL10.GL_CW);
      
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      
      var1.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);
      var1.glEnableClientState(GL10.GL_COLOR_ARRAY);
      
      var1.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.vertices.length / 3);
   
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_COLOR_ARRAY);
      
      var1.glDisable(GL10.GL_BLEND);
      var1.glEnable(GL10.GL_DEPTH_TEST);
      GLES20.glBlendEquation( GLES20.GL_FUNC_ADD);
   }
   
   public void setColor(float var1, float var2, float var3, float var4) {
      this.colorBuffer.position(0);
      this.color = new float[]{var1, var2, var3, var4, var1, var2, var3, var4, var1, var2, var3, var4, var1, var2, var3, var4};
      this.colorBuffer.put(this.color);
      this.colorBuffer.position(0);
   }

   public void setVertex(float var1, boolean var2) {
      if(var2) {
         if(var1 >= 0.0F && this.vertices[3] < 1.0F) {
            this.vertices[3] += var1;
            this.vertices[9] += var1;
         } else {
            this.vertices[3] = -0.9F;
            this.vertices[9] = -0.9F;
            if(var1 > 0) coolFl = true;
         }
      } else {
         this.vertices = new float[]{-3.0F, -1.5F, 0.0F, 3.0F, -1.5F, 0.0F, -3.0F, 1.5F, 0.0F, 3.0F, 1.5F, 0.0F};
      }

      this.vertexBuffer.position(0);
      this.vertexBuffer.put(this.vertices);
      this.vertexBuffer.position(0);
   }
   
   public void setVertexF(boolean fl)
   {
	   if(fl) this.vertices = new float[]{-0.5F, -0.5F, 0.0F, 0.5F, -0.5F, 0.0F, -0.5F, 0.5F, 0.0F, 0.5F, 0.5F, 0.0F};
	   else this.vertices = new float[]{0.0F, -0.7F, 0.0F, 0.7F, 0.0F, 0.0F, -0.7F, 0.0F, 0.0F, 0.0F, 0.7F, 0.0F};
	   this.vertexBuffer.position(0);
	   this.vertexBuffer.put(this.vertices);
	   this.vertexBuffer.position(0);
   }
}