package com.afomic.syndicate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afomic.syndicate.R;
import com.afomic.syndicate.model.NavItem;

import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavHolder> {
    private Context mContext;
    private List<NavItem> mNavItems;
    private NavListener mNavListener;
    public NavAdapter(Context context, List<NavItem> navItems,NavListener navListener){
        mContext=context;
        mNavItems=navItems;
        mNavListener=navListener;
    }
    @NonNull
    @Override
    public NavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_navigation,parent,false);
        return new NavHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NavHolder holder, int position) {
        NavItem navItem=mNavItems.get(position);
        if(navItem.isSelected()){
            holder.navigationLayout.setBackgroundColor(Color.GRAY);
        }else {
            holder.navigationLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.navTitleTextView.setText(navItem.getTitle());
        if(navItem.getType()==NavItem.TYPE_CHAT){
            holder.navigationIcon.setImageResource(R.drawable.ic_chat);
        }else if(navItem.getType()==NavItem.TYPE_SETTINGS){
            holder.navigationIcon.setImageResource(R.drawable.ic_settings);
        }else {
            holder.navigationIcon.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    public int getItemCount() {
        if(mNavItems!=null){
            return  mNavItems.size();
        }
        return 0;
    }

    public class NavHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView navTitleTextView;
        LinearLayout navigationLayout;
        ImageView navigationIcon;
        public NavHolder(View itemView) {
            super(itemView);
            navigationIcon=itemView.findViewById(R.id.imv_nav_icon);
            navTitleTextView=itemView.findViewById(R.id.tv_nav_title);
            navigationLayout=itemView.findViewById(R.id.navigation_item_container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNavListener.onNavigationSelected(getAdapterPosition());

        }
    }
    public interface NavListener{
        void onNavigationSelected(int position);
    }

}
