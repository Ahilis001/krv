/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.kalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author iduras
 */
public class Dogadaj implements Comparable<Dogadaj>{
    
    static List<Dogadaj> listaSvihDogadaja = new ArrayList<>();
    
    String naziv = "";
    Date datumIVrijemePocetka;
    Date datumIVrijemeZavrsetka;
    String lokacija = "";
    String opis = "";
    boolean cjelodnevni = false;
    boolean nocnaPocetak = false;
    boolean nocnaKraj = false;

    public Dogadaj() {
    }
    
    public Dogadaj(Dogadaj dogadaj) {
        this.naziv = dogadaj.getNaziv();
        this.datumIVrijemePocetka = dogadaj.getDatumIVrijemePocetka();
        this.datumIVrijemeZavrsetka = dogadaj.getDatumIVrijemeZavrsetka();
        this.lokacija = dogadaj.getLokacija();
        this.opis = dogadaj.getOpis();
        this.cjelodnevni = dogadaj.isCjelodnevni();
    }
    
    public Date getDatumIVrijemePocetka() {
        return datumIVrijemePocetka;
    }

    public void setDatumIVrijemePocetka(Date datumIVrijemePocetka) {
        this.datumIVrijemePocetka = datumIVrijemePocetka;
    }

    public Date getDatumIVrijemeZavrsetka() {
        return datumIVrijemeZavrsetka;
    }

    public void setDatumIVrijemeZavrsetka(Date datumIVrijemeZavrsetka) {
        this.datumIVrijemeZavrsetka = datumIVrijemeZavrsetka;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public static List<Dogadaj> getListaSvihDogadaja() {
        return listaSvihDogadaja;
    }

    public static void setListaSvihDogadaja(List<Dogadaj> listaSvihDogadaja) {
        Dogadaj.listaSvihDogadaja = listaSvihDogadaja;
    }

    public boolean isCjelodnevni() {
        return cjelodnevni;
    }

    public void setCjelodnevni(boolean cjelodnevni) {
        this.cjelodnevni = cjelodnevni;
    }

    public boolean isNocnaPocetak() {
        return nocnaPocetak;
    }

    public void setNocnaPocetak(boolean nocnaPocetak) {
        this.nocnaPocetak = nocnaPocetak;
    }

    public boolean isNocnaKraj() {
        return nocnaKraj;
    }

    public void setNocnaKraj(boolean nocnaKraj) {
        this.nocnaKraj = nocnaKraj;
    }
    
    public String getDatumPocetka() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        
        return formatter.format(datumIVrijemePocetka);
    }
    
    /**
     * Za sortiranje po vremenu početka
     * @param d - dogadaj koji se mora sortirati
     * @return 
     */
    @Override
    public int compareTo(Dogadaj d) {
        if (getDatumIVrijemePocetka() == null || d.getDatumIVrijemePocetka() == null) {
            return 0;
        }
        return getDatumIVrijemePocetka().compareTo(d.getDatumIVrijemePocetka());
    }
}
