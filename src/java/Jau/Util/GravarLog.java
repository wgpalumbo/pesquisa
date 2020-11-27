/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Util;

import Jau.Infra.Propriedades;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Win7
 */
public class GravarLog {

    //-------------
    public static void gravarLog(String cordao) throws IOException {
        //----
        Executor executor1 = Executors.newSingleThreadExecutor();

        executor1.execute(() -> {
            SimpleDateFormat ddmmaaaa = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
            Calendar todayip = Calendar.getInstance();
            String hojeip = ddmmaaaa.format(todayip.getTime());
            String hojeiplog = ddmmaaaalog.format(todayip.getTime());

            String txtseqip = cordao;
            String pastalog = new Propriedades().getLogDir();
            java.io.File arqip = new java.io.File(pastalog, "JAU" + hojeiplog + ".log");
            try {
                BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
                bf.write(hojeip + " = " + txtseqip + "\r\n");
                bf.flush();
                bf.close();
            } catch (IOException ex) {
                Logger.getLogger(GravarLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

    }

    //-------------
    public static void gravarTexto(String cordao) throws IOException {
        SimpleDateFormat ddmmaaaa = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
        Calendar todayip = Calendar.getInstance();
        String hojeip = ddmmaaaa.format(todayip.getTime());
        String hojeiplog = ddmmaaaalog.format(todayip.getTime());

        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "dem-texto-" + hojeiplog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(hojeip + " = " + txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

    public static void gravarGPS(String cordao) throws IOException {
        SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
        Calendar todayip = Calendar.getInstance();
        String hojeiplog = ddmmaaaalog.format(todayip.getTime());
        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "gps-demanda-" + hojeiplog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

    public static void gravarNaoAchouGPS(String data, String cordao) throws IOException {
        String hojeiplog = data.replace("/", "");
        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "naoachougps-" + hojeiplog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

    //-------------
    public static void gravarErros(String cordao) throws IOException {
        SimpleDateFormat ddmmaaaa = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
        Calendar todayip = Calendar.getInstance();
        String hojeip = ddmmaaaa.format(todayip.getTime());
        String hojeiplog = ddmmaaaalog.format(todayip.getTime());

        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "Erros-" + hojeiplog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(hojeip + " = " + txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

    //-------------
    public static void gravarRobo(String cordao) throws IOException {
        SimpleDateFormat ddmmaaaa = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
        Calendar todayip = Calendar.getInstance();
        String hojeip = ddmmaaaa.format(todayip.getTime());
        String hojeiplog = ddmmaaaalog.format(todayip.getTime());

        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "Consolidar-Geral-" + hojeiplog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(hojeip + " = " + txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

    //-------------
    public static void gravarRobo2(String datalog, String cordao) throws IOException {
        SimpleDateFormat ddmmaaaa = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        SimpleDateFormat ddmmaaaalog = new SimpleDateFormat("ddMMyyyy");
        Calendar todayip = Calendar.getInstance();
        String hojeip = ddmmaaaa.format(todayip.getTime());
        String hojeiplog = ddmmaaaalog.format(todayip.getTime());

        String txtseqip = cordao;
        String pastalog = new Propriedades().getLogDir();
        java.io.File arqip = new java.io.File(pastalog, "Consolidar-Saidas-" + datalog + ".txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(arqip, true));
        bf.write(hojeip + " = " + txtseqip + "\r\n");
        bf.flush();
        bf.close();

    }

}
