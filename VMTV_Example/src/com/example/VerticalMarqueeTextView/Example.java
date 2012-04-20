package com.example.VerticalMarqueeTextView;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

public class Example extends Activity {

	// Create an instance of the VerticalMarqueeTextView class
	// to be used with the above TextView.
	private VerticalMarqueeTextView VMTV;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Instantiate the VMTV.
		VMTV = (VerticalMarqueeTextView) findViewById(R.id.vmTextView);

		// Set the VMTV movement method so that it can scroll.
		VMTV.setMovementMethod(new ScrollingMovementMethod());

		// Alternately you may also pass in the (long) duration between scroll
		// moves and the (int) pixelYOffSet amount that you wish to scroll by.
		// Using the following method calls:
		// VMTV.setDuration(duration);
		// VMTV.setPixelYOffSet(pixelYOffSet);

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