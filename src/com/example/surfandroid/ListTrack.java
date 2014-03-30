package com.example.surfandroid;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi") public class ListTrack extends ListActivity 
{
  MediaPlayer mp;
  Cursor cursor;
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
      super.onCreate(savedInstanceState);
      String[] from = {MediaStore.MediaColumns.TITLE};
      int[] to = {android.R.id.text1};
      
      String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
      String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("wav"); 
      String[] selectionArgsMp3 = new String[]{ mimeType };
   
      ContentResolver cr = getContentResolver();
      
      cursor = cr.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        null,
        selectionMimeType,
        selectionArgsMp3,
        MediaStore.Audio.Media.TITLE);
      
      ListAdapter adapter = new SimpleCursorAdapter(this,
      android.R.layout.simple_list_item_1, cursor, from, to);
      setListAdapter(adapter);
      
      //adapter.
  }
  
  protected void onListItemClick(ListView l, View v, int position, long id) 
  {
      super.onListItemClick(l, v, position, id);
      // Получение элемента, который был нажат
      cursor.moveToPosition(position);
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
      WorkFile workFile = new WorkFile();
      workFile.readFile();
      workFile.path = cursor.getString(column_index);
      workFile.writeFile();
      this.finish();
  }
}
