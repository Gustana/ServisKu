package com.example.serviceku.ui.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceku.BR;
import com.example.serviceku.R;
import com.example.serviceku.databinding.ActivityDetailServiceBinding;
import com.example.serviceku.room.DBHolder;
import com.example.serviceku.room.entity.ServiceEntity;

public class DetailServiceActivity extends AppCompatActivity {

    private static final String TAG = DetailServiceActivity.class.getSimpleName();
    private ActivityDetailServiceBinding binding;
    private DBHolder dbHolder;

    private int serviceId;

    private String username;
    private ServiceEntity serviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        serviceId = getIntent().getIntExtra("idService", 0);
        username = getIntent().getStringExtra("username");

        dbHolder = DBHolder.getInstance(this);

        Log.i(TAG, "onCreate: serviceId: " + serviceId);

        getServiceDetail();

        binding.btnUpdateService.setOnClickListener(v -> {

            RadioButton rb = findViewById(binding.rgVehicleType.getCheckedRadioButtonId());

            serviceData.setStatus(generateStatus(rb));

            if(serviceData.getStatus()==2){
                if(binding.edtTotalPrice.getText().toString().isEmpty()){
                    Toast.makeText(this, "Total price kosong", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    float totalPrice = Float.parseFloat(binding.edtTotalPrice.getText().toString());
                    serviceData.setTotalPrice(totalPrice);
                }
            }

            updateService(serviceData);

        });
    }

    private int generateStatus(RadioButton rb) {
        return (rb.getText().toString().equals(getString(R.string.sedang_diservis))) ? 1 : 2;
    }

    private void updateService(ServiceEntity serviceData) {
        class UpdateService extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                dbHolder.getAppDB().serviceDao().updateService(serviceData);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                Toast.makeText(getApplicationContext(), "Berhasil update", Toast.LENGTH_SHORT).show();
            }
        }

        new UpdateService().execute();
    }

    private void getServiceDetail() {
        class GetService extends AsyncTask<Void, Void, ServiceEntity> {

            @Override
            protected ServiceEntity doInBackground(Void... voids) {
                return dbHolder.getAppDB().serviceDao().getServiceDetail(serviceId);
            }

            @Override
            protected void onPostExecute(ServiceEntity serviceEntity) {
                super.onPostExecute(serviceEntity);

                binding.txtName.setText(username);
                serviceData = serviceEntity;

                binding.setVariable(BR.serviceDetailData, serviceEntity);
                binding.executePendingBindings();

            }
        }

        new GetService().execute();
    }

}