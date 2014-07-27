package me.naiyu.android.app.gittutorial.helper;

import io.vov.vitamio.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Environment;

/**
 * 
 * 视频文件下载帮助类
 * 
 * @author naiyu(naiyu.me)
 * 
 *         2014-7-27
 */
public class VideoDownloadHelper {

	private static final String VIDEO_CACHE_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "GitTutorial"
			+ File.separator + "video";

	/**
	 * 新建下载线程
	 * 
	 * @param url
	 *            下载路径
	 */
	public String newDownloadFile(String url) {
		String playUrl = "";
		String videoName = FileUtils.getName(url);
		//TODO 判断是否已经存在缓存了，如何存在，不用下载，否则，下载
		File videoCacheFile = new File(VIDEO_CACHE_PATH);
		File cacheParentFile = new File(videoCacheFile.getParent());
		
		if (!cacheParentFile.exists()) {
			cacheParentFile.mkdir();
		}
		
		if (!videoCacheFile.exists()) {
			videoCacheFile.mkdir();
		}
		
		File[] videoFiles = videoCacheFile.listFiles();
		
		if (videoFiles == null || videoFiles.length == 0) {
			//TODO 下载
			playUrl = url;
			startDownload(url, VIDEO_CACHE_PATH + File.separator + videoName);
		} else {
			for (int i = 0; i < videoFiles.length; i++) {
				if (videoFiles[i].getPath().indexOf(videoName) == -1) {
					playUrl = url;
					startDownload(url, VIDEO_CACHE_PATH + File.separator + videoName);
				} else {
					playUrl = VIDEO_CACHE_PATH + File.separator + videoName;
				}
			}
		}
		return playUrl;
	}
	
	private void startDownload(String url, String savePath) {
		new Thread(new DownloadRunnable(url, savePath)).start();
	}

	class DownloadRunnable implements Runnable {

		private String url;

		private String savePath;

		public DownloadRunnable(String url, String savePath) {
			this.url = url;
			this.savePath = savePath;
		}

		@Override
		public void run() {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			InputStream inputStream = null;
			FileOutputStream outputStream = null;

			try {
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();
				final int size = (int) entity.getContentLength();
				inputStream = entity.getContent();

				if (size > 0 && inputStream != null) {
					outputStream = new FileOutputStream(savePath);
					int ch = -1;
					byte[] buf = new byte[1024];
					// 每秒更新一次进度
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							try {
								FileInputStream fis = new FileInputStream(
										new File(savePath));
								int downloadedSize = fis.available();
								if (downloadedSize >= size)
									cancel();
							} catch (Exception e) {

							}
						}
					}, 50, 1000);

					while ((ch = inputStream.read(buf)) != -1) {
						outputStream.write(buf, 0, ch);
					}
					outputStream.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (outputStream != null)
						outputStream.close();
				} catch (IOException ex) {
				}
				try {
					if (inputStream != null)
						inputStream.close();
				} catch (IOException ex) {
				}
			}
		}

	}

}
