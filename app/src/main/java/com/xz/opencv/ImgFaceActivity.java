package com.xz.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImgFaceActivity extends AppCompatActivity {

	private CascadeClassifier classifier;
	private ImageView image;
	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_face);
		initClassifier();
		initView();
	}

	private void initView() {
		image = findViewById(R.id.image);
		btn1 = findViewById(R.id.btn1);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
				image.setImageBitmap(face(bitmap));
			}
		});
	}

	public void initClassifier() {
		try {
			//读取存放在raw的文件
			InputStream is = getResources()
					.openRawResource(R.raw.lbpcascade_frontalface_improved);
			File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
			File cascadeFile = new File(cascadeDir, "lbpcascade_frontalface_improved.xml");
			FileOutputStream os = new FileOutputStream(cascadeFile);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.close();
			//通过classifier来操作人脸检测， 在外部定义一个CascadeClassifier classifier
			classifier = new CascadeClassifier(cascadeFile.getAbsolutePath());
			cascadeFile.delete();
			cascadeDir.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap face(Bitmap bitmap) {
		Mat mat = new Mat();
		Mat matdst = new Mat();
		Utils.bitmapToMat(bitmap, mat);
		//把当前数据复制一份给matdst
		mat.copyTo(matdst);

		//1.把图片转为灰度图 BGR2GRAY，注意是BGR
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);

		//2.定义MatOfRect用于接收人脸位置
		MatOfRect faces = new MatOfRect();

		//3.开始人脸检测，把检测到的人脸数据存放在faces中
		classifier.detectMultiScale(mat, faces, 1.05, 3, 0, new Size(30, 30), new Size());
		List<Rect> faceList = faces.toList();

		//4.判断是否存在人脸
		if (faceList.size() > 0) {
			for (Rect rect : faceList) {
				//5.根据得到的人脸位置绘制矩形框
				//rect.tl() 左上角
				//rect.br() 右下角
				Imgproc.rectangle(matdst, rect.tl(), rect.br(), new Scalar(255, 0, 0,255), 4);
			}
		}else{
			Toast.makeText(this, "没有检测到人脸", Toast.LENGTH_SHORT).show();
		}

		Bitmap resultBitmap = Bitmap.createBitmap(matdst.width(), matdst.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(matdst, resultBitmap);
		mat.release();
		matdst.release();
		return resultBitmap;
	}


}
