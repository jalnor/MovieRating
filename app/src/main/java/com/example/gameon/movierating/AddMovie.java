package com.example.gameon.movierating;

import android.app.Application;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class AddMovie extends AppCompatActivity {

    public static String movieName = "";
    public static String movieDescription = "";
    public static String movieGenre = "";
    public static String movieRating = "";
    public static String movieYear = "";
    public static String imdbRating = "";
    Movie movie = new Movie();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        findViewById(R.id.ti_movie_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout name = findViewById(R.id.movieNameTextInput);
                name.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                name.setError("");
            }
        });

        findViewById(R.id.multiDescription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText desc = findViewById(R.id.multiDescription);
                desc.setError("");
            }
        });

        final Spinner genre = findViewById(R.id.spinner);
        String[] genres = new String[]{"Action","Animation","Comedy","Documentary","Family","Horror","Crime","Others"};
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genres);
        genre.setAdapter(adapt);

        final SeekBar sb = findViewById(R.id.seekBar);
        final TextView sBarText = findViewById(R.id.sBarTextView);
        sBarText.setText("0");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int prog = sb.getProgress();
                if ( progress > prog ) {
                    sb.incrementProgressBy(1);
                    prog += 1;

                } else {
                    sb.incrementProgressBy(-1);
                    prog -= 1;
                }
                String displayText = "" + prog;
                sBarText.setText(displayText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.yearEditText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout year = findViewById(R.id.yearTextInput);
                year.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                year.setError("");
            }
        });

        findViewById(R.id.imdbEditText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout imdb = findViewById(R.id.imdbTextInput);
                imdb.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                imdb.setError("");
            }
        });

        findViewById(R.id.addMovieBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextInputLayout name = findViewById(R.id.movieNameTextInput);
                movieName = name.getEditText().getText().toString();
                if ( movieName.equals("") ) {
                    name.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.error));
                    name.setError("Movie must have a name");
                } else {
                    movie.setName(movieName);
                }

                EditText desc = findViewById(R.id.multiDescription);
                movieDescription = desc.getText().toString();
                if ( movieDescription.equals("") ) {
                    desc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.error));
                    desc.setError("Movie must have a description");
                }  else {
                    movie.setDescription(movieDescription);
                }

                movieGenre = genre.getSelectedItem().toString();
                movie.setGenre(movieGenre);
                movie.setRating(sBarText.getText().toString());

                TextInputLayout year = findViewById(R.id.yearTextInput);
                movieYear = year.getEditText().getText().toString();
                if ( movieYear.equals("") ) {
                    year.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.error));
                    year.setError("Movie must have a year");
                } else {
                    movie.setMovieYear(movieYear);
                }

                TextInputLayout imdb = findViewById(R.id.imdbTextInput);
                imdbRating = imdb.getEditText().getText().toString();
                if ( imdbRating.equals("") ) {
                    imdb.getEditText().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.error));
                    imdb.setError("Movie needs an imdb rating");
                } else {
                    movie.setImdbRating(imdbRating);
                }



                Log.d("Items", "\n" +
                        movieName + "\n" +
                        movieDescription + "\n" +
                        movieGenre + "\n" +
                        sBarText.getText().toString() + "\n" +
                        movieYear + "\n" +
                        imdbRating);

                Intent intent = new Intent();
                intent.putExtra("data", movie);
                setResult(RESULT_OK, intent);
                finish();



            }
        });







    }
}
