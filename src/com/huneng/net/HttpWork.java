package com.huneng.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class HttpWork {
	public String site;
	public String usr, pw;

	public HttpWork() {
		site = "";
	}

	public HttpWork(String s) {
		site = s;
	}

	public String submit() {
		String url = site + "/android/login/" + usr + '/' + pw;
		String r = "";
		try {
			r = contactWithServer(url);
		} catch (IOException e) {
			r = "error";
		}
		return r;

	}

	public int synchronous(String data, int id) {
		int t = 0;
		if (id == -1) {
			t = this.pushResume(data);
		} else {
			t = this.updateResume(data);

		}
		return t;
	}

	public String getResume(int id) {
		String result = "";
		String url = site + "/android/getresume/" + id;
		try {
			result = contactWithServer(url);
		} catch (IOException e) {
			result = "error";
		}
		if (result.equals("error"))
			return "Can't get!";
		try {
			result = URLDecoder.decode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int pushResume(String data) {
		try {
			data = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
		}
		String myurl = site + "/android/pushresume/" + data;

		String r = "";
		try {
			r = contactWithServer(myurl);
		} catch (IOException e) {
			r = "error";
		}
		int t;
		if (r.equals("error")) {
			t = -1;
		} else {
			t = Integer.parseInt(r);
		}
		return t;
	}

	public int updateResume(String data) {
		try {
			data = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
		}
		String myurl = site + "/android/update/" + data;

		String r = "";
		try {
			r = contactWithServer(myurl);
		} catch (IOException e) {
			r = "error";
		}
		int t;
		if (r.equals("error")) {
			t = -1;
		} else {
			t = 0;
		}
		return t;
	}

	@SuppressLint("NewApi")
	private String contactWithServer(String myUrl) throws IOException {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		String result = "";
		URL url = new URL(myUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		InputStream in = con.getInputStream();
		StringBuffer buffer = new StringBuffer();
		int length = con.getContentLength();
		for (int i = 0; i < length; i++) {
			buffer.append((char) in.read());
		}
		result = new String(buffer);
		in.close();
		con.disconnect();
		return result;
	}

}