package com.example.julienelkaim.testyoutube.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Chat;
import com.example.julienelkaim.testyoutube.model.ChatImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageImageAdapter extends RecyclerView.Adapter<MessageImageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<ChatImage> mChatImage;
    private FirebaseUser mUser;

    public MessageImageAdapter(Context mContext, List<ChatImage> chatImage) {
        this.mContext = mContext;
        this.mChatImage = chatImage;

    }
    public  MessageImageAdapter(){

    }

    @NonNull
    @Override
    public MessageImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,viewGroup,false);
            return new MessageImageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,viewGroup,false);
            return new MessageImageAdapter.ViewHolder(view);
        }
    }


    public void onBindViewHolder(@NonNull MessageImageAdapter.ViewHolder viewHolder, int position) {

        ChatImage chatImage = mChatImage.get(position);

        // On utilise un 'long' pour les id image à cause de FireBase : il semblerait que firebase convertisse les ressources int du code en long (pour la transmission sur les serveurs du int, la taille est trop grande donc
        // pour Firebase c'est un long alors que la memoire cache accordée a android studio est plus grande donc c'est un int pour android studio(/compilateur java)
        // Le cast de long vers int fonctionne donc on prend comme valeur par défaut le long.
        viewHolder.showImage.setImageResource((int) chatImage.getImageId());
    }

    @Override
    public int getItemCount() {
        return mChatImage.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profile_image;
        public ImageView showImage;

        public ViewHolder(View itemView){
            super(itemView);

            profile_image = itemView.findViewById(R.id.profileImage);
            showImage = itemView.findViewById(R.id.showImage);

        }
    }

    @Override
    public int getItemViewType(int position) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChatImage.get(position).getSender().equals(mUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}