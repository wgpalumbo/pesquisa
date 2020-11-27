/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Infra;

import java.util.ArrayList;

/**
 *
 * @author Walter
 */
public class InfoPesquisa {

    public static ArrayList Modos() throws Exception {
        ArrayList<String> lstModos = new ArrayList<>();
        lstModos.add("01-A pé (maior ou igual 500m/5min");
        lstModos.add("02-Bicicleta");
        lstModos.add("03-Onibus Municipal Jau");
        lstModos.add("04-Onibus Suburbano");
        lstModos.add("05-Onibus Intermunicipal");
        lstModos.add("06-Vans/Micro/Alternativo");
        lstModos.add("07-Onibus Interestadual");
        lstModos.add("08-Transp.Escolar");
        lstModos.add("09-Transp.Fretado");
        lstModos.add("10-Condutor Auto");
        lstModos.add("11-Passageiro Auto");
        lstModos.add("12-Uber e similares");
        lstModos.add("13-Taxi");
        lstModos.add("14-Moto-Taxi");
        lstModos.add("15-Moto");
        lstModos.add("16-Caminhão");
        lstModos.add("17-Outros");
        return lstModos;

    }

    public static ArrayList ModosPR() throws Exception {
        ArrayList<String> lstModos = new ArrayList<>();
        lstModos.add("1-Coletivo");
        lstModos.add("2-Indiv.Priv.");
        lstModos.add("3-Indiv.Publ.");
        lstModos.add("4-Nao Motor..");
        return lstModos;

    }

    public static ArrayList Motivos() throws Exception {
        ArrayList<String> lstMotivos = new ArrayList<>();
        lstMotivos.add("01-Residencia");
        lstMotivos.add("02-Trabalho");
        lstMotivos.add("03-Estudo (regular: faculdade, colegio)");
        lstMotivos.add("04-Estudo (outros: ingles, informática)");
        lstMotivos.add("05-Compras");
        lstMotivos.add("06-Assuntos Pessoais/Negocios");
        lstMotivos.add("07-Saude");
        lstMotivos.add("08-Lazer");
        lstMotivos.add("09-Esportes");
        lstMotivos.add("10-Transportar passag. p/trabalho");
        lstMotivos.add("11-Transportar passag. p/estudo");
        lstMotivos.add("12-Transportar passag. Por outros motivos");
        lstMotivos.add("13-Procurar Emprego");
        lstMotivos.add("14-Outros");
        return lstMotivos;

    }

    public static ArrayList Escolaridade() throws Exception {
        ArrayList<String> lstMotivos = new ArrayList<>();
        lstMotivos.add("01-Analfabeto");
        lstMotivos.add("02-Pre-Escola");
        lstMotivos.add("03-Fund./Primario Inc.");
        lstMotivos.add("04-Fund./Primario Comp.");
        lstMotivos.add("05-Fund./Ginasio Inc.");
        lstMotivos.add("06-Fund./Ginasio Comp.");
        lstMotivos.add("07-Colegial/Medio Inc.");
        lstMotivos.add("08-Colegial/Medio Comp.");
        lstMotivos.add("09-Superior Incompleto");
        lstMotivos.add("10-Superior Completo");
        lstMotivos.add("11-Pos Graduacao");
        lstMotivos.add("12-Menor 4 (Nao Estud)");
        return lstMotivos;

    }

    public static ArrayList Idades() throws Exception {
        ArrayList<String> lstMotivos = new ArrayList<>();
        lstMotivos.add("1-Ate 9");
        lstMotivos.add("2-10 a 19");
        lstMotivos.add("3-20 a 29");
        lstMotivos.add("4-30 a 39");
        lstMotivos.add("5-40 a 49");
        lstMotivos.add("6-50 a 59");
        lstMotivos.add("7-60 a 65");
        lstMotivos.add("8-65 ou mais");
        return lstMotivos;

    }

}
