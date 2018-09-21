package bd.com.infobox.weather.Utils;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
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
        Date date = new Date(timeInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String timeString = dateFormat.format(date);
        Log.e("getTime : ", timeString);

        return timeString;
    }
}
