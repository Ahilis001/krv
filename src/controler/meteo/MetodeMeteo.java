/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.meteo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.meteo.MeteoPodaci;
import view.krv.Pocetna;

/**
 *
 * @author iduras
 */
public class MetodeMeteo {
    
    private static List<MeteoPodaci> listaMeteoPodataka = new ArrayList<>();
    static SimpleDateFormat formatterSat = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat formatterDatum = new SimpleDateFormat("dd.MM.yyyy.");
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    
    
    public static void generirajPrikazMeteo(String grad, String drzava, JPanel panelDanas, JPanel panelPrognoza){
        prikaziMeteoPodatke(panelDanas, panelPrognoza, povuciPodatkeSve(grad, drzava));
    }
    
    /**
     * Povlači meteo podatke.
     * @param grad
     * @param drzava
     * @return lista meteo podataka
     */
    public static List<MeteoPodaci> povuciPodatkeSve(String grad, String drzava){
        listaMeteoPodataka.clear();
        povuciPodatkeDanas(grad, drzava, listaMeteoPodataka);
        //    http://api.openweathermap.org/data/2.5/weather?q=Varazdin,hr&appid=508677720ef8cc355645dce949620fbb
        //    "id": 3188383,
        //    "name": "Varazdin",
        //    "country": "HR",
        //    "coord": {
        //      "lon": 16.33778,
        //      "lat": 46.30444
        String key = "508677720ef8cc355645dce949620fbb";
        String[] command = {"curl ", "-H " ,"POST ", "http://api.openweathermap.org/data/2.5/forecast/daily?q="+grad+","+drzava+"&units=metric&appid=" + key};
        ProcessBuilder process = new ProcessBuilder(command); 
        Process p;
        
        //spajanje i preuzimanje stringa u json formatu koji sadrži meteo podatke
        try{
            p = process.start();
            BufferedReader br =  new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            
            while ( (line = br.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
            }
            
            //kreiranje json objekta iz stringa
            JsonObject body = Json.createReader(new StringReader(builder.toString())).readObject();
            
            //uzimanje liste meteo podataka
            JsonArray list = body.getJsonArray("list");
            
            //popunjavanje liste meteo podacija za svaki dan
            for (JsonValue jsonValue : list) {
                JsonObject jo = (JsonObject) jsonValue;
                
                MeteoPodaci mp = new MeteoPodaci();
                
                mp.setLastUpdate(new Date(jo.getJsonNumber("dt").bigDecimalValue().longValue()*1000));
                mp.setSunRise(new Date(jo.getJsonNumber("sunrise").bigDecimalValue().longValue()*1000));
                mp.setSunSet(new Date(jo.getJsonNumber("sunset").bigDecimalValue().longValue()*1000));
                
                mp.setTemperatureValue(new Double(jo.getJsonObject("temp").getJsonNumber("day").doubleValue()).floatValue());
                mp.setTemperatureMin(new Double(jo.getJsonObject("temp").getJsonNumber("min").doubleValue()).floatValue());
                mp.setTemperatureMax(new Double(jo.getJsonObject("temp").getJsonNumber("max").doubleValue()).floatValue());
                mp.setTemperatureUnit("celsius");

                mp.setHumidityValue(new Double(jo.getJsonNumber("humidity").doubleValue()).floatValue());
                mp.setHumidityUnit("%");

                mp.setPressureValue(new Double(jo.getJsonNumber("pressure").doubleValue()).floatValue());
                mp.setPressureUnit("hPa");

//                if (jo.getJsonObject("wind").getJsonNumber("speed") == null) {
//                    mp.setWindSpeedValue(new Double(("0")).floatValue());
//                }
//                else{
//                    mp.setWindSpeedValue(new Double(jo.getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
//                }
//                mp.setWindSpeedName("");

//                if (jo.getJsonObject("wind").getJsonNumber("deg") == null) {
//                    mp.setWindDirectionValue(new Double(("0")).floatValue());
//                }
//                else{
//                    mp.setWindDirectionValue(new Double(jo.getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());
//                }
//                mp.setWindDirectionCode("");
//                mp.setWindDirectionName("");

//                mp.setCloudsValue(jo.getJsonObject("clouds").getInt("all"));
                mp.setCloudsName(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
                mp.setPrecipitationMode("");

                mp.setWeatherNumber(jo.getJsonArray("weather").getJsonObject(0).getInt("id"));
                mp.setWeatherValue(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
                mp.setWeatherIcon(jo.getJsonArray("weather").getJsonObject(0).getString("icon"));
                
                listaMeteoPodataka.add(mp);
            }
        }
        
        catch (IOException e){   
            Pocetna.getjTextFieldPoruka().setText("Greška kod preuzimanja meteo podataka. Pokušaj kliknuti na gumb 'Osvježi'.");
            e.printStackTrace();
        }   
        
        return listaMeteoPodataka;
    }
    
    /**
     * Povlači meteo podatke za danas.
     * @param grad
     * @param drzava
     * @param listaMeteoPodataka
     */
    public static void povuciPodatkeDanas(String grad, String drzava, List<MeteoPodaci> listaMeteoPodataka){
        
        String key = "508677720ef8cc355645dce949620fbb";
        String[] command = {"curl ", "-H " ,"POST ", "http://api.openweathermap.org/data/2.5/weather?q=" + grad + "," + drzava + "&units=metric&appid=" + key};
        ProcessBuilder process = new ProcessBuilder(command); 
        Process p;
        
        try{
            p = process.start();
            BufferedReader br =  new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            
            while ( (line = br.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            
            JsonObject jo = Json.createReader(new StringReader(builder.toString())).readObject();
                
            MeteoPodaci mp = new MeteoPodaci();

            mp.setSunRise(new Date(jo.getJsonObject("sys").getJsonNumber("sunrise").bigDecimalValue().longValue()*1000));
            mp.setSunSet(new Date(jo.getJsonObject("sys").getJsonNumber("sunset").bigDecimalValue().longValue()*1000));

            mp.setTemperatureValue(new Double(jo.getJsonObject("main").getJsonNumber("temp").doubleValue()).floatValue());
            mp.setTemperatureMin(new Double(jo.getJsonObject("main").getJsonNumber("temp_min").doubleValue()).floatValue());
            mp.setTemperatureMax(new Double(jo.getJsonObject("main").getJsonNumber("temp_max").doubleValue()).floatValue());
            mp.setTemperatureUnit("°C");

            mp.setHumidityValue(new Double(jo.getJsonObject("main").getJsonNumber("humidity").doubleValue()).floatValue());
            mp.setHumidityUnit("%");

            mp.setPressureValue(new Double(jo.getJsonObject("main").getJsonNumber("pressure").doubleValue()).floatValue());
            mp.setPressureUnit("hPa");

            if (jo.getJsonObject("wind").getJsonNumber("speed") == null) {
                mp.setWindSpeedValue(new Double(("0")).floatValue());
            }
            else{
                mp.setWindSpeedValue(new Double(jo.getJsonObject("wind").getJsonNumber("speed").doubleValue()).floatValue());
            }
            mp.setWindSpeedName("");

            if (jo.getJsonObject("wind").getJsonNumber("deg") == null) {
                mp.setWindDirectionValue(new Double(("0")).floatValue());
            }
            else{
                mp.setWindDirectionValue(new Double(jo.getJsonObject("wind").getJsonNumber("deg").doubleValue()).floatValue());
            }
            mp.setWindDirectionCode("");
            mp.setWindDirectionName("");

            mp.setCloudsValue(jo.getJsonObject("clouds").getInt("all"));
            mp.setCloudsName(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setPrecipitationMode("");

            mp.setWeatherNumber(jo.getJsonArray("weather").getJsonObject(0).getInt("id"));
            mp.setWeatherValue(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setWeatherIcon(jo.getJsonArray("weather").getJsonObject(0).getString("icon"));

            mp.setLastUpdate(new Date(jo.getJsonNumber("dt").bigDecimalValue().longValue()*1000));

            listaMeteoPodataka.add(mp);
        }
        catch (IOException e)
        {   
            Pocetna.getjTextFieldPoruka().setText("Greška kod preuzimanja meteo podataka. Pokušaj kliknuti na gumb 'Osvježi'.");
            e.printStackTrace();
        }
    }
    
    static Image dajIkonu(String ikonaID, String velicina){
        Image ikona = null;
        
        try {
            URL url = new URL(
                "http://openweathermap.org/img/wn/" + ikonaID + velicina + ".png");
            ikona = ImageIO.read(url);
        } catch (IOException e) {
            Pocetna.getjTextFieldPoruka().setText("Greška kod Preuzimanja ikone. Pokušaj kliknuti na gumb 'Osvježi'.");
        }
        
        return ikona;
    
    }
    
    /**
     * prikazuje podatke u prosljeđenoj formi
     * @param panelDanas
     * @param panelPrognoza
     * @param listaMeteoPodataka 
     */
    public static void prikaziMeteoPodatke(JPanel panelDanas, JPanel panelPrognoza, List<MeteoPodaci> listaMeteoPodataka){
        panelDanas.removeAll();
        panelPrognoza.removeAll();
        
        panelDanas.setLayout(new GridBagLayout()); 
        GridBagConstraints gridDanas = new GridBagConstraints();
        gridDanas.fill = GridBagConstraints.HORIZONTAL;
        gridDanas.insets = new Insets(-20,0,0,0);
        
        panelPrognoza.setLayout(new GridBagLayout()); 
        GridBagConstraints gridPrognoza = new GridBagConstraints();
        gridPrognoza.fill = GridBagConstraints.HORIZONTAL;
        gridPrognoza.insets = new Insets(1,8,1,8);
        
        //meteo podaci za danas
        MeteoPodaci mp = listaMeteoPodataka.get(0);
        
        //dodavanje ikone za danas u panel
        gridDanas.gridx = 0;
        gridDanas.gridy = 0;
        panelDanas.add(new JLabel(new ImageIcon(dajIkonu(mp.getWeatherIcon(),"@2x"))), gridDanas);
        
        //dodavanje ostalih podataka za danas u panel
        gridDanas.gridx = 0;
        gridDanas.gridy = 1;
        panelDanas.add(new JLabel(
                "<html><font color=\"white\">Trenutno: " + mp.getWeatherValue() + "<br/>"+
                "Trenutno: " + mp.getTemperatureValue().toString() + " " + mp.getTemperatureUnit() + "<br/>" +
                "Min: " + mp.getTemperatureMin().toString() + " " + mp.getTemperatureUnit() + "<br/>" +
                "Max: " + mp.getTemperatureMax().toString() + " " + mp.getTemperatureUnit() + "<br/>" +
                "Izlazak sunca: " + formatterSat.format(mp.getSunRise()) + "<br/>" +
                "Zalazak sunca: " + formatterSat.format(mp.getSunSet()) + "</font></html>"), gridDanas);

        //dodavanje ostatka prognoze u panel
        int gridxIkonaTekst = 0, gridyTekstDatum = 0, gridyIkona = 1, gridyTekst = 2;
        for (MeteoPodaci meteoPodaci : listaMeteoPodataka) {
            if(meteoPodaci != listaMeteoPodataka.get(0)){
                //dodavanje datuma prognoze
                gridPrognoza.gridx = gridxIkonaTekst;
                gridPrognoza.gridy = gridyTekstDatum;
                panelPrognoza.add(new JLabel("<html><font color=\"white\">" + formatterDatum.format(meteoPodaci.getSunRise()) + "</html>"), gridPrognoza);
                
                //dodavanje ikone prognoze
                gridPrognoza.gridx = gridxIkonaTekst;
                gridPrognoza.gridy = gridyIkona;
                panelPrognoza.add(new JLabel(new ImageIcon(dajIkonu(meteoPodaci.getWeatherIcon(),""))), gridPrognoza);

                //dodavanje info prognoze
                gridPrognoza.gridx = gridxIkonaTekst;
                gridPrognoza.gridy = gridyTekst;
                panelPrognoza.add(new JLabel(
                    "<html><font color=\"white\">Min: " + meteoPodaci.getTemperatureMin().toString()  + " " + mp.getTemperatureUnit() + "<br/>" +  
                    "Max: " + meteoPodaci.getTemperatureMax().toString() + " " + mp.getTemperatureUnit() + "</font></html>"), gridPrognoza);
                gridxIkonaTekst++;
            }
        }
        panelDanas.revalidate();
        panelPrognoza.revalidate();
    }
    
    public List<MeteoPodaci> getListaMeteoPodataka() {
        return listaMeteoPodataka;
    }

    public void setListaMeteoPodataka(List<MeteoPodaci> listaMeteoPodataka) {
        MetodeMeteo.listaMeteoPodataka = listaMeteoPodataka;
    }
}
