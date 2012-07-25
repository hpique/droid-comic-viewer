package net.robotmedia.acv.ui;

import net.robotmedia.acv.logic.PreferencesController;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class ACVActivity extends SherlockActivity {

	private PreferencesController preferences;
	
	protected PreferencesController getPreferences() {
		return preferences;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = new PreferencesController(this);
		final int themeId = this.preferences.getTheme();
		this.setTheme(themeId);
	}
	
}