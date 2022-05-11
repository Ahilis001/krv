/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.kalendar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iduras
 */
public class Datum {
    
    List<Dogadaj> listaDogadajaDatuma = new ArrayList<>();
    
    int dan;
    int mjesec;
    int godina;
    
    public Datum() {
    }

    public List<Dogadaj> getListaDogadajaDatuma() {
        return listaDogadajaDatuma;
    }

    public void setListaDogadajaDatuma(List<Dogadaj> listaDogadajaDatuma) {
        this.listaDogadajaDatuma = listaDogadajaDatuma;
    }

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public int getMjesec() {
        return mjesec;
    }

    public void setMjesec(int mjesec) {
        this.mjesec = mjesec;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }
    
    /**
     * Vraća datum u određenom formatu.
     * @return dd.MM.yyyy.
     */
    public String dajDatum(){
        String localDan;
        String localMjesec;
        
        if(dan < 10){
            localDan = "0" + dan;
        }
        
        else{
            localDan = Integer.toString(dan);
        }
        
        if (mjesec < 10) {
            localMjesec = "0" + mjesec;
        } 
        
        else {
            localMjesec = Integer.toString(mjesec);
        }
        
        return localDan + "." + localMjesec + "." + godina + ".";
        
    }
}
