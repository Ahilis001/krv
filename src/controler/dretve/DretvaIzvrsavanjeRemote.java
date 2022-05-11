/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.dretve;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.remote.Tajmer;
import view.krv.Pocetna;

/**
 *
 * @author iduras
 */
public class DretvaIzvrsavanjeRemote extends Thread{

    private static DretvaIzvrsavanjeRemote dretva = new DretvaIzvrsavanjeRemote();
    
    private DretvaIzvrsavanjeRemote() {
    }
            
    public static DretvaIzvrsavanjeRemote getInstanca(){
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
                for (List<Tajmer> lista : Pocetna.getListaListaTajmera()) {
                    ListIterator<Tajmer> iteratorTajmera = lista.listIterator();
                    if (iteratorTajmera.hasNext()) {
                        Tajmer tajmer = iteratorTajmera.next();
                        tajmer.smanjiTajmer();
                            if (tajmer.getUkupno() == 0) {
                                tajmer.izvrsi();
                                tajmer.setKraj(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy.").format(Calendar.getInstance().getTime()));
                                tajmer.dodajULog(Pocetna.getjTableLogTablicaLoga());
                                iteratorTajmera.remove();
                            }
                    }
                    
                    try{
                        Tajmer.popuniAzurirajTabelu(Pocetna.getjTableTajmeriTablicaTajmera(), Pocetna.getListaListaTajmera());
                    }
                    catch(Exception e){
                    }
                }
                
//                brojSekundi++;
                Thread.sleep(1000);
//                System.out.println(brojSekundi);
                
            } catch (InterruptedException ex) {
                interrupt();
                Logger.getLogger(DretvaIzvrsavanjeRemote.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public static DretvaIzvrsavanjeRemote getDretva() {
        return dretva;
    }
}
