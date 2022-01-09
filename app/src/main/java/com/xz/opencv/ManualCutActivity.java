package com.xz.opencv;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class ManualCutActivity extends AppCompatActivity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_cut);
		imageView = findViewById(R.id.image);
		Button bt0 = findViewById(R.id.bt0);
		bt0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imageView.setImageResource(R.mipmap.bg_color);
			}
		});
		Button bt1 = findViewById(R.id.bt1);
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cut();
			}
		});

		Button bt2 = findViewById(R.id.bt2);
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				color();
			}
		});

	}

	/**
	 * 手动切割
	 */
	public void cut() {
		Mat src = null;
		try {
			src = Utils.loadResource(ManualCutActivity.this, R.mipmap.bg_3);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//手动指定切割区域
		Rect rect = new Rect(55, 108, 365, 269);
		Mat dst = new Mat(src, rect);

		//转换会RGB颜色
		Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2RGB);

		Bitmap resultBitmap = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dst, resultBitmap);
		imageView.setImageBitmap(resultBitmap);
		dst.release();
		src.release();
	}

	/**
	 * 颜色检测
	 */
	public void color() {
		Mat src = null;
		try {
			src = Utils.loadResource(ManualCutActivity.this, R.mipmap.bg_color);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//手动指定切割区域
		Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);

		Core.inRange(src,new Scalar(100,100,100),new Scalar(125,255,255),src);

		//这就是一个运算核，一个3x3的矩阵
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
		//进行开运算
		Imgproc.morphologyEx(src, src, Imgproc.MORPH_OPEN, kernel);
		//进行闭运算
		Imgproc.morphologyEx(src, src, Imgproc.MORPH_CLOSE, kernel);

		Bitmap resultBitmap = Bitmap.createBitmap(src.width(), src.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(src, resultBitmap);
		imageView.setImageBitmap(resultBitmap);

		src.release();
	}

}
