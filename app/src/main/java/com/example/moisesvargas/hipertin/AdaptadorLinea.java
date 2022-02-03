package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataLinea;

import java.util.List;

public class AdaptadorLinea extends BaseAdapter{


    Context contexto;
    List<DataLinea> ListaObjetos;


    public AdaptadorLinea(Context contexto, List<DataLinea> listaObjetos) {
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
        return ListaObjetos.get(position).getIdProducto();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflate = LayoutInflater.from(contexto);
        vista = inflate.inflate(R.layout.row_linea, null);

        TextView Descripcion = vista.findViewById(R.id.tvDescripcionLinea);
        TextView Codigo =  vista.findViewById(R.id.tvCodigoLinea);
        TextView Precio = vista.findViewById(R.id.tvPrecioLinea);
        TextView Cantidad = vista.findViewById(R.id.tvCantidadLinea);
        TextView literal = vista.findViewById(R.id.literalLinea);

        Descripcion.setText(ListaObjetos.get(position).getDescripcion().toString());
        Codigo.setText(ListaObjetos.get(position).getCodigo().toString());
        Precio.setText(ListaObjetos.get(position).getPrecio().toString());
        Cantidad.setText(ListaObjetos.get(position).getCantidad().toString());
        if(ListaObjetos.get(position).getCantidad().toString().equals("0")){
            Cantidad.setVisibility(View.INVISIBLE);
            literal.setVisibility(View.INVISIBLE);
        }



        return vista;
    }
}
