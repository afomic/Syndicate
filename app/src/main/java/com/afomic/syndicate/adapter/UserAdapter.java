package com.afomic.syndicate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.syndicate.R;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.util.GlideApp;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<User> mUsers;
    private UserListener mUserListener;
    public UserAdapter(Context context, List<User> users, UserListener userListener){
        mContext=context;
        mUserListener=userListener;
        mUsers=users;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user=mUsers.get(position);
        GlideApp.with(mContext)
                .load(user.getPictureUrl())
                .placeholder(R.drawable.avater)
                .into(holder.friendImageView);
        holder.friendStatusTextView.setText(user.getStatus());
        String name=String.format(user.getUsername());
        holder.friendNameTextView.setText(name);

    }

    @Override
    public int getItemCount() {
        if(mUsers!=null){
            return mUsers.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView friendImageView;
        TextView friendNameTextView,friendStatusTextView;
        public UserViewHolder(View itemView) {
            super(itemView);
            friendImageView=itemView.findViewById(R.id.imv_friend_picture);
            friendNameTextView=itemView.findViewById(R.id.tv_friend_name);
            friendStatusTextView=itemView.findViewById(R.id.tv_friend_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            mUserListener.onClick(mUsers.get(position));
        }
    }
    public interface UserListener{
        void onClick(User friend);
    }
}
