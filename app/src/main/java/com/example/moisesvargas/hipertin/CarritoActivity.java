package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.DataCliente;
import com.example.moisesvargas.hipertin.CapaData.DataPedido;
import com.example.moisesvargas.hipertin.CapaData.DataPedidoDetalle;
import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CarritoActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        MainActivity main;
        TextView tvnombreCliente;
        View view;
        String tvnombrecliente;
        Intent Categoria,FacturaPendiente;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch(item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    return true;

                case R.id.navigation_categoria:
                    //Asignar numero de documento a Variable global
                    final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
                     variablesGlobal.setDocumento(numeroPedido);

                    Categoria = new Intent(CarritoActivity.this,Categoria_de_Productos.class);
                    Categoria.putExtra("cuenta",tvcuentaCarrito.getText().toString());
                    Categoria.putExtra("documento",numeroPedido);
                    Categoria.putExtra("nombre",tvCarritoNombreCliente.getText().toString());
                    Categoria.putExtra("data",data);
                    startActivity(Categoria);
                    finish();
                    return true;
//                case  R.id.factura_pendiente:
//                    FacturaPendiente = new Intent(CarritoActivity.this,FacturaPendiente.class);
//                    FacturaPendiente.putExtra("nombre",tvCarritoNombreCliente.getText().toString());
//                    FacturaPendiente.putExtra("data",data);
//                    startActivity(FacturaPendiente);
//                    return true;
            }
            return false;
        }
    };
    private TextView tvCarritoNombreCliente,tvcuentaCarrito,tvcontacto,tvtelefono,tvsector,tvlimitecredito,tvbalance,tvdireccionClienteCarrito,tvvendedorCarrito,tvfechaCarrito,tvimporteCarrito,tvnumerodepedido;
    AdaptadorCarritoPedido miadapatadorPedido;
    ListView listViewCarrito;
    ArrayList<DataPedidoDetalle> listaCarrito;
    ConexionSqlite conn;
    DataPedido dtPedido;
    String numeroPedido,nombre,cuenta,data;
    TabLayout tabLayout;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);

        listViewCarrito = findViewById(R.id.lstCarrito);
        listaCarrito = new ArrayList<DataPedidoDetalle>();
        tvCarritoNombreCliente= findViewById(R.id.tvnombreClienteCarrito);
        tvcuentaCarrito= findViewById(R.id.tvcuentaCarrito);
        tvimporteCarrito= findViewById(R.id.tvimporteCarrito);
        tvnumerodepedido= findViewById(R.id.tvnumerodePedidoCarrito);
        tvcontacto= findViewById(R.id.tvContactoCarrito);
        tvtelefono= findViewById(R.id.tvTelefonoCarrito);
        tvsector= findViewById(R.id.tvSectorCarrito);
        tvlimitecredito= findViewById(R.id.tvlimiteCreditoCarrito);
        tvbalance= findViewById(R.id.tvBalanceCarrito);
        tvdireccionClienteCarrito= findViewById(R.id.tvdireccionClienteCarrito);
        final VariablesGlobal variableGlobal = (VariablesGlobal) this.getApplicationContext();
        numeroPedido = getIntent().getExtras().getString("documento");
        nombre = getIntent().getExtras().getString("nombre");
        cuenta =getIntent().getExtras().getString("cuenta");
        data =getIntent().getExtras().getString("data");
        tvdireccionClienteCarrito.setText(getIntent().getExtras().getString("direccion"));
        tvcontacto.setText(getIntent().getExtras().getString("contacto"));
        tvtelefono.setText(getIntent().getExtras().getString("telefono"));
        tvsector.setText(getIntent().getExtras().getString("sector"));
        tvlimitecredito.setText(getIntent().getExtras().getString("limiteCredito"));
        tvbalance.setText(getIntent().getExtras().getString("tvBalance"));
        variableGlobal.setCuenta(cuenta);
        DataPedidoDetalle dtDetallePedido;
        DataCliente dtCliente;


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#C7A63F'>"+"Carrito"+"</font>"));

        if(numeroPedido == null) {

            dtCliente = new DataCliente();
            dtCliente.consultarCliente(getApplicationContext(),cuenta,conn);
            tvCarritoNombreCliente.setText(dtCliente.getNombre());
            tvcuentaCarrito.setText(dtCliente.getCuenta());
            tvnumerodepedido.setText(numeroPedido);

//            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//            tvfechaCarrito.setText(date);
//             Calling Application class (see application tag in AndroidManifest.xml)
            final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();

            // Get name and email from global/application context
            variablesGlobal.setCuenta(tvcuentaCarrito.getText().toString());
            variablesGlobal.setDocumento(numeroPedido);
        }else {
            //Consultar el pedido
            dtPedido = new DataPedido();
            dtPedido.consultarPedido(getApplicationContext(),numeroPedido,conn);
            tvCarritoNombreCliente.setText(dtPedido.getNombre());
            tvcuentaCarrito.setText(dtPedido.getCuenta());
            tvnumerodepedido.setText(dtPedido.getDocumento());
            NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
            tvimporteCarrito.setText("Importe:" +formatoDinero.format(dtPedido.getImporte()));


            final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
            variablesGlobal.setCuenta(tvcuentaCarrito.getText().toString());
            variablesGlobal.setDocumento(numeroPedido);

            // Llenar lista de detalle del pedido
            try {
                // Iniciar Adaptador, llenar con lista de detalle del pedido y pasar al listview
                dtDetallePedido = new DataPedidoDetalle();
                listaCarrito = dtDetallePedido.consultarListaDetallePedido(getApplicationContext(),numeroPedido,conn);
                miadapatadorPedido = new AdaptadorCarritoPedido(getApplicationContext(), listaCarrito);
                listViewCarrito.setAdapter(miadapatadorPedido);

            } catch (Exception e) {
                Toast toast1 = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast1.show();
            }
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
//    private void consultarPedido() {
//        try {
//            SQLiteDatabase db = conn.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM Pedidos_HDR WHERE documento = '"+numeroPedido+"'",null);
//            NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
//
//            if (cursor.moveToNext()) {
//                dtPedido = new DataPedido();
//                tvCarritoNombreCliente.setText(cursor.getString(5));
//                tvcuentaCarrito.setText(cursor.getString(3));
//                tvfechaCarrito.setText(cursor.getString(16));
//                tvnumerodepedido.setText(cursor.getString(0));
//                tvimporteCarrito.setText("Importe:" +formatoDinero.format(cursor.getDouble(31)));
//
//
//            }
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Consultar pedido: "+e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void consultarListaDetallePedido() {
//        try {
//            SQLiteDatabase db = conn.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM Pedido_Detalle WHERE documento = '"+numeroPedido+"'",null);
//
//            while (cursor.moveToNext()) {
//
//                listaCarrito.add(new DataPedidoDetalle(1,cursor.getString(2),cursor.getDouble(13),cursor.getDouble(14),cursor.getString(3),cursor.getString(30)));
//
//            }
//            miadapatadorPedido = new AdaptadorCarritoPedido(getApplicationContext(), listaCarrito);
//            listViewCarrito.setAdapter(miadapatadorPedido);
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Consultar detalle: "+e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void consultarCliente() {
//        try {
//            SQLiteDatabase db = conn.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM Clientes where cuenta='"+cuenta+"' ",null);
//
//
//            if (cursor.moveToNext()) {
//                tvCarritoNombreCliente.setText(cursor.getString(0));
//                tvcuentaCarrito.setText(cursor.getString(1));
//                tvnumerodepedido.setText(numeroPedido);
//            }
//
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Consltar clientes: "+e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//    }
}
