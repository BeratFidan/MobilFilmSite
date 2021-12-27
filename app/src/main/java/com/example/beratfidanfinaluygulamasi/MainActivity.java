package com.example.beratfidanfinaluygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Urun> urunArrayList;
    OzelAdapterRV ozelAdapterRV;

    TextView textView;
    FirebaseDatabase db;
    DatabaseReference ref;
    ValueEventListener urunlerListener;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        textView = findViewById(R.id.textView3);
        textView.setText("Hoşgeldin " + mUser.getEmail());

        urunArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        ozelAdapterRV = new OzelAdapterRV(this,urunArrayList);
        recyclerView.setAdapter(ozelAdapterRV);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("urunler");

      urunlerListener =  ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            urunArrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                Urun u = data.getValue(Urun.class);
                urunArrayList.add(u);
            }
                ozelAdapterRV.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected  void  onDestroy() {
        super.onDestroy();
        ref.removeEventListener(urunlerListener);
    }
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
}
@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.item_ekle) {
        Intent i = new Intent(this,EkleActivity.class);
        startActivity(i);

    }
      else  if (item.getItemId()==R.id.item_cikis){
         mAuth.signOut();
            Intent i = new Intent(this,GirisActivity.class);
            startActivity(i);
            finish();
        }
return super.onOptionsItemSelected(item);

}
}
