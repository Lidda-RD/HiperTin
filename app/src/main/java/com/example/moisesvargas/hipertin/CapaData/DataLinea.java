package com.example.moisesvargas.hipertin.CapaData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.VariablesGlobal;

import java.util.ArrayList;

public class DataLinea {
    int idProducto;
    String codigo,descripcion,renglon,cantidad;
    Double precio, itbis;

    public DataLinea(int idProducto, String codigo, String descripcion, String renglon, Double precio,String cantidad, Double itbis) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.renglon = renglon;
        this.precio = precio;
        this.cantidad = cantidad;
        this.itbis = itbis;
    }

    public DataLinea() {

    }

    public Double getItbis() {
        return itbis;
    }

    public void setItbis(Double itbis) {
        this.itbis = itbis;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    ArrayList<DataLinea> listaLinea;
    public ArrayList<DataLinea> consultarListaLinea(Context context, ConexionSqlite conn,String _subrenglon) {
        try {
            final VariablesGlobal variablesGlobal = (VariablesGlobal) context;

            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT *, ifnull((select sum(cantidad) from Pedido_Detalle where codigo = Producto.codigo and documento ='"+variablesGlobal.getDocumento()+"' ),0) As Cantidad FROM Producto WHERE subrenglon = '"+_subrenglon+"' AND trim(renglon) = 'PRODUCTOS' ORDER BY descripcion ASC ",null);
            listaLinea = new ArrayList<DataLinea>();
            while (cursor.moveToNext()) {
                listaLinea.add(new DataLinea(1,cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3),cursor.getString(6),cursor.getDouble(5)));
            }

        }catch (Exception e){
            Toast.makeText(context,"Linea"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return  listaLinea;
    }
}
