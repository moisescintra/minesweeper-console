package com.mcc.minefield.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mcc.minefield.exception.BoomException;

class FieldTest {

	private Field field;
	
	@BeforeEach
	void initField() {
		field = new Field( 3, 3);
	}
	
	@Test
	void testAddNeighborTRUEleft() {
		Field neighbor = new Field( 3, 2);
		assertTrue(field.addNeighbor(neighbor));
	}

	@Test
	void testAddNeighborTRUEright() {
		Field neighbor = new Field( 3, 4);
		assertTrue(field.addNeighbor(neighbor));
	}

	@Test
	void testAddNeighborTRUEup() {
		Field neighbor = new Field( 2, 3);
		assertTrue(field.addNeighbor(neighbor));
	}

	@Test
	void testAddNeighborTRUEdown() {
		Field neighbor = new Field( 4, 3);
		assertTrue(field.addNeighbor(neighbor));
	}

	@Test
	void testAddNeighborTRUEdiagonal() {
		Field neighbor = new Field( 2, 2);
		assertTrue(field.addNeighbor(neighbor));
	}

	@Test
	void testAddNeighborFalse() {
		Field neighbor = new Field( 1, 3);
		assertFalse(field.addNeighbor(neighbor));
	}

	@Test
	void testMark() {
		assertFalse(field.isMarked());
	}

	@Test
	void testChangeMark() {
		field.changeMark();
		assertTrue(field.isMarked());
	}

	@Test
	void testChangeMarkDual() {
		field.changeMark();
		field.changeMark();
		assertFalse(field.isMarked());
	}

	@Test
	void testOpenNoMineNoMarked() {
		assertTrue(field.open());
	}

	@Test
	void testOpenNoMineMarked() {
		field.changeMark();
		assertFalse(field.open());
	}

	@Test
	void testOpenoMineMarked() {
		field.changeMark();
		field.putMine();
		assertFalse(field.open());
	}

	@Test
	void testOpenoMineNoMarked() {
		field.putMine();
		assertThrows(BoomException.class, () -> {
			field.open();
		});
	}

	@Test
	void testOpenoWithNeighborNoMineNoMarked() {
		Field neighbor11 = new Field( 1, 1);

		Field neighbor22 = new Field( 2, 2);
		neighbor22.addNeighbor(neighbor11);

		field.addNeighbor(neighbor22);
		field.open();

		assertTrue(neighbor11.isOpened() && neighbor22.isOpened());
	}

	@Test
	void testOpenoWithNeighborMineNoMarked() {
		Field neighbor11 = new Field( 1, 1);
		Field neighbor12 = new Field( 1, 2);
		neighbor12.putMine();
		
		Field neighbor22 = new Field( 2, 2);
		neighbor22.addNeighbor(neighbor11);
		neighbor22.addNeighbor(neighbor12);

		field.addNeighbor(neighbor22);
		field.open();

		assertTrue(!neighbor11.isOpened() && neighbor22.isOpened());
	}
}
