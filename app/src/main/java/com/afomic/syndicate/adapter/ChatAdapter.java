package com.afomic.syndicate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.syndicate.R;
import com.afomic.syndicate.data.Constants;
import com.afomic.syndicate.data.PreferenceManager;
import com.afomic.syndicate.model.Chat;
import com.afomic.syndicate.model.Message;
import com.afomic.syndicate.model.User;
import com.afomic.syndicate.ui.messages.MessageActivity;
import com.afomic.syndicate.util.DateUtil;
import com.afomic.syndicate.util.GlideApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by afo mic on 1/24/18.
 *
 */

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    private Context mContext;
    private List<Chat> mChats;
    PreferenceManager mPreferenceManager;

    public ChatAdapter(Context context, List<Chat> chats){
        mChats=chats;
        mContext=context;
        mPreferenceManager=new PreferenceManager(context);

    }
    @Override
    @NonNull
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_chat,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(final @NonNull ChatHolder holder, int position) {
        Chat chatItem=mChats.get(position);
        final String userId;
        if(chatItem.getUserOne().equals(mPreferenceManager.getUserId())){
            userId=chatItem.getUserTwo();
        }else {
            userId=chatItem.getUserOne();
        }
        FirebaseDatabase.getInstance()
                .getReference(Constants.USER_REF)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        GlideApp.with(mContext)
                                .load(user.getPictureUrl())
                                .placeholder(R.drawable.avater)
                                .into(holder.recipientInitial);
                        holder.recipientTextView.setText(user.getUsername());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        holder.lastMessageTextView.setText(chatItem.getLastMessage());
        String lastUpdateTime= DateUtil.formatDate(chatItem.getLastUpdate());
        holder.lastUpdateTextView.setText(lastUpdateTime);

        FirebaseDatabase.getInstance()
                .getReference(Constants.MESSAGES_REF)
                .child(chatItem.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count =0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Message message=snapshot.getValue(Message.class);
                            if(!message.getSenderId().equals(mPreferenceManager.getUserId())
                                    &&!message.isRead()){
                                count++;
                            }
                        }
                        if(count>0){
                            holder.unreadMentionTextView.setText(String.valueOf(count));
                            holder.unreadMentionTextView.setVisibility(View.VISIBLE);
                        }else {
                            holder.unreadMentionTextView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        if(mChats!=null){
            return mChats.size();
        }
        return 0;
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipientTextView, lastMessageTextView,lastUpdateTextView,unreadMentionTextView;
        CircleImageView recipientInitial;
        public ChatHolder(View itemView) {
            super(itemView);
             itemView.setOnClickListener(this);
             recipientTextView=itemView.findViewById(R.id.tv_recipient);
             lastMessageTextView=itemView.findViewById(R.id.tv_last_message);
             lastUpdateTextView=itemView.findViewById(R.id.tv_last_update);
             unreadMentionTextView=itemView.findViewById(R.id.tv_unread_messages);
             recipientInitial=itemView.findViewById(R.id.imv_recipient_initials);
        }

        @Override
        public void onClick(View v) {
            Chat selectedChat=mChats.get(getAdapterPosition());
            Intent intent=new Intent(mContext, MessageActivity.class);
            intent.putExtra(Constants.EXTRA_CHAT,selectedChat);
            mContext.startActivity(intent);
        }
    }
}
