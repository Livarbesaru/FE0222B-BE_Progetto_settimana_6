package business;

import data.ContoCorrente;
import jakarta.ejb.Local;

@Local
public interface azioniEJBLocal {
	public ContoCorrente getContoCorrente(int numero, int pin);
	
	public boolean versa(int numero, float quantita, int pin);
	
	public boolean preleva(int numero, float quantita, int pin);
	
	public boolean controllaOperazione(String operazione, int numero, float quantita, int pin);
}
