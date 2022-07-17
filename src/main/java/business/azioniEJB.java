package business;

import data.ContoCorrente;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import presentation.dao.ContoCorrenteDAO;
import presentation.dao.ContoCorrenteDAOImpl;

/**
 * Session Bean implementation class azioniEJB
 */
@Stateless
@LocalBean
public class azioniEJB implements azioniEJBLocal {
	
	private ContoCorrenteDAO conto=new ContoCorrenteDAOImpl();
	
    public azioniEJB() {
    }

	@Override
	public ContoCorrente getContoCorrente(int numero, int pin) {
		return conto.getContoCorrente(numero,pin);
	}

	@Override
	public boolean versa(int numero, float quantita, int pin) {
		return conto.versa(numero, quantita,pin);
	}

	@Override
	public boolean preleva(int numero, float quantita, int pin) {
		return conto.preleva(numero, quantita, pin);
	}
	public boolean controllaOperazione(String operazione, int numero, float quantita,int pin) {
		int i=0;
		if(conto.esiste(numero, pin)) {
			switch (operazione) {
			case "Scelta": {
				i=1;
				break;
			}
			case "Saldo": {
				i=1;
				break;
			}
			case "Prelievo": {
				if(quantita>0 && conto.getContoCorrente(numero,pin).getSaldo()>= quantita) {
					i=1;
				}
				break;
			}
			case "Versamento": {
				if(quantita>0) {
					i=1;
				}
				break;
			}
			case "Login": {
				i=1;
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + operazione);
			}
		};
		return (i>0);
	}

}
