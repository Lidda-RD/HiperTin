package com.example.moisesvargas.hipertin;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.DataCliente;
import com.example.moisesvargas.hipertin.CapaData.DataPedido;
import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Popup extends Activity {

    TextView precioPopup,descripcion,tvImporte;
    Button agregar;
    Producto producto;
    Button suma,resta,salir;
    EditText cantidad;
    int cantida;
    String resultado,codigo,Documento;
    ConexionSqlite conn;
    Double importe,importeFinal=0.0,precio1;
    DataPedido dtPedido;
    String date;
    boolean estaActualizarArticulo,actualizando,borrarArticulo = false;
    Tinte tinte;
    String cantidad1;
    ImageView imgArticuloPopup;
    Double itbis;
    double _monto,_itbis;
    int sort;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);

        precioPopup= findViewById(R.id.precioPopup);
        descripcion= findViewById(R.id.NombredeProducto);
        cantidad = findViewById(R.id.cantidadPopup);
        agregar= findViewById(R.id.btnAgregar);
        suma = findViewById(R.id.btnmas);
        salir = findViewById(R.id.btnSalir);
        resta = findViewById(R.id.btnmenos);
        tvImporte = findViewById(R.id.tvImporte);
        imgArticuloPopup = findViewById(R.id.imgArticuloPopup);
        date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //Suma de cantidad
        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantidad.getText().toString().isEmpty())
                {
                    cantidad.setText("0");
                }

                NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
                cantida = Integer.valueOf(cantidad.getText().toString());
                resultado = String.valueOf(cantida+=1);
                cantidad.setText(resultado);
                CalcularImporte();
                tvImporte.setText("Importe: "+String.valueOf(formatoDinero.format(importe)));
            }
        });

        //Resta de cantidad
        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantida = Integer.valueOf(cantidad.getText().toString());
                if(cantida >= 1) {
                    if(cantidad.getText().toString().isEmpty())
                    {
                        cantidad.setText("0");
                    }
                    NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
                    resultado = String.valueOf(cantida -= 1);
                    cantidad.setText(resultado);
                    CalcularImporte();
                    tvImporte.setText("Importe: "+String.valueOf(formatoDinero.format(importe)));
                }
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Click en enter de teclado virtual
        cantidad.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Calling Application class (see application tag in AndroidManifest.xml)
                    final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
                    Documento = variablesGlobal.getDocumento();
                    if(Documento == null || Documento.isEmpty())
                    {
                        buscarUltimoPedido();
                        if (Documento == null || Documento.isEmpty()){
                            Documento = "1";
                            variablesGlobal.setDocumento(Documento);
                        }else {
                            Documento = String.valueOf(Integer.valueOf(Documento) + 1) ;
                            variablesGlobal.setDocumento(Documento);
                        }
                        Insertarpedido();
                        InsertarDetallepedido();
                        Toast.makeText(getApplicationContext(),"Pedido Guardado.." + Documento.trim(),Toast.LENGTH_SHORT).show();
                    }else {
                        actualizando = true;
                        consultarArticuloDetallePedido();
                        if(estaActualizarArticulo == true)
                        {
                            ActualizarDetallepedido();
                            ActualizarPedido();
                            Toast.makeText(getApplicationContext(),"Pedido Actualizado",Toast.LENGTH_SHORT).show();
                        }else {
                            //buscarUltimoPedido();
                            InsertarDetallepedido();
                            ActualizarPedido();
                            Toast.makeText(getApplicationContext(),"Pedido Guardado",Toast.LENGTH_SHORT).show();
                        }
                    }
                    finish();
                    return true;
                }
                return false;
            }
        });



        //Resivir parametos de producto
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);

        Double precio = getIntent().getExtras().getDouble("precio");
        precioPopup.setText("Precio: "+formatoDinero.format(precio));
        precio1=Double.valueOf(precio);

        cantidad1 = getIntent().getExtras().getString("cantidad");
        cantidad.setText(cantidad1);

        if (cantidad1.equals("0"))
        {
            cantidad.setText("1");
            cantida = 1;
        }


        String descripciondePedido = getIntent().getExtras().getString("descripcion");
        descripcion.setText(descripciondePedido.trim());
        itbis = getIntent().getExtras().getDouble("Itbis");

        CalcularImporte();
        tvImporte.setText("Importe: "+String.valueOf(formatoDinero.format(importe)));

        String direccionImagen = getIntent().getExtras().getString("imagen");
        Uri myUri = (Uri.parse(direccionImagen));
        imgArticuloPopup.setImageURI(myUri);
        codigo = getIntent().getExtras().getString("codigo");

        //click en boton de afregar pedido
        agregar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // Calling Application class (see application tag in AndroidManifest.xml)
                    final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
                    Documento = variablesGlobal.getDocumento();
                    // Toast.makeText(getApplicationContext(),"Numero de documento"+Documento,Toast.LENGTH_SHORT).show();
                    if(Documento == null || Documento.isEmpty())
                    {
                        if(cantida == 0 ){
                        }
                        else{
                            buscarUltimoPedido();
                            if (Documento == null || Documento.isEmpty()){
                                Documento = "1";
                                variablesGlobal.setDocumento(Documento);
                            }else {
                                Documento = String.valueOf(Integer.valueOf(Documento) + 1) ;
                                variablesGlobal.setDocumento(Documento);
                            }
                                Insertarpedido();
                                InsertarDetallepedido();
                                Toast.makeText(getApplicationContext(),"Pedido Guardado",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        consultarArticuloDetallePedido();
                        if(estaActualizarArticulo == true)
                        {
                            if(cantida == 0 ){
                                borrarArticulo = true;
                            }
                            ActualizarDetallepedido();
                            ActualizarPedido();
                            Toast.makeText(getApplicationContext(),"Pedido Actualizado",Toast.LENGTH_SHORT).show();
                        }else {
                            if(cantida == 0 ){
                                Toast.makeText(getApplicationContext(),"Pedido no Guardado",Toast.LENGTH_SHORT).show();
                            }else {
                                //buscarUltimoPedido();
                                InsertarDetallepedido();
                                ActualizarPedido();
                                Toast.makeText(getApplicationContext(),"Pedido Guardado",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                finish();
            }
        });

//        //Asignar tama;o del popup
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int heigth = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(heigth*.6));
    }

    String nombreCliente,Cuenta,Direccion,Vendedor,Telefono,Data;
    //buscar datos del cliente en tabla Clientes;
    private void consultarCliente() {
        final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
        Cuenta = variablesGlobal.getCuenta();

        DataCliente dtCliente = new DataCliente();
        dtCliente.consultarCliente(getApplicationContext(),Cuenta,conn);
        nombreCliente = dtCliente.getNombre();
        Direccion = dtCliente.getDireccion();
        Vendedor = dtCliente.getVendedor();
        Telefono = dtCliente.getTelefono();
        Data = dtCliente.getData();
//        try {
//            SQLiteDatabase db = conn.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM Clientes WHERE cuenta = '"+Cuenta+"'",null);
//
//            if (cursor.moveToNext()) {
//
//                nombreCliente = cursor.getString(0);
//                Direccion = cursor.getString(2);
//                Vendedor = cursor.getString(3);
//                Telefono = cursor.getString(4);
//                Data = cursor.getString(7);
//            }
//
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),"ConsultarClientes: "+e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
    }
    //Insertar datos en pedido.
    private void Insertarpedido() {
        try {
            consultarCliente();
            ConexionSqlite conn = new ConexionSqlite(this,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            CalcularImporte();
            ContentValues values = new ContentValues();
            values.put("documento",Documento);
            values.put("tipomovi","130001");
            values.put("detalle","ORDEN DE PEDIDO");
            values.put("cuenta",Cuenta);
            values.put("pedido",Documento);
            values.put("nombre",nombreCliente);
            values.put("direccion",Direccion);
            values.put("dir_envio",Direccion);
            values.put("vendedor",Vendedor);
            values.put("almacen","001");
            values.put("ruta","");
            values.put("observacion","");
            values.put("usuario_sel", Vendedor);
            values.put("usuario_pklist",Vendedor);
            values.put("estatus","ABIERTO");
            values.put("data",Data);
            values.put("fecha_pedido",date);
            values.put("id",1);
            values.put("condicion",0);
            values.put("ID_pickinglist",0);
            values.put("cerrado",1);
            values.put("facturaLibre",1);
            values.put("nograva",0.0);
            values.put("grava",importe - _itbis);
            values.put("itbis",_itbis);
            values.put("descuento",0.0);
            values.put("importe",importe);
            values.put("nograva_sel",0.0);
            values.put("grava_sel",0.0);
            values.put("itbis_sel",itbis);
            values.put("descuento_sel",0.0);
            values.put("importe_sel",0.0);
            values.put("Sincronizado",false);
            //values.put("data",);

            Long idResultante=db.insert("Pedidos_HDR","codigo",values);

            // db.execSQL("DROP TABLE IF  EXISTS Producto");
            // db.execSQL("UPDATE Producto SET categoria='Tinte' WHERE categoria = '0' ");
            // db.execSQL("DELETE FROM Producto WHERE categoria='Producto' ");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"EncabezadoPedido"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    // Buscar la secuencia del ultimo pedido;
    private void buscarUltimoPedido() {
        try {
            // Calling Application class (see application tag in AndroidManifest.xml)
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedidos_HDR ORDER BY CAST(DOCUMENTO AS INT) DESC LIMIT 1",null);
            dtPedido = new DataPedido();
            if (cursor.moveToNext()) {
                Documento = cursor.getString(0);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"BuscarUltipçmoPedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Insertar datos en detalle de pedido.
    private void InsertarDetallepedido() {
        try {
            ConexionSqlite conn = new ConexionSqlite(this,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("documento", Documento);
            values.put("tipomovi","130001");
            values.put("codigo",codigo);
            values.put("descripcion",descripcion.getText().toString());
            values.put("unidad"," ");
            values.put("referencia"," ");
            values.put("almacen","");
            values.put("fecha_pedido",date);
            values.put("fecha_requerida",date);
            values.put("fecha_envio",date);
            values.put("fecha_cerrado",date);
            values.put("nota","ninguna");
            values.put("usuario_pick", Vendedor);
            values.put("cantidad",cantidad.getText().toString());
            values.put("precio",precio1.toString());
            values.put("precio2",0.0);
            values.put("precio_lista",0.0);
            values.put("precio_especial",0.0);
            values.put("costo",0.0);
            values.put("descuento",0.0);
            values.put("p_descuento",0.0);
            values.put("pedido",0.0);
            values.put("itbis",_itbis);
            values.put("p_itbis",itbis);
            values.put("cantidad_sel",0.0);
            values.put("cantidad_facturada",0.0);
            consultarImporteDetallePedido();
            values.put("sort",sort);
            values.put("incluido",0.0);
            values.put("cerrado",0.0);
            values.put("id",1);


            Long idResultante=db.insert("Pedido_Detalle","codigo",values);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"DetallePedido"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void ActualizarDetallepedido() {
        try {
            ConexionSqlite conn = new ConexionSqlite(this,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            if(borrarArticulo == true)
            {
                db.execSQL("Delete from Pedido_Detalle WHERE codigo='"+codigo+"' AND documento='"+Documento+"' ");
                Toast.makeText(getApplicationContext(),"Articulo Eliminado",Toast.LENGTH_SHORT).show();
            }else{
                CalcularImporte();
                db.execSQL("UPDATE Pedido_Detalle SET cantidad='"+cantidad.getText().toString()+"', itbis='"+_itbis+"' WHERE codigo='"+codigo+"' AND documento='"+Documento+"' ");
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ActualizarDetalle: "+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
    //Actualizar el importe del pedido
    private void ActualizarPedido() {
        try {
            consultarImporteDetallePedido();
            consultarCliente();
            CalcularImporte();
            ConexionSqlite conn = new ConexionSqlite(this,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();
            Double grava = importeFinal - _itbis;
            db.execSQL("UPDATE Pedidos_HDR SET importe='"+importeFinal+"', itbis='"+_itbis+"', grava='"+grava+"' WHERE documento='"+Documento+"' ");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ActualizarEncabezadoPedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    //Consultar y calcular el importe del detalle
    private void consultarImporteDetallePedido() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedido_Detalle WHERE documento = '"+Documento+"'",null);
            sort = 1;
            while (cursor.moveToNext()) {
                importeFinal = importeFinal + cursor.getDouble(13) * cursor.getDouble(14);
                _itbis = _itbis +  cursor.getDouble(22);
                sort = sort + 1;
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ConsultarImporteDetallePedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Consultar si el articulo existe en ese pedido
    private void consultarArticuloDetallePedido() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedido_Detalle WHERE documento = '"+Documento+"' AND codigo='"+codigo+"'",null);

            if (cursor.moveToNext()) {
                estaActualizarArticulo = true;
            }else {
                estaActualizarArticulo = false;
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ConsultarDetallePedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Calcular el importe
    public void CalcularImporte()
    {
        importe = cantida * precio1;
        _monto = importe / (1 + (itbis /100 )) ;
        _itbis = _monto * itbis / 100;
    }
}
