package net.robotmedia.acv.ui;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;

import net.androidcomics.acv.R;

public class OpenActivity extends ACVActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.open);

		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
	}
	
	@Override
	protected int getContentViewId() {
		return R.layout.open;
	}
	
	@Override
	protected int getMenuId() {
		return R.menu.open;
	}
	
}
