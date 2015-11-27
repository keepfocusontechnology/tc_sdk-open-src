package com.gavegame.tiancisdk.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {

	public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int viewWidth = 0;
		int viewHeight = 0;
		MarginLayoutParams params;
		for (int i = 0; i < childCount; i++) {
			int left = 0;
			int top = 0;
			int right = 0;
			int bottom = 0;
			View childView = getChildAt(i);
			params = (MarginLayoutParams) childView.getLayoutParams();
			viewWidth = childView.getMeasuredWidth();
			viewHeight = childView.getMeasuredHeight();
			if (i == 0) {
				left = params.leftMargin;
				top = getHeight() / 2 - viewHeight / 2;
				right = viewWidth + params.leftMargin;
				bottom = getHeight() / 2 + viewHeight / 2;
			} else if (i == 1) {
				left = getWidth() - (getWidth() - (getChildAt(0).getMeasuredWidth() + params.rightMargin + params.leftMargin));
				top = 0;
				right = getWidth() - params.rightMargin;
				bottom = getHeight();
			}
			childView.layout(left, top, right, bottom);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int width = 0;
		int height = 0;

		int childCount = getChildCount();
		for (int i = 0; i < childCount; ++i) {
			View v = getChildAt(i);
			if (i == 1) {
				MarginLayoutParams cParams = (MarginLayoutParams) v
						.getLayoutParams();
				width += cParams.leftMargin + cParams.rightMargin;
				height += cParams.topMargin + cParams.bottomMargin;
			}
			width += v.getMeasuredWidth();
			height += v.getMeasuredHeight();
		}

		/**
		 * 如果是wrap_content设置为我们计算的值 否则：直接设置为父容器计算的值
		 */
		setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
				: width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
				: height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect rect = new Rect();
		Paint paint = new Paint();
		canvas.drawRect(rect, paint);
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
}
