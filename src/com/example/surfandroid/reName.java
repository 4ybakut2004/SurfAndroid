package com.example.surfandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class reName extends Activity implements OnClickListener
{
	EditText editText;
	WorkFile workFile = new WorkFile();
	Button reName;
	TextView textW;
	
  	protected void onCreate(Bundle paramBundle)
  	{
	    super.onCreate(paramBundle);
	    setContentView(R.layout.rename);
	    
	    this.textW = ((TextView)findViewById(R.id.Tex3));
	    textW.setText("¬ведите новый ник:");
	    
	    this.reName = ((Button)findViewById(R.id.But5));
        this.reName.setOnClickListener(this);
        
	    workFile.readFile();
	    editText = (EditText)findViewById(R.id.Edit1);
	    editText.setText(workFile.nickName);
  	}

  	@Override
	public void onClick(View arg0) 
	{
  		// TODO Auto-generated method stub
  		switch (arg0.getId())
	    {
		    default: 
		      return;
		    case R.id.But5: 
		    	workFile.nickName = editText.getText().toString();
		    	workFile.writeFile();
		    	this.finish();
		    break;
	    }
	}
}