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
import com.afomic.syndicate.model.Friend;
import com.afomic.syndicate.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private Context mContext;
    private List<User> mFriends;
    private FriendListener mFriendListener;
    public FriendAdapter(Context context,List<User> friends,FriendListener friendListener){
        mContext=context;
        mFriendListener=friendListener;
        mFriends=friends;
    }
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_friend,parent,false);
        return new FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        User friend=mFriends.get(position);

    }

    @Override
    public int getItemCount() {
        if(mFriends!=null){
            return mFriends.size();
        }
        return 0;
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView friendImageView;
        TextView friendNameTextView,friendStatusTextView;
        public FriendViewHolder(View itemView) {
            super(itemView);
            friendImageView=itemView.findViewById(R.id.imv_friend_picture);
            friendNameTextView=itemView.findViewById(R.id.tv_friend_name);
            friendStatusTextView=itemView.findViewById(R.id.tv_friend_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            mFriendListener.onClick(mFriends.get(position));
        }
    }
    public interface FriendListener{
        void onClick(User friend);
    }
}
