package com.example.moisesvargas.hipertin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataFacturaPendiente;

import java.util.ArrayList;

public class FacturaPendiente extends AppCompatActivity {

    ListView listViewFacturaPendiente;
    ArrayList<DataFacturaPendiente> listaFacturaPendiente;
    DataFacturaPendiente dtFacturaPendiente;
    AdaptadorFacturaPendiente miadapatador;
    ConexionSqlite conn;
    String Cuenta,nombre,Data;
    TextView tvnombre,tvDataFacturaPendiente,tvContacto,tvCuenta,tvTelefono,tvLimiteCredito,tvDireccion;
    //ArrayList<String>listaInformacion;
    TextView tvDocumento,tvFecha,tvMonto,tvBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_pendiente);
        conn = new ConexionSqlite(getApplicationContext(),"nextsales",null,1);
        listViewFacturaPendiente = (ListView) findViewById(R.id.lstFacturaPendiente);
        listaFacturaPendiente = new ArrayList<DataFacturaPendiente>();
        tvnombre = findViewById(R.id.tvNombreFacturaspendientes);
        tvDataFacturaPendiente = findViewById(R.id.tvDataFacturaPendiente);
        tvContacto = findViewById(R.id.tvContactoFacturaPendiente);
        tvCuenta = findViewById(R.id.tvcuentaCliente2);
        tvTelefono = findViewById(R.id.tvTelefono2);
        tvLimiteCredito = findViewById(R.id.tvlimiteCredito2);
        tvDireccion = findViewById(R.id.tvdireccionCliente2);
        tvDataFacturaPendiente.setText(Data);

        final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
        final String contacto = variablesGlobal.getContacto();

        Cuenta = variablesGlobal.getCuenta();
        Data = variablesGlobal.getData();
        nombre = variablesGlobal.getNombre();
        tvnombre.setText(nombre);
        tvContacto.setText(contacto);
        tvCuenta.setText(variablesGlobal.getCuenta());
        tvDireccion.setText(variablesGlobal.getDireccion());
        tvLimiteCredito.setText(variablesGlobal.getLimite());
        tvTelefono.setText(variablesGlobal.getTelefono());
        listaFacturaPendiente = new DataFacturaPendiente().consultarListaFacturaPendiente(getApplicationContext(),conn,Cuenta,Data);
        miadapatador = new AdaptadorFacturaPendiente(getApplicationContext(), listaFacturaPendiente);
        listViewFacturaPendiente.setAdapter(miadapatador);
    }
}
