package com.huneng.activity;

import com.huneng.data.MyJson;
import com.huneng.resume.SimpleResume;
import com.huneng.resume.SimpleResume2;
import com.huneng.resume.SimpleResume3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class Painting extends Activity {
	MyJson data;
	LinearLayout layout;
	SimpleResume resume1;
	SimpleResume2 resume2;
	SimpleResume3 resume3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		this.setContentView(R.layout.paint);

		data = ResumeActivity.resume.myData;
		resume1 = new SimpleResume(this, ResumeActivity.width, 1300, data);
		resume2 = new SimpleResume2(this, ResumeActivity.width,
				ResumeActivity.height, data);
		resume3 = new SimpleResume3(this, ResumeActivity.resume.myData.skills,
				ResumeActivity.width, ResumeActivity.height);

		layout = (LinearLayout) findViewById(R.id.paint_layout);
		layout.addView(resume1);

		Button btn = (Button) findViewById(R.id.resume1);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.resume2);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.resume3);
		btn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.resume1:
				layout.removeAllViews();

				layout.addView(resume1);
				break;
			case R.id.resume2:
				layout.removeAllViews();
				layout.addView(resume2);
				break;
			case R.id.resume3:
				layout.removeAllViews();
				layout.addView(resume3);
				break;
			}
		}

	};

	void setFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

	}
}