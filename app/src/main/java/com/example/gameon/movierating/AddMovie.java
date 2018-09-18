/****************************************
 * Assignment HW3                       *
 * AddMovie.java                        *
 * Jarrod Norris, Andrew Schelesinger   *
 ****************************************/

package com.example.gameon.movierating;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public String regularExpression = "^\\d{4}$";
    public String imdbURL = "";
    public int min = 1921;
    public int max = 2100;
    public boolean erno = true;
    Movie movie = new Movie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        // Gets intent from MainActivity
        Intent intent = getIntent();


        // Gets the spinner and creates the dropdown list and adds String[] names to it
        final Spinner genre = findViewById(R.id.spinner);
        String[] genres = new String[]{"Action", "Animation", "Comedy", "Documentary", "Family", "Horror", "Crime", "Others"};
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genres);
        genre.setAdapter(adapt);
        // Gets the seekBar and checks for changes, then displays the changes in a TextView
        final SeekBar sb = findViewById(R.id.seekBar);
        final TextView sBarText = findViewById(R.id.sBarTextView);
        sBarText.setText("0");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sb.setKeyProgressIncrement(1);
                String displayText = "" + progress;
                sBarText.setText(displayText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Checks if the intent from MainActivity has extras and if not goes to Add Movie section
        if ( intent.getExtras() == null ) {

            findViewById(R.id.addMovieBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets the name of movie, tests it for empty and displays toast if is requiring name, else adds name to movie object
                    TextInputLayout name = findViewById(R.id.movieNameTextInput);
                    movieName = name.getEditText().getText().toString();
                    Log.d("Items", "Name is " + movieName.isEmpty());
                    if (movieName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.movie_name_issue), Toast.LENGTH_LONG).show();
                        erno = false;
                    } else {
                        movie.setName(movieName);
                    }
                    // Gets the description of movie, tests it for empty and sets desc to unknown, else adds desc to movie object
                    EditText desc = findViewById(R.id.multiDescription);
                    movieDescription = desc.getText().toString();
                    if ( movieDescription.isEmpty() ) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.desc_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    } else {
                        movie.setDescription(movieDescription);
                    }
                    // Gets the selected genre if none adds default to movie object
                    movieGenre = genre.getSelectedItem().toString();
                    movie.setGenre(movieGenre);
                    // Sets the current setting of seekbar rating to movie object, if none selected sets to 0
                    movie.setRating(sBarText.getText().toString());
                    /* Gets the year and tests it for empty, if is sets movie object year to null, else
                     * tests year against regular expression for correct format, then checks if in between certain values.
                     * If less than 1921 the toasts needs greater than, if greater than 2018, toasts needs less than.
                     * Else adds year to movie object
                     */
                    TextInputLayout year = findViewById(R.id.yearTextInput);
                    movieYear = year.getEditText().getText().toString();
                    if ( !movieYear.isEmpty() ) {
                        Log.d("Items", "Made it past regular expression in year");
                        if (!movieYear.matches(regularExpression) || Integer.parseInt(movieYear) < min ||
                                Integer.parseInt(movieYear) > max) {
                            if (!movieYear.matches(regularExpression)) {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_size), Toast.LENGTH_LONG).show();
                                erno = false;
                            } else if (Integer.parseInt(movieYear) < min) {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_less_than), Toast.LENGTH_LONG).show();
                                erno = false;
                            } else {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_greater_than), Toast.LENGTH_LONG).show();
                                erno = false;
                            }
                        } else {
                            movie.setMovieYear(movieYear);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    }
                    /* Gets the imdb url and validates it using the built-in function URLUtil.isValid
                     * If not valid toasts needs correct format, else adds url or null to movie object
                     */
                    TextInputLayout imdb = findViewById(R.id.imdbTextInput);
                    imdbURL = imdb.getEditText().getText().toString();
                    if ( !imdbURL.isEmpty() ) {
                        Log.d("Items", "Made it past regular expression in imdb");
                        if ( !URLUtil.isValidUrl(imdbURL)) {
                            imdb.getEditText().setText(null);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_format_issue), Toast.LENGTH_LONG);
                            erno = false;
                        } else {
                            movie.setImdbRating(imdbURL);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    }
                    // Test log
                    Log.d("Items", "\n" +
                            movieName + "\n" +
                            movieDescription + "\n" +
                            movieGenre + "\n" +
                            sBarText.getText().toString() + "\n" +
                            movieYear + "\n" +
                            imdbURL);
                    // Checks if erno is true, if any of the above functions fail this will be false
                    if (erno) {
                        Intent intent = new Intent();
                        intent.putExtra("data", movie);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    // Resets erno to true for next pass
                    erno = true;
                }
            });
        } else { // If intent from MainActivity has extras, goes to EditMovie section
            // From line 170-214 gets the movie attributes from intent and populates fields of activity
            Bundle extras = intent.getExtras();
            Movie oldMovie = extras.getParcelable("Movie");
            final int position = extras.getInt("Position");

            TextView activityTitle = findViewById(R.id.addEditTitleTextView);
            activityTitle.setText("Edit Movie");

            Button btn = findViewById(R.id.addMovieBtn);
            btn.setText("Edit Movie");

            TextInputLayout name = findViewById(R.id.movieNameTextInput);
            name.getEditText().setText(oldMovie.getName());

            EditText desc = findViewById(R.id.multiDescription);
            desc.setText(oldMovie.getDescription());

            movieGenre = oldMovie.getGenre();

            switch (movieGenre) {
                case "Action":      genre.setSelection(0);
                                    break;
                case "Animation":   genre.setSelection(1);
                                    break;
                case "Comedy":      genre.setSelection(2);
                                    break;
                case "Documentary": genre.setSelection(3);
                                    break;
                case "Family":      genre.setSelection(4);
                                    break;
                case "Horror":      genre.setSelection(5);
                                    break;
                case "Crime":       genre.setSelection(6);
                                    break;
                case "Others":      genre.setSelection(7);
                                    break;
            }

            movieRating = oldMovie.getRating();
            sBarText.setText(movie.getRating());

            TextInputLayout year = findViewById(R.id.yearTextInput);
            year.getEditText().setText(oldMovie.getMovieYear());

            TextInputLayout imdb = findViewById(R.id.imdbTextInput);
            imdb.getEditText().setText(oldMovie.getImdbRating());


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets the name of movie, tests it for empty and displays toast if is requiring name, else adds name to movie object
                    TextInputLayout name = findViewById(R.id.movieNameTextInput);
                    movieName = name.getEditText().getText().toString();
                    Log.d("Items", "Name is " + movieName.isEmpty());
                    if (movieName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.movie_name_issue), Toast.LENGTH_LONG).show();
                        erno = false;
                    } else {
                        movie.setName(movieName);
                    }
                    // Gets the description of movie, tests it for empty and sets desc to unknown, else adds desc to movie object
                    EditText desc = findViewById(R.id.multiDescription);
                    movieDescription = desc.getText().toString();
                    if (movieDescription.equals("")) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.desc_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    } else {
                        movie.setDescription(movieDescription);
                    }
                    // Gets the selected genre if none adds default to movie object
                    movieGenre = genre.getSelectedItem().toString();
                    movie.setGenre(movieGenre);
                    // Sets the current setting of seekbar rating to movie object, if none selected sets to 0
                    movie.setRating(sBarText.getText().toString());
                    /* Gets the year and tests it for empty, if is sets movie object year to null, else
                     * tests year against regular expression for correct format, then checks if in between certain values.
                     * If less than 1921 the toasts needs greater than, if greater than 2018, toasts needs less than.
                     * Else adds year to movie object
                     */
                    TextInputLayout year = findViewById(R.id.yearTextInput);
                    movieYear = year.getEditText().getText().toString();
                    if ( !movieYear.isEmpty() ) {
                        Log.d("Items", "Made it past regular expression in year");
                        if (!movieYear.matches(regularExpression) || Integer.parseInt(movieYear) < min ||
                                Integer.parseInt(movieYear) > max) {
                            if (!movieYear.matches(regularExpression)) {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_size), Toast.LENGTH_LONG).show();
                                erno = false;
                            } else if (Integer.parseInt(movieYear) < min) {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_less_than), Toast.LENGTH_LONG).show();
                                erno = false;
                            } else {
                                year.getEditText().setText(null);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_greater_than), Toast.LENGTH_LONG).show();
                                erno = false;
                            }
                        } else {
                            movie.setMovieYear(movieYear);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.year_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    }
                    /* Gets the imdb url and validates it using the built-in function URLUtil.isValid
                     * If not valid toasts needs correct format, else adds url or null to movie object
                     */
                    TextInputLayout imdb = findViewById(R.id.imdbTextInput);
                    imdbURL = imdb.getEditText().getText().toString();
                    if ( !imdbURL.isEmpty() ) {
                        Log.d("Items", "Made it past regular expression in imdb");
                        if ( !URLUtil.isValidUrl(imdbURL)) {
                            imdb.getEditText().setText(null);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_format_issue), Toast.LENGTH_LONG);
                            erno = false;
                        } else {
                            movie.setImdbRating(imdbURL);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.imdb_req), Toast.LENGTH_LONG).show();
                        erno = false;
                    }
                    // Test log
                    Log.d("Items", "\n" +
                            movieName + "\n" +
                            movieDescription + "\n" +
                            movieGenre + "\n" +
                            sBarText.getText().toString() + "\n" +
                            movieYear + "\n" +
                            imdbURL + "\n" +
                            position);
                    // Checks if erno is true, if any of the above functions fail this will be false
                    if (erno) {
                        Intent intent = new Intent();
                        intent.putExtra("data", movie);
                        intent.putExtra("position", position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    // Resets erno to true for next pass
                    erno = true;
                }
            });
        }
    }
}
