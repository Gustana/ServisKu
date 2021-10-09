package com.example.serviceku.ui.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.serviceku.MainActivity;
import com.example.serviceku.R;
import com.example.serviceku.databinding.ActivityHomeAdminBinding;
import com.example.serviceku.db.DBHolder;
import com.example.serviceku.db.entity.ServiceEntity;
import com.example.serviceku.helper.SPManager;
import com.example.serviceku.ui.admin.adapter.RecyclerAdapterServiceAdmin;
import com.example.serviceku.util.LogoutUtil;

import java.util.List;

public class HomeAdminActivity extends AppCompatActivity implements LogoutUtil {

    private static final String TAG = HomeAdminActivity.class.getSimpleName();
    private ActivityHomeAdminBinding binding;
    private DBHolder dbHolder;
    private SPManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHolder = new DBHolder(this);
        spManager = new SPManager(this);

        binding.rvAdminServiceList.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );

        getServiceList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getServiceList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLogout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logout() {
        spManager.clearSP();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void getServiceList() {
        class GetService extends AsyncTask<Void, Void, List<ServiceEntity>> {

            @Override
            protected List<ServiceEntity> doInBackground(Void... voids) {
                return dbHolder.getAppDB().serviceDao().getAdminServiceList();
            }

            @Override
            protected void onPostExecute(List<ServiceEntity> serviceEntities) {
                super.onPostExecute(serviceEntities);
                Log.i(TAG, "onPostExecute: " + serviceEntities.toString());
                binding.rvAdminServiceList.setAdapter(new RecyclerAdapterServiceAdmin(serviceEntities));
            }
        }

        new GetService().execute();
    }
}