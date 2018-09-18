/****************************************
 * Assignment HW3                       *
 * MainActivity.java                    *
 * Jarrod Norris, Andrew Schelesinger   *
 ****************************************/

package com.example.gameon.movierating;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQ_CODE = 10;
    public static final int EDIT_REQ_CODE = 20;
    ArrayList<Movie> movies;
    Movie movie;

    public MainActivity() {
        movies = new ArrayList<>();
        movie = new Movie();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        try{
            movies = bundle.getParcelableArrayList("original");
        }catch (NullPointerException e){
        }

        // Gets the add button and creates an intent that is sent to AddMovie activity
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddMovie.class);
                startActivityForResult(addIntent, ADD_REQ_CODE);
            }
        });
        /* Gets Edit button and displays an alert with a list of movies then
         * creates an intent and adds the selected movie and sends it along with
         * the current  position in the list to AddMovie activity
         */
        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder listMovies = new AlertDialog.Builder(MainActivity.this);
                listMovies.setTitle("Pick a Movie");

                final ArrayList<String> names = new ArrayList<>();
                for ( int i = 0; i < movies.size(); i++ ) {
                    names.add(movies.get(i).getName());
                }
                Log.d("Items", names.toString());
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_item, names);
                Log.d("Items", "Made it past the arrayadapter");

                listMovies.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Items", "Made it in onClick" + adapter.getItem(which));
                        String name = adapter.getItem(which);
                        Log.d("Itmes", "The position of the movie we are sending to edit is " + which);
                        dialog.dismiss();
                        if ( !name.isEmpty() ) {
                            Intent editIntent = new Intent(getApplicationContext(), AddMovie.class);
                            editIntent.putExtra("Movie", movies.get(which));
                            editIntent.putExtra("Position", which);
                            editIntent.putExtra("Edit", true);
                            startActivityForResult(editIntent, EDIT_REQ_CODE);
                        } else {

                        }
                    }
                });
                listMovies.show();
            }
        });
        // Gets the delete button and displays a list of movies and then deletes the selected movie
        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] names = new String[movies.size()];
                for ( int i = 0; i < movies.size(); i++ ) {
                    names[i]= movies.get(i).getName();
                }
                AlertDialog listMovies = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Pick a Movie")
                        .setItems(names, deleteMovie)
                        .create();
                listMovies.show();
            }
        });

        // Gets the show by year button and opens the show activity allowing the user to scrub by year
        findViewById(R.id.showByYearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movies.size() > 0) {
                    ArrayList<Movie> sortedMovies = movies;
                    Collections.sort(sortedMovies, new Comparator<Movie>() {
                        public int compare(Movie o1, Movie o2) {
                            return o1.getMovieYear().compareTo(o2.getMovieYear());
                        }
                    });
                    System.out.print(sortedMovies);
                    Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                    intent.putExtra("header", "Movies by Year");
                    intent.putParcelableArrayListExtra("movies", sortedMovies);
                    intent.putParcelableArrayListExtra("original", movies);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Add a movie before Viewing", Toast.LENGTH_LONG);
                }
            }
        });

        // Gets the show by rating button and opens the show activity allowing the user to scrub by rating
        findViewById(R.id.showByRatingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movies.size() > 0) {
                    ArrayList<Movie> sortedMovies = movies;
                    Collections.sort(sortedMovies, new Comparator<Movie>() {
                        public int compare(Movie o1, Movie o2) {
                            return o1.getRating().compareTo(o2.getMovieYear());
                        }
                    });
                    System.out.print(sortedMovies.toString());
                    Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                    intent.putExtra("header", "Movies by Rating");
                    intent.putParcelableArrayListExtra("movies", sortedMovies);
                    intent.putParcelableArrayListExtra("original", movies);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Add a movie before Viewing", Toast.LENGTH_LONG);
                }
            }
        });
    }

    DialogInterface.OnClickListener deleteMovie = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            movies.remove(which);
        }
    };
    // This is the function that captures the returned data from the add movie intent issued above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == ADD_REQ_CODE && data != null ) {
            if ( resultCode == RESULT_OK ) {
                Log.d("Items", "Made it into return result");
                Bundle bundle = data.getExtras();

                movie = bundle.getParcelable("data");
                if ( movie != null ) {
                    movies.add(movie);
                }
                Log.d("Items", movie.name);
            } else if ( resultCode == RESULT_CANCELED ) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.intent_canceled), Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.other_issue), Toast.LENGTH_LONG);
            }

        } else if ( requestCode == EDIT_REQ_CODE && data != null ) { // Gets the return for edit intent issued above
            if ( resultCode == RESULT_OK ) {
                Log.d("Items", "Made it into return result");
                Bundle bundle = data.getExtras();

                Log.d("Items", "THis is the position sent back with intent " + bundle.getInt("position"));
                movie = bundle.getParcelable("data");
                if ( movie != null ) {
                    movies.set(bundle.getInt("position"), movie);
                }
                Log.d("Items", movie.name);
            } else if ( resultCode == RESULT_CANCELED ) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.intent_canceled), Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.other_issue), Toast.LENGTH_LONG);
            }
        }
    }


}
