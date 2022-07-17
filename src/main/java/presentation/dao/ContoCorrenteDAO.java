package presentation.dao;

import data.ContoCorrente;

public interface ContoCorrenteDAO {
	public boolean versa(int numero, float quantita, int pin);
	public ContoCorrente getContoCorrente(int numero, int pin);
	public boolean preleva(int numero, float quantita, int pin);
	public boolean esiste(int numero, int pin);
}
