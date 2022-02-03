package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataProducto;

import java.util.ArrayList;

public class Producto extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listViewProducto;
    ArrayList<DataProducto> listaProducto;
    AdaptadorProducto miadapatadorProducto;
    ConexionSqlite conn;
    String _subrenglon;
    ArrayList<String>listaInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);
        _subrenglon = getIntent().getExtras().getString("renglon");
        this.setTitle(_subrenglon);
        listViewProducto = (ListView)findViewById(R.id.lstProducto);
        listaProducto = new ArrayList<>();
        consultarListaProductos();
        listViewProducto.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView cantidadPoducto =  view.findViewById(R.id.cantidadPoducto);
        TextView PrecioProducto =  view.findViewById(R.id.tvPrecioProducto);
        TextView DescripciondeProducto = view.findViewById(R.id.tvDescripcionProducto);
        TextView tvCodigoTinte = view.findViewById(R.id.tvCodigoProducto);

        Intent additem = new Intent(view.getContext(),Popup.class);
        additem.putExtra("cantidad",cantidadPoducto.getText().toString());
        additem.putExtra("precio",Double.valueOf(PrecioProducto.getText().toString()));
        additem.putExtra("descripcion",DescripciondeProducto.getText().toString());
        additem.putExtra("codigo",tvCodigoTinte.getText().toString());

        startActivity(additem);
    }

    private void consultarListaProductos() {
        listaProducto = new DataProducto().consultarListaProductos(getApplicationContext(),conn,_subrenglon);
        miadapatadorProducto = new AdaptadorProducto(getApplicationContext(), listaProducto);
        listViewProducto.setAdapter(null);
        listViewProducto.setAdapter(miadapatadorProducto);
    }
    @Override
    protected void onResume() {
        super.onResume();
        consultarListaProductos();
    }
}
