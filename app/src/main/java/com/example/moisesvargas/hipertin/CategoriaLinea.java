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


public class CategoriaLinea extends Fragment implements ListView.OnItemClickListener{

    View viewLinea;
    ListView listViewLinea;
    String _renglon;
    ArrayList<String> listaProducto;
    ConexionSqlite conn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewLinea = inflater.inflate(R.layout.fragment_categoria_linea, container, false);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);
        listViewLinea=viewLinea.findViewById(R.id.lstCategoriaLinea);

        listaProducto = new ArrayList<>();
        consultarLinea();

        listViewLinea.setOnItemClickListener(this);
        return viewLinea;

        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent _linea = new Intent(view.getContext(),Linea.class);
        _renglon = (listViewLinea.getItemAtPosition(position).toString());
        _linea.putExtra("renglon",_renglon );
        startActivity(_linea);
    }

    private void consultarLinea() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM subrenglon WHERE renglon='1  ' ",null);
            listaProducto.clear();
            while (cursor.moveToNext()) {

                listaProducto.add(cursor.getString(1));

            }
            ArrayAdapter adaptador= new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaProducto);
            listViewLinea.setAdapter(adaptador);
        }catch (Exception e){
            Toast.makeText(getActivity(),"consultarLinea "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
