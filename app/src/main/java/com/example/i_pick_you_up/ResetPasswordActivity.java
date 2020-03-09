package com.example.i_pick_you_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText mEditTextemail;
    private Button mButtonresetpass;

    private String email = "";

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        mEditTextemail = (EditText) findViewById(R.id.editTextemail3);
        mButtonresetpass = (Button) findViewById(R.id.resetBTN);

        mButtonresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEditTextemail.getText().toString();

                if (!email.isEmpty())
                {
                    mDialog.setMessage("Espera un momento");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }
                else 
                {
                    Toast.makeText(ResetPasswordActivity.this, "No haz ingresado tu E-mail!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resetPassword()
    {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ResetPasswordActivity.this, "Se ha enviado a tu Correo el link para restablecer tu contraseña", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, "No se pudo enviar el correo para restablecer la Contraseña, Verifica tu E-mail", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }

    public void Anterior(View view2)
    {
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }
}
