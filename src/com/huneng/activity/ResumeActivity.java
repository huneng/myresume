package com.huneng.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.huneng.data.MyJson;
import com.huneng.net.HttpWork;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class ResumeActivity extends TabActivity {
	private static final int GET_LOCAL_FILE = 0;

	public static ResumeActivity resume;
	public MyJson myData;
	public Bitmap photo;
	public static int width, height;
	public String usrname, pwd;
	public String site = "http://192.168.1.103:8080";
	private FileDealtor filedealtor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		height = display.getHeight();
		width = display.getWidth();
		Window win = getWindow();
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		win.requestFeature(Window.FEATURE_NO_TITLE);

		usrname = pwd = "";
		resume = this;
		myData = new MyJson();
		filedealtor = new FileDealtor("/mnt/sdcard/ImageResume");

		setContentView(R.layout.main);
		initTab();
		initButton();
	}

	private void initButton() {
		Button btn = (Button) findViewById(R.id.ok);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.open);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.save);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.net);
		btn.setOnClickListener(listener);
	}

	private void initTab() {
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent = new Intent().setClass(ResumeActivity.this, Base.class);
		spec = tabHost.newTabSpec("base").setIndicator("Base")
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(ResumeActivity.this, Skill.class);
		spec = tabHost.newTabSpec("skill").setIndicator("Skill")
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(ResumeActivity.this, Work.class);
		spec = tabHost.newTabSpec("work").setIndicator("Work")
				.setContent(intent);
		tabHost.addTab(spec);
		getTabHost().setCurrentTab(0);

	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ok:
				getTabHost().setCurrentTab(0);
				Intent intent = new Intent();
				intent.setClass(ResumeActivity.this, Painting.class);
				startActivity(intent);
				break;
			case R.id.save:
				String data = "";
				try {
					data = myData.changToJsonData();
				} catch (JSONException e) {
				}
				if (!(myData.id == -1 || usrname.equals("") || pwd.equals(""))) {
					HttpWork hw = new HttpWork();
					hw.usr = usrname;
					hw.pw = pwd;
					hw.site = site;
					hw.updateResume(data);
				}
				try {
					filedealtor.writeToFile(data);
				} catch (IOException e) {
				}
				break;
			case R.id.open:
				Intent intent2 = new Intent();
				intent2.setClass(ResumeActivity.this, FileManager.class);
				startActivityForResult(intent2, GET_LOCAL_FILE);
				break;
			case R.id.net:
				Intent intent4;
				if (usrname.equals("") || pwd.equals("")) {
					intent4 = new Intent();
					intent4.setClass(ResumeActivity.this, LoginActivity.class);
					startActivity(intent4);
				} else {
					intent4 = new Intent();
					intent4.setClass(ResumeActivity.this, ResumeList.class);
					startActivity(intent4);
				}
				break;
			}
		}

	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GET_LOCAL_FILE) {
			String fileName = data.getStringExtra("resume_file");
			if (fileName.equals("/mnt/sdcard"))
				return;
			String str = "";
			try {
				str = filedealtor.readFromFile(fileName);
			} catch (IOException e) {
			}
			try {
				myData = new MyJson(new JSONObject(str));
			} catch (JSONException e) {
			}
			filedealtor.reset(fileName);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}