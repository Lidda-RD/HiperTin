package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataPedido;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptadorPedido extends BaseAdapter implements Filterable
{
    Context contexto;
    List<DataPedido> ListaObjetos;
    CustomFilter filter;
    List<DataPedido> filterList;


    public AdaptadorPedido(Context contexto, List<DataPedido> listaObjetos) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
        this.filterList = listaObjetos;
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
        vista = inflate.inflate(R.layout.row_pedido, null);

        TextView tvnombrePedido = vista.findViewById(R.id.tvnombrePedido);
        TextView tvnoPedido = vista.findViewById(R.id.tvnoPedido);
        TextView tvimportePedido = vista.findViewById(R.id.tvimportePedido);
        TextView tvDireccionPedido = vista.findViewById(R.id.tvDireccionPedido);
        TextView tvCuentaPedido = vista.findViewById(R.id.tvCuentaPedido);
        TextView tvfechaPedido = vista.findViewById(R.id.tvfechaPedido);
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
        Double importe;

        tvnombrePedido.setText(ListaObjetos.get(position).getNombre());
        tvnoPedido.setText(ListaObjetos.get(position).getPedido());
        importe = Double.valueOf(ListaObjetos.get(position).getImporte().toString());
        tvimportePedido.setText(formatoDinero.format(importe));
        tvDireccionPedido.setText(ListaObjetos.get(position).getDireccion());
        tvCuentaPedido.setText(ListaObjetos.get(position).getCuenta());
        tvfechaPedido.setText(ListaObjetos.get(position).getFecha_pedido());

        return vista;

    }
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }

    // Clase Filtro Personalizado
    class  CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                constraint = constraint.toString().toUpperCase();
                ArrayList<DataPedido> filters= new ArrayList<>();

                for (int i=0;i<filterList.size();i++)
                {
                    if (filterList.get(i).getNombre().toUpperCase().contains(constraint))
                    {
//                        DataPedido d = new DataPedido(
//                                filterList.get(i).getId(),
//                                filterList.get(i).getNombre(),
//                                filterList.get(i).getPedido(),
//                                filterList.get(i).getImporte(),
//                                filterList.get(i).getDireccion(),
//                                filterList.get(i).getCuenta(),
//                                filterList.get(i).getFecha_pedido());
//                        filters.add(d);
                    }
                }
                results.count=filters.size();
                results.values = filters;
            }
            else {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }



        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ListaObjetos=(ArrayList<DataPedido>) results.values;
            notifyDataSetChanged();

        }
    }
}
