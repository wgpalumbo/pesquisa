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
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Walter
 */
public class Consolidar {

    //-----------------
    private final HttpServletResponse response;
    private final PageContext paginaContexto;
    private final PrintWriter out;

    public Consolidar(PageContext pageContext) throws IOException, Exception {

        this.response = (HttpServletResponse) pageContext.getResponse();
        this.out = response.getWriter();
        this.paginaContexto = pageContext;

    }

    public void AjustaZonaOrigem() throws Exception {

        String[] arrmodopr1 = {"3", "4", "5", "6", "7", "8", "9"};
        String[] arrmodopr2 = {"10", "11", "14", "15", "16", "17"};
        String[] arrmodopr3 = {"12", "13"};
        String[] arrmodopr4 = {"1", "2"};

        List<String> modopr1 = Arrays.asList(arrmodopr1);
        List<String> modopr2 = Arrays.asList(arrmodopr2);
        List<String> modopr3 = Arrays.asList(arrmodopr3);
        List<String> modopr4 = Arrays.asList(arrmodopr4);

        StringBuilder msgErros = new StringBuilder();

        //Executor executor1 = Executors.newSingleThreadExecutor();
        //executor1.execute(() -> {
        try {

            Connection cnn1 = Conecta.getConexao(1);
            Statement stm1 = cnn1.createStatement();
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm2 = cnn2.createStatement();

            stm2.execute("DELETE FROM VIAGEM74");

            String sqlquery = "select v.id_casa as key,";
            sqlquery += "v.numero_pessoa,";
            sqlquery += "v.numero_viagem,";
            sqlquery += "v.zona,";
            sqlquery += "d.zona as zonad,";
            sqlquery += "v.hora,";
            sqlquery += "v.mot,";
            sqlquery += "v.mod,";
            sqlquery += "v.mod1,";
            sqlquery += "v.mod2 ";
            sqlquery += " from viajens v, casa d WHERE v.id_casa=d.id  Order by id_casa, numero_pessoa, numero_viagem, hora ";

            //out.println(sqlquery);
            String keyref = "", zonaorigem = "";
            ResultSet TbRs1 = stm1.executeQuery(sqlquery);
            while (TbRs1.next()) {

                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String numerodapessoa = TbRs1.getString("numero_pessoa");
                if (numerodapessoa == null) {
                    numerodapessoa = "";
                }

                String numeroviagem = TbRs1.getString("numero_viagem");
                if (numeroviagem == null) {
                    numeroviagem = "";
                } else {
                    numeroviagem = numeroviagem.replaceAll("[^\\d]", "");
                }

                String zona = TbRs1.getString("zona");
                if (zona == null || zona.trim().length() == 0) {
                    zona = key.substring(0, 2);
                }

                String zonad = TbRs1.getString("zonad");
                if (zonad == null) {
                    zonad = "null";
                }

                String horasaida = TbRs1.getString("hora");
                if (horasaida == null) {
                    horasaida = "";
                }

                String motivo = TbRs1.getString("mot");
                if (motivo == null) {
                    motivo = "null";
                } else {
                    motivo = motivo.replaceAll("[^\\d]", "");
                }

                String modo1 = TbRs1.getString("mod");
                if (modo1 == null) {
                    modo1 = "0";
                } else {
                    modo1 = modo1.replaceAll("[^\\d]", "");
                }
                String modo2 = TbRs1.getString("mod1");
                if (modo2 == null) {
                    modo2 = "0";
                } else {
                    modo2 = modo2.replaceAll("[^\\d]", "");
                }
                String modo3 = TbRs1.getString("mod2");
                if (modo3 == null) {
                    modo3 = "0";
                } else {
                    modo3 = modo3.replaceAll("[^\\d]", "");
                }

                //-------------    
                //Modo 1
                try {
                    int mod_num = Integer.parseInt(modo1);
                    modo1 = String.valueOf(mod_num);
                } catch (Exception e) {
                    modo1 = "0";
                }
                //Modo 2
                try {
                    int mod_num = Integer.parseInt(modo2);
                    modo2 = String.valueOf(mod_num);
                } catch (Exception e) {
                    modo2 = "0";
                }
                //Modo 3
                try {
                    int mod_num = Integer.parseInt(modo3);
                    modo3 = String.valueOf(mod_num);
                } catch (Exception e) {
                    modo3 = "0";
                }
                //-------------    
                String modopr = "0";
                try {
                    if (modopr1.contains(modo1.trim()) || modopr1.contains(modo2.trim()) || modopr1.contains(modo3.trim())) {
                        modopr = "1";
                    } else if (modopr2.contains(modo1.trim()) || modopr2.contains(modo2.trim()) || modopr2.contains(modo3.trim())) {
                        modopr = "2";
                    } else if (modopr3.contains(modo1.trim()) || modopr2.contains(modo2.trim()) || modopr3.contains(modo3.trim())) {
                        modopr = "3";
                    } else if (modopr4.contains(modo1.trim()) || modopr4.contains(modo2.trim()) || modopr4.contains(modo3.trim())) {
                        modopr = "4";
                    }
                } catch (Exception e) {
                    modopr = "0";
                }
                //-------------
                String domicilio = key.replaceAll("[^\\d]", "");
                zona = zona.replaceAll("[^\\d]", "");
                zonad = zonad.replaceAll("[^\\d]", "");
                String hora = "";
                try {
                    hora = horasaida.substring(0, 5);
                    hora = hora.replaceAll("[^\\d]", "");
                } catch (Exception e) {
                    hora = "";
                }
                //-------------
                if (!key.equals(keyref)) {
                    keyref = key;
                    zonaorigem = zonad;
                }
                //-------------
                boolean podeAdd = true;
                if (numerodapessoa.trim().length() == 0) {
                    msgErros.append("ERRO NUMEROPESSOA/ID = " + key + " / NRO.VIAGEM = " + numeroviagem + "<br>");
                    podeAdd = false;
                }
                if (numeroviagem.trim().length() == 0) {
                    msgErros.append("ERRO NUMEROVIAGEM/ID = " + key + " / NRO.VIAGEM = " + numeroviagem + "<br>");
                    podeAdd = false;
                }
                if (hora.trim().length() == 0) {
                    msgErros.append("ERRO HORA/ID = " + key + " / NRO.VIAGEM = " + numeroviagem + "<br>");
                    podeAdd = false;
                }
                if (zonad.trim().length() == 0) {
                    msgErros.append("ERRO ZONADESTINO/ID = " + key + " / NRO.VIAGEM = " + numeroviagem + "<br>");
                    podeAdd = false;
                }
                if (motivo.trim().length() == 0) {
                    msgErros.append("ERRO MOTIVO/ID = " + key + " / NRO.VIAGEM = " + numeroviagem + "<br>");
                    podeAdd = false;
                }
                //-------------
                if (podeAdd) {

                    String sqlServer = "INSERT INTO VIAGEM74 ( DOMICILIO, CODIGO, VIAGEM, HORA, ZONADESTINO, MOTIVO, MODO1, MODO2, MODO3, ZONAORIGEM, KEY, MODOPR ) VALUES ( " + domicilio + " , " + numerodapessoa + " , " + numeroviagem + " , '" + hora + "' , " + zona + " , " + motivo + ", " + modo1 + ", " + modo2 + ", " + modo3 + ", " + zonaorigem + ", '" + key + "', " + modopr + " )";

                    ExecutorService executor2 = Executors.newFixedThreadPool(15);
                    executor2.execute(() -> {
                        try {

                            stm2.execute(sqlServer);

                            //----------------                           
                        } catch (Exception e) {
                            msgErros.append(e.getMessage() + sqlServer);
                        }
                    });
                    executor2.shutdown();
                    while (!executor2.isTerminated()) {
                    }
                    //};
                    //T//hread thread = new Thread(task);
                    //thread.start();

                    zonaorigem = zona;
                }

            }
            //Ajuste ZONACASA nas Viagem
            try {

                String sqlServer = "update viagem74 set zonacasa = domicilio.zona from domicilio where domicilio.key = viagem74.key;";
                stm2.execute(sqlServer);

                //----------------                           
            } catch (Exception e) {
                msgErros.append(e.getMessage() + "ERRO AJUSTE ZONACASA !");
            }

            TbRs1.close();
            stm1.close();
            stm2.close();
            cnn1.close();
            cnn2.close();

            msgErros.append("Finalizado");
            out.println(msgErros.toString());

        } catch (Exception e) {
            try {
                GravarLog.gravarLog("-Loop Fora-" + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //});

    }

    public void AjustaMorador() throws Exception {

        StringBuilder msgErros = new StringBuilder();

        //Executor executor1 = Executors.newSingleThreadExecutor();
        //executor1.execute(() -> {
        try {

            Connection cnn1 = Conecta.getConexao(1);
            Statement stm1 = cnn1.createStatement();
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm2 = cnn2.createStatement();

            stm2.execute("DELETE FROM PESSOAS");

            String sqlquery = "select id_casa as key,";
            sqlquery += "numero_pessoa,";
            sqlquery += "nome,";
            sqlquery += "sexo,";
            sqlquery += "idade,";
            sqlquery += "grau,";
            sqlquery += "renda ";

            sqlquery += " from moradores";

            //out.println(sqlquery);
            String keyref = "", zonaorigem = "";
            ResultSet TbRs1 = stm1.executeQuery(sqlquery);
            while (TbRs1.next()) {

                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String numerodapessoa = TbRs1.getString("numero_pessoa");
                if (numerodapessoa == null) {
                    numerodapessoa = "";
                }

                String nome = TbRs1.getString("nome");
                if (nome == null || nome.trim().length() == 0) {
                    nome = key.substring(0, 2);
                }

                String sexo = TbRs1.getString("sexo");
                if (sexo == null) {
                    sexo = "";
                } else {
                    sexo = sexo.replaceAll("[^\\d]", "");
                }

                String idade = TbRs1.getString("idade");
                if (idade == null) {
                    idade = "";
                } else {
                    idade = idade.replaceAll("[^\\d]", "");
                }

                String grau = TbRs1.getString("grau");
                if (grau == null) {
                    grau = "";
                } else {
                    grau = grau.replaceAll("[^\\d]", "");
                }

                String renda = TbRs1.getString("renda");
                if (renda == null || renda.trim().length() == 0) {
                    if (renda.contains(",")) {
                        renda = renda.substring(0, renda.indexOf(","));
                    }
                }

                //-------------    
                //Sexo
                try {
                    int mod_num = Integer.parseInt(sexo);
                    sexo = String.valueOf(mod_num);
                } catch (Exception e) {
                    sexo = "";
                }
                //Modo 2
                try {
                    int mod_num = Integer.parseInt(idade);
                    idade = String.valueOf(mod_num);
                } catch (Exception e) {
                    idade = "";
                }
                //Modo 3
                try {
                    int mod_num = Integer.parseInt(grau);
                    grau = String.valueOf(mod_num);
                } catch (Exception e) {
                    grau = "";
                }
                //Renda
                try {
                    if (renda.indexOf(".") != renda.lastIndexOf(".")) {
                        renda = "";
                    } else {

                        if (renda.contains(",")) {
                            if (renda.indexOf(",") != renda.lastIndexOf(",")) {
                                renda = "";
                            } else {
                                renda = renda.substring(0, renda.indexOf(","));
                            }
                        }
                        renda = renda.replaceAll("[^\\d]", "");
                    }
                } catch (Exception e) {
                    renda = "";
                }

                //-------------
                boolean podeAdd = true;
                if (numerodapessoa.trim().length() == 0) {
                    msgErros.append("ERRO NUMEROPESSOA/ID = " + key);
                    podeAdd = false;
                }
                if (idade.trim().length() == 0) {
                    msgErros.append("ERRO IDADE/ID = " + key + " / NRO.PESSOA = " + numerodapessoa + "<br>");
                    podeAdd = false;
                }
                if (grau.trim().length() == 0) {
                    msgErros.append("ERRO GRAU INSTR./ID = " + key + " / NRO.PESSOA = " + numerodapessoa + "<br>");
                    podeAdd = false;
                }
                if (renda.trim().length() == 0) {
                    msgErros.append("ERRO RENDA/ID = " + key + " / NRO.PESSOA = " + numerodapessoa + "<br>");
                    podeAdd = false;
                }

                //-------------
                if (podeAdd) {

                    String sqlServer = "INSERT INTO PESSOAS ( key, numerodapessoa, nomedapessoa, sexo, idade, graudeinstrucao, rendamensal, zonacasa ) VALUES ( '" + key + "' , " + numerodapessoa + " , '" + nome + "' , " + sexo + " , " + idade + ", " + grau + ", " + renda + "," + key.substring(0, 2) + " )";

                    ExecutorService executor2 = Executors.newFixedThreadPool(15);
                    executor2.execute(() -> {
                        try {

                            stm2.execute(sqlServer);
                            //System.out.println(sqlServer);

                            //----------------                           
                        } catch (Exception e) {
                            msgErros.append(e.getMessage() + sqlServer);
                            System.out.println(e.getMessage());
                        }
                    });
                    executor2.shutdown();
                    while (!executor2.isTerminated()) {
                    }
                    //};
                    //T//hread thread = new Thread(task);
                    //thread.start();

                }

            }

            TbRs1.close();
            stm1.close();
            stm2.close();
            cnn1.close();
            cnn2.close();

            msgErros.append("Finalizado");
            out.println(msgErros.toString());

        } catch (Exception e) {
            try {
                GravarLog.gravarLog("-Loop Fora-" + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //});

    }

    private String acharFaixaRenda(int valor) {

        String retorno = "A";
        if (valor >= 38 && valor <= 44) {
            retorno = "B1";
        }
        if (valor >= 29 && valor <= 37) {
            retorno = "B2";
        }
        if (valor >= 23 && valor <= 28) {
            retorno = "C1";
        }
        if (valor >= 17 && valor <= 22) {
            retorno = "C2";
        }
        if (valor >= 0 && valor <= 16) {
            retorno = "D-E";
        }

        return retorno;
    }

    public void AjustaDomicilioOrigem() throws Exception {

        StringBuilder msgErros = new StringBuilder();

        //Executor executor1 = Executors.newSingleThreadExecutor();
        //executor1.execute(() -> {
        try {

            Connection cnn1 = Conecta.getConexao(1);
            Statement stm1 = cnn1.createStatement();
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm2 = cnn2.createStatement();

            stm2.execute("DELETE FROM DOMICILIO");

            String sqlquery = "select id as key,";
            sqlquery += "latitude,";
            sqlquery += "longitude,";
            sqlquery += "zona,";
            sqlquery += "pontos";

            sqlquery += " from casa";

            //out.println(sqlquery);
            ResultSet TbRs1 = stm1.executeQuery(sqlquery);
            while (TbRs1.next()) {

                String key = TbRs1.getString("key");
                if (key == null) {
                    key = "";
                }
                String latitude = TbRs1.getString("latitude");
                if (latitude == null) {
                    latitude = "";
                }

                String longitude = TbRs1.getString("longitude");
                if (longitude == null) {
                    longitude = "";
                }

                String zona = TbRs1.getString("zona");
                if (zona == null) {
                    zona = "";
                } else {
                    zona = zona.replaceAll("[^\\d]", "");
                }

                String pontos = TbRs1.getString("pontos");
                if (pontos == null) {
                    pontos = "";
                } else {
                    pontos = pontos.replaceAll("[^\\d]", "");
                }
                int pontosNum = 0;

                //-------------    
                //Sexo
                try {
                    pontosNum = Integer.parseInt(pontos);
                } catch (Exception e) {
                    pontos = "";
                }
                String fxrenda = acharFaixaRenda(pontosNum);

                //Modo 2
                try {
                    int mod_num = Integer.parseInt(zona);
                    zona = String.valueOf(mod_num);
                } catch (Exception e) {
                    zona = "";
                }

                //-------------
                boolean podeAdd = true;
                if (zona.trim().length() == 0) {
                    msgErros.append("ERRO ZONA/ID = " + key + "<br>");
                    podeAdd = false;
                }
                if (pontos.trim().length() == 0) {
                    msgErros.append("ERRO PONTOS/ID = " + key + "<br>");
                    podeAdd = false;
                }

                //-------------
                if (podeAdd) {

                    String sqlServer = "INSERT INTO DOMICILIO ( key, latitude, longitude , zona , pontos , faixaderenda ) VALUES ( '" + key + "' , '" + latitude + "' , '" + longitude + "' , " + zona + " , " + String.valueOf(pontosNum) + ", '" + fxrenda + "')";

                    ExecutorService executor2 = Executors.newFixedThreadPool(15);
                    executor2.execute(() -> {
                        try {

                            stm2.execute(sqlServer);

                            //----------------                           
                        } catch (Exception e) {
                            msgErros.append(e.getMessage() + sqlServer);
                        }
                    });
                    executor2.shutdown();
                    while (!executor2.isTerminated()) {
                    }
                    //};
                    //T//hread thread = new Thread(task);
                    //thread.start();

                }

            }

            //------------------
            stm2.execute("update pessoas set zonacasa=zona from domicilio where pessoas.key = domicilio.key");

            TbRs1.close();
            stm1.close();
            stm2.close();
            cnn1.close();
            cnn2.close();

            msgErros.append("Finalizado");
            out.println(msgErros.toString());

        } catch (Exception e) {
            try {
                GravarLog.gravarLog("-Loop Fora-" + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //});

    }

    public void GravarFatoresExpansao(int ano, HashMap<String, String> mapFatoresAchados, double salariominimo) throws SQLException, IOException {

        //------
        try {
            if (!mapFatoresAchados.isEmpty()) {
                //----
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm2 = cnn2.createStatement();
                //---
                ExecutorService executor2 = Executors.newFixedThreadPool(15);
                executor2.execute(() -> {

                    for (Map.Entry<String, String> set : mapFatoresAchados.entrySet()) {
                        String zona = set.getKey();
                        String valor = set.getValue();
                        //------------
                        String mycriteria = "UPDATE fatorexpansao set fator='" + (valor) + "' WHERE zona='" + zona + "' AND ano=" + ano + "; ";
                        mycriteria += " INSERT INTO fatorexpansao (ano, zona, fator) ";
                        mycriteria += " SELECT " + ano + ", " + zona + ", '" + (valor) + "'";
                        mycriteria += " WHERE NOT EXISTS (SELECT * FROM fatorexpansao WHERE zona='" + zona + "' AND ano=" + ano + ")";
                        try {
                            stm2.execute(mycriteria);
                            //----------------
                        } catch (Exception e) {
                            try {
                                GravarLog.gravarLog("GravarFatoresExpansao: " + e.getMessage() + mycriteria);
                            } catch (IOException ex) {
                                Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                });
                executor2.shutdown();
                while (!executor2.isTerminated()) {
                }
                stm2.close();
                cnn2.close();
                //---
                AplicarFatoresExpansao(ano, salariominimo);
            }

        } catch (Exception e) {
            GravarLog.gravarLog("GRAVARtypes:" + e.getMessage());
        }

    }

    public void AtribuirFatorExpansaoNasViagens() throws SQLException, IOException {

        try {
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            Statement stm2 = cnn2.createStatement();
            String sqlquery = "select key, numerodapessoa, fator2019, fator2036 from pessoas";
            System.out.println(sqlquery);
            ExecutorService executor2 = Executors.newFixedThreadPool(15);
            executor2.execute(() -> {
                try {
                    ResultSet TbRs1 = stm.executeQuery(sqlquery);
                    while (TbRs1.next()) {
                        String key = TbRs1.getString("key");
                        if (key == null) {
                            key = "";
                        }
                        int numerodapessoa = TbRs1.getInt("numerodapessoa");
                        int fator2019 = TbRs1.getInt("fator2019");
                        int fator2036 = TbRs1.getInt("fator2036");
                        //---------------
                        try {
                            String myQuery = "UPDATE VIAGEM74 set fat2019=" + String.valueOf(fator2019) + ",fat2036=" + String.valueOf(fator2036) + " WHERE key='" + key + "' AND codigo=" + String.valueOf(numerodapessoa);
                            stm2.execute(myQuery);
                            System.out.println(myQuery);
                            //---------------
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    TbRs1.close();
                    out.println("Finalizado");
                } catch (SQLException ex) {
                    Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            executor2.shutdown();
            while (!executor2.isTerminated()) {
            }
            stm.close();
            stm2.close();
            cnn2.close();

        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-AtribuirFatorExpansaoViagens-" + e.getMessage());
        }

    }

    public HashMap<String, Integer[]> ObterFatoresExpansao(int ano) throws SQLException, IOException {
        HashMap<String, Integer[]> mapFatores = new HashMap<>();
        //------
        try {
            if (ano > 0) {
                //----
                Connection cnn2 = Conecta.getConexao(2);
                Statement stm2 = cnn2.createStatement();
                //---
                ExecutorService executor2 = Executors.newFixedThreadPool(15);
                executor2.execute(() -> {

                    String sqlps2 = "SELECT zona,fator FROM fatorexpansao WHERE ano=" + ano + " order by zona";
                    try {
                        ResultSet TbRs2 = stm2.executeQuery(sqlps2);
                        while (TbRs2.next()) {
                            String zona = TbRs2.getString("zona");
                            if (zona != null) {
                                zona = zona.trim();
                            } else {
                                zona = "";
                            }
                            Array outputArray = TbRs2.getArray("fator");
                            mapFatores.put(zona, (Integer[]) outputArray.getArray());
                            // System.out.println(outputArray.toString());

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                executor2.shutdown();
                while (!executor2.isTerminated()) {
                }

                //---
                stm2.close();
                cnn2.close();
            }

        } catch (Exception e) {
            GravarLog.gravarLog("GRAVARtypes:" + e.getMessage());
        }
        return mapFatores;
    }

    private void AplicarFatoresExpansao(int ano, double salariominimo) throws SQLException, IOException {
        /*
        HashMap<String, Integer> mapSM_0 = relato.MontaSalarioMinimo(0,0.5*salariominimo);
	HashMap<String, Integer> mapSM_1 = relato.MontaSalarioMinimo(0.5*salariominimo,salariominimo);
	HashMap<String, Integer> mapSM_1_2 = relato.MontaSalarioMinimo(salariominimo,2*salariominimo);
	HashMap<String, Integer> mapSM_2_3 = relato.MontaSalarioMinimo(2*salariominimo,3*salariominimo);
	HashMap<String, Integer> mapSM_3_5 = relato.MontaSalarioMinimo(3*salariominimo,5*salariominimo);
	HashMap<String, Integer> mapSM_5_10 = relato.MontaSalarioMinimo(5*salariominimo,10*salariominimo);
	HashMap<String, Integer> mapSM_10_15 = relato.MontaSalarioMinimo(10*salariominimo,15*salariominimo);
	HashMap<String, Integer> mapSM_15_20 = relato.MontaSalarioMinimo(15*salariominimo,20*salariominimo);
	HashMap<String, Integer> mapSM_20_99 = relato.MontaSalarioMinimo(20*salariominimo,1000*salariominimo);
        HashMap<String, Integer> mapSM_SemRend = relato.MontaSalarioMinimo(0,0);
         */
        double[] salario1 = new double[]{0, 0.5 * salariominimo, salariominimo, 2 * salariominimo, 3 * salariominimo, 5 * salariominimo, 10 * salariominimo, 15 * salariominimo, 20 * salariominimo, 0};
        double[] salario2 = new double[]{0.5 * salariominimo, salariominimo, 2 * salariominimo, 3 * salariominimo, 5 * salariominimo, 10 * salariominimo, 15 * salariominimo, 20 * salariominimo, 1000 * salariominimo, 0};

        HashMap<String, Integer[]> mapFatores = ObterFatoresExpansao(ano);

        ExecutorService executor2 = Executors.newFixedThreadPool(15);
        executor2.execute(() -> {

            for (Map.Entry<String, Integer[]> set : mapFatores.entrySet()) {
                String zona = set.getKey();
                Integer[] valor = set.getValue();
                System.out.println(zona + " - " + String.valueOf(valor[11]));
                //valor.length nao pode pois as ultimas colunas sao com ou sem rendimento e menores (11-2=9)
                //-----
                //Com ou Sem Rendimento (todo mundo inclusive menor de idade)
                for (int j = 0; j < 10; j++) {
                    try {
                        MontaFatorExpansao(ano, zona, salario1[j], salario2[j], valor[j], false);
                    } catch (Exception ex) {
                        Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex.getMessage());
                    }
                }
                //----- 
                //Fator Menor de Idade (arrumando somente os menores de idade)
                try {
                    MontaFatorExpansao(ano, zona, 0, 0, valor[11], true);
                } catch (Exception ex) {
                    Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
                //-----
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }
    }

    private void MontaFatorExpansao(int ano, String zona, double sal1, double sal2, int fator, boolean menor) throws Exception {

        try {

            ExecutorService executor2 = Executors.newFixedThreadPool(15);
            executor2.execute(() -> {

                String campo = "fator" + String.valueOf(ano);

                String sqlquery = " update pessoas set " + campo + " =" + fator + " where rendamensal>" + String.valueOf(sal1) + " and rendamensal<=" + String.valueOf(sal2) + " AND zonacasa=" + zona;
                if (sal1 == 0 && sal2 == 0 && !menor) {
                    sqlquery = " update pessoas set " + campo + "=" + fator + " where rendamensal=0  AND zonacasa=" + zona;
                }
                if (sal1 == 0 && sal2 == 0 && menor) {
                    sqlquery = " update pessoas set " + campo + "=" + fator + " where idade<=10 AND zonacasa=" + zona;
                }
                try {
                    Connection cnn2 = Conecta.getConexao(2);
                    Statement stm = cnn2.createStatement();
                    stm.execute(sqlquery);
                    stm.close();
                    cnn2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }

            });
            executor2.shutdown();
            while (!executor2.isTerminated()) {
            }

            //===========
        } catch (Exception e) {
            GravarLog.gravarLog(this.getClass().getSimpleName() + "-MontaSintesePorZona-" + e.getMessage());
        }

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

    public void AjustaCentroide() {
        HashMap<String, String[]> mapCentro = new HashMap<>();
        String xutm1 = "", yutm1 = "", zonaref = "";
        try {

            Connection cnn2 = Conecta.getConexao(2);
            Statement stm = cnn2.createStatement();
            //-----------------
            //Obtendo Cetroides da Zona

            String sqlquery = "select key, latitude, longitude, zona from domicilio order By zona";
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
                String zona = TbRs1.getString("zona");
                if (zona == null) {
                    zona = "";
                } else {
                    if (!zona.equals(zonaref)) {
                        zonaref = zona;
                        mapCentro = ObterCentroides(zona);
                    }
                }
                //----------                
                try {
                    xutm1 = xutm.substring(0, 6) + "." + xutm.substring(6, xutm.length());
                    yutm1 = yutm.substring(0, 7) + "." + yutm.substring(7, yutm.length());
                } catch (Exception e) {
                }

                //----------
                //Centroides
                String xutm_escolhido = "";
                String yutm_escolhido = "";
                String escolhido = "";
                double valorescolhido = 0;
                for (Map.Entry<String, String[]> set1 : mapCentro.entrySet()) {
                    String[] utm2 = set1.getValue();
                    double result = AcharDistancia(xutm1, yutm1, utm2[0], utm2[1]);
                    if ((valorescolhido == 0) || (valorescolhido > result && result != -1)) {
                        valorescolhido = result;
                        escolhido = set1.getKey();
                        xutm_escolhido = utm2[0];
                        yutm_escolhido = utm2[1];
                    }
                }
                //----------
                //Matriz
                double vxutm2 = 751615.65;
                double vyutm2 = 7532329.79;
                double result1 = -1;
                try {
                    //----------
                    //Distancia Matriz
                    double vxutm1 = Double.parseDouble(xutm_escolhido);
                    double vyutm1 = Double.parseDouble(yutm_escolhido);
                    //---------------
                    double xx = Math.pow((vxutm1 - vxutm2), 2);
                    double yy = Math.pow((vyutm1 - vyutm2), 2);
                    result1 = Math.sqrt(xx + yy);
                } catch (Exception ex) {
                    result1 = -1;
                }
                //----------  
                //Achando Faixa Distancia
                int result3 = 1000;
                if (result1 > 1000 && result1 <= 2000) {
                    result3 = 2000;
                }
                if (result1 > 2000 && result1 <= 3000) {
                    result3 = 3000;
                }
                if (result1 > 3000 && result1 <= 4000) {
                    result3 = 4000;
                }
                if (result1 > 4000 && result1 <= 5000) {
                    result3 = 5000;
                }
                if (result1 > 5000 && result1 <= 7000) {
                    result3 = 7000;
                }
                if (result1 > 7000 && result1 <= 10000) {
                    result3 = 10000;
                }
                if (result1 > 10000 && result1 <= 1000000) {
                    result3 = 1000000;
                }
                //---------- 
                GravarCentroides(key, escolhido, result1, result3);
                //----------
            }
            TbRs1.close();
            //-----------------
            stm.close();
            cnn2.close();
            out.println("Finalizado");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void GravarCentroides(String key, String centroide, double result, int result2) throws SQLException, IOException {

        //------
        try {

            //----
            Connection cnn2 = Conecta.getConexao(2);
            Statement stm2 = cnn2.createStatement();
            //---
            ExecutorService executor2 = Executors.newFixedThreadPool(15);
            executor2.execute(() -> {

                //------------
                String mycriteria = "UPDATE domicilio set centroide='" + centroide + "',distmatriz=" + String.valueOf(result) + ",distfaixa=" + String.valueOf(result2) + " WHERE key='" + key + "'";

                try {
                    stm2.execute(mycriteria);
                    //----------------
                } catch (Exception e) {
                    try {
                        GravarLog.gravarLog("GravarFatoresExpansao: " + e.getMessage() + mycriteria);
                    } catch (IOException ex) {
                        Logger.getLogger(Consolidar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            executor2.shutdown();
            while (!executor2.isTerminated()) {
            }
            stm2.close();
            cnn2.close();
            //---

        } catch (Exception e) {
            GravarLog.gravarLog("GravarCentroides:" + e.getMessage());
        }

    }
}
