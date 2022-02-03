package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataLinea;
import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;

import java.util.ArrayList;

public class Linea extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listViewLinea;
    ArrayList<DataLinea> listaLinea;
    AdaptadorLinea miadapatadorLinea;
    ConexionSqlite conn;
    ArrayList <String>listaInformacion;
    String _subrenglon;
    int codigo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);
        _subrenglon = getIntent().getExtras().getString("renglon");
        this.setTitle(_subrenglon);

        listViewLinea = findViewById(R.id.lstLinea);
        listaLinea = new ArrayList<DataLinea>();
        consultarListaLinea();
        listViewLinea.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvCantidadLinea =  view.findViewById(R.id.tvCantidadLinea);
        TextView tvPrecioLinea =  view.findViewById(R.id.tvPrecioLinea);
        TextView tvDescripcionLinea = view.findViewById(R.id.tvDescripcionLinea);
        TextView tvCodigoTinte = view.findViewById(R.id.tvCodigoLinea);


        Intent additem = new Intent(view.getContext(),Popup.class);
        additem.putExtra("cantidad",tvCantidadLinea.getText().toString());
        additem.putExtra("precio",Double.valueOf(tvPrecioLinea.getText().toString()));
        additem.putExtra("descripcion",tvDescripcionLinea.getText().toString());
        additem.putExtra("codigo",tvCodigoTinte.getText().toString());

        startActivity(additem);
    }
    private void consultarListaLinea() {
        listaLinea = new  DataLinea().consultarListaLinea(getApplicationContext(),conn,_subrenglon);
        miadapatadorLinea = new AdaptadorLinea(getApplicationContext(), listaLinea);
        listViewLinea.setAdapter(null);
        listViewLinea.setAdapter(miadapatadorLinea);
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarListaLinea();
    }
}
