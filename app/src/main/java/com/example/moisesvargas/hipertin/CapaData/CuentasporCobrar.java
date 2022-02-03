package com.example.moisesvargas.hipertin.CapaData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moisesvargas.hipertin.VariablesGlobal;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CuentasporCobrar {
String Transaccion,Documento,Fecha,Data,Cuenta;
int id;
Double Monto,Balance,total;
Date fechaUltimaSincronizacion;
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public CuentasporCobrar(String transaccion, String documento, String fecha,Double monto, Double balance, String cuenta,String data,int id) {
        Transaccion = transaccion;
        Documento = documento;
        Fecha = fecha;
        Monto = monto;
        Balance = balance;
        Cuenta = cuenta;
        Data = data;
        this.id = id;
    }
    public CuentasporCobrar() {

    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMonto() {
        return Monto;
    }

    public void setMonto(Double monto) {
        Monto = monto;
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaccion() {
        return Transaccion;
    }

    public void setTransaccion(String transaccion) {
        Transaccion = transaccion;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public CuentasporCobrar SincronizarCuentaporCobrar(Context context){
        CuentasporCobrar CuentasporCobrar = new CuentasporCobrar();
        try{
            // Variable global vendedor
            final VariablesGlobal variablesGlobal = (VariablesGlobal) context.getApplicationContext();

            // Crear conexio a SQL Server
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String select,fecha = "";

            // Conexion a SQLite
            ConexionSqlite conn = new ConexionSqlite(context,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * FROM UltimaSincronizacion",null);
            if(cursor.moveToNext()){
                //fecha = cursor.getString(0);
                fecha = "20000101";
            }
            PreparedStatement statement = con.prepareStatement("SELECT data,cuenta,documento,detalle,fecha,tipomovi,case " +
                    "                     when substring(tipomovi,2,1)= '1' then importe " +
                    "                     else importe*-1 end as importe " +
                    "                     FROM ( SELECT * from V_Estado_Cuentas WHERE   (vendedor = '"+variablesGlobal.getVendedorCodigo()+"')" +
                    "                     and (fecha > '"+fecha+"') " +
                    "                     ) AS AA " +
                    "                     ORDER BY DATA,CUENTA,FECHA");
            ResultSet rs = statement.executeQuery();
            if(rs!=null && rs.next()) {
                double balance = 0.0, _Monto = 0.0;
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");

                Date ultimaFecha = formatoFecha.parse(fecha);
                String tipomovi, Cuenta = "";
                while (rs.next()) {
                    if (rs.getString("cuenta").equals(Cuenta)) {

                    } else {
                        balance = 0.0;
                        Cuenta = rs.getString("cuenta");
                    }
                    Date fechaRegistroActual = rs.getDate("fecha");
                    if (ultimaFecha.before(fechaRegistroActual)) {
                        ultimaFecha = fechaRegistroActual;
                        db = conn.getWritableDatabase();
                        db.execSQL("update UltimaSincronizacion set fecha = '" + ultimaFecha + "'");
                    }
                    tipomovi = rs.getString("tipomovi").trim();
                    CuentasporCobrar.setData(rs.getString("DATA"));
                    CuentasporCobrar.setDocumento(rs.getString("documento"));
                    CuentasporCobrar.setCuenta(rs.getString("cuenta"));
                    CuentasporCobrar.setTransaccion(rs.getString("detalle"));
                    CuentasporCobrar.setFecha(rs.getString("fecha"));
                    CuentasporCobrar.setMonto(rs.getDouble("importe"));
                    _Monto = rs.getDouble("importe");
                    CuentasporCobrar.setBalance(balance + _Monto);
                    balance = balance + _Monto;
                    registrarEstadoCuenta(CuentasporCobrar, conn);
                }
            }
            //Insertar fecha en ultima sincronizacion

//            ContentValues values = new ContentValues();
//            values.put("Fecha",CuentasporCobrar.getFecha());
        }
        catch (Exception e)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      {
            e.getMessage();
            throw new RuntimeException("Registrar: "+e.getMessage());
        }
        return CuentasporCobrar;
    }

    public void registrarEstadoCuenta(CuentasporCobrar EstadoCuenta,ConexionSqlite conn) {
        try {

            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Transaccion",EstadoCuenta.getTransaccion().trim());
            values.put("Documento",EstadoCuenta.getDocumento().trim());
            values.put("Fecha",EstadoCuenta.getFecha().trim());
            values.put("Monto",EstadoCuenta.getMonto());
            values.put("Balance",EstadoCuenta.getBalance());
            values.put("Data",EstadoCuenta.getData().trim());
            values.put("Cuenta",EstadoCuenta.getCuenta().trim());

            db.insert("CxC","stringConexion",values);
        }catch (Exception e){
            e.getMessage();
            throw new RuntimeException("Registrar Estado Cuenta: "+e.getMessage());
        }
    }

    //ArrayList<DataCliente> listaCliente;
    ArrayList<CuentasporCobrar> listaCuentaporCobrar;
    public ArrayList<CuentasporCobrar> consultarListaEstadoCuenta(Context context, ConexionSqlite conn, String cuenta, String data) {
           SQLiteDatabase db = conn.getReadableDatabase();
           Cursor cursorCXC = db.rawQuery("SELECT * FROM CxC where Cuenta = '"+cuenta+"' order by fecha",null);
           //listaCliente = new ArrayList<>();
           listaCuentaporCobrar =  new ArrayList<>();
           if(cursorCXC!=null && cursorCXC.getCount()>0){
               while (cursorCXC.moveToNext()) {
                   //listaCliente.add(new DataCliente(1,cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));
                   setTotal(cursorCXC.getDouble(5));
                   listaCuentaporCobrar.add(new CuentasporCobrar(cursorCXC.getString(0),cursorCXC.getString(2),cursorCXC.getString(3),cursorCXC.getDouble(4),cursorCXC.getDouble(5),cursorCXC.getString(1),cursorCXC.getString(6),1));
               }
           }else {
               setTotal(0.0);
           }


        return  listaCuentaporCobrar;
        //return listaCliente;
    }
}
