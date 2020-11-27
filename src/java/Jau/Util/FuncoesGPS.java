/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author Win7
 */
public class FuncoesGPS {

    public static double getDistancia1(String lat1, String lon1, String lat2, String lon2, String onde) throws IOException {
        double d = 0;
        try {
            //--------------
            double nlat1 = Double.parseDouble(lat1.replace(",", "."));
            double nlon1 = Double.parseDouble(lon1.replace(",", "."));
            double nlat2 = Double.parseDouble(lat2.replace(",", "."));
            double nlon2 = Double.parseDouble(lon2.replace(",", "."));
            //--------------
            int R = 6378137;
            double dLat = Math.toRadians(nlat2 - nlat1);
            double dLon = Math.toRadians(nlon2 - nlon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(nlat1)) * Math.cos(Math.toRadians(nlat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            d = R * c;
        } catch (Exception e) {
            GravarLog.gravarLog("Funcoes-GetDistancia: " + onde + " -> " + lat1 + ";" + lon1 + " - " + lat2 + ";" + lon2 + " = " + e.getMessage());
        }
        return d; // returns the distance in meter

    }

    public static double getDistancia(String lat1, String lon1, String lat2, String lon2) throws IOException {
        double d = 0;
        try {
            //--------------
            double nlat1 = Double.parseDouble(lat1.replace(",", "."));
            double nlon1 = Double.parseDouble(lon1.replace(",", "."));
            double nlat2 = Double.parseDouble(lat2.replace(",", "."));
            double nlon2 = Double.parseDouble(lon2.replace(",", "."));
            //--------------
            int R = 6378137;
            double dLat = Math.toRadians(nlat2 - nlat1);
            double dLon = Math.toRadians(nlon2 - nlon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(nlat1)) * Math.cos(Math.toRadians(nlat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            d = R * c;
        } catch (Exception e) {
            GravarLog.gravarLog("Funcoes-GetDistancia: " + lat1 + ";" + lon1 + " - " + lat2 + ";" + lon2 + " = " + e.getMessage());
        }
        return d; // returns the distance in meter

    }

    public static double HoraEmSegundos(String Hora1) {
        if (Hora1 == null || !Hora1.contains(":")) {
            return 0;
        }
        double h1 = Double.parseDouble(Hora1.split(":")[0]);
        if (h1 < 4) {
            h1 += 24;
        }
        h1 = h1 * 3600;
        double m1 = Double.parseDouble(Hora1.split(":")[1]);
        m1 = m1 * 60;
        double s1 = Double.parseDouble(Hora1.split(":")[2]);
        double hor1 = h1 + m1 + s1;
        return hor1;
    }

    public static double HoraNum(String Hora1) {
        if (Hora1 == null || !Hora1.contains(":")) {
            return 0;
        }
        double h1 = Double.parseDouble(Hora1.split(":")[0]);
        if (h1 < 4) {
            h1 += 24;
        }
        double m1 = Double.parseDouble(Hora1.split(":")[1]);
        m1 = m1 / 60;
        double s1 = Double.parseDouble(Hora1.split(":")[2]);
        s1 = s1 / 3600;
        double hor1 = h1 + m1 + s1;
        return hor1;
    }

    public static double HoraNumPuro(String Hora1) {
        if (Hora1 == null || !Hora1.contains(":")) {
            return 0;
        }
        double h1 = Double.parseDouble(Hora1.split(":")[0]);

        double m1 = Double.parseDouble(Hora1.split(":")[1]);
        m1 = m1 / 60;
        double s1 = Double.parseDouble(Hora1.split(":")[2]);
        s1 = s1 / 3600;
        double hor1 = h1 + m1 + s1;
        return hor1;
    }

    public static String NumHora(double Hora1) {
        double horas = (int) (Hora1);
        double minutos = (Hora1 % 1) * 60;
        minutos = Math.round(minutos);
        String varhor = String.valueOf((int) horas);
        String varmin = String.valueOf((int) minutos);
        if (minutos < 10) {
            varmin += "0";
        }
        return varhor + ":" + varmin;
    }

    public static String NumMinutos(double Hora1) {
        // In a string replace one substring with another
        double horas = (int) (Hora1);
        double minutos = (Hora1 % 1) * 60;
        double hor1 = horas * 60 + minutos;
        hor1 = Math.round(hor1);
        String varmin = String.valueOf((int) hor1);
        if (hor1 < 10) {
            varmin = " " + varmin;
        }
        if (hor1 == 0) {
            varmin = "  ";
        }
        return varmin;
    }

    public static double MinutoTotal(String Hora1) {
        double retorno = 0;
        try {
            if (Hora1.equals("") || Hora1.equals("&nbsp;")) {
                return retorno;
            }
            if (Hora1.replace(":", "").length() < 4) {
                return retorno;
            }
            if (Hora1.replace(":", "").length() > 4) {
                Hora1 = Hora1.replace(":", "").substring(0, 2) + Hora1.replace(":", "").substring(2, 4);
            }
            double h1 = Double.parseDouble(Hora1.substring(0, 2));
            double m1 = Double.parseDouble(Hora1.substring(2, 4));
            retorno = h1 * 60 + m1;
        } catch (Exception e) {
        }
        return retorno;
    }

    public static String DifHora(String Hora1, String Hora2) throws IOException {
        String res = "";
        try {
            if (Hora1 == null || Hora1.equals("")) {
                return "";
            }
            if (Hora2 == null || Hora2.equals("")) {
                return "";
            }
            if (Hora1.length() < 4) {
                return "";
            }
            if (Hora2.length() < 4) {
                return "";
            }
            if (Hora1.length() > 4) {
                Hora1 = Hora1.substring(0, 2) + Hora1.substring(3, 5);
            }
            if (Hora2.length() > 4) {
                Hora2 = Hora2.substring(0, 2) + Hora2.substring(3, 5);
            }
            double h1 = Double.parseDouble(Hora1.substring(0, 2));
            double m1 = Double.parseDouble(Hora1.substring(2, 4));
            m1 = m1 / 60;
            double h2 = Double.parseDouble(Hora2.substring(0, 2));
            double m2 = Double.parseDouble(Hora2.substring(2, 4));
            m2 = m2 / 60;
            double hor1 = h1 + m1;
            double hor2 = h2 + m2;
            if (hor2 < hor1) {
                hor2 += 24;
            }
            double difhor = (hor2 - hor1);
            double horas = (int) (difhor);
            double minutos = (difhor % 1) * 60;
            minutos = Math.round(minutos * 100);
            minutos = minutos / 100;
            if (minutos == 60) {
                minutos = 0;
                horas++;
            }
            String rHor = String.valueOf((int) horas);
            if (rHor.length() < 2) {
                rHor = "0" + rHor;
            }
            String rMin = String.valueOf((int) minutos);
            if (rMin.length() < 2) {
                rMin = "0" + rMin;
            }
            res = rHor + ":" + rMin;
        } catch (Exception e) {
            GravarLog.gravarLog("DifHora:" + e.getMessage());
        }
        return res;
    }

    public static String SegundosHora(double totalSecs) {
        double hours = totalSecs / 3600;
        double minutes = (totalSecs % 3600) / 60;
        double seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static double MinutosEmSegundos(String Hora1) {
        if (Hora1 == null) {
            return 0;
        }
        double m1 = Double.parseDouble(Hora1);
        m1 = m1 * 60;

        double hor1 = m1;
        return hor1;
    }

    public static String EpocaToData(String timestamp) {
        String retorno = "";
        try {
            Timestamp ts = new Timestamp(Long.parseLong(timestamp));
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            retorno = f.format(ts);
        } catch (Exception e) {
        }
        return retorno;
    }

    public static double[] UTM2Deg(String UTM) {

        double retorno[] = new double[2];

        String[] parts = UTM.split(" ");
        int Zone = Integer.parseInt(parts[0]);
        char Letter = parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
        double Easting = Double.parseDouble(parts[2]);
        double Northing = Double.parseDouble(parts[3]);
        double Hem;
        if (Letter > 'M') {
            Hem = 'N';
        } else {
            Hem = 'S';
        }
        double north;
        if (Hem == 'S') {
            north = Northing - 10000000;
        } else {
            north = Northing;
        }
        double latitude = (north / 6366197.724 / 0.9996 + (1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) - 0.006739496742 * Math.sin(north / 6366197.724 / 0.9996) * Math.cos(north / 6366197.724 / 0.9996) * (Math.atan(Math.cos(Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996))) * Math.tan((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) - north / 6366197.724 / 0.9996) * 3 / 2) * (Math.atan(Math.cos(Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996))) * Math.tan((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) - north / 6366197.724 / 0.9996)) * 180 / Math.PI;
        latitude = Math.round(latitude * 10000000);
        latitude = latitude / 10000000;
        double longitude = Math.atan((Math.exp((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)) - Math.exp(-(Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3))) / 2 / Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996 - 0.006739496742 * 3 / 4 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / 6366197.724 / 0.9996 + Math.sin(2 * north / 6366197.724 / 0.9996) / 2) + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 4 + Math.sin(2 * north / 6366197.724 / 0.9996) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) / 3)) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))) * (1 - 0.006739496742 * Math.pow((Easting - 500000) / (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))), 2) / 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)) + north / 6366197.724 / 0.9996)) * 180 / Math.PI + Zone * 6 - 183;
        longitude = Math.round(longitude * 10000000);
        longitude = longitude / 10000000;

        retorno[0] = latitude;
        retorno[1] = longitude;

        return retorno;
    }

    private void Deg2UTM(double Lat, double Lon) {

        double Easting;
        double Northing;
        int Zone;
        char Letter;

        Zone = (int) Math.floor(Lon / 6 + 31);
        if (Lat < -72) {
            Letter = 'C';
        } else if (Lat < -64) {
            Letter = 'D';
        } else if (Lat < -56) {
            Letter = 'E';
        } else if (Lat < -48) {
            Letter = 'F';
        } else if (Lat < -40) {
            Letter = 'G';
        } else if (Lat < -32) {
            Letter = 'H';
        } else if (Lat < -24) {
            Letter = 'J';
        } else if (Lat < -16) {
            Letter = 'K';
        } else if (Lat < -8) {
            Letter = 'L';
        } else if (Lat < 0) {
            Letter = 'M';
        } else if (Lat < 8) {
            Letter = 'N';
        } else if (Lat < 16) {
            Letter = 'P';
        } else if (Lat < 24) {
            Letter = 'Q';
        } else if (Lat < 32) {
            Letter = 'R';
        } else if (Lat < 40) {
            Letter = 'S';
        } else if (Lat < 48) {
            Letter = 'T';
        } else if (Lat < 56) {
            Letter = 'U';
        } else if (Lat < 64) {
            Letter = 'V';
        } else if (Lat < 72) {
            Letter = 'W';
        } else {
            Letter = 'X';
        }
        Easting = 0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) * 0.9996 * 6399593.62 / Math.pow((1 + Math.pow(0.0820944379, 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)), 0.5) * (1 + Math.pow(0.0820944379, 2) / 2 * Math.pow((0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin(Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) / 3) + 500000;
        Easting = Math.round(Easting * 100) * 0.01;
        Northing = (Math.atan(Math.tan(Lat * Math.PI / 180) / Math.cos((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) - Lat * Math.PI / 180) * 0.9996 * 6399593.625 / Math.sqrt(1 + 0.006739496742 * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) * (1 + 0.006739496742 / 2 * Math.pow(0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180))) / (1 - Math.cos(Lat * Math.PI / 180) * Math.sin((Lon * Math.PI / 180 - (6 * Zone - 183) * Math.PI / 180)))), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) + 0.9996 * 6399593.625 * (Lat * Math.PI / 180 - 0.005054622556 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + 4.258201531e-05 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 - 1.674057895e-07 * (5 * (3 * (Lat * Math.PI / 180 + Math.sin(2 * Lat * Math.PI / 180) / 2) + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 4 + Math.sin(2 * Lat * Math.PI / 180) * Math.pow(Math.cos(Lat * Math.PI / 180), 2) * Math.pow(Math.cos(Lat * Math.PI / 180), 2)) / 3);
        if (Letter < 'M') {
            Northing = Northing + 10000000;
        }
        Northing = Math.round(Northing * 100) * 0.01;
    }

}
