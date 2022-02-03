package com.example.moisesvargas.hipertin.CapaData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSqlite extends SQLiteOpenHelper {

    public ConexionSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear tabla de verdedor
        db.execSQL("CREATE TABLE Vendedor (codigo TEXT,nombre TEXT,existente TEXT)");
        //Crear tabla de productos
        db.execSQL("CREATE TABLE Producto (codigo TEXT,descripcion TEXT,renglon TEXT, precio DOUBLE, itbis Double)");
        //Crear tabla subrenglon
        db.execSQL("CREATE TABLE renglon (codigo TEXT,descripcion TEXT)");
       //Crear tabla de clientes
        db.execSQL("CREATE TABLE Clientes (nombre TEXT,cuenta TEXT,direccion TEXT,vendedor TEXT, dia_visita TEXT, telefono TEXT, limite_credito TEXT, Balance DOUBLE, data TEXT,Contacto TEXT,Sector TEXT)");
        //Crear tabla de detalle de pedido
        db.execSQL("CREATE TABLE Pedido_Detalle (documento TEXT, tipomovi TEXT, codigo TEXT,descripcion TEXT,unidad TEXT,referencia TEXT" +
                ",almacen TEXT,fecha_pedido TEXT,fecha_requerida TEXT,fecha_envio TEXT,fecha_cerrado TEXT,nota TEXT,usuario_pick TEXT," +
                "cantidad DOUBLE,precio DOUBLE,precio2 DOUBLE,precio_lista DOUBLE,precio_especial DOUBLE,costo DOUBLE,descuento DOUBLE," +
                "p_descuento DOUBLE,pedido DOUBLE,itbis DOUBLE,p_itbis DOUBLE,cantidad_sel DOUBLE,cantidad_facturada DOUBLE," +
                "sort INT,incluido INT,cerrado INT,id INT, data TEXT)");
        //Crear tabla de encabezado de pedido
        db.execSQL("CREATE TABLE Pedidos_HDR (documento TEXT, tipomovi TEXT, detalle TEXT, cuenta TEXT, pedido TEXT, nombre TEXT," +
                " direccion TEXT, dir_envio TEXT, vendedor TEXT, almacen TEXT, ruta TEXT, observacion TEXT, usuario_sel TEXT," +
                " usuario_pklist TEXT, estatus TEXT, data TEXT, fecha_pedido TEXT, fecha_requerida TEXT, fecha_envio TEXT," +
                " fecha_sel TEXT, fecha_pklist TEXT,fecha_cerrado TEXT, id INT, condicion INT,ID_pickinglist INT,cerrado INT," +
                " facturaLibre INT, nograva DOUBLE, grava DOUBLE, itbis DOUBLE, descuento DOUBLE, importe DOUBLE," +
                " nograva_sel DOUBLE, grava_sel DOUBLE, itbis_sel DOUBLE, descuento_sel DOUBLE, importe_sel DOUBLE,Sincronizado Boolean)");
        //Crear tabla de Facturas Pendientes
        db.execSQL("CREATE TABLE FacturaPendiente (documento TEXT,tipomovi TEXT,detalle TEXT,fecha TEXT, cuenta TEXT, nombre TEXT, direccion TEXT," +
                " vendedor TEXT, importe Double, balance Double, Vendedor_Cliente TEXT, Data TEXT)");

        //db.execSQL("CREATE TABLE CuentaporCobrar (Transaccion TEXT,Cuenta TEXT, Documento TEXT,Fecha TEXT,Debito DOUBLE, Credito DOUBLE, Balance DOUBLE, Data TEXT)");

        db.execSQL("CREATE TABLE CxC (Transaccion TEXT,Cuenta TEXT, Documento TEXT,Fecha TEXT,Monto DOUBLE, Balance DOUBLE, Data TEXT)");

        db.execSQL("CREATE TABLE UltimaSincronizacion (Fecha TEXT)");
        db.execSQL("Insert into UltimaSincronizacion (Fecha) values ('19000101')");

        db.execSQL("CREATE TABLE Imagen (Imagen TEXT)");
        db.execSQL("Insert into Imagen (Imagen) values ('pash06')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF  EXISTS Producto");
        db.execSQL("DROP TABLE IF  EXISTS Clientes");
//        db.execSQL("DROP TABLE IF  EXISTS FacturaPendiente");
//        db.execSQL("DROP TABLE IF  EXISTS EstadoCuenta");
//        db.execSQL("DROP TABLE IF  EXISTS Pedidos_HDR");
//        db.execSQL("DROP TABLE IF  EXISTS Pedido_Detalle");
//        db.execSQL("DROP TABLE IF  EXISTS subrenglon");
//        db.execSQL("DROP TABLE IF  EXISTS Vendedor");
        onCreate(db);
    }

}
