import static org.junit.Assert.*;

import org.junit.Test;

public class HistogramaTest {
	Histograma histograma = new Histograma();
	
	@Test
	public void cantMateriasTestBasico() {
		Solicitud s1 = new Solicitud("a", "lunes", 8, 10);
		histograma.agregarSolicitud(s1);
		
		Solicitud s2 = new Solicitud("b", "lunes", 9, 11);
		histograma.agregarSolicitud(s2);
		
		Solicitud s3 = new Solicitud("c", "lunes", 8, 12);
		histograma.agregarSolicitud(s3);		
	
		// Horarios normales.
		assertEquals(0,histograma.cantMaterias("lunes", 7));
		assertEquals(2,histograma.cantMaterias("lunes", 8));
		assertEquals(3,histograma.cantMaterias("lunes", 9));
		assertEquals(2,histograma.cantMaterias("lunes", 10));
		assertEquals(1,histograma.cantMaterias("lunes", 11));
		assertEquals(0,histograma.cantMaterias("lunes", 12));
		assertEquals(2,histograma.cantMaterias("lunes", 8));
		assertEquals(3,histograma.cantMaterias("lunes", 9));	
	}
		
}
