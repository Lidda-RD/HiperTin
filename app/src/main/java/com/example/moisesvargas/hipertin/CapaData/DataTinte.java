package com.example.moisesvargas.hipertin.CapaData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.VariablesGlobal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataTinte {
    int id;
    String codigo,descripcion,renglon,cantidad,categoria;
    Double precio, itbis;

    public DataTinte(int id, String codigo, String descripcion, String renglon, Double precio,String cantidad, Double itbis) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.renglon = renglon;
        this.precio = precio;
        this.cantidad = cantidad;
        this.itbis = itbis;
    }


    public DataTinte() {

    }

    public Double getItbis() {
        return itbis;
    }

    public void setItbis(Double itbis) {
        this.itbis = itbis;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRenglon() {
        return renglon;
    }

    public void setRenglon(String renglon) {
        this.renglon = renglon;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public DataTinte getArticulo(Context context)
    {
        DataTinte articulos = new DataTinte();
        try{
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            db.execSQL("DELETE FROM Producto");
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String select = "SELECT ARTICULO.codigo, ARTICULO.itbis, ARTICULO.descripcion, ARTICULO.precio3, RENGLON.descripcion AS Renglon " +
                    " FROM ARTICULO  LEFT OUTER JOIN RENGLON ON ARTICULO.renglon = RENGLON.codigo " +
                    " WHERE (ARTICULO.activo = 1)";
            ResultSet rs = st.executeQuery(select);

            while (rs.next()) {

                articulos.setDescripcion(rs.getString("descripcion"));
                articulos.setCodigo(rs.getString("codigo"));
                articulos.setPrecio(rs.getDouble("precio3"));
                articulos.setRenglon(rs.getString("renglon"));
                articulos.setItbis(rs.getDouble("itbis"));
                //Insertar articulos en Sqlite
                ContentValues values = new ContentValues();
                values.put("descripcion",articulos.getDescripcion());
                values.put("codigo",articulos.getCodigo());
                values.put("precio",articulos.getPrecio());
                values.put("renglon",articulos.getRenglon());
                values.put("itbis",articulos.getItbis());
                Long idResultante=db.insert("Producto","stringConexion",values);

            }
        }
        catch (Exception e)
        {
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());

        }
        return articulos;
    }

    ArrayList<DataTinte> listaTinte;
    public ArrayList<DataTinte> consultarListaTinte(Context context, ConexionSqlite conn,String _subrenglon) {
        final VariablesGlobal variablesGlobal = (VariablesGlobal) context;
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT *, ifnull((select sum(cantidad) from Pedido_Detalle where codigo = Producto.codigo and documento ='"+variablesGlobal.getDocumento()+"' ),0) As Cantidad FROM Producto WHERE renglon = '"+_subrenglon+"'  ORDER BY descripcion ASC ",null);
            listaTinte = new ArrayList<DataTinte>();
            while (cursor.moveToNext()) {
                listaTinte.add(new DataTinte(1,cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3),cursor.getString(5),cursor.getDouble(4)));
            }
        }catch (Exception e){
            Toast.makeText(context,"Tinte: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return listaTinte;
    }

}
