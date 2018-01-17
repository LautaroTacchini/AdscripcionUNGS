package negocio;
import java.util.Collections;
import java.util.List;

public class Histograma {
	
	public List<Solicitud> solicitudes;
	public int intervalo;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(List<Solicitud> solicitudes, int intervalo){
		this.solicitudes = Collections.unmodifiableList(solicitudes);
		this.intervalo = intervalo;	
	}
	
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, Horario hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) &&
               s.getHrIni().getHora() <= hora.getHora() && s.getHrFin().getHora() <= hora.getMinutos()+intervalo)
			{
					cantSolucitudes++;
			}
			//TODO  COMPARETO EN HORARIO PARA COMPARAR LAS HORAS
		}		
		return cantSolucitudes;
	}

	public int cantSolicitudesPorHora(String dia, int hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) && s.getHrIni().getHora() <= hora && s.getHrFin().getHora() >= hora+1){
					cantSolucitudes++;
			}
			//TODO  COMPARETO EN HORARIO PARA COMPARAR LAS HORAS
		}		
		return cantSolucitudes;
	}	
}
