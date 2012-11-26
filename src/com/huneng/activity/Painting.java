package com.huneng.activity;

import com.huneng.data.MyJson;
import com.huneng.ui.SimpleResume;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Painting extends Activity {
	MyJson data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.paint);
		data = ResumeActivity.resume.myData;
		SimpleResume mv = new SimpleResume(this, ResumeActivity.width, 1300,
				data);
		LinearLayout layout = (LinearLayout) findViewById(R.id.paint_layout);
		layout.addView(mv);
	}
}