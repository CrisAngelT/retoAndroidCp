package com.example.ecommercecineplanet.ui.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommercecineplanet.R;
import com.example.ecommercecineplanet.commons.RecyclerItemListener;
import com.example.ecommercecineplanet.data.model.PremierResponse;
import com.example.ecommercecineplanet.databinding.ItemsPremiersBinding;
import com.example.ecommercecineplanet.ui.home.HomeViewModel;

import java.util.List;

public class PremiersAdapter extends RecyclerView.Adapter<PremiersAdapter.MyOrderHolder> {

    private List<PremierResponse.Premiere> listPremiere;
    private final HomeViewModel homeViewModel;

    public PremiersAdapter(List<PremierResponse.Premiere> listPremiere, HomeViewModel viewModel) {
        this.listPremiere = listPremiere;
        this.homeViewModel = viewModel;
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {

        private final ItemsPremiersBinding binding;

        public MyOrderHolder(ItemsPremiersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PremierResponse.Premiere item, RecyclerItemListener<PremierResponse.Premiere> itemListener) {
            Glide.with(itemView.getContext()).load(item.getImage())
                    .error(R.drawable.noimage_available)
                    .into(binding.imgPremiers);
            binding.titlePremiere.setText(item.getDescription());
            binding.getRoot().setOnClickListener(v -> itemListener.onItemSelected(item));

        }
    }

    private final RecyclerItemListener<PremierResponse.Premiere> onItemClickListener = new RecyclerItemListener<>() {
        @Override
        public void onItemSelected(PremierResponse.Premiere item) {
            homeViewModel.onTouchPremier(item);
        }
    };

    @NonNull
    @Override
    public PremiersAdapter.MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsPremiersBinding itemBinding = ItemsPremiersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyOrderHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PremiersAdapter.MyOrderHolder holder, int position) {
        holder.bind(listPremiere.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listPremiere.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<PremierResponse.Premiere> list) {
        this.listPremiere = list;
        notifyDataSetChanged();
    }
}
