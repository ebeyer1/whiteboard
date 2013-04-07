package com.beyer.whiteboard;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	private static final float STROKE_WIDTH = 6f;
	private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
	private Canvas mCanvas;
	private Path mPath;
	public Paint mPaint;
	ArrayList<DrawableObject> drawableObjects = new ArrayList<DrawableObject>();
	private ArrayList<DrawableObject> undoneDrawableObjects = new ArrayList<DrawableObject>();
	
    public DrawView(Context context) {
        super(context);
        clearDrawing();
        setupPaint();
    }
    
    public DrawView(Context context, AttributeSet attrs)
    {
    	super(context, attrs);
    	clearDrawing();
    	setupPaint();
    }
    
    public void clearDrawing() 
    {
    	drawableObjects.clear();
	    undoneDrawableObjects.clear();
	    invalidate();
    }
    
	private void setupPaint() {
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(Color.BLUE);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(STROKE_WIDTH);
	
	    mCanvas = new Canvas();
	    mPath = new Path();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    //canvas.drawColor(mPaint.getColor()); // background color
	
	    for (DrawableObject p : drawableObjects) {	
	        canvas.drawPath(p.getPath(), p.getPaint());
	    }
	    // TODO look here
	    canvas.drawPath(mPath, mPaint);	
	}
	
	public void setColor (int color)
	{
		mPaint.setColor(color);
	}
	
	
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	
	private void touch_start(float x, float y) {
	
	    mPaint.setColor(mPaint.getColor());
	    undoneDrawableObjects.clear();
	    mPath.reset();
	    mPath.moveTo(x, y);
	    mX = x;
	    mY = y;
	
	    Log.e("", "pathsize:::" + drawableObjects.size());
	    Log.e("", "undonepathsize:::" + undoneDrawableObjects.size());
	}
	
	private void touch_move(float x, float y) {
	    float dx = Math.abs(x - mX);
	    float dy = Math.abs(y - mY);
	    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
	        mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
	        mX = x;
	        mY = y;
	    }
	}
	
	private void touch_up() {
	    mPath.lineTo(mX, mY);
	    // commit the path to our offscreen
	    mCanvas.drawPath(mPath, mPaint);
	    // kill this so we don't double draw
	    Paint newPaint = new Paint(mPaint);
	    drawableObjects.add(new DrawableObject(mPath, newPaint));
	    mPath = new Path();
	
	    Log.e("", "pathsize:::" + drawableObjects.size());
	    Log.e("", "undonepathsize:::" + undoneDrawableObjects.size());
	}
	
	public void onClickUndo() {
	
	    Log.e("", "pathsize:::" + drawableObjects.size());
	    Log.e("", "undonepathsize:::" + undoneDrawableObjects.size());
	    if (drawableObjects.size() > 0) {
	    	undoneDrawableObjects.add(drawableObjects.remove(drawableObjects.size() - 1));
	        invalidate();
	    } else {
	
	    }
	    // toast the user
	}
	
	public void onClickRedo() {
	
	    Log.e("", "pathsize:::" + drawableObjects.size());
	    Log.e("", "undonepathsize:::" + undoneDrawableObjects.size());
	    if (undoneDrawableObjects.size() > 0) {
	    	drawableObjects.add(undoneDrawableObjects.remove(undoneDrawableObjects.size() - 1));
	        invalidate();
	    } else {
	
	    }
	    // toast the user
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    float x = event.getX();
	    float y = event.getY();
	
	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
	        touch_start(x, y);
	        return true;
	    case MotionEvent.ACTION_MOVE:
	        touch_move(x, y);
	        break;
	    case MotionEvent.ACTION_UP:
	        touch_up();
	        break;
	    default:
	    	return false;
	    }
	    invalidate();
	    return true;
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}