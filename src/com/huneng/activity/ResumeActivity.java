package com.huneng.activity;

import org.json.JSONException;

import com.huneng.data.MyJson;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class ResumeActivity extends TabActivity {
	public static ResumeActivity resume;
	public MyJson myData;
	public Bitmap photo;
	public static int width, height;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// init window titlebar

		height = getWindowManager().getDefaultDisplay().getHeight();
		width = getWindowManager().getDefaultDisplay().getWidth();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		resume = this;
		myData = new MyJson();
		setContentView(R.layout.main);
		initTab();
		
		Button btn = (Button) findViewById(R.id.ok);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.open);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.save);
		btn.setOnClickListener(listener);
	}

	void initTab() {
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
				break;
			case R.id.open:
				break;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}