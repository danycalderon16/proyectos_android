package com.example.myjuegodelgato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static com.example.myjuegodelgato.Util.PLAYER_ONE_VS_COMPUTER;
import static com.example.myjuegodelgato.Util.PLAYER_ONE_VS_PLAYER_TWO;
import static com.example.myjuegodelgato.Util.getSettingsPlayer;
import static com.example.myjuegodelgato.Util.goHome;
import static com.example.myjuegodelgato.Util.saveSettingsPlayer;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup familyRadio;
    private RadioButton first;
    private RadioButton second;
    private Toolbar toolbar;

    private SharedPreferences pref;

    String s = Build.MODEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        pref = getSharedPreferences("settings",MODE_PRIVATE);

        setToolbar();
        sendBind();
        setRadioValues();
    }

    private void setRadioValues() {
        int i = getSettingsPlayer(pref);
        if(i==PLAYER_ONE_VS_PLAYER_TWO)
            first.setChecked(true);
        if(i==PLAYER_ONE_VS_COMPUTER)
            second.setChecked(true);
    }

    private void sendBind(){
        familyRadio = findViewById(R.id.select_players_group);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        familyRadio.setOnCheckedChangeListener(this);
        second.setText(getResources().getString(R.string.p_one)+" vs "+s);

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbarSt);
        toolbar.setTitle("Configuraciones");
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                goHome(this,MainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        setRadioValues();
        super.onRestart();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.first:
                saveSettingsPlayer(pref, PLAYER_ONE_VS_PLAYER_TWO);
                break;
            case R.id.second:
                saveSettingsPlayer(pref, PLAYER_ONE_VS_COMPUTER);
                break;
        }
    }
}