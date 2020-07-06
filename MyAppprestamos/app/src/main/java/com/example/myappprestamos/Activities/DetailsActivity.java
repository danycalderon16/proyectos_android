package com.example.myappprestamos.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.myappprestamos.Adapters.MyAdapterDetails;
import com.example.myappprestamos.Models.Details;
import com.example.myappprestamos.Models.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappprestamos.R;

import java.util.List;
import java.util.Locale;

import static com.example.myappprestamos.Utils.Util.ABONO;
import static com.example.myappprestamos.Utils.Util.ADDED;
import static com.example.myappprestamos.Utils.Util.BIWEEKLY;
import static com.example.myappprestamos.Utils.Util.RESTAR;
import static com.example.myappprestamos.Utils.Util.SUMAR;
import static com.example.myappprestamos.Utils.Util.WEEKLY;
import static com.example.myappprestamos.Utils.Util.getDate;
import static com.example.myappprestamos.Utils.Util.goMain;
import static com.example.myappprestamos.Utils.Util.loadDataCompleted;
import static com.example.myappprestamos.Utils.Util.loadDataFromDetails;
import static com.example.myappprestamos.Utils.Util.loadDataFromPerson;
import static com.example.myappprestamos.Utils.Util.saveDataCompleted;
import static com.example.myappprestamos.Utils.Util.saveDataDetails;
import static com.example.myappprestamos.Utils.Util.saveDataPerson;
import static com.example.myappprestamos.Utils.Util.setDate;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private List<Person> personList;
    private List<Details> detailsList;
    private List<Person> completedList;
    private RecyclerView recyclerView;
    private MyAdapterDetails myAdapterDetails;
    private RecyclerView.LayoutManager manager;

    private FloatingActionButton fabAbono;
    private Toolbar toolbar;

    private String nombre;
    private TextView tvFecha;
    private TextView tvCantidad;
    private TextView tvSaldo;
    private TextView tvSaldoInicai;
    private TextView tvPagos;
    private TextView changeMethod;

    private int abono = 0;

    private String fecha;
    private int pagos;
    private int plazos;
    private int cantidad;
    private int saldo;

    private TextView fechaPick;
    private EditText etAbono;

    private int position;
    private int positionId;
    private int positionDelete;
    private int pays = 0;
    private int change = 0;
    //private int counter = 0;

    private int positionList = 0;

    private SharedPreferences prefCounter;
    private SharedPreferences prefCounterID;
    private SharedPreferences prefPerson;
    private SharedPreferences prefDetails;
    private SharedPreferences prefCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            positionList = bundle.getInt("positionList");//lista del recyclerView del Main
            positionId = bundle.getInt("positionID");//id unico de cada item del recyclerView del Main
        }
        getPreferences();
        sendBind();
        //counter = getCounterDetails(prefCounter,myAdapterDetails,0);
        setToolbar();
        getDate(fechaPick);
        sendRecyclerView();
        setDataFromPerson();
    }

    private void getPreferences() {
        prefPerson    = getSharedPreferences("preferencesMain",MODE_PRIVATE);
        prefCounterID   = getSharedPreferences("counter",MODE_PRIVATE);
        prefCounter   = getSharedPreferences("counter" + positionId,MODE_PRIVATE);
        prefDetails   = getSharedPreferences("preferencesDetails" + positionId, MODE_PRIVATE);
        prefCompleted = getSharedPreferences("preferencesCompleted",MODE_PRIVATE);
    }

    private void setDataFromPerson() {
        if(myAdapterDetails != null)pays= myAdapterDetails.getItemCount();
        tvFecha.setText(fecha);
        tvCantidad.setText(String.format(Locale.getDefault(), "$%d", cantidad));
        tvSaldo.setText(String.format(Locale.getDefault(), "$%d", saldo));
        tvPagos.setText(String.format(Locale.getDefault(), "%d/%d", pays, plazos));
        tvSaldoInicai.setText(String.format(Locale.getDefault(), "$%d", (pagos * plazos)));
    }

    private void setToolbar() {
        toolbar.setTitle(nombre);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void sendRecyclerView() {
        detailsList = loadDataFromDetails(prefDetails,detailsList, positionId);

        manager = new LinearLayoutManager(this);
        myAdapterDetails = new MyAdapterDetails(detailsList, this, positionId,
                new MyAdapterDetails.OnItemEventListener() {
                    @Override
                    public void onMoreClicked(Details details, int position, View v) {
                        positionDelete = position;
                        PopupMenu popup = new PopupMenu(DetailsActivity.this, v);
                        popup.setOnMenuItemClickListener(DetailsActivity.this);
                        popup.inflate(R.menu.menu_main);
                        popup.show();
                    }
                });
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnCreateContextMenuListener(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapterDetails);
    }

    private void sendBind() {
        personList = loadDataFromPerson(prefPerson,personList);
        completedList = loadDataCompleted(prefCompleted,completedList);
        toolbar = findViewById(R.id.toolbarDetails);
        fabAbono = findViewById(R.id.fabAbono);
        tvFecha = findViewById(R.id.fechaDetails);
        tvCantidad = findViewById(R.id.cantidadDetails);
        tvSaldo = findViewById(R.id.saldodDetails);
        tvSaldoInicai = findViewById(R.id.saldoInicial);
        fechaPick = findViewById(R.id.fechaDetailsPick);
        etAbono = findViewById(R.id.abono);
        recyclerView = findViewById(R.id.rvDetails);
        tvPagos = findViewById(R.id.pagosDetails);
        changeMethod = findViewById(R.id.changeMethod);

        nombre = personList.get(positionList).getName();
        fecha = personList.get(positionList).getFechaInicial();
        pagos = personList.get(positionList).getPagos();
        plazos = personList.get(positionList).getPlazos();
        cantidad = personList.get(positionList).getQuantity();
        saldo = personList.get(positionList).getSaldo();

        fabAbono.setOnClickListener(this);
        fechaPick.setOnClickListener(this);
        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(Person p : personList){
                    p.setPayment(BIWEEKLY);
                }
                return false;
            }
        });
        changeMethod.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                changeMethod();
                Toast.makeText(DetailsActivity.this, "Ha cambiado forma de pago", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void changeMethod() {
        int i = personList.get(positionList).getPayment();
        if(i == WEEKLY)
            personList.get(positionList).setPayment(BIWEEKLY);
        if(i == BIWEEKLY)
            personList.get(positionList).setPayment(WEEKLY);
        saveDataPerson(prefPerson,personList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAbono:
                addItem(ABONO);
                break;
            case R.id.fechaDetailsPick:
                setDate(this, fechaPick);
                break;
        }
    }

    private void addItem(int type) {
        position = detailsList.size();

        if (etAbono.getText().toString().isEmpty()) {
            etAbono.setError("Ingrese cantidad");
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));
            }else{
                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
            }
        } else {
            abono = Integer.parseInt(etAbono.getText().toString());
            detailsList.add(new Details(nombre,fechaPick.getText().toString(),abono,type));
            recyclerView.scrollToPosition(position);
            myAdapterDetails.notifyItemInserted(position);
            getDate(fechaPick);
            etAbono.setText("");
            etAbono.requestFocus();
            saveDataDetails(prefDetails,detailsList, positionId);
            personList.get(positionList).setFechaFinal(fechaPick.getText().toString());
            updateSaldo(abono,RESTAR);
            pays++;
            tvPagos.setText(String.format(Locale.getDefault(), "%d/%d", pays, plazos));
            if(checkIfCompleted()) {
                confirmCompleted("Ha compledado el total de pagos\n¿Desea agregarlo a  prestamo completado?");
            }
            //saveCounterDetails(prefCounter,positionId,counter);
        }

    }

    private boolean checkIfCompleted(){
        if(pays == plazos)
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goMain(this);
                return true;
            case R.id.addFinish:
                confirmCompleted("¿Desea agregar prestamo completado?");
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void addCompleted() {
        if(personList.get(positionList).getAdded()!=ADDED) {
            personList.get(positionList).setAdded(ADDED);
            completedList.add(personList.get(positionList));
            saveDataPerson(prefPerson,personList);
            saveDataCompleted(prefCompleted,completedList);
            Toast.makeText(this,completedList.get(positionList).toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Prestamo completado ya ha sido agregado",Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmCompleted(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCompleted();
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateSaldo(int abono,int type) {
        int saldoParcial = saldo;
        if(type == RESTAR) saldo = saldoParcial-abono;
        if(type == SUMAR) saldo = saldoParcial+abono;
        tvSaldo.setText(String.format(Locale.getDefault(), "$%d", saldo));
        personList.get(positionList).setSaldo(saldo);
        saveDataPerson(prefPerson,personList);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Details d = detailsList.get(positionDelete);
        switch (menuItem.getItemId()) {
            case R.id.delete:
                String m = "¿Desea borrar abono de $" + d.getCantidad() + ",\n del " + d.getFecha() + "?";
                showConfirmDeleteDialog(d,m);
                return true;
            default:
                return false;
        }
    }

    private void deleteAbono(Details d) {
        int desc = d.getCantidad();
        updateSaldo(desc,SUMAR);
        detailsList.remove(positionDelete);
        myAdapterDetails.notifyItemRemoved(positionDelete);
        saveDataDetails(prefDetails,detailsList, positionId);
        Toast.makeText(this, "Borrado exitoso", Toast.LENGTH_SHORT).show();
        pays--;
        tvPagos.setText(String.format(Locale.getDefault(), "%d/%d", pays, plazos));
        //saveCounterDetails(prefCounter,positionId,counter);
    }

    public void showConfirmDeleteDialog(final Details d,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAbono(d);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
