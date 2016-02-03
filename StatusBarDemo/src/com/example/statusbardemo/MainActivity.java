package com.example.statusbardemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	private Button nextPageButton;
	private Button changeBgButton;
	private Button changeColorButton;
	private View headLayuot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nextPageButton = (Button) findViewById(R.id.main_nextpage_Button);
		changeBgButton = (Button) findViewById(R.id.main_changebg_button);
		changeColorButton = (Button) findViewById(R.id.main_changecolor_button);
		headLayuot = findViewById(R.id.main_head_layout);

		nextPageButton.setOnClickListener(onClickListener);
		changeBgButton.setOnClickListener(onClickListener);
		changeColorButton.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		private int colors[] = { Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Color.parseColor("#FF5722"), Color.parseColor("#4CAF50") };
		private int colorIndex;

		@Override
		public void onClick(View v) {
			if (v == nextPageButton) {
				startActivity(new Intent(getApplicationContext(), DetailActivity.class));
			} else if (v == changeColorButton) {
				int color = colors[(colorIndex++) % colors.length];
				setStatusBarColorBg(color);
				headLayuot.setBackgroundColor(color);
			} else if (v == changeBgButton) {
				setStatusBarBg(R.drawable.a_1);
				headLayuot.setBackgroundResource(R.drawable.a_2);
			}
		}
	};

}
