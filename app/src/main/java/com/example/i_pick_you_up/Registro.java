package com.example.i_pick_you_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText mEdittextNombre;
    private EditText mEdittextApellido;
    private EditText mEdittextEmail;
    private EditText mEdittextPassword;
    private Button mButtonRegistro;

    //Variables de datos a REGISTRAR
    private String name = "";
    private String lastname = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEdittextNombre = (EditText) findViewById(R.id.editTextNombre);
        mEdittextApellido = (EditText) findViewById(R.id.editTextApellido);
        mEdittextEmail = (EditText) findViewById(R.id.editTextemail);
        mEdittextPassword = (EditText) findViewById(R.id.editTextpass);
        mButtonRegistro = (Button) findViewById(R.id.BTNregis);

        mButtonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mEdittextNombre.getText().toString();
                lastname = mEdittextApellido.getText().toString();
                email = mEdittextEmail.getText().toString();
                password = mEdittextPassword.getText().toString();

                if (!name.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !password.isEmpty())
                //if (!name.isEmpty() && !password.isEmpty())
                {
                    if (password.length() >=6)
                    {
                        Toast.makeText(Registro.this, "REGISTRO EXITOSO!", Toast.LENGTH_SHORT).show();
                        registerUser();
                    }
                    else
                    {
                        Toast.makeText(Registro.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Registro.this, "No haz completado el Registro", Toast.LENGTH_SHORT).show();
                }
                }
        });


    }

    private void  registerUser()
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("lastname", lastname);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Registro.this, DahsboardActivity.class));
                                finish();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(Registro.this, "Haz ingresado mal los datos en algun campo, Revisa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void Anterior(View view2)
    {
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }
}
