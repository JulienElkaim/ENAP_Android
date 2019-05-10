package com.example.julienelkaim.testyoutube.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.adapter.PlaylistListAdapter;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class CONTROLYoutubePlaylistEnumeratorActivity extends AppCompatActivity {


    ListView mListView;
    ArrayList<Playlist> mPlaylistArrayList;
    PlaylistListAdapter mPlaylistListAdapter;/* TO DO: ADAPT THE CODE*/


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Constants.windowAndSystemSettings(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_youtube_playlist_enumerator);
        mListView = findViewById(R.id.listOfVideos);

        setMyPlaylistList();

    }

    private void setMyPlaylistList() {


        mPlaylistArrayList = new ArrayList<>();
        mPlaylistArrayList.add(new Playlist("Un Test", "", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "KeT_XDMnauU", "DCFW0gbEH0Y", "ixxxcHI4kpI", "aAcVD_TtlFI", "y3zKhDLCg9g")));
        mPlaylistArrayList.add(new Playlist("Un second Test", "", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "KeT_XDMnauU", "DCFW0gbEH0Y", "ixxxcHI4kpI", "aAcVD_TtlFI", "y3zKhDLCg9g")));
        mPlaylistListAdapter = new PlaylistListAdapter(this, mPlaylistArrayList);
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "KeT_XDMnauU")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList( "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("U_thPyTPwqw", "ADTdpypVZD0", "j9HYtsxteW0", "lXGYAoyabdg", "Dp_8O2FhoCY", "jwpV-p2Y5TU", "U_thPyTPwqw")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList("KeT_XDMnauU")));
        mPlaylistArrayList.add(new Playlist("Playlist voyage Test", "Une texte ultra long qui est sensé dépasser du cadre et se réduire en petit point description et on rallong le texte pour voir si ca va vraiment le aire apres tout y a que trois ligne a tester pour voir si ca marche ", Arrays.asList( "jwpV-p2Y5TU", "KeT_XDMnauU")));

        mListView.setAdapter(mPlaylistListAdapter);
        mPlaylistListAdapter.notifyDataSetChanged();

    }


}
