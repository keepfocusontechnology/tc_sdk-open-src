package com.gavegame.tiancisdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

public class WelcomeActivity extends Activity {

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
				finish();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_page);
		
		View view = (View)findViewById(R.id.tv_main_desc);

		ScaleAnimation myAnimation_Scale = new ScaleAnimation(0.5f, 1.0f, 0.5f,1.0f
				, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Scale.setFillAfter(true);
		myAnimation_Scale.setStartOffset(500);
		myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
		myAnimation_Scale.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Message msg = Message.obtain();
				msg.what = 1;
				handler.sendMessageDelayed(msg, 1000);
			}
		});
		AnimationSet aa = new AnimationSet(true);
		aa.addAnimation(myAnimation_Scale);
		aa.setDuration(1500);

		view.startAnimation(aa);
	}

}
