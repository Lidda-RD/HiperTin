package com.example.moisesvargas.hipertin.CapaData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class DataPedidoDetalle{

    String documento,tipomovi,codigo,descripcion,unidad,referencia,almacen,fecha_pedido,fecha_requerida,fecha_envio,fecha_cerrado,nota,usuario_pick,data;
    Double cantidad,precio,precio2,precio_lista,precio_especial,costo,descuento,p_descuento,pedido,itbis,p_itbis,cantidad_sel,cantidad_facturada;
    int sort,incluido,cerrado,id;



    public DataPedidoDetalle(int id,String codigo, Double cantidad, Double precio, String descripcion,String data) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.id = id;
        this.data = data;
    }

    public DataPedidoDetalle(String documento, String tipomovi, String codigo, String descripcion, String unidad, String referencia, String almacen, String fecha_pedido, String fecha_requerida, String fecha_envio, String fecha_cerrado, String nota, String usuario_pick, Double cantidad, Double precio, Double precio2, Double precio_lista, Double precio_especial, Double costo, Double descuento, Double p_descuento, Double pedido, Double itbis, Double p_itbis, Double cantidad_sel, Double cantidad_facturada, int sort, int incluido, int cerrado, int id) {
        this.documento = documento;
        this.tipomovi = tipomovi;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.referencia = referencia;
        this.almacen = almacen;
        this.fecha_pedido = fecha_pedido;
        this.fecha_requerida = fecha_requerida;
        this.fecha_envio = fecha_envio;
        this.fecha_cerrado = fecha_cerrado;
        this.nota = nota;
        this.usuario_pick = usuario_pick;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precio2 = precio2;
        this.precio_lista = precio_lista;
        this.precio_especial = precio_especial;
        this.costo = costo;
        this.descuento = descuento;
        this.p_descuento = p_descuento;
        this.pedido = pedido;
        this.itbis = itbis;
        this.p_itbis = p_itbis;
        this.cantidad_sel = cantidad_sel;
        this.cantidad_facturada = cantidad_facturada;
        this.sort = sort;
        this.incluido = incluido;
        this.cerrado = cerrado;
        this.id = id;
    }

    public DataPedidoDetalle() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
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

    public String getFecha_cerrado() {
        return fecha_cerrado;
    }

    public void setFecha_cerrado(String fecha_cerrado) {
        this.fecha_cerrado = fecha_cerrado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getUsuario_pick() {
        return usuario_pick;
    }

    public void setUsuario_pick(String usuario_pick) {
        this.usuario_pick = usuario_pick;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(Double precio2) {
        this.precio2 = precio2;
    }

    public Double getPrecio_lista() {
        return precio_lista;
    }

    public void setPrecio_lista(Double precio_lista) {
        this.precio_lista = precio_lista;
    }

    public Double getPrecio_especial() {
        return precio_especial;
    }

    public void setPrecio_especial(Double precio_especial) {
        this.precio_especial = precio_especial;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getP_descuento() {
        return p_descuento;
    }

    public void setP_descuento(Double p_descuento) {
        this.p_descuento = p_descuento;
    }

    public Double getPedido() {
        return pedido;
    }

    public void setPedido(Double pedido) {
        this.pedido = pedido;
    }

    public Double getItbis() {
        return itbis;
    }

    public void setItbis(Double itbis) {
        this.itbis = itbis;
    }

    public Double getP_itbis() {
        return p_itbis;
    }

    public void setP_itbis(Double p_itbis) {
        this.p_itbis = p_itbis;
    }

    public Double getCantidad_sel() {
        return cantidad_sel;
    }

    public void setCantidad_sel(Double cantidad_sel) {
        this.cantidad_sel = cantidad_sel;
    }

    public Double getCantidad_facturada() {
        return cantidad_facturada;
    }

    public void setCantidad_facturada(Double cantidad_facturada) {
        this.cantidad_facturada = cantidad_facturada;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIncluido() {
        return incluido;
    }

    public void setIncluido(int incluido) {
        this.incluido = incluido;
    }

    public int getCerrado() {
        return cerrado;
    }

    public void setCerrado(int cerrado) {
        this.cerrado = cerrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String insertar(String _data){
        try {

            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String insertDatapedioDetalle ="INSERT INTO Pedido_Detail " +
                    " (documento, tipomovi, codigo, descripcion, unidad, referencia, cantidad, precio, precio2, " +
                    " descuento,costo, p_descuento,incluido,fecha_pedido, itbis, p_itbis, sort) " +
                    " VALUES " +
                    " ('"+getDocumento()+"','"+getTipomovi()+"','"+getCodigo()+"','"+getDescripcion()+"','"+getUnidad()+"', " +
                    " '"+getReferencia()+"','"+getCantidad()+"','"+getPrecio()+"','"+getPrecio2()+"','"+getDescuento()+"', " +
                    " '"+getCosto()+"','"+getP_descuento()+"','"+getIncluido()+"','"+getFecha_pedido()+"', "+getItbis()+", "+getP_itbis()+", "+getSort()+")";

            st.executeUpdate(insertDatapedioDetalle);


        }catch (Exception e)
        {
            e.getMessage();
            throw new RuntimeException("Detalle: "+e.getMessage());

        }
        return null;
    }

    ArrayList<DataPedidoDetalle> listaDetalle;
    public ArrayList<DataPedidoDetalle> consultarListaDetallePedido(Context context, String numeroPedido, ConexionSqlite conn) {
        try {
            listaDetalle = new ArrayList<DataPedidoDetalle>();
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedido_Detalle WHERE documento = '"+numeroPedido+"'",null);

            while (cursor.moveToNext()) {

                listaDetalle.add(new DataPedidoDetalle(1,cursor.getString(2),cursor.getDouble(13),cursor.getDouble(14),cursor.getString(3),cursor.getString(30)));

            }

        }catch (Exception e){
            Toast.makeText(context,"Consultar detalle: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return  listaDetalle;
    }
}
