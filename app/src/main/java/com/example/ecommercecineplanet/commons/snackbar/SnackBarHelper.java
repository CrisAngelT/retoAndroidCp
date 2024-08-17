package com.example.ecommercecineplanet.commons.snackbar;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.example.ecommercecineplanet.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarHelper {

    public void showSnackBar(View showView, String message) {
        Snackbar snackBarView = Snackbar.make(showView, message, BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        View view = snackBarView.getView();
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.color_primary));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        snackBarView.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackBarView.show();
    }

}
