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

import net.androidcomics.acv.R;
import net.robotmedia.acv.logic.AdsManager;
import net.robotmedia.acv.logic.PreferencesController;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

public abstract class ACVActivity extends SherlockFragmentActivity {

	private PreferencesController preferences;
	
	protected PreferencesController getPreferences() {
		return preferences;
	}

	private RelativeLayout mAdContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = new PreferencesController(this);
		final int themeId = this.preferences.getTheme();
		this.setTheme(themeId);
		
		EasyTracker.getInstance().activityStart(this);
		
		mAdContainer = (RelativeLayout) findViewById(R.id.ad_container);
		showAd();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(getMenuId(), menu);
	    return true;
	}
	
	protected abstract int getMenuId();
	
	@Override
	protected void onStop() {
		EasyTracker.getInstance().activityStop(this);
		super.onStop();
	}
	
	private void hideAd() {
		if (mAdContainer == null) return;

		AdsManager.destroyAd(this);
		mAdContainer.removeAllViews();
	}
	
	protected void showAd() {
		if (mAdContainer == null) return;

		hideAd();
		final AdView ad = AdsManager.getAd(this);
		if (ad == null) return;
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		mAdContainer.addView(ad, lp);
	}
	
}