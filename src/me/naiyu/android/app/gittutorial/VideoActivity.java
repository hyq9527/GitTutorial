package me.naiyu.android.app.gittutorial;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import me.naiyu.android.app.gittutorial.helper.VideoDownloadHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class VideoActivity extends Activity {
	
	private String mUrl;
	
	private VideoView mVideoView;
	
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vedio_layout);
		
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
			return;
		}
		
		mDialog = new ProgressDialog(this);
		
		mUrl = getIntent().getStringExtra("vedioUrl");
		
		initView();
		
	}
	
	private void initView() {
		mVideoView = (VideoView) findViewById(R.id.videoview);
		String playUrl = getVideoFile(mUrl);
		mVideoView.setVideoPath(playUrl);
		MediaController controller = new MediaController(this);
		mVideoView.setMediaController(controller);
		mVideoView.requestFocus();
		mDialog.show();
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				mDialog.dismiss();
				mediaPlayer.setPlaybackSpeed(1.0f);
			}
			
		});
	}
	
	private String getVideoFile(String url) {
		VideoDownloadHelper download = new VideoDownloadHelper();
		return download.newDownloadFile(url);
	}

}
