package com.example.ecommercecineplanet.commons;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

public class TimeHelper {

    public static void startTimer(Activity activity, Class<?> targetActivity, long delayInMillis) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(activity, targetActivity);
            activity.startActivity(intent);
            activity.finish();
        }, delayInMillis);
    }
}
