package me.naiyu.android.app.gittutorial;

import java.io.IOException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureActivity extends Activity {
	
	private static final String HOST = "git-html/";

	private ImageView mImageView;
	
	private PhotoViewAttacher mAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_picture);
		initView();
		getDataFromIntent();
		
	}

	private void initView() {
		mImageView = (ImageView) findViewById(R.id.iv_pic);
	}

	private void getDataFromIntent() {
		try {
			Intent it = getIntent();
			String picUrl = it.getStringExtra("picture");
			InputStream is = getAssets().open(HOST + picUrl);
			Bitmap bm = BitmapFactory.decodeStream(is);
			is.close();
			mImageView.setImageBitmap(bm);
			mAttacher = new PhotoViewAttacher(mImageView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
