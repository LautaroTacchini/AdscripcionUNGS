package negocio;

public class Horario implements Comparable<Horario>{
	private int hora;
	private int minutos;
	
	public Horario(int hora, int minutos) {
		assert horaValida(hora);
		this.hora = hora;
		
		assert minutosValidos(minutos);
		this.minutos = minutos;
	}
	
	public boolean horaValida(int hr) {
		return 0 <= hr && hr<=23;
	}
	
	public boolean minutosValidos(int minutos) {
		return 0 <= minutos && minutos <= 59;
	}
	
	public int getHora() {
		return hora;
	}
	
	public int getMinutos() {
		return minutos;
	}
	
	public Horario desplazarHorario(int minutos) {
		assert(minutos > 0 && minutos < 60);
		
		int nuevaHora = hora;
		int nuevosMinutos = minutos + this.minutos;
		
		if(nuevosMinutos >= 60) {
			nuevaHora++;
			nuevosMinutos -= 60;
		} 
		if(nuevaHora >= 24) {
			nuevaHora -= 24;
		}
		return new Horario(nuevaHora,nuevosMinutos);
	}
	
	
	@Override
	public String toString() {
		return String.format("%02d",hora) + ":" + String.format("%02d", minutos);
	}

	@Override
	public int compareTo(Horario otro) {
				
		if(hora != otro.getHora())
			return hora - otro.getHora();
		return minutos - otro.getMinutos();
	}
	
}
