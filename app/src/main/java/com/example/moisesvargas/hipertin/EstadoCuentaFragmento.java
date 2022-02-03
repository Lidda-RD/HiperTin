package com.example.moisesvargas.hipertin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.CuentasporCobrar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class EstadoCuentaFragmento extends Fragment {
    View viewEstadoCuenta;
    ListView listViewEstadoCuenta;
    ArrayList<CuentasporCobrar> listaEstadoCuenta;
    CuentasporCobrar dtEstadoCuenta;
    AdaptadorEstadoCuenta miadapatador;
    ConexionSqlite conn;

    TextView tvnombre,tvTotal,tvContacto,tvCuenta,tvDireccion,tvLimiteCredito,tvTelefono;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewEstadoCuenta = inflater.inflate(R.layout.estado_cuenta_fragmento, container, false);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);
        listViewEstadoCuenta =  viewEstadoCuenta.findViewById(R.id.lstEstadoCuenta);
        tvnombre = viewEstadoCuenta.findViewById(R.id.tvClienteEstadoCuenta);
        tvContacto = viewEstadoCuenta.findViewById(R.id.tvContactoEstadoCuenta);
        tvCuenta = viewEstadoCuenta.findViewById(R.id.tvCuentaEstadoCuenta);
        tvDireccion = viewEstadoCuenta.findViewById(R.id.tvdireccionClienteEstadoCuenta);
        tvLimiteCredito = viewEstadoCuenta.findViewById(R.id.tvlimiteCreditoEstradoCuenta);
        tvTelefono = viewEstadoCuenta.findViewById(R.id.tvTelefonoEstadoCuenta);
        tvTotal = viewEstadoCuenta.findViewById(R.id.tvTotalFacturaPendiente2);
        final VariablesGlobal variableGlobal = (VariablesGlobal) getActivity().getApplicationContext();

        final String Cuenta = variableGlobal.getCuenta().trim();
        final String Data = variableGlobal.getData();
        final String nombre = variableGlobal.getNombre();
        final String contacto = variableGlobal.getContacto();
        NumberFormat formatoDinero = NumberFormat.getCurrencyInstance(Locale.US);
        dtEstadoCuenta = new CuentasporCobrar();
        tvContacto.setText(contacto);
        tvnombre.setText(nombre);
        tvCuenta.setText(Cuenta);
        tvDireccion.setText(variableGlobal.getDireccion());
        tvLimiteCredito.setText(variableGlobal.getLimite());
        tvTelefono.setText(variableGlobal.getTelefono());
        listaEstadoCuenta = dtEstadoCuenta.consultarListaEstadoCuenta(getContext(),conn,Cuenta.toString(),Data);
        miadapatador = new AdaptadorEstadoCuenta(getContext(), listaEstadoCuenta);
        listViewEstadoCuenta.setAdapter(miadapatador);
        tvTotal.setText("Total Pendiente: " +String.valueOf(formatoDinero.format(dtEstadoCuenta.getTotal())));
        return viewEstadoCuenta;
    }
}
