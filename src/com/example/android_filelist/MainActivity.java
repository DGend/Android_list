package com.example.android_filelist;

import java.io.File;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class MainActivity extends Activity {
	static int kill=0;
	String File_path="/sdcard/";
	FileList _FileList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		
		_FileList = new FileList(this);
		
        _FileList.setOnPathChangedListener(new OnPathChangedListener() {
			@Override
			public void onChanged(String path) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.FilePath)).setText("경로: "+path);
				File_path = path;
			}
		});
        
        _FileList.setOnFileSelected(new OnFileSelectedListener() {
			@Override
			public void onSelected(String path, String fileName) {
				// TODO Auto-generated method stub
				((TextView) findViewById(R.id.FilePath)).setText("경로: "+path+fileName);
			}
		});
        
        _FileList.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
        		int position, long id) {
        		// TODO Auto-generated method stub
        		
        		return true;
        	}
		});
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout01);
        layout.addView(_FileList);
        
        _FileList.setPath(File_path);
        _FileList.setFocusable(true);
        _FileList.setFocusableInTouchMode(true);
    }
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	public void _finish(){
    	moveTaskToBack(true);
    	finish();
    	android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public void Make_Folder(View v){
		LayoutInflater inflater = (LayoutInflater)getSystemService(this.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.make_folder, null);
		
		new AlertDialog.Builder(this)
		.setTitle(R.string.Make_Folder)
		.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener(){
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	EditText make_folder_name = (EditText) view.findViewById(R.id.make_folder_name);
		    	String folder_path = File_path + make_folder_name.getText().toString();
		    	File folder =new File(folder_path);
		    	folder.mkdirs();
		    	_FileList.setPath(File_path);
		    	
		        dialog.dismiss();
		    }
		})
		.setView(view)
		.setNegativeButton(R.string.Exit, null)
		.show();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  { // 뒤로가기 키를 눌렀을때
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
        	if(kill==0){
        		kill++;
        		Toast.makeText(this, "종료하시려면 한번더 누르세요", Toast.LENGTH_SHORT).show();
        	}else
        		_finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
