package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;

import java.util.ArrayList;

public class CategoriaTinte extends Fragment implements ListView.OnItemClickListener{
    View viewTinte;
    ListView listViewTinte;
    String _renglon;
    ArrayList<String> listaProducto;
    ConexionSqlite conn;
    AdaptadorSubrenglon miadapatador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewTinte = inflater.inflate(R.layout.fragment_categoria_tinte, container, false);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);
        listViewTinte=viewTinte.findViewById(R.id.lstCategoriaTinte);

        listaProducto = new ArrayList<>();
        consultarProducto();

//        ArrayAdapter adaptador= new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaTintes);
//        listViewTinte.setAdapter(adaptador);
        listViewTinte.setOnItemClickListener(this);
        return viewTinte;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent tinte = new Intent(view.getContext(),Tinte.class);
        _renglon = (listViewTinte.getItemAtPosition(position).toString());
        tinte.putExtra("renglon",_renglon );
        startActivity(tinte);
    }

    private void consultarProducto() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM renglon ",null);
            listaProducto.clear();
            while (cursor.moveToNext()) {
                listaProducto.add(cursor.getString(1));
            }

            ArrayAdapter adaptador= new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaProducto);
            listViewTinte.setAdapter(adaptador);
        }catch (Exception e){
            Toast.makeText(getActivity(),"Consultar producto"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
