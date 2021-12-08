package com.xz.opencv;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class PixelModifActivity extends AppCompatActivity {

	private ImageView imgAfter;
	private Button btn1;
	private ImageView imgBefore;
	private Bitmap srcBitmap;
	private Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pixel_modif);
		initView();
		srcBitmap = ((BitmapDrawable) imgAfter.getDrawable()).getBitmap();
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgAfter.setImageBitmap(modify(srcBitmap));
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgAfter.setImageBitmap(modifyV2(srcBitmap));
			}
		});
	}

	private void initView() {
		imgAfter = findViewById(R.id.img_after);
		btn1 = findViewById(R.id.btn1);
		imgBefore = findViewById(R.id.img_before);
		btn2 = findViewById(R.id.btn2);
	}

	/**
	 * 转换怀旧图片处理
	 *
	 * @param srcBitmap 源bitmap
	 * @return 处理后bitmap
	 */
	private Bitmap modify(Bitmap srcBitmap) {
		Mat mat = new Mat();
		Utils.bitmapToMat(srcBitmap, mat);

		//通道数
		int channels = mat.channels();
		//宽度
		int col = mat.cols();
		//高度 等同于srcMat.height();
		int row = mat.rows();
		//类型
		int type = mat.type();
		Log.d("TAG", "通道数： " + channels + " 宽度：" + col + " 高度：" + row + " 类型：" + type);


		//定义一个数组，用来存储每个像素点数据，数组长度对应图片的通道数
		//至于为什么用byte，那就要看文档的对照表，不同图片类型对应不同java数据类型，这里CV_8UC4，对应Java byte类型
		byte[] p = new byte[channels];

		int r = 0, g = 0, b = 0;

		//循环遍历每个像素点，对每个像素点进行操作
		for (int h = 0; h < row; h++) {
			for (int w = 0; w < col; w++) {
				//通过像素点位置得到该像素带点的数据，并存入p数组中
				mat.get(h, w, p);

				//得到一个像素点的RGB值
				//这里为啥要 & 0xff ，下节有讲。
				r = p[0] & 0xff;
				g = p[1] & 0xff;
				b = p[2] & 0xff;


				//根据某个滤镜公式进行计算
				int AR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
				int AG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
				int AB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

				//防越界判断，byte最大数值是255。
				AR = (AR > 255 ? 255 : (AR < 0 ? 0 : AR));
				AG = (AG > 255 ? 255 : (AG < 0 ? 0 : AG));
				AB = (AB > 255 ? 255 : (AB < 0 ? 0 : AB));

				//把修改后的数据重新写入数组
				p[0] = (byte) AR;
				p[1] = (byte) AG;
				p[2] = (byte) AB;

				//把数组写入像素点
				mat.put(h, w, p);
			}
		}

		Bitmap dstBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(mat, dstBitmap);
		mat.release();
		return dstBitmap;
	}

	private Bitmap modifyV2(Bitmap srcBitmap) {
		Mat mat = new Mat();
		Utils.bitmapToMat(srcBitmap, mat);

		int channels = mat.channels();
		int col = mat.cols();
		int row = mat.rows();
		int type = mat.type();
		Log.d("TAG", "通道数： " + channels + " 宽度：" + col + " 高度：" + row + " 类型：" + type);


		//用于保存一行像素的数据，单个像素点的数据*一行的像素
		byte[] p = new byte[channels * col];

		int r = 0, g = 0, b = 0;

		for (int h = 0; h < row; h++) {
			//col = 0 表示这是一行的数据，把一行的像素点读取到p数组来
			mat.get(h, 0, p);
			for (int w = 0; w < col; w++) {
				int index = channels * w;

				r = p[index] & 0xff;
				g = p[index + 1] & 0xff;
				b = p[index + 2] & 0xff;


				int AR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
				int AG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
				int AB = (int) (0.272 * r + 0.534 * g + 0.131 * b);
				//另一种算法
				//int AR = Math.abs(g - b + g + r) * g / 256;
				//int AG = Math.abs(b - g + b + r) * r / 256;
				//int AB = Math.abs(b - g + b + r) * r / 256;
				AR = (AR > 255 ? 255 : (AR < 0 ? 0 : AR));
				AG = (AG > 255 ? 255 : (AG < 0 ? 0 : AG));
				AB = (AB > 255 ? 255 : (AB < 0 ? 0 : AB));
				p[index] = (byte) AR;
				p[index + 1] = (byte) AG;
				p[index + 2] = (byte) AB;

			}

			//同上，0表示是一行的数据，不再通过某个像素点写入
			mat.put(h, 0, p);
		}

		Bitmap dstBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(mat, dstBitmap);
		mat.release();
		return dstBitmap;
	}
}
