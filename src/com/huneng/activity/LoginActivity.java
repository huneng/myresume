package com.huneng.activity;

import com.huneng.net.HttpWork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	EditText pwdEd, usrEd;
	HttpWork hw;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		usrEd = (EditText) findViewById(R.id.et_login);
		pwdEd = (EditText) findViewById(R.id.et_password);
		TextView tv = (TextView) findViewById(R.id.submit);
		Button btn = (Button)findViewById(R.id.login_btn);
		btn.setOnClickListener(listener);
		tv.setText(Html
				.fromHtml("<a href=\"http://192.168.1.103：8080/regisit\">注册>></a>"));
		hw = new HttpWork();
		hw.site= ResumeActivity.resume.site;
		
	}
	
	OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			hw.usr = usrEd.getText().toString();
			hw.pw = pwdEd.getText().toString();
			
			String str = hw.submit();
			if(!str.equals("error")){
				ResumeActivity.resume.usrname = hw.usr;
				ResumeActivity.resume.pwd = hw.pw;
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, ResumeList.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		}
	};

}