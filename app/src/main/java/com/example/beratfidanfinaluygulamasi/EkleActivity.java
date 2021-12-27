package com.example.beratfidanfinaluygulamasi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class EkleActivity extends AppCompatActivity {
    TextView txt_key;
    EditText edt_urun_tarih;
    EditText edt_urun_adi;
    EditText edt_fiyat;
    ImageView resim1,resim2,resim3;
    ImageView yuklendi1,yuklendi2,yuklendi3;



    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri galeriUri1,galeriUri2,galeriUri3;
    Uri downloadUri1,downloadUri2,downloadUri3;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        txt_key = findViewById(R.id.textView);
        edt_urun_tarih = findViewById(R.id.editText);
        edt_urun_adi = findViewById(R.id.editText2);
        edt_fiyat = findViewById(R.id.editText3);
        resim1 = findViewById(R.id.resim1);
        resim2 = findViewById(R.id.resim2);
        resim3 = findViewById(R.id.resim3);
        yuklendi1 = findViewById(R.id.yüklendi1);
        yuklendi2 = findViewById(R.id.yüklendi2);
        yuklendi3 = findViewById(R.id.yüklendi3);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("urunler");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images");

        Urun gelenUrun = (Urun) getIntent().getSerializableExtra("urunNesnesi");
        if (gelenUrun!=null){
            txt_key.setText(gelenUrun.getKey());
            edt_urun_tarih.setText(gelenUrun.getTarih());
            edt_urun_adi.setText(gelenUrun.getUrunAdi());
            edt_fiyat.setText(String.valueOf(gelenUrun.getFiyat()));
            Picasso.get().load(gelenUrun.getResim1()).into(resim1);
            Picasso.get().load(gelenUrun.getResim2()).into(resim2);
            Picasso.get().load(gelenUrun.getResim3()).into(resim3);
        }
        resim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriIntent = new Intent(Intent.ACTION_PICK);
                galeriIntent.setType("image/*");
                startActivityForResult(galeriIntent,123);
            }
        });
        resim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriIntent = new Intent(Intent.ACTION_PICK);
                galeriIntent.setType("image/*");
                startActivityForResult(galeriIntent,124);
            }
        });
        resim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriIntent = new Intent(Intent.ACTION_PICK);
                galeriIntent.setType("image/*");
                startActivityForResult(galeriIntent,125);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode==123 && resultCode==RESULT_OK ){

                  galeriUri1 = data.getData();
                 resim1.setImageURI(galeriUri1);
        }
        if (requestCode==124 && resultCode==RESULT_OK ){

            galeriUri2 = data.getData();
            resim2.setImageURI(galeriUri2);
        }
        if (requestCode==125 && resultCode==RESULT_OK ){

            galeriUri3 = data.getData();
            resim3.setImageURI(galeriUri3);
        }


    }



    public void btnEkle(View v) {

        resimYukle(galeriUri1, 1);
        resimYukle(galeriUri2,2);
        resimYukle(galeriUri3,3);


    }
    public void resimYukle(Uri uri, final int resimNo) {
        final StorageReference yukleRef = storageReference.child("resim"+ UUID.randomUUID() + ".jpg");
        yukleRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(EkleActivity.this, "Resim Yüklendi", Toast.LENGTH_SHORT).show();
                        yukleRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                System.out.println("Download uri:" + uri.toString());
                            if (resimNo==1){
                                downloadUri1 = uri;
                                yuklendi1.setVisibility(View.VISIBLE);
                            }
                            else if (resimNo==2){
                                    downloadUri2 = uri;
                                yuklendi2.setVisibility(View.VISIBLE);
                                }
                            else if (resimNo==3){
                                    downloadUri3 = uri;
                                yuklendi3.setVisibility(View.VISIBLE);
                                }

                            if (yuklendi1.getVisibility()==View.VISIBLE && yuklendi2.getVisibility()==View.VISIBLE && yuklendi3.getVisibility()==View.VISIBLE) {
                                String tarih = edt_urun_tarih.getText().toString();
                                String urunAdi = edt_urun_adi.getText().toString();
                                double fiyat = Double.parseDouble(edt_fiyat.getText().toString());

                                final Urun yeniUrun = new Urun(null, tarih, urunAdi, fiyat, downloadUri1.toString(), downloadUri2.toString(), downloadUri3.toString());

                                String yeniKey = ref.push().getKey();
                                yeniUrun.setKey(yeniKey);

                                ref.child(yeniKey).setValue(yeniUrun)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(EkleActivity.this, "Ürün eklendi", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(EkleActivity.this, "Hata oluştu", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EkleActivity.this, "Resim Yüklenemedi" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
