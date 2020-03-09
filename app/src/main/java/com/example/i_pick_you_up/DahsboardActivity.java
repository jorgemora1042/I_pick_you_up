package com.example.i_pick_you_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DahsboardActivity extends AppCompatActivity {

    private Button mButtonSingout;
    private TextView mTextViewName;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);


        mAuth = FirebaseAuth.getInstance();
        mButtonSingout = (Button) findViewById(R.id.BTNsignout);
        mTextViewName = (TextView) findViewById(R.id.textView2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mButtonSingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(DahsboardActivity.this, MainActivity.class));
                finish();
            }
        });

        getUserInfo();
    }

    private void getUserInfo()
    {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String name = dataSnapshot.child("name").getValue().toString();

                    mTextViewName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
