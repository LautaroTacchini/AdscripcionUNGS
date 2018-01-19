package negocio;
import java.util.Collections;
import java.util.List;

public class Histograma {
	
	public List<Solicitud> solicitudes;
	public int solapamiento;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(List<Solicitud> solicitudes, int intervalo){
		this.solicitudes = Collections.unmodifiableList(solicitudes);
		this.solapamiento = intervalo;	
	}
			
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, Horario horario){
		assert horarioDeInicio(horario);
		
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			Horario nuevoHorario = horario.desplazarHorario(solapamiento);
			if(s.getDia().equals(dia) && s.getHrIni().compareTo(horario) <= 0 && s.getHrFin().compareTo(nuevoHorario) >= 0)
			{
			   cantSolucitudes++;
			}
		}		
		return cantSolucitudes;
	}

	private boolean horarioDeInicio(Horario horario) {
		return horario.getMinutos() == 00;
	}

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
}
