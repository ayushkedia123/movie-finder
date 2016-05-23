package com.ayush.moviefinder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import com.ayush.moviefinder.R;
import com.ayush.moviefinder.view.AppConstant;

public class MainActivity extends Activity implements OnItemSelectedListener {

    private Spinner spinner;
    private EditText searchEditText;
    private EditText yearEditText;
    private String searchText;
    private String yearText;
    private String movieType;
    private TextView footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        /* Spinner to select movie type */

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString().trim();
                yearText = yearEditText.getText().toString().trim();
                Intent i = new Intent(MainActivity.this, MoviesListActivity.class);
                i.putExtra(AppConstant.intentExtras.SEARCH_TEXT, searchText);
                i.putExtra(AppConstant.intentExtras.YEAR_TEXT, yearText);
                i.putExtra(AppConstant.intentExtras.MOVIE_TYPE, movieType);
                MainActivity.this.startActivity(i);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

    /* Initialising the UI components */

    private void initUI() {
        searchEditText = (EditText) findViewById(R.id.et_search);
        yearEditText = (EditText) findViewById(R.id.et_year_of_release);
        spinner = (Spinner) findViewById(R.id.selection_spinner);
        footer = (TextView) findViewById(R.id.footer_search);
    }


    /**
     * Getting the movie type that is selected by the user.
     * If the user selects "All" then sending empty type to the server so that we will get the complete list
     */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        movieType = parent.getItemAtPosition(position).toString();
        if (movieType.equalsIgnoreCase("All"))
            movieType = "";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
