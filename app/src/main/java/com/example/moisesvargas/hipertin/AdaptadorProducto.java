package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataProducto;

import java.util.List;

public class AdaptadorProducto extends BaseAdapter {
    Context contexto;
    List<DataProducto> ListaObjetos;

    public AdaptadorProducto(Context contexto, List<DataProducto> listaObjetos) {
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
        vista = inflate.inflate(R.layout.row_producto, null);

        TextView Descripcion = (TextView) vista.findViewById(R.id.tvDescripcionProducto);
        TextView Codigo = (TextView) vista.findViewById(R.id.tvCodigoProducto);
        TextView Precio = vista.findViewById(R.id.tvPrecioProducto);
        TextView Cantidad = vista.findViewById(R.id.cantidadPoducto);
        TextView literal = vista.findViewById(R.id.literalProducto);

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
