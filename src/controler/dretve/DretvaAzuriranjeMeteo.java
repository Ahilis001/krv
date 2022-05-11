/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.dretve;

import controler.meteo.MetodeMeteo;
import java.text.SimpleDateFormat;
import view.krv.Pocetna;

/**
 *
 * @author iduras
 */
public class DretvaAzuriranjeMeteo extends Thread{
    
    private static DretvaAzuriranjeMeteo dretva = new DretvaAzuriranjeMeteo();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    
    private DretvaAzuriranjeMeteo() {
    }
            
    public static DretvaAzuriranjeMeteo getInstanca(){
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
                Thread.sleep(1000 * 60 * 30);
                MetodeMeteo.generirajPrikazMeteo("Varazdin", "HR", Pocetna.getjPanelDanas(), Pocetna.getjPanePrognoza());
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

    public static DretvaAzuriranjeMeteo getDretva() {
        return dretva;
    }
}
