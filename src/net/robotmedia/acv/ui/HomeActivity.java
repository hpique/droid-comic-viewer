package net.robotmedia.acv.ui;

import net.androidcomics.acv.R;
import net.robotmedia.acv.adapter.RecentListBaseAdapter;
import net.robotmedia.acv.logic.AdsManager;
import android.content.res.Configuration;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;

public class HomeActivity extends SherlockActivity {

	protected ViewGroup mRecentItems = null;
	protected ListView mRecentItemsList = null;
	protected RecentListBaseAdapter mRecentItemsListAdapter = null;
	private RelativeLayout mAdsContainer;
	private RelativeLayout mLogoContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		ActionBar actionBar = this.getSupportActionBar();

		mLogoContainer = (RelativeLayout) findViewById(R.id.home_logo_container);
		
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);

		mRecentItems = (ViewGroup) findViewById(R.id.main_recent);
		mRecentItemsList = (ListView) findViewById(R.id.main_recent_list);
		mRecentItemsList.setEmptyView(findViewById(R.id.main_recent_list_no_items));
		mRecentItemsListAdapter = new RecentListBaseAdapter(this, R.layout.list_item_recent);
		mRecentItemsListAdapter.setMaxNumItems(2);
		mRecentItemsList.setAdapter(mRecentItemsListAdapter);
		mRecentItemsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// String path = (String) parent.getItemAtPosition(position);
			}
		});
		
		ImageView logo = (ImageView) findViewById(R.id.main_logo);
		logo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
		}});

		mAdsContainer = (RelativeLayout) findViewById(R.id.mainAdsContainer);
		showAds();
	}
	
	private void hideAds() {
		AdsManager.destroyAds(this);
		mAdsContainer.removeAllViews();
	}
	
	private void showAds() {
		hideAds();
		View ad = AdsManager.getAd(this);
		if(ad != null) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			mAdsContainer.addView(ad, lp);
		}
	}
		
	@Override
	public void onResume() {
		mRecentItemsListAdapter.refresh();
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.home, menu);
	    return true;
	}
	
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		layoutLogoContainer();
	}
	
	private void layoutLogoContainer() {
		ActionBar actionBar = this.getSupportActionBar();
		ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLogoContainer.getLayoutParams();
		params.setMargins(0, actionBar.getHeight(), 0, 0);
		mLogoContainer.setLayoutParams(params);
		mLogoContainer.requestLayout();
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	layoutLogoContainer();
    }

}
