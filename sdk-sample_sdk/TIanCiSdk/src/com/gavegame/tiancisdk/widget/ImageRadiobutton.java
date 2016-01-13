package com.gavegame.tiancisdk.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class ImageRadiobutton extends LinearLayout {

	private final String TAG = "ImageRadiobutton";
	private RadioButton radioButton;
	private ImageView image;
	private Context context;
	private Drawable drawable;
	// private int radioWidth = 80;
	private RadioButtonCheckedListener listener;
	private boolean isChecked;

	// 两个控件相隔的距离
	private final int maginWidth = 10;

	public ImageRadiobutton(Context context) {
		super(context);
		this.context = context;
		radioButton = new RadioButton(context);
		image = new ImageView(context);
		init();
	}

	public ImageRadiobutton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		radioButton = new RadioButton(context);
		image = new ImageView(context);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.imageRadiabutton, 0, 0);
		drawable = a.getDrawable(R.styleable.imageRadiabutton_image);
		if (drawable != null)
			image.setImageDrawable(drawable);
		a.recycle();
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int defaultWidthSize = getMinWidth();
		if (defaultWidthSize == 0) {
			throw new NullPointerException(
					"the radiaView not contains any child view!!!");
		}

		int defaultHeightSize = radioButton.getHeight() > drawable
				.getMinimumHeight() ? radioButton.getHeight() : drawable
				.getMinimumHeight();

		switch (widthMode) {
		// wrap
		case MeasureSpec.AT_MOST:
			// TCLogUtils.e(TAG, " width onMeasure mode is AT_MOST width = " +
			// width);
			// here we must confirm mini size
			if (width > defaultWidthSize) {
				width = defaultWidthSize;
			}
			// TCLogUtils.e(TAG, " width onMeasure mode is AT_MOST");

			break;

		case MeasureSpec.UNSPECIFIED:
			// TCLogUtils.e(TAG, "width onMeasure mode is UNSPECIFIED");
			break;
		// match
		case MeasureSpec.EXACTLY:
			// match_parent and with confirmed size
			// TCLogUtils.e(TAG, "width onMeasure mode is EXACTLY");
			break;
		}

		switch (heightMode) {
		// wrap
		case MeasureSpec.AT_MOST:
			// TCLogUtils.e(TAG, " height onMeasure mode is AT_MOST width = " +
			// height);
			// here we must confirm mini size
			if (height > defaultHeightSize) {
				height = defaultHeightSize;
			}
			// TCLogUtils.e(TAG, " height onMeasure mode is AT_MOST");

			break;

		case MeasureSpec.UNSPECIFIED:
			// TCLogUtils.e(TAG, "height onMeasure mode is UNSPECIFIED");
			break;
		// match
		case MeasureSpec.EXACTLY:
			// match_parent and with confirmed size
			// TCLogUtils.e(TAG, "height onMeasure mode is EXACTLY");
			break;
		}
		setMeasuredDimension(width, height);
	}

	/**
	 * 设置监听器
	 * 
	 * @param listener
	 */
	public void setCheckListener(RadioButtonCheckedListener listener) {
		this.listener = listener;
	}

	void init() {
		// listener = (RadioButtonCheckedListener) context;
		RelativeLayout.LayoutParams bt_lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		;
		radioButton.setLayoutParams(bt_lp);
		radioButton.setGravity(Gravity.CENTER_VERTICAL);
		// radioButton.setEnabled(false);
		addView(radioButton);
		RelativeLayout.LayoutParams img_lp = new RelativeLayout.LayoutParams(
				drawable.getMinimumWidth(), drawable.getMinimumHeight());
		// bt_lp.gravity = Gravity.CENTER_VERTICAL;
		bt_lp.setMargins(maginWidth, 0, 0, 0);
		if (image != null)
			addView(image, img_lp);
		// this.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (isChecked) {
		// setUnChecked();
		// } else {
		// setChecked();
		// }
		// if (listener != null)
		// listener.onCheckedChanged(isChecked);
		// }
		// });
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (isChecked) {
					setUnChecked();
				} else {
					setChecked();
				}
				if (listener != null)
					listener.onCheckedChanged(isChecked);
				return true;
			}
		});
		//
		// radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// TCLogUtils.e(TAG, "radioButton checked!!!!!");
		// }
		// });
		radioButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				TCLogUtils.e(TAG, "radioButton touched!!!!!");
				if (isChecked) {
					setUnChecked();
				} else {
					setChecked();
				}
				if (listener != null)
					listener.onCheckedChanged(isChecked);
				return true;
			}
		});
		// radioButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// TCLogUtils.e(TAG, "radioButton clicked!!!!!");
		// }
		// });

	}

	/**
	 * 获取当前控件最小的宽度
	 * 
	 * @return
	 */
	private int getMinWidth() {
		if (radioButton == null || image == null) {
			return 0;
		}
		return radioButton.getWidth() + drawable.getMinimumWidth() + maginWidth;
	}

	@SuppressLint("NewApi")
	void setImageView(int resId) {
		image.setBackground(getResources().getDrawable(resId));
	}

	public void setChecked() {
		radioButton.setChecked(true);
		isChecked = true;
	}

	public void setUnChecked() {
		radioButton.setChecked(false);
		isChecked = false;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public interface RadioButtonCheckedListener {
		void onCheckedChanged(boolean isChecked);
	}
}
