package com.huneng.resume;

import java.util.LinkedList;
import java.util.List;

import com.huneng.data.SkillData;
import com.huneng.paint.Arc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class SimpleResume3 extends View {
	List<Arc> arcs;
	float cx, cy, radius;
	Paint mPaint;
	int color[] = { Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY,
			Color.GREEN, Color.MAGENTA, Color.LTGRAY, Color.MAGENTA, Color.RED,
			Color.TRANSPARENT, Color.YELLOW };
	int colorIndex;

	public SimpleResume3(Context context) {
		super(context);
	}

	public SimpleResume3(Context context, List<SkillData> skills, int width,
			int height) {
		super(context);
		this.setLayoutParams(new LayoutParams(width, height));
		cx = width / 2;
		cy = height / 2;
		radius = cx - width/6;
		colorIndex = 0;
		mPaint = new Paint();
		colorChange();
		setArcAngle(skills);
	}

	void colorChange() {
		mPaint.setColor(color[colorIndex++]);
		colorIndex %= color.length;
	}

	void setArcAngle(List<SkillData> skills) {
		arcs = new LinkedList<Arc>();
		int size = skills.size();
		float sum = 0;
		for (int i = 0; i < size; i++) {
			sum += skills.get(i).average();
		}
		float p[] = new float[size];
		for (int i = 0; i < size; i++) {
			p[i] = skills.get(i).average() / sum;
		}
		float startAngle = 0, sweepAngle = 0;
		for (int i = 0; i < size; i++) {
			colorChange();
			sweepAngle = p[i] * 360;

			Arc arc = new Arc();
			arc.startAngle = startAngle;
			arc.sweepAngle = sweepAngle;
			arc.cx = cx;
			arc.cy = cy;
			arc.radius = radius;
			arc.paint = new Paint(mPaint);
			arc.text = skills.get(i).skillname;
			arcs.add(arc);
			startAngle += sweepAngle;
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for (int i = 0; i < arcs.size(); i++) {
			arcs.get(i).draw(canvas);
		}
		super.onDraw(canvas);
	}

}