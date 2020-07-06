package mx.tecnm.tepic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatosGeneralesActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etNC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);

        etNombre = findViewById(R.id.editTextNombre);
        etNC = findViewById(R.id.editTextNC);

    }

    public void enviarMensaje(View view){
        String nombre = etNombre.getText().toString();
        String numeroControl = etNC.getText().toString();

        String texto = String.format("Enviando ... No.Control:%s - %s",numeroControl,nombre);

        Toast mensaje = Toast.makeText(this, texto, Toast.LENGTH_SHORT);
        mensaje.show();
    }
}
