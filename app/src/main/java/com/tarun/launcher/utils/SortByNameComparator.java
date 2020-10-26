package com.tarun.launcher.utils;

import com.tarun.applibrary.apps.DownloadedApps;

import java.util.Comparator;

public class SortByNameComparator implements Comparator<DownloadedApps> {
    @Override
    public int compare(DownloadedApps downloadedApps, DownloadedApps t1) {
        return downloadedApps.getName().compareTo(t1.getName());
    }
}
