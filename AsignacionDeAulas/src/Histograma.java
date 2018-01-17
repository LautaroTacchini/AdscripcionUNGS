import java.util.Collections;
import java.util.List;

public class Histograma {
	
	public List<Solicitud> solicitudes;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(List<Solicitud> solicitudes){
		this.solicitudes = Collections.unmodifiableList(solicitudes);	
		
	}
	
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, int hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) && s.getHrIni().getHora() <= hora && s.getHrFin().getHora() >= hora+1){
					cantSolucitudes++;
			}
			// COMPARETO EN HORARIO PARA COMPARAR LAS HORAS
		}		
		return cantSolucitudes;
	}
}
