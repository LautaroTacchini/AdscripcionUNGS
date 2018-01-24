package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import negocio.Horario;

public class HorarioTest {
	
	@Test (expected = AssertionError.class)
	public void horarioValido1() {
		new Horario(0,-1);
		new Horario(-1,0);
		new Horario(24,0);
		new Horario(0,60);
		new Horario(24,59);		
	}
	
	@Test 
	public void desplazarHorario() {
		Horario horario1 = new Horario(0,0);
		Horario nHorario1 = horario1.desplazarHorario(15);
		assertEquals(15, nHorario1.getMinutos());
		assertEquals(0, nHorario1.getHora());
		
		Horario horario2 = new Horario(0,0);
		Horario nHorario2 = horario2.desplazarHorario(30);
		assertEquals(30, nHorario2.getMinutos());
		assertEquals(0, nHorario2.getHora());
		
		Horario horario3 = new Horario(8,45);
		Horario nHorario3 = horario3.desplazarHorario(30);
		assertEquals(15, nHorario3.getMinutos());
		assertEquals(9, nHorario3.getHora());
		
		Horario horario4 = new Horario(8,59);
		Horario nHorario4 = horario4.desplazarHorario(2);
		assertEquals(1, nHorario4.getMinutos());
		assertEquals(9, nHorario4.getHora());
		

		Horario horario5 = new Horario(23,59);
		Horario nHorario5 = horario5.desplazarHorario(1);
		assertEquals(0, nHorario5.getMinutos());
		assertEquals(0, nHorario5.getHora());
	}
	
	@Test
	public void compareTo() {
		Horario hr1 = new Horario(8,30);
		Horario hr2 = new Horario(8,45);
		assertTrue(hr1.compareTo(hr2) < 0);
		assertTrue(hr2.compareTo(hr1) > 0);
		

		Horario hr3 = new Horario(23,59);
		Horario hr4 = new Horario(0,0);
		assertTrue(hr3.compareTo(hr4) > 0);
		assertTrue(hr4.compareTo(hr3) < 0);

		Horario hr5 = new Horario(9,0);
		Horario hr6 = new Horario(9,1);
		assertTrue(hr5.compareTo(hr6) < 0);
		assertTrue(hr6.compareTo(hr5) > 0);
	
	}
	
}
