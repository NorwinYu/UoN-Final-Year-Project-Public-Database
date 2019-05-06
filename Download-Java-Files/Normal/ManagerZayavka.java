package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

// Объект - заявка. Спокойно можешь менять поля и их названия. Только вот смотри код

public class ManagerZayavka {
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
    private String namegruz;
    private String soprdocument;

    private String pochta;
    private String lisootprav;
    private String lisoplouch;
    private String telefonOtprav;
    private String telefonPoluch;

    private String adresotp;
    private String adrespol;

    private String obiemplan;
    private String obiemfact;
    private String vesplan;
    private String vesfact;
    private String kolplan;
    private String kolfact;
    private String vidpere;
    private String priceorder;
    private String statusorder;
    private String comment;
    private String nomerdogovor;



    public ManagerZayavka(){

    }
    public ManagerZayavka(String number, String date, String sender, String recept, String senderadr, String receptadr, String Ref_key, String zakaz, String menedjer, String podrazd, String status, String namegruz, String soprdocument,
                          String pochta,String lisootprav,String lisoplouch,String telefonOtprav,String telefonPoluch,
                          String adresotp,String adrespol,
                          String obiemplan,String obiemfact,String vesplan,String vesfact,String kolplan,String kolfact,String vidpere,
                          String priceorder,String statusorder,String comment
                          ) {
        this.soprdocument=soprdocument;
        this.number = number;
        this.date = date;
        this.namegruz=namegruz;
        this.sender = sender;
        this.recept =recept;
        this.senderadr=senderadr;
        this.receptadr=receptadr;
        this.Ref_key=Ref_key;
        this.zakaz=zakaz;
        this.menedjer=menedjer;
        this.podrazd=podrazd;
        this.status=status;

        this.pochta=pochta;
        this.lisootprav=lisootprav;
        this.lisoplouch=lisoplouch;
        this.telefonOtprav=telefonOtprav;
        this.telefonPoluch=telefonPoluch;

        this.adresotp=adresotp;
        this.adrespol=adrespol;

        this.obiemplan=obiemplan;
        this.obiemfact=obiemfact;
        this.vesplan=vesplan;
        this.vesfact=vesfact;
        this.kolplan=kolplan;
        this.kolfact=kolfact;
        this.vidpere=vidpere;
        this.priceorder=priceorder;
        this.statusorder=statusorder;

        this.comment=comment;

        this.nomerdogovor=nomerdogovor;




    }

    public String getNomerdogovor() {
        return nomerdogovor;
    }

    public String getComment() {
        return comment;
    }

    public String getKolfact() {
        return kolfact;
    }

    public String getKolplan() {
        return kolplan;
    }

    public String getObiemfact() {
        return obiemfact;
    }

    public String getObiemplan() {
        return obiemplan;
    }

    public String getPriceorder() {
        return priceorder;
    }

    public String getStatusorder() {
        return statusorder;
    }

    public String getVesfact() {
        return vesfact;
    }

    public String getVesplan() {
        return vesplan;
    }

    public String getVidpere() {
        return vidpere;
    }


    public String getAdresotp() {
        return adresotp;
    }
    public String getAdrespol() {
        return adrespol;
    }



    public String getLisootprav() {
        return lisootprav;
    }

    public String getLisoplouch() {
        return lisoplouch;
    }

    public String getPochta() {
        return pochta;
    }

    public String getTelefonOtprav() {
        return telefonOtprav;
    }

    public String getTelefonPoluch() {
        return telefonPoluch;
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

    public String getNamegruz() {
        return namegruz;
    }

    public String getSoprdocument() {
        return soprdocument;
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
