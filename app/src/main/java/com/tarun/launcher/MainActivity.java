package com.tarun.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tarun.applibrary.apps.AppLib;
import com.tarun.applibrary.apps.AppsManager;
import com.tarun.applibrary.apps.DownloadedApps;
import com.tarun.launcher.utils.InjectManager;
import com.tarun.launcher.utils.SortByNameComparator;
import com.tarun.launcher.viewModel.AppsViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements InjectManager.InjectedEventNotifier {

    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView searchIcon;
    private ArrayList<DownloadedApps> listOfApps = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing App Library
        AppLib.init(this);

        searchIcon = findViewById(R.id.searchIcon);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        final AppsAdapter appsAdapter = new AppsAdapter(this);
        recyclerView.setAdapter(appsAdapter);

        AppsViewModel viewModel = new ViewModelProvider(this).get(AppsViewModel.class);
        viewModel.getAppsLiveData().observe(this, new Observer<ArrayList<DownloadedApps>>() {
            @Override
            public void onChanged(ArrayList<DownloadedApps> downloadedApps) {
                if (downloadedApps != null) {
                    listOfApps.clear();
                    listOfApps.addAll(downloadedApps);
                    Collections.sort(listOfApps, new SortByNameComparator());
                    appsAdapter.setData(listOfApps);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    ArrayList<DownloadedApps> list = AppsManager.getInstance().searchAndReturnList(s);
                    Collections.sort(list,new SortByNameComparator());
                    appsAdapter.setData(list);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        InjectManager.getInstance().addListener(InjectManager.LAUNCH_APP, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.clearFocus();
    }

    @Override
    public void onReceiveEvent(int eventType, Object object) {
        try {
            Intent i = getPackageManager().getLaunchIntentForPackage(listOfApps.get((int) object).getPackageName());
            startActivity(i);
        } catch (Exception e) {
            Log.e(TAG, "onReceiveEvent: Exception handled ==" + e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        InjectManager.getInstance().removeListener(InjectManager.LAUNCH_APP, this);
    }
}