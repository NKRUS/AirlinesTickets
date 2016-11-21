package ru.nk.tickets.util;

import ru.nk.tickets.model.City;
import ru.nk.tickets.model.PlaceClass;

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
}
