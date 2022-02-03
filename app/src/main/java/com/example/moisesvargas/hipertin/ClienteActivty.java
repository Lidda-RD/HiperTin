package com.example.moisesvargas.hipertin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataCliente;

import java.util.ArrayList;

public class ClienteActivty extends AppCompatActivity implements ListView.OnItemClickListener, ListView.OnLongClickListener {

    ListView listViewCliente;
    MenuItem item ;
    SearchView searchView;
    TabLayout tabLayout;
    ArrayList<DataCliente> listaCliente;
    AdaptadorCliente miadapatador;
    ConexionSqlite conn;
    ArrayList<String>listaInformacion;
    EditText txtNombre,txtCuenta,txtDireccion,txtTelefono,txtVendedor;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);

        //optener datos de cliente del carrito
        final String nombreCliente = getIntent().getExtras().getString("nombre");


        listViewCliente = (ListView) findViewById(R.id.lstCliente);

        listViewCliente.setOnItemClickListener(this);
        listViewCliente.setLongClickable(true);
        listViewCliente.setOnLongClickListener(this);
        consultarListaCliente();

        //Accion de Retorno al carrito
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent carrito = new Intent(ClienteActivty.this,CarritoActivity.class);
                carrito.putExtra("nombre",nombreCliente );
                startActivity(carrito);
                finish();
                return;
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaCliente.clear();
                miadapatador.notifyDataSetChanged();
                consultarListaCliente();
                registrarConeccion();
            }
        });
    }

    //Seleccion de cliente y retorno al carrito
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        finish();
        TextView tvnombrecliente =  view.findViewById(R.id.tvnombreCliente);
        TextView tvcuentaCliente =  view.findViewById(R.id.tvcuentaCliente);
        TextView tvdireccionCliente =  view.findViewById(R.id.tvdireccionCliente);
        TextView tvvendedorCliente =  view.findViewById(R.id.tvvendedorCliente);
        TextView tvData =  view.findViewById(R.id.tvdata);

        Intent additem = new Intent(view.getContext(),CarritoActivity.class);
        additem.putExtra("nombre",tvnombrecliente.getText().toString());
        additem.putExtra("cuenta",tvcuentaCliente.getText().toString());
        additem.putExtra("direccion",tvdireccionCliente.getText().toString());
        additem.putExtra("vendedor",tvvendedorCliente.getText().toString());
        additem.putExtra("data",tvData.getText().toString());
        startActivity(additem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.menuSearch);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar Cliente");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                miadapatador.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    private void consultarListaCliente() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Clientes ORDER BY nombre ASC ",null);

            listaInformacion = new ArrayList<String>();
            while (cursor.moveToNext()) {

                listaCliente.add(new DataCliente(1,cursor.getString(1),cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getDouble(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));

            }

            listaCliente = new ArrayList<DataCliente>();
            miadapatador = new AdaptadorCliente(getApplicationContext(), listaCliente);
            listViewCliente.setAdapter(miadapatador);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"consultarListaConeccion "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarConeccion() {
        try {
            ConexionSqlite conn = new ConexionSqlite(this,"nextsales",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("cuenta",txtCuenta.getText().toString());
            values.put("nombre",txtNombre.getText().toString());
            values.put("direccion",txtDireccion.getText().toString());
            values.put("vendedor",txtVendedor.getText().toString());
            values.put("telefono",txtTelefono.getText().toString());

            Long idResultante=db.insert("Clientes","stringConexion",values);

            // db.execSQL("UPDATE Producto SET categoria='Tinte' WHERE categoria = '0' ");
            // db.execSQL("DELETE FROM Producto WHERE categoria='Producto' ");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"registrar"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getApplicationContext(),"registrar",Toast.LENGTH_SHORT).show();
        return true;
    }
}
