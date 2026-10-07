package application.model;

import java.io.Serializable;

public class Szallas implements Serializable {
    private int id;
    private String nev;
    private String leiras;
    private Double ar;
    private String utca;
    private int isz;
    private String varos;
    private Double user_id;

    public Szallas() {}


    public Szallas(String nev, Double ar, String leiras, String utca, int isz, String varos, Double user_id) {
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.utca = utca;
        this.isz = isz;
        this. varos = varos;
        this.user_id = user_id;
    }

    public Szallas(int id, String nev, Double ar, String leiras, String utca, int isz, String varos, Double user_id) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.utca = utca;
        this.isz = isz;
        this. varos = varos;
        this.user_id = user_id;
    }

    public int getId(){
        return this.id;
    }

    public String getNev(){
        return this.nev;
    }

    public String getLeiras(){
        return this.leiras;
    }

    public Double getAr(){
        return this.ar;
    }

    public String getUtca(){
        return this.utca;
    }

    public int getIsz(){
        return this.isz;
    }

    public String getVaros(){
        return this.varos;
    }

    public Double getUser_id(){
        return this.user_id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public void setNev(String nev){
        this.nev = nev;
    }

    public void setLeiras(String ujLeiras){
        this.leiras = ujLeiras;
    }

    public void setAr(Double ujAr){
        this.ar = ujAr;
    }

    public void setUtca(String utca){
        this.utca = utca;
    }

    public void setIsz(int isz){
        this.isz = isz;
    }

    public void setVaros(String varos){
        this.varos = varos;
    }

    public void setUser_Id(Double id){
        this.user_id = id;
    }

}
