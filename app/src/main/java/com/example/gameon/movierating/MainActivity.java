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

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddMovie.class);
                startActivityForResult(addIntent, ADD_REQ_CODE);
            }
        });

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
    }

    DialogInterface.OnClickListener deleteMovie = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            movies.remove(which);
        }
    };

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

            } else {

            }

        } else if ( requestCode == EDIT_REQ_CODE && data != null ) {
            if ( resultCode == RESULT_OK ) {
                Log.d("Items", "Made it into return result");
                Bundle bundle = data.getExtras();
                movie = bundle.getParcelable("data");
                if ( movie != null ) {
                    movies.set(bundle.getInt("position"), movie);
                }
                Log.d("Items", movie.name);
            } else if ( resultCode == RESULT_CANCELED ) {

            } else {

            }
        }
    }


}
