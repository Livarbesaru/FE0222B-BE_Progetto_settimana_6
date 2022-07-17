package presentation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

import business.ConnectionFactory;
import data.ContoCorrente;

public class ContoCorrenteDAOImpl implements ContoCorrenteDAO{
	private static String getConto="select * from contocorrente where numero=? and pin=?";
	private static String cashInOut="UPDATE public.contocorrente"
			+ "	SET saldo=?"
			+ "	WHERE numero=?;";
    private static final DecimalFormat df = new DecimalFormat("0.00");

	@Override
	public boolean versa(int numero, float quantita,int pin) {
		Connection conn=ConnectionFactory.getConnection();
		int i=0;
		PreparedStatement st=null;
		ContoCorrente conto=getContoCorrente(numero,pin); 
		if(quantita>0) {
			try {
				st=conn.prepareStatement(cashInOut);
				String decimali=df.format(conto.getSaldo()+quantita);
				decimali=decimali.replace(',', '.');
				st.setFloat(1, Float.parseFloat(decimali));
				st.setInt(2, numero);
				i=st.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
		}
		try {conn.close();} catch (Exception e) {}
		try {st.close();} catch (Exception e) {}
		return i>0;
	}

	@Override
	public ContoCorrente getContoCorrente(int numero, int pin) {
		Connection conn=ConnectionFactory.getConnection();
		PreparedStatement st=null;
		ContoCorrente conto=null;
		java.sql.ResultSet rs=null;
		
		try {
			st=conn.prepareStatement(getConto);
			st.setInt(1, numero);
			st.setInt(2, pin);
			rs=st.executeQuery();
			while(rs.next()) {
				conto=new ContoCorrente(rs.getInt("numero"),rs.getString("intestatario"),rs.getLong("saldo"),rs.getInt("pin"));
			}
		} catch (SQLException e) {e.printStackTrace();}
		try {conn.close();} catch (Exception e) {}
		try {st.close();} catch (Exception e) {}
		try {rs.close();} catch (Exception e) {}
		
		return conto;
	}

	@Override
	public boolean preleva(int numero, float quantita,int pin) {
		Connection conn=ConnectionFactory.getConnection();
		int i=0;
		PreparedStatement st=null;
		ContoCorrente conto=getContoCorrente(numero,pin); 
		if(quantita>0 && conto.getSaldo()>=quantita) {
			try {
				st=conn.prepareStatement(cashInOut);
				String decimali=df.format(conto.getSaldo()-quantita);
				decimali=decimali.replace(',', '.');
				st.setFloat(1, Float.parseFloat(decimali));
				st.setInt(2, numero);
				i=st.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
		}
		try {conn.close();} catch (Exception e) {}
		try {st.close();} catch (Exception e) {}
		return i>0;
	}

	@Override
	public boolean esiste(int numero, int pin) {
		return getContoCorrente(numero,pin)!=null;
	}

}
