package com.example.julienelkaim.testyoutube.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.MyApplication;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.adapter.MessageAdapter;
import com.example.julienelkaim.testyoutube.adapter.MessageImageAdapter;
import com.example.julienelkaim.testyoutube.adapter.PictogrammesAdapter;
import com.example.julienelkaim.testyoutube.model.Chat;
import com.example.julienelkaim.testyoutube.model.ChatImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    Button envoiTestImage;
    RecyclerView recyclerView;
    GridView gridView;

    MessageImageAdapter messageImageAdapter;
    List<Chat> mChat;
    List<ChatImage> mChatImage;

    List<String> mContactsNameList;
    List<String> mUserIdList;

    FirebaseAuth mAuth;
    String mUserId;
    FirebaseUser mUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Ressources
        envoiTestImage = findViewById(R.id.envoiTest);
        gridView = findViewById(R.id.gridView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //Listes
        mContactsNameList = ((MyApplication) getApplicationContext()).contactsNameList;
        mUserIdList = ((MyApplication) getApplicationContext()).userIdList;
        mChat = new ArrayList<>();
        mChatImage = new ArrayList<>();

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null){
            startActivity(new Intent(ChatActivity.this,AuthActivity.class));
        }
        mUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        //Récupération de l'intent: la position du contact cliqué --> son id..
        final String mOtherUserId = mUserIdList.get(ChatActivity.this.getIntent().getIntExtra("position",0));

        envoiTestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageMessage(mUserId,mOtherUserId,R.drawable.add_button);
            }
        });

        /**
         * Ce qui concerne l'affichage du panneau de commande pour les smileys
         */

        final ArrayList<Integer> resourceList = new ArrayList<>();
        resourceList.add(R.drawable.add_button);
        resourceList.add(R.drawable.logo_chenapan);

        PictogrammesAdapter pictogrammesAdapter = new PictogrammesAdapter(ChatActivity.this,resourceList);
        gridView.setAdapter(pictogrammesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChatActivity.this, "Vous avez cliqué en " + position + "eme position", Toast.LENGTH_LONG).show();

                System.out.println("C'est cliqué");
                //sendImageMessage(mUserId,mOtherUserId,resourceList.get(position));
            }
        });

        /**
         * Ce qui concerne la lecture/ ecriture de message
         *   ||||||
         *   ||||||
         *   VVVVVV
         */
        final ProgressDialog mRegProgress;
        // Progress Dialog
        mRegProgress= new ProgressDialog(this);
        mRegProgress.setTitle("Chargement des messages");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*readMessage(mUserId,mOtherUserId);*/
                readImageMessage(mUserId,mOtherUserId);
                mRegProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
/*
    protected void sendMessage(String sender,String receiver,String message){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    protected void readMessage(final String myId, final String userId){

        reference.child("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    HashMap<String,String> hashMapTemp = (HashMap) ds.getValue();
                    Chat chat =  new Chat(hashMapTemp.get("sender"),hashMapTemp.get("receiver"),hashMapTemp.get("message"));

                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        mChat.add(chat);
                        }
                    messageAdapter = new MessageAdapter(ChatActivity.this,mChat) ;
                    recyclerView.setAdapter(messageAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/


    ////---------------- IMAGES ----------------
    public void sendImageMessage(String sender,String receiver,int imageId){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("imageId",imageId);

        ChatImage chatImage = new ChatImage(sender,receiver,imageId);

        reference.child("ChatImage").push().setValue(chatImage);
    }


    protected void readImageMessage(final String myId, final String userId){

        DatabaseReference referenceImage = reference.child("ChatImage");
        referenceImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChatImage.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    HashMap<String,Object> hashMapTemp = (HashMap) ds.getValue();
                    ChatImage chatImage =  new ChatImage((String) hashMapTemp.get("sender"),(String) hashMapTemp.get("receiver"),(long) hashMapTemp.get("imageId"));

                    if (chatImage.getReceiver().equals(myId) && chatImage.getSender().equals(userId) ||
                            chatImage.getReceiver().equals(userId) && chatImage.getSender().equals(myId)) {
                        mChatImage.add(chatImage);
                    }
                    messageImageAdapter = new MessageImageAdapter(ChatActivity.this,mChatImage) ;
                    recyclerView.setAdapter(messageImageAdapter);
                    LinearLayoutManager linearLayoutManagerImage = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManagerImage.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManagerImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
