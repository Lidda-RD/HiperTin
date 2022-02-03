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

public class DataCliente {
    private int id;
    private String nombre;
    private String cuenta;
    private String direccion;
    private String vendedor;
    private  String dia_visita;
    private String telefono;
    ConexionSqlite conn;
    private String data;
    private String limite_credito;
    private Double balance;
    private String Contacto;
    private String Sector;
   // DataFacturaPendiente facturaPendiente;
    public DataCliente(int id, String nombre, String cuenta, String direccion, String vendedor, String dia_visita, String telefono, String limite_credito, Double balance, String data, String Contacto,String sector) {
        this.id = id;
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.direccion = direccion;
        this.vendedor = vendedor;
        this.dia_visita = dia_visita;
        this.telefono = telefono;
        this.limite_credito = limite_credito;
        this.balance = balance;
        this.data = data;
        this.Contacto = Contacto;
        this.Sector = sector;
    }

    public DataCliente() {

    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public String getDia_visita() {
        return dia_visita;
    }

    public void setDia_visita(String dia_visita) {
        this.dia_visita = dia_visita;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLimite_credito() {
        return limite_credito;
    }

    public void setLimite_credito(String limite_credito) {
        this.limite_credito = limite_credito;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public DataCliente getCliente(Context context)
    {
        DataCliente Cliente = new DataCliente();
        try{
                ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
                SQLiteDatabase db = conn.getWritableDatabase();
            db.execSQL("DELETE FROM Clientes");

            final VariablesGlobal variablesGlobal = (VariablesGlobal) context.getApplicationContext();
            Connection con = Conexion.ConexionLocal();
            //Connection con2 = Conexion.ConexionLocal();
            //Statement stBalance = con2.createStatement();
            Statement st =  con.createStatement();
            String select = "SELECT 1 as fila, *,'DATA_002' as DATA from V_CLIENTE_BALANCE WHERE (VENDEDOR = '"+variablesGlobal.getVendedorCodigo()+"')" +
                    " order by CUENTA asc";
//            String select = "SELECT fila, CUENTA, ESTABLECIMIENTO, DIRECCION, VENDEDOR, TELEFONOS, SECTOR, DATA " +
//                    " FROM (SELECT 1 AS fila, CLIENTE.CUENTA, CLIENTE.VENDEDOR, CLIENTE.ESTABLECIMIENTO, CLIENTE.DIRECCION, CLIENTE.TELEFONOS, cliente02.SECTOR, 'DATA_001' AS DATA" +
//                    " FROM CLIENTE INNER JOIN" +
//                    " cliente02 ON CLIENTE.CUENTA = cliente02.CUENTA" +
//                    " WHERE (CLIENTE.VENDEDOR = '"+variablesGlobal.getVendedorCodigo()+"')" +
//                    " UNION" +
//                    " SELECT 1 AS fila, CLIENTE_1.CUENTA, CLIENTE_1.VENDEDOR, CLIENTE_1.ESTABLECIMIENTO, CLIENTE_1.DIRECCION, CLIENTE_1.TELEFONOS, cliente02_1.SECTOR, 'DATA_002' AS DATA" +
//                    " FROM Data_002.dbo.CLIENTE AS CLIENTE_1 INNER JOIN" +
//                    " Data_002.dbo.cliente02 AS cliente02_1 ON CLIENTE_1.CUENTA = cliente02_1.CUENTA" +
//                    " WHERE (CLIENTE_1.VENDEDOR = '"+variablesGlobal.getVendedorCodigo()+"')) AS derivedtbl_1" +
//                    " ORDER BY STR(CUENTA)";
            ResultSet rs = st.executeQuery(select);
//            ResultSet rsBalance;
            while (rs.next()) {
//                String selectBalance = "SELECT TOP (1) cuenta, nombre, SUM(balance) AS BALANCE" +
//                        " FROM (SELECT cuenta, nombre, importe - ISNULL" +
//                        " ((SELECT SUM(abono + descuento) AS Expr1" +
//                        " FROM TRANSAC02" +
//                        " WHERE (tipomovi1 = TRANSAC01.tipomovi) AND (documento1 = TRANSAC01.documento)), 0) AS balance" +
//                        " FROM TRANSAC01" +
//                        " WHERE (tipomovi = '210001')) AS derivedtbl_1" +
//                        " WHERE (balance > 0)" +
//                        " GROUP BY cuenta, nombre";
//                rsBalance = stBalance.executeQuery(selectBalance);
//                if(rsBalance.next())
//                {
//                    Cliente.setBalance(rsBalance.getString("BALANCE"));
//                }
                Cliente.setBalance(rs.getDouble("BALANCE"));
                if (Cliente.getBalance() != null)
                {
                    if(Double.valueOf(Cliente.getBalance()) < 0 ){
                        Cliente.setBalance(0.0);
                    }
                }
                else {
                    Cliente.setBalance(0.0);
                }
                Cliente.setCuenta(rs.getString("CUENTA"));
                variablesGlobal.setCuenta(Cliente.getCuenta());
                Cliente.setNombre(rs.getString("Establecimiento"));
                Cliente.setDireccion(rs.getString("Direccion"));
                Cliente.setVendedor(rs.getString("Vendedor"));
                Cliente.setDia_visita(rs.getString("DIA_VISITA"));
                Cliente.setTelefono(rs.getString("Telefonos"));
                Cliente.setLimite_credito(rs.getString("Limite_credito"));
                Cliente.setSector(rs.getString("Ciudad").trim());
                Cliente.setData(rs.getString("data"));
                Cliente.setContacto(rs.getString("CONTACTO"));
//                registrarClientes(context, Cliente);
                //Insertar clientes en SQLITE
                ContentValues values = new ContentValues();
                values.put("cuenta",Cliente.getCuenta());
                values.put("nombre",Cliente.getNombre());
                values.put("direccion",Cliente.getDireccion());
                values.put("vendedor",Cliente.getVendedor());
                values.put("dia_visita",Cliente.getDia_visita().trim());
                values.put("telefono",Cliente.getTelefono());
                values.put("limite_credito",Cliente.getLimite_credito());
                values.put("balance",Cliente.getBalance());
                values.put("data",Cliente.getData());
                values.put("Contacto",Cliente.getContacto());
                values.put("Sector",Cliente.getSector());
                db.insert("Clientes","cuenta",values);
          }
        }
        catch (Exception e)
        {
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());
        }
        return Cliente;
    }

    public void consultarCliente(Context context, String cuentaCliente, ConexionSqlite cone) {
        try {
            SQLiteDatabase db = cone.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Clientes where cuenta='"+cuentaCliente+"' ",null);
            if (cursor.moveToNext()) {
                setNombre(cursor.getString(0));
                setCuenta(cursor.getString(1));
                setDireccion(cursor.getString(2));
                setVendedor(cursor.getString(3));
                setTelefono(cursor.getString(4));
                setData(cursor.getString(8));
                setContacto(cursor.getString(9));
                setSector(cursor.getString(10));
            }
        }catch (Exception e){
            Toast.makeText(context,"Consltar clientes: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    ArrayList<DataCliente> listaCliente;
    public ArrayList<DataCliente>consultarListaCliente(Context context, ConexionSqlite cone,int dia) {

        SQLiteDatabase db = cone.getReadableDatabase();
        // Cursor cursor = db.rawQuery("SELECT * FROM Clientes order by cuenta asc ",null);
        Cursor cursor;
        if(dia == 0)
        {
            cursor = db.rawQuery("SELECT * FROM Clientes order by cuenta asc ",null);
        }
        else{
            cursor = db.rawQuery("SELECT * FROM Clientes where dia_visita = '"+dia+"' and Balance > 0 order by cuenta asc ",null);
        }
        listaCliente = new ArrayList<>();
        while (cursor.moveToNext()) {

            listaCliente.add(new DataCliente(1,cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getDouble(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
        }
        return listaCliente;
    }
//    public String insertar()
//    {
//        try {
//
//            Connection con = Conexion.ConexionLocal();
//            Statement st =  con.createStatement();
//            String insertDatapedioDetalle ="INSERT INTO Cliente (Nombre, Cuenta, Direccion, Vendedor, Telefono) " +
//                    "VALUES ('"+getNombre()+"','"+getCuenta()+"','"+getDireccion()+"','"+getVendedor()+"','"+getTelefono()+"')";
//
//            st.executeUpdate(insertDatapedioDetalle);
//
//
//        }catch (Exception e)
//        {
//            e.getMessage();
//            throw new RuntimeException("Verso: "+e.getMessage());
//
//        }
//        return null;
//    }
}
