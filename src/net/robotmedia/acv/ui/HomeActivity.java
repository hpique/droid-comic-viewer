package net.robotmedia.acv.ui;

import java.util.Random;

import net.androidcomics.acv.R;
import net.robotmedia.acv.adapter.RecentListBaseAdapter;
import net.robotmedia.acv.logic.AdsManager;
import net.robotmedia.acv.logic.PreferencesController;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuInflater;
import com.google.ads.AdView;

public class HomeActivity extends ACVActivity {

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

		mAdsContainer = (RelativeLayout) findViewById(R.id.mainAdsContainer);
		showAds();
	}
	
	private void hideAds() {
		AdsManager.destroyAds(this);
		mAdsContainer.removeAllViews();
	}
	
	private void showAds() {
		hideAds();
		AdView ad = AdsManager.getAd(this);
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
		layoutLogoContainerBelowActionBar();
	}
	
	private void layoutLogoContainerBelowActionBar() {
		ActionBar actionBar = this.getSupportActionBar();
		ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLogoContainer.getLayoutParams();
		params.setMargins(0, actionBar.getHeight(), 0, 0);
		mLogoContainer.setLayoutParams(params);
		mLogoContainer.requestLayout();
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	layoutLogoContainerBelowActionBar();
    }
    
    public void setRandomTheme(View v) {
    	final int currentThemeId = this.getPreferences().getTheme();
    	int newThemeId;
    	do {
    		final int i = new Random().nextInt(PreferencesController.THEMES.length);
    		newThemeId = PreferencesController.THEMES[i];
    	} while (currentThemeId == newThemeId); 
    	
    	this.getPreferences().setTheme(newThemeId);
    	this.updateTheme(newThemeId);
    }
    
    private void updateTheme(int resId) {
    	final int[] attrs = new int[] { R.attr.acv__primary_color};
    	TypedArray values = this.getTheme().obtainStyledAttributes(resId, attrs);
    	final int primaryColor = values.getColor(0, getResources().getColor(R.color.acv__green));
    	values.recycle();
    	
		final ImageView dotsTop = (ImageView) findViewById(R.id.bg_dots_top);
		dotsTop.setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
		final ImageView dotsBottom = (ImageView) findViewById(R.id.bg_dots_bottom);
		dotsBottom.setColorFilter(primaryColor, PorterDuff.Mode.SRC_ATOP);
		final TextView recentHeader = (TextView) findViewById(R.id.home_recent_header);
		recentHeader.setTextColor(primaryColor);
    }
}
