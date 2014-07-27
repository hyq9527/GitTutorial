package me.naiyu.android.app.gittutorial.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreHelper {
	
	private static final String SHARED_NAME = "git_config";
	
	private static final String POSITION_KEY = "reading";
	
	public static int getReadingPosition(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
		return preferences.getInt(POSITION_KEY, 1);
	}
	
	public static void setReadingPostion(Context context, int position) {
		SharedPreferences preferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
		preferences.edit().putInt(POSITION_KEY, position).commit();
	}

}
