package com.example.surfandroid;

import javax.microedition.khronos.opengles.GL10;

public class Bird 
{
   int a;
   show_ball bird = new show_ball(0.0F, 0.0F, 0.6F);
   boolean birdFl;
   int kol;


   public Bird() 
   {
      this.bird.setStartText(0.25F, 0.0F);
      this.kol = 0;
      this.a = 0;
      this.birdFl = false;
   }

   public void Fly() 
   {
      if(this.bird.xyz.x < 0.6F) 
      {
         this.bird.xyz.x += 0.01F;
         this.bird.xyz.y += 0.005F;
      }

      if(this.bird.xyz.x >= 0.6F) 
      {
         this.bird.xyz.x = 0.0F;
         this.bird.xyz.y = 0.0F;
         this.birdFl = false;
      }
   }

   public void draw(GL10 var1) 
   {
      if(this.kol % 7 == 0) 
      {
         ++this.a;
         this.bird.setText(this.kol / 7, 0.25F);
      }

      if(this.kol > 21) 
      {
         this.kol = 0;
         this.a = 0;
      }

      var1.glLoadIdentity();
      var1.glTranslatef(-this.bird.xyz.x, this.bird.xyz.y, -this.bird.xyz.z);
      var1.glScalef(0.02F, 0.02F, 0.02F);
      this.bird.draw(var1);
      ++this.kol;
      if(this.birdFl) 
      {
         this.Fly();
      }
   }
}
