package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataSubrenglon;

import java.util.List;

public class AdaptadorSubrenglon extends BaseAdapter {
    Context contexto;
    List<DataSubrenglon> ListaObjetos;

    public AdaptadorSubrenglon(Context contexto, List<DataSubrenglon> listaObjetos) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        return ListaObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaObjetos.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflate = LayoutInflater.from(contexto);
        vista = inflate.inflate(R.layout.row_categoriatinte, null);

        TextView Descripcion = (TextView) vista.findViewById(R.id.descripcionsubcategoriaTinte);
        Descripcion.setText(ListaObjetos.get(position).getDescripcion().toString());
        return vista;
    }
}
