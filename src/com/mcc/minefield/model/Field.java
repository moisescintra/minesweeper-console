package com.mcc.minefield.model;

import java.util.ArrayList;
import java.util.List;

import com.mcc.minefield.exception.BoomException;

public class Field {
	
	private final int line;
	private final int column;
	
	private boolean opened = false;
	private boolean mine = false;
	private boolean marked = false;
	
	private List<Field> neighbors = new ArrayList<>();

	//package
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	//package
	boolean addNeighbor (Field neighbor) {
		boolean differentLine = this.line != neighbor.line;
		boolean differentColumn = this.column != neighbor.column;
		boolean diagonal = differentLine && differentColumn;
		
		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int deltaTotal = deltaLine + deltaColumn;
		
		if(deltaTotal == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(deltaTotal == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else
			return false;
	}

	//package
	void changeMark() {
		if(!isOpened())
			setMarked(!isMarked());
	}

	//package
	boolean open() {
		if(!isOpened() && !isMarked()) {
			setOpened(true);

			if(mine)
				throw new BoomException();

			if(safeNeighborhood())
				neighbors.forEach(n -> n.open());

			return true;
		}
		return false;
	}

	//package
	void putMine() {
		if(!isMine())
			setMine(true);
	}

	private boolean safeNeighborhood() {
		return neighbors.stream().noneMatch(n -> n.isMine());
	}

	//package
	boolean achievedGoal() {
		boolean unraveled = !isMine() && isOpened();
		boolean secured = isMine() && isMarked();
		return unraveled || secured;
	}

	private long mineInNeighborhood() {
		return neighbors.stream().filter(n -> n.isMine()).count();
	}

	//package
	void reset() {
		setMarked(false);
		setMine(false);
		setOpened(false);
	}

	@Override
	public String toString() {
		if(isMarked())
			return "x";
		else if(isOpened() && isMine())
			return "*";
		else if(isOpened() && mineInNeighborhood() > 0)
			return Long.toString(mineInNeighborhood());
		else if(isOpened())
			return " ";
		else
			return "?";
	}

	//package
	int getLine() {
		return this.line;
	}

	//package
	int getColumn() {
		return this.column;
	}

	//package
	boolean isOpened() {
		return this.opened;
	}

	//package
	void setOpened(boolean opened) {
		this.opened = opened;
	}

	//package
	boolean isMine() {
		return mine;
	}

	private void setMine(boolean mine) {
		this.mine = mine;
	}

	//package
	boolean isMarked() {
		return marked;
	}

	private void setMarked(boolean marked) {
		this.marked = marked;
	}
}
