package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.CuentasporCobrar;
import com.example.moisesvargas.hipertin.CapaData.DataCliente;
import com.example.moisesvargas.hipertin.CapaData.DataFacturaPendiente;
import com.example.moisesvargas.hipertin.CapaData.DataMovimiento;
import com.example.moisesvargas.hipertin.CapaData.DataPedido;
import com.example.moisesvargas.hipertin.CapaData.DataPedidoDetalle;
import com.example.moisesvargas.hipertin.CapaData.DataSubrenglon;
import com.example.moisesvargas.hipertin.CapaData.DataTinte;
import com.example.moisesvargas.hipertin.CapaData.DataVendedor;

import java.util.ArrayList;

public class PopupSincronizacion extends AppCompatActivity {
    ConexionSqlite conn;
    ArrayList<DataCliente> listaCliente;
    DataCliente dtCliente;
    DataPedido dtPedido;
    DataPedidoDetalle dtPedidoDetalle;
    Button btnSincronizar;
    CheckBox cbsincronizarPedido,cbsincronizarCategorias,cbsincronizarCliente,cbsincronizarCuentasXCobrar;
    ProgressBar progresoPopup;
    EditText edtNombreVendedor,edtCodigoVendedor,edtPin;
    String _data;
    DataMovimiento dtMovimiento;
    CuentasporCobrar cuentasporCobrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_sincronizacion);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);
        listaCliente = new ArrayList<DataCliente>();

        cbsincronizarPedido = findViewById(R.id.cbsincronizarPedido);
        cbsincronizarCategorias = findViewById(R.id.cbsincronizarCategorias);
        cbsincronizarCliente = findViewById(R.id.cbsincronizarCliente);
        cbsincronizarCuentasXCobrar = findViewById(R.id.cbsincronizarCuentasXCobrar);
        btnSincronizar = findViewById(R.id.btnsincronizar);
        progresoPopup = findViewById(R.id.progresoPopup);
        progresoPopup.setMax(100);
        progresoPopup.setProgress(0);
        edtCodigoVendedor = findViewById(R.id.edtCodigoVendedor);
        edtNombreVendedor = findViewById(R.id.edtVendedor);
        edtPin = findViewById(R.id.edtPin);
        consultarVendedor();

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
                if (edtNombreVendedor.getText() != null && !edtNombreVendedor.getText().toString().isEmpty())
                {
                    SQLiteDatabase db = conn.getWritableDatabase();
                    db.execSQL("DELETE FROM Vendedor");
                    db.execSQL("DELETE FROM Clientes");
                    db.execSQL("DELETE FROM Producto");
                    db.execSQL("DELETE FROM renglon");
                    db.execSQL("DELETE FROM Pedido_Detalle");
                    db.execSQL("DELETE FROM Pedidos_HDR");
                    db.execSQL("DELETE FROM FacturaPendiente");
                    db.execSQL("DELETE FROM CxC");
                    variablesGlobal.setExistente(null);
                }

                try{
                    if(variablesGlobal.getExistente() == null){
                        registrarVendedor();
                        finish();
                    }else {

                        AsyncTaskCargaDatos ATCargaDatos = new AsyncTaskCargaDatos(PopupSincronizacion.this);
                        ATCargaDatos.execute();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        edtPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(edtPin.length() >= 4) {
                    if(edtPin.getText().toString().equals("9999")) {
                        cbsincronizarPedido.setVisibility(View.INVISIBLE);
                        cbsincronizarCliente.setVisibility(View.INVISIBLE);
                        cbsincronizarCategorias.setVisibility(View.INVISIBLE);
                        cbsincronizarCuentasXCobrar.setVisibility(View.INVISIBLE);
                        edtPin.setVisibility(View.INVISIBLE);

                        edtNombreVendedor.setVisibility(View.VISIBLE);
                        edtCodigoVendedor.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void SincronizarCliente() {
        try {
            Context context = getApplicationContext();
            new DataCliente().getCliente(context);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    //Sincronizar Cuetas por Cobrar
    private void SincronizarCxC() {
        try {
            Context context = getApplicationContext();
            new DataFacturaPendiente().getFacturaPendiente(context);

            cuentasporCobrar = new CuentasporCobrar().SincronizarCuentaporCobrar(context);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void SincronizarPedido() {
        try {

            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedidos_HDR where Sincronizado ='0'",null);
            String documentoLocal,documentoServer;
            while (cursor.moveToNext()) {
                _data = cursor.getString(15);
                dtMovimiento = new DataMovimiento().getMovimiento(_data,"2");

                dtPedido = new DataPedido();
                dtPedido.setDocumento(dtMovimiento.getSecuencia());
                documentoLocal = cursor.getString(0);
                documentoServer = (dtMovimiento.getSecuencia());
                dtPedido.setTipomovi(cursor.getString(1));
                dtPedido.setDetalle(cursor.getString(2));
                dtPedido.setCuenta(cursor.getString(3));
                dtPedido.setPedido(cursor.getString(4));
                dtPedido.setNombre(cursor.getString(5));
                dtPedido.setDireccion(cursor.getString(6));
                dtPedido.setDir_envio(cursor.getString(7));
                dtPedido.setVendedor(cursor.getString(8));
                dtPedido.setAlmacen(cursor.getString(9));
                dtPedido.setRuta(cursor.getString(10));
                dtPedido.setObservacion(cursor.getString(11));
                dtPedido.setUsuario_sel(cursor.getString(12));
                dtPedido.setUsuario_pklist(cursor.getString(13));
                dtPedido.setEstatus(cursor.getString(14));
                dtPedido.setFecha_pedido(cursor.getString(16));
                dtPedido.setId(cursor.getInt(17));
                dtPedido.setCondicion(cursor.getInt(18));
                dtPedido.setID_pickinglist(cursor.getInt(19));
                dtPedido.setCerrado(cursor.getInt(20));
                dtPedido.setFacturaLibre(cursor.getInt(21));
                dtPedido.setNograva(cursor.getDouble(22));
                dtPedido.setGrava(cursor.getDouble(28));
                dtPedido.setItbis(cursor.getDouble(29));
                dtPedido.setDescuento(cursor.getDouble(25));
                dtPedido.setImporte(cursor.getDouble(31));
                dtPedido.setNograva_sel(cursor.getDouble(27));
                dtPedido.setGrava_sel(cursor.getDouble(28));
                dtPedido.setItbis_sel(cursor.getDouble(29));
                dtPedido.setDescuento_sel(cursor.getDouble(30));
                dtPedido.setImporte_sel(cursor.getDouble(31));
                dtPedido.setData(cursor.getString(15));
                dtPedido.insertar(_data);
                dtMovimiento.ActualizarSecuencia(_data);
                db.execSQL("UPDATE Pedidos_HDR SET Sincronizado = 'true' WHERE documento = '"+documentoLocal+"' ");
                SincronizarPedidoDetalle(documentoLocal, documentoServer);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void SincronizarPedidoDetalle(String documentoLocal,String documentoServer) {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pedido_Detalle where documento='"+documentoLocal+"'",null);
            while (cursor.moveToNext()) {
                dtPedidoDetalle = new DataPedidoDetalle();
                dtPedidoDetalle.setDocumento(documentoServer);
                dtPedidoDetalle.setTipomovi(cursor.getString(1));
                dtPedidoDetalle.setCodigo(cursor.getString(2));
                dtPedidoDetalle.setDescripcion(cursor.getString(3));
                dtPedidoDetalle.setUnidad(cursor.getString(4));
                dtPedidoDetalle.setReferencia(cursor.getString(5));
                dtPedidoDetalle.setCantidad(cursor.getDouble(13));
                dtPedidoDetalle.setPrecio(cursor.getDouble(14));
                dtPedidoDetalle.setPrecio2(cursor.getDouble(15));
                dtPedidoDetalle.setDescuento(cursor.getDouble(19));
                dtPedidoDetalle.setCosto(cursor.getDouble(18));
                dtPedidoDetalle.setP_descuento(cursor.getDouble(20));
                dtPedidoDetalle.setIncluido(cursor.getInt(27));
                dtPedidoDetalle.setFecha_pedido(cursor.getString(7));
                dtPedidoDetalle.setData(cursor.getString(30));
                dtPedidoDetalle.setItbis(cursor.getDouble(22));
                dtPedidoDetalle.setP_itbis(cursor.getDouble(23));
                dtPedidoDetalle.setSort(cursor.getInt(26));
                dtPedidoDetalle.insertar(_data);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void SincronizarArticulos() {
        try {
            Context context = getApplicationContext();
            new DataTinte().getArticulo(context);

            new DataSubrenglon().getSubrenglon(context);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Id Registro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void consultarVendedor() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Vendedor",null);
            final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();

            if (cursor.moveToNext()) {

                variablesGlobal.setVendedorCodigo(cursor.getString(0));
                variablesGlobal.setVendedorNombre(cursor.getString(1));
                variablesGlobal.setExistente(cursor.getString(2));
             Toast.makeText(getApplicationContext(),"Bienvenido "+variablesGlobal.getVendedorNombre(),Toast.LENGTH_SHORT).show();
            }
            if(variablesGlobal.getExistente() == null){
                cbsincronizarPedido.setVisibility(View.INVISIBLE);
                cbsincronizarCliente.setVisibility(View.INVISIBLE);
                cbsincronizarCategorias.setVisibility(View.INVISIBLE);
                edtPin.setVisibility(View.INVISIBLE);
                cbsincronizarCuentasXCobrar.setVisibility(View.INVISIBLE);

                edtNombreVendedor.setVisibility(View.VISIBLE);
                edtCodigoVendedor.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Vendedor "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarVendedor() {
        try {
            DataVendedor dtVendedor = new DataVendedor();
            dtVendedor.setNombre(edtNombreVendedor.getText().toString());
            dtVendedor.setCodigo(edtCodigoVendedor.getText().toString());
            dtVendedor.registrarVendedor(getApplicationContext());
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"registrar"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public class AsyncTaskCargaDatos extends AsyncTask<String, Float, Void> {

        public AsyncTaskCargaDatos(PopupSincronizacion popup) {
        }
        @Override
        protected void onPreExecute() {
            progresoPopup.setProgress(0);
            progresoPopup.setMax(100);
            int progressbarstatus = 0;
            progresoPopup.setVisibility(View.VISIBLE);
        }

        final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();

        @Override
        protected Void doInBackground(String... params) {
            float progreso = 0.0f;

            try {
                if(cbsincronizarCliente.isChecked())
                {
                    SincronizarCliente();
                }
                if (cbsincronizarCuentasXCobrar.isChecked()){
                    SincronizarCxC();
//                    Toast.makeText(getApplicationContext(),cuentasporCobrar.ultimaFecha.toString(),Toast.LENGTH_SHORT).show();
                }
                if(cbsincronizarPedido.isChecked())
                {
                    SincronizarPedido();
                }
                if(cbsincronizarCategorias.isChecked()){
                    SincronizarArticulos();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Sincronizando"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Float... value) {
            super.onProgressUpdate(value);
        }
        @Override
        protected void onPostExecute(Void result) {
            progresoPopup.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Todos los Registros Sincronizados ",Toast.LENGTH_SHORT).show();
            finish();
        }
    }// fin asynctask
}
