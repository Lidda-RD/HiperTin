package com.example.moisesvargas.hipertin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class ContextMenu_Cliente extends AppCompatActivity {

    Button btnCxC,btnPedido;
    String numeroPedido,nombre,cuenta,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu__cliente);

        //Asignar tamaño del popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(heigth*.4));

        btnCxC = findViewById(R.id.btnCxC);
        btnPedido = findViewById(R.id.btnPedidoContext);

        nombre = getIntent().getExtras().getString("nombre");
        cuenta =getIntent().getExtras().getString("cuenta");
        data =getIntent().getExtras().getString("data");
        final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
        variablesGlobal.setData(data);
        variablesGlobal.setCuenta(cuenta);
        variablesGlobal.setNombre(nombre);
        btnCxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Cuenta_por_Cobrar = new Intent(ContextMenu_Cliente.this,Cuentas_por_Cobrar.class);

                startActivity(Cuenta_por_Cobrar);
                finish();
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent additem = new Intent(v.getContext(),CarritoActivity.class);
                additem.putExtra("nombre",nombre);
                additem.putExtra("cuenta",cuenta);
                additem.putExtra("data",data);
                startActivity(additem);
                finish();
            }
        });

    }
}
