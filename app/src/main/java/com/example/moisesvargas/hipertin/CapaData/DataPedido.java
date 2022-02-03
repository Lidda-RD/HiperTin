package com.example.moisesvargas.hipertin.CapaData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;


public class DataPedido{

    String documento, tipomovi, detalle, cuenta, pedido, nombre, direccion, dir_envio, vendedor, almacen, ruta,
            observacion, usuario_sel, usuario_pklist, estatus, data;

    Double nograva, grava, itbis, descuento, importe, nograva_sel, grava_sel, itbis_sel, descuento_sel, importe_sel;

    String  fecha_requerida, fecha_envio, fecha_sel, fecha_pklist,fecha_cerrado;

    String fecha_pedido;

    int id, condicion,ID_pickinglist,cerrado, facturaLibre;

    public DataPedido(int id,String nombre, String pedido, Double importe, String fecha_pedido, String direccion, String cuenta) {
        this.id = id;
        this.nombre = nombre;
        this.pedido = pedido;
        this.importe = importe;
        this.fecha_pedido = fecha_pedido;
        this.direccion = direccion;
        this.cuenta = cuenta;
    }

    public DataPedido() {

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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
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

    public String getDir_envio() {
        return dir_envio;
    }

    public void setDir_envio(String dir_envio) {
        this.dir_envio = dir_envio;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuario_sel() {
        return usuario_sel;
    }

    public void setUsuario_sel(String usuario_sel) {
        this.usuario_sel = usuario_sel;
    }

    public String getUsuario_pklist() {
        return usuario_pklist;
    }

    public void setUsuario_pklist(String usuario_pklist) {
        this.usuario_pklist = usuario_pklist;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getNograva() {
        return nograva;
    }

    public void setNograva(Double nograva) {
        this.nograva = nograva;
    }

    public Double getGrava() {
        return grava;
    }

    public void setGrava(Double grava) {
        this.grava = grava;
    }

    public Double getItbis() {
        return itbis;
    }

    public void setItbis(Double itbis) {
        this.itbis = itbis;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getNograva_sel() {
        return nograva_sel;
    }

    public void setNograva_sel(Double nograva_sel) {
        this.nograva_sel = nograva_sel;
    }

    public Double getGrava_sel() {
        return grava_sel;
    }

    public void setGrava_sel(Double grava_sel) {
        this.grava_sel = grava_sel;
    }

    public Double getItbis_sel() {
        return itbis_sel;
    }

    public void setItbis_sel(Double itbis_sel) {
        this.itbis_sel = itbis_sel;
    }

    public Double getDescuento_sel() {
        return descuento_sel;
    }

    public void setDescuento_sel(Double descuento_sel) {
        this.descuento_sel = descuento_sel;
    }

    public Double getImporte_sel() {
        return importe_sel;
    }

    public void setImporte_sel(Double importe_sel) {
        this.importe_sel = importe_sel;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getFecha_requerida() {
        return fecha_requerida;
    }

    public void setFecha_requerida(String fecha_requerida) {
        this.fecha_requerida = fecha_requerida;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public String getFecha_sel() {
        return fecha_sel;
    }

    public void setFecha_sel(String fecha_sel) {
        this.fecha_sel = fecha_sel;
    }

    public String getFecha_pklist() {
        return fecha_pklist;
    }

    public void setFecha_pklist(String fecha_pklist) {
        this.fecha_pklist = fecha_pklist;
    }

    public String getFecha_cerrado() {
        return fecha_cerrado;
    }

    public void setFecha_cerrado(String fecha_cerrado) {
        this.fecha_cerrado = fecha_cerrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public int getID_pickinglist() {
        return ID_pickinglist;
    }

    public void setID_pickinglist(int ID_pickinglist) {
        this.ID_pickinglist = ID_pickinglist;
    }

    public int getCerrado() {
        return cerrado;
    }

    public void setCerrado(int cerrado) {
        this.cerrado = cerrado;
    }

    public int getFacturaLibre() {
        return facturaLibre;
    }

    public void setFacturaLibre(int facturaLibre) {
        this.facturaLibre = facturaLibre;
    }

    String replaceNombre;
    public String insertar(String _data)
    {
        try {
            //Reemplazar apostrofe en nombre por doble apostrofe
            replaceNombre = getNombre().replace("'","''");

            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String insertDatapedioDetalle ="INSERT INTO Pedido_HDR " +
                    " (documento, tipomovi, detalle, cuenta, pedido, nombre, direccion, dir_envio, vendedor, " +
                    " almacen, ruta, observacion, condicion, cerrado, nograva, grava, itbis, descuento, importe," +
                    " fecha_pedido, fecha_requerida, fecha_envio ) " +
                    " VALUES " +
                    " ('"+getDocumento()+"','"+getTipomovi()+"','"+getDetalle()+"','"+getCuenta()+"','"+getPedido()+"'," +
                    " '"+replaceNombre+"','"+getDireccion()+"','"+getDir_envio()+"','"+getVendedor()+"'," +
                    " '"+getAlmacen()+"', '"+getRuta()+"','"+getObservacion()+"',"+getCondicion()+","+getCerrado()+"," +
                    " "+getNograva()+","+getGrava()+","+getItbis()+","+getDescuento()+","+getImporte()+",'"+getFecha_pedido()+"', " +
                    " '"+getFecha_pedido()+"','"+getFecha_pedido()+"')";

//            String insertDatapedioDetalle ="INSERT INTO "+_data+".DBO.Pedido_HDR " +
//                    " (documento, tipomovi, detalle, cuenta, pedido, nombre, direccion, dir_envio, vendedor," +
//                    " almacen, ruta, observacion, " +
//                    " condicion, cerrado, nograva, grava, itbis, descuento, importe,fecha_pedido) " +
//                    " VALUES " +
//                    " ('"+getDocumento()+"','"+getTipomovi()+"','"+getDetalle()+"','"+getCuenta()+"','"+getPedido()+"'," +
//                    " '"+getNombre()+"','"+getDireccion()+"','"+getDir_envio()+"','"+getVendedor()+"','"+getAlmacen()+"'," +
//                    " '"+getRuta()+"','"+getObservacion()+"',"+getCondicion()+"," +
//                    " "+getCerrado()+","+getNograva()+","+getGrava()+","+getItbis()+","+getDescuento()+","+getImporte()+",'"+getFecha_pedido()+"')";

            st.executeUpdate(insertDatapedioDetalle);


        }catch (Exception e)
        {
            throw new RuntimeException("Insert DataPedido " +e.getMessage());
        }
        return null;
    }

    public void consultarPedido(Context context,String numeroPedido,ConexionSqlite conn) {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedidos_HDR WHERE documento = '"+numeroPedido+"'",null);

            if (cursor.moveToNext()) {
                setNombre(cursor.getString(5));
                setCuenta(cursor.getString(3));
                setFecha_pedido(cursor.getString(16));
                setDocumento(cursor.getString(0));
                setImporte(cursor.getDouble(31));


            }
        }catch (Exception e){
            Toast.makeText(context,"Consultar pedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    ArrayList<DataPedido> listaPedido;
    public ArrayList<DataPedido> consultarListaPedio(Context context,ConexionSqlite conn) {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedidos_HDR where Sincronizado ='0'",null);
            listaPedido = new ArrayList<DataPedido>();
            while (cursor.moveToNext()) {

                listaPedido.add(new DataPedido( 1,cursor.getString(5),cursor.getString(0),cursor.getDouble(31),cursor.getString(16),cursor.getString(6), cursor.getString(3)));
            }
        }catch (Exception e){
            Toast.makeText(context,"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return  listaPedido;
    }
}


