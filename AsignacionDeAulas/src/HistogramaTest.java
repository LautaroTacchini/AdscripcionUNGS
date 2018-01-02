import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HistogramaTest {
	Histograma histograma = new Histograma();
	
	@Test
	public void cantMateriasSolicitantes1() {
		Solicitud s1 = new Solicitud("a", "lunes", 8, 10);
		histograma.agregarSolicitud(s1);
		
		Solicitud s2 = new Solicitud("b", "lunes", 9, 11);
		histograma.agregarSolicitud(s2);
		
		Solicitud s3 = new Solicitud("c", "lunes", 8, 12);
		histograma.agregarSolicitud(s3);		
	
		// Horarios normales.
		assertEquals(0,histograma.cantMateriasSolicitantes("lunes", 7));
		assertEquals(2,histograma.cantMateriasSolicitantes("lunes", 8));
		assertEquals(3,histograma.cantMateriasSolicitantes("lunes", 9));
		assertEquals(2,histograma.cantMateriasSolicitantes("lunes", 10));
		assertEquals(1,histograma.cantMateriasSolicitantes("lunes", 11));
		assertEquals(0,histograma.cantMateriasSolicitantes("lunes", 12));
		assertEquals(2,histograma.cantMateriasSolicitantes("lunes", 8));
		assertEquals(3,histograma.cantMateriasSolicitantes("lunes", 9));	
	}
	
	public void cantMateriasSolicitantes2() {
		assertNotEquals(3,histograma.cantMateriasSolicitantes("martes", 9));
		assertNotEquals(3,histograma.cantMateriasSolicitantes("martes", 10));
	}
	
}
