package com.example.surfandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	Button recordB;
	Button startB;
	Button reName;
	Button listTrack;
	TextView textW;
	
	WorkFile workFile = new WorkFile();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);
        this.startB = ((Button)findViewById(R.id.But1));
        this.startB.setOnClickListener(this);
        this.recordB = ((Button)findViewById(R.id.But2));
        this.recordB.setOnClickListener(this);
        this.reName = ((Button)findViewById(R.id.But4));
        this.reName.setOnClickListener(this);
        this.listTrack = ((Button)findViewById(R.id.But3));
        this.listTrack.setOnClickListener(this);
        
        this.textW = ((TextView)findViewById(R.id.Tex1));
        //WorkFile genFile = new WorkFile();
        //genFile.readFile();
	   	//genFile.readWave();
        //this.textW.setText(genFile.wav.get(0).toString());
    }
    
    protected void onStart() 
    {
        super.onStart(); 
        workFile.readFile();
        textW.setText("Добро пожаловать, " + workFile.nickName + "!\nВыберете аудио файл и начните игру!" );
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View paramView) 
	{
		// TODO Auto-generated method stub
		switch (paramView.getId())
	    {
		    default: 
		      return;
		    case R.id.But1: 
		    	startActivity(new Intent(this, GraphAct.class));
		    break;
		    case R.id.But2:
		    	startActivity(new Intent(this, record.class));
		    break;
		    case R.id.But3:
		    	startActivity(new Intent(this, ListTrack.class));
		    break;
		    case R.id.But4:
		    	startActivity(new Intent(this, reName.class));
		    break;
	    }
	}
    
}
