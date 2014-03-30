package com.example.surfandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class record extends Activity
{
  TextView textV;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.layout_record);
    this.textV = ((TextView)findViewById(R.id.Text1));
    WorkFile fileRecord = new WorkFile();
    fileRecord.readFile();
    String srt = "";
    for(int i = 0; i < 5; i++)
    {
    	srt += " " + (i + 1) + ". " + fileRecord.nameCool.get(i).name + " " + fileRecord.nameCool.get(i).cool + "\n";
    }
    textV.setText(srt);
  }
}
