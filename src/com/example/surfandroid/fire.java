package com.example.surfandroid;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class fire {
	
	int count = 10;
    
    ArrayList<line_cool> mapFire = new ArrayList<line_cool>();
    
	public fire(float var2, float var3, float var4, float[] col)
	{
		Random rdn = new Random();
		float tx = (rdn.nextInt(2) - 0.5f) * 0.8f;
		float ty = rdn.nextInt(5)/10.0f;
		float tz = -1.0f;
		for(int i = 0; i < count; i++)
		{
			line_cool tmp = new line_cool();
			tmp.setVertexF(true);
			tmp.xyz.x = -rdn.nextInt(30)/100.0f + 0.15f + tx;
			tmp.xyz.y = -rdn.nextInt(10)/100.0f + 0.05f + ty;
			tmp.xyz.z = tz + 0.01f;
			
			tmp.disFromC.x = (tmp.xyz.x - tx) / 3.0f;
			tmp.disFromC.y = (tmp.xyz.y - ty) / 3.0f;
			
			tmp.setColor(col[0], col[1], col[2], col[3]);
			mapFire.add(tmp);
			
			line_cool tmp1 = new line_cool();
			tmp1.setVertexF(false);
			tmp1.xyz.x = tmp.xyz.x;
			tmp1.xyz.y = tmp.xyz.y;
			tmp1.xyz.z = tz + 0.01f;
			
			tmp1.disFromC.x = (tmp1.xyz.x - tx) / 3.0f;
			tmp1.disFromC.y = (tmp1.xyz.y - ty) / 3.0f;
			
			tmp1.setColor(col[0], col[1], col[2], col[3]);
			mapFire.add(tmp1);
		}
	}
	
	public void draw(GL10 var1)
	{
		for(int i = 0; i < mapFire.size(); i++)
		{
			var1.glLoadIdentity();
            var1.glTranslatef(mapFire.get(i).xyz.x, mapFire.get(i).xyz.y, mapFire.get(i).xyz.z);
            
            mapFire.get(i).xyz.x += mapFire.get(i).disFromC.x; 
            mapFire.get(i).xyz.y += mapFire.get(i).disFromC.y;
            
            mapFire.get(i).disFromC.x /= 1.2f;
            mapFire.get(i).disFromC.y /= 1.2f;
            
            var1.glScalef(0.025F, 0.025F, 0.025F);
			mapFire.get(i).drawTer(var1);
			if(mapFire.get(i).color[3] > 0)
			{
				mapFire.get(i).color[3] -= 0.02f;
				if(mapFire.get(i).color[0] < 0.9f) mapFire.get(i).color[0] += 0.9f;
				else if(mapFire.get(i).color[0] < 1.0f) mapFire.get(i).color[0] -= 0.9f;
				
				if(mapFire.get(i).color[1] < 0.9f) mapFire.get(i).color[1] += 0.9f;
				else if(mapFire.get(i).color[1] < 1.0f) mapFire.get(i).color[1] -= 0.9f;
				
				if(mapFire.get(i).color[2] < 0.9f) mapFire.get(i).color[2] += 0.9f;
				else if(mapFire.get(i).color[2] < 1.0f) mapFire.get(i).color[2] -= 0.9f;
			}
			mapFire.get(i).setColor(mapFire.get(i).color[0], mapFire.get(i).color[1], mapFire.get(i).color[2], mapFire.get(i).color[3]);
		}
	}

	
}
