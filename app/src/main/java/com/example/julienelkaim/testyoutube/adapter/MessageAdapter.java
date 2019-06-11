package com.example.julienelkaim.testyoutube.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.julienelkaim.testyoutube.controller.Message.EmojiconTextView;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private FirebaseUser mUser;

    public MessageAdapter(Context mContext, List<Chat> chat) {
        this.mContext = mContext;
        this.mChat = chat;

    }
    public MessageAdapter(){

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,viewGroup,false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,viewGroup,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }


    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int position) {

        Chat chat = mChat.get(position);
        //viewHolder.emojiTextView.setText((String) chat.getMessage());
        viewHolder.showText.setText((String) chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profile_image;
        public EmojiconTextView showText;

        public ViewHolder(View itemView){
            super(itemView);



            profile_image = itemView.findViewById(R.id.profileImage);
            showText = itemView.findViewById(R.id.emojiTextView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(mUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}