package com.huneng.activity;

import java.util.List;

import com.huneng.data.SkillData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Skill extends Activity {
	public List<SkillData> skills;
	SkillData curSkill;
	EditText snameEd, sstartEd, sscoresEd;
	Button frontBtn, nextBtn;
	Button sAddBtn, sDeleteBtn;
	int curIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skill);
		skills = ResumeActivity.resume.myData.skills;
		if (skills.size() == 0) {
			curSkill = new SkillData();
			skills.add(curSkill);
		} else
			curSkill = skills.get(0);
		curIndex = 0;

		frontBtn = (Button) findViewById(R.id.skill_front);
		nextBtn = (Button) findViewById(R.id.skill_next);
		sAddBtn = (Button) findViewById(R.id.skill_new);
		sDeleteBtn = (Button) findViewById(R.id.skill_delete);
		frontBtn.setOnClickListener(cl);
		nextBtn.setOnClickListener(cl);
		sAddBtn.setOnClickListener(cl);
		sDeleteBtn.setOnClickListener(cl);

		snameEd = (EditText) findViewById(R.id.skill_edit);
		sstartEd = (EditText) findViewById(R.id.skill_start_edit);
		sscoresEd = (EditText) findViewById(R.id.skill_score_edit);
	}

	@Override
	protected void onResume() {
		skills = ResumeActivity.resume.myData.skills;
		if (curIndex >= skills.size()) {
			curIndex = 0;
		}
		curSkill = skills.get(curIndex);
		initEdit(curSkill);
		super.onResume();
	}

	@Override 
	protected void onPause() {
		saveData();
		super.onPause();
	}
	private OnClickListener cl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			saveData();
			switch (v.getId()) {
			case R.id.skill_delete:
				delSkill();
				break;
			case R.id.skill_new:
				newSkill();
				break;
			case R.id.skill_front:
				if (curIndex > 0)
					front();
				break;
			case R.id.skill_next:
				if (curIndex < skills.size() - 1)
					next();
				break;
			}
		}

	};

	private void saveData() {
		String str;
		str = snameEd.getText().toString();
		curSkill.skillname = str;

		str = sstartEd.getText().toString();
		if (str.equals(""))
			str = "0";
		curSkill.starttime = Integer.parseInt(str);

		str = sscoresEd.getText().toString();
		if (str.equals(""))
			str = "0";
		curSkill.setScore(str);

		skills.set(curIndex, curSkill);
	}

	private void initEdit(SkillData curSkill) {
		snameEd.setText(curSkill.skillname);
		
		String str = "";
		if (curSkill.starttime != 0)
			str = "" + curSkill.starttime;
		sstartEd.setText(str);
		
		str = "";
		for (int i = 0; i < curSkill.length; i++) {
			str += curSkill.scores[i] + " ";
		}
		sscoresEd.setText(str);
	}

	protected void next() {
		curIndex++;
		curSkill = skills.get(curIndex);
		initEdit(curSkill);
	}

	protected void front() {
		curIndex--;
		curSkill = skills.get(curIndex);
		initEdit(curSkill);
	}

	protected void newSkill() {
		curIndex = skills.size();
		curSkill = new SkillData();
		initEdit(curSkill);
		skills.add(curSkill);
	}

	protected void delSkill() {
		skills.remove(curIndex);
		curIndex = 0;
		if (skills.size() == 0) {
			curSkill = new SkillData();
			initEdit(curSkill);
			return;
		}
		curSkill = skills.get(0);
		initEdit(curSkill);
	}

}