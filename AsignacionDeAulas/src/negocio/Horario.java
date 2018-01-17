package negocio;

public class Horario {
	private int hora;
	private int minutos;
	
	public Horario(int hora, int minutos) {
		assert horaValida(hora);
		this.hora = hora;
		
		assert minutosValidos(minutos);
		this.minutos = minutos;
	}
	
	public boolean horaValida(int hr) {
		return 0<=hr && hr<=23;
	}
	
	public boolean minutosValidos(int minutos) {
		return 0<=minutos && minutos<=59;
	}
	
	public int getHora() {
		return hora;
	}
	
	public int getMinutos() {
		return minutos;
	}
	
	public boolean compareTo(Horario hora) {
		
		return false;
	}
	
}