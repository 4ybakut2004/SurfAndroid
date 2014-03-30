package com.example.surfandroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;


class CubeClass {

   public FloatBuffer colorBuffer;
   private ShortBuffer indexBuffer;
   private short[] indices;
   private FloatBuffer vertexBuffer;
   private float[] vertices = new float[]{-1.0F, -0.5F, -1.0F, 1.0F, -0.5F, -1.0F, 1.0F, 0.5F, -1.0F, -1.0F, 0.5F, -1.0F, -1.0F, -0.5F, 1.0F, 1.0F, -0.5F, 1.0F, 1.0F, 0.5F, 1.0F, -1.0F, 0.5F, 1.0F};


   public CubeClass(ColorCube var1) 
   {
      short[] var2 = new short[]{(short)0, (short)4, (short)5, (short)0, (short)5, (short)1, (short)1, (short)5, (short)6, (short)1, (short)6, (short)2, (short)2, (short)6, (short)7, (short)2, (short)7, (short)3, (short)3, (short)7, (short)4, (short)3, (short)4, (short)0, (short)4, (short)7, (short)6, (short)4, (short)6, (short)5, (short)3, (short)0, (short)1, (short)3, (short)1, (short)2};
      this.indices = var2;
      ByteBuffer var3 = ByteBuffer.allocateDirect(4 * this.vertices.length);
      var3.order(ByteOrder.nativeOrder());
      this.vertexBuffer = var3.asFloatBuffer();
      this.vertexBuffer.put(this.vertices);
      this.vertexBuffer.position(0);
      
      ByteBuffer var7 = ByteBuffer.allocateDirect(8 * 4 * 4);
      var7.order(ByteOrder.nativeOrder());
      this.colorBuffer = var7.asFloatBuffer();
      float[] var9 = new float[]{var1.r, var1.b, var1.g, var1.a};

      for(int var10 = 0; var10 < 8; ++var10) 
      {
         this.colorBuffer.put(var9);
      }

      this.colorBuffer.position(0);
      this.indexBuffer = ShortBuffer.allocate(2 * this.indices.length);
      this.indexBuffer.put(this.indices);
      this.indexBuffer.position(0);
   }

   public void draw(GL10 var1) 
   {
	  var1.glAlphaFunc(GL10.GL_GREATER, 0.5f);
      var1.glFrontFace(GL10.GL_CW);
      var1.glEnable(GL10.GL_BLEND);
      var1.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
      
      var1.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexBuffer);
      
      var1.glEnableClientState(GL10.GL_COLOR_ARRAY);
      var1.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorBuffer);
      
      var1.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_SHORT, this.indexBuffer);
      
      var1.glDisableClientState(GL10.GL_VERTEX_ARRAY);
      var1.glDisableClientState(GL10.GL_COLOR_ARRAY);
      
      var1.glDisable(GL10.GL_BLEND);
      var1.glDisable(GL10.GL_ALPHA_TEST);
   }

   public void setColor(GL10 var1) 
   {
      float[] var2 = new float[]{0.5F, 0.5F, 0.5F, 0.6F};
      this.colorBuffer.position(0);

      for(int var4 = 0; var4 < 8; ++var4) 
      {
         this.colorBuffer.put(var2);
      }

      this.colorBuffer.position(0);
   }
}
