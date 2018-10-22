package com.example.vedad.moviesearch;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vedad.moviesearch.helper.Config;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            TextView tvTitle = findViewById(R.id.details_title);
            ImageView ivPoster = findViewById(R.id.details_poster);
            TextView tvDescription = findViewById(R.id.details_description);

            tvTitle.setText(extras.getString("title"));
            Glide.with(ivPoster).asBitmap().
                    load(Config.imageUrl + "w780/" + extras.getString("backdrop_path")).into(ivPoster);

            tvDescription.setText(extras.getString("overview"));
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
