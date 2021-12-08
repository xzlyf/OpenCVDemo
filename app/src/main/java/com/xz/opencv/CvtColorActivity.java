package com.xz.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class CvtColorActivity extends AppCompatActivity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cvt_color);
		imageView = findViewById(R.id.image);

	}

	public void addMat(View view) {
		Mat mat1 = null;
		Mat mat2 = null;
		try {
			mat1 = Utils.loadResource(this, R.mipmap.bg_color);
			mat2 = Utils.loadResource(this, R.mipmap.bg_1);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Mat dst = new Mat();
		Core.bitwise_and(mat1, mat2, dst);
		//转换回Bitmap
		Bitmap newBitmap = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dst, newBitmap);
		imageView.setImageBitmap(newBitmap);
		mat1.release();
		mat2.release();
		dst.release();
	}

	public void xorMat(View view) {
		Mat mat1 = null;
		Mat mat2 = null;
		try {
			mat1 = Utils.loadResource(this, R.mipmap.bg_color);
			mat2 = Utils.loadResource(this, R.mipmap.bg_1);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Mat dst = new Mat();
		Core.bitwise_xor(mat1, mat2, dst);
		//转换回Bitmap
		Bitmap newBitmap = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dst, newBitmap);
		imageView.setImageBitmap(newBitmap);
		mat1.release();
		mat2.release();
		dst.release();
	}

	public void notMat(View view) {
		Mat mat1 = null;
		try {
			mat1 = Utils.loadResource(this, R.mipmap.bg_1);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Mat dst = new Mat();
		Core.bitwise_not(mat1, dst);
		//转换回Bitmap
		Bitmap newBitmap = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dst, newBitmap);
		imageView.setImageBitmap(newBitmap);
		mat1.release();
		dst.release();
	}
	public void orMat(View view) {
		Mat mat1 = null;
		Mat mat2 = null;
		try {
			mat1 = Utils.loadResource(this, R.mipmap.bg_color);
			mat2 = Utils.loadResource(this, R.mipmap.bg_1);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Mat dst = new Mat();
		Core.bitwise_or(mat1, mat2, dst);
		//转换回Bitmap
		Bitmap newBitmap = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dst, newBitmap);
		imageView.setImageBitmap(newBitmap);
		mat1.release();
		mat2.release();
		dst.release();
	}

	public void cvtColor(View view) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_1);
		Mat src = new Mat();
		Mat dst = new Mat();
		Utils.bitmapToMat(bitmap, src);
		//转换为灰度图模式
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
		//把mat转换回bitmap
		Utils.matToBitmap(dst, bitmap);
		imageView.setImageBitmap(bitmap);
		src.release();
		dst.release();

	}

}
