package com.codesquad.blackjack;

public class BlackjackApplication {

	public static void main(String[] args) throws Exception{
		System.out.println("간단 카드 게임을 시작합니다.\n");

		Game game = new Game();
		game.startGame();

		System.out.println("게임을 종료합니다.\n" +
				"플레이해주셔서 감사합니다.");
	}

}
