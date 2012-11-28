package com.huneng.activity;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

public class FileDealtor {
	public String fileName;
	public String path;

	public FileDealtor(String path) {
		fileName = "";
		this.path = path;
		makeMissingDir(this.path);
	}

	public FileDealtor(String path, String name) {
		this.path = path;
		this.fileName = name;
		makeMissingDir(this.path);
	}

	public void makeMissingDir(String path) {
		File file = new File(path);
		file.mkdirs();
	}
	void reset(String fileName){
		File file = new File(fileName);
		this.fileName = file.getName();
		path = file.getPath();
	}

	@SuppressWarnings("resource")
	public void writeToFile(String data) throws IOException {
		if (fileName.equals(""))
			fileName = productName();
		File file = new File(path,fileName);
		
		FileOutputStream os = new FileOutputStream(file);
		OutputStreamWriter outWriter = new OutputStreamWriter(os);
		outWriter.write(data);
		outWriter.flush();
	}

	@SuppressWarnings("resource")
	public String readFromFile(String fileName) throws IOException {
		File file = new File(fileName);
		FileInputStream os = new FileInputStream(file);
		InputStreamReader inReader = new InputStreamReader(os);
		char[] buf = new char[50000];
		inReader.read(buf);
		String str = new String(buf);
		return str;
	}

	@SuppressLint("SimpleDateFormat")
	private String productName() {
		String filename = "";
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		filename = "resume" + date + ".txt";
		return filename;
	}
}
