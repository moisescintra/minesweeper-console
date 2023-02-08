package com.mcc.minefield.view;

import com.mcc.minefield.model.Board;

public class Application {
	public static void main(String[] args) {
		Board board = new Board( 6, 6, 4);
		
		new ConsoleBoard(board);
	}
}
