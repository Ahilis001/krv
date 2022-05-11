/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.dretve;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.krv.Pocetna;

/**
 *
 * @author iduras
 */
public class DretvaSat extends Thread{
    
    private static DretvaSat dretva = new DretvaSat();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    
    private DretvaSat() {
    }
            
    public static DretvaSat getInstanca(){
        return dretva;
    }
    
    @Override
    public void interrupt() {
        System.out.println("Dretva je završila s radom.");
        Pocetna.getjTextFieldPoruka().setText("Greška u dretvi sata.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
//        long brojSekundi = 0;
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while (true) {  
            try {
                Pocetna.getjTextFieldDanasnjiDatum().setText(formatter.format(new Date(System.currentTimeMillis())));
                Thread.sleep(1000);
//                System.out.println(brojSekundi);
                
            } catch (InterruptedException ex) {
                interrupt();
                Logger.getLogger(DretvaSat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public static DretvaSat getDretva() {
        return dretva;
    }
}
