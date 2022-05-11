/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.dretve;

import controler.kalendar.MetodeKalendara;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import model.kalendar.Dogadaj;
import view.krv.Pocetna;

/**
 *
 * @author iduras
 */
public class DretvaAzuriranjeKalendara extends Thread{
    
    private static DretvaAzuriranjeKalendara dretva = new DretvaAzuriranjeKalendara();
    
    private DretvaAzuriranjeKalendara() {
    }
            
    public static DretvaAzuriranjeKalendara getInstanca(){
        return dretva;
    }
    
    @Override
    public void interrupt() {
        System.out.println("Dretva je završila s radom.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
//        long brojSekundi = 0;
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while (true) {  
            
            try {
                Thread.sleep(1000 * 60 * 60);
                MetodeKalendara.generirajPrikaz(MetodeKalendara.getURLKalendaraGooglePosao(), Dogadaj.getListaSvihDogadaja(), Pocetna.getjTableKalendar(), Pocetna.getjTableTablicaDogadaja(), Pocetna.getjTableTablicaDogadajaZaTjedanDana(), Pocetna.getjTextFieldGodina(), Pocetna.getListaListaDatuma(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
            } 

            catch (Exception e) {
                Pocetna.getjTextFieldPoruka().setText("Greška u dretvi ažuriranja.");
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public static DretvaAzuriranjeKalendara getDretva() {
        return dretva;
    }
}
