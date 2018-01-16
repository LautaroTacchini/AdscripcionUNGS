
public class Horario {
	int hora;
	int minutos;
	
	public Horario(int hora, int minutos) {
		assert horaValida(hora);
		assert minutosValidos(minutos);
	}
	
	public boolean horaValida(int hr) {
		return 0<=hr && hr<=23;
	}
	
	public boolean minutosValidos(int minutos) {
		return 0<=minutos && minutos<=59;
	}

}
