package com.example.moisesvargas.hipertin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataFacturaPendiente;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class FacturasPendiente_Fragmento extends Fragment {
    View viewFacturaPendiente;
    ListView listViewFacturaPendiente;
    ArrayList<DataFacturaPendiente> listaFacturaPendiente;
    DataFacturaPendiente dtFacturaPendiente;
    AdaptadorFacturaPendiente miadapatador;
    ConexionSqlite conn;

    TextView tvnombre,tvTotal,tvContacto,tvCuenta,tvTelefono,tvLimiteCredito,tvDireccion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFacturaPendiente = inflater.inflate(R.layout.facturas_pendiente__fragmento, container, false);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);
        listViewFacturaPendiente =  viewFacturaPendiente.findViewById(R.id.lstFacturaPendienteFragmento);
        tvnombre = viewFacturaPendiente.findViewById(R.id.tvNombreFacturaspendientesFragmento);
        tvTotal = viewFacturaPendiente.findViewById(R.id.tvTotalFacturaPendiente);
        tvContacto = viewFacturaPendiente.findViewById(R.id.tvContactoFacturaPendiente);
        tvCuenta = viewFacturaPendiente.findViewById(R.id.tvcuentaCliente2);
        tvTelefono = viewFacturaPendiente.findViewById(R.id.tvTelefono2);
        tvLimiteCredito = viewFacturaPendiente.findViewById(R.id.tvlimiteCredito2);
        tvDireccion = viewFacturaPendiente.findViewById(R.id.tvdireccionCliente2);
        //tvDataFacturaPendiente = viewFacturaPendiente.findViewById(R.id.tvDataFacturaPendienteFragmento);
        final VariablesGlobal variablesGlobal = (VariablesGlobal) getActivity().getApplicationContext();
        final String Cuenta = variablesGlobal.getCuenta();
        final String Data = variablesGlobal.getData();
        final String nombre = variablesGlobal.getNombre();
        final String contacto = variablesGlobal.getContacto();

        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
        dtFacturaPendiente = new DataFacturaPendiente();
        tvnombre.setText(nombre);
        tvContacto.setText(contacto);
        tvCuenta.setText(variablesGlobal.getCuenta());
        tvDireccion.setText(variablesGlobal.getDireccion());
        tvLimiteCredito.setText(variablesGlobal.getLimite());
        tvTelefono.setText(variablesGlobal.getTelefono());
        // tvDataFacturaPendiente.setText(Data);
        listaFacturaPendiente = dtFacturaPendiente.consultarListaFacturaPendiente(getContext(),conn,Cuenta,Data);
        miadapatador = new AdaptadorFacturaPendiente(getContext(), listaFacturaPendiente);
        listViewFacturaPendiente.setAdapter(miadapatador);
        tvTotal.setText("Total Pendiente: " +String.valueOf(formatoDinero.format(dtFacturaPendiente.getTotal())));
        return viewFacturaPendiente;
    }
}
