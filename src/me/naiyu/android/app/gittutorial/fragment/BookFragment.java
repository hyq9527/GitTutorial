package me.naiyu.android.app.gittutorial.fragment;

import me.naiyu.android.app.gittutorial.PictureActivity;
import me.naiyu.android.app.gittutorial.R;
import me.naiyu.android.app.gittutorial.VideoActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class BookFragment extends Fragment {

	private WebView mWebView;
	
	private static final String HOST = "file:///android_asset/git-html/";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.book_layout, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		String url = getArguments().getString("url");
		mWebView = (WebView) view.findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(HOST + url);
		mWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Git");
	}

	public class WebAppInterface {
		Context mContext;

		WebAppInterface(Context c) {
			mContext = c;
		}

		@JavascriptInterface
		public void showPic(String picUrl) {
			Intent it = new Intent();
			it.setClass(getActivity(), PictureActivity.class);
			it.putExtra("picture", picUrl);
			startActivity(it);
		}
		
		@JavascriptInterface
		public void playVideo(String vedioUrl) {
			Intent it = new Intent();
			it.setClass(getActivity(), VideoActivity.class);
			it.putExtra("vedioUrl", vedioUrl);
			startActivity(it);
		}
	}
	
	public void reload(String url) {
		mWebView.loadUrl(HOST + url);
	}

}
