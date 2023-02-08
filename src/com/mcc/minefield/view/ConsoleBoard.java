package com.mcc.minefield.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.mcc.minefield.exception.BoomException;
import com.mcc.minefield.exception.ExitException;
import com.mcc.minefield.model.Board;

public class ConsoleBoard {

	private Board board;
	private Scanner scin = new Scanner(System.in);
	
	public ConsoleBoard(Board board) {
		this.board = board;
		
		runGame();
	}

	private void runGame() {
		boolean maintain = true;
		try {
			while(maintain) {
				cycleGame();

				System.out.println("Outra Partida? (S/n)");
				
				if(scin.nextLine().equals("n"))
					maintain = false;
				else
					board.reset();
			}
		} catch (ExitException e) {
			System.out.println("Fim do Jogo!");
		} finally {
			System.out.close();
			scin.close();
		}
		
	}

	private void cycleGame() {
		try {
			while(!board.achievedGoal()) {
				System.out.println(board);
				
				String typed = getValueTyped("Digite ( x, y): ");
				
				Iterator<Integer>xy =  Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim()))
					.iterator();

				typed = getValueTyped("1 - Abrir ou 2 - (Des)Marcar : ");
				
				if("1".equalsIgnoreCase(typed))
					board.open( xy.next(), xy.next());
				else if("2".equalsIgnoreCase(typed))
					board.mark( xy.next(), xy.next());
			}
			System.out.println(board);
			System.out.println("Você Ganhou!");
		} catch (BoomException e) {
			System.out.println(board);
			System.out.println("Game Over!");
		}
	}

	private String getValueTyped(String text) {
		System.out.print(text);
		String typed = scin.nextLine();
		
		if("sair".equalsIgnoreCase(typed))
			throw new ExitException();
		
		return typed;
	}
}
