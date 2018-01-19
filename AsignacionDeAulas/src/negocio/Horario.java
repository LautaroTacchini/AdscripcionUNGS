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
		assert(minutos > 0);
		
		int nuevaHora = 0;
		int nuevosMinutos = 0;
		if(this.minutos + minutos >= 60) {
			nuevaHora = this.hora + 1;
			nuevosMinutos = (this.minutos + minutos) - 60;
		}
		if(nuevaHora >= 23) {
			nuevaHora = (this.hora + nuevaHora) - 24;
		}
		Horario nuevoHorario = new Horario(nuevaHora,nuevosMinutos);
		return nuevoHorario;
	}
	
	
	@Override
	public String toString() {
		return hora + ":" + minutos;
	}

	@Override
	public int compareTo(Horario otro) {
				
		if(hora != otro.getHora())
			return hora - otro.getHora();
		
		return minutos - otro.getMinutos();
	}
	
}
