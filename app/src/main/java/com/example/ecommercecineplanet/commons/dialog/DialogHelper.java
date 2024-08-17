package com.example.ecommercecineplanet.commons.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.ecommercecineplanet.R;
import com.example.ecommercecineplanet.databinding.DialogGeneralBinding;
import com.example.ecommercecineplanet.databinding.DialogShowCompleteOrderBinding;

import java.util.Objects;

public class DialogHelper {

    public static Dialog dialogLoadingApi(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        Dialog modal = new Dialog(activity);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_loading_api, null);
        modal.setContentView(view);
        Window window = modal.getWindow();
        if (window != null) {
            window.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            window.setWindowAnimations(R.style.DialogAnimation);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        modal.setCancelable(false);
        return modal;
    }


    public void dialogGeneral(Activity activity,
                              String description,
                              DialogInterface.OnClickListener listener,
                              boolean isVisibleButtonCancel,
                              DialogInterface.OnClickListener listenerCancel) {
        DialogGeneralBinding bindingAlertUniversal = DialogGeneralBinding.inflate(activity.getLayoutInflater());
        Dialog modal = new Dialog(activity);
        modal.setContentView(bindingAlertUniversal.getRoot());
        Objects.requireNonNull(modal.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        modal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bindingAlertUniversal.descriptionDialog.setText(description);
        bindingAlertUniversal.btnContinue.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(modal, DialogInterface.BUTTON_POSITIVE);
            }
            modal.dismiss();
        });
        bindingAlertUniversal.btnGone.setOnClickListener(v -> {
            if (listenerCancel != null) {
                listenerCancel.onClick(modal, DialogInterface.BUTTON_POSITIVE);
            }
            modal.dismiss();
        });
        if (isVisibleButtonCancel) {
            bindingAlertUniversal.btnGone.setVisibility(View.VISIBLE);
        }
        if (modal.getWindow() != null) {
            modal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        modal.setCancelable(false);
        modal.show();

    }

    public void dialogSuccessfullyOrder(Activity activity, DialogInterface.OnClickListener listener) {
        DialogShowCompleteOrderBinding dialogShowCompleteOrderBinding = DialogShowCompleteOrderBinding.inflate(activity.getLayoutInflater());
        Dialog modal = new Dialog(activity);
        modal.setContentView(dialogShowCompleteOrderBinding.getRoot());
        Objects.requireNonNull(modal.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        modal.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogShowCompleteOrderBinding.btnSuccessfull.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(modal, DialogInterface.BUTTON_POSITIVE);
            }
            modal.dismiss();
        });
        if (modal.getWindow() != null) {
            modal.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        modal.setCancelable(false);
        modal.show();

    }
}
