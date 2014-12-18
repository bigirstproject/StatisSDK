package com.duowan.util;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.duowan.downloadmanagerdemo.R;


public class AnimationHelper {

	public static Animation createAnimDownToUpOut(Context context,
			AnimationListener listener) {
		Animation mAnimOut = AnimationUtils.loadAnimation(context,
				R.anim.common_down_to_up_in);
		if (mAnimOut != null && listener != null) {
			mAnimOut.setAnimationListener(listener);
		}
		return mAnimOut;
	}

	public static Animation createAnimDownToUpIn(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_down_to_up_out);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

	public static Animation createAnimUpToDownOut(Context context,
			AnimationListener listener) {
		Animation mAnimOut = AnimationUtils.loadAnimation(context,
				R.anim.common_up_to_down_out);
		if (mAnimOut != null && listener != null) {
			mAnimOut.setAnimationListener(listener);
		}
		return mAnimOut;
	}

	public static Animation createAnimUpToDownIn(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_up_to_down_in);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

	public static Animation createAnimRightToLeftIn(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_right_to_left_out);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

	public static Animation createAnimRightToLeftSecondLongIn(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_right_to_left_second_long_out);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

	public static Animation createAnimRightToLeftLongIn(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_right_to_left_long_out);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

	public static Animation createAnimLeftToRightOut(Context context,
			AnimationListener listener) {
		Animation mAnimIn = AnimationUtils.loadAnimation(context,
				R.anim.common_left_to_right_out);
		if (mAnimIn != null && listener != null) {
			mAnimIn.setAnimationListener(listener);
		}
		return mAnimIn;
	}

}
