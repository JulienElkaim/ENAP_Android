package com.example.julienelkaim.testyoutube.controller.Message;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.julienelkaim.testyoutube.MyApplication;
import com.example.julienelkaim.testyoutube.R;

import com.example.julienelkaim.testyoutube.adapter.MessageAdapter;
import com.example.julienelkaim.testyoutube.emoji.Emojicon;
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

    RecyclerView recyclerView;

    MessageAdapter messageAdapter;
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
        if (mUser == null) {
            startActivity(new Intent(ChatActivity.this, AuthActivity.class));
        }
        mUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        //Récupération de l'intent: la position du contact cliqué --> son id..
        final String mOtherUserId = mUserIdList.get(ChatActivity.this.getIntent().getIntExtra("position", 0));

        /**
         * Ce qui concerne la lecture/ ecriture de message
         *   ||||||
         *   ||||||
         *   VVVVVV
         */
        final ProgressDialog mRegProgress;
        // Progress Dialog
        mRegProgress = new ProgressDialog(this);
        mRegProgress.setTitle("Chargement des messages");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        //Lecture de la base de données pour pouvoir lire les messages
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //readImageMessage(mUserId, mOtherUserId);
                readMessage(mUserId,mOtherUserId);
                mRegProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         *  EMOJICON
         */

        final EmojiconEditText emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        final View rootView = findViewById(R.id.root_view);
        final ImageView emojiButton = (ImageView) findViewById(R.id.emoji_btn);
        final ImageView submitButton = (ImageView) findViewById(R.id.submit_btn);

        // Give the topmost view of your activity layout hierarchy. This will be used to measure soft keyboard height
        final EmojiconsPopup popup = new EmojiconsPopup(rootView, this);

        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, R.drawable.smiley);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojiconsPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {

            }

            @Override
            public void onKeyboardClose() {
                if(popup.isShowing())
                    popup.dismiss();
            }
        });

        //On emoji clicked, add it to edittext
        popup.setOnEmojiconClickedListener(new EmojiconGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (emojiconEditText == null || emojicon == null) {
                    return;
                }

                int start = emojiconEditText.getSelectionStart();
                int end = emojiconEditText.getSelectionEnd();
                if (start < 0) {
                    emojiconEditText.append(emojicon.getEmoji());
                } else {
                    emojiconEditText.getText().replace(Math.min(start, end),
                            Math.max(start, end), emojicon.getEmoji(), 0,
                            emojicon.getEmoji().length());
                }
            }
        });

        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojiconsPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                emojiconEditText.dispatchKeyEvent(event);
            }
        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        emojiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if(!popup.isShowing()){

                    //If keyboard is visible, simply show the emoji popup
                    if(popup.isKeyBoardOpen()){
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else{
                        emojiconEditText.setFocusableInTouchMode(true);
                        emojiconEditText.requestFocus();
                        popup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(emojiconEditText, InputMethodManager.SHOW_IMPLICIT);
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else{
                    popup.dismiss();
                }
            }
        });

        //On submit, add the edittext text to listview and clear the edittext
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newText = emojiconEditText.getText().toString();
                emojiconEditText.getText().clear();
                sendMessage(mUserId,mOtherUserId,newText);

            }
        });
    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId){
        iconToBeChanged.setImageResource(drawableResourceId);
    }




        // --------------- TEXTE ET PICTOGRAMMES -----------------------

    /**
     *
     * @author Mathis Fouques
     *
     * Write a message via Firebase realtime database
     *
     * @param sender
     * @param receiver
     * @param message
     */
        protected void sendMessage(String sender,String receiver,String message){

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);

            reference.child("Chats").push().setValue(hashMap);
        }

    /**
     *
     * @author Mathis Fouques
     *
     * Read the message via Firebase realtime database reference
     *
     * @param myId
     * @param userId
     */
        protected void readMessage(final String myId, final String userId){

            reference.child("Chats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mChat.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        HashMap<String,String> hashMapTemp = (HashMap) ds.getValue();

                        //Récupération sous forme de hashmap puis création d'un chat pour pouvoir utiliser messageadapter
                        Chat chat =  new Chat(hashMapTemp.get("sender"),hashMapTemp.get("receiver"),hashMapTemp.get("message"));

                        if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                                chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                            mChat.add(chat);
                            }

                        //Adapter
                        messageAdapter = new MessageAdapter(ChatActivity.this,mChat) ;
                        recyclerView.setAdapter(messageAdapter);
                        //Layout manager
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setStackFromEnd(true);
                        recyclerView.setLayoutManager(linearLayoutManager);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    @Override
    protected void onStart() {
        super.onStart();

        //GlobalBox.windowAndSystemSettings(this);

    }
}
