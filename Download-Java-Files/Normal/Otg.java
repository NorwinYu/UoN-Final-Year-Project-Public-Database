package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

public class Otg {


        String primech;
        String pallet;
        String korob;
        String kuda;
        String otkuda;
        String datakont;
        String datavyg;
        String vesfact;
        String obiemfact;
        String kolfact;
        String zakaz;
        String Ref_key = "";

    public Otg(){

    }
    public Otg(String Ref_key, String zakaz, String kolfact, String obiemfact, String vesfact, String datavyg, String datakont, String otkuda, String kuda,
               String korob, String pallet, String primech) {
        this.Ref_key=Ref_key;
        this.zakaz=zakaz;
        this.kolfact=kolfact;
        this.obiemfact=obiemfact;
        this.vesfact=vesfact;
        this.datavyg=datavyg;
        this.datakont=datakont;
        this.otkuda=otkuda;
        this.kuda=kuda;
        this.korob=korob;
        this.pallet=pallet;
        this.primech=primech;
    }

    public String getDatakont() {
        return datakont;
    }
    public String getDatavyg() {
        return datavyg;
    }

    public String getKolfact() {
        return kolfact;
    }

    public String getKorob() {
        return korob;
    }

    public String getKuda() {
        return kuda;
    }

    public String getObiemfact() {
        return obiemfact;
    }

    public String getOtkuda() {
        return otkuda;
    }

    public String getPallet() {
        return pallet;
    }
    public String getPrimech() {
        return primech;
    }

    public String getVesfact() {
        return vesfact;
    }
}
