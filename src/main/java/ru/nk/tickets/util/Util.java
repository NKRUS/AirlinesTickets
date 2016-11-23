package ru.nk.tickets.util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import ru.nk.tickets.model.City;
import ru.nk.tickets.model.PlaceClass;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;

/**
 * Created by NK on 21.11.2016.
 */
public class Util {
    public static boolean containsCity(Collection<City> cities, String city) {
        for(City c : cities) {
            if(c.getCity().toLowerCase().equals(city.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static City getCityByName(Collection<City> cities, String city){
        for(City c : cities) {
            if(c.getCity().toLowerCase().equals(city.toLowerCase())) {
                return c;
            }
        }
        return null;
    }
    public static PlaceClass getPlaceClassByName(Collection<PlaceClass> placeClasses, String placeClass){
        for(PlaceClass pc : placeClasses) {
            if(pc.getClass_title().toLowerCase().equals(placeClass.toLowerCase())) {
                return pc;
            }
        }
        return null;
    }

    public static Image getAviaLogoByName(String name){
        String path = "";

        switch (name){
            case "Aeroflot":
                path=DBUtil.prop.getProperty("image.aeroflot");
                break;
            case "Transaero":
                path=DBUtil.prop.getProperty("image.transaero");
                break;
            case "S7 Airlines":
                path=DBUtil.prop.getProperty("image.s7");
                break;
            case "Red Wings":
                path=DBUtil.prop.getProperty("image.red_wings");
                break;
            case "UTair":
                path=DBUtil.prop.getProperty("image.utair");
                break;
            case "Nord Wind":
                path=DBUtil.prop.getProperty("image.nord_wind");
                break;
            case "Vim Avia":
                path=DBUtil.prop.getProperty("image.vim_avia");
                break;
            case "Ural Airlines":
                path=DBUtil.prop.getProperty("image.ural_airlines");
                break;
        }

        return new Image(path);
    }

    public static String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("dd MMM yyyy, E");
        String text = df.format(date);
        return text;
    }

    public static String timeToString(Time time){
        Date myDate = new Date(time.getTime());
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateStr = df.format(myDate);
        return dateStr;
    }

    public static String diffBeetwenTwoTimes(Date d1, Time t1, Date d2, Time t2){
        long c = (t2.getTime()+d2.getTime()) - (t1.getTime()+d1.getTime()) - (3*60*60*1000);
        DateFormat df = new SimpleDateFormat("HHч mmм");
        String dateStr = df.format(c);
        return dateStr;
    }

    public static void showAlert(Alert.AlertType type, String title, String headerText, String contextText){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);

        alert.showAndWait();
    }

    public static boolean validateOnlyNumbers(String string){
        String regEX = "[0-9 ]+";
        if(string.matches(regEX)) return true;
        return false;
    }

    public static Date localDateToSqlDate(LocalDate localDate){
        java.util.Date date =
                java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date dateSQL = new java.sql.Date(date.getTime());
        return dateSQL;
    }
}
