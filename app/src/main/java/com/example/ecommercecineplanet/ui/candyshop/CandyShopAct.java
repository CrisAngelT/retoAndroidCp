package com.example.ecommercecineplanet.ui.candyshop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.EcommerceCineplanetApp;
import com.example.ecommercecineplanet.R;
import com.example.ecommercecineplanet.commons.BeanMapper;
import com.example.ecommercecineplanet.commons.GlobalAmountManager;
import com.example.ecommercecineplanet.commons.dialog.DialogHelper;
import com.example.ecommercecineplanet.commons.snackbar.SnackBarHelper;
import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.databinding.ActivityCandyShopBinding;
import com.example.ecommercecineplanet.domain.usecase.GetCandyStoreUseCase;
import com.example.ecommercecineplanet.ui.base.BaseActivity;
import com.example.ecommercecineplanet.ui.base.BaseViewModel;
import com.example.ecommercecineplanet.ui.candyshop.adapter.CandyStoreAdapter;
import com.example.ecommercecineplanet.ui.pay.PayAct;

import java.util.ArrayList;
import java.util.List;

public class CandyShopAct extends BaseActivity {
    private ActivityCandyShopBinding binding;
    private CandyStoreViewModel candyStoreViewModel;
    private CandyStoreAdapter candyStoreAdapter;
    PremierResponse.Premiere premiereData;
    private Double total;
    private Dialog loadingDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViewBinding() {
        binding = ActivityCandyShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = DialogHelper.dialogLoadingApi(this);

        loadingDialog.show();
        EcommerceCineplanetApp app = (EcommerceCineplanetApp) getApplication();
        GetCandyStoreUseCase getCandyStoreUseCase = app.getAppModule().provideGetCandyStoreUseCase(
                app.getAppModule().providePremierRepository(app.getAppModule().provideApiService())
        );
        candyStoreViewModel = new ViewModelProvider(this, new BaseViewModel(null, getCandyStoreUseCase, null, null)).get(CandyStoreViewModel.class);
        candyStoreAdapter = new CandyStoreAdapter(new ArrayList<>());
        binding.rcvCandyStore.setAdapter(candyStoreAdapter);
        binding.rcvCandyStore.setHasFixedSize(false);
        GlobalAmountManager globalAmountManager = GlobalAmountManager.getInstance();
        globalAmountManager.getTotalAmountLiveData().observe(this, totalAmount -> {
            total = totalAmount;
            binding.txvMountTotal.setText("S/ " + (totalAmount));
            updateViewWithTotal(totalAmount);

        });
        getExtras();
        binding.btnContinue.setOnClickListener(view -> {
            if (total.equals(0.0)) {
                new SnackBarHelper().showSnackBar(view, "Elija un producto por favor.");

            } else {
                goPay();
            }

        });
        binding.imgArrow.setOnClickListener(view -> finish());
    }

    public void goPay() {
        startActivity(new Intent(this, PayAct.class));
    }

    public void getExtras() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                premiereData = BeanMapper.fromJson(extras.getString("prueba2"), PremierResponse.Premiere.class);
                paintData(premiereData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void observerViewModel() {
        candyStoreViewModel.candyStoreLiveData().observe(this, this::paintAdapter);
    }

    public void paintAdapter(CandyStoreResponse candyStoreResponse) {
        if (candyStoreResponse != null) {
            List<CandyStoreResponse.Items> items = candyStoreResponse.getItems();
            for (int i = 0; i < items.size(); i++) {
                CandyStoreResponse.Items item = items.get(i);
                item.setImageIndex(i);
            }
            loadingDialog.dismiss();
            candyStoreAdapter.updateData(items);
        }
    }

    public void paintData(PremierResponse.Premiere premiere) {
        binding.txvTitlePremier.setText(premiere.getDescription());
    }
    public void updateViewWithTotal(Double totalOrder) {
        if (totalOrder.equals(0.0)) {
            binding.lytMountTotal.setBackgroundColor(ContextCompat.getColor(this,R.color.color_plom));
            binding.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_plom));
            binding.btnContinue.setEnabled(false);
        } else {
            binding.lytMountTotal.setBackgroundColor(ContextCompat.getColor(this,R.color.color_primary));
            binding.btnContinue.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_primary));
            binding.btnContinue.setEnabled(true);

        }
    }


}