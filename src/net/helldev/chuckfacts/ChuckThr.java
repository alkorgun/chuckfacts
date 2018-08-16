package net.helldev.chuckfacts;

import android.content.Context;

public class ChuckThr extends Thread {

	private Context context;
	protected static String fact = null;

	public ChuckThr(Context context) {
		super();
		this.context = context;
	}

	public String getFact() {
		return fact;
	}

	@Override
	public void run() {
		fact = ChuckParse.getFact(context);
	}

}
