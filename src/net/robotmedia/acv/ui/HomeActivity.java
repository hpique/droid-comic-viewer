/*******************************************************************************
 * Copyright 2009 Robot Media SL
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.robotmedia.acv.ui;

import java.util.Random;

import net.androidcomics.acv.R;
import net.robotmedia.acv.logic.AdsManager;
import net.robotmedia.acv.logic.PreferencesController;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Bundle;
import com.actionbarsherlock.view.Menu;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuInflater;
import com.google.ads.AdView;

public class HomeActivity extends ACVActivity {

	private RelativeLayout mAdsContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.home);

		ActionBar actionBar = this.getSupportActionBar();

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);

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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.home, menu);
	    return true;
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
