/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Administrador
 */
public class Propriedades {

    private Properties props;
    private static final File file = new File("/jspupload/props", "propsjau.properties");

    /**
     * Creates a new instance of Propriedades
     */
    public Propriedades() {

        props = new Properties();
        try {
            props.load(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

    }

    public String getDbUrl(int tipo) {
        String var = "dbUrl" + String.valueOf(tipo);
        return props.getProperty(var);
    }

    public String getDbJdbcDriver(int tipo) {
        String var = "dbJdbcDriver" + String.valueOf(tipo);
        return props.getProperty(var);
    }

    public String getDbUser(int tipo) {
        String var = "dbUser" + String.valueOf(tipo);
        return props.getProperty(var);
    }

    public String getDbSenha(int tipo) {
        String var = "dbSenha" + String.valueOf(tipo);
        return props.getProperty(var);
    }

    public String getMainDir() {
        String var = "mainDir";
        return props.getProperty(var);
    }
   
     public String getLogDir() {
        String var = "logDir";
        return props.getProperty(var);
    }
   

    public String getTempDir() {
        String var = "tempDir";
        return props.getProperty(var);
    }
    
    public String getExcelDir() {
        String var = "excelDir";
        return props.getProperty(var);
    }

    
    public String getSenhaAdm() {
        String var = "senhaAdm";
        return props.getProperty(var);
    }

}
