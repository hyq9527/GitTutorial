package me.naiyu.android.app.gittutorial.adapter;

import java.util.List;

import me.naiyu.android.app.gittutorial.R;
import me.naiyu.android.app.gittutorial.Entity.MenuItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private List<MenuItem> mItems;
	
	private LayoutInflater mInflater;
	
	
	public MenuAdapter(Context context, List<MenuItem> items) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mItems = items;
	}
	

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public MenuItem getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.menu_item_layout, parent, false);
			holder.tvNameOne = (TextView) convertView.findViewById(R.id.tv_menu_name_one);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MenuItem item = getItem(position);
		holder.tvNameOne.setText(item.getName());
		
		if (item.isCheck()) {
			convertView.setBackgroundResource(R.color.item_press_color);
		} else {
			convertView.setBackgroundResource(R.drawable.item_bg_selector);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tvNameOne;
	}

}
