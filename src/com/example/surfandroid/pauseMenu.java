package com.example.surfandroid;

import javax.microedition.khronos.opengles.GL10;

class pauseMenu 
{
   show_ball back_menu = new show_ball(0.0F, -0.07F, 0.3F);
   line_cool menu = new line_cool();
   show_ball restart = new show_ball(0.0F, 0.0F, 0.3F);
   show_ball start = new show_ball(0.0F, 0.07F, 0.3F);

   public pauseMenu() 
   {
      this.restart.setStartText(1.0F, 0.0F);
      this.start.setStartText(1.0F, 0.0F);
      this.back_menu.setStartText(1.0F, 0.0F);
      this.menu.setColor(0.0F, 0.0F, 0.0F, 0.7F);
      this.menu.setVertex(0.0F, false);
      this.menu.xyz.x = 0.0F;
      this.menu.xyz.y = 0.0F;
      this.menu.xyz.z = 0.3F;
   }

   public void draw(GL10 var1) 
   {
      var1.glLoadIdentity();
      var1.glTranslatef(-this.menu.xyz.x, this.menu.xyz.y, -this.menu.xyz.z);
      var1.glScalef(0.1F, 0.1F, 0.1F);
      this.menu.draw(var1);
      var1.glLoadIdentity();
      var1.glTranslatef(-this.start.xyz.x, this.start.xyz.y, -this.start.xyz.z);
      var1.glScalef(0.02F, 0.02F, 0.1F);
      this.start.draw(var1);
      var1.glLoadIdentity();
      var1.glTranslatef(-this.restart.xyz.x, this.restart.xyz.y, -this.restart.xyz.z);
      var1.glScalef(0.02F, 0.02F, 0.1F);
      this.restart.draw(var1);
      var1.glLoadIdentity();
      var1.glTranslatef(-this.back_menu.xyz.x, this.back_menu.xyz.y, -this.back_menu.xyz.z);
      var1.glScalef(0.02F, 0.02F, 0.1F);
      this.back_menu.draw(var1);
   }
}