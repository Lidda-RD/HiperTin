package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataCliente;

import java.util.ArrayList;


public class ClienteFragmento extends Fragment implements ListView.OnItemClickListener, ListView.OnLongClickListener{

    //View
    ListView listViewCliente;
    View viewCliente;
    ConexionSqlite conn;
    ArrayList<String>listaInformacion;
    //Propiedades
    AdaptadorCliente miadapatador;
    ArrayList<DataCliente> listaCliente;
    EditText txtNombre,txtCuenta,txtDireccion,txtTelefono,txtVendedor,txtData,txtContacto;
    Button btnGuardar;
    String codigo,vendedor;
    int dia = 0;
    boolean conBalance;
    DataCliente dtCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewCliente = inflater.inflate(R.layout.fragment_cliente, container, false);
        listViewCliente =  viewCliente.findViewById(R.id.lstCliente);
        txtCuenta = viewCliente.findViewById(R.id.txtCuenta);
        txtNombre = viewCliente.findViewById(R.id.txtNombre);
        txtTelefono = viewCliente.findViewById(R.id.txtTelefono);
        txtDireccion = viewCliente.findViewById(R.id.txtDireccion);
        txtVendedor = viewCliente.findViewById(R.id.txtVendedor);
        txtData = viewCliente.findViewById(R.id.tvdata);
        txtContacto = viewCliente.findViewById(R.id.tvContacto);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);

        filtrarCliente(dia);
        listViewCliente.setOnItemClickListener(this);
        listViewCliente.setLongClickable(true);
        listViewCliente.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                TextView tvnombrecliente =  view.findViewById(R.id.tvnombreCliente);
                TextView tvcuentaCliente =  view.findViewById(R.id.tvcuentaCliente);
                TextView tvData =  view.findViewById(R.id.tvdata);
                TextView tvContacto = view.findViewById(R.id.tvContacto);
                TextView tvTelefono = view.findViewById(R.id.tvTelefono);
                TextView tvDireccion = view.findViewById(R.id.tvdireccionCliente);
                TextView tvLimiteCredito = view.findViewById(R.id.tvlimiteCredito);
                TextView tvCuenta = view.findViewById(R.id.tvcuentaCliente);
//                TextView tvdireccionCliente =  view.findViewById(R.id.tvdireccionCliente);
//                TextView tvvendedorCliente =  view.findViewById(R.id.tvvendedorCliente);
//                Intent contexMenu = new Intent(view.getContext(),ContextMenu_Cliente.class);
//                contexMenu.putExtra("nombre",tvnombrecliente.getText().toString());
//                contexMenu.putExtra("cuenta",tvcuentaCliente.getText().toString());
//                contexMenu.putExtra("direccion",tvdireccionCliente.getText().toString());
//                contexMenu.putExtra("vendedor",tvvendedorCliente.getText().toString());
//                contexMenu.putExtra("data",tvData.getText().toString());
                final VariablesGlobal variablesGlobal = (VariablesGlobal) getActivity().getApplicationContext();
                variablesGlobal.setData(tvData.getText().toString());
                variablesGlobal.setCuenta(tvcuentaCliente.getText().toString());
                variablesGlobal.setNombre(tvnombrecliente.getText().toString());
                variablesGlobal.setContacto(tvContacto.getText().toString());
                variablesGlobal.setTelefono(tvTelefono.getText().toString());
                variablesGlobal.setDireccion(tvDireccion.getText().toString());
                variablesGlobal.setLimite(tvLimiteCredito.getText().toString());
                variablesGlobal.setCuenta(tvCuenta.getText().toString());
                Intent Cuenta_por_Cobrar = new Intent(getContext(),Cuentas_por_Cobrar.class);

                startActivity(Cuenta_por_Cobrar);
                return true;
            }

        });
        return viewCliente;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //*se optiene el valor string del textview nombre selecionado
        TextView tvnombrecliente =  view.findViewById(R.id.tvnombreCliente);
        TextView tvcuentaCliente =  view.findViewById(R.id.tvcuentaCliente);
        TextView tvdireccionCliente =  view.findViewById(R.id.tvdireccionCliente);
        TextView tvvendedorCliente =  view.findViewById(R.id.tvvendedorCliente);
        TextView tvContacto =  view.findViewById(R.id.tvContacto);
        TextView tvTelefono =  view.findViewById(R.id.tvTelefono);
        TextView tvSectorCliente =  view.findViewById(R.id.tvSectorCliente);
        TextView tvLimiteCredito =  view.findViewById(R.id.tvlimiteCredito);
        TextView tvBalance =  view.findViewById(R.id.tvBalance);
        TextView tvData =  view.findViewById(R.id.tvdata);
        Intent additem = new Intent(view.getContext(),CarritoActivity.class);
        additem.putExtra("nombre",tvnombrecliente.getText().toString());
        additem.putExtra("cuenta",tvcuentaCliente.getText().toString());
        additem.putExtra("direccion",tvdireccionCliente.getText().toString());
        additem.putExtra("vendedor",tvvendedorCliente.getText().toString());
        additem.putExtra("contacto",tvContacto.getText().toString());
        additem.putExtra("telefono",tvTelefono.getText().toString());
        additem.putExtra("sector",tvSectorCliente.getText().toString());
        additem.putExtra("limiteCredito",tvLimiteCredito.getText().toString());
        additem.putExtra("tvBalance",tvBalance.getText().toString());
        additem.putExtra("data",tvData.getText().toString());

        startActivity(additem);
    }

    public void filtrarCliente(int _dia){
        this.dia = _dia;
        listaCliente = new DataCliente().consultarListaCliente(getActivity().getApplicationContext(),conn,dia);
        miadapatador = new AdaptadorCliente(getActivity(), listaCliente);
        listViewCliente.setAdapter(miadapatador);
    }



    @Override
    public void onResume() {
        super.onResume();
        listaCliente = new DataCliente().consultarListaCliente(getActivity(),conn,dia);
        miadapatador = new AdaptadorCliente(getActivity(), listaCliente);
        listViewCliente.setAdapter(miadapatador);
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getContext(),"registrar",Toast.LENGTH_SHORT).show();

        return true;
    }
}

