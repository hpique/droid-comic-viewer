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
import net.robotmedia.acv.logic.PreferencesController;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;

public class HomeActivity extends ACVActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = this.getSupportActionBar();

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
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
		final int[] attrs = new int[] { R.attr.acv__primary_color };
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

	@Override
	protected int getContentViewId() {
		return R.layout.home;
	}
	
	@Override
	protected int getMenuId() {
		return R.menu.home;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int id = item.getItemId();
		switch (id) {
		case R.id.menu_open:
			startOpen();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void startOpen() {
		final Intent intent = new Intent(this, OpenActivity.class);
		startActivity(intent);
	}


}
