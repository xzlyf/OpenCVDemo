package com.xz.opencv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThresholdActivity extends AppCompatActivity {

	private ImageView imageView;
	private SeekBar seekBar;
	private Bitmap bitmap;
	private Mat src;
	private Mat dst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adaptive_threshold);
		imageView = findViewById(R.id.image);
		seekBar = findViewById(R.id.seekBar);

		bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_2);
		src = new Mat();
		dst = new Mat();
		Utils.bitmapToMat(bitmap, src);
		//先把源文转为灰度图
		Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
		adaptiveThreshold();


	}

	public void adaptiveThreshold() {
		Imgproc.adaptiveThreshold(src,dst,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,13,5);
		Utils.matToBitmap(dst, bitmap);
		imageView.setImageBitmap(bitmap);
	}
}
