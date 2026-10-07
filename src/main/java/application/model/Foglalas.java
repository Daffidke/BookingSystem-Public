package application.model;

import java.io.Serializable;

public class Foglalas implements Serializable {
    private int foglalasId;
    private String kezdet;
    private String veg;
    private String telefonszam;
    private int felhasznaloId;
    private int szallasId;
    private String allapot;

    public Foglalas(){}
    
    public Foglalas(String kezdet, String veg, String telefonszam, int felhasznaloId, int szallasId, String allapot){
        this.kezdet = kezdet;
        this.veg = veg;
        this.telefonszam = telefonszam;
        this.felhasznaloId = felhasznaloId;
        this.szallasId = szallasId;
        this.allapot = allapot;
    }

    public Foglalas(int foglalasId, String kezdet, String veg, String telefonszam, int felhasznaloId, int szallasId, String allapot){
        this.foglalasId = foglalasId;
        this.kezdet = kezdet;
        this.veg = veg;
        this.telefonszam = telefonszam;
        this.felhasznaloId = felhasznaloId;
        this.szallasId = szallasId;
        this.allapot = allapot;
    }

    public int getFoglalasId() {
        return foglalasId;
    }

    public String getKezdet() {
        return kezdet;
    }

    public String getVeg() {
        return veg;
    }

    public String getTelefonszam() {
        return telefonszam;
    }

    public int getFelhasznaloId() {
        return felhasznaloId;
    }

    public int getSzallasId() {
        return szallasId;
    }

    public String getAllapot() {
        return allapot;
    }

    public void setId(int id) {
        this.foglalasId = id;
    }

    public void setKezdet(String date) {
        this.kezdet= date;
    }

    public void setVeg(String date) {
        this.veg = date;
    }

    public void setTelefonszam(String tel) {
        this.telefonszam = tel;
    }

    public void setFelhasznaloId(int id) {
        this.felhasznaloId = id;
    }

    public void setSzallasId(int id) {
        this.szallasId = id;
    }

    public void setAllapot(String allapot) {
        this.allapot = allapot;
    }
    
}
