package model;

import android.content.*;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class TotalCalendar {
    Context mContext;
    Intent mIntent;

    public TotalCalendar(Context context) {
        this.mContext = context;
    }

    public boolean insert(String dzien, String miesiac, String lokacja, String tytul, String opis, String godziny) {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        if((Miesiac(miesiac) == 0) && (currentMonth == 11)  )
        {
            year++;
        }

        int hh1 = 0;
        int mm1 = 0;
        int hh2 = 0;
        int mm2 = 0;

        if(isNumeric(getHH1(godziny)) && isNumeric(getMM2(godziny)))
        {
            Log.d("debug","numeric");
            hh1 = Integer.valueOf(getHH1(godziny)).intValue();
            mm1 = Integer.valueOf(getMM1(godziny)).intValue();
            hh2 = Integer.valueOf(getHH2(godziny)).intValue();
            mm2 = Integer.valueOf(getMM2(godziny)).intValue();
        }
        else
        {
            Log.d("debug","else");
            hh1 = 0;
            mm1 = 0;
            hh2 = 0;
            mm2 = 0;
        }

        beginTime.set(year, Miesiac(miesiac), Integer.valueOf(dzien).intValue(), hh1, mm1);
        endTime.set(year, Miesiac(miesiac), Integer.valueOf(dzien).intValue(), hh2, mm2);
        Log.d("debug", "Begin time: " + Miesiac(miesiac) + " " + Integer.valueOf(dzien));

        long startMillis = beginTime.getTimeInMillis();
        long endMillis = endTime.getTimeInMillis();


        ContentResolver cr = mContext.getContentResolver();
        ContentValues values = new ContentValues();
        TimeZone tz = TimeZone.getDefault();

        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE,tytul);
        values.put(CalendarContract.Events.DESCRIPTION,opis);
        values.put(CalendarContract.Events.CALENDAR_ID,1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE,tz.getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,values);


        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);

        this.mIntent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);


        return true;
    }

    public int Miesiac(String m) {
        if (m.equals("01")) {
            return 0;
        }
        if (m.equals("02")) {
            return 1;
        }
        if (m.equals("03")) {
            return 2;
        }
        if (m.equals("04")) {
            return 3;
        }
        if (m.equals("05")) {
            return 4;
        }
        if (m.equals("06")) {
            return 5;
        }
        if (m.equals("07")) {
            return 6;
        }
        if (m.equals("08")) {
            return 7;
        }
        if (m.equals("09")) {
            return 8;
        }
        if (m.equals("10")) {
            return 9;
        }
        if (m.equals("11")) {
            return 10;
        }
        if (m.equals("12")) {
            return 11;
        }
        return 0;
    }


    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }


    public String getHH1(String wejscie) {
        String hh1 = "0";
        if (wejscie.length() < 5) {
            return hh1;
        }
        int i;
        hh1 = wejscie.charAt(0) + "" + wejscie.charAt(1);
        if (Character.isDigit(Character.valueOf(wejscie.charAt(1)).charValue())) {
            i = 0;
        } else {
            i = 1;
        }
        if (i == 1) {
            return wejscie.charAt(0) + "";
        }
        return hh1;
    }

    public String getMM1(String wejscie) {
        String mm1 = "0";
        if (wejscie.length() < 5) {
            return mm1;
        }
        mm1 = wejscie.charAt(3) + "" + wejscie.charAt(4);
        if ((!Character.isDigit(Character.valueOf(wejscie.charAt(1)).charValue()) ? 1 : 0) == 1) {
            return wejscie.charAt(2) + "" + wejscie.charAt(3);
        }
        return mm1;
    }

    public String getHH2(String wejscie) {
        String hh2 = "0";
        if (wejscie.length() >= 5) {
            return wejscie.charAt(wejscie.length() - 5) + "" + wejscie.charAt(wejscie.length() - 4);
        }
        return hh2;
    }

    public static String getMM2(String wejscie) {
        String mm2 = "0";
        if (wejscie.length() >= 5) {
            mm2 = wejscie.charAt(wejscie.length() - 2) + "" + wejscie.charAt(wejscie.length() - 1);
        }
        if (Character.isDigit(mm2.charAt(0))) {
            return mm2;
        }
        return "0";
    }
}