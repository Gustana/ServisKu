package com.example.serviceku.ui.user.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceku.BR;
import com.example.serviceku.R;
import com.example.serviceku.databinding.RecyclerItemUserServiceBinding;
import com.example.serviceku.room.entity.ServiceEntity;

import java.util.List;

public class RecyclerAdapterServiceUser extends RecyclerView.Adapter<RecyclerAdapterServiceUser.UI> {

    private static final String TAG = RecyclerAdapterServiceUser.class.getSimpleName();
    private final List<ServiceEntity> serviceEntityList;

    public RecyclerAdapterServiceUser(List<ServiceEntity> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
        Log.i(TAG, "RecyclerAdapterServiceUser: " + serviceEntityList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UI onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemUserServiceBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recycler_item_user_service,
                parent,
                false
        );
        return new UI(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UI holder, int position) {
        String status;
        ServiceEntity serviceEntity = serviceEntityList.get(position);

        holder.bind(serviceEntity);

        holder.binding.txtNoPlat.setText(serviceEntity.getNoPlat());

        if(serviceEntity.getVehicleType().equalsIgnoreCase("Motor")){
            holder.binding.imgVehicle.setImageDrawable(ResourcesCompat.getDrawable(holder.binding.getRoot().getResources(), R.drawable.motor, null));
        }else{
            holder.binding.imgVehicle.setImageDrawable(ResourcesCompat.getDrawable(holder.binding.getRoot().getResources(), R.drawable.mobil, null));
        }

        if (serviceEntity.getStatus() == 0) {
            status = holder.binding.getRoot().getResources().getString(R.string.belum_diservis);
        } else if (serviceEntity.getStatus() == 1) {
            status = holder.binding.getRoot().getResources().getString(R.string.sedang_diservis);
        } else {
            status = holder.binding.getRoot().getResources().getString(R.string.selesai_service);
        }

        holder.binding.txtStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return serviceEntityList.size();
    }

    static class UI extends RecyclerView.ViewHolder {
        private final RecyclerItemUserServiceBinding binding;

        public UI(@NonNull RecyclerItemUserServiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Object obj) {
            binding.setVariable(BR.serviceUserData, obj);
            binding.executePendingBindings();
        }

    }
}
