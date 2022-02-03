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

public class DataFacturaPendiente {


    String  documento, tipomovi, detalle, fecha, cuenta, nombre, direccion, vendedor, Vendedor_Cliente, Data;
    Double importe,balance,total = 0.0;;
    int id;




    public DataFacturaPendiente() {

    }

    public DataFacturaPendiente(String documento, String tipomovi, String detalle, String fecha, String cuenta, String nombre, String direccion, String vendedor, Double importe, Double balance, String vendedor_Cliente, String Data, int id) {
        this.documento = documento;
        this.tipomovi = tipomovi;
        this.detalle = detalle;
        this.fecha = fecha;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.direccion = direccion;
        this.vendedor = vendedor;
        Vendedor_Cliente = vendedor_Cliente;
        this.importe = importe;
        this.balance = balance;
        this.id = id;
        this.listaFacturaPendiente = listaFacturaPendiente;
        this.Data = Data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipomovi() {
        return tipomovi;
    }

    public void setTipomovi(String tipomovi) {
        this.tipomovi = tipomovi;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getVendedor_Cliente() {
        return Vendedor_Cliente;
    }

    public void setVendedor_Cliente(String vendedor_Cliente) {
        Vendedor_Cliente = vendedor_Cliente;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<DataFacturaPendiente> getListaFacturaPendiente() {
        return listaFacturaPendiente;
    }

    public void setListaFacturaPendiente(ArrayList<DataFacturaPendiente> listaFacturaPendiente) {
        this.listaFacturaPendiente = listaFacturaPendiente;
    }

    public DataFacturaPendiente getFacturaPendiente(Context context){
        DataFacturaPendiente FacturaPendiente = new DataFacturaPendiente();
        try{
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            db.execSQL("DELETE FROM FacturaPendiente");

            final VariablesGlobal variablesGlobal = (VariablesGlobal) context.getApplicationContext();
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String select = "SELECT documento, tipomovi, detalle, fecha, cuenta, nombre, direccion, vendedor, importe," +
                    " importe - balance AS balance, Vendedor_Cliente, 'DATA_001' as Data FROM V_Facturas_Pendientes " +
                    "WHERE (importe - balance > 0 and Vendedor_Cliente = '"+variablesGlobal.getVendedorCodigo()+"')";
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                FacturaPendiente.setDocumento(rs.getString("documento"));
                FacturaPendiente.setTipomovi(rs.getString("tipomovi"));
                FacturaPendiente.setDetalle(rs.getString("detalle"));
                FacturaPendiente.setFecha(rs.getString("fecha"));
                FacturaPendiente.setCuenta(rs.getString("cuenta"));
                FacturaPendiente.setNombre(rs.getString("nombre"));
                FacturaPendiente.setDireccion(rs.getString("direccion"));
                FacturaPendiente.setVendedor(rs.getString("vendedor"));
                FacturaPendiente.setImporte(rs.getDouble("importe"));
                FacturaPendiente.setBalance(rs.getDouble("balance"));
                FacturaPendiente.setVendedor_Cliente(rs.getString("Vendedor_Cliente"));
                FacturaPendiente.setData(rs.getString("DATA"));
                registrarFacturaPendiente(context, FacturaPendiente);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());
        }
        return FacturaPendiente;
    }

    public void registrarFacturaPendiente(Context context, DataFacturaPendiente FacturaPendiente) {
        try {
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("documento",FacturaPendiente.getDocumento());
            values.put("tipomovi",FacturaPendiente.getTipomovi());
            values.put("detalle",FacturaPendiente.getDetalle());
            values.put("fecha",FacturaPendiente.getFecha());
            values.put("cuenta",FacturaPendiente.getCuenta());
            values.put("nombre",FacturaPendiente.getNombre());
            values.put("direccion",FacturaPendiente.getDireccion());
            values.put("vendedor",FacturaPendiente.getVendedor());
            values.put("importe",FacturaPendiente.getImporte());
            values.put("balance",FacturaPendiente.getBalance());
            values.put("Vendedor_Cliente",FacturaPendiente.getVendedor_Cliente());
            values.put("Data",FacturaPendiente.getData());

            db.insert("FacturaPendiente","stringConexion",values);
        }catch (Exception e){
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());
        }
    }

    double _total = 0.0;
    ArrayList<DataFacturaPendiente> listaFacturaPendiente;
    public ArrayList<DataFacturaPendiente> consultarListaFacturaPendiente(Context context, ConexionSqlite conn,String cuenta,String data) {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM FacturaPendiente where cuenta= '"+cuenta+"'",null);
            listaFacturaPendiente =  new ArrayList<DataFacturaPendiente>();
            while (cursor.moveToNext()) {
                listaFacturaPendiente.add(new DataFacturaPendiente(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getDouble(8),cursor.getDouble(9),cursor.getString(10),cursor.getString(11),1));
                _total = _total +  cursor.getDouble(9);
            }
            setTotal(_total);
            cursor.close();
        }catch (Exception e){

            Toast.makeText(context,"consultarListaConeccion "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return  listaFacturaPendiente;
    }
}
