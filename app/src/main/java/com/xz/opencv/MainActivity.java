package com.xz.opencv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initLoadOpenCV();
	}

	public void initLoadOpenCV() {
		boolean success = OpenCVLoader.initDebug();
		if (success) {
			Log.d("init", "initLoadOpenCV: openCV load success");
		} else {
			Log.e("init", "initLoadOpenCV: openCV load failed");
		}
	}

	public void cvtColorActivity(View view) {
		startActivity(new Intent(MainActivity.this, CvtColorActivity.class));
	}

	public void thresholdActivity(View view) {
		startActivity(new Intent(MainActivity.this, ThresholdActivity.class));
	}

	public void adaptiveThresholdActivity(View view) {
		startActivity(new Intent(MainActivity.this, AdaptiveThresholdActivity.class));
	}

	public void drawActivity(View view) {
		startActivity(new Intent(MainActivity.this, DrawActivity.class));
	}

	public void manualCut(View view) {
		startActivity(new Intent(MainActivity.this, ManualCutActivity.class));
	}

	public void pixelModif(View view) {
		startActivity(new Intent(MainActivity.this, PixelModifActivity.class));
	}

	public void Imgface(View view) {
		startActivity(new Intent(MainActivity.this, ImgFaceActivity.class));
	}

	public void realface(View view) {
		startActivity(new Intent(MainActivity.this, ReadFaceActivity.class));
	}
}
