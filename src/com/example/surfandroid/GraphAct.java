package com.example.surfandroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class GraphAct extends Activity {

   private NewRender glSurface;
   ArrayList<MediaPlayer> mp = new ArrayList<MediaPlayer>();


   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.glSurface = new NewRender(this);
      this.glSurface.start();
      this.setContentView(this.glSurface);
      this.setVolumeControlStream(3);
   }

   public void onDestroy() {
      super.onDestroy();
   }

   
   protected void onPause() {
      super.onPause();
      this.glSurface.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.glSurface.onResume();
   }

   protected void onStart() {
      super.onStart();
   }
}
