package com.example.beratfidanfinaluygulamasi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Urun implements Serializable {
    private String key;
    private  String tarih;
    private String urunAdi;
    private double fiyat;
    private String resim1,resim2,resim3;
    private int puan;
    private Map<String, Boolean> begenenler = new HashMap<>();

    public Urun(){

    }

    public Urun(String key, String tarih, String urunAdi, double fiyat, String resim1, String resim2, String resim3) {
        this.key = key;
        this.tarih = tarih;
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
        this.resim1 = resim1;
        this.resim2 = resim2;
        this.resim3 = resim3;
    }

    public  String getKey() { return key; }

    public  void setKey(String key) { this.key = key; }

    public String getTarih() {return  tarih;}

    public  void setTarih(String tarih) { this.tarih = tarih; }

    public String getUrunAdi() {return urunAdi;}

    public void setUrunAdi(String urunAdi) {this.urunAdi = urunAdi;}

    public double getFiyat() {return fiyat;}

    public void setFiyat(double fiyat) {this.fiyat = fiyat;}

    public String getResim1() {
        return resim1;
    }

    public void setResim1(String resim1) {
        this.resim1 = resim1;
    }

    public String getResim2() {
        return resim2;
    }

    public void setResim2(String resim2) {
        this.resim2 = resim2;
    }

    public String getResim3() {
        return resim3;
    }

    public void setResim3(String resim3) {
        this.resim3 = resim3;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public Map<String, Boolean> getBegenenler() {
        return begenenler;
    }

    public void setBegenenler(Map<String, Boolean> begenenler) {
        this.begenenler = begenenler;
    }
}
