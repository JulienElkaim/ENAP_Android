package com.example.julienelkaim.testyoutube.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.julienelkaim.testyoutube.MyApplication;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ListView listView;
    List<String> mContactsNameList;
    List<String> mUserIdList;
    private DatabaseReference reference;

    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.listViewContacts);
        mContactsNameList = ((MyApplication) getApplicationContext()).contactsNameList;
        mUserIdList = ((MyApplication) getApplicationContext()).userIdList;
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Progress Dialog
        mRegProgress= new ProgressDialog(this);
        mRegProgress.setTitle("Chargement des contacts");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        //Firebase
        reference = FirebaseDatabase.getInstance().getReference("Users");
        // Chargement des contacts
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserIdList.clear();
                mContactsNameList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    mContactsNameList.add(ds.child("username").getValue().toString());
                    mUserIdList.add(ds.child("id").getValue().toString());
                }
                ContactsActivity.this.arrayAdapterListView(mContactsNameList);
                mRegProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void arrayAdapterListView(List<String> mContactsNameList){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ContactsActivity.this,android.R.layout.simple_list_item_1, mContactsNameList);
        arrayAdapter.remove(null);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentClickItem = new Intent(ContactsActivity.this,ChatActivity.class);
                //On renvoie en intent juste la position : on a créé la classe java 'MyApplication' et fait d'elle notre application, grâce au Manifest
                //Comme on a alors accès à partir de toutes les activités aux Listes de contacts et de noms, on a juste a passer en intent la position de l'objet cliqué
                intentClickItem.putExtra("position",position);
                startActivity(intentClickItem);
                finish();

            }
        });


    }
}
