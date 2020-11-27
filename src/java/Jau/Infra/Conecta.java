/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrador
 */
public class Conecta {

    public static String msgErro = "";

    public static Connection getConexao(int tipo) {
        Connection conn = null;
        try {

            boolean lupinho = true;
            int contaLup = 0;
            while (lupinho) {
                try {
                    Propriedades props = new Propriedades();
                    //----------------------
                    Class.forName(props.getDbJdbcDriver(tipo));
                    conn = DriverManager.getConnection(props.getDbUrl(tipo), props.getDbUser(tipo), props.getDbSenha(tipo));
                    if (contaLup == 100) {
                        lupinho = false;
                    }
                    //----------------------
                } catch (ClassNotFoundException e) {
                     msgErro += "Err.Class="+e.getMessage();
                    contaLup++;
                } catch (SQLException e) {
                     msgErro += "Err.SQL="+e.getMessage();
                    contaLup++;
                }
                //----------------------
                lupinho = false;
            }
            //==================================

        } catch (Exception e) {
             msgErro += "Erro="+e.getMessage();
        }

        return conn;
    }
}
