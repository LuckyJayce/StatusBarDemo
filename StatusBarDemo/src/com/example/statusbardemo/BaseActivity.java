package com.example.statusbardemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseActivity extends AppCompatActivity {
	private FrameLayout group;
	private View statusBarView;
	private View content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		group = new FrameLayout(getApplicationContext());
		// 创建一个statusBarView 占位，填充状态栏的区域，以后设置状态栏背景效果其实是设置这个view的背景。
		group.addView(statusBarView = createStatusBar());
		super.setContentView(group, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// 设置activity的window布局从状态栏开始
		setTranslucentStatus(true);
	}

	/**
	 * 设置状态栏的占位的statusBarView是否显示
	 * 
	 * @param isVisiable
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
	public void setStatusBarVisiable(boolean isVisiable) {
		if (isVisiable) {
			statusBarView.setVisibility(View.VISIBLE);
		} else {
			statusBarView.setVisibility(View.GONE);
		}
		// 如果不显示状态栏的话 setFitsSystemWindows(false)，否则状态栏就是空白。
		// setFitsSystemWindows(true) 的效果就相当于 布局是保持状态栏下面（不包括状态栏的区域）开始计算
		// setFitsSystemWindows(false)
		// 的效果，因为setTranslucentStatus（true），所以布局是在整个屏幕开始
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			if (content != null) {
//				content.setFitsSystemWindows(isVisiable);
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
//					content.requestApplyInsets();
//				} else {
//					content.requestFitSystemWindows();
//				}
//			}
//		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void setTranslucentStatus(boolean isTranslucentStatus) {
		if (isTranslucentStatus) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
				localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
			}
			statusBarView.setVisibility(View.VISIBLE);
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
				localLayoutParams.flags = ((~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) & localLayoutParams.flags);
			}
			statusBarView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置状态栏的背景图片
	 * 
	 * @param resid
	 */
	public void setStatusBarBg(int resid) {
		statusBarView.setBackgroundResource(resid);
	}

	/**
	 * 设置状态栏的颜色
	 * 
	 * @param color
	 */
	public void setStatusBarColorBg(int color) {
		statusBarView.setBackgroundColor(color);
	}

	@Override
	public void setContentView(int layoutResID) {
		this.setContentView(getLayoutInflater().inflate(layoutResID, group, false));

	}

	@Override
	public void setContentView(View view) {
		this.setContentView(view, null);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void setContentView(View view, LayoutParams params) {
		content = view;
		if (params == null) {
			group.addView(view, 0);
		} else {
			group.addView(view, 0, params);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (statusBarView.getVisibility() == View.VISIBLE) {
				view.setFitsSystemWindows(true);
			} else {
				view.setFitsSystemWindows(false);
			}
		}
	}

	/**
	 * 获取状态的高度
	 * 
	 * @param context
	 * @return
	 */
	public int getStatusBarHeight() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			int result = 0;
			int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
			if (resourceId > 0) {
				result = getResources().getDimensionPixelSize(resourceId);
			}
			return result;
		}
		return 0;
	}

	/**
	 * 创建状态栏填充的 statusBarView
	 * 
	 * @return
	 */
	private View createStatusBar() {
		int height = getStatusBarHeight();
		View statusBarView = new View(this);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
		statusBarView.setBackgroundResource(R.color.primary);
		statusBarView.setLayoutParams(lp);
		return statusBarView;
	}
}
