package com.example.julienelkaim.testyoutube.controller.Wikipedia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WikiAffichage extends AppCompatActivity {


    String wikiTitle;
    String wikiTxt;
    String wikiImgLink;
    String url;
    Document document = null;
    int page = 0;
    int pageMax = 1;
    int imgNum = 0;
    int imgNumMax = 1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_affichage);
        progressBar = findViewById(R.id.progressbar);
        Intent msgFromMainActivity = getIntent();
        String nameArticle = msgFromMainActivity.getStringExtra("articleTitle");
        url = "https://fr.vikidia.org/wiki/" + nameArticle;
        new Description(msgFromMainActivity.getIntExtra("page",0),
                msgFromMainActivity.getIntExtra("imgnum",0)).execute(); //Main script
    }

    public void next(View view){
        if (page +3 < pageMax)
            page++;
        if (imgNum +1   < imgNumMax)
            imgNum++;
        new Description(page, imgNum).execute();
    }

    public void previous(View view){
        if (page > 0)
            page--;
        if (imgNum > 0)
            imgNum--;
        new Description(page, imgNum).execute();
    }




    private class Description extends AsyncTask<Void, Void, Void> {

        int mpage;
        int mimg;

        Description(int page, int imgNum){
            mpage = page;
            mimg = imgNum;
        }

        TextView wikiTitleView = findViewById(R.id.wikiTitle);


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                document = Jsoup.connect(url).get();

                //get title
                Elements srcTitle = document.getElementsByTag("h1");
                Element srcTitleElem = srcTitle.get(0);
                wikiTitle = srcTitleElem.text();

                //get txt
                Elements text = document.getElementsByTag("p");
                pageMax = text.size();
                Element textElem = text.get(mpage);
                wikiTxt = textElem.text();
                progressBar.setProgress((int)(((float)page/pageMax)*((float)(pageMax)/(pageMax-3))*100));

                //get img
                Elements link = document.getElementsByClass("image");
                imgNumMax = link.size();
                if (!link.isEmpty()) {
                    Elements img = link.get(imgNum).getElementsByTag("img");
                    Element img1 = img.get(0);
                    wikiImgLink = img1.attr("src");
                    if (wikiImgLink.charAt(0) == '/')
                        wikiImgLink = "https:" + wikiImgLink;
                }
                else
                    wikiImgLink = "https://ui-ex.com/images/transparent-logo-book-3.png";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            wikiTitleView = findViewById(R.id.wikiTitle);
            wikiTitleView.setText(wikiTitle);
            WebView myWebView = findViewById(R.id.wbv);
            String unencodedHtml =
                    "<div style=\"padding: 20px 10px 0px 10px;\" align=\"center\">" +
                            "<img style=\" width: 100%; \" src=\"" + wikiImgLink + "\"> " + "</div> " +
                    "<div style=\"font-size  : 1.4em; padding: 20px 2px 20px 2px; line-height: 150%;\" align=\"justify\">" + wikiTxt + "</div> ";
            myWebView.loadData(unencodedHtml, "text/html; charset=UTF-8",null);

        }



    }
}
