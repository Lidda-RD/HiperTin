package com.example.moisesvargas.hipertin.CapaData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataMovimiento {

    String secuencia;

    public DataMovimiento(String secuencia) {
        this.secuencia = secuencia;
    }

    public DataMovimiento() {

    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public DataMovimiento getMovimiento(String _data, String tipomovi)
    {
        DataMovimiento movimiento = new DataMovimiento();

        try{
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String select = "SELECT secuencia FROM "+_data+".DBO.MOVIMIENTO WHERE codigo='130001'";
            ResultSet rs = st.executeQuery(select);
            rs.next();
            movimiento.setSecuencia(rs.getString("secuencia"));

            String select2 = "SELECT * FROM "+_data+".DBO.PEDIDO_HDR WHERE documento='"+movimiento.getSecuencia()+"'";
            ResultSet rs2 = st.executeQuery(select2);
            rs2.next();
            if(movimiento.getSecuencia() == rs.getString("documento")){
                ActualizarSecuencia(_data);
                movimiento.setSecuencia(movimiento.getSecuencia()+1);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return movimiento;
    }

    public void ActualizarSecuencia(String _data)
    {
        try{
            Connection con = Conexion.ConexionLocal();
            Statement st =  con.createStatement();
            String update = "UPDATE "+_data+".DBO.MOVIMIENTO SET secuencia = secuencia + 1 WHERE (codigo = '130001')";
            st.executeUpdate(update);
        }catch (Exception e)
        {
            e.getMessage();
        }
    }
}
