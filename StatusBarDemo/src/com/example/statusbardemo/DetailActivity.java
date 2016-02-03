package com.example.statusbardemo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class DetailActivity extends BaseActivity {

	private View headLayout;
	private View backView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarVisiable(false);
		setContentView(R.layout.activity_detail);

		headLayout = findViewById(R.id.detail_head_layout);
		backView = findViewById(R.id.detail_back_view);

		int top = getStatusBarHeight();
		headLayout.setPadding(0, top, 0, 0);

		backView.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == backView) {
				finish();
			}
		}
	};
}
