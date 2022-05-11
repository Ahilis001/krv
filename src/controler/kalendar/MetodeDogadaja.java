/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.kalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.kalendar.Datum;
import model.kalendar.Dogadaj;
import static view.krv.Pocetna.getjLabelOdabraniDatum;

/**
 *
 * @author iduras
 */
public class MetodeDogadaja {
    
    /**
     * Vraća redni broj prvog dana mjeseca.
     * @param mjesec - broj mjeseca
     * @param godina - broj godine
     * @return - redni broj prvog dana mjeseca
     */
    public static int prviDanMjeseca(int mjesec, int godina){
        int danUTjednu = 0;
        try {
           
            Calendar c = Calendar.getInstance(new Locale("hr","HR"));

            c.setTime(new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").parse("01." + mjesec + "." + godina + ". 01:00:00"));
            danUTjednu = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (danUTjednu == 0){
                danUTjednu = 7;
            }
           
            return danUTjednu;
            
        } catch (ParseException ex) {
            Logger.getLogger(Dogadaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danUTjednu;
    }
    
    /**
     * Vraća broj dana mjeseca.
     * @param mjesec - broj mjeseca
     * @param godina - broj godine
     * @return - redni broj prvod dana mjeseca
     */
    public static int brojDanaUMjesecu(int mjesec, int godina){
        YearMonth yearMonthObject = YearMonth.of(godina, mjesec);
        return yearMonthObject.lengthOfMonth();
    }
    
    
    /**
     * Popunjava tablicu s odgovarajućim brojem dana.
     * @param tablicaKalendara
     * @param listaListaDatuma
     * @param mjesec
     * @param godina 
     */    
    public static void generirajKalendar(JTable tablicaKalendara, List<List<Datum>> listaListaDatuma, int mjesec, int godina){
        
        tablicaKalendara.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Ponedjeljak", "Utorak", "Srijeda", "Četvrtak", "Petak", "Subota", "Nedjelja"
        }));
        
        DefaultTableModel model = (DefaultTableModel) tablicaKalendara.getModel();
        
        popuniListuKalendara(listaListaDatuma, mjesec, godina);
        
        Object podaciReda[] = new Object[10];
        for (List<Datum> listaDatuma : listaListaDatuma) {
            for (int i = 0; i < listaDatuma.size(); i++) {
                podaciReda[i] = listaDatuma.get(i).getDan();
            }
            model.addRow(podaciReda);
        }
    }
    
    /**
     * Popunjava tablicu koja predstavlja kalendar.
     * @param listaListaDatuma
     * @param mjesec
     * @param godina 
     */
    public static void popuniListuKalendara(List<List<Datum>> listaListaDatuma, int mjesec, int godina){
        listaListaDatuma.clear();
        int trenutniDan = 1;
        int prijasnjiMjesec = mjesec - 1;
        int sljedeciMjesec = mjesec + 1;
        int sljedecaGodina = godina;
        int prijasnjaGodina = godina;
        
        if(prijasnjiMjesec == 0){
            prijasnjiMjesec = 12;
            prijasnjaGodina = godina - 1;
        }
        
        if(sljedeciMjesec == 13){
            prijasnjiMjesec = 1;
            sljedecaGodina = godina + 1;
        }
        
        //popunjavanje kalendara za 6 tjedana
        for (int i = 0; i < 6; i++) {
            List<Datum> listaTjedna = new ArrayList<>();
            
            //ako je prvi tjedan mjeseca
            if(listaListaDatuma.isEmpty()){
                
                int daniPrijasnjegMjeseca = brojDanaUMjesecu(prijasnjiMjesec, prijasnjaGodina) - prviDanMjeseca(mjesec, godina) + 2;
                for (int j = 1; j < 8; j++) {
                    if (j != prviDanMjeseca(mjesec, godina) && trenutniDan == 1){
                        Datum datum = new Datum();
                        datum.setDan(daniPrijasnjegMjeseca++);
                        datum.setMjesec(prijasnjiMjesec);
                        datum.setGodina(godina);
                        listaTjedna.add(datum);
                    }
                    
                    else{
                        Datum datum = new Datum();
                        datum.setDan(trenutniDan);
                        datum.setMjesec(mjesec);
                        datum.setGodina(godina);
                        trenutniDan++;
                        listaTjedna.add(datum);
                    }
                }
            }
            
            //ostalih 5 tjedana prikaza kaledara
            else{
                boolean tekuciMjesec = true;
                for (int j = 0; j < 7; j++) {
                    if (trenutniDan % brojDanaUMjesecu(mjesec, godina) == 1) {
                        trenutniDan = 1;
                        tekuciMjesec = false;
                    }
                    
                    if (tekuciMjesec) {
                        Datum datum = new Datum();
                        datum.setDan(trenutniDan);
                        datum.setMjesec(mjesec);
                        datum.setGodina(godina);
                        listaTjedna.add(datum);
                        trenutniDan++;
                    } 
                    
                    else {
                        Datum datum = new Datum();
                        datum.setDan(trenutniDan);
                        datum.setMjesec(sljedeciMjesec);
                        datum.setGodina(sljedecaGodina);
                        listaTjedna.add(datum);
                        trenutniDan++;
                    }
                }
            }
            listaListaDatuma.add(listaTjedna);
        }
    }
    
    /**
     * Klikom miša na odabrani datum, prikazuje sve dogadaje tog dana.
     * @param tablicaDogadaja - tablica u kojoj se prikazuju dogadaji
     * @param listaDogadaja - lista dogadaja za prikaz
     */
    public static void generirajPrikazDogadajaDatuma(JTable tablicaDogadaja, List<Dogadaj> listaDogadaja){
        
        if (listaDogadaja.isEmpty()) {
                            getjLabelOdabraniDatum().setText("Ne postoji niti jedan dogadaj.");
                        } 
                        
                        else {
                            getjLabelOdabraniDatum().setText("Prikaz događaja za " + listaDogadaja.get(0).getDatumPocetka() + ":");
                        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
        
        tablicaDogadaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Opis", "Lokacija", "Počinje", "Završava"
            }
        ));
        
        DefaultTableModel model = (DefaultTableModel) tablicaDogadaja.getModel();
        
        Object podaciReda[] = new Object[4];
        for (Dogadaj dogadaj : listaDogadaja) {
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
}
