package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataTinte;

import java.io.InputStream;
import java.util.ArrayList;

public class Tinte extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listViewTinte;
    ArrayList<DataTinte> listaTinte;
    ArrayList<String>listaInformacion;
    AdaptadorTinte miadapatadorTinte;
    ConexionSqlite conn;
    String _subrenglon;
    EditText txtRenglon,txtDescripcion,txtCategoria;
    ImageView imgArticulo;
    Button btnGuardar;
    int codigo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinte);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);

        listViewTinte = findViewById(R.id.lstTinte);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtRenglon = findViewById(R.id.txtRenglon);
        txtCategoria = findViewById(R.id.txtCategoria);
        btnGuardar = findViewById(R.id.btnGuardar);
        imgArticulo = findViewById(R.id.imgArticulo);
        listaTinte = new ArrayList<DataTinte>();
        _subrenglon = getIntent().getExtras().getString("renglon");
        txtRenglon.setText(_subrenglon);
        this.setTitle(_subrenglon);

        consultarListaConeccion();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarListaConeccion();
            }
        });

        listViewTinte.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvCantidadTinte =  view.findViewById(R.id.tvCantidadTinte);
        TextView tvPrecioTinte =  view.findViewById(R.id.tvPrecioTinte);
        TextView tvDescripcionTinte = view.findViewById(R.id.tvDescripcionTinte);
        TextView tvCodigoTinte = view.findViewById(R.id.tvCodigoTinte);
        ImageView imgProducto = view.findViewById(R.id.imgArticulo);
        TextView tvItbis = view.findViewById(R.id.tvItbisTinte);

        Intent popup = new Intent(view.getContext(),Popup.class);
        popup.putExtra("Itbis",Double.valueOf(tvItbis.getText().toString()));
        popup.putExtra("cantidad",tvCantidadTinte.getText().toString());
        popup.putExtra("precio",Double.valueOf(tvPrecioTinte.getText().toString()));
        popup.putExtra("descripcion",tvDescripcionTinte.getText().toString());
        popup.putExtra("codigo",tvCodigoTinte.getText().toString());
        popup.putExtra("imagen",imgProducto.getTag().toString());
        startActivity(popup);
    }

    public void consultarListaConeccion() {
        listaTinte = new DataTinte().consultarListaTinte(getApplicationContext(),conn,_subrenglon);
        miadapatadorTinte = new AdaptadorTinte(getApplicationContext(), listaTinte);
        listViewTinte.setAdapter(null);
        listViewTinte.setAdapter(miadapatadorTinte);





    }

    @Override
    protected void onResume() {
        super.onResume();
       consultarListaConeccion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        consultarListaConeccion();
    }
}
