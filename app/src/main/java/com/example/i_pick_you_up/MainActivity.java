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

public class MainActivity extends AppCompatActivity {

    private EditText mEdittextemail;
    private EditText mEdittextPass;
    private Button mButtonLogin;

    private  String email = "";
    private  String password = "";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        mEdittextemail = (EditText) findViewById(R.id.editTextemail2);
        mEdittextPass = (EditText) findViewById(R.id.editText2);
        mButtonLogin = (Button) findViewById(R.id.LoginBTN);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEdittextemail.getText().toString();
                password = mEdittextPass.getText().toString();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    loginUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Campos Incompletos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void  loginUser()
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this, DahsboardActivity.class));
                    finish();
                }
                else 
                {
                    Toast.makeText(MainActivity.this, "No se pudo Iniciar sesión - Verifica tus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Boton

    public void Siguiente(View view)
        {
            Intent siguiente = new Intent(this, Registro.class);
            startActivity(siguiente);
        }

    public void Siguientepass(View view)
    {
        Intent siguientepass = new Intent(this, ResetPasswordActivity.class);
        startActivity(siguientepass);
    }
    public void Anterior(View view)
    {
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(MainActivity.this, DahsboardActivity.class));
            finish();
        }
    }
}
