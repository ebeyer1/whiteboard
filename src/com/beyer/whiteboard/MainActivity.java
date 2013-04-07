package com.beyer.whiteboard;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.method.Touch;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	DrawView dw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void drawLine(View view)
	{
		//Toast.makeText(this, "Some text", Toast.LENGTH_SHORT).show();
		
		//dw = new DrawView(this.getApplicationContext());
		//dw.setBackgroundColor(Color.WHITE);
		//setContentView(dw);
		Intent intent = new Intent(this.getApplicationContext(), CreateDrawingActivity.class);
		startActivity(intent);
	}
}
