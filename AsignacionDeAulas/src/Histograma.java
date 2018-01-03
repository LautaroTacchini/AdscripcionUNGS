import java.util.ArrayList;
import java.util.List;

public class Histograma {
	List<Solicitud> solicitudes;
	
	// Represeta todas las solicitudes registradas.
	public Histograma(){
		solicitudes = new ArrayList<Solicitud>();		
	}
	
	// Agrega una solicitud al histograma.
	public void agregarSolicitud(Solicitud solicitud){
		solicitudes.add(solicitud);
	}
	
	// Dado un dia, con un horario de inicio,
	// devuelve la cantidad de materias que solicitaron ese horario.	
	public int cantSolicitudes(String dia, int hora){
		int cantSolucitudes = 0;
		for(Solicitud s : solicitudes) {
			if(s.getDia().equals(dia) && s.getHrIni() <= hora && s.getHrFin() >= hora+1){
					cantSolucitudes++;
				}
		}		
		return cantSolucitudes;
	}
	
	public List<Integer> cantPorHora(String dia){
		List<Integer> cantMateriasPorHora = new ArrayList<Integer>();
		
		for(int i=0; i<=23; i++){
			cantMateriasPorHora.add(cantSolicitudes(dia,i));
		}
		
		return cantMateriasPorHora;
	}
	
	public String imprimir(String dia){
		String ret = dia;
		
		List<Integer> cantMaterias = cantPorHora(dia);
		//for(Integer i : cantMaterias)
		for(int indice =0; indice< cantMaterias.size();indice++){
			ret = ret + " " +cantMaterias.get(indice);
		}
		
		return ret;
	}
	
}
