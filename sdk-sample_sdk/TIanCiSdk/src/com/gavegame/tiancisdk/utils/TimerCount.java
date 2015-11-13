package com.gavegame.tiancisdk.utils;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimerCount extends CountDownTimer {

	private Button checking;

	public TimerCount(long millisInFuture, long countDownInterval, Button view) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		checking = view;
	}

	@Override
	public void onFinish() {// 计时完毕时触发
		checking.setText("重新验证");
		checking.setClickable(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
		checking.setClickable(false);
		checking.setText(millisUntilFinished / 1000 + "秒");
	}
}
