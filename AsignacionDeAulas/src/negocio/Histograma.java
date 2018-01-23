package negocio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Histograma {
	
	public List<Solicitud> solicitudes;
	public int solapamiento;
	public int intervalo;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(List<Solicitud> solicitudes, int solapamiento, int intervalo){
		this.solicitudes = Collections.unmodifiableList(solicitudes);
		this.solapamiento = solapamiento;
				
		assert intervaloValido(intervalo);
		this.intervalo = intervalo;
	}
	
	static boolean intervaloValido(int intervalo) {
		if(intervalo <= 60)
			return 60 % intervalo==0;
			
		return intervalo % 60 == 0;			
	}
			
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, Horario horario){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			Horario nuevoHorario = horario.desplazarHorario(solapamiento);
			if(s.getDia().equals(dia) && 
			   s.getHrIni().compareTo(horario) <= 0 && 
			   s.getHrFin().compareTo(nuevoHorario) >= 0)
			{
				cantSolucitudes++;
			}
		}		
		return cantSolucitudes;
	}


	// Dado un dia y una hora de inicio, devuelve la cantidad
	// de solicitudes que tienen ese dia en esa hora.
	public int cantSolicitudesPorHora(String dia, int hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) &&
			   s.getHrIni().getHora() <= hora && s.getHrFin().getHora() >= hora+1){
					cantSolucitudes++;
			}
		}		
		return cantSolucitudes;
	}
	
	// Dado un dia de la semana, devuelve todos los horarios
	// con la cantidad de solicitudes por hora.
	public List<Integer> cantSolicitudesPorHorario(String dia){
		List<Integer> cantMateriasPorHorario = new ArrayList<Integer>();
		List<Horario> horarios = new ArrayList<>();
		
		calcularHorarios(horarios);
	
		for(Horario h :  horarios) {
			cantMateriasPorHorario.add(cantSolicitudes(dia,h));
		}
		
		return cantMateriasPorHorario;
	}
	
	// Dado un dia y su cantidad de solicitudes, devuelve el 
	// horario correspondiente.
	public Horario obtenerHorario(String dia, int cantSolicitudes) {
		List<Horario> horarios = new ArrayList<>();
		calcularHorarios(horarios);
	
		for(Horario h :  horarios) {
			if(cantSolicitudes == cantSolicitudes(dia,h))
				return h;
		}
		
		return new Horario(0,0);
	}

	private void calcularHorarios(List<Horario> horarios) {
		for(int i=0; i<=23; i++) {
			int minutos = 0;
			while(minutos < 59) {
				Horario nuevo = new Horario(i,minutos);
				minutos = minutos + intervalo;
				horarios.add(nuevo);
			}
			minutos = 0;
		}
	}
	
	// Devuelve un String con todos los horarios de un dia.
	public String mostrarSolicitudesPorHorario(String dia){
		String ret = dia;
		for(Integer i : cantSolicitudesPorHorario(dia)) {
			ret += " " + i;
		}
		return ret;
	}
	
	// Dado un dia de la semana, devuelve todos los horarios
	// con la cantidad de solicitudes por hora.
	public List<Integer> cantSolicitudesPorHora(String dia){
		List<Integer> cantMateriasPorHora = new ArrayList<Integer>();
		for(int i=0; i<=23; i++){
			cantMateriasPorHora.add(cantSolicitudesPorHora(dia,i));
		}	
		return cantMateriasPorHora;
	}
	
	// Devuelve un String con todos los horarios de un dia.
	public String mostrarSolicitudesPorHora(String dia){
		String ret = dia;
		for(int i : cantSolicitudesPorHora(dia)) {
			ret += " " + i;
		}
		return ret;
	}
	
}
