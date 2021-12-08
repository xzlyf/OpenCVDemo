package com.xz.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ThresholdActivity extends AppCompatActivity {

	private ImageView imageView;
	private SeekBar seekBar;
	private Bitmap bitmap;
	private Mat src;
	private Mat dst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_threshold);
		imageView = findViewById(R.id.image);
		seekBar = findViewById(R.id.seekBar);

		bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_2);
		src = new Mat();
		dst = new Mat();
		Utils.bitmapToMat(bitmap, src);
		//先把源文转为灰度图
		Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
		threshold(125);

		//滑杆监听
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				threshold(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

	}

	public void threshold(double threshold) {
		Imgproc.threshold(src, dst, threshold, 255, Imgproc.THRESH_BINARY);
		Utils.matToBitmap(dst, bitmap);
		imageView.setImageBitmap(bitmap);
	}
}
