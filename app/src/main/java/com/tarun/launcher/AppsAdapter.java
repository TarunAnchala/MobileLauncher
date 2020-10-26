package com.tarun.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tarun.applibrary.apps.DownloadedApps;
import com.tarun.launcher.utils.InjectManager;

import java.util.ArrayList;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {


    private final RequestManager glide;
    private LayoutInflater layoutInflater;
    private ArrayList<DownloadedApps> listOfDownloadedApps = new ArrayList<>();
    private static final String TAG = "AppsAdapter";

    public AppsAdapter(Context context) {
        glide = Glide.with(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.app_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appName.setText(listOfDownloadedApps.get(position).getName());
        glide.load(listOfDownloadedApps.get(position).getLogo()).into(holder.getAppLogo());
    }

    public void setData(ArrayList<DownloadedApps> listOfApps) {
        if (this.listOfDownloadedApps.size() > 0) {
            this.listOfDownloadedApps.clear();
        }
        this.listOfDownloadedApps.addAll(listOfApps);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfDownloadedApps != null ? listOfDownloadedApps.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView appLogo;
        private TextView appName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.appName);
            appLogo = itemView.findViewById(R.id.appLogo);
            itemView.setOnClickListener(view -> InjectManager.getInstance().inject(InjectManager.LAUNCH_APP, getAdapterPosition()));

        }

        public ImageView getAppLogo() {
            return appLogo;
        }

        public TextView getAppName() {
            return appName;
        }
    }
}
