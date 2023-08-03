package com.truepolitician.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.onesignal.OneSignal;
import com.truepolitician.game.TruePolitician;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
//	private AdView adView;

//	String deviceId = "026D397652285514C25E0D0AC77273A2";

//	protected Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == 0)
//				adView.setVisibility(View.GONE);
//			if (msg.what == 1) {
//				adView.setVisibility(View.VISIBLE);
//				AdRequest adRequest = new AdRequest.Builder().build();
//				adView.loadAd(adRequest);
//			}
//		}
//	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useWakelock = true;

		// создём главный слой
		RelativeLayout layout = new RelativeLayout(this);

		// устанавливаем флаги, которые устанавливались в методе initialize() вместо нас
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// представление для LibGDX
		View gameView = initializeForView(new TruePolitician(), config);

		// представление и настройка AdMob
//		MobileAds.initialize(this, getString(R.string.application_id));
//		adView = new AdView(this);
//		adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
//		adView.setAdSize(AdSize.SMART_BANNER);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);

		// добавление представление игры к слою
		layout.addView(gameView);
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		// добавление представление рекламы к слою
//		layout.addView(adView, adParams);

		// всё соединяем в одной слое
		setContentView(layout);
		showAdMob(true);

		try {
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		} catch (Exception e) { }

		// OneSignal Initialization
		OneSignal.initWithContext(this);
		OneSignal.setAppId(getString(R.string.onesignal_app_ad));
		OneSignal.promptForPushNotifications();

		// Initializing the AppMetrica SDK
		YandexMetricaConfig yaConfig = YandexMetricaConfig.newConfigBuilder(getString(R.string.appMetrica_api_key)).build();
		YandexMetrica.activate(getApplicationContext(), yaConfig);
		YandexMetrica.enableActivityAutoTracking(getApplication());

		new Intent(Intent.ACTION_VIEW, Uri.parse("https://bruimafia.github.io/AppPrivacyPolicy/russian_politician.html"));
	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAdMob(boolean show){
		handler.sendEmptyMessage(show ? 1 : 0);
	}

}
