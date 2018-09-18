package com.example.gameon.movierating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ArrayList<Movie> original;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_show);

        Bundle bundle = getIntent().getExtras();
        movies = bundle.getParcelableArrayList("movies");
        original = bundle.getParcelableArrayList("original");
        String header = bundle.getString("header");
        Log.d("header", header);
        ((TextView)findViewById(R.id.textView2)).setText(header);
        ((TextView)findViewById(R.id.textView3)).setText(header);
        showDetails(movies.get(0));

        //finish
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra("original", original);
                startActivity(intent);
                finish();
            }
        });


        //first
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = 0;
                showDetails(movies.get(0));
            }
        });

        //pre
        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((current - 1 ) >=  0) {
                    current--;
                    showDetails(movies.get(current));
                }else{
                    showDetails(movies.get(0));
                }
            }
        });

        //next
        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((current + 1 ) <= movies.size()) {
                    current++;
                    showDetails(movies.get(current));
                }else{
                    showDetails(movies.get(movies.size()-1));
                }
            }
        });

        //last
        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails(movies.get(movies.size()-1));
            }
        });
    }

    private void showDetails(Movie movie){
        ((TextView)findViewById(R.id.textView5)).setText(movie.getName());
        ((EditText)findViewById(R.id.editText)).setText(movie.getDescription());
        ((TextView)findViewById(R.id.textView8)).setText(movie.getGenre());
        ((TextView)findViewById(R.id.textView10)).setText(movie.getRating() + " / 5");
        ((TextView)findViewById(R.id.textView12)).setText(movie.getMovieYear());
        ((TextView)findViewById(R.id.textView14)).setText(movie.getImdbRating());
    }
}
