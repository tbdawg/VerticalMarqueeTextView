package com.example.VerticalMarqueeTextView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.TextView;

public class VerticalMarqueeTextView extends TextView {

	private boolean isUserScrolling, isPaused, stop;
	private boolean isNotDrawn = true;
	private final Activity activity;
	// private final TextView textView;
	private long duration;
	private int pixelYOffSet;

	/**
	 * 
	 * Creates a vertically auto scrolling marquee of a TextView within an
	 * Activity. The (long) duration in milliseconds between calls to the next
	 * scrollBy(0, pixelYOffSet). Defaults to 65L. The (int) amount of Y pixels
	 * to scroll by defaults to 1.
	 * 
	 */
	public VerticalMarqueeTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		this.activity = (Activity) context;
		// this.textView = this;

		init();
	}

	/**
	 * 
	 * Creates a vertically auto scrolling marquee of a TextView within an
	 * Activity. The (long) duration in milliseconds between calls to the next
	 * scrollBy(0, pixelYOffSet). Defaults to 65L. The (int) amount of Y pixels
	 * to scroll by defaults to 1.
	 * 
	 */
	public VerticalMarqueeTextView(Context context, AttributeSet attrs) {

		super(context, attrs);

		this.activity = (Activity) context;
		// this.textView = this;

		init();
	}

	/**
	 * 
	 * Creates a vertically auto scrolling marquee of a TextView within an
	 * Activity. The (long) duration in milliseconds between calls to the next
	 * scrollBy(0, pixelYOffSet). Defaults to 65L. The (int) amount of Y pixels
	 * to scroll by defaults to 1.
	 * 
	 */
	public VerticalMarqueeTextView(Context context) {
		super(context);

		this.activity = (Activity) context;
		// this.textView = this;

		init();
	}

	/**
	 * Initialize fields and start the marquee.
	 */
	private void init() {
		setDuration(65l);
		setPixelYOffSet(1);

		isUserScrolling = isPaused = stop = false;

		startMarquee();

	}

	/**
	 * @return Returns the (long) duration in milliseconds between calls to the
	 *         next scrollBy(0, pixelYOffSet).
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            Sets the (long) duration in milliseconds between calls to the
	 *            next scrollBy(0, pixelYOffSet). Defaults to 65L if value is
	 *            less than or equal to 0.
	 */
	public void setDuration(long duration) {
		if (duration <= 0) {
			this.duration = 65l;
		} else {
			this.duration = duration;
		}

	}

	/**
	 * @return Returns the (int) amount of Y pixels to scroll by.
	 */
	public int getPixelYOffSet() {
		return pixelYOffSet;
	}

	/**
	 * @param pixelYOffSet
	 *            Sets the (int) amount of Y pixels to scroll by. Defaults to 1
	 *            if value is less.
	 */
	public void setPixelYOffSet(int pixelYOffSet) {
		if (pixelYOffSet < 1) {
			this.pixelYOffSet = 1;
		} else {
			this.pixelYOffSet = pixelYOffSet;
		}
	}

	/**
	 * Starts the marquee. May only be called once.
	 */
	private void startMarquee() {
		new AutoScrollTextView().execute();
	}

	/**
	 * Stop the marquee. Cannot be restarted.
	 */
	public void stopMarquee() {
		stop = true;
	}

	/**
	 * Pauses the marquee.
	 */
	public void pauseMarquee() {
		isPaused = true;
	}

	/**
	 * Resumes marquee from a call to pauseMarquee();
	 */
	public void resumeMarquee() {
		isPaused = false;
	}

	/**
	 * @return Returns true if paused (including if paused do to user manually
	 *         scrolling), false otherwise.
	 */
	public boolean isPaused() {
		if (isPaused || isUserScrolling) {
			return true;
		}
		return false;
	}

	private class AutoScrollTextView extends AsyncTask<Void, Void, Void> {

		private int pixelCount;

		@Override
		protected Void doInBackground(Void... params) {

			// Check to see if the textView has been drawn to get proper sizing.
			while (textViewNotDrawn()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Changing stop to true will permanently cancel autoscrolling.
			// Cannot be restarted.
			while (!stop) {

				// Allows scrolling to resume after textView has been released.
				if (!(VerticalMarqueeTextView.this).isPressed()
						&& isUserScrolling && !isPaused) {
					isUserScrolling = false;
				}

				while (!isUserScrolling && !stop && !isPaused) {

					// Sleep duration amount between scrollBy pixelYOffSet
					try {
						Thread.sleep(duration);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// If the user is manually scrolling pause the auto
							// scrolling marquee
							if ((VerticalMarqueeTextView.this).isPressed()) {

								isUserScrolling = true;

							} else { // Otherwise auto scroll marquee

								// if textView has reached or exceeded the last
								// Y pixel scroll back to top
								if ((VerticalMarqueeTextView.this).getScrollY() >= pixelCount) {

									(VerticalMarqueeTextView.this).scrollTo(0,
											0);

								} else { // Otherwise scroll by the pixelYOffSet

									(VerticalMarqueeTextView.this).scrollBy(0,
											pixelYOffSet);
								}

								(VerticalMarqueeTextView.this).invalidate();
							}

						}
					});
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		private boolean textViewNotDrawn() {

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// Checks to see if TextView has been drawn.
					// In theory line count should be greater than 0 if drawn.
					if ((VerticalMarqueeTextView.this).getLineCount() > 0) {
						// Calculate the total pixel height that needs to be
						// scrolled.
						// May need additional calculations if there is
						// additional padding.
						pixelCount = (VerticalMarqueeTextView.this)
								.getLineHeight()
								* (VerticalMarqueeTextView.this).getLineCount();
						isNotDrawn = false;
					}

				}
			});

			return isNotDrawn;
		}

	}

}
