package com.example.moisesvargas.hipertin.CapaData;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DataVendedor {
    String codigo,nombre,existente;

    public DataVendedor(String codigo, String nombre, String existente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.existente = existente;
    }

    public DataVendedor() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExistente() {
        return existente;
    }

    public void setExistente(String existente) {
        this.existente = existente;
    }

    public void registrarVendedor(Context context) {
        try {
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("codigo",getCodigo());
            values.put("nombre",getNombre());
            values.put("existente","YES");

            Long idResultante=db.insert("Vendedor","codigo",values);
        }catch (Exception e){
            Toast.makeText(context,"registrar"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
}
