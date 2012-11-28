package com.huneng.activity;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huneng.data.MyJson;
import com.huneng.net.HttpWork;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResumeList extends ListActivity {
	public HttpWork hw;
	List<String> id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hw = new HttpWork();
		hw.site = ResumeActivity.resume.site;
		hw.usr = ResumeActivity.resume.usrname;
		hw.pw = ResumeActivity.resume.pwd;
		try {
			init();
		} catch (JSONException e) {
		}
	}

	private void init() throws JSONException {
		String str = hw.submit();
		JSONArray array = new JSONArray(str);
		int len = array.length();
		id = new LinkedList<String>();
		for (int i = 0; i < len; i++) {
			id.add("" + array.getInt(i));
		}
		id.add("new resume");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, id);

		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String str = this.id.get(position);
		int t;
		if (str.equals("new resume")) {
			MyJson json = new MyJson();
			try {
				t = hw.pushResume(json.changToJsonData());
			} catch (JSONException e) {
				t = -1;
			}
			if (t != -1) {
				ResumeActivity.resume.myData = json;
				ResumeActivity.resume.myData.id = t;
			}
		} else {
			t = Integer.parseInt(str);

			try {
				ResumeActivity.resume.myData = new MyJson(new JSONObject(
						hw.getResume(t)));
				ResumeActivity.resume.myData.id = t;
			} catch (JSONException e) {
			}
			
		}
		this.finish();
		super.onListItemClick(l, v, position, id);
	}

}
