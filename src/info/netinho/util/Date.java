package info.netinho.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.datatype.XMLGregorianCalendar;

public class Date {

    public static String getDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
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
        DateFormat df = new SimpleDateFormat(format);

        df.setLenient(false);

        return df.format(date);
    }
}