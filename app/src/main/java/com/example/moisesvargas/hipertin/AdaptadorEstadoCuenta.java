package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.CuentasporCobrar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdaptadorEstadoCuenta extends BaseAdapter {
    Context contexto;
    List<CuentasporCobrar> ListaObjetos;


    public AdaptadorEstadoCuenta(Context contexto, List<CuentasporCobrar> listaObjetos) {
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
        vista = inflate.inflate(R.layout.row_estado_cuenta, null);

        TextView tvDocumentoEstadoCuenta = vista.findViewById(R.id.tvDocumentoEstadoCuenta);
        TextView tvFechaEstadoCuenta = vista.findViewById(R.id.tvFechaEstadoCuenta);
        TextView tvTransaccionEstadoCuenta = vista.findViewById(R.id.tvTransaccionEstadoCuenta);
        TextView tvBalanceEstadoCuenta = vista.findViewById(R.id.tvBalanceEstadoCuenta);
        TextView tvMontoEstadoCuenta = vista.findViewById(R.id.tvMontoEstadoCuenta);
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);

        tvDocumentoEstadoCuenta.setText("no. " + ListaObjetos.get(position).getDocumento().toString());
        tvFechaEstadoCuenta.setText(ListaObjetos.get(position).getFecha());
        tvTransaccionEstadoCuenta.setText(ListaObjetos.get(position).getTransaccion());
        tvBalanceEstadoCuenta.setText("Balance: " + String.valueOf(formatoDinero.format(ListaObjetos.get(position).getBalance())));
        tvMontoEstadoCuenta.setText("Monto: " +  String.valueOf(formatoDinero.format(ListaObjetos.get(position).getMonto())));
        return vista;
    }
}
