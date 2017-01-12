/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Contains various methods needed by the Application,
 */
public class Tools {

    private static final int PHONE_NORMAL_SCREEN_DENSITY = 3;

    /**
     * Checks internet availability
     *
     * @param context The context of the Activity ( or attached Fragment) that requests
     *                the online state verification
     * @Retuns true if the device is online.
     */
    public static boolean isInternetAvailaible(Context context) throws IOException {

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * Calculates the sample size needed to create a manual bitmap thumb.
     *
     * @param maxSize the max wanted size fot the image
     * @param options Image options
     *
     * @Returns the sample size of a bitmap according to the provided MAX_SIZE.
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int maxSize) {
        int height = options.outHeight;
        int inSampleSize = 1;
        while (((double) (options.outWidth * height)) * (1.0d / Math.pow((double) inSampleSize, 2.0d)) > ((double) maxSize)) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /**
     * @param context The context of the Activity ( or attached Fragment) wants to get the Screen difference
     * @Returns true if the device is a tablet.
     */
    public static boolean isTabletScreen(Context context) {
        return PHONE_NORMAL_SCREEN_DENSITY <= (context.getResources().getConfiguration().screenLayout & 0xF);
    }

}
