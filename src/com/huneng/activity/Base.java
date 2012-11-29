package com.huneng.activity;

import java.util.Calendar;

import com.huneng.data.BaseData;
import com.huneng.ui.TimeDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Base extends Activity {
	private EditText name_ed, phone_ed, addr_ed;
	private EditText job_ed, holi_ed, salary_ed;
	private EditText remark_ed;
	private Button btn, getPhoneBtn, timeBtn, sexBtn;
	private BaseData basedata;
	private String remark;
	private ImageView image;
	private ResumeActivity parent;
	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base);

		name_ed = (EditText) findViewById(R.id.name_edit);
		phone_ed = (EditText) findViewById(R.id.phone_edit);
		addr_ed = (EditText) findViewById(R.id.address_edit);
		job_ed = (EditText) findViewById(R.id.job1_edit);
		holi_ed = (EditText) findViewById(R.id.holiday1_edit);
		salary_ed = (EditText) findViewById(R.id.salary1_edit);
		remark_ed = (EditText) findViewById(R.id.remark_edit);

		btn = (Button) findViewById(R.id.age_btn);
		timeBtn = (Button) findViewById(R.id.start_end_btn);
		getPhoneBtn = (Button) findViewById(R.id.get_tel);
		sexBtn = (Button) findViewById(R.id.sex_btn);
		image = (ImageView) findViewById(R.id.photo_view);

		sexBtn.setOnClickListener(l);
		btn.setOnClickListener(l);
		getPhoneBtn.setOnClickListener(l);
		timeBtn.setOnClickListener(l);
		image.setOnClickListener(l);

		parent = ResumeActivity.resume;

	}

	@Override
	protected void onResume() {
		remark = ResumeActivity.resume.myData.getRemark();
		basedata = ResumeActivity.resume.myData.basedata;
		init();
		super.onResume();
	}

	@Override
	protected void onPause() {
		String str = timeBtn.getText().toString();
		int time[] = getNumber(str);
		basedata.starttime = time[0];
		basedata.endtime = time[1];
		saveData();
		super.onPause();
	}

	OnClickListener l = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.age_btn:
				Calendar calendar = Calendar.getInstance();
				new DatePickerDialog(Base.this, listener,
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.sex_btn:
				if (basedata.sex.equals("male"))
					basedata.sex = "female";
				else
					basedata.sex = "male";
				sexBtn.setText(basedata.sex);
				break;
			case R.id.get_tel:
				TelephonyManager tm = (TelephonyManager) Base.this
						.getSystemService(Context.TELEPHONY_SERVICE);
				String str = "";
				str = tm.getLine1Number();
				if (str == null || str.length() == 0) {
					phone_ed.setText("");
					Toast.makeText(getApplicationContext(), "Can't get",
							Toast.LENGTH_SHORT).show();
					break;
				}
				phone_ed.setText(str);
				break;
			case R.id.start_end_btn:
				new TimeDialog(Base.this, timeBtn).show();
				break;
			case R.id.photo_view:
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
				break;
			}
		}
	};

	void saveData() {
		basedata.name = name_ed.getText().toString();
		basedata.phone = phone_ed.getText().toString();
		basedata.address = addr_ed.getText().toString();
		basedata.job = job_ed.getText().toString();
		basedata.holiday = holi_ed.getText().toString();
		basedata.salary = salary_ed.getText().toString();
		remark = remark_ed.getText().toString();
		ResumeActivity.resume.myData.setRemark(remark);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			parent.photo = BitmapFactory.decodeFile(picturePath);
			ResumeActivity.resume.myData.picturePath = picturePath;
			parent.photo = Bitmap.createScaledBitmap(parent.photo,
					image.getHeight(), image.getHeight(), true);
			image.setImageBitmap(parent.photo);
		}

	}

	OnDateSetListener listener = new OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String str = "" + year + '-' + monthOfYear + '-' + dayOfMonth;
			btn.setText(str);
			basedata.birth = str;
		}

	};

	private void init() {
		if (parent.photo != null)
			image.setImageBitmap(parent.photo);
		name_ed.setText(basedata.name);
		sexBtn.setText(basedata.sex);
		phone_ed.setText(basedata.phone);
		addr_ed.setText(basedata.address);
		String str = "" + basedata.starttime + "-" + basedata.endtime;
		timeBtn.setText(str);
		btn.setText(basedata.birth);
		job_ed.setText(basedata.job);
		holi_ed.setText(basedata.holiday);
		salary_ed.setText(basedata.salary);
		remark_ed.setText(remark);
	}

	int[] getNumber(String str) {
		int time[] = { 0, 0 };
		String t1, t2;
		try {
			t1 = str.substring(0, str.indexOf('-'));
			t2 = str.substring(str.indexOf('-') + 1);
		} catch (Exception e) {
			t1 = t2 = null;
		}
		if (t1 == null || t2 == null)
			return time;
		time[0] = Integer.parseInt(t1);
		time[1] = Integer.parseInt(t2);
		return time;
	}
}
