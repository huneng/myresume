package com.huneng.activity;

import java.util.List;

import com.huneng.data.WorkData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Work extends Activity {
	Button frontBtn, nextBtn;
	Button wnewBtn, wdeleteBtn;
	EditText companyEd, positionEd;
	EditText STimeEd, ETimeEd, scoreEd;
	List<WorkData> works;
	WorkData curWork;
	int curIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work);
		works = ResumeActivity.resume.myData.works;
		curIndex = 0;
		if (works.size() == 0) {
			curWork = new WorkData();
			works.add(curWork);
		} else
			curIndex = 0;
		frontBtn = (Button) findViewById(R.id.work_front);
		nextBtn = (Button) findViewById(R.id.work_next);
		wnewBtn = (Button) findViewById(R.id.work_new);
		wdeleteBtn = (Button) findViewById(R.id.work_delete);

		frontBtn.setOnClickListener(cl);
		nextBtn.setOnClickListener(cl);
		wnewBtn.setOnClickListener(cl);
		wdeleteBtn.setOnClickListener(cl);

		STimeEd = (EditText) findViewById(R.id.work_start);
		ETimeEd = (EditText) findViewById(R.id.work_end);
		scoreEd = (EditText) findViewById(R.id.work_score);
		companyEd = (EditText) findViewById(R.id.company_edit);
		positionEd = (EditText) findViewById(R.id.work_edit);
	}

	@Override
	protected void onResume() {
		if (curIndex >= works.size()) {
			curIndex = 0;
		}
		curWork = works.get(curIndex);
		initEdit(curWork);
		super.onResume();
	}
	
	protected void onPause() {
		saveData();
		super.onPause();
	}

	private OnClickListener cl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			saveData();
			switch (v.getId()) {
			case R.id.work_front:
				if (curIndex != 0)
					front();
				break;
			case R.id.work_next:
				if (curIndex != works.size())
					next();
				break;
			case R.id.work_new:
				newWork();
				break;
			case R.id.work_delete:
				delWork();
				break;

			}
			works.set(curIndex, curWork);
		}

	};

	private void initEdit(WorkData curWork) {
		positionEd.setText(curWork.workname);
		companyEd.setText(curWork.company);
		String str = "";
		if (curWork.begintime != 0)
			str = "" + curWork.begintime;
		STimeEd.setText(str);
		if (curWork.endtime != 0)
			str = "" + curWork.endtime;
		ETimeEd.setText(str);
		if (curWork.score != 0)
			str = "" + curWork.score;
		scoreEd.setText(str);
	}

	private void saveData() {
		String str = positionEd.getText().toString();
		curWork.workname = str;

		str = companyEd.getText().toString();
		curWork.company = str;

		str = STimeEd.getText().toString();
		if (str.equals(""))
			str = "0";
		curWork.begintime = Integer.parseInt(str);

		str = ETimeEd.getText().toString();
		if (str.equals(""))
			str = "0";
		curWork.endtime = Integer.parseInt(str);

		str = scoreEd.getText().toString();
		if (str.equals(""))
			str = "0";
		curWork.score = Integer.parseInt(str);

		works.set(curIndex, curWork);
	}

	protected void delWork() {
		works.remove(curIndex);
		curIndex = 0;
		if (works.size() == 0) {
			curWork = new WorkData();
			works.add(curWork);
		} else {
			curWork = works.get(0);
		}
		initEdit(curWork);
	}

	protected void newWork() {
		curIndex = works.size();
		curWork = new WorkData();
		works.add(curWork);
		initEdit(curWork);
	}

	protected void next() {
		curIndex++;
		curWork = works.get(curIndex);
		initEdit(curWork);
	}

	protected void front() {
		curIndex--;
		curWork = works.get(curIndex);
		initEdit(curWork);
	}
}