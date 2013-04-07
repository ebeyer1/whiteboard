package com.beyer.whiteboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	private static final float STROKE_WIDTH = 6f;
	private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
	private Paint paint = new Paint();
	
	private Path path = new Path();
	
	private float lastTouchX;
	private float lastTouchY;
	private final RectF dirtyRect = new RectF();
	
    public DrawView(Context context) {
        super(context);
        paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(STROKE_WIDTH);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
		float eventX = e.getX();
		float eventY = e.getY();
		
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			lastTouchX = eventX;
			lastTouchY = eventY;			
			return true;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			resetDirtyRect(eventX, eventY);			
			for (int i = 0; i < e.getHistorySize(); i++)
			{
				float historicalX = e.getHistoricalX(i);
				float historicalY = e.getHistoricalY(i);
				expandDirtyRect(historicalX, historicalY);
				path.lineTo(historicalX, historicalY);
			}
			
			path.lineTo(eventX, eventY);
			break;
		default:
			return false;
		}
		
		invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
		
		lastTouchX = eventX;
		lastTouchY = eventY;
		
		return true;
    }
    
    private void expandDirtyRect(float historicalX, float historicalY)
    {
    	if (historicalX < dirtyRect.left) dirtyRect.left = historicalX;
    	else if ( historicalX > dirtyRect.right) dirtyRect.right = historicalX; 
    	if (historicalY < dirtyRect.top) dirtyRect.top = historicalY;
    	else if ( historicalY > dirtyRect.bottom) dirtyRect.bottom = historicalY;
    }
    
    private void resetDirtyRect(float eventX, float eventY)
    {
    	dirtyRect.left = Math.min(lastTouchX, eventX);
    	dirtyRect.right = Math.max(lastTouchX, eventX);
    	dirtyRect.top = Math.min(lastTouchY, eventY);
    	dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
    
    @Override
    public void onDraw(Canvas c)
    {
    	//c.drawLine(20, 0, 0, 20, paint);
    	c.drawPath(path, paint);
    }
    
    public void clear()
    {
    	path.reset();
    	invalidate();
    }
    
}