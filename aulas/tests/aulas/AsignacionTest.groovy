package aulas;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import ungs.AsignacionUNGS
import ungs.Id;

class AsignacionTest {

	Id martes = new Id(3, "martes")
	Id jueves = new Id(5, "jueves")
	// see java.text.SimpleDateFormat
	def parseDate = { Date.parse("dd/MM/yyyy", it) }
	def parseTime = { Date.parse("HH:mm", it) }
	Date ene01 = parseDate("01/01/2000")
	Date dic31 = parseDate("31/12/2000")

//	Date am00 = parseTime("00:00")
//	Date am10 = parseTime("10:00")
//	Date pm00 = parseTime("12:00")
	Date pm04 = parseTime("16:00")
	Date pm06 = parseTime("18:00")
	Date pm08 = parseTime("20:00")

	
	Clase asignacionAnual(Date horaDesde, Date horaHasta) {
		return new AsignacionUNGS(fechaDesde: ene01, fechaHasta: dic31, 
			horaDesde: horaDesde, horaHasta: horaHasta)
	}
	
	
//	@Test
	public void testSeSolapan() {
		fail("Not yet implemented");
	}

//	@Test 
	public void testSeSolapaCon() {
//		boolean seSolapaCon(Id dia, Date fecha, Date hora) {
		Clase pm04a06 = asignacionAnual(pm04, pm06)
		assertFalse(pm04a06.seSolapaCon(null, ene01, pm04)) 
	}		
	
	@Test
	public void testEmpiezaAntesQue() {
		Clase pm04a06 = asignacionAnual(pm04, pm06)
		Clase pm04a08 = asignacionAnual(pm04, pm08)
		Clase pm06a08 = asignacionAnual(pm06, pm08)
		assertTrue(pm04a06.terminaAntesQue(pm06a08.fechaDesde, pm06a08.horaDesde))
		assertFalse(pm04a06.terminaAntesQue(pm04a08.fechaDesde, pm04a08.horaDesde))
	}

//	@Test
	public void testTerminaDespuesQue() {
		fail("Not yet implemented");
	}

}
