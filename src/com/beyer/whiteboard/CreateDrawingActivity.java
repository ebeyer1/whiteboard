package com.beyer.whiteboard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class CreateDrawingActivity extends Activity {
	private DrawView drawView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_view);
		drawView = (DrawView) this.findViewById(R.id.drawingView);
		//setContentView(dw);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void setColorRed(View view)
	{
		drawView.setColor(Color.RED);
	}
	
	public void setColorBlue(View view)
	{
		drawView.setColor(Color.BLUE);
	}
	
	public void setColorGreen(View view)
	{
		drawView.setColor(Color.GREEN);
	}
	
	public void undoAction(View view)
	{
		drawView.onClickUndo();
	}
	
	public void redoAction(View view)
	{
		drawView.onClickRedo();
	}
	
	public void clearDrawing(View view)
	{
		drawView.clearDrawing();
	}
}
