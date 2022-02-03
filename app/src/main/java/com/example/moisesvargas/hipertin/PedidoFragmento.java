package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.DataPedido;
import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;

import java.util.ArrayList;


public class PedidoFragmento extends Fragment implements ListView.OnItemClickListener {


    //view
    ListView listViewPedido;
    View viewPedido;
    ConexionSqlite conn;
    ArrayList<String>listaInformacion;

    //Propiedades
    AdaptadorPedido miadapatadorPedido;
    ArrayList<DataPedido> listaPedido;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewPedido = inflater.inflate(R.layout.fragment_pedido, container, false);
        listViewPedido = viewPedido.findViewById(R.id.lstPedido);
        conn = new ConexionSqlite(getActivity(),"nextsales",null,1);



        listaPedido = new ArrayList<DataPedido>();
        listaPedido = new DataPedido().consultarListaPedio(getActivity(),conn);
        miadapatadorPedido = new AdaptadorPedido(getActivity(), listaPedido);
        listViewPedido.setAdapter(miadapatadorPedido);

        swipeRefreshLayout = (SwipeRefreshLayout) viewPedido.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaPedido = new DataPedido().consultarListaPedio(getActivity(),conn);
                miadapatadorPedido = new AdaptadorPedido(getActivity(), listaPedido);
                listViewPedido.setAdapter(miadapatadorPedido);
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        listViewPedido.setOnItemClickListener(this);

        return viewPedido;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String optenerNombre = (String) listaPedido.get(position).getNombre();
        //String optenerPedido = (String) listaPedido.get(position).getPedido();

        TextView tvnombrePedido =  view.findViewById(R.id.tvnombrePedido);
        TextView tvnoPedido =  view.findViewById(R.id.tvnoPedido);
        TextView tvimportePedido  =  view.findViewById(R.id.tvimportePedido);
        TextView tvfechaPedido  =  view.findViewById(R.id.tvfechaPedido);
        TextView tvCuentaPedido  =  view.findViewById(R.id.tvCuentaPedido);
        TextView tvDireccionPedido  =  view.findViewById(R.id.tvDireccionPedido);

        Intent _carrito = new Intent(view.getContext(),CarritoActivity.class);
        _carrito.putExtra("nombre",tvnombrePedido.getText().toString() );
        _carrito.putExtra("documento", tvnoPedido.getText().toString());
        _carrito.putExtra("importe",tvimportePedido.getText().toString());
        _carrito.putExtra("fecha",tvfechaPedido.getText().toString());
        _carrito.putExtra("cuenta",tvCuentaPedido.getText().toString());
        _carrito.putExtra("direccion",tvDireccionPedido.getText().toString());

        startActivity(_carrito);
    }
}
