package me.naiyu.android.app.gittutorial;

import java.util.List;

import me.naiyu.android.app.gittutorial.Entity.MenuItem;
import me.naiyu.android.app.gittutorial.adapter.MenuAdapter;
import me.naiyu.android.app.gittutorial.fragment.BookFragment;
import me.naiyu.android.app.gittutorial.helper.MenuHelper;
import me.naiyu.android.app.gittutorial.helper.SharedPreHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnItemClickListener {
	
	private DrawerLayout mDrawerLayout;
	
	private ListView mMenuListView;
	
	private MenuAdapter mMenuAdapter;
	
	private List<MenuItem> mMenuItems;
	
	private BookFragment mBookFragment;
	
	private TextView mTvTitle;
	
	private long mFirstKeyBackTime = 0;
	private static final long TIME_DURATION = 2000;
	
	// 记录阅读到那里
	private int mReadingPositon = 0;
	
	private String mTheCurrentTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mReadingPositon = SharedPreHelper.getReadingPosition(this);
		mMenuItems = MenuHelper.getMenus(this, mReadingPositon);
		
		initView();
		setNavTitle(mMenuItems.get(mReadingPositon).getName());
		
		addContent();
	}
	
	
	private void initView() {
		mTvTitle = (TextView) findViewById(R.id.tv_title);
		initMenu();
	}
	
	private void addContent() {
		mBookFragment = new BookFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", mMenuItems.get(mReadingPositon).getUrl());
		mBookFragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, mBookFragment).commit();
	}
	
	private void initMenu() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			
			@Override
			public void onDrawerStateChanged(int arg0) {
			}
			
			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				
			}
			
			@Override
			public void onDrawerOpened(View view) {
				mTheCurrentTitle = mTvTitle.getText().toString().trim();
				setNavTitle(R.string.catalog);
			}
			
			@Override
			public void onDrawerClosed(View view) {
				setNavTitle(mTheCurrentTitle);
			}
		});
		mMenuListView = (ListView) findViewById(R.id.listview);
		
		
		mMenuAdapter = new MenuAdapter(this, mMenuItems);
		mMenuListView.setAdapter(mMenuAdapter);
		mMenuListView.setOnItemClickListener(this);
		mMenuListView.setSelection(mReadingPositon);
	}
	
	/**
	 * 设置标题
	 * @param title
	 */
	private void setNavTitle(String title) {
		mTvTitle.setText(title);
	}
	
	private void setNavTitle(int resId) {
		mTvTitle.setText(resId);
	}
	
	public void menuClick(View view) {
		if (mDrawerLayout.isDrawerOpen(mMenuListView)) {
			mDrawerLayout.closeDrawer(mMenuListView);
		} else {
			mDrawerLayout.openDrawer(mMenuListView);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mDrawerLayout.closeDrawer(mMenuListView);
		mMenuAdapter.getItem(mReadingPositon).setCheck(false);
		MenuItem item = mMenuAdapter.getItem(position);
		item.setCheck(true);
		mMenuAdapter.notifyDataSetChanged();
		mTheCurrentTitle = item.getName();
		mBookFragment.reload(item.getUrl());
		mReadingPositon = position;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mDrawerLayout.isDrawerOpen(mMenuListView)) {
				mDrawerLayout.closeDrawer(mMenuListView);
				return true;
			}
			long currentTime = System.currentTimeMillis();
			if (currentTime - mFirstKeyBackTime < TIME_DURATION) {
				SharedPreHelper.setReadingPostion(this, mReadingPositon);
				finish();
			} else {
				mFirstKeyBackTime = currentTime;
				Toast.makeText(this, R.string.again_back, Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		SharedPreHelper.setReadingPostion(this, mReadingPositon);
		super.onPause();
	}

}
