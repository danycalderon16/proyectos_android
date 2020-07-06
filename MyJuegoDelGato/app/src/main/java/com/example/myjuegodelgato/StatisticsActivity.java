package com.example.myjuegodelgato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.myjuegodelgato.Util.getCounterP1Saved;
import static com.example.myjuegodelgato.Util.getCounterP2Saved;
import static com.example.myjuegodelgato.Util.getCounterTiedSaved;
import static com.example.myjuegodelgato.Util.goHome;
import static com.example.myjuegodelgato.Util.saveCounterP1;
import static com.example.myjuegodelgato.Util.saveCounterP2;
import static com.example.myjuegodelgato.Util.saveCounterTied;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;

    private SharedPreferences pref;
    private int winPlayer1 = 0;
    private int winPlayer2 = 0;
    private int tiedGames  = 0;

    private TextView txtP1 ;
    private TextView txtP2;
    private TextView tied ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        setSupportActionBar(toolbar);

        pref = getSharedPreferences("counters", Context.MODE_PRIVATE);

        sendBind();
        setToolbar();
        try{
            winPlayer1 =  getCounterP1Saved(pref,winPlayer1);
            winPlayer2 =  getCounterP2Saved(pref,winPlayer2);
            tiedGames  =  getCounterTiedSaved(pref,tiedGames);
        }catch(NullPointerException e){
            winPlayer1 =  0;
            winPlayer2 =  0;
            tiedGames = 0;
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendTxt();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_statistics));
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendBind(){
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fabRefresh);
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        tied = findViewById(R.id.txtTied);
        fab.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        goHome(this,MainActivity.class);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goHome(this,MainActivity.class); return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private  void sendTxt(){
        txtP1.setText(String.format(Locale.getDefault(), "%d", winPlayer1));
        txtP2.setText(String.format(Locale.getDefault(), "%d", winPlayer2));
        tied.setText(String.format(Locale.getDefault(), "%d", tiedGames));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabRefresh:
                refreshCounters();
                break;
        }
    }

    private void refreshCounters() {
        winPlayer1 = 0;
        winPlayer2 = 0;
        tiedGames  = 0;
        saveCounterP1(pref,winPlayer1);
        saveCounterP2(pref,winPlayer2);
        saveCounterTied(pref,tiedGames);
        sendTxt();
    }
}
