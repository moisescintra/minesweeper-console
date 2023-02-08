package com.mcc.minefield.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

	private int lines;
	private int columns;
	private int mines;

	private final List<Field> fields = new ArrayList<>();

	public Board(int lines, int columns, int mines) {
		setLines(lines);
		setColumns(columns);
		setMines(mines);
		
		createField();
		connectNeighbors();
		randomMines();
	}

	private void createField() {
		for (int l = 0; l < getLines(); l++) {
			for (int c = 0; c < getColumns(); c++) {
				fields.add(new Field( l, c));
			}
		}
	}

	private void connectNeighbors() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbor(f2);
			}
		}
	}

	private void randomMines() {
		long setMines = 0;
		Predicate<Field> filterMine = m -> m.isMine();
		int random;
		do {
			random = (int) (Math.random() * fields.size());
			fields.get(random).putMine();
			setMines = fields.stream().filter(filterMine).count();
		}while(setMines < getMines());
	}

	public void open(int line, int column) {
		try {
			fields.parallelStream()
				.filter(f -> f.getLine() == line
					&& f.getColumn() == column)
				.findFirst()
				.ifPresent(f -> f.open());
		} catch (Exception e) {
			fields.forEach(f -> f.setOpened(true));
			throw e;// relaunch exception
		}
	}

	public void mark(int line, int column) {
		fields.parallelStream()
		.filter(f -> f.getLine() == line
			&& f.getColumn() == column)
		.findFirst()
		.ifPresent(f -> f.changeMark());;
	}

	public boolean achievedGoal() {
		return fields.stream().allMatch(f -> f.achievedGoal());
	}

	public void reset() {
		fields.stream().forEach(f -> f.reset());
		randomMines();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;

		sb.append("  ");
		for (int c = 0; c < getColumns(); c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}

		sb.append("\n");

		for (int l = 0; l < getLines(); l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < getColumns(); c++) {
				sb.append(" ");
				sb.append(fields.get(i++));
				sb.append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private int getLines() {
		return lines;
	}

	private void setLines(int lines) {
		this.lines = lines;
	}

	private int getColumns() {
		return columns;
	}

	private void setColumns(int columns) {
		this.columns = columns;
	}

	private int getMines() {
		return mines;
	}

	private void setMines(int mines) {
		this.mines = mines;
	}
}
