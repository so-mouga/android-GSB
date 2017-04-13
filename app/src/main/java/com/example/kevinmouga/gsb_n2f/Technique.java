package com.example.kevinmouga.gsb_n2f;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by kevinmouga on 05/02/2017.
 */

public class Technique {


    public static String datenow (){
        SimpleDateFormat formatter  = new SimpleDateFormat ("yyyyMM");
        Date currentTime_1 = new Date();
        String dateNow = formatter.format(currentTime_1);

        return dateNow;
    }

    public static String reultatLong(String resultat){

        String resu;
        if(!Objects.equals(resultat, "-1")){
            resu = "ok";
        }else {
            resu ="erreur";
        }
        return resu;
    }
    public static boolean compareDate ( Date date1 , Date date2){
        Boolean resu=false;
        if(date1.after(date2)){
            resu=true;
        }

        return resu;
    }

}