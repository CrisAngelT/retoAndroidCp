package com.example.ecommercecineplanet.ui.candyshop.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercecineplanet.commons.GlobalAmountManager;
import com.example.ecommercecineplanet.commons.RecyclerItemListener;
import com.example.ecommercecineplanet.commons.images.ImageResources;
import com.example.ecommercecineplanet.data.model.CandyStoreResponse;
import com.example.ecommercecineplanet.databinding.ItemCandyshopBinding;
import com.example.ecommercecineplanet.ui.candyshop.CandyStoreViewModel;

import java.util.List;

public class CandyStoreAdapter extends RecyclerView.Adapter<CandyStoreAdapter.MyOrderHolder> {

    private List<CandyStoreResponse.Items> listItems;

    public CandyStoreAdapter(List<CandyStoreResponse.Items> listPremiere) {
        this.listItems = listPremiere;
    }
    
    public static class MyOrderHolder extends RecyclerView.ViewHolder {
        private final ItemCandyshopBinding binding;
        public MyOrderHolder(ItemCandyshopBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(CandyStoreResponse.Items item, RecyclerItemListener<CandyStoreResponse.Items> itemListener) {
        binding.txvName.setText(item.getName());
        binding.txvDescription.setText(item.getDescription());
        binding.txvPrice.setText("S/ " + item.getPrice());
        binding.getRoot().setOnClickListener(v -> itemListener.onItemSelected(item));
        binding.buttonPlus.setOnClickListener(v -> {
                int currentCount = Integer.parseInt(binding.textCount.getText().toString());
                double price = Double.parseDouble(item.getPrice());
                currentCount++;
                double newTotalPrice = price * currentCount;
                int previousCount = currentCount - 1;
                double previousTotalPrice = price * previousCount;
                GlobalAmountManager.getInstance().addAmount(newTotalPrice - previousTotalPrice);
                binding.textCount.setText(String.valueOf(currentCount));
                item.setQuantity(currentCount);
            });
        binding.buttonMinus.setOnClickListener(v -> {
                int currentCount = Integer.parseInt(binding.textCount.getText().toString());
                if (currentCount > 0) {
                    double price = Double.parseDouble(item.getPrice());
                    currentCount--;
                    int previousCount = currentCount + 1;
                    double previousTotalPrice = price * previousCount;
                    double newTotalPrice = price * currentCount;
                    GlobalAmountManager.getInstance().subtractAmount(previousTotalPrice - newTotalPrice);
                    binding.textCount.setText(String.valueOf(currentCount));
                    item.setQuantity(currentCount);
                }
            });
            int imageIndex = item.getImageIndex();
            if (imageIndex >= 0 && imageIndex < ImageResources.IMAGES.length) {
                binding.imgCandyShop.setImageResource(ImageResources.IMAGES[imageIndex]);
            }
        }
    }

    private final RecyclerItemListener<CandyStoreResponse.Items> onItemClickListener = item -> {

    };

    @NonNull
    @Override
    public CandyStoreAdapter.MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCandyshopBinding itemBinding = ItemCandyshopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyOrderHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CandyStoreAdapter.MyOrderHolder holder, int position) {
        holder.bind(listItems.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<CandyStoreResponse.Items> list) {
        this.listItems = list;
        notifyDataSetChanged();
    }
}
