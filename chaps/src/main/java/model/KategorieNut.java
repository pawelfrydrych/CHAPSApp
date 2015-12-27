package model;

import java.util.HashMap;

/**
 * Created by Pawe³ on 2015-12-19.
 */
public class KategorieNut {



    public HashMap<String,String> utwory = new HashMap<String, String>();
    public HashMap<String,String> koledy = new HashMap<String, String>();
    public HashMap<String,String> sakralne = new HashMap<String, String>();
    public HashMap<String,String> rozrywkowe = new HashMap<String, String>();
    public HashMap<String,String> ludowe = new HashMap<String, String>();
    public HashMap<String,String> wspolczesna = new HashMap<String, String>();
    public HashMap<String,String> kategorie = new HashMap<String, String>();

    public KategorieNut()
    {

        this.kategorie.put("Koledy","Koledy");
        this.kategorie.put("Muzyka sakralna","Muzyka sakralna");
        this.kategorie.put("Muzyka rozrywkowa","Muzyka rozrywkowa");
        this.kategorie.put("Muzyka ludowa","Muzyka ludowa");
        this.kategorie.put("Muzyka wspolczesna","Muzyka wspolczesna");


    }

}