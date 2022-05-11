/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import view.krv.Pocetna;
//import org.json.JSONException;
//import org.json.JSONObject;



/**
 *
 * @author iduras
 */
public class proba {
 
    public static void main(String args[]) throws ParseException, MalformedURLException, IOException {
        System.out.println("proba");
        JPanel jPanelSlikaDanas = null;

//MetodeMeteo.generirajPrikazMeteo("Varazdin", "HR", jPanelSlikaDanas);
//        MetodeMeteo.povuciPodatkeSve("Varazdin", "HR");


try {
            URL url = new URL(
                "https://maker.ifttt.com/trigger/  this.naredba /with/key/mzZTuxP03gmKKROV0zbmn");
            System.out.println(ImageIO.read(url));
        } catch (IOException e) {
            Pocetna.getjTextFieldPoruka().setText("Greška kod Preuzimanja ikone. Pokušaj kliknuti na gumb 'Osvježi'.");
        }














//        prikazSljedecihSedamDana(Dogadaj.getListaSvihDogadaja(), 1, 8, 2019);

//
//boolean isMetric = true;
//    String owmApiKey = "508677720ef8cc355645dce949620fbb"; /* YOUR OWM API KEY HERE */
//    String weatherCity = "Brisbane,AU";
//    byte forecastDays = 3;
//    OpenWeatherMap.Units units = (isMetric)
//        ? OpenWeatherMap.Units.METRIC
//        : OpenWeatherMap.Units.IMPERIAL;
//    OpenWeatherMap owm = new OpenWeatherMap(units, owmApiKey);
//    try {
//      DailyForecast forecast = owm.dailyForecastByCityName(weatherCity, forecastDays);
//      System.out.println("Weather for: " + forecast.getCityInstance().getCityName());
//      int numForecasts = forecast.getForecastCount();
//      for (int i = 0; i < numForecasts; i++) {
//        DailyForecast.Forecast dayForecast = forecast.getForecastInstance(i);
//        DailyForecast.Forecast.Temperature temperature = dayForecast.getTemperatureInstance();
//        System.out.println("\t" + dayForecast.getDateTime());
//        System.out.println("\tTemperature: " + temperature.getMinimumTemperature() +
//            " to " + temperature.getMaximumTemperature() + "\n");
//      }
//    }
//    catch (JSONException e) {
//      e.printStackTrace();
//    }
        
        
//        generirajKalendar(Pocetna.getjTableKalendar(), listaListaDatuma, Calendar.getInstance().get(Calendar.MONTH) + 1,Calendar.getInstance().get(Calendar.YEAR));
//        
        
//        MetodeKalendara.dodijeliDatumuDogadaje(Dogadaj.getListaSvihDogadaja(), Pocetna.getListaListaDatuma(), Integer.toString(8), Integer.toString(2019));
        
        
        //radi
//        for (Iterator<Dogadaj> iterator = Dogadaj.getListaSvihDogadaja().iterator(); iterator.hasNext();) {
//            Dogadaj dogadaj = iterator.next();
//            
////            System.out.println(dogadaj.getDatumIVrijemePocetka());
//            try {
//                LocalDate localDate = dogadaj.getDatumIVrijemePocetka().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    int year  = localDate.getYear();
//                    int month = localDate.getMonthValue();
//                    int day   = localDate.getDayOfMonth();
//                    
//                    System.out.println(year + " " + month + " " + day);
//            } catch (Exception e) {
//                Logger.getLogger(MetodeKalendara.class.getName()).log(Level.SEVERE, null, e);
//            }
//            
//        }
//        
        
        
        
        
        /**
         * radi, kalendar sprema u varijablu
         */
//        String out = new Scanner(new URL("https://calendar.google.com/calendar/ical/2ucj8rrcrvg49ltmg5rec06hao%40group.calendar.google.com/private-1a8aa760f8545a3cc1b92c417cabf831/basic.ics").openStream(), "UTF-8").useDelimiter("\\A").next();
//        
//        for (StringTokenizer stringTokenizer = new StringTokenizer(out); stringTokenizer.hasMoreTokens();) {
//            String token = stringTokenizer.nextToken();
//            System.out.println(token);
//        }
        
        
        
        /**
         * radi, kalendar sprema u datoteku
         */
//        try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://calendar.google.com/calendar/ical/2ucj8rrcrvg49ltmg5rec06hao%40group.calendar.google.com/private-1a8aa760f8545a3cc1b92c417cabf831/basic.ics").openStream());
//            FileOutputStream fileOS = new FileOutputStream("C:\\Users\\Korisnik\\Desktop\\file_name.txt")) {
//              byte data[] = new byte[1024];
//              int byteContent;
//              while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
//                  fileOS.write(data, 0, byteContent);
//              }
//          } catch (IOException e) {
//              // handles IO exceptions
//          }
        
//        System.out.println(out);
        
        
       
            
    }
    
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

//  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//    InputStream is = new URL(url).openStream();
//    try {
//      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//      String jsonText = readAll(rd);
//      JSONObject json = new JSONObject(jsonText);
//      return json;
//    } finally {
//      is.close();
//    }
//  }
    
    
//    public static void prikazSljedecihSedamDana(List<Dogadaj> ListaDogadaja, int dan, int mjesec, int godina){
//        MetodeKalendara.dohvatiDogadaje(MetodeKalendara.getURLKalendaraGooglePosao(), Dogadaj.getListaSvihDogadaja());
//        
//        SimpleDateFormat isoFormatDatumCjelodnevni = new SimpleDateFormat("yyyyMMdd");
//        
//        Date pocetniDatum = new Date();
//        Date zavrsniDatum = new Date();
//        
//        String sDan = Integer.toString(dan);
//        String sMjesec = Integer.toString(mjesec);
//        String sGodina = Integer.toString(godina);
//        
//        if(sDan.length() == 1){
//            sDan = "0" + sDan;
//        }
//        
//        if(sMjesec.length() == 1){
//            sMjesec = "0" + sMjesec;
//        }
//        
//        String datum = sGodina+sMjesec+sDan;
//        
//        //kreiranje početnog i završnog datuma 
//        try {
//            pocetniDatum = isoFormatDatumCjelodnevni.parse(datum);
//            
//            Calendar c = Calendar.getInstance();
//	
//	   //Setting the date to the given date
//	   c.setTime(isoFormatDatumCjelodnevni.parse(datum));
//	
//	   
//            //dodavanje 7 dana
//            c.add(Calendar.DAY_OF_MONTH, 7);  
//
//            //završni datum
//            zavrsniDatum = isoFormatDatumCjelodnevni.parse(isoFormatDatumCjelodnevni.format(c.getTime()));
//        } catch (Exception e) {
//        }
//        
//        
//        for (Dogadaj dogadaj : Dogadaj.getListaSvihDogadaja()) {
//            try {
//                
//                if (dogadaj.getDatumIVrijemePocetka().compareTo(pocetniDatum) == 1 && zavrsniDatum.compareTo(dogadaj.getDatumIVrijemePocetka()) == 1) {
//                    System.out.println(dogadaj.getNaziv());
//                }
//                
//                
//            } 
//            
//            catch (Exception e) {
//            }
//            
//        }
//    }
    
}
