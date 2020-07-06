package com.example.myappprestamos.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.myappprestamos.Adapters.MyAdapterPerson;
import com.example.myappprestamos.Models.Person;
import com.example.myappprestamos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import static com.example.myappprestamos.Utils.Util.BIWEEKLY;
import static com.example.myappprestamos.Utils.Util.NO_ADDED;
import static com.example.myappprestamos.Utils.Util.getCounterId;
import static com.example.myappprestamos.Utils.Util.getDate;
import static com.example.myappprestamos.Utils.Util.loadDataFromPerson;
import static com.example.myappprestamos.Utils.Util.saveCounterId;
import static com.example.myappprestamos.Utils.Util.saveDataPerson;
import static com.example.myappprestamos.Utils.Util.setDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Person> personList;
    private MyAdapterPerson myAdapterPerson;
    private RecyclerView.LayoutManager manager;

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CoordinatorLayout coordy;

    private TextInputLayout nombre;
    private TextInputLayout cantidad;
    private TextInputLayout plazos;
    private TextInputLayout pagos;
    private TextView fecha;
    private RecyclerView recyclerView;

    private SharedPreferences prefCounter;
    private SharedPreferences prefPerson;

    private int countId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPreferences();
        sendBind();
        getDate(fecha);
        setToolbar();
        sendRecycler();

    }

    private void setToolbar(){
        toolbar.setTitle(getString(R.string.prestamos));
        setSupportActionBar(toolbar);

    }
    private void setPreferences() {
        prefCounter = getSharedPreferences("counter",MODE_PRIVATE);
        prefPerson  = getSharedPreferences("preferencesMain",MODE_PRIVATE);
        countId = getCounterId(prefCounter,0);
    }

    private void sendBind() {
        countId = getCounterId(prefCounter,0);

        toolbar = findViewById(R.id.toolbarMain);
        fab = findViewById(R.id.addPerson);
        recyclerView = findViewById(R.id.recyclerViewPerson);
        coordy = findViewById(R.id.cordyMain);
        nombre = findViewById(R.id.nombre);
        cantidad = findViewById(R.id.cantidad);
        plazos = findViewById(R.id.plazos);
        fecha = findViewById(R.id.fecha);
        pagos = findViewById(R.id.pagos);

        fecha.setOnClickListener(this);
        fab.setOnClickListener(this);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showInfoDialog();
                return false;
            }
        });
    }

    private void sendRecycler() {
        personList = loadDataFromPerson(prefPerson,personList);

        manager = new LinearLayoutManager(this);
        myAdapterPerson = new MyAdapterPerson(personList,this,this,
                new MyAdapterPerson.OnItemClickListener() {
                    @Override
                    public void onItemClick(Person person, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("positionList",position);
                        intent.putExtra("positionID",person.getPositionID());
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapterPerson);
    }

    private void showInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater  = getLayoutInflater();
        View v = inflater.inflate(R.layout.info_layout,null);

        int recuperar = 0;
        int ganar = 0;

        int infoQuantity = 0;
        int infoSaldo = 0;
        int saldoInicial = 0;

        TextView infoRecuperar = v.findViewById(R.id.txtRecuperar);
        TextView infoGanar     = v.findViewById(R.id.txtGanar);
        TextView infoTotal     = v.findViewById(R.id.txtTotal);

        for(int i = 0; i < myAdapterPerson.getItemCount();i++){
            int abonado = 0;
            infoQuantity = personList.get(i).getQuantity();
            saldoInicial = personList.get(i).getPagos() *  personList.get(i).getPlazos();
            infoSaldo = personList.get(i).getSaldo();

            abonado = saldoInicial - infoSaldo;

            if(infoQuantity>abonado){
                recuperar += (infoQuantity-abonado);
                ganar += (saldoInicial-infoQuantity);
            }else{
                ganar += (saldoInicial-abonado);
            }
        }

        infoRecuperar.setText(String.format(Locale.getDefault(),"$%d",recuperar));
        infoGanar.setText(String.format(Locale.getDefault(),"$%d",ganar));
        infoTotal.setText(String.format(Locale.getDefault(),"$%d",(ganar+recuperar)));

        builder.setCancelable(true);
        builder.create();
        builder.show();

        builder.setView(v);
        builder.show();
    }

    private boolean validateEditText(TextInputLayout textInputLayout) {
        String text = textInputLayout.getEditText().getText().toString().trim();
        if (text.isEmpty()) {
            textInputLayout.setError("Campo requerido");
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));
            }else{
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
            }
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
    private void addPerson() {
        int position = personList.size();
        int saldo = 0;
        if (!validateEditText(nombre) | !validateEditText(cantidad) |
                !validateEditText(plazos) | !validateEditText(pagos))
            return;
        saldo = Integer.parseInt(plazos.getEditText().getText().toString()) *
                Integer.parseInt(pagos.getEditText().getText().toString());
        personList.add(new Person(nombre.getEditText().getText().toString(),
                Integer.parseInt(cantidad.getEditText().getText().toString()),
                Integer.parseInt(plazos.getEditText().getText().toString()),
                Integer.parseInt(pagos.getEditText().getText().toString()),
                saldo,
                fecha.getText().toString(),
                fecha.getText().toString(),
                countId,
                NO_ADDED,BIWEEKLY));
        countId++;

        saveCounterId(prefCounter,countId);

        recyclerView.scrollToPosition(position);
        myAdapterPerson.notifyItemInserted(position);
        nombre.getEditText().setText("");
        cantidad.getEditText().setText("");
        pagos.getEditText().setText("");
        plazos.getEditText().setText("");
        getDate(fecha);
        nombre.requestFocus();
        saveDataPerson(prefPerson,personList);
        Snackbar.make(coordy,"Prestamo agregado",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_completed,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_completed:
                Intent intent = new Intent(this, CompletedActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPerson:
                addPerson();
                break;
            case R.id.fecha:
                setDate(this, fecha);
                break;
        }
    }
}
