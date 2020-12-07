/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Sistema;

import Jau.Infra.Conecta;
import Jau.Util.GravarLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Walter
 */
public class Relatorios {

    //-----------------
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final PrintWriter out;
    private final SimpleDateFormat datamask, datamaskbd;

    public ArrayList<String> lstFxRenda = new ArrayList<>();
    public ArrayList<String> lstZonas = new ArrayList<>();

    //-----------------
    /**
     * Creates a new instance of Relatorios
     *
     * @param pageContext
     * @throws java.lang.Exception
     */
    public Relatorios(PageContext pageContext) throws Exception {

        this.request = (HttpServletRequest) pageContext.getRequest();
        this.response = (HttpServletResponse) pageContext.getResponse();
        this.out = response.getWriter();
        //----------------------------------------
        datamask = new SimpleDateFormat("dd/MM/yyyy");
        datamaskbd = new SimpleDateFormat("yyyy-MM-dd");
        //----------------------------------------
        lstFxRenda = new ArrayList<>();
        lstZonas = new ArrayList<>();

    }

    public String castingData(String data) throws ParseException {
        return (data.length() > 0 ? "CAST('" + datamaskbd.format(datamask.parse(data)) + "' AS DATE)" : "NULL");
    }

    public HashMap<String, Integer> Populacao() throws Exception {
        HashMap<String, Integer> mapPop = new HashMap<>();
        mapPop.put("01", 3800);
        mapPop.put("02", 4793);
        mapPop.put("03", 7561);
        mapPop.put("04", 5577);
        mapPop.put("05", 7221);
        mapPop.put("06", 13113);
        mapPop.put("07", 4443);
        mapPop.put("08", 3851);
        mapPop.put("09", 7906);
        mapPop.put("10", 8493);
        mapPop.put("11", 7315);
        mapPop.put("12", 9143);
        mapPop.put("13", 11756);
        mapPop.put("14", 6542);
        mapPop.put("15", 3581);
        mapPop.put("16", 7153);
        mapPop.put("17", 8086);
        mapPop.put("18", 9825);
        mapPop.put("19", 518);
        mapPop.put("20", 363);
        return mapPop;
    }

    public ArrayList Modos() throws Exception {
        ArrayList<String> lstModos = new ArrayList<>();
        lstModos.add("01-A pé 500m/5min");
        lstModos.add("02-Bicicleta");
        lstModos.add("03-Municipal Jau");
        lstModos.add("04-Suburbano");
        lstModos.add("05-Intermunicipal");
        lstModos.add("06-Vans/Alternativo");
        lstModos.add("07- Interestadual");
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

    public ArrayList ModosPR() throws Exception {
        ArrayList<String> lstModos = new ArrayList<>();
        lstModos.add("1-Coletivo");
        lstModos.add("2-Indiv.Priv.");
        lstModos.add("3-Indiv.Publ.");
        lstModos.add("4-Trans.Ativo");
        return lstModos;

    }

    public static StringBuffer montaModos(ArrayList<String> lstModos, String id) throws Exception {
        StringBuffer buffRetorno = new StringBuffer();
        buffRetorno.append("<select name='modo' id='modo'/>");
        buffRetorno.append("<option value = ''>Selecione:</option>");
        buffRetorno.append("<option value = 'C'" + (id.equals("C") ? "selected" : "") + ">CARRO</option>");
        buffRetorno.append("<option value = 'O'" + (id.equals("O") ? "selected" : "") + ">ONIBUS</option>");
        try {
            for (int ij = 0; ij < lstModos.size(); ij++) {
                String cc = lstModos.get(ij);
                String ee[] = cc.split("-");
                String achou = (ee[0].trim().equals(id) ? " SELECTED" : " ");
                buffRetorno.append("<option value = '").append(ee[0].trim()).append("' ").append(achou).append(">").append(ee[1].trim()).append("</option>");
            }

        } catch (Exception e) {
            GravarLog.gravarLog("montaModos:" + e.getMessage());
        }
        buffRetorno.append("</select>");
        return buffRetorno;
    }

    public static StringBuffer montaMotivos(ArrayList<String> lstModos, String id) throws Exception {
        StringBuffer buffRetorno = new StringBuffer();
        buffRetorno.append("<select name='motivo' id='motivo'/>");
        buffRetorno.append("<option value = ''>Selecione:</option>");
        try {
            for (int ij = 0; ij < lstModos.size(); ij++) {
                String cc = lstModos.get(ij);
                String ee[] = cc.split("-");
                String achou = (ee[0].trim().equals(id) ? " SELECTED" : " ");
                buffRetorno.append("<option value = '").append(ee[0].trim()).append("' ").append(achou).append(">").append(ee[1].trim()).append("</option>");
            }

        } catch (Exception e) {
            GravarLog.gravarLog("montaModos:" + e.getMessage());
        }
        buffRetorno.append("</select>");
        return buffRetorno;
    }

    public static StringBuffer montaModosPrincipal(String id) throws Exception {
        StringBuffer buffRetorno = new StringBuffer();
        buffRetorno.append("<select name='modopr' id='modopr'/>");
        buffRetorno.append("<option value = ''>Selecione:</option>");
        ArrayList<String> lstModos = new ArrayList<>();
        lstModos.add("1-Coletivo");
        lstModos.add("2-Indiv.Priv.");
        lstModos.add("3-Indiv.Publ.");
        lstModos.add("4-Nao Motor..");
        try {
            for (int ij = 0; ij < lstModos.size(); ij++) {
                String cc = lstModos.get(ij);
                String ee[] = cc.split("-");
                String achou = (ee[0].trim().equals(id) ? " SELECTED" : " ");
                buffRetorno.append("<option value = '").append(ee[0].trim()).append("' ").append(achou).append(">").append(ee[1].trim()).append("</option>");
            }

        } catch (Exception e) {
            GravarLog.gravarLog("montaModos:" + e.getMessage());
        }
        buffRetorno.append("</select>");
        return buffRetorno;
    }

    public ArrayList Motivos() throws Exception {
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

    public ArrayList FxHoraria() throws Exception {
        ArrayList<String> lstMotivos = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            String ii = String.valueOf(i);
            String ii2 = String.valueOf(i + 1);
            if (i < 10) {
                ii = "0" + ii;
                if ((i + 1) < 10) {
                    ii2 = "0" + ii2;
                }
            }
            lstMotivos.add(ii + "-" + ii2);
        }
        return lstMotivos;

    }

    public ArrayList Escolaridade() throws Exception {
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
        lstMotivos.add("99-Outros");
        return lstMotivos;

    }

    public ArrayList Idades() throws Exception {
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

    public ArrayList RendaIndividual() throws Exception {
        ArrayList<String> lstMotivos = new ArrayList<>();
        lstMotivos.add("0 - 1/2");
        lstMotivos.add("1/2 - 1");
        lstMotivos.add("1 - 2");
        lstMotivos.add("2 - 3");
        lstMotivos.add("3 - 5");
        lstMotivos.add("5 - 10");
        lstMotivos.add("10 - 15");
        lstMotivos.add("15 - 20");
        lstMotivos.add("20 - 99");
        lstMotivos.add("Sem Rend.");
        lstMotivos.add("< 10 anos");
        return lstMotivos;

    }

    public HashMap MontaMotivosModos(String ismaior65) throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            //String sqlquery = "SELECT v.motivo,v.modo1,v.modo2,v.modo3,v.modo4,p.idade FROM viagem v,pessoas p WHERE v.key = p.key and v.numerodapessoa = p.numerodapessoa";
            String sqlquery = "SELECT v.motivo,v.modo1,v.modo2,v.modo3,p.idade FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";
            if (ismaior65.equals("1")) {
                sqlquery += " AND p.idade>64";
            }

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }

                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                } else {
                    modo1 = modo1.trim();
                    if (modo1.length() < 2) {
                        modo1 = "0" + modo1;
                    }
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                } else {
                    modo2 = modo2.trim();
                    if (modo2.length() < 2) {
                        modo2 = "0" + modo2;
                    }
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                } else {
                    modo3 = motivo.trim();
                    if (modo3.length() < 2) {
                        modo3 = "0" + modo3;
                    }
                }
                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "0";
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------
                    if (modo2.trim().length() > 0) {
                        conta = 0;
                        String ch2 = motivo.trim() + "-" + modo2.trim();
                        if (mapMotModo.containsKey(ch2)) {
                            conta = mapMotModo.get(ch2);
                        }
                        conta++;
                        mapMotModo.put(ch2, conta);
                    }
                    //----------
                    if (modo3.trim().length() > 0) {
                        conta = 0;
                        String ch3 = motivo.trim() + "-" + modo3.trim();
                        if (mapMotModo.containsKey(ch3)) {
                            conta = mapMotModo.get(ch3);
                        }
                        conta++;
                        mapMotModo.put(ch3, conta);
                    }

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaMotivosModosPR(String ismaior65) throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.motivo,v.modopr,p.idade FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";
            if (ismaior65.equals("1")) {
                sqlquery += " AND p.idade>64";
            }
            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                } else {
                    modo1 = modo1.trim();
                    if (modo1.length() < 2) {
                        modo1 = "0" + modo1;
                    }
                }
                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "0";
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------                    
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoPR-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaFxRendaMotivos() throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        lstFxRenda = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.motivo,d.faixaderenda FROM viagem74 v,domicilio d WHERE v.key = d.key";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                }
                String faixaderenda = TbRs1.getString("faixaderenda");
                if (faixaderenda == null) {
                    faixaderenda = "";
                } else {
                    faixaderenda = faixaderenda.trim();
                    if (!lstFxRenda.contains(faixaderenda)) {
                        lstFxRenda.add(faixaderenda);
                    }
                }
                //----------

                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (faixaderenda.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + faixaderenda.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaFxRendaModos() throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        lstFxRenda = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.motivo,v.modo1,v.modo2,v.modo3,d.faixaderenda FROM viagem74 v,domicilio d WHERE v.key = d.key";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                }
                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                }

                String faixaderenda = TbRs1.getString("faixaderenda");
                if (faixaderenda == null) {
                    faixaderenda = "";
                } else {
                    faixaderenda = faixaderenda.trim();
                    if (!lstFxRenda.contains(faixaderenda)) {
                        lstFxRenda.add(faixaderenda);
                    }
                }
                //----------
                if (faixaderenda.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = modo1.trim() + "-" + faixaderenda.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------
                    if (modo2.trim().length() > 0) {
                        conta = 0;
                        String ch2 = modo2.trim() + "-" + faixaderenda.trim();
                        if (mapMotModo.containsKey(ch2)) {
                            conta = mapMotModo.get(ch2);
                        }
                        conta++;
                        mapMotModo.put(ch2, conta);
                    }
                    //----------
                    if (modo3.trim().length() > 0) {
                        conta = 0;
                        String ch3 = modo3.trim() + "-" + faixaderenda.trim();
                        if (mapMotModo.containsKey(ch3)) {
                            conta = mapMotModo.get(ch3);
                        }
                        conta++;
                        mapMotModo.put(ch3, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaFxRendaModosPR(String ano) throws Exception {

        String campo = "v.fat" + ano;
        if (ano.length() == 0) {
            campo = "v.fat2019";
        }
        HashMap<String, Integer> mapMotModo = new HashMap<>();
        lstFxRenda = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.motivo,v.modopr,d.faixaderenda," + campo + " as fator FROM viagem74 v,domicilio d WHERE v.key = d.key order by d.faixaderenda";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                }
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                String faixaderenda = TbRs1.getString("faixaderenda");
                if (faixaderenda == null) {
                    faixaderenda = "";
                } else {
                    faixaderenda = faixaderenda.trim();
                    if (!lstFxRenda.contains(faixaderenda)) {
                        lstFxRenda.add(faixaderenda);
                    }
                }
                int fator = TbRs1.getInt("fator");
                if ((ano.length() == 0)) {
                    fator = 1;
                }
                //----------
                if (faixaderenda.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = modo1.trim() + "-" + faixaderenda.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                        //out.println(ch1+" - "+conta+"<br>");
                    }
                    //----------                    
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaEscolaridadeModos() throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.modo1,v.modo2,v.modo3,p.graudeinstrucao FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                }

                String motivo = TbRs1.getString("graudeinstrucao");
                if (motivo == null) {
                    motivo = "";
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------
                    if (modo2.trim().length() > 0) {
                        conta = 0;
                        String ch2 = motivo.trim() + "-" + modo2.trim();
                        if (mapMotModo.containsKey(ch2)) {
                            conta = mapMotModo.get(ch2);
                        }
                        conta++;
                        mapMotModo.put(ch2, conta);
                    }
                    //----------
                    if (modo3.trim().length() > 0) {
                        conta = 0;
                        String ch3 = motivo.trim() + "-" + modo3.trim();
                        if (mapMotModo.containsKey(ch3)) {
                            conta = mapMotModo.get(ch3);
                        }
                        conta++;
                        mapMotModo.put(ch3, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaEscolaridadeModosPR(String ano) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.modopr,p.graudeinstrucao," + campo + " as fator FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                String motivo = TbRs1.getString("graudeinstrucao");
                if (motivo == null) {
                    motivo = "";
                }
                int fator = TbRs1.getInt("fator");
                if ((ano.length() == 0)) {
                    fator = 1;
                }
                //----------
                if (motivo.trim().length() > 0) {
                    if (motivo.trim().length() < 2) {
                        motivo = "0" + motivo.trim();
                    }
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------                    
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaIdadeModos() throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.modo1,v.modo2,v.modo3,p.idade as idade FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                }

                int idade = TbRs1.getInt("idade");
                //------------
                String motivo = "";
                if (idade < 10) {
                    motivo = "1";
                }
                if (idade > 9 && idade < 20) {
                    motivo = "2";
                }
                if (idade > 19 && idade < 30) {
                    motivo = "3";
                }
                if (idade > 29 && idade < 40) {
                    motivo = "4";
                }
                if (idade > 39 && idade < 50) {
                    motivo = "5";
                }
                if (idade > 49 && idade < 60) {
                    motivo = "6";
                }
                if (idade > 59 && idade < 65) {
                    motivo = "7";
                }
                if (idade > 64) {
                    motivo = "8";
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta++;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------
                    if (modo2.trim().length() > 0) {
                        conta = 0;
                        String ch2 = motivo.trim() + "-" + modo2.trim();
                        if (mapMotModo.containsKey(ch2)) {
                            conta = mapMotModo.get(ch2);
                        }
                        conta++;
                        mapMotModo.put(ch2, conta);
                    }
                    //----------
                    if (modo3.trim().length() > 0) {
                        conta = 0;
                        String ch3 = motivo.trim() + "-" + modo3.trim();
                        if (mapMotModo.containsKey(ch3)) {
                            conta = mapMotModo.get(ch3);
                        }
                        conta++;
                        mapMotModo.put(ch3, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaIdadeModosPR(String ano) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.modopr,p.idade as idade," + campo + " as fator FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                int idade = TbRs1.getInt("idade");
                int fator = TbRs1.getInt("fator");
                if ((ano.length() == 0)) {
                    fator = 1;
                }
                //------------
                String motivo = "";
                if (idade < 10) {
                    motivo = "1";
                }
                if (idade > 9 && idade < 20) {
                    motivo = "2";
                }
                if (idade > 19 && idade < 30) {
                    motivo = "3";
                }
                if (idade > 29 && idade < 40) {
                    motivo = "4";
                }
                if (idade > 39 && idade < 50) {
                    motivo = "5";
                }
                if (idade > 49 && idade < 60) {
                    motivo = "6";
                }
                if (idade > 59 && idade < 65) {
                    motivo = "7";
                }
                if (idade > 64) {
                    motivo = "8";
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaSexoModosPR(String ano) throws Exception {

        String campo = "v.fat" + ano;
        if (ano.length() == 0) {
            campo = "v.fat2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.modopr,p.sexo as sexo," + campo + " as fator FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                int sexo = TbRs1.getInt("sexo");
                int fator = TbRs1.getInt("fator");
                if ((ano.length() == 0)) {
                    fator = 1;
                }
                //------------
                String motivo = String.valueOf(sexo);
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaTotalFaixa(String ano) throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select idade,count(0) as conta,sum(fator2019) as conta2019,sum(fator2036) as conta2036 from pessoas group by idade order by idade";

            //System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                int idade = TbRs1.getInt("idade");
                int conta = TbRs1.getInt("conta");
                int conta2019 = TbRs1.getInt("conta2019");
                int conta2036 = TbRs1.getInt("conta2036");
                if (ano.equals("2019")) {
                    conta = conta2019;
                }
                if (ano.equals("2036")) {
                    conta = conta2036;
                }
                //------------
                String ch = "1";
                if (idade > 9 && idade < 20) {
                    ch = "2";
                }
                if (idade > 19 && idade < 30) {
                    ch = "3";
                }
                if (idade > 29 && idade < 40) {
                    ch = "4";
                }
                if (idade > 39 && idade < 50) {
                    ch = "5";
                }
                if (idade > 49 && idade < 60) {
                    ch = "6";
                }
                if (idade > 59 && idade < 66) {
                    ch = "7";
                }
                if (idade > 65) {
                    ch = "8";
                }
                //----------
                int contador = 0;
                if (mapMotModo.containsKey(ch)) {
                    contador = mapMotModo.get(ch);
                }
                contador = contador + conta;
                mapMotModo.put(ch, contador);
                System.out.println(ch + " - " + String.valueOf(contador));
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaTotalFaixa-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaTotalSexo(String ano) throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select sexo,count(0) as conta,sum(fator2019) as conta2019,sum(fator2036) as conta2036 from pessoas group by sexo order by sexo";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                int sexo = TbRs1.getInt("sexo");
                int conta = TbRs1.getInt("conta");
                String ch = String.valueOf(sexo);

                //----------
                int conta2019 = TbRs1.getInt("conta2019");
                int conta2036 = TbRs1.getInt("conta2036");
                if (ano.equals("2019")) {
                    conta = conta2019;
                }
                if (ano.equals("2036")) {
                    conta = conta2036;
                }
                //----------
                int contador = 0;
                if (mapMotModo.containsKey(ch)) {
                    contador = mapMotModo.get(ch);
                }
                contador = contador + conta;
                mapMotModo.put(ch, contador);
                //out.println(ch+" - "+String.valueOf(contador));
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaTotalFaixa-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaTotalEscolaridade(String ano) throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select graudeinstrucao,count(0) as conta,sum(fator2019) as conta2019,sum(fator2036) as conta2036 from pessoas group by graudeinstrucao order by graudeinstrucao";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String ch = TbRs1.getString("graudeinstrucao");
                if (ch == null) {
                    ch = "";
                } else {
                    if (ch.length() < 2) {
                        ch = "0" + ch;
                    }
                }
                int conta = TbRs1.getInt("conta");
                int conta2019 = TbRs1.getInt("conta2019");
                int conta2036 = TbRs1.getInt("conta2036");
                if (ano.equals("2019")) {
                    conta = conta2019;
                }
                if (ano.equals("2036")) {
                    conta = conta2036;
                }
                //----------      
                if (ch.length() > 0) {
                    mapMotModo.put(ch, conta);
                }
                //out.println(ch+" - "+String.valueOf(contador));
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaTotalEscolaridade-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaTotalRenda() throws Exception {

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select faixaderenda,count(faixaderenda) as conta from domicilio group by faixaderenda order by faixaderenda";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String ch = TbRs1.getString("faixaderenda");
                if (ch == null) {
                    ch = "";
                } else {
                    ch = ch.trim();
                }
                int conta = TbRs1.getInt("conta");
                //----------                
                mapMotModo.put(ch, conta);
                //out.println(ch+" - "+String.valueOf(contador));
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaTotalEscolaridade-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap<String, Integer> MontaOrigemDestinoOld(String modo, String motivo, String modopr) throws Exception {

        HashMap<String, Integer> mapOrigemDestino = new HashMap<>();
        lstZonas = new ArrayList<>();
        //----------
        String modopr1 = "('1','2','10','11','12','13','14','15','16','17')";
        String modopr2 = "('3','4','5','6','7','8','9')";
        //----------
        HashMap<String, ArrayList<String>> mapOrigem = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = " select v.key,v.numerodapessoa,v.numeroviagem,v.zona,d.zona as zonad from viagem v, domicilio d WHERE v.key=d.key ";
            String meio = " AND ";
            if (modopr.length() == 0) {
                if (modo.length() > 0) {
                    sqlquery += meio + " (trim(modo1)='" + modo + "' OR trim(modo2)='" + modo + "' OR trim(modo3)='" + modo + "') ";
                    //meio = "AND";
                }
            } else {
                if (modopr.equals("A")) {
                    sqlquery += meio + " (trim(modo1) in " + modopr1 + " OR trim(modo2) in " + modopr1 + " OR trim(modo3) in " + modopr1 + ") ";
                    //meio = "AND";
                }
                if (modopr.equals("B")) {
                    sqlquery += meio + " (trim(modo1) in " + modopr2 + " OR trim(modo2) in " + modopr2 + " OR trim(modo3) in " + modopr2 + ") ";
                    //meio = "AND";
                }
            }
            //-------------------
            if (motivo.length() > 0) {
                sqlquery += meio + " trim(motivo)='" + motivo + "'";
                meio = "";
            }
            sqlquery += " order by v.key,v.numerodapessoa,v.numeroviagem;";

            out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String numerodapessoa = TbRs1.getString("numerodapessoa");
                if (numerodapessoa == null) {
                    numerodapessoa = "";
                }
                String zona = TbRs1.getString("zona");
                if (zona == null) {
                    zona = "";
                }
                String zonad = TbRs1.getString("zonad");
                if (zonad == null) {
                    zonad = "";
                }
                //----------
                try {
                    //domicilio
                    int zd = Integer.parseInt(zonad.trim());
                    if (zd < 100) {
                        String zz2 = String.valueOf(zd);
                        if (zd < 10) {
                            zz2 = "0" + zz2;
                        }
                        if (!lstZonas.contains(zz2)) {
                            lstZonas.add(zz2);
                        }
                    }
                    //--------
                    //viagem
                    int zz = Integer.parseInt(zona.trim());
                    if (zz < 100) {
                        String zz2 = String.valueOf(zz);
                        if (zz < 10) {
                            zz2 = "0" + zz2;
                        }
                        if (!lstZonas.contains(zz2)) {
                            lstZonas.add(zz2);
                        }
                    } else {
                        zona = "";
                    }
                } catch (Exception e) {
                    zona = "";
                }

                //----------
                if (key.trim().length() > 0 && numerodapessoa.trim().length() > 0 && zona.trim().length() > 0) {
                    ArrayList<String> lstTBC = new ArrayList<>();
                    String ch1 = key.trim() + "-" + numerodapessoa.trim();
                    if (mapOrigem.containsKey(ch1)) {
                        lstTBC = mapOrigem.get(ch1);
                    }
                    if (lstTBC.size() == 0) {
                        lstTBC.add(zonad.trim());
                    }
                    lstTBC.add(zona.trim());
                    mapOrigem.put(ch1, lstTBC);
                    //----------                   
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();
            //===========
            //Acumulando os pares
            if (!mapOrigem.isEmpty()) {
                Iterator iterator = new TreeSet(mapOrigem.keySet()).iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String corda = AjustaTipoTBC(mapOrigem.get(key));
                    out.println(key + ";" + corda + "<br>");
                    if (corda.contains(";")) {
                        String cc[] = corda.split(";");
                        for (int j = 0; j < cc.length; j++) {
                            int conta = 0;
                            if (mapOrigemDestino.containsKey(cc[j])) {
                                conta = mapOrigemDestino.get(cc[j]);
                            }
                            conta++;
                            mapOrigemDestino.put(cc[j], conta);
                        }
                    }
                }
            }

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaOrigemDestino-" + e.getMessage());
        }

        return mapOrigemDestino;

    }

    public HashMap<String, Integer> MontaOrigemDestino(String ano, String motivo, String modo) throws Exception {

        HashMap<String, Integer> mapOrigemDestino = new HashMap<>();
        lstZonas = new ArrayList<>();
        //----------

        //----------
        HashMap<String, ArrayList<String>> mapOrigem = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            //String sqlquery = "select zonaorigem,zonadestino,count(zonadestino) as conta from viagem74";
            String sqlquery = "SELECT zonaorigem,zonadestino,count(zonadestino) as conta ";
            //-----------
            if (ano.equals("2019")) {
                sqlquery = "select zonaorigem,zonadestino,sum(fat2019) as conta ";
            }
            if (ano.equals("2036")) {
                sqlquery = "select zonaorigem,zonadestino,sum(fat2036) as conta ";
            }
            //-----------      
            sqlquery += " FROM viagem74  WHERE motivo=" + motivo;
            //if (modo.length() == 0) {
            //sqlquery += " and (modo1=3 or modo1=10 or modo1=11 or modo2=3 or modo2=10 or modo2=11) ";
            if (modo.equalsIgnoreCase("O")) {
                sqlquery += " and (modo1=3 or modo2=3 or modo3=3) ";
            } else if (modo.equalsIgnoreCase("C")) {
                sqlquery += " and (modo1=10 or modo1=11 or modo2=10 or modo2=11 or modo3=10 or modo3=11) ";
            } else if (modo.length() > 0) {
                sqlquery += " and  (modo1=" + modo + " OR modo2=" + modo + " OR modo3=" + modo + ") ";
                //meio = "AND";

            }
            sqlquery += " group by zonaorigem,zonadestino ";
            sqlquery += " order by zonaorigem,zonadestino;";

            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zonaorigem");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                String zonadestino = TbRs1.getString("zonadestino");
                if (zonadestino == null) {
                    zonadestino = "";
                }
                int conta = TbRs1.getInt("conta");
                //----------
                try {
                    //domicilio
                    int zd = Integer.parseInt(zonaorigem);
                    if (zd < 100) {
                        String zz2 = String.valueOf(zd);
                        if (zd < 10) {
                            zz2 = "0" + zz2;
                        }
                        if (!lstZonas.contains(zz2)) {
                            lstZonas.add(zz2);
                        }
                    }
                    //--------
                    //viagem
                    int zz = Integer.parseInt(zonadestino);
                    if (zz < 100) {
                        String zz2 = String.valueOf(zz);
                        if (zz < 10) {
                            zz2 = "0" + zz2;
                        }
                        if (!lstZonas.contains(zz2)) {
                            lstZonas.add(zz2);
                        }
                    }
                } catch (Exception e) {

                }
                //----------------
                String ch1 = zonaorigem + "-" + zonadestino;
                if (mapOrigemDestino.containsKey(ch1)) {
                    int soma = mapOrigemDestino.get(ch1);
                    conta += soma;
                }
                mapOrigemDestino.put(ch1, conta);
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaOrigemDestino-" + e.getMessage());
        }

        return mapOrigemDestino;

    }

    private String AjustaTipoTBC(ArrayList<String> lista) throws IOException {
        String retorno = "";
        try {
            for (int i = 0; i < lista.size() - 1; i++) {
                String x = lista.get(i);
                String x2 = lista.get(0);
                if (lista.size() > (i + 1)) {
                    x2 = lista.get(i + 1);
                }
                retorno += x + "-" + x2 + ";";
            }

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-AjustaTipoTBC-" + e.getMessage());
        }
        return retorno;

    }

    public HashMap<String, Integer> MontaSintesePorZona() throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select zonaorigem,count(key) as conta from viagem74 group by zonaorigem order by zonaorigem";
            //-----------

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zonaorigem");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                int conta = TbRs1.getInt("conta");
                //----------                               
                mapSintese.put(zonaorigem, conta);
                //----------               
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Integer> MontaSintesePorZonaResidentes() throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            //String sqlquery = "select zonaorigem,count(key) as conta from ( select zonaorigem,key from viagem74 group by zonaorigem,key order by key,zonaorigem ) as foo group by zonaorigem order by zonaorigem;";
            String sqlquery = " select zonacasa,count(zonacasa) as conta from pessoas group by zonacasa order by zonacasa;";
            //-----------

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zonacasa");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                int conta = TbRs1.getInt("conta");
                //----------                               
                mapSintese.put(zonaorigem, conta);
                //----------
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Integer> MontaSintesePorZonaDomicilio() throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            String sqlquery = " select zona,count(zona) as conta from domicilio group by zona order by zona;";
            //-----------

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zona");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                int conta = TbRs1.getInt("conta");
                //----------                               
                mapSintese.put(zonaorigem, conta);
                //----------
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Integer> MontaSintesePorZonaModosPR() throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            String sqlquery = "select zonaorigem,modopr,count(modopr) as conta from viagem74 group by zonaorigem,modopr order by zonaorigem,modopr;";
            //-----------

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zonaorigem");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                String modopr = TbRs1.getString("modopr");
                if (modopr == null) {
                    modopr = "";
                }
                int conta = TbRs1.getInt("conta");
                //----------                    
                if (modopr.length() > 0) {
                    String ch = zonaorigem + "-" + modopr;
                    mapSintese.put(ch, conta);
                }
                //----------
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Integer> MontaMenores10Anos() throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            String sqlquery = " select zonacasa as zona,count(key) as conta from pessoas where idade<=10 group by zonacasa order by zonacasa;";
            //-----------

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zona");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                int conta = TbRs1.getInt("conta");
                //----------                               
                mapSintese.put(zonaorigem, conta);
                //----------
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Integer> MontaSalarioMinimo(double sal1, double sal2) throws Exception {

        HashMap<String, Integer> mapSintese = new HashMap<>();

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            String sqlquery = " select zonacasa as zona,count(key) as conta from pessoas where rendamensal>" + String.valueOf(sal1) + " and rendamensal<=" + String.valueOf(sal2) + " group by zonacasa order by zonacasa;";
            if (sal1 == 0 && sal2 == 0) {
                sqlquery = " select zonacasa as zona,count(key) as conta from pessoas where rendamensal=0 group by zonacasa order by zonacasa;";
            }
            //-----------

            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String zonaorigem = TbRs1.getString("zona");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                if (zonaorigem.length() < 2 && zonaorigem.length() > 0) {
                    zonaorigem = "0" + zonaorigem;
                }
                int conta = TbRs1.getInt("conta");
                //----------                               
                mapSintese.put(zonaorigem, conta);
                //----------
                if (zonaorigem.length() > 1 && !lstZonas.contains(zonaorigem)) {
                    lstZonas.add(zonaorigem);
                }
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

        return mapSintese;

    }

    public HashMap<String, Double[]> MatrizPico2010(double coef) throws Exception {
        HashMap<String, Double[]> mapPop = new HashMap<>();
        mapPop.put("01", new Double[]{3800 * coef, 1443 * coef, 2.63 * coef, 4448 * coef, 36 * coef, 569 * coef, 1052 * coef, 456 * coef, 447 * coef, 365 * coef, 68 * coef, 71 * coef, 53 * coef, 1001 * coef, 4118 * coef, 330 * coef});
        mapPop.put("02", new Double[]{4793 * coef, 1683 * coef, 2.85 * coef, 4793 * coef, 71 * coef, 802 * coef, 1404 * coef, 499 * coef, 315 * coef, 174 * coef, 21 * coef, 10 * coef, 15 * coef, 1029 * coef, 4340 * coef, 453 * coef});
        mapPop.put("03", new Double[]{7561 * coef, 2524 * coef, 3.00 * coef, 7529 * coef, 53 * coef, 1100 * coef, 2063 * coef, 737 * coef, 486 * coef, 286 * coef, 39 * coef, 40 * coef, 40 * coef, 1966 * coef, 6810 * coef, 719 * coef});
        mapPop.put("04", new Double[]{5577 * coef, 1963 * coef, 2.84 * coef, 5577 * coef, 36 * coef, 655 * coef, 1431 * coef, 669 * coef, 504 * coef, 395 * coef, 52 * coef, 103 * coef, 65 * coef, 1224 * coef, 5134 * coef, 443 * coef});
        mapPop.put("05", new Double[]{7221 * coef, 2487 * coef, 2.90 * coef, 7221 * coef, 67 * coef, 646 * coef, 1780 * coef, 844 * coef, 742 * coef, 562 * coef, 94 * coef, 85 * coef, 50 * coef, 1597 * coef, 6467 * coef, 754 * coef});
        mapPop.put("06", new Double[]{13113 * coef, 3809 * coef, 3.44 * coef, 13046 * coef, 152 * coef, 1407 * coef, 4308 * coef, 1256 * coef, 672 * coef, 282 * coef, 24 * coef, 15 * coef, 9 * coef, 3282 * coef, 11407 * coef, 1639 * coef});
        mapPop.put("07", new Double[]{4443 * coef, 1196 * coef, 3.71 * coef, 4414 * coef, 57 * coef, 515 * coef, 1227 * coef, 305 * coef, 112 * coef, 62 * coef, 9 * coef, 9 * coef, 12 * coef, 1322 * coef, 3630 * coef, 784 * coef});
        mapPop.put("08", new Double[]{3851 * coef, 1287 * coef, 2.99 * coef, 3850 * coef, 26 * coef, 437 * coef, 821 * coef, 438 * coef, 400 * coef, 356 * coef, 50 * coef, 34 * coef, 16 * coef, 917 * coef, 3495 * coef, 355 * coef});
        mapPop.put("09", new Double[]{7906 * coef, 2631 * coef, 3.00 * coef, 7906 * coef, 72 * coef, 1305 * coef, 2437 * coef, 869 * coef, 500 * coef, 237 * coef, 33 * coef, 13 * coef, 4 * coef, 1764 * coef, 7234 * coef, 672 * coef});
        mapPop.put("10", new Double[]{8493 * coef, 2689 * coef, 3.16 * coef, 8257 * coef, 97 * coef, 998 * coef, 2462 * coef, 998 * coef, 599 * coef, 211 * coef, 20 * coef, 11 * coef, 4 * coef, 1875 * coef, 7275 * coef, 982 * coef});
        mapPop.put("11", new Double[]{7315 * coef, 2369 * coef, 3.09 * coef, 7314 * coef, 40 * coef, 766 * coef, 1871 * coef, 886 * coef, 710 * coef, 500 * coef, 77 * coef, 49 * coef, 25 * coef, 1680 * coef, 6604 * coef, 710 * coef});
        mapPop.put("12", new Double[]{9143 * coef, 2949 * coef, 3.10 * coef, 9098 * coef, 85 * coef, 746 * coef, 2234 * coef, 1059 * coef, 850 * coef, 481 * coef, 58 * coef, 49 * coef, 34 * coef, 2205 * coef, 7801 * coef, 1297 * coef});
        mapPop.put("13", new Double[]{11756 * coef, 3280 * coef, 3.58 * coef, 11726 * coef, 214 * coef, 1332 * coef, 3675 * coef, 1033 * coef, 392 * coef, 105 * coef, 4 * coef, 6 * coef, 5 * coef, 3182 * coef, 9948 * coef, 1778 * coef});
        mapPop.put("14", new Double[]{6542 * coef, 1945 * coef, 3.36 * coef, 6274 * coef, 84 * coef, 686 * coef, 1851 * coef, 706 * coef, 430 * coef, 145 * coef, 18 * coef, 11 * coef, 14 * coef, 1510 * coef, 5455 * coef, 819 * coef});
        mapPop.put("15", new Double[]{3581 * coef, 1027 * coef, 3.49 * coef, 3486 * coef, 53 * coef, 457 * coef, 1180 * coef, 297 * coef, 131 * coef, 72 * coef, 13 * coef, 7 * coef, 6 * coef, 852 * coef, 3068 * coef, 418 * coef});
        mapPop.put("16", new Double[]{7153 * coef, 2203 * coef, 3.25 * coef, 7121 * coef, 70 * coef, 574 * coef, 2179 * coef, 872 * coef, 482 * coef, 154 * coef, 5 * coef, 5 * coef, 3 * coef, 1728 * coef, 6072 * coef, 1049 * coef});
        mapPop.put("17", new Double[]{8086 * coef, 2384 * coef, 3.39 * coef, 7996 * coef, 169 * coef, 1044 * coef, 2328 * coef, 716 * coef, 290 * coef, 70 * coef, 2 * coef, 2 * coef, 2 * coef, 2074 * coef, 6697 * coef, 1299 * coef});
        mapPop.put("18", new Double[]{9825 * coef, 2814 * coef, 3.49 * coef, 9706 * coef, 118 * coef, 914 * coef, 2870 * coef, 720 * coef, 273 * coef, 70 * coef, 6 * coef, 2 * coef, 5 * coef, 3095 * coef, 8073 * coef, 1633 * coef});
        mapPop.put("19", new Double[]{518 * coef, 147 * coef, 3.52 * coef, 518 * coef, 22 * coef, 112 * coef, 129 * coef, 34 * coef, 11 * coef, 4 * coef, 1 * coef, 0 * coef, 0 * coef, 136 * coef, 449 * coef, 69 * coef});
        mapPop.put("20", new Double[]{363 * coef, 115 * coef, 3.16 * coef, 363 * coef, 3 * coef, 72 * coef, 87 * coef, 34 * coef, 22 * coef, 6 * coef, 1 * coef, 1 * coef, 2 * coef, 83 * coef, 311 * coef, 52 * coef});

        return mapPop;
    }

    public HashMap MontaMotivosModosPorZonas(String zona, String ano, String tirarfator) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.key,v.codigo,v.motivo,v.modo1,v.modo2,v.modo3,p.idade,v.zonaorigem,v.zonadestino," + campo + " as fator,p.rendamensal,v.viagem,v.hora,p.nomedapessoa ";
            sqlquery += " FROM viagem74 v,pessoas p,domicilio d ";
            sqlquery += " WHERE v.key = p.key and v.key=d.key ";
            sqlquery += " and v.codigo = p.numerodapessoa ";
            if (zona.length() > 0) {
                sqlquery += " AND p.zonacasa = " + zona;
            }
            sqlquery += " Order by key, codigo, viagem, hora";

            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }

                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                } else {
                    modo1 = modo1.trim();
                    if (modo1.length() < 2) {
                        modo1 = "0" + modo1;
                    }
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                } else {
                    modo2 = modo2.trim();
                    if (modo2.length() < 2) {
                        modo2 = "0" + modo2;
                    }
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                } else {
                    modo3 = modo3.trim();
                    if (modo3.length() < 2) {
                        modo3 = "0" + modo3;
                    }
                }
                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "0";
                }
                int fator = TbRs1.getInt("fator");
                if (tirarfator.equalsIgnoreCase("s")) {
                    fator = 1;
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                        //System.out.println(ch1);
                    }
                    //----------

                    if (modo2.trim().length() > 220) {
                        conta = 0;
                        String ch2 = motivo.trim() + "-" + modo2.trim();
                        if (mapMotModo.containsKey(ch2)) {
                            conta = mapMotModo.get(ch2);
                        }
                        conta += fator;
                        mapMotModo.put(ch2, conta);
                        System.out.println(ch2);
                    }
                    //----------
                    if (modo3.trim().length() > 220) {
                        conta = 0;
                        String ch3 = motivo.trim() + "-" + modo3.trim();
                        if (mapMotModo.containsKey(ch3)) {
                            conta = mapMotModo.get(ch3);
                        }
                        conta += fator;
                        mapMotModo.put(ch3, conta);
                        System.out.println(ch3);
                    }

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();
            System.out.println(sqlquery);

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaMotivosModosPRPorZonas(String zona, String ano, String tirarfator) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "SELECT v.motivo,v.modopr,p.idade," + campo + " as fator FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";
            if (zona.length() > 0) {
                sqlquery += " AND p.zonacasa = " + zona;
            }

            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "0";
                }
                int fator = TbRs1.getInt("fator");
                if (tirarfator.equalsIgnoreCase("s")) {
                    fator = 1;
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = motivo.trim() + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                    }
                    //----------                    
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoPR-" + e.getMessage());
        }

        return mapMotModo;

    }

    private String ObterChaveRendaIndividual(double valor, int idade) {

        double salariominimo = 1045.00;
        String retorno = "";

        if (valor > 0 && valor <= 0.5 * salariominimo) {
            retorno = "0 - 1/2";
        }
        if (valor > 0.5 * salariominimo && valor <= salariominimo) {
            retorno = "1/2 - 1";
        }
        if (valor > salariominimo && valor <= 2 * salariominimo) {
            retorno = "1 - 2";
        }
        if (valor > 2 * salariominimo && valor <= 3 * salariominimo) {
            retorno = "2 - 3";
        }
        if (valor > 3 * salariominimo && valor <= 5 * salariominimo) {
            retorno = "3 - 5";
        }
        if (valor > 5 * salariominimo && valor <= 10 * salariominimo) {
            retorno = "5 - 10";
        }
        if (valor > 10 * salariominimo && valor <= 15 * salariominimo) {
            retorno = "10 - 15";
        }
        if (valor > 20 * salariominimo && valor <= 99 * salariominimo) {
            retorno = "20 - 99";
        }
        if (valor > 20 * salariominimo && valor <= 99 * salariominimo) {
            retorno = "20 - 99";
        }
        if (valor == 0) {
            retorno = "Sem Rend.";
        }
        if (idade < 10) {
            retorno = "< 10 anos";
        }
        return retorno;

    }

    public HashMap MontaRendaIndividualModosPRPorZonas(String zona, String ano, String tirarfator) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            String sqlquery = "SELECT v.motivo,v.modopr,p.rendamensal,p.idade," + campo + " as fator FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa ";

            if (zona.length() > 0) {
                sqlquery += " AND p.zonacasa = " + zona;
            }

            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                double rendamensal = TbRs1.getDouble("rendamensal");
                int idade = TbRs1.getInt("idade");

                int fator = TbRs1.getInt("fator");
                if (tirarfator.equalsIgnoreCase("s")) {
                    fator = 1;
                }
                //----------
                if (motivo.trim().length() > 0) {
                    int conta = 0;
                    if (modo1.trim().length() > 0) {
                        conta = 0;
                        String ch1 = ObterChaveRendaIndividual(rendamensal, idade) + "-" + modo1.trim();
                        if (mapMotModo.containsKey(ch1)) {
                            conta = mapMotModo.get(ch1);
                        }
                        conta += fator;
                        mapMotModo.put(ch1, conta);
                        System.out.println(ch1 + " -> " + String.valueOf(conta));
                    }
                    //----------                    
                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaRendaIndividualModosPRPorZonas-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap MontaFxHorariaModosPRPorZonas(String zona, String ano, String tirarfator) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        HashMap<String, Integer> mapMotModo = new HashMap<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select horanum,count(horanum) as conta,sum(fat2019) as fator2019,sum(fat2036) as fator2036,modopr from viagem74 ";
            if (zona.length() > 0) {
                sqlquery += " WHERE zonacasa = " + zona;
            }
            sqlquery += " group by horanum,modopr order by horanum,modopr";
            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                int horanum = TbRs1.getInt("horanum");
                int conta = TbRs1.getInt("conta");
                int fator2019 = TbRs1.getInt("fator2019");
                int fator2036 = TbRs1.getInt("fator2036");
                String modo1 = TbRs1.getString("modopr");
                if (modo1 == null) {
                    modo1 = "";
                }
                if (ano.equals("2019")) {
                    conta = fator2019;
                }
                if (ano.equals("2036")) {
                    conta = fator2036;
                }
                //----------                
                String motivo = String.valueOf(horanum);
                if (horanum < 10) {
                    motivo = "0" + motivo;
                }
                if (modo1.trim().length() > 0) {
                    String ch1 = motivo.trim() + "-" + modo1.trim();
                    int soma = 0;
                    if (mapMotModo.containsKey(ch1)) {
                        soma = mapMotModo.get(ch1);
                    }
                    conta += soma;
                    mapMotModo.put(ch1, conta);
                    //System.out.println(ch1+" -> "+String.valueOf(conta));
                }

                //----------
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoPR-" + e.getMessage());
        }

        return mapMotModo;

    }

    public ArrayList<String> ListaMotivosModos(String zona, String ano) throws Exception {

        String campo = "fator" + ano;
        if (ano.length() == 0) {
            campo = "fator2019";
        }

        ArrayList<String> mapMotModo = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            //String sqlquery = "SELECT v.motivo,v.modo1,v.modo2,v.modo3,v.modo4,p.idade FROM viagem v,pessoas p WHERE v.key = p.key and v.numerodapessoa = p.numerodapessoa";
            //String sqlquery = "SELECT distinct v.key,v.codigo,v.motivo,v.modo1,v.modo2,v.modo3,p.idade,v.zonaorigem,v.zonadestino," + campo + " as fator,p.rendamensal,v.viagem,v.hora,p.nomedapessoa FROM viagem74 v,pessoas p WHERE v.key = p.key and v.codigo = p.numerodapessoa";
            //if (zona.length() > 0) {
            //    sqlquery += " AND p.zonacasa = " + zona;
            //}
            //sqlquery += " Order by key, codigo, viagem, hora";
            String sqlquery = "SELECT v.key,v.codigo,v.motivo,v.modo1,v.modo2,v.modo3,p.idade,v.zonaorigem,v.zonadestino," + campo + " as fator,p.rendamensal,v.viagem,v.hora,p.nomedapessoa ";
            sqlquery += " FROM viagem74 v,pessoas p,domicilio d ";
            sqlquery += " WHERE v.key = p.key and v.key=d.key ";
            sqlquery += " and v.codigo = p.numerodapessoa ";
            if (zona.length() > 0) {
                sqlquery += " AND p.zonacasa = " + zona;
            }
            sqlquery += " Order by key, codigo, viagem, hora";

            System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String codigo = TbRs1.getString("codigo");
                if (codigo == null) {
                    codigo = "";
                }

                String motivo = TbRs1.getString("motivo");
                if (motivo == null) {
                    motivo = "";
                } else {
                    motivo = motivo.trim();
                    if (motivo.length() < 2) {
                        motivo = "0" + motivo;
                    }
                }

                String modo1 = TbRs1.getString("modo1");
                if (modo1 == null) {
                    modo1 = "";
                } else {
                    modo1 = modo1.trim();
                    if (modo1.length() < 2) {
                        modo1 = "0" + modo1;
                    }
                }
                String modo2 = TbRs1.getString("modo2");
                if (modo2 == null) {
                    modo2 = "";
                } else {
                    modo2 = modo2.trim();
                    if (modo2.length() < 2) {
                        modo2 = "0" + modo2;
                    }
                }
                String modo3 = TbRs1.getString("modo3");
                if (modo3 == null) {
                    modo3 = "";
                } else {
                    modo3 = modo3.trim();
                    if (modo3.length() < 2) {
                        modo3 = "0" + modo3;
                    }
                }
                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "0";
                }
                String zonaorigem = TbRs1.getString("zonaorigem");
                if (zonaorigem == null) {
                    zonaorigem = "";
                }
                String zonadestino = TbRs1.getString("zonadestino");
                if (zonadestino == null) {
                    zonadestino = "";
                }
                int fator = TbRs1.getInt("fator");
                int rendamensal = TbRs1.getInt("rendamensal");
                int viagem = TbRs1.getInt("viagem");
                String hora = TbRs1.getString("hora");
                if (hora == null) {
                    hora = "";
                }
                String nomedapessoa = TbRs1.getString("nomedapessoa");
                if (nomedapessoa == null) {
                    nomedapessoa = "";
                }
                //----------

                if (motivo.trim().length() > 0) {
                    String corda = key + ";" + codigo + ";" + motivo + ";" + modo1 + ";" + modo2 + ";" + modo3 + ";" + zonaorigem + ";" + zonadestino + ";" + String.valueOf(fator) + ";" + String.valueOf(rendamensal) + ";" + String.valueOf(viagem) + ";" + hora + ";" + nomedapessoa;
                    //System.out.println(corda);
                    mapMotModo.add(corda);

                }
                //----------

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public ArrayList<String> ListaCentroides() throws Exception {

        ArrayList<String> mapMotModo = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select * from centroides order by zona, id";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String id = TbRs1.getString("id");
                if (id == null) {
                    id = "";
                }
                String zona = TbRs1.getString("zona");
                if (zona == null) {
                    zona = "";
                }

                String local = TbRs1.getString("endereco");
                if (local == null) {
                    local = "";
                } else {
                    local = local.trim();
                }

                String xutm = TbRs1.getString("xutm");
                if (xutm == null) {
                    xutm = "";
                }
                String yutm = TbRs1.getString("yutm");
                if (yutm == null) {
                    yutm = "";
                }

                //----------
                String corda = id + ";" + zona + ";" + local + ";" + xutm + ";" + yutm;
                //System.out.println(corda);
                mapMotModo.add(corda);

                //----------
            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap<String, String[]> ObterDomiciliosCentroides(String zona) throws Exception {

        HashMap<String, String[]> mapCentro = new HashMap<>();
        HashMap<String, String[]> mapDomici = new HashMap<>();
        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {
            try {
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm = cnn2.createStatement();
                //-----------------
                //Obtendo Domicilios da Zona
                String sqlquery = "select key,latitude,longitude from domicilio where zona=" + zona + " Order by key";
                System.out.println(sqlquery);
                ResultSet TbRs1 = stm.executeQuery(sqlquery);
                while (TbRs1.next()) {
                    String id = TbRs1.getString("key");
                    if (id == null) {
                        id = "";
                    }
                    String xutm = TbRs1.getString("latitude");
                    if (xutm == null) {
                        xutm = "";
                    } else {
                        xutm = xutm.replaceAll("[^\\d]", "");
                    }
                    String yutm = TbRs1.getString("longitude");
                    if (yutm == null) {
                        yutm = "";
                    } else {
                        yutm = yutm.replaceAll("[^\\d]", "");
                    }
                    //----------     
                    try {
                        xutm = xutm.substring(0, 6) + "." + xutm.substring(6, xutm.length());
                        yutm = yutm.substring(0, 7) + "." + yutm.substring(7, yutm.length());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    //----------     
                    //System.out.println(corda);
                    mapDomici.put(id, new String[]{xutm, yutm});
                    System.out.println("Domicilio: " + id + " - " + xutm + " / " + yutm);
                    //----------
                }
                TbRs1.close();
                //-----------------
                stm.close();
                cnn2.close();
                //===============================
                //===============================

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }
        return mapDomici;
    }

    public HashMap<String, String[]> ObterCentroides(String zona) throws Exception {

        HashMap<String, String[]> mapDomici = new HashMap<>();
        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {
            try {
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm = cnn2.createStatement();
                //-----------------
                //Obtendo Cetroides da Zona
                String sqlquery = "select id, xutm, yutm from centroides where zona=" + zona + " Order by id";
                //out.println(sqlquery);
                ResultSet TbRs1 = stm.executeQuery(sqlquery);
                while (TbRs1.next()) {
                    String id = TbRs1.getString("id");
                    if (id == null) {
                        id = "";
                    }
                    String xutm = TbRs1.getString("xutm");
                    if (xutm == null) {
                        xutm = "";
                    } else {
                        xutm = xutm.replaceAll("[^\\d]", "");
                    }
                    String yutm = TbRs1.getString("yutm");
                    if (yutm == null) {
                        yutm = "";
                    } else {
                        yutm = yutm.replaceAll("[^\\d]", "");
                    }
                    //----------                
                    try {
                        xutm = xutm.substring(0, 6) + "." + xutm.substring(6, xutm.length());
                        yutm = yutm.substring(0, 7) + "." + yutm.substring(7, yutm.length());
                    } catch (Exception e) {
                    }
                    //----------   
                    //System.out.println(corda);
                    mapDomici.put(id, new String[]{xutm, yutm});
                    //System.out.println("Centroide: " + id + " - " + xutm + " / " + yutm);
                    //----------
                }
                TbRs1.close();
                //-----------------
                stm.close();
                cnn2.close();
                //===============================
                //===============================

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }

        return mapDomici;
    }

    public double AcharDistancia(String xutm1, String yutm1, String xutm2, String yutm2) {

        double retorno = 0.0;

        try {

            double vxutm1 = 0, vyutm1 = 0, vxutm2 = 0, vyutm2 = 0;
            vxutm1 = Double.parseDouble(xutm1);
            vyutm1 = Double.parseDouble(yutm1);
            vxutm2 = Double.parseDouble(xutm2);
            vyutm2 = Double.parseDouble(yutm2);
            //---------------
            double xx = Math.pow((vxutm1 - vxutm2), 2);
            double yy = Math.pow((vyutm1 - vyutm2), 2);
            double result = Math.sqrt(xx + yy);
            retorno = (result);

        } catch (Exception e) {
            retorno = -1;
        }

        return retorno;
    }

    public double AcharDistanciaMatriz(String centroide) {

        double retorno = 0.0;
        String xutm1 = "", yutm1 = "";
        try {

            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            //-----------------
            //Obtendo Cetroides da Zona
            String sqlquery = "select xutm, yutm from centroides where id=" + centroide;
            //System.out.println(sqlquery);
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String xutm = TbRs1.getString("xutm");
                if (xutm == null) {
                    xutm = "";
                } else {
                    xutm = xutm.replaceAll("[^\\d]", "");
                }
                String yutm = TbRs1.getString("yutm");
                if (yutm == null) {
                    yutm = "";
                } else {
                    yutm = yutm.replaceAll("[^\\d]", "");
                }
                //----------                
                try {
                    xutm1 = xutm.substring(0, 6) + "." + xutm.substring(6, xutm.length());
                    yutm1 = yutm.substring(0, 7) + "." + yutm.substring(7, yutm.length());
                } catch (Exception e) {
                }
                //----------
            }
            TbRs1.close();
            //-----------------
            stm.close();
            cnn2.close();
            //-----------------
            //Matriz
            double vxutm2 = 751615.65;
            double vyutm2 = 7532329.79;

            double vxutm1 = Double.parseDouble(xutm1);
            double vyutm1 = Double.parseDouble(yutm1);
            //---------------
            double xx = Math.pow((vxutm1 - vxutm2), 2);
            double yy = Math.pow((vyutm1 - vyutm2), 2);
            double result = Math.sqrt(xx + yy);
            retorno = (result);

        } catch (Exception e) {
            retorno = -1;
        }

        return retorno;
    }

    public ArrayList<String> ListaDomicilios(String zona) throws Exception {

        ArrayList<String> mapMotModo = new ArrayList<>();
        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();

            String sqlquery = "select key, latitude, longitude, centroide, distmatriz from domicilio where zona='" + zona + "' order By key";
            ResultSet TbRs1 = stm.executeQuery(sqlquery);
            while (TbRs1.next()) {
                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String xutm = TbRs1.getString("latitude");
                if (xutm == null) {
                    xutm = "";
                } else {
                    xutm = xutm.replaceAll("[^\\d]", "");
                }
                String yutm = TbRs1.getString("longitude");
                if (yutm == null) {
                    yutm = "";
                } else {
                    yutm = yutm.replaceAll("[^\\d]", "");
                }
                String centroide = TbRs1.getString("centroide");
                if (centroide == null) {
                    centroide = "";
                }
                double distmatriz = TbRs1.getDouble("distmatriz");
                //----------                
                try {
                    xutm = xutm.substring(0, 6) + "." + xutm.substring(6, xutm.length());
                    yutm = yutm.substring(0, 7) + "." + yutm.substring(7, yutm.length());
                } catch (Exception e) {
                }
                //----------
                String corda = key + ";" + xutm + ";" + yutm + ";" + centroide + ";" + String.valueOf(distmatriz);
                mapMotModo.add(corda);
                System.out.println(corda);

            }
            TbRs1.close();
            stm.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaMotivosModoso-" + e.getMessage());
        }

        return mapMotModo;

    }

    public HashMap<Integer, int[]> ObterMobilidadeViagem(String modo) throws Exception {

        HashMap<Integer, int[]> mapVg = new HashMap<>();

        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {
            try {
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm = cnn2.createStatement();
                //-----------------
                //Obtendo Cetroides da Zona
                String sqlquery = "SELECT v.key,v.codigo,v.viagem,v.hora,p.nomedapessoa,p.rendamensal,p.fator2019,p.fator2036,d.centroide,d.distfaixa,p.idade ";
                sqlquery += " FROM viagem74 v,pessoas p,domicilio d ";
                sqlquery += " WHERE v.key = p.key and v.key=d.key ";
                sqlquery += " and v.codigo = p.numerodapessoa ";
                sqlquery += " and motivo=1 ";
                if (modo.length() == 0) {
                    sqlquery += " and (modo1=3 or modo1=10 or modo1=11 or modo2=3 or modo2=10 or modo2=11) ";
                } else if (modo.equalsIgnoreCase("O")) {
                    sqlquery += " and (modo1=3 or modo2=3 or modo3=3) ";
                } else if (modo.equalsIgnoreCase("C")) {
                    sqlquery += " and (modo1=10 or modo1=11 or modo2=10 or modo2=11 or modo3=10 or modo3=11) ";
                }
                sqlquery += " Order by v.key,v.codigo,v.viagem ";
                System.out.println(sqlquery);
                ResultSet TbRs1 = stm.executeQuery(sqlquery);
                while (TbRs1.next()) {
                    String key = TbRs1.getString("key");
                    if (key == null) {
                        key = "";
                    }
                    double rendamensal = TbRs1.getDouble("rendamensal");
                    int distfaixa = TbRs1.getInt("distfaixa");
                    int idade = TbRs1.getInt("idade");

                    //----------   
                    if (!mapVg.containsKey(distfaixa)) {
                        mapVg.put(distfaixa, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
                    }
                    //----------   
                    System.out.println(rendamensal);
                    int idx = 0;
                    if (rendamensal > 0 && rendamensal <= 522.5) {
                        idx = 1;
                    }
                    if (rendamensal > 522.5 && rendamensal <= 1045.0) {
                        idx = 2;
                    }
                    if (rendamensal > 1045.0 && rendamensal <= 2090.0) {
                        idx = 3;
                    }
                    if (rendamensal > 2090.0 && rendamensal <= 3135.0) {
                        idx = 4;
                    }
                    if (rendamensal > 3135.0 && rendamensal <= 5225.0) {
                        idx = 5;
                    }
                    if (rendamensal > 5225.0 && rendamensal <= 10450.0) {
                        idx = 6;
                    }
                    if (rendamensal > 10450.0 && rendamensal <= 15675.0) {
                        idx = 7;
                    }
                    if (rendamensal > 15675.0 && rendamensal <= 20900.0) {
                        idx = 8;
                    }
                    if (rendamensal > 20900.0 && rendamensal <= 1045000.0) {
                        idx = 9;
                    }
                    if (idade < 10) {
                        idx = 10;
                    }
                    //----------
                    if (mapVg.containsKey(distfaixa)) {
                        int[] valor = mapVg.get(distfaixa);
                        valor[idx]++;
                        mapVg.put(distfaixa, valor);
                    }

                }
                TbRs1.close();
                //-----------------
                stm.close();
                cnn2.close();
                //===============================
                //===============================

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }

        return mapVg;
    }

    public HashMap<Integer, int[]> ObterMobilidadePessoas() throws Exception {

        HashMap<Integer, int[]> mapVg = new HashMap<>();

        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {
            try {
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm = cnn2.createStatement();
                //-----------------
                //Obtendo Cetroides da Zona
                String sqlquery = "SELECT p.nomedapessoa,p.rendamensal,p.fator2019,p.fator2036,d.centroide,d.distfaixa,p.idade ";
                sqlquery += " FROM pessoas p,domicilio d ";
                sqlquery += " WHERE p.key = d.key ";

                //out.println(sqlquery);
                ResultSet TbRs1 = stm.executeQuery(sqlquery);
                while (TbRs1.next()) {
                    double rendamensal = TbRs1.getDouble("rendamensal");
                    int distfaixa = TbRs1.getInt("distfaixa");
                    int idade = TbRs1.getInt("idade");

                    //----------   
                    if (!mapVg.containsKey(distfaixa)) {
                        mapVg.put(distfaixa, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
                    }
                    //----------   
                    System.out.println(rendamensal);
                    int idx = 0;
                    if (rendamensal > 0 && rendamensal <= 522.5) {
                        idx = 1;
                    }
                    if (rendamensal > 522.5 && rendamensal <= 1045.0) {
                        idx = 2;
                    }
                    if (rendamensal > 1045.0 && rendamensal <= 2090.0) {
                        idx = 3;
                    }
                    if (rendamensal > 2090.0 && rendamensal <= 3135.0) {
                        idx = 4;
                    }
                    if (rendamensal > 3135.0 && rendamensal <= 5225.0) {
                        idx = 5;
                    }
                    if (rendamensal > 5225.0 && rendamensal <= 10450.0) {
                        idx = 6;
                    }
                    if (rendamensal > 10450.0 && rendamensal <= 15675.0) {
                        idx = 7;
                    }
                    if (rendamensal > 15675.0 && rendamensal <= 20900.0) {
                        idx = 8;
                    }
                    if (rendamensal > 20900.0 && rendamensal <= 1045000.0) {
                        idx = 9;
                    }
                    if (idade < 10) {
                        idx = 10;
                    }
                    //----------
                    if (mapVg.containsKey(distfaixa)) {
                        int[] valor = mapVg.get(distfaixa);
                        valor[idx]++;
                        mapVg.put(distfaixa, valor);
                    }

                }
                TbRs1.close();
                //-----------------
                stm.close();
                cnn2.close();
                //===============================
                //===============================

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }

        return mapVg;
    }

    public HashMap<String, Integer> ObterViagemCentroide(String zona, String ano) throws Exception {

        HashMap<String, Integer> mapVg = new HashMap<>();

        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {
            try {
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm = cnn2.createStatement();
                //-----------------
                //Obtendo Cetroides da Zona
                String sqlquery = " select d.centroide,v.fat2019,v.fat2036 from domicilio d,viagem74 v where d.key=v.key and v.motivo=1 and (modo1=3 or modo2=3 or modo3=3);";
                if (zona.length() > 0) {
                    sqlquery = " select d.centroide,v.fat2019,v.fat2036 from domicilio d,viagem74 v where d.key=v.key and v.motivo=1 and (modo1=3 or modo2=3 or modo3=3) AND d.zona=" + zona;
                }

                //out.println(sqlquery);
                ResultSet TbRs1 = stm.executeQuery(sqlquery);
                while (TbRs1.next()) {
                    String id = TbRs1.getString("centroide");
                    if (id == null) {
                        id = "";
                    }
                    int fat2019 = TbRs1.getInt("fat2019");
                    int fat2036 = TbRs1.getInt("fat2036");

                    //----------   
                    int soma = 1;
                    if (ano.equals("2019")) {
                        soma = fat2019;
                    }
                    if (ano.equals("2036")) {
                        soma = fat2036;
                    }
                    if (mapVg.containsKey(id)) {
                        int conta = mapVg.get(id);
                        soma += conta;
                    }
                    //----------   
                    mapVg.put(id, soma);

                }
                TbRs1.close();
                //-----------------
                stm.close();
                cnn2.close();
                //===============================
                //===============================

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }

        return mapVg;
    }

}
