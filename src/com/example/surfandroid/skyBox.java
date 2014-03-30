package com.example.surfandroid;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class skyBox 
{
   ArrayList<show_ball> cMap = new ArrayList<show_ball>();

   public skyBox() 
   {
      for(int var1 = 0; var1 < 5; ++var1) 
      {
         show_ball var2 = new show_ball(0.0F, 0.0F, 1.0F);
         var2.setVertex(var1);
         var2.setStartText(1.0F, 0.0F);
         this.cMap.add(var2);
      }

   }

   public void draw(GL10 var1, float var2, float var3) 
   {
      for(int var4 = 0; var4 < 5; ++var4) 
      {
         var1.glLoadIdentity();
         var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var3))), 1.0F, 0.0F, 0.0F);
         var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + var2))), 0.0F, 1.0F, 0.0F);
         var1.glTranslatef(-this.cMap.get(var4).xyz.x, this.cMap.get(var4).xyz.y, -this.cMap.get(var4).xyz.z);
         var1.glScalef(1.0F, 1.0F, 1.0F);
         this.cMap.get(var4).draw(var1);
      }
   }
}