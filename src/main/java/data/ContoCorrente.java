package data;

public class ContoCorrente {
	private int numero;
	private String intestatario;
	private long saldo;
	private int pin;
	
	public ContoCorrente() {
	}

	public ContoCorrente(String intestatario, long saldo) {
		this.intestatario = intestatario;
		this.saldo = saldo;
	}

	public ContoCorrente(int numero, String intestatario, long saldo, int pin) {
		this.numero = numero;
		this.intestatario = intestatario;
		this.saldo = saldo;
		this.pin=pin;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public long getSaldo() {
		return saldo;
	}

	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}
	
	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	
	
}
