package xyz.hasnat.weather.extras;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeAndDateConverter {

    public  static String getDate(long dateInSeconds){
        Date date = new Date(dateInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String dateString = dateFormat.format(date);
        Log.e("getDate : ", dateString);
        return dateString;
    }

    public static String getTime(long timeInSeconds){
        Date time = new Date(timeInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String timeString = dateFormat.format(time);
        Log.e("getTime : ", timeString);

        return timeString;
    }

    public static String getDay(long timeInSeconds){
        // current date
       Date currentDate = Calendar.getInstance().getTime();
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       String today = formatter.format(currentDate);

        // api date
        Date day = new Date(timeInSeconds * 1000);
        SimpleDateFormat datFormat = new SimpleDateFormat("EEEE");
        //SimpleDateFormat formatterAPIdate = new SimpleDateFormat("dd/MM/yyyy");
        String dayNameString = datFormat.format(day);

        if (currentDate.equals(day)){
            return "Today";
        }

        //Log.e("getTime : ", dayNameString);

        return dayNameString;
    }

}
