package chess.domain.piece;

import static chess.domain.color.Color.*;
import static chess.domain.position.File.*;
import static chess.domain.position.Rank.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.move.Direction;
import chess.domain.position.Position;

class PawnTest {

	@Test
	@DisplayName("폰이 검은말(대문자)이라면 아래로만 이동할 수 있다")
	void movableDownIfBlack() {
		// given
		final Position position = Position.of(C, TWO);
		final Pawn pawn = new Pawn(BLACK, position);

		// then
		assertTrue(pawn.movable(Direction.DOWN));
	}

	@Test
	@DisplayName("폰이 흰말(소문자)이라면 위로만 이동할 수 있다")
	void movableUpIfWhite() {
		// given
		final Position position = Position.of(C, TWO);
		final Pawn pawn = new Pawn(WHITE, position);
		// then
		assertTrue(pawn.movable(Direction.UP));
	}

	@Test
	@DisplayName("폰은 처음에 두 칸까지 이동할 수 있다.")
	void movableByCount_firstMove() {
		// given
		final Position position = Position.of(C, TWO);
		final Pawn pawn = new Pawn(BLACK, position);

		// then
		assertTrue(pawn.movableByCount(1));
	}

	@Test
	@DisplayName("폰은 두 번째 이동부터 두 칸 이상을 이동할 수 없다.")
	void notMovableByCount_secondMove() {
		// given
		final Position position = Position.of(C, TWO);
		final Pawn pawn = new Pawn(BLACK, position);

		// when
		pawn.movableByCount(2);

		// then
		assertFalse(pawn.movableByCount(2));
	}

	@Test
	@DisplayName("폰은 두 번째 이동부터 한 칸만 이동할 수 있다.")
	void movableByCount_secondMove() {
		// given
		final Position position = Position.of(C, TWO);
		final Pawn pawn = new Pawn(BLACK, position);

		// when
		pawn.movableByCount(1);

		// then
		assertTrue(pawn.movableByCount(0));
	}
}
