package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataFacturaPendiente;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorFacturaPendiente extends BaseAdapter {
    Context contexto;
    List<DataFacturaPendiente> ListaObjetos;


    public AdaptadorFacturaPendiente(Context contexto, List<DataFacturaPendiente> listaObjetos) {
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
        vista = inflate.inflate(R.layout.row_factura_pendiente, null);

        TextView tvDocumentoFacturaPendiente = vista.findViewById(R.id.tvDocumentoFacturapendiente);
        TextView tvFechaFacturaPendiente = vista.findViewById(R.id.tvFechaFacturaPendiente);
        TextView tvMontoFacturaPendiente = vista.findViewById(R.id.tvMontoFacturaPendiente);
        TextView tvBalanceFacturaPendiente = vista.findViewById(R.id.tvBalanceFacturaPendiente);
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
        double importe,balance;
        importe = Double.valueOf(ListaObjetos.get(position).getImporte().toString());
        balance = Double.valueOf(ListaObjetos.get(position).getBalance().toString());

        //Quitarle la hora, minuto y segungo a la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(ListaObjetos.get(position).getFecha().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy");
        String fecha= targetFormat.format(sourceDate);

        tvDocumentoFacturaPendiente.setText("Factura no. " + ListaObjetos.get(position).getDocumento().toString());
        tvFechaFacturaPendiente.setText(fecha);
        tvMontoFacturaPendiente.setText("Importe:" + formatoDinero.format(importe));
        tvBalanceFacturaPendiente.setText("Balance: " + formatoDinero.format(balance));
        return vista;
    }

}
