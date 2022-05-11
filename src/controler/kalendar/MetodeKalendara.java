/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.kalendar;

import static controler.kalendar.MetodeDogadaja.generirajKalendar;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Pattern;
import model.kalendar.Dogadaj;
import static controler.kalendar.MetodeKalendara.isoFormatDatumVrijeme;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.kalendar.Datum;
import view.krv.Pocetna;
import static view.krv.Pocetna.getjLabelOdabraniDatum;

/**
 *
 * @author iduras
 */
public class MetodeKalendara {
    
    static String URLKalendaraGooglePosao = "https://calendar.google.com/calendar/ical/2ucj8rrcrvg49ltmg5rec06hao%40group.calendar.google.com/private-1a8aa760f8545a3cc1b92c417cabf831/basic.ics";
    
    static SimpleDateFormat isoFormatDatumVrijeme = new SimpleDateFormat("yyyyMMddHHmm");
    static SimpleDateFormat isoFormatDatumCjelodnevni = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    
    static List<Dogadaj> listaDanas = new ArrayList<>();
    
    /**
     * dohvaća sve događaje
     * @param URLKalendara
     * @param ListaDogadaja
     */
    public static void dohvatiDogadaje(String URLKalendara, List<Dogadaj> ListaDogadaja){
        isoFormatDatumVrijeme.setTimeZone(TimeZone.getTimeZone("UTC"));
        isoFormatDatumCjelodnevni.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        Dogadaj.getListaSvihDogadaja().clear();
        
        try {
            String out = new Scanner(new URL(URLKalendara).openStream(), "UTF-8").useDelimiter("\\A").next();
                        
            //razdvajanje string događaja na polje događaja
            String patternString = "END:VEVENT";
            Pattern pattern = Pattern.compile(patternString);

            String[] split = pattern.split(out);

            //čišćenje i popunjavanje svih liste događaja
            ListaDogadaja.clear();
            
            for(String element : split){
                
                //za svaki dogadaj iz povucenih dogadaja kalendara se kreira dogadaj
                Dogadaj dogadaj = new Dogadaj();
                
                String patternDogadaja = System.getProperty("line.separator");
                Pattern pattern1 = Pattern.compile(patternDogadaja);

                String[] dohvacenDogadaj = pattern1.split(element);
                
                //s obzirom na vrijednost iz povucenog dogadaja, popunjava se objekt dogadaja
                for (String string : dohvacenDogadaj) {
                    
                    //popunjavanje datuma i vremena početka
                    if (string.contains("DTSTART")) {
                        
                        //ako nije cjelodnevni događaj
                        if (string.contains("Z")) {
                            dogadaj.setDatumIVrijemePocetka(isoFormatDatumVrijeme.parse(string.substring(string.lastIndexOf(":") + 1).replace("T", "")));
                        } 
                        
                        //ako je cjelodnevni događaj
                        else {
                            dogadaj.setDatumIVrijemePocetka(isoFormatDatumCjelodnevni.parse(string.substring(string.lastIndexOf(":") + 1)));
                        }
                    }
                    
                    //popunjavanje datuma i vremena završetka
                    else if (string.contains("DTEND")) {
                        
                        //ako nije cjelodnevni događaj
                        if (string.contains("Z")) {
                            dogadaj.setDatumIVrijemeZavrsetka(isoFormatDatumVrijeme.parse(string.substring(string.lastIndexOf(":") + 1).replace("T", "")));
                        } 
                        
                        //ako je cjelodnevni događaj
                        else {
                            dogadaj.setDatumIVrijemeZavrsetka(isoFormatDatumCjelodnevni.parse(string.substring(string.lastIndexOf(":") + 1)));
                        }
                    }
                    
                    //popunjavanje lokacije
                    else if (string.contains("LOCATION")) {
                        
                        dogadaj.setLokacija(string.substring(string.lastIndexOf(":") + 1));
                    }
                    
                    //popunjavanje naziva
                    else if (string.contains("SUMMARY")) {
                        
                        dogadaj.setNaziv(string.substring(string.lastIndexOf(":") + 1));
                    }
                }
                
                //ako je dogadaj kroz više dana rade se dva događaja i dodaju se u listu
                try {
                    if (!isoFormatDatumCjelodnevni.format(dogadaj.getDatumIVrijemePocetka()).equals(isoFormatDatumCjelodnevni.format(dogadaj.getDatumIVrijemeZavrsetka()))) {
                        Dogadaj dogadajPomocni = new Dogadaj(dogadaj);
                        dogadaj.setDatumIVrijemeZavrsetka(dogadaj.getDatumIVrijemePocetka());
                        dogadaj.setNocnaPocetak(true);
                        dogadajPomocni.setDatumIVrijemePocetka(dogadajPomocni.getDatumIVrijemeZavrsetka());
                        dogadajPomocni.setNocnaKraj(true);

                        Dogadaj.getListaSvihDogadaja().add(dogadaj);
                        Dogadaj.getListaSvihDogadaja().add(dogadajPomocni);
                    } 

                    //inače se dodaje dogadaj u listu 
                    else {
                        Dogadaj.getListaSvihDogadaja().add(dogadaj);
                    }
                } catch (Exception e) {
                }
                
                
            }
        } catch (Exception ex) {
            Pocetna.getjTextFieldPoruka().setText("Greška kod preuzimanja podataka.");
        }
        
        Collections.sort(Dogadaj.getListaSvihDogadaja());
    }
    
    /**
     * Dodaje dogadaje datumu.
     * @param listaDogadaja
     * @param listaListaDatuma
     */
    public static void dodijeliDatumuDogadaje(List<Dogadaj> listaDogadaja, List<List<Datum>> listaListaDatuma){
          
        listaDanas.clear();
        
        LocalDate danasnjiDatum = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
        
        //za svaku listu koja predstavlja tjedan (red) u tablici
        for (List<Datum> tjedan : listaListaDatuma) {
            
            //za svaku čeliju u redu tablice
            for (Datum dan : tjedan) {
                
                //dodavanje događaje iz liste događaja u listu datuma
                for (Dogadaj dogadaj : listaDogadaja) {
                    try {
                        LocalDate localDatePocetak = dogadaj.getDatumIVrijemePocetka().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        
                        int year  = localDatePocetak.getYear();
                        int month = localDatePocetak.getMonthValue();
                        int day   = localDatePocetak.getDayOfMonth();
                        
                        //dodavanje dogadaja za prikaz danasnjeg dana
                        if ((danasnjiDatum.getDayOfMonth() == localDatePocetak.getDayOfMonth() && 
                            danasnjiDatum.getMonthValue() == localDatePocetak.getMonthValue() &&
                            danasnjiDatum.getYear() == localDatePocetak.getYear() &&
                            !listaDanas.contains(dogadaj))) {
                            listaDanas.add(dogadaj);
                        }
                        
//                        if (listaDanas.isEmpty()) {
//                            getjLabelOdabraniDatum().setText("Ne postoji niti jedan dogadaj.");
//                        } 
//                        
//                        else {
//                            getjLabelOdabraniDatum().setText("Prikaz događaja za " + listaDanas.get(0).getDatumPocetka() + ":");
//                        }
                        
                        
                        if ((dan.getDan() == day && dan.getMjesec() == month && dan.getGodina() == year)
                         || (dan.getDan() == day && dan.getMjesec() == month && dan.getGodina() == year)) {
                            dan.getListaDogadajaDatuma().add(dogadaj);
                        }  
                    } 
                    
                    catch (Exception e) {
                        
                    }
                }
            }
        }
        
        MetodeDogadaja.generirajPrikazDogadajaDatuma(Pocetna.getjTableTablicaDogadaja(), MetodeKalendara.getListaDanas());

    }    
    
    /**
     *
     * @param URLKalendaraGooglePosao
     * @param listaSvihDogadaja
     * @param listaListaDatuma
     * @param jTableTablicaDogadaja
     * @param jTableTablicaDogadajaZaTjedanDana
     * @param tablicaKalendara
     * @param jTextFieldGodina
     * @param dan
     * @param mjesec
     * @param godina
     */
    public static void generirajPrikaz(String URLKalendaraGooglePosao, List<Dogadaj> listaSvihDogadaja, JTable tablicaKalendara, JTable jTableTablicaDogadaja, JTable jTableTablicaDogadajaZaTjedanDana, JTextField jTextFieldGodina, List<List<Datum>> listaListaDatuma, int dan, int mjesec, int godina){
        Pocetna.getjTextFieldPoruka().setText("Ažurirano: " + formatter.format(new Date(System.currentTimeMillis())));
        MetodeKalendara.dohvatiDogadaje(URLKalendaraGooglePosao, listaSvihDogadaja);
        
        generirajKalendar(tablicaKalendara, listaListaDatuma, mjesec, godina);
        jTextFieldGodina.setText(Integer.toString(godina));
        
        MetodeKalendara.dodijeliDatumuDogadaje(listaSvihDogadaja, listaListaDatuma);
        
        prikazSljedecihSedamDana(listaSvihDogadaja, jTableTablicaDogadajaZaTjedanDana, dan, mjesec, godina);
    } 
    
    /**
     *
     * @param listaSvihDogadaja
     * @param listaListaDatuma
     * @param tablicaKalendara
     * @param jTextFieldGodina
     * @param mjesec
     * @param godina
     */
    public static void azurirajPrikaz(List<Dogadaj> listaSvihDogadaja, JTable tablicaKalendara, JTextField jTextFieldGodina, List<List<Datum>> listaListaDatuma, int mjesec, int godina){
//        MetodeKalendara.dohvatiDogadaje(URLKalendaraGooglePosao, listaSvihDogadaja);
        generirajKalendar(tablicaKalendara, listaListaDatuma, mjesec, godina);
        jTextFieldGodina.setText(Integer.toString(godina));
        
        MetodeKalendara.dodijeliDatumuDogadaje(listaSvihDogadaja, listaListaDatuma);
    } 
    
    /**
     * 
     * @param listaDogadaja
     * @param tablicaDogadajaSljedecih7Dana
     * @param dan
     * @param mjesec
     * @param godina 
     */
    public static void prikazSljedecihSedamDana(List<Dogadaj> listaDogadaja, JTable tablicaDogadajaSljedecih7Dana, int dan, int mjesec, int godina){
        MetodeKalendara.dohvatiDogadaje(MetodeKalendara.getURLKalendaraGooglePosao(), Dogadaj.getListaSvihDogadaja());
                
        Date pocetniDatum = new Date();
        Date zavrsniDatum = new Date();
        
        String sDan = Integer.toString(dan);
        String sMjesec = Integer.toString(mjesec);
        String sGodina = Integer.toString(godina);
        
        if(sDan.length() == 1){
            sDan = "0" + sDan;
        }
        
        if(sMjesec.length() == 1){
            sMjesec = "0" + sMjesec;
        }
        
        String datum = sGodina+sMjesec+sDan;
        
        //kreiranje početnog i završnog datuma 
        try {
            pocetniDatum = isoFormatDatumCjelodnevni.parse(datum);
            
            Calendar c = Calendar.getInstance();
	
            //Setting the date to the given date
            c.setTime(isoFormatDatumCjelodnevni.parse(datum));
	
            //dodavanje 7 dana
            c.add(Calendar.DAY_OF_MONTH, 7);  

            //završni datum
            zavrsniDatum = isoFormatDatumCjelodnevni.parse(isoFormatDatumCjelodnevni.format(c.getTime()));
        } catch (Exception e) {
        }
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        
        //izgled tablice
        tablicaDogadajaSljedecih7Dana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Opis", "Lokacija", "Počinje", "Završava"
            }
        ));
        
        DefaultTableModel model = (DefaultTableModel) tablicaDogadajaSljedecih7Dana.getModel();
        
        Object podaciReda[] = new Object[4];
        
        //dodavanje dogadaja za sljedecih 7 dana
        for (Dogadaj dogadaj : Dogadaj.getListaSvihDogadaja()) {
            try {
                
                if (dogadaj.getDatumIVrijemePocetka().compareTo(pocetniDatum) == 1 && zavrsniDatum.compareTo(dogadaj.getDatumIVrijemePocetka()) == 1) {
                    
                    if (dogadaj.isNocnaPocetak()) {
                        podaciReda[3] = "|->";
                        podaciReda[2] = formatter.format(dogadaj.getDatumIVrijemePocetka());
                    } 
                    
                    else if (dogadaj.isNocnaKraj()){
                        podaciReda[3] = formatter.format(dogadaj.getDatumIVrijemeZavrsetka());
                        podaciReda[2] = "->|";
                    }
                    
                    else{
                        podaciReda[3] = formatter.format(dogadaj.getDatumIVrijemeZavrsetka());
                        podaciReda[2] = formatter.format(dogadaj.getDatumIVrijemePocetka());
                    }
                    
                    podaciReda[1] = dogadaj.getLokacija();
                    podaciReda[0] = dogadaj.getNaziv();
                    model.addRow(podaciReda);
                }
            } 
            
            catch (Exception e) {
            }
        }
    } 
    
    public static String getURLKalendaraGooglePosao() {
        return URLKalendaraGooglePosao;
    }

    public static List<Dogadaj> getListaDanas() {
        return listaDanas;
    }
}
