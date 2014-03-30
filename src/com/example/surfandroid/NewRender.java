package com.example.surfandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.MotionEvent;
import java.util.ArrayList;

public class NewRender extends GLSurfaceView implements SensorEventListener 
{	
   Sensor O;
   private Context get_Context;
   int height;
   SensorManager m;
   private GLClass mRenderer;
   ArrayList<MediaPlayer> mp = new ArrayList<MediaPlayer>();
   float predFlf = 0.0F;
   float pred_x = 0.0F;
   float pred_y = 0.0F;
   int width;
   float xy_angle;
   float xz_angle;
   float zy_angle;

   @SuppressLint({"NewApi"})
   public NewRender(Context var1) {
      super(var1);
      this.get_Context = var1;
      if(Integer.valueOf(VERSION.SDK_INT).intValue() < 13) {
         Display var7 = ((Activity)var1).getWindowManager().getDefaultDisplay();
         this.width = var7.getWidth();
         this.height = var7.getHeight();
      } else {
         Display var3 = ((Activity)var1).getWindowManager().getDefaultDisplay();
         Point var4 = new Point();
         var3.getSize(var4);
         this.width = var4.x;
         this.height = var4.y;
      }

      Activity var5 = (Activity)this.get_Context;
      this.m = (SensorManager)var5.getSystemService("sensor");
      
      MediaPlayer var2 = MediaPlayer.create(get_Context, R.raw.bum);
      this.mp.add(var2);
      var2 = MediaPlayer.create(get_Context, R.raw.pum);
      this.mp.add(var2);
      
   }

   public void functionMenu(float var1, float var2) 
   {
      float var3 = (float)(-150 + this.height);
      if(!this.mRenderer.enable && var1 > (float)(-100 + this.width / 2) 
    	&& var1 < (float)(100 + this.width / 2) && var2 > var3 - var3 / 3.0F) 
      {
         ((Activity)this.get_Context).finish();
      }

   }

   public void functionPause(float var1, float var2) 
   {
      if(var1 > (float)(-150 + this.width) && var2 > (float)(-150 + this.height) && this.mRenderer.enable) 
      {
         this.mRenderer.enable = false;
      }

   }

   public void functionRestart(float var1, float var2) 
   {
      float var3 = (float)(-150 + this.height);
      if(!this.mRenderer.enable && var1 > (float)(-100 + this.width / 2) 
    	&& var1 < (float)(100 + this.width / 2) && var2 > (float)(this.height / 3) && var2 < var3 - var3 / 3.0F) 
      {
    	  
    	 this.mRenderer.cube.musStop();
         this.mRenderer.init(this.mp);
      }

   }

   public void functionStart(float var1, float var2) 
   {
      if(var1 > (float)(-100 + this.width / 2) && var1 < (float)(100 + this.width / 2) 
    	&& var2 > 0.0F && var2 < (float)(this.height / 3)) 
      {
         this.mRenderer.enable = true;
         this.mRenderer.cube.nonPause();
      }
   }

   public void onAccuracyChanged(Sensor var1, int var2) {}

   protected void onDestroy() 
   {
	   for(int var1 = 0; var1 < this.mRenderer.mp.size(); ++var1) 
	   {
	         if(this.mRenderer.mp.get(var1).isPlaying()) {
	        	 this.mRenderer.mp.get(var1).stop();
	        	 this.mRenderer.mp = null;
	         }
	  }
	   
	  this.mRenderer.cube.musStop();
      this.m.unregisterListener(this);
   }
   
   public void onPause()
   {
	   for(int var1 = 0; var1 < this.mRenderer.mp.size(); ++var1) {
	       if(this.mRenderer.mp.get(var1).isPlaying()) {
	    	   this.mRenderer.mp.get(var1).pause();
	       }
	   }
	   
	   this.mRenderer.cube.musPause();
   }
   
   // гироскоп
   public void onSensorChanged(SensorEvent var1) 
   {
	  // наклоняем телефон на 30 градусов, ничего не изменяется! 
	  if((double)var1.values[1] < 3.14/6.f || (double)var1.values[1] > 3.14 - 3.14/6.f)
	  {
		  
	  }
	  
	  // наклоняем на больше чем +-30 градусов, но меньше, чем на +-60
	  if((double)var1.values[1] >= 3.14/6.f && (double)var1.values[1] < 3.14/3.f)
	  {
		  //this.mRenderer.dx_turn += 0.14;
	  }
	  
	  if((double)var1.values[1] > 3.14 - 3.14/3.f && (double)var1.values[1] <= 3.14 - 3.14/6.f)
	  {
		  //this.mRenderer.dx_turn -= 0.164;
	  }
	  // наклоняем на больше чем +-60 градусов
	  if((double)var1.values[1] > 3.14/3.f)
	  {
		  //this.mRenderer.dx_turn += 0.2;
	  }
		  
	  if((double)var1.values[1] < 3.14 - 3.14/3.f)
	  {
		  //this.mRenderer.dx_turn -= 0.2;
	  }
      this.predFlf = var1.values[1];
   }

   public boolean onTouchEvent(MotionEvent var1) 
   {
      switch(var1.getAction()) 
      {
	      case 0:
	         this.pred_x = var1.getY();
	         this.pred_y = var1.getX();
	         this.functionPause(var1.getX(), var1.getY());
	         this.functionStart(var1.getX(), var1.getY());
	         this.functionMenu(var1.getX(), var1.getY());
	         this.functionRestart(var1.getX(), var1.getY());
	      case 1:
	      default:
	         break;
	      case 2:
	         this.setRot(var1.getY(), var1.getX());
      }
      return true;
   }

   public void setRot(float var1, float var2) 
   {
      if(this.mRenderer.enable) 
      {
         if(this.pred_y < var2 && !this.mRenderer.up)
         {
            this.mRenderer.dx_turn = 6.5E-4F * Math.abs(var2 - this.pred_y);
         }

         if(this.pred_y > var2 && !this.mRenderer.up) 
         {
            this.mRenderer.dx_turn = 6.5E-4F * -Math.abs(var2 - this.pred_y);
         }

         if(var1 - this.pred_x > -150.0F && var1 - this.pred_x < -80.0F && !this.mRenderer.up && Math.abs(var2 - this.pred_y) < 30.0F) 
         {
            this.mRenderer.up = true;
         }
      }
   }

   public void start() 
   {
	   this.mRenderer = new GLClass(this.get_Context, this.mp);
	   this.setRenderer(this.mRenderer);
	   this.m.registerListener(this, this.m.getDefaultSensor(3), 3);
   }
}
