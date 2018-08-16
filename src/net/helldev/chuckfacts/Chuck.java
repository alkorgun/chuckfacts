package net.helldev.chuckfacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Chuck extends Activity {

	protected TextView output;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		output = (TextView) findViewById(R.id.show_fact);
		final Button push = (Button) findViewById(R.id.get_fact);
		push.setOnClickListener(
			new Button.OnClickListener() {
				@Override
				public void onClick(View args) {
					updateFact();
				}
			}
		);
		updateFact();
	}

	public void updateFact() {
		ChuckThr thr = new ChuckThr(this);
		thr.start();
		while (thr.getFact() == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException exc) {
				continue;
			}
		}
		output.setText(thr.getFact());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.about:
				output.setText(getString(R.string.about_app));
				break;
			case R.id.abort:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
