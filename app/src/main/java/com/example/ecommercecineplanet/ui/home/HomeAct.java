package com.example.ecommercecineplanet.ui.home;

import static com.example.ecommercecineplanet.commons.KeyExtras.KEY_EXTRA_PREMIERE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommercecineplanet.EcommerceCineplanetApp;
import com.example.ecommercecineplanet.commons.BeanMapper;
import com.example.ecommercecineplanet.commons.Util;
import com.example.ecommercecineplanet.commons.dialog.DialogHelper;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.databinding.ActivityHomeBinding;
import com.example.ecommercecineplanet.databinding.ActivityMainMenuBinding;
import com.example.ecommercecineplanet.domain.provider.LoginProviderFirebase;
import com.example.ecommercecineplanet.domain.usecase.GetPremiersUseCase;
import com.example.ecommercecineplanet.ui.candyshop.CandyShopAct;
import com.example.ecommercecineplanet.ui.login.LoginAct;
import com.example.ecommercecineplanet.ui.base.BaseActivity;
import com.example.ecommercecineplanet.ui.base.BaseViewModel;
import com.example.ecommercecineplanet.ui.home.adapter.PremiersAdapter;
import com.example.ecommercecineplanet.ui.splash.SplashAct;

import java.util.ArrayList;

public class HomeAct extends BaseActivity {
    private HomeViewModel homeViewModel;
    private PremiersAdapter premiersAdapter;
    private Handler handler;
    private Runnable runnable;
    private int currentItem = 0;
    private ActivityMainMenuBinding menuBinding;
    private Dialog loadingDialog;
    private final DialogHelper dialogHelper = new DialogHelper();

    @Override
    protected void initViewBinding() {
        com.example.ecommercecineplanet.databinding.ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        menuBinding = binding.activityMenu;
        loadingDialog = DialogHelper.dialogLoadingApi(this);
        loadingDialog.show();
        EcommerceCineplanetApp app = (EcommerceCineplanetApp) getApplication();
        GetPremiersUseCase getPremiersUseCase = app.getAppModule().provideGetPremiersUseCase(app.getAppModule().providePremierRepository(app.getAppModule().provideApiService()));
        homeViewModel = new ViewModelProvider(this, new BaseViewModel(getPremiersUseCase, null, null, null)).get(HomeViewModel.class);
        premiersAdapter = new PremiersAdapter(new ArrayList<>(), homeViewModel);
        binding.rcvPremiers.setAdapter(premiersAdapter);
        binding.rcvPremiers.setHasFixedSize(false);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem++;
                if (currentItem >= premiersAdapter.getItemCount()) {
                    currentItem = 0;
                }
                binding.rcvPremiers.smoothScrollToPosition(currentItem);
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);
        binding.imgArrow.setOnClickListener(view -> binding.drawerLayout.openDrawer(binding.navMenu));
        menuBinding.menuHome.setOnClickListener(view -> new Util().closeDrawer(binding.drawerLayout));
        menuBinding.menuLogin.setOnClickListener(view -> {
            new Util().closeDrawer(binding.drawerLayout);
            startActivity(new Intent(HomeAct.this, LoginAct.class));
        });
        menuBinding.menuCandyStore.setOnClickListener(view -> {
            new Util().closeDrawer(binding.drawerLayout);
            startActivity(new Intent(HomeAct.this, CandyShopAct.class));
        });

        menuBinding.menuLogout.setOnClickListener(view -> {
            new LoginProviderFirebase().logout();
            startActivity(new Intent(HomeAct.this, SplashAct.class));
            finish();
        });
        binding.cardLogin.setOnClickListener(view -> startActivity(new Intent(HomeAct.this, LoginAct.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void observerViewModel() {
        homeViewModel.premierResponseLiveData().observe(this, this::paintData);
        homeViewModel.touchPremiereLiveData().observe(this, this::goActivityLogin);
    }

    public void paintData(PremierResponse premierResponse) {
        if (premierResponse != null && premierResponse.getPremieres() != null) {
            premiersAdapter.updateData(premierResponse.getPremieres());
        } else {
            dialogHelper.dialogGeneral(this, // Activity context
                    "Ocurrio un error,intente de nuevo por favor.", (dialog, which) -> {
                        homeViewModel.loadPremieres();
                        loadingDialog.show();
                    }, false, (dialog, which) -> {

                    });
        }
        loadingDialog.dismiss();
    }

    public void goActivityLogin(PremierResponse.Premiere premiere) {
        if (new LoginProviderFirebase().existSession()) {
            startActivity(new Intent(this, CandyShopAct.class).putExtra(KEY_EXTRA_PREMIERE, BeanMapper.toJson(premiere)));

        } else {
            startActivity(new Intent(this, LoginAct.class).putExtra(KEY_EXTRA_PREMIERE, BeanMapper.toJson(premiere)));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        menuBinding.txvUser.setText(new LoginProviderFirebase().getEmail());
        if (!new LoginProviderFirebase().existSession()) {
            menuBinding.menuLogout.setVisibility(View.GONE);
        }else {
            menuBinding.menuLogout.setVisibility(View.VISIBLE);
        }
    }
}