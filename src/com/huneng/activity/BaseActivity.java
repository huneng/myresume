package com.huneng.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class BaseActivity extends Activity{
	List<EditText> edits;
	List<String> texts;
	ImageView photos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);
		edits = new ArrayList<EditText>(7);
		
		EditText ed = (EditText )findViewById(R.id.name_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.phone_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.address_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.job1_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.holiday1_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.salary1_edit);
		edits.add(ed);
		ed = (EditText)findViewById(R.id.remark_edit);
		edits.add(ed);
		
		texts = new ArrayList<String>(9);
		for(int i = 0; i < 9; i++){
			texts.add("");
		}
		texts.set(2, "male");
	}

}
