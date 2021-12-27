package com.example.beratfidanfinaluygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GirisActivity extends AppCompatActivity {
   private FirebaseAuth mAuth;
   private FirebaseUser mUser;
   private EditText eAd, eSifre;
    @Override
    protected void onCreate(Bundle savedInstaneState) {
        super.onCreate(savedInstaneState);
        setContentView(R.layout.activity_giris);

        eAd = findViewById(R.id.editTextTextPersonName);
        eSifre = findViewById(R.id.editTextTextPersonName2);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser!=null) {
            //MainActivity'e geç
            Intent i = new Intent(GirisActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
public void btnGiris(View v){
        String email = eAd.getText().toString();
        String sifre = eSifre.getText().toString();
mAuth.signInWithEmailAndPassword(email,sifre)
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(GirisActivity.this,"Giriş Başarılı.",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GirisActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
Toast.makeText(GirisActivity.this, "Giriş Hatalı." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
}
    public void btnKayit(View v){
        String email = eAd.getText().toString();
        String sifre = eSifre.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,sifre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(GirisActivity.this,"Kayıt Başarılı.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GirisActivity.this, "Kayıt Hatalı." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
