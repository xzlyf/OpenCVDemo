package com.xz.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DrawActivity extends AppCompatActivity {
	private ImageView imageView;
	private Bitmap bitmap;
	private Mat src;
	private Point p1, p2;
	private Scalar scalar;
	private int rectSize = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw);
		imageView = findViewById(R.id.image);
		bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_2);
		src = new Mat();

		p1 = new Point(0, 0);
		p2 = new Point(rectSize, rectSize);
		scalar = new Scalar(255, 0, 0);
		draw(0, 0);

		imageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						draw(event.getX()/2, event.getY()/2);
				}
				return true;
			}
		});
	}

	public void draw(float x, float y) {
		p1.set(new double[]{x, y});
		p2.set(new double[]{x+rectSize, y+rectSize});
		Utils.bitmapToMat(bitmap, src);
		Imgproc.rectangle(src, p1, p2, scalar, 2);
		Utils.matToBitmap(src, bitmap);
		imageView.setImageBitmap(bitmap);
	}

}
