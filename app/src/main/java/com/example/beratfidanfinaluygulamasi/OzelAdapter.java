package com.example.beratfidanfinaluygulamasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OzelAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Urun> urunler;
    public OzelAdapter(Context context, ArrayList<Urun> urunler) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.urunler = urunler;
    }
    @Override
    public int getCount() {return urunler.size();}

    @Override
    public Object getItem(int position) {return urunler.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.satir_layout, parent, false);
        TextView txt_tarih = convertView.findViewById(R.id.satir_urun_tarih);
        TextView txt_urunAdi = convertView.findViewById(R.id.satir_urun_adi);
        TextView txt_fiyat = convertView.findViewById(R.id.satir_fiyat);
        ImageView img_resim = convertView.findViewById(R.id.imageView4);

        Urun u = urunler.get(position);
        txt_tarih.setText(u.getTarih());
        txt_urunAdi.setText(u.getUrunAdi());
        txt_fiyat.setText(String.valueOf(u.getFiyat()));
        Picasso.get().load(u.getResim1()).into(img_resim);

            return convertView;
    }
}
