package com.example.VerticalMarqueeTextView;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Example extends Activity {

	private TextView textView;
	// Create an instance of the VerticalMarqueeTextView class
	// to be used with the above TextView.
	private VerticalMarqueeTextView VMTV;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Instantiate the TextView to be used with VMTV.
		textView = (TextView) findViewById(R.id.vmTextView);
		// Set the TextViews movement method so that it can scroll.
		textView.setMovementMethod(new ScrollingMovementMethod());

		// Instantiate the VMTV passing this as the Activity and the
		// TextView that you wish to marquee.
		VMTV = new VerticalMarqueeTextView(this, textView);

		// Alternately you may also pass in the (long) duration between scroll
		// moves and the (int) pixelYOffSet amount that you wish to scroll by.
		// VMTV = new VerticalMarqueeTextView(this, textView, duration,
		// pixelYOffSet);

		// Optionally pause the marquee to wait for the Activity to start.
		VMTV.pauseMarquee();

	}

	@Override
	protected void onResume() {

		// Start or restart the Marquee if paused.
		if (VMTV.isPaused()) {
			VMTV.resumeMarquee();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {

		// Pause the Marquee when the Activity pauses.
		VMTV.pauseMarquee();
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		VMTV.stopMarquee();
		super.onDestroy();
	}

}