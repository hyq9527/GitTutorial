package me.naiyu.android.app.gittutorial.helper;

import java.util.ArrayList;
import java.util.List;

import me.naiyu.android.app.gittutorial.R;
import me.naiyu.android.app.gittutorial.Entity.MenuItem;
import android.content.Context;

public class MenuHelper {
	
	
	public static List<MenuItem> getMenus(Context context, int readingPos) {
		String[] menuNames = context.getResources().getStringArray(R.array.menu_name);
		String[] menuUrls = context.getResources().getStringArray(R.array.menu_url);
		List<MenuItem> menus = new ArrayList<MenuItem>();
		for (int i = 0; i < menuNames.length; i++) {
			MenuItem item = new MenuItem();
			item.setName(menuNames[i]);
			item.setUrl(menuUrls[i]);
			if (i == readingPos) {
				item.setCheck(true);
			} else {
				item.setCheck(false);
			}
			menus.add(item);
		}
		return menus;
	}

}
