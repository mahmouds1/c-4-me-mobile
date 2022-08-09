package edu.wit.mobileapp.c_4_me_mobile;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public String DATE_PATTERN = "^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|[3][01])/[0-9]{4}$";
    public Boolean isValidDateInput(String dateString) {
        if (dateString == null || !dateString.matches(DATE_PATTERN)) {
            Log.v("myApp", "dateString " + dateString + " no match regex");
            return false;
        }
        Date date = null;
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = dtf.parse(dateString);
        } catch (ParseException e) {
            Log.v("myApp", "dateString " + dateString + " no match formatter");
            return false;
        }
        return true;
    }

    public Long getUnixTimestampDateFromInput(String dateString) {
        Date date = null;
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = dtf.parse(dateString);
            assert date != null;
            return date.getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    public String getDateStringFromTimestamp(Long timestamp) {
        Date curDate = new Date(timestamp);
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy");
        return dtf.format(curDate);
    }
}
