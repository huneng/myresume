package com.huneng.data;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJson {
	public List<SkillData> skills;
	public List<WorkData> works;
	public BaseData basedata;
	public List<String> remarks;
	public String usr;
	public int id;
	public String picturePath;
	public int starttime, endtime;

	public MyJson() {
		// id = -1;
		// usr = "";
		// basedata = new BaseData();
		// skills = new LinkedList<SkillData>();
		// works = new LinkedList<WorkData>();
		// remarks = new LinkedList<String>();
		// picturePath = "";
		sampleData();
	}

	void sampleData() {
		id = -1;
		usr = "huneng";
		basedata = new BaseData();
		basedata.name = "张三";
		basedata.birth = "1991-6-17";
		basedata.phone = "18768122382";
		basedata.address = "浙江杭州";
		basedata.job = "程序员";
		basedata.holiday = "3";
		basedata.salary = "9000员";
		basedata.sex = "male";
		basedata.starttime = 2010;
		basedata.endtime = 2016;

		skills = new LinkedList<SkillData>();
		SkillData skill = new SkillData();
		skill.skillname = "c";
		skill.setScore("4 7 9 9");
		skill.starttime = 2010;
		skills.add(skill);
		skill = new SkillData();
		skill.skillname = "c++";
		skill.setScore("3 5 8 6");
		skill.starttime = 2011;
		skills.add(skill);
		skill = new SkillData();
		skill.skillname = "database";
		skill.setScore("6 4 8 9");
		skill.starttime = 2010;
		skills.add(skill);
		skill = new SkillData();
		skill.skillname = "datastructure";
		skill.setScore("6 4 8");
		skill.starttime = 2012;
		skills.add(skill);
		skill = new SkillData();
		skill.skillname = "internet";
		skill.setScore("6 5 9");
		skill.starttime = 2012;
		skills.add(skill);

		works = new LinkedList<WorkData>();

		WorkData work = new WorkData();
		work.company = "IBM";
		work.workname = "程序员";
		work.begintime = 201009;
		work.endtime = 201101;
		work.score = 9;
		works.add(work);
		work = new WorkData();
		work.company = "微软";
		work.workname = "网络";
		work.begintime = 201105;
		work.endtime = 201201;
		work.score = 10;
		works.add(work);
		work = new WorkData();
		work.company = "谷歌";
		work.workname = "网络";
		work.begintime = 201205;
		work.endtime = 201209;
		work.score = 10;
		works.add(work);

		remarks = new LinkedList<String>();
		remarks.add("希望获得更多的机会");
		remarks.add("能够自由的开发");
		remarks.add("工作方式自由");

		picturePath = "";
	}

	public void set_id_usr(int id, String usr) {
		this.id = id;
		this.usr = usr;
	}

	public void setRemark(String str) {
		remarks.clear();
		int length = str.length();
		int index = 0;
		String t = "";
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == ';') {
				t = str.substring(index, i);
				remarks.add(t);
				index = i + 1;
				t = "";
			}
		}
		if (!t.equals(""))
			remarks.add(t);
	}

	public String getRemark() {
		StringBuffer str = new StringBuffer(100);
		str.append("");
		int size = remarks.size();
		for (int i = 0; i < size; i++) {
			str.append(remarks.get(i) + ';');
		}
		return new String(str);
	}

	public MyJson(JSONObject object) throws JSONException {

		skills = new LinkedList<SkillData>();
		works = new LinkedList<WorkData>();
		remarks = new LinkedList<String>();
		basedata = new BaseData();
		id = object.getInt("id");
		usr = object.getString("username");

		JSONObject base = object.getJSONObject("fundamental");
		basedata.name = base.getString("people");
		basedata.sex = base.getString("sex");
		basedata.birth = base.getString("birth");
		basedata.phone = base.getString("phone");

		basedata.address = base.getString("address");
		basedata.starttime = base.getInt("starttime");
		basedata.endtime = base.getInt("endtime");
		JSONObject want = object.getJSONObject("wanted");
		basedata.job = want.getString("job");
		basedata.salary = want.getString("salary");
		basedata.holiday = want.getString("holiday");

		JSONArray s = object.getJSONArray("studys");
		int size = s.length();
		for (int i = 0; i < size; i++) {
			JSONObject o = s.getJSONObject(i);
			skills.add(new SkillData(o));
		}

		JSONArray w = object.getJSONArray("works");
		size = w.length();
		for (int i = 0; i < size; i++) {
			JSONObject o = w.getJSONObject(i);
			works.add(new WorkData(o));
		}
		JSONArray remark = object.getJSONArray("remark");
		size = remark.length();
		for (int i = 0; i < size; i++) {
			String str = remark.getString(i);
			remarks.add(str);
		}

	}

	public String changToJsonData() throws JSONException {
		JSONObject resume = new JSONObject();
		JSONObject base = new JSONObject();
		base.put("people", basedata.name);
		base.put("sex", basedata.sex);
		base.put("birth", basedata.birth);
		base.put("phone", basedata.phone);
		base.put("address", basedata.address);
		base.put("starttime", basedata.starttime);
		base.put("endtime", basedata.endtime);
		JSONObject want = new JSONObject();
		want.put("job", basedata.job);
		want.put("salary", basedata.salary);
		want.put("holiday", basedata.holiday);
		resume.put("fundamental", base);
		resume.put("wanted", want);

		JSONArray s = new JSONArray();
		int size = skills.size();
		for (int i = 0; i < size; i++) {
			if (skills.get(i).check())
				s.put(skills.get(i).changeToJson());
			else {
				skills.remove(i);
			}
		}
		size = works.size();
		JSONArray w = new JSONArray();
		for (int i = 0; i < size; i++) {
			if (works.get(i).check())
				w.put(works.get(i).changeToJson());
			else
				works.remove(i);
		}

		resume.put("id", id);
		resume.put("username", usr);
		resume.put("studys", s);
		resume.put("works", w);

		JSONArray r = new JSONArray();
		size = remarks.size();
		for (int i = 0; i < size; i++) {
			r.put(remarks.get(i));

		}
		resume.put("remark", r);
		return resume.toString();
	}

}
