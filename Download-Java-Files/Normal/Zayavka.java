package com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles;

// Объект - заявка. Спокойно можешь менять поля и их названия. Только вот смотри код

public class Zayavka {
    private String number;
    private String date;
    private String sender;
    private String recept;
    private String senderadr;
    private String receptadr;
    private String Ref_key;
    private String zakaz;
    private String menedjer="";
    private String podrazd;
    private String status;

    public Zayavka(){
        
    }

    public Zayavka(String number, String date, String sender, String recept, String senderadr, String receptadr, String Ref_key, String zakaz,String menedjer, String podrazd, String status ) {
        this.number = number;
        this.date = date;
        this.sender = sender;
        this.recept =recept;
        this.senderadr=senderadr;
        this.receptadr=receptadr;
        this.Ref_key=Ref_key;
        this.zakaz=zakaz;
        this.menedjer=menedjer;
        this.podrazd=podrazd;
        this.status=status;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String birthday) {
        this.date = date;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getRecept() {
        return recept;
    }
    public void setRecept(String recept) {
        this.recept = recept;
    }
    public String getReceptadr() {
        return receptadr;
    }
    public void setReceptadr(String receptadr) {
        this.receptadr = receptadr;
    }
    public String getSenderadr() {
        return senderadr;
    }
    public void setSenderadr(String senderadr) {
        this.senderadr = senderadr;
    }
    public String getRef_key() {
        return Ref_key;
    }
    public String getZakaz() {
        return zakaz;
    }
    public String getMenedjer() {
        return menedjer;
    }
    public String getPodrazd() {
        return podrazd;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    /*private String created_at, zakazchik, otpravitel, poluchatel, upakovka, brak, status, title ,ves, obem, kolichestvo, id;

    // default constructor
    public Zayavka() {

    }

    // two parameters constructor
    public Zayavka(String status, String otpravitel) {
        this.status = status;
        this.otpravitel = otpravitel;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getZakazchik() {
        return zakazchik;
    }

    public void setZakazchik(String zakazchik) {
        this.zakazchik = zakazchik;
    }

    public String getOtpravitel() {
        return otpravitel;
    }

    public void setOtpravitel(String otpravitel) {
        this.otpravitel = otpravitel;
    }

    public String getPoluchatel() {
        return poluchatel;
    }

    public void setPoluchatel(String poluchatel) {
        this.poluchatel = poluchatel;
    }

    public String getUpakovka() {
        return upakovka;
    }

    public void setUpakovka(String upakovka) {
        this.upakovka = upakovka;
    }

    public String getBrak() {
        return brak;
    }

    public void setBrak(String brak) {
        this.brak = brak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getObem() {
        return obem;
    }

    public void setObem(String obem) {
        this.obem = obem;
    }

    public String getKolichestvo() {
        return kolichestvo;
    }

    public void setKolichestvo(String kolichestvo) {
        this.kolichestvo = kolichestvo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/


}
