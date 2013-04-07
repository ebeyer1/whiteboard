package com.beyer.whiteboard;

import android.graphics.Paint;
import android.graphics.Path;

public class DrawableObject {
	private Path mPath;
	private Paint mPaint;
	public DrawableObject()
	{
		mPath = new Path();
		mPaint = new Paint();
	}
	
	public DrawableObject(Path path, Paint paint)
	{
		mPath = path;
		mPaint = paint;
	}
	
	public Path getPath()
	{
		return mPath;
	}
	
	public void setPath(Path path)
	{
		mPath = path;
	}
	
	public Paint getPaint()
	{
		return mPaint;
	}
	
	public void setPaint(Paint paint)
	{
		mPaint = paint;
	}
	
}
