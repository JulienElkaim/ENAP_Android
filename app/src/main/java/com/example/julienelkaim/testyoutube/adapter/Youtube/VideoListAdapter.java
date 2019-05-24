package com.example.julienelkaim.testyoutube.adapter.Youtube;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity.ThumbnailListDisplayerActivity;
import com.example.julienelkaim.testyoutube.controller.Youtube.Aidant.PlayListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Youtube.Video;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;



public class VideoListAdapter extends BaseAdapter {

    private ThumbnailListDisplayerActivity mActivity;
    private ArrayList<Video> mVideoArrayList;
    private LayoutInflater mLayoutInflater;

    /**
     * @author  Julien Elkaim
     *
     * @param activity is the page where video list is displayed.
     * @param videoArrayList is the list of video object to display.
     */
    public VideoListAdapter(ThumbnailListDisplayerActivity activity, ArrayList<Video> videoArrayList){
        mActivity = activity;
        mVideoArrayList = videoArrayList;
    }


    /**
     *
     * @return the number of video to display.
     */
    @Override
    public int getCount() {
        return mVideoArrayList.size();
    }

    /**
     *
     * @param position is the position in the arraylist of the video to get
     * @return the video object at this position.
     */
    @Override
    public Object getItem(int position) {
        return mVideoArrayList.get(position);
    }


    /**
     *
     * @param position of the relative video.
     * @return position in a long encoding.
     */
    @Override
    public long getItemId(int position) {
        return (long)position;
    }


    /**
     * @author Julien Elkaim
     *
     * @param position is the position in the arraylist of the video to display.
     * @param convertView is the view to convert and adapte with the video data.
     * @param parent is the list view to display every convertview.
     * @return the view converted with video's data.
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if(mLayoutInflater==null){  mLayoutInflater = mActivity.getLayoutInflater();    }
        if(convertView == null){    convertView = mLayoutInflater.inflate(R.layout.custom_video_item,null); }


        final Video video = mVideoArrayList.get(position);

        ImageView imageView = convertView.findViewById(R.id.thumbnailsImageView);
        Picasso.get().load(video.getUrl()).into(imageView);

        TextView textView = convertView.findViewById(R.id.video_title);
        textView.setText(video.getTitle());

        TextView secondTextView = convertView.findViewById(R.id.video_description);
        secondTextView.setText(video.getDescription());

        LinearLayout linearLayout = convertView.findViewById(R.id.root);

        final Intent i = new Intent(mActivity, PlayListDisplayerActivity.class);
        i.putExtra(GlobalBox.YOUTUBE_VIDEO_ID_FROM_RESEARCH, video.getVideoId());

        if(mActivity.mIsListModifiable){
            //Single playlist display part    =================== DIFFERENT INTENT QUE DANS LE ELSE !!
            addASupprButton(mActivity,convertView, video);
            linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i.putExtra(GlobalBox.YOUTUBE_DISPLAYER_MODE, GlobalBox.YOUTUBE_DISPLAYER_MODE_VIEW);
                    mActivity.startActivity(i);
                }
            });

        }else{
            linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i.putExtra(GlobalBox.YOUTUBE_DISPLAYER_MODE,  GlobalBox.YOUTUBE_DISPLAYER_MODE_ADD);
                    mActivity.startActivity(i);
                }
            });
        }

        mActivity.incrementCountVideoDisplayed(); // Can be used as we want to add new behavior for a specific video... For instance, load further videos at the last position
        return convertView;
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is the activity displaying the list of video
     * @param convertView is the video card that we should modify.
     * @param video is the video relative to the convertView.
     */
    private void addASupprButton(final ThumbnailListDisplayerActivity activity, View convertView, final Video video) {


        ImageButton supprBtn = new ImageButton(activity);
        supprBtn.setLayoutParams(new ViewGroup.LayoutParams(50,50));
        supprBtn.setBackgroundResource(R.drawable.suppr_button);
        supprBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.modifyYourList(video.getVideoId());
                activity.finish();
                activity.startActivity(activity.getIntent()); //Relancer l'activité elle même

            }
        });

        LinearLayout ll = convertView.findViewById(R.id.desc_and_button);
        if(ll.getChildCount() == 1 ){
            ll.addView(supprBtn);
        }else{
            ll.removeViewAt(1);
            ll.addView(supprBtn);
        }
    }

}
