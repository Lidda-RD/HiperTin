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

public class CategoriaProducto extends Fragment implements ListView.OnItemClickListener{
    View viewProducto;
    ListView listViewProducto;
    String _renglon;
    ArrayList<String> listaProducto;
    ConexionSqlite conn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewProducto = inflater.inflate(R.layout.fragment_categoria_producto, container, false);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);
        listViewProducto=viewProducto.findViewById(R.id.lstCategoriaProducto);

        listaProducto= new ArrayList<>();
        consultarProducto();

//        ArrayAdapter adaptador= new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaTintes);
//        listViewProducto.setAdapter(adaptador);
        listViewProducto.setOnItemClickListener(this);
        return viewProducto;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent _producto = new Intent(view.getContext(),Producto.class);
        _renglon = (listViewProducto.getItemAtPosition(position).toString());
        _producto.putExtra("renglon",_renglon );
        startActivity(_producto);
    }

    private void consultarProducto() {
        try {
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM subrenglon WHERE renglon='2  '",null);
            listaProducto.clear();
            while (cursor.moveToNext()) {

                listaProducto.add(cursor.getString(1));

            }
            ArrayAdapter adaptador= new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaProducto);
            listViewProducto.setAdapter(adaptador);
        }catch (Exception e){
            Toast.makeText(getActivity(),"consultarProducto"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
