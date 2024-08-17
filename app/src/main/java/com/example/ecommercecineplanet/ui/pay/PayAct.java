package com.example.ecommercecineplanet.ui.pay;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.EcommerceCineplanetApp;
import com.example.ecommercecineplanet.R;
import com.example.ecommercecineplanet.commons.Util;
import com.example.ecommercecineplanet.commons.card.UtilCreditCard;
import com.example.ecommercecineplanet.commons.dialog.DialogHelper;
import com.example.ecommercecineplanet.commons.snackbar.SnackBarHelper;
import com.example.ecommercecineplanet.data.model.request.CompleteRequest;
import com.example.ecommercecineplanet.databinding.ActivityPayBinding;
import com.example.ecommercecineplanet.domain.usecase.GetCompleteUseCase;
import com.example.ecommercecineplanet.ui.base.BaseActivity;
import com.example.ecommercecineplanet.ui.base.BaseViewModel;
import com.example.ecommercecineplanet.ui.splash.SplashAct;

import java.util.Objects;

public class PayAct extends BaseActivity {
    private ActivityPayBinding binding;
    private PayViewModel payViewModel;
    private Dialog loadingDialog;
    private AnimatorSet frontAnimationation;
    private AnimatorSet backAnimation;
    private boolean isFront;
    private final DialogHelper dialogHelper = new DialogHelper();

    @Override
    protected void initViewBinding() {
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = DialogHelper.dialogLoadingApi(this);
        animationCard();
        binding.edtExpirationDate.addTextChangedListener(UtilCreditCard.formatExpirationDate(binding.edtExpirationDate));
        binding.edtNumberCard.addTextChangedListener(UtilCreditCard.formatCreditCard(binding.edtNumberCard));
        EcommerceCineplanetApp app = (EcommerceCineplanetApp) getApplication();
        GetCompleteUseCase getCompleteUseCase = app.getAppModule().provideGetCOmpletUseCase(
                app.getAppModule().providePremierRepository(app.getAppModule().provideApiService())
        );
        payViewModel = new ViewModelProvider(this, new BaseViewModel(null, null, getCompleteUseCase,null)).get(PayViewModel.class);
        initializeTextWatcher();
        binding.edtNumberCard.requestFocus();
        binding.btnPay.setOnClickListener(view -> {
            if (validateCreditCard(view)) {
                loadingDialog.show();
                payViewModel.sendCompleteCard(
                        new CompleteRequest(
                                Objects.requireNonNull(binding.edtNameCard.getText()).toString(),
                                Objects.requireNonNull(binding.edtEmail.getText()).toString(),
                                Objects.requireNonNull(binding.edtDocumentNumber.getText()).toString(),
                                ""
                        )
                );
            }
        });
    }

    @Override
    protected void observerViewModel() {
        payViewModel.completeResponseLiveData().observe(this, completeResponse -> {
            if (!completeResponse.getResulCode().isEmpty()) {
                dialogHelper.dialogSuccessfullyOrder(PayAct.this, (dialog, which) -> {
                        startActivity(new Intent(PayAct.this, SplashAct.class));
                        finish();
                        }
                );
            }
            loadingDialog.dismiss();
        });
    }

    private boolean validateCreditCard(View view) {
        String cardNumber = Objects.requireNonNull(binding.edtNumberCard.getText()).toString().replace(" ", "");
        String cardMonth;
        String cardYear;
        String value = Objects.requireNonNull(binding.edtExpirationDate.getText()).toString().trim();
        String cardName = Objects.requireNonNull(binding.edtNameCard.getText()).toString();
        String cardCcv = Objects.requireNonNull(binding.edtCvvCard.getText()).toString();

        if (value.length() <= 3) {
            cardMonth = value;
            cardYear = "";
        } else {
            String[] parts = value.split("/");
            cardMonth = parts[0];
            cardYear = "20" + parts[1];
        }

        if (!cardNumber.isEmpty() && !cardMonth.isEmpty() && !cardYear.isEmpty() && !cardName.isEmpty() && !cardCcv.isEmpty()) {
            if (cardCcv.length() == 3) {
                if (new Util().checkCard(cardNumber)) {
                    if (!new UtilCreditCard().validateDate(cardMonth, cardYear, view)) {
                        return false;
                    }
                } else {
                    new SnackBarHelper().showSnackBar(view, "No es una tarjeta valida");
                    return false;
                }
            } else {
                new SnackBarHelper().showSnackBar(view, "CCV debe tener 3 dígitos");
                return false;
            }
        } else {
            new SnackBarHelper().showSnackBar(view, "Complete todos los campos de la tarjeta");
            return false;
        }

        String email = Objects.requireNonNull(binding.edtEmail.getText()).toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new SnackBarHelper().showSnackBar(view, "Ingrese un correo electrónico válido");
            return false;
        }

        String selectedDocumentType = binding.spinnerDocumentType.getSelectedItem().toString();
        if (selectedDocumentType.isEmpty()) {
            new SnackBarHelper().showSnackBar(view, "Seleccione un tipo de documento");
            return false;
        }

        String documentNumber = Objects.requireNonNull(binding.edtDocumentNumber.getText()).toString();
        if (documentNumber.isEmpty()) {
            new SnackBarHelper().showSnackBar(view, "Ingrese el número de documento");
            return false;
        }

        return true;
    }

    @SuppressLint("ResourceType")
    private void animationCard() {
        float scale = getResources().getDisplayMetrics().density;
        binding.cardFrom.setCameraDistance(8000 * scale);
        binding.cardBack.setCameraDistance(8000 * scale);

        frontAnimationation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.front_animator);
        backAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.back_animator);

        binding.edtCvvCard.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && isFront) {
                frontAnimationation.setTarget(binding.cardFrom);
                backAnimation.setTarget(binding.cardBack);
                frontAnimationation.start();
                backAnimation.start();
                isFront = false;
            }
        });

        binding.edtNumberCard.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isFront) {
                frontAnimationation.setTarget(binding.cardBack);
                backAnimation.setTarget(binding.cardFrom);
                frontAnimationation.start();
                backAnimation.start();
                isFront = true;
            }
        });

        binding.edtNameCard.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isFront) {
                frontAnimationation.setTarget(binding.cardBack);
                backAnimation.setTarget(binding.cardFrom);
                frontAnimationation.start();
                backAnimation.start();
                isFront = true;
            }
        });

        binding.edtExpirationDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isFront) {
                frontAnimationation.setTarget(binding.cardBack);
                backAnimation.setTarget(binding.cardFrom);
                backAnimation.start();
                backAnimation.start();
                isFront = true;
            }
        });
    }

    private void setupTextWatcher(EditText editText, final TextView textView, final boolean updateAnimation, final AnimatorSet frontAnimation, final AnimatorSet backAnimation) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = Objects.requireNonNull(editText.getText()).toString();
                textView.setText(text);

                if (updateAnimation && text.length() >= 3) {
                    if (!isFront) {
                        frontAnimation.setTarget(binding.cardBack);
                        backAnimation.setTarget(binding.cardFrom);
                        frontAnimation.start();
                        backAnimation.start();
                        isFront = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    private void initializeTextWatcher() {
        setupTextWatcher(binding.edtNumberCard, binding.txvNumberCard, false, null, null);
        setupTextWatcher(binding.edtNameCard, binding.txvName, false, null, null);
        setupTextWatcher(binding.edtExpirationDate, binding.txvExpirationDate, false, null, null);
        setupTextWatcher(binding.edtCvvCard, binding.edtShowCcvCard, true, frontAnimationation, backAnimation);
    }
}