package com.example.moisesvargas.hipertin.CapaData;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static String mensajeError = null;
    public static Connection ConexionLocal()
    {
        Connection conexionLocal = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String cn = "jdbc:jtds:sqlserver://bpdistribuciones.ddns.net:1433;databaseName=Data_002;user=sa;password=Ab123456789;";
            //String cn = "jdbc.jtds:sqlserver://10.0.0.2\\SQLEXPRESS;databaseName=Next_Sales;user=Sa;password=Ab123456789;";
            //String cn = "jdbc:jtds:sqlserver://DESKTOP-BU2EP49\\SQLEXPRESS:1433;databaseName=data_001;user=Sa;password=Ab123456789;";
            //String cn = "jdbc:jtds:sqlserver://DESKTOP-BU2EP49:1433;instanceName=SQLEXPRESS;databaseName=Versiculo;user=Sa;password=Ab123456789;";
            conexionLocal = DriverManager.getConnection(cn);
        }
        catch (Exception e)
        {
            mensajeError = e.getMessage();
            throw new RuntimeException("Conexion: "+e.getMessage());
        }
        return conexionLocal;
    }
}