package net.robotmedia.acv.logic;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import net.androidcomics.acv.BuildConfig;
import net.androidcomics.acv.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;

public class AdsManager {

	private static String publisherId = null;
	private static String testDeviceId = null;
	private final static String PUBLISHER_ID_RESOURCE_NAME = "admob_publisher_id";
	private final static String PUBLISHER_TEST_DEVICE_ID = "admob_test_device_id";
	private static boolean usesAds = true;

	public static final int SIZE_BANNER = 0;
	public static final int SIZE_FULL_BANNER = 1;

	// For compatibility with older versions
	// (equivalent to Configuration.SCREENLAYOUT_SIZE_XLARGE, but avoiding reflection for this)
	protected static final int RETRO_SCREENLAYOUT_SIZE_XLARGE = 4;
	
	public static AdView getAd(Activity activity, int size) {
		init(activity);
		if (!usesAds) return null;

		AdView adView = new AdView(activity, sizeToAdSize(size), publisherId);
		AdRequest request = new AdRequest();
		
		setExtras(request, activity);
		
		if (BuildConfig.DEBUG && testDeviceId != null) {
			request.addTestDevice(testDeviceId);
			request.addTestDevice(AdRequest.TEST_EMULATOR);
		}
		adView.loadAd(request);		
		adView.setId(R.id.ad);
		return adView;
	}
	
	private static void setExtras(AdRequest request, Activity activity) {
    	final int[] attrs = new int[] { R.attr.acv__primary_color};
    	TypedArray values = activity.getTheme().obtainStyledAttributes(attrs);
    	final int primaryColor = values.getColor(0, activity.getResources().getColor(R.color.acv__green));
    	values.recycle();
		final String primaryColorString = String.format("#%06X", (0xFFFFFF & primaryColor));

		request.addExtra("color_bg", primaryColorString);
		request.addExtra("color_bg_top", primaryColorString);
		request.addExtra("color_border", primaryColorString);
		request.addExtra("color_link", primaryColorString);
		request.addExtra("color_text", primaryColorString);
		request.addExtra("color_url", primaryColorString);
	}
	
	public static AdView getAd(Activity activity) {
		init(activity);
		if (!usesAds) return null;
		
		int adaptedSize;
		int screenSize = activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
		if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == RETRO_SCREENLAYOUT_SIZE_XLARGE) {
			adaptedSize = SIZE_FULL_BANNER;
		} else {
			adaptedSize = SIZE_BANNER;
		}
		return getAd(activity, adaptedSize);
	}
	
	public static void destroyAd(Activity activity) {
		AdView adView = (AdView) activity.findViewById(R.id.ad);
		if (adView != null) {
			adView.destroy();
		}
	}
	
	protected static void init(Context context) {
		if (!usesAds) return;
		
		if (publisherId != null) return;
		
		final int resourceId = context.getResources().getIdentifier(PUBLISHER_ID_RESOURCE_NAME, "string", context.getPackageName());
		final int testDeviceResourceId = context.getResources().getIdentifier(PUBLISHER_TEST_DEVICE_ID, "string", context.getPackageName());

		if (resourceId != 0) {
			publisherId = context.getString(resourceId);
			usesAds = true;
		} else {
			usesAds = false;
		}

		if (testDeviceResourceId != 0) {
			testDeviceId = context.getString(testDeviceResourceId);
		}
	}
	
	public static void disableAds() {
		usesAds = false;
	}
	
	private static AdSize sizeToAdSize(int size) {
		switch(size) {
			case AdsManager.SIZE_FULL_BANNER:
				return AdSize.IAB_BANNER;
			default:
				case AdsManager.SIZE_BANNER:
				return AdSize.BANNER;	
		}
}
}
