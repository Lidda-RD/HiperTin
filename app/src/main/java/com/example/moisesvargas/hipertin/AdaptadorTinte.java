package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moisesvargas.hipertin.CapaData.ConexionSqlite;
import com.example.moisesvargas.hipertin.CapaData.DataTinte;

import java.io.File;
import java.util.List;

public class AdaptadorTinte extends BaseAdapter{

    Context contexto;
    List<DataTinte> ListaObjetos;


    public AdaptadorTinte(Context contexto, List<DataTinte> listaObjetos) {
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
        vista = inflate.inflate(R.layout.row_tinte, null);

        TextView Descripcion = (TextView) vista.findViewById(R.id.tvDescripcionTinte);
        TextView Codigo = (TextView) vista.findViewById(R.id.tvCodigoTinte);
        TextView Precio = vista.findViewById(R.id.tvPrecioTinte);
        TextView Cantidad = vista.findViewById(R.id.tvCantidadTinte);
        TextView literal = vista.findViewById(R.id.literal);
        TextView tvitbis = vista.findViewById(R.id.tvItbisTinte);
        ImageView imgArticulo = vista.findViewById(R.id.imgArticulo);

        Descripcion.setText(ListaObjetos.get(position).getDescripcion().toString());
        Codigo.setText(ListaObjetos.get(position).getCodigo().toString());
        Precio.setText(ListaObjetos.get(position).getPrecio().toString());
        tvitbis.setText(ListaObjetos.get(position).getItbis().toString());
        Cantidad.setText(ListaObjetos.get(position).getCantidad().toString());
        if(ListaObjetos.get(position).getCantidad().toString().equals("0")){
            Cantidad.setVisibility(View.INVISIBLE);
            literal.setVisibility(View.INVISIBLE);
        }
        //Uri myUri = (Uri.parse("/storage/emulated/0/Imagenes/001.png"));
        String direcionImagen = "/storage/emulated/0/Hipertin/" + ListaObjetos.get(position).getCodigo().trim() + ".png";
        File imagen = new File(direcionImagen);
        if (imagen.exists())
        {
            Uri myUri = (Uri.parse(direcionImagen));
            imgArticulo.setImageURI(myUri);
            imgArticulo.setTag(direcionImagen);
        }else {
            Uri myUri = (Uri.parse("/storage/emulated/0/Hipertin/Generica.png"));
            imgArticulo.setImageURI(myUri);
            imgArticulo.setTag(direcionImagen);
        }

        return vista;
    }


}
