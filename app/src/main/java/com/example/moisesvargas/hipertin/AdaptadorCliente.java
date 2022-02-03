package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataCliente;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptadorCliente extends BaseAdapter implements Filterable
 {
     Context contexto;
     List<DataCliente> ListaObjetos;
     CustomFilter filter;
     List<DataCliente> filterList;

     public AdaptadorCliente(Context contexto, List<DataCliente> listaObjetos) {
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
         vista = inflate.inflate(R.layout.row_cliente, null);

         TextView nameCli = vista.findViewById(R.id.tvnombreCliente);
         TextView cuentaCli = vista.findViewById(R.id.tvcuentaCliente);
         TextView direcionCli = vista.findViewById(R.id.tvdireccionCliente);
         TextView tvvendedorCliente = vista.findViewById(R.id.tvvendedorCliente);
         TextView tvTelefono = vista.findViewById(R.id.tvTelefono);
         TextView tvlimiteCredito = vista.findViewById(R.id.tvlimiteCredito);
         TextView tvData = vista.findViewById(R.id.tvdata);
         TextView tvBalance = vista.findViewById(R.id.tvBalance);
         TextView tvContacto = vista.findViewById(R.id.tvContacto);
         TextView tvSector = vista.findViewById(R.id.tvSectorCliente);
         NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
         Double balance;

         nameCli.setText(ListaObjetos.get(position).getNombre());
         tvContacto.setText(ListaObjetos.get(position).getContacto());
         cuentaCli.setText(ListaObjetos.get(position).getCuenta());
         direcionCli.setText(ListaObjetos.get(position).getDireccion());
         tvvendedorCliente.setText(ListaObjetos.get(position).getVendedor());
         tvTelefono.setText(ListaObjetos.get(position).getTelefono().trim());
         tvlimiteCredito.setText( formatoDinero.format(Double.valueOf(ListaObjetos.get(position).getLimite_credito())));
         tvSector.setText(ListaObjetos.get(position).getSector().trim());
         if(tvlimiteCredito.getText().toString()== null)
         {
             tvlimiteCredito.setText("LIMITE:0");
         }
         balance =Double.valueOf(ListaObjetos.get(position).getBalance());
         tvBalance.setText(formatoDinero.format(balance));
         //tvBalance.setText(ListaObjetos.get(position).getBalance());
         tvData.setText(ListaObjetos.get(position).getData());
         return vista;
     }

     @Override
     public Filter getFilter()
     {
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
                 ArrayList<DataCliente> filters=new ArrayList<DataCliente>();

                 for (int i=0;i<filterList.size();i++)
                 {
                     if (filterList.get(i).getNombre().toUpperCase().contains(constraint))
                     {
                         DataCliente d = new DataCliente(
                                 filterList.get(i).getId(),
                                 filterList.get(i).getNombre(),
                                 filterList.get(i).getCuenta(),
                                 filterList.get(i).getDireccion(),
                                 filterList.get(i).getVendedor(),
                                 filterList.get(i).getDia_visita(),
                                 filterList.get(i).getTelefono(),
                                 filterList.get(i).getLimite_credito(),
                                 filterList.get(i).getBalance(),
                                 filterList.get(i).getData(),
                                 filterList.get(i).getContacto(),
                                 filterList.get(i).getSector());

                         filters.add(d);
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
             ListaObjetos=(ArrayList<DataCliente>) results.values;
             notifyDataSetChanged();
         }
     }
 }

