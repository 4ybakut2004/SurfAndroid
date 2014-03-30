package com.example.surfandroid;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLClass implements Renderer 
{
   int ball;
   line_bonus bonus_star;
   Cube cube;
   Cube_line cube_line;
   ArrayList<fire> fireMap = new ArrayList<fire>();
   float dlx = 0.0F;
   float dly = 0.0F;
   float dx = 0.0F;
   float dx_turn;
   float dy = 0.0F;
   float dy_b = 0.0F;
   float dy_up;
   float dz = 0.1F;
   public boolean enable;
   Context get_Context;
   GL10 gl_copy;
   int height_h;
   boolean left;
   line_cool lineCool;
   pauseMenu menu;
   ArrayList<MediaPlayer> mp;
   show_ball pauseButton;
   boolean rigth;
   int sign = 1;
   skyBox sky;
   int tack = 0;
   boolean up;
   int width_w;

   public void Mus(boolean var1) 
   {
      for(int var2 = 0; var2 < this.mp.size(); ++var2) 
      {
         if(this.mp.get(var2).isPlaying()) 
         {
            if(var1) 
            {
               this.mp.get(var2).start();
            } 
            else 
            {
               this.mp.get(var2).pause();
            }
         }
      }

   }

   public GLClass(Context var1, ArrayList<MediaPlayer> var2) 
   {
      this.get_Context = var1;
      this.cube = new Cube(var1);
      this.cube_line = new Cube_line(this.cube.Camera, this.cube.Size_y);
      this.bonus_star = new line_bonus(this.cube.Camera, this.cube.Size_y);
      this.init(var2);
      this.pauseButton = new show_ball(-0.235F, -0.1F, 0.3F);
      this.pauseButton.setStartText(1.0F, 0.0F);
      this.menu = new pauseMenu();
      this.sky = new skyBox();
   }

   public void init(ArrayList<MediaPlayer> var1) 
   {
      this.cube_line.init();
      this.bonus_star.init();
      this.lineCool = new line_cool();
      this.fireMap.clear();
      this.tack = 0;
      this.dlx = 0.0F;
      this.dly = 0.0F;
      this.dx = 0.0F;
      this.dy = 0.0F;
      this.dy_b = 0.0F;
      this.dz = 0.1F;
      this.up = false;
      this.left = true;
      this.rigth = true;
      this.dy_up = 0.0F;
      this.dx_turn = 0.0F;
      this.sign = 1;
      this.cube.posCamera.x = -0.15F;
      this.cube.posCamera.y = -0.18F;
      this.cube.posCamera.z = 0.0F;
      this.ball = 0;
      this.enable = true;
      this.mp = var1;
      
      this.cube.musStart();
   }

   public void loadTexture(GL10 var1) 
   {
	   
	  this.cube.loadGLTexture(var1, this.get_Context, R.drawable.fon, 0);
	  this.cube.loadGLTexture(var1, this.get_Context, R.drawable.border, 1);
	  
      for(int var2 = 0; var2 < this.cube_line.bird.size(); ++var2) 
      {
         this.cube_line.bird.get(var2).bird.loadGLTexture(var1, this.get_Context, R.drawable.burd);
      }

      for(int var3 = 0; var3 < this.bonus_star.count; ++var3) 
      {
         switch(this.bonus_star.bMap.get(var3).type_bonuse)
         {
	         case 1:
	            this.bonus_star.bMap.get(var3).loadGLTexture(var1, this.get_Context, R.drawable.blue_star);
	            break;
	         case 2:
	            this.bonus_star.bMap.get(var3).loadGLTexture(var1, this.get_Context, R.drawable.star);
	            break;
	         case 3:
	            this.bonus_star.bMap.get(var3).loadGLTexture(var1, this.get_Context, R.drawable.star_red);
         }
      }

      for(int var4 = 0; var4 < this.cube_line.ballLine.count; ++var4) 
      {
         this.cube_line.ballLine.cMap.get(var4).loadGLTexture(var1, this.get_Context, R.drawable.shift);
      }

      this.pauseButton.loadGLTexture(var1, this.get_Context, R.drawable.pausa);
      
      this.sky.cMap.get(0).loadGLTexture(var1, this.get_Context, R.drawable.grimmnight_rt);
      this.sky.cMap.get(1).loadGLTexture(var1, this.get_Context, R.drawable.grimmnight_bk);
      this.sky.cMap.get(2).loadGLTexture(var1, this.get_Context, R.drawable.grimmnight_ft);
      this.sky.cMap.get(3).loadGLTexture(var1, this.get_Context, R.drawable.grimmnight_lf);
      this.sky.cMap.get(4).loadGLTexture(var1, this.get_Context, R.drawable.grimmnight_up);
      this.menu.restart.loadGLTexture(var1, this.get_Context, R.drawable.restart);
      this.menu.start.loadGLTexture(var1, this.get_Context, R.drawable.play);
      this.menu.back_menu.loadGLTexture(var1, this.get_Context, R.drawable.menu);
   }

   public void onDrawFrame(GL10 var1) 
   {
      var1.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

      this.gl_copy = var1;
      Vec3 var2 = this.cube.posCamera;
      int var3 = (int)(10.0F * this.cube.posCamera.z);
      this.sky.draw(var1, this.dlx, this.dly);
      
      for(int i = 0; i < this.fireMap.size(); i++)
      {
    	  this.fireMap.get(i).draw(var1);
      }
      
      if(var3 >= this.cube.Size_y - 10 && this.enable) 
      {
         this.enable = false;
         Mus(false);
         cube.musStop();
         int var6 = 5;
         
         WorkFile var7 = new WorkFile();
         var7.readFile();

         for(int var8 = 4; var8 >= 0; --var8) 
         {
            if(var7.nameCool.get(var8).cool < this.cube_line.ball) 
            {
               --var6;
            }
         }

         if(var6 < 5) 
         {
            for(int var9 = 4; var9 >= var6; --var9) 
            {
      		   if(var9 > 0)
      		   {
               		var7.nameCool.get(var9).name = var7.nameCool.get(var9 - 1).name;
               		var7.nameCool.get(var9).cool = var7.nameCool.get(var9 - 1).cool;
               }
            }

            var7.nameCool.get(var6).name = var7.nickName;
            var7.nameCool.get(var6).cool = this.cube_line.ball;
            var7.writeFile();
         }
      }

      if(this.enable) 
      {
         this.dy = 0.0F;
         this.dx = 0.0F;
         this.dly = 0.0F;
         this.dlx = 0.0F;
         var2.z += this.dz;
         this.dy = Math.abs(this.cube.posCamera.z - (float)var3 / 10.0F) * (this.cube.Camera.get(var3 + 1).y - this.cube.Camera.get(var3).y);
         this.dy += this.cube.Camera.get(var3).y / 10.0F;
         this.dx = Math.abs(this.cube.posCamera.z - (float)var3 / 10.0F) * (this.cube.Camera.get(var3 + 1).x - this.cube.Camera.get(var3).x);
         this.dx += this.cube.Camera.get(var3).x / 10.0F - 0.3F;
         this.dly = (float)Math.atan((double)(this.cube.Camera.get(var3 + 1).y - this.cube.Camera.get(var3).y));
         this.dlx = (float)Math.atan((double)(this.cube.Camera.get(var3 + 1).x - this.cube.Camera.get(var3).x));
         if(this.dx_turn != 0.0F) 
         {
            if(this.dx_turn < 0.0F && this.left) 
            {
               if(!this.rigth) 
               {
                  this.rigth = true;
               }

               if(10.0F * (this.cube.posCamera.x + this.dx + this.dx_turn) <= this.cube.Camera.get(var3).x - 7.0F) 
               {
                  this.left = false;
               }

               Vec3 var5 = this.cube.posCamera;
               var5.x += this.dx_turn;
            }

            if(this.dx_turn > 0.0F && this.rigth) 
            {
               if(!this.left) 
               {
                  this.left = true;
               }

               if(10.0F * (this.cube.posCamera.x + this.dx + this.dx_turn) >= this.cube.Camera.get(var3).x - 2.0F) 
               {
                  this.rigth = false;
               }

               Vec3 var4 = this.cube.posCamera;
               var4.x += this.dx_turn;
            }

            this.dx_turn = 0.0F;
         }
      }

      if(this.up && this.enable) 
      {
         if((double)this.dy_up > 0.4D) 
         {
            this.sign = -this.sign;
         }

         this.dy_up = (float)((double)this.dy_up + 0.007D * (double)this.sign);
         if((double)this.dy_up < 0.007D) 
         {
            this.up = false;
            this.sign = 1;
         }

         this.repeatCode(var1, this.cube.posCamera.x + this.dx, this.cube.posCamera.y - this.dy - this.dy_up, this.cube.posCamera.z);
      } 
      else 
      {
         this.repeatCode(var1, this.cube.posCamera.x + this.dx, this.cube.posCamera.y - this.dy + this.dy_b, this.cube.posCamera.z);
      }
     
      var1.glLoadIdentity();
      var1.glTranslatef(-this.lineCool.xyz.x, this.lineCool.xyz.y, -this.lineCool.xyz.z);
      var1.glScalef(0.1F, 0.1F, 0.1F);
      this.lineCool.draw(var1);
      var1.glLoadIdentity();
      var1.glTranslatef(-this.pauseButton.xyz.x, this.pauseButton.xyz.y, -this.pauseButton.xyz.z);
      var1.glScalef(0.02F, 0.02F, 0.1F);
      this.pauseButton.draw(var1);
      
      if(!this.enable) 
      {
         this.menu.draw(var1);
         cube.musPause();
         Mus(false);
      }

   }

   public void onSurfaceChanged(GL10 var1, int var2, int var3) 
   {
      if(var3 == 0) 
      {
         var3 = 1;
      }

      var1.glLoadIdentity();
      var1.glViewport(0, 0, var2, var3);
      var1.glMatrixMode(GL10.GL_PROJECTION);
      var1.glLoadIdentity();
      GLU.gluPerspective(var1, 45.0F, (float)var2 / (float)var3, 0.1F, 10.0F);
      var1.glMatrixMode(GL10.GL_MODELVIEW);
      var1.glLoadIdentity();
   }

   public void onSurfaceCreated(GL10 gl, EGLConfig var2) 
   {
	   gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
	   gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
	   gl.glClearDepthf(1.0f); 						//Depth Buffer Setup
	   gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
	   gl.glDepthFunc(GL10.GL_LEQUAL); 				//The Type Of Depth Testing To Do
		
	   loadTexture(gl);
	   //Really Nice Perspective Calculations
	   gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
   }

   public void repeatCode(GL10 var1, float var2, float var3, float var4) 
   {
	  
	  var1.glLoadIdentity();
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dly))), 1.0F, 0.0F, 0.0F);
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dlx))), 0.0F, 1.0F, 0.0F);
      var1.glTranslatef(var2, var3, var4);
      var1.glScalef(0.1F, 0.1F, 0.1F);
      var1.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      
      this.cube.draw(var1);
      
      var1.glLoadIdentity();
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dly))), 1.0F, 0.0F, 0.0F);
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dlx))), 0.0F, 1.0F, 0.0F);
      var1.glTranslatef(var2, var3, var4);
      var1.glScalef(0.1F, 0.1F, 0.1F);
      var1.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      
      this.cube.drawBorder(var1);
      
      var1.glLoadIdentity();
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dly))), 1.0F, 0.0F, 0.0F);
      var1.glRotatef(-((float)Math.toDegrees((double)(0.0F + this.dlx))), 0.0F, 1.0F, 0.0F);
      var1.glTranslatef(var2 + 1.5f, var3, var4);
      var1.glScalef(0.1F, 0.1F, 0.1F);
      var1.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      
      this.cube.drawBorder(var1);
	      
      int var5 = this.ball;
      this.ball = this.cube_line.draw(var1, var2, var3, var4, this.dlx, this.dly, this.get_Context);
      if(this.ball < var5) 
      {
         this.lineCool.setVertex(-0.1F, true);
         this.mp.get(0).start();
      }

      if(this.ball > var5) 
      {
         this.lineCool.setVertex(0.1F, true);
         this.mp.get(0).start();
      }
      
      switch(this.bonus_star.draw(var1, var2, var3, var4, this.dlx, this.dly)) 
      {
	      case 1: // должно быть ускорение, но нет...
	         this.mp.get(1).start();
	         this.fireMap.add(new fire(var2, var3, var4, new float[] {0, 0, 1, 1})); 
	         break;
	      case 2: // удвоить очки за кубики! 
	    	 this.cube_line.doublePride();
	         this.mp.get(1).start();
	         this.fireMap.add(new fire(var2, var3, var4, new float[] {0, 1, 0, 1}));
	         break;
	      case 3: // закрашивает кубики в серый
	         this.cube_line.gray_cube(var1, this.cube_line.numb);
	         this.mp.get(1).start();
	         this.fireMap.add(new fire(var2, var3, var4, new float[] {1, 0, 0, 1}));
      }
      
      int k = this.fireMap.size();
      int j = 0;
      while(j < k)
      {
    	  if(this.fireMap.get(j).mapFire.get(0).color[3] <= 0.0f)
    	  {
    		  this.fireMap.remove(j); 
    	  }
    	  else
    	  {
    		  j++;
    	  }
    	  k = this.fireMap.size();
      }
      if(this.dy_b < 0.0F) 
      {
         if(this.tack < 100) 
         {
            ++this.tack;
         } 
         else 
         {
            this.tack = 0;
            this.dy_b = 0.0F;
         }
      }
   }
}
