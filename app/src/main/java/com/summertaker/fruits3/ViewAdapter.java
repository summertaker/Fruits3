package com.summertaker.fruits3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ViewInterface mViewInterface;
    private ArrayList<Member> mMembers;

    ViewAdapter(ArrayList<Member> members, ViewInterface listener) {
        this.mMembers = members;
        this.mViewInterface = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item, parent, false);
        return new ViewHolder(context, mViewInterface, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mMembers.get(position));
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }
}
