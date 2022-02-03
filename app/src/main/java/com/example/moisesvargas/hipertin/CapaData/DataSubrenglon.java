package com.example.moisesvargas.hipertin.CapaData;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataSubrenglon {
    String codigo,descripcion,renglon;
    int id;

    public DataSubrenglon(String codigo, String descripcion, String renglon) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.renglon = renglon;
    }

    public DataSubrenglon( String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenglon() {
        return renglon;
    }

    public void setRenglon(String renglon) {
        this.renglon = renglon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DataSubrenglon() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public DataSubrenglon getSubrenglon(Context context)
    {
        DataSubrenglon subRenglon = new DataSubrenglon();
        try{
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            db.execSQL("DELETE FROM renglon");
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String select = "select * from RENGLON";
            ResultSet rs = st.executeQuery(select);

            while (rs.next()) {
                subRenglon.setCodigo(rs.getString("codigo"));
                subRenglon.setDescripcion(rs.getString("descripcion"));
                registrarSubrenglones(context,subRenglon);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());

        }
        return subRenglon;
    }

    public void registrarSubrenglones(Context context, DataSubrenglon subRenglon) {
        try {

            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("descripcion",subRenglon.getDescripcion());
            values.put("codigo",subRenglon.getCodigo());

            Long idResultante=db.insert("renglon","stringConexion",values);

            // db.execSQL("UPDATE Producto SET categoria='Tinte' WHERE categoria = '0' ");
            // db.execSQL("DELETE FROM Producto WHERE categoria='Producto' ");

        }catch (Exception e){
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());


        }

    }
}
