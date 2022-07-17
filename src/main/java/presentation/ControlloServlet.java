package presentation;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import business.azioniEJB;
import business.azioniEJBLocal;
import data.ContoCorrente;

/**
 * Servlet implementation class controllo
 */
public class ControlloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static 	ContoCorrente conto;
       
	azioniEJBLocal azioni=new azioniEJB();
    public ControlloServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long quantita=(long)((request.getParameter("quantita")!="")
				?Long.parseLong(request.getParameter("quantita")):0.0);
			
			String evento=request.getParameter("evento");
			String azione=request.getParameter("azione");
			if (evento.equals("Login")) {
				System.out.println("Dio porco");
				if(evento.equals("Login") && azione.equals("Scelta")) {
					conto = azioni.getContoCorrente(Integer.parseInt(request.getParameter("numeroConto")),
							Integer.parseInt(request.getParameter("pin")));
				}
				ridireziona(azione, request, response);
			} else if(evento.equals("azione")){
				if (azioni.controllaOperazione(request.getParameter("azione"),
						conto.getNumero(), quantita,
						conto.getPin())) {
				azioni(azione, request, response,conto);
				}
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void ridireziona(String operazione, HttpServletRequest request, HttpServletResponse response) {
		switch (operazione) {
		case "Saldo": {
			try {request.getRequestDispatcher("saldo.jsp").forward(request, response);}
			catch (ServletException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			break;
		}
		case "Prelievo": {
			try {request.getRequestDispatcher("prelievo.jsp").forward(request, response);}
			catch (ServletException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			break;
		}
		case "Versamento": {
			try {request.getRequestDispatcher("versamento.jsp").forward(request, response);}
			catch (ServletException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			break;
		}
		case "Scelta": {
			try {request.getRequestDispatcher("scelta.jsp").forward(request, response);}
			catch (ServletException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operazione);
		}
	}
	
	protected void azioni(String operazione, HttpServletRequest request, HttpServletResponse response,ContoCorrente conto) {
		switch (operazione) {
		case "Saldo": {
			saldo(request, response, conto);
			break;
		}
		case "Prelievo": {
			prelievo(request, response, conto);
			break;
		}
		case "Versamento": {
			versamento(request, response, conto);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operazione);
		}
	}
	
	public void saldo(HttpServletRequest request,HttpServletResponse response, ContoCorrente conto) {
		PrintWriter out=null;
		try {out = response.getWriter();
		out.println("Il tuo saldo e' di: "+conto.getSaldo()+" Eur");
	} 
	catch (IOException e) {e.printStackTrace();}
	}
	
	public void versamento(HttpServletRequest request,HttpServletResponse response, ContoCorrente conto) {
		PrintWriter out = null;
		azioni.versa(conto.getNumero(),
				Long.parseLong(request.getParameter("quantita")), conto.getPin());
		try {
			out = response.getWriter();
			out.println("Il tuo saldo era di: " + conto.getSaldo()+" Eur");
			out.println("Hai versato " + Long.parseLong(request.getParameter("quantita"))+" Eur");
			out.println(
					"Il tuo saldo ora e' di: " + 
			(conto.getSaldo() + Long.parseLong(request.getParameter("quantita")))+" Eur");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prelievo(HttpServletRequest request, HttpServletResponse response, ContoCorrente conto) {
		PrintWriter out = null;
		azioni.preleva(conto.getNumero(),
				Long.parseLong(request.getParameter("quantita")), conto.getPin());
		try {
			out = response.getWriter();
			out.println("Il tuo saldo era di: " + conto.getSaldo()+" Eur");
			out.println("Hai prelevato " + Long.parseLong(request.getParameter("quantita"))+" Eur");
			out.println(
					"Il tuo saldo ora e' di: " + 
			(conto.getSaldo() - Long.parseLong(request.getParameter("quantita")))+" Eur");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
