package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import java.io.Serializable;

public class ReceptionData implements Serializable {
    private String code;
    private String date;
    private String zakazchik;
    private String pochta;
    private String dogovor;
    private String otpavitel;
    private String otkuda;
    private String address;
    private String kontakt;
    private String telefon;
    private String poluchatel;
    private String dateEdit;
    private String kuda;
    private String planKolich;
    private String planVes;
    private String planObiem;
    private String factKolich;
    private String factVes;
    private String factObiem;
    private String info;
    private String vid;
    private String dostavka;
    private String stoimost;
    private String status;
    private String komment;

    private String tv_p_kolich;
    private String tv_p_ves;
    private String tv_p_obiem;
    private String tv_f_kolich;
    private String tv_f_ves;
    private String tv_f_obiem;
    private String tv_info;
    private String tv_vid;
    private String tv_dostavka;
    private String tv_stoimost;
    private String tv_status;
    private String tv_kommentar;
    private String tv_p_kuda;
    private String tv_p_adres;
    private String tv_p_kontakt;
    private String tv_p_telefon;





    public ReceptionData() {
    }

    public ReceptionData(String code, String date, String zakazchik, String pochta, String dogovor,
                         String otpavitel, String otkuda, String address, String kontakt, String telefon,
                         String poluchatel, String dateEdit, String kuda, String planKolich, String planVes, String planObiem,
                         String factKolich, String factVes, String factObiem, String info, String vid,
                         String stoimost, String status, String komment,String tv_p_kuda,
                         String tv_p_adres,String tv_p_kontakt,String tv_p_telefon) {
        this.code = code;
        this.date = date;
        this.zakazchik = zakazchik;
        this.pochta = pochta;
        this.dogovor = dogovor;
        this.otpavitel = otpavitel;
        this.otkuda = otkuda;
        this.address = address;
        this.kontakt = kontakt;
        this.telefon = telefon;
        this.poluchatel = poluchatel;
        this.dateEdit = dateEdit;
        this.kuda=kuda;

        this.planKolich = planKolich;
        this.planVes = planVes;
        this.planObiem = planObiem;
        this.factKolich = factKolich;
        this.factVes = factVes;
        this.factObiem = factObiem;
        this.info = info;
        this.vid = vid;
        this.dostavka = dostavka;
        this.stoimost = stoimost;
        this.status = status;
        this.komment = komment;


        this.tv_p_kuda=tv_p_kuda;
        this.tv_p_adres=tv_p_adres;
        this.tv_p_kontakt=tv_p_kontakt;
        this.tv_p_telefon=tv_p_telefon;
    }

    public String getTv_dostavka() {
        return tv_dostavka;
    }

    public String getTv_f_kolich() {
        return tv_f_kolich;
    }

    public String getTv_f_obiem() {
        return tv_f_obiem;
    }

    public String getTv_f_ves() {
        return tv_f_ves;
    }

    public String getTv_info() {
        return tv_info;
    }

    public String getTv_kommentar() {
        return tv_kommentar;
    }

    public String getTv_p_adres() {
        return tv_p_adres;
    }

    public String getTv_p_kolich() {
        return tv_p_kolich;
    }

    public String getTv_p_kontakt() {
        return tv_p_kontakt;
    }

    public String getTv_p_kuda() {
        return tv_p_kuda;
    }

    public String getTv_p_obiem() {
        return tv_p_obiem;
    }

    public String getTv_p_telefon() {
        return tv_p_telefon;
    }

    public String getTv_p_ves() {
        return tv_p_ves;
    }

    public String getTv_status() {
        return tv_status;
    }

    public String getTv_stoimost() {
        return tv_stoimost;
    }

    public String getTv_vid() {
        return tv_vid;
    }

    public void setTv_dostavka(String tv_dostavka) {
        this.tv_dostavka = tv_dostavka;
    }

    public void setTv_f_kolich(String tv_f_kolich) {
        this.tv_f_kolich = tv_f_kolich;
    }

    public void setTv_f_obiem(String tv_f_obiem) {
        this.tv_f_obiem = tv_f_obiem;
    }

    public void setTv_f_ves(String tv_f_ves) {
        this.tv_f_ves = tv_f_ves;
    }

    public void setTv_info(String tv_info) {
        this.tv_info = tv_info;
    }

    public void setTv_kommentar(String tv_kommentar) {
        this.tv_kommentar = tv_kommentar;
    }

    public void setTv_p_adres(String tv_p_adres) {
        this.tv_p_adres = tv_p_adres;
    }

    public void setTv_p_kolich(String tv_p_kolich) {
        this.tv_p_kolich = tv_p_kolich;
    }

    public void setTv_p_kontakt(String tv_p_kontakt) {
        this.tv_p_kontakt = tv_p_kontakt;
    }

    public void setTv_p_kuda(String tv_p_kuda) {
        this.tv_p_kuda = tv_p_kuda;
    }

    public void setTv_p_obiem(String tv_p_obiem) {
        this.tv_p_obiem = tv_p_obiem;
    }

    public void setTv_p_telefon(String tv_p_telefon) {
        this.tv_p_telefon = tv_p_telefon;
    }

    public void setTv_p_ves(String tv_p_ves) {
        this.tv_p_ves = tv_p_ves;
    }

    public void setTv_status(String tv_status) {
        this.tv_status = tv_status;
    }

    public void setTv_stoimost(String tv_stoimost) {
        this.tv_stoimost = tv_stoimost;
    }

    public void setTv_vid(String tv_vid) {
        this.tv_vid = tv_vid;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZakazchik() {
        return zakazchik;
    }

    public void setZakazchik(String zakazchik) {
        this.zakazchik = zakazchik;
    }

    public String getPochta() {
        return pochta;
    }

    public void setPochta(String pochta) {
        this.pochta = pochta;
    }

    public String getDogovor() {
        return dogovor;
    }

    public void setDogovor(String dogovor) {
        this.dogovor = dogovor;
    }

    public String getOtpavitel() {
        return otpavitel;
    }

    public void setOtpavitel(String otpavitel) {
        this.otpavitel = otpavitel;
    }

    public String getOtkuda() {
        return otkuda;
    }

    public void setOtkuda(String otkuda) {
        this.otkuda = otkuda;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPoluchatel() {
        return poluchatel;
    }

    public void setPoluchatel(String poluchatel) {
        this.poluchatel = poluchatel;
    }

    public String getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(String dateEdit) {
        this.dateEdit = dateEdit;
    }

    public String getKuda() {
        return kuda;
    }

    public void setKuda(String kuda) {
        this.kuda = kuda;
    }

    public String getPlanKolich() {
        return planKolich;
    }

    public void setPlanKolich(String planKolich) {
        this.planKolich = planKolich;
    }

    public String getPlanVes() {
        return planVes;
    }

    public void setPlanVes(String planVes) {
        this.planVes = planVes;
    }

    public String getPlanObiem() {
        return planObiem;
    }

    public void setPlanObiem(String planObiem) {
        this.planObiem = planObiem;
    }

    public String getFactKolich() {
        return factKolich;
    }

    public void setFactKolich(String factKolich) {
        this.factKolich = factKolich;
    }

    public String getFactVes() {
        return factVes;
    }

    public void setFactVes(String factVes) {
        this.factVes = factVes;
    }

    public String getFactObiem() {
        return factObiem;
    }

    public void setFactObiem(String factObiem) {
        this.factObiem = factObiem;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDostavka() {
        return dostavka;
    }

    public void setDostavka(String dostavka) {
        this.dostavka = dostavka;
    }

    public String getStoimost() {
        return stoimost;
    }

    public void setStoimost(String stoimost) {
        this.stoimost = stoimost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKomment() {
        return komment;
    }

    public void setKomment(String komment) {
        this.komment = komment;
    }
}