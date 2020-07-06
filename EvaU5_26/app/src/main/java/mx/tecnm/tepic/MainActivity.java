package mx.tecnm.tepic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText idUser;
    private EditText user;
    private EditText email;
    private EditText pass;
    private EditText idPais;

    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idUser = findViewById(R.id.editText_id_user);
        user = findViewById(R.id.editText_user);
        email = findViewById(R.id.editText_email);
        pass = findViewById(R.id.editText_pass);
        idPais = findViewById(R.id.editText_id_pais);
        btn = findViewById(R.id.button_send_data);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_usuario = idUser.getText().toString();
                String usuario = user.getText().toString();
                String correo = email.getText().toString();
                String contraseña = pass.getText().toString();
                String id_pais = idPais.getText().toString();

                if(id_usuario.isEmpty() || usuario.isEmpty()|| correo.isEmpty() || contraseña.isEmpty() || id_pais.isEmpty())
                    Toast.makeText(MainActivity.this, "Por favor, llene los campos solicitados", Toast.LENGTH_SHORT).show();
                else {
                    String text = "ID USUARIO: "+id_usuario+"" +
                            "\nUSUARIO: "+usuario+"" +
                            "\nCORREO ELECTRONICO: "+correo+"" +
                            "\nCONTRASEÑA: "+contraseña+"" +
                            "\nID PAIS_ "+id_pais;
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }

                usuario = correo = contraseña = id_pais = id_usuario = "";
            }
        });

    }

}
