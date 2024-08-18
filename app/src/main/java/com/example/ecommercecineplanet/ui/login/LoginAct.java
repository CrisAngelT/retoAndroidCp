package com.example.ecommercecineplanet.ui.login;

import static com.example.ecommercecineplanet.commons.KeyExtras.KEY_EXTRA_PREMIERE;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.R;
import com.example.ecommercecineplanet.commons.dialog.DialogHelper;
import com.example.ecommercecineplanet.commons.snackbar.SnackBarHelper;
import com.example.ecommercecineplanet.databinding.ActivityLoginBinding;
import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;
import com.example.ecommercecineplanet.ui.base.BaseActivity;
import com.example.ecommercecineplanet.ui.base.BaseViewModel;
import com.example.ecommercecineplanet.ui.candyshop.CandyShopAct;

public class LoginAct extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private Dialog loadingDialog;
    private View viewButton;
    private SnackBarHelper snackBarHelper;
    private DialogHelper dialogHelper;

    @Override
    protected void initViewBinding() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
                EdgeToEdge.enable(this);
        binding.imgArrow.setOnClickListener(view -> finish());
        loadingDialog = DialogHelper.dialogLoadingApi(this);
        dialogHelper = new DialogHelper();
        snackBarHelper = new SnackBarHelper();
        LoginProviderFirebase loginProvider = new LoginProviderFirebase();
        loginViewModel = new ViewModelProvider(this, new BaseViewModel(null, null, null, loginProvider)).get(LoginViewModel.class);
        binding.btnContinueGuest.setOnClickListener(view -> goCandyStore(false));
        binding.btnAccess.setOnClickListener(view -> {
            viewButton = view;
            validationSession();
        });
        getExtras();
        if (!new LoginProviderFirebase().getEmail().isEmpty()){
            binding.edtEmail.setText(new LoginProviderFirebase().getEmail());

        }
    }

    public String getExtras() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                return extras.getString(KEY_EXTRA_PREMIERE, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected void observerViewModel() {
        loginViewModel.loginSuccess().observe(this, aBoolean -> {
            loadingDialog.dismiss();
            if (Boolean.TRUE.equals(aBoolean)) {
                dialogHelper.dialogGeneral(
                        this,
                        "Bienvenido!",
                        (dialog, which) -> goCandyStore(true),
                        false,
                        (dialog, which) -> {

                        }
                );

            } else {
                snackBarHelper.showSnackBar(viewButton, getString(R.string.error_login));
            }

        });
    }

    public void goCandyStore(Boolean isLogin) {
        startActivity(new Intent(this, CandyShopAct.class).putExtra(KEY_EXTRA_PREMIERE, getExtras()));
        if (Boolean.TRUE.equals(isLogin)){
            finish();
        }

    }

    private void validationSession() {
        if (binding.edtEmail.getText().toString().isEmpty()) {
            snackBarHelper.showSnackBar(viewButton, "Ingrese su correo");
        } else if (binding.edtPassword.getText().toString().isEmpty()) {
            snackBarHelper.showSnackBar(viewButton, "Ingrese su contraseña");

        } else {
            loadingDialog.show();
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            loginViewModel.login(email, password);
        }

    }


}