package com.example.surfandroid;

import javax.microedition.khronos.opengles.GL10;

public class loadingClass {
	float angle = 0;
	bonus loadBonus = new bonus(0.0f, 0.0f, 0.8f);
	public loadingClass(){
		
	}
	public void draw(GL10 gl){
		gl.glRotatef(angle, 0, 1, 0);
		angle+=1.0;
		loadBonus.draw(gl);
	}
}
