package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataPedidoDetalle;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdaptadorCarritoPedido extends BaseAdapter
{
    Context contexto;
    List<DataPedidoDetalle> ListaObjetos;

    public AdaptadorCarritoPedido(Context contexto, List<DataPedidoDetalle> listaObjetos) {
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
        vista = inflate.inflate(R.layout.row_carrito, null);

        TextView tvrowCarritoCodigo = vista.findViewById(R.id.tvrowCarritoCodigo);
        TextView tvrowCarritoCantidad = vista.findViewById(R.id.tvrowCarritoCantidad);
        TextView tvrowCarritoDescripcion = vista.findViewById(R.id.tvrowCarritoDescripcion);
        TextView tvrowCarritoPrecio = vista.findViewById(R.id.tvrowCarritoPrecio);
        TextView tvCarritoImporte = vista.findViewById(R.id.tvrowCarritoimporte);

        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
        tvrowCarritoCodigo.setText(ListaObjetos.get(position).getCodigo().toString());
        tvrowCarritoCantidad.setText(ListaObjetos.get(position).getCantidad().toString());
        tvrowCarritoDescripcion.setText(ListaObjetos.get(position).getDescripcion().toString());
        tvrowCarritoPrecio.setText("RD"+formatoDinero.format( ListaObjetos.get(position).getPrecio()).toString());
        double importe = ListaObjetos.get(position).getPrecio()* ListaObjetos.get(position).getCantidad();
        tvCarritoImporte.setText("RD"+String.valueOf(formatoDinero.format(importe)));
        return vista;
    }
}
