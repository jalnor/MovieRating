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
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {

    public static String movieName = "";
    public static String movieDescription = "";
    public static String movieGenre = "";
    public static String movieRating = "";
    public static String movieYear = "";
    public static String imdbRating = "";
    public String regularExpression = "^\\d{4}$";
    public String imdbRegExpression = "^[+-]?([0-9]*[.])?[0-9]+$";
    public int min = 1921;
    public int max = 2018;
    public boolean erno = true;
    Movie movie = new Movie();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        //
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

        findViewById(R.id.addMovieBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputLayout name = findViewById(R.id.movieNameTextInput);
                movieName = name.getEditText().getText().toString();
                Log.d("Items", "Name is " + movieName.isEmpty());
                if ( movieName.isEmpty() ) {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.movie_name_issue),Toast.LENGTH_LONG).show();
                    erno = false;
                } else {
                    movie.setName(movieName);
                }

                EditText desc = findViewById(R.id.multiDescription);
                movieDescription = desc.getText().toString();
                if ( movieDescription.equals("") ) {
                    movie.setDescription("Unknown");
                }  else {
                    movie.setDescription(movieDescription);
                }

                movieGenre = genre.getSelectedItem().toString();
                movie.setGenre(movieGenre);
                movie.setRating(sBarText.getText().toString());

                TextInputLayout year = findViewById(R.id.yearTextInput);
                movieYear = year.getEditText().getText().toString();
                if ( !movieYear.equals("") ) {
                    Log.d("Items", "Made it past regular expression in year" );
                    if ( !movieYear.matches(regularExpression) || Integer.parseInt(movieYear) < min ||
                            Integer.parseInt(movieYear) > max ) {
                        if ( !movieYear.matches(regularExpression) ) {
                            year.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_size), Toast.LENGTH_LONG).show();
                            erno = false;
                        } else if ( Integer.parseInt(movieYear) < min ) {
                            year.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_less_than), Toast.LENGTH_LONG).show();
                            erno = false;
                        } else {
                            year.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_greater_than), Toast.LENGTH_LONG).show();
                            erno = false;
                        }
                    } else {
                        movie.setMovieYear(movieYear);
                    }
                } else {
                    movie.setMovieYear("Unknown");
                }

                TextInputLayout imdb = findViewById(R.id.imdbTextInput);
                imdbRating = imdb.getEditText().getText().toString();
                if ( !imdbRating.equals("") ) {
                    Log.d("Items", "Made it past regular expression in imdb" );
                    if ( !imdbRating.matches(imdbRegExpression) || Double.parseDouble(imdbRating) > 10.0 || Double.parseDouble(imdbRating) < 0.0) {
                        if ( !imdbRating.matches(imdbRegExpression) ) {
                            imdb.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_format_issue), Toast.LENGTH_LONG).show();
                            erno = false;
                        } else if ( Double.parseDouble(imdbRating) > 10.0 ){
                            imdb.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_greater_than), Toast.LENGTH_LONG).show();
                            erno = false;
                        } else {
                            imdb.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_less_than), Toast.LENGTH_LONG).show();
                            erno = false;
                        }
                    } else {
                        movie.setImdbRating(imdbRating);
                    }
                } else {
                    movie.setImdbRating("Unknown");
                }

                Log.d("Items", "\n" +
                        movieName + "\n" +
                        movieDescription + "\n" +
                        movieGenre + "\n" +
                        sBarText.getText().toString() + "\n" +
                        movieYear + "\n" +
                        imdbRating);
                if ( erno ) {
                    Intent intent = new Intent();
                    intent.putExtra("data", movie);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                erno = true;
            }
        });
    }
}
