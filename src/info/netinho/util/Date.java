package info.netinho.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Date {

    private static DatatypeFactory df = null;

    static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException("Exception while obtaining DatatypeFactory instance", dce);
        }
    }

    /**
     * Converts a java.util.Date into an instance of XMLGregorianCalendar
     *
     * @param date Instance of java.util.Date or a null reference
     * @return XMLGregorianCalendar instance whose value is based upon the value
     * in the date parameter. If the date parameter is null then this method
     * will simply return null.
     */
    public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return df.newXMLGregorianCalendar(gc);
        }
    }

    /**
     * Converts an XMLGregorianCalendar to an instance of java.util.Date
     *
     * @param xgc Instance of XMLGregorianCalendar or a null reference
     * @return java.util.Date instance whose value is based upon the value in
     * the xgc parameter. If the xgc parameter is null then this method will
     * simply return null.
     */
    public static java.util.Date asDate(XMLGregorianCalendar xgc) {
        return (xgc == null) ? null : xgc.toGregorianCalendar().getTime();
    }

    public static String getXMLGregorianCalendarDateToString(XMLGregorianCalendar date) {
        String dia = Integer.toString(date.getDay());
        while (dia.length() < 2) {
            dia = "0" + dia;
        }

        String mes = Integer.toString(date.getMonth());
        while (mes.length() < 2) {
            mes = "0" + mes;
        }

        return dia + "/" + mes + "/" + Integer.toString(date.getYear());
    }

    public static String getXMLGregorianCalendarTimeToString(XMLGregorianCalendar time) {
        String hora = Integer.toString(time.getHour());
        while (hora.length() < 2) {
            hora = "0" + hora;
        }

        String minuto = Integer.toString(time.getMinute());
        while (minuto.length() < 2) {
            minuto = "0" + minuto;
        }

        String segundo = Integer.toString(time.getSecond());
        while (segundo.length() < 2) {
            segundo = "0" + segundo;
        }

        return hora + ":" + minuto + ":" + segundo;
    }

    public static String getDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    public static String getMonthName(int month) {
        String monthname = "";

        switch (month) {
            case 1:
                monthname = "Janeiro";
                break;
            case 2:
                monthname = "Fevereiro";
                break;
            case 3:
                monthname = "Março";
                break;
            case 4:
                monthname = "Abril";
                break;
            case 5:
                monthname = "Maio";
                break;
            case 6:
                monthname = "Junho";
                break;
            case 7:
                monthname = "Julho";
                break;
            case 8:
                monthname = "Agosto";
                break;
            case 9:
                monthname = "Setembro";
                break;
            case 10:
                monthname = "Outubro";
                break;
            case 11:
                monthname = "Novembro";
                break;
            case 12:
                monthname = "Dezembro";
        }

        return monthname;
    }

    private static Calendar getCalendarByMonth(int year, int month) {
        Calendar c = Calendar.getInstance();

        c.set(1, year);

        switch (month) {
            case 1:
                c.set(2, 0);
                break;
            case 2:
                c.set(2, 1);
                break;
            case 3:
                c.set(2, 2);
                break;
            case 4:
                c.set(2, 3);
                break;
            case 5:
                c.set(2, 4);
                break;
            case 6:
                c.set(2, 5);
                break;
            case 7:
                c.set(2, 6);
                break;
            case 8:
                c.set(2, 7);
                break;
            case 9:
                c.set(2, 8);
                break;
            case 10:
                c.set(2, 9);
                break;
            case 11:
                c.set(2, 10);
                break;
            case 12:
                c.set(2, 11);
        }

        return c;
    }

    public static java.util.Date getFirstDayOfMonth(int year, int month) {
        Calendar c = getCalendarByMonth(year, month);
        c.set(5, c.getActualMinimum(5));
        return c.getTime();
    }

    public static java.util.Date getLastDayOfMonth(int year, int month) {
        Calendar c = getCalendarByMonth(year, month);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static String formatDate(java.util.Date date) {
        return formatDate(date, "dd/MM/yyyy");
    }

    public static String formatDate(java.util.Date date, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.format(date);
    }

    public static java.util.Date parseDate(String date) {
        return parseDate(date, "dd/MM/yyyy");
    }

    public static java.util.Date parseDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if ((date != null) && (!date.isEmpty())) {
            try {
                return sdf.parse(date);
            } catch (ParseException ex) {
                return null;
            }
        }

        return null;
    }
}