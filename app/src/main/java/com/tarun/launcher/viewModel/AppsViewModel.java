package com.tarun.launcher.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tarun.applibrary.apps.AppsManager;
import com.tarun.applibrary.apps.DownloadedApps;

import java.util.ArrayList;

public class AppsViewModel extends AndroidViewModel {

    private static final String TAG = "AppsViewModel";
    private MutableLiveData<ArrayList<DownloadedApps>> appsLiveData;

    public AppsViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    /**
     * API to init data fetching
     * @param
     */
    private void init() {
        appsLiveData=AppsManager.getInstance().getAppsAndGamesLiveData();
    }

    /**
     * API to return list of apps live data
     * @return MutableLiveData<ArrayList<DownloadedApps>>
     */
    public MutableLiveData<ArrayList<DownloadedApps>> getAppsLiveData(){
        return appsLiveData;
    }
}
