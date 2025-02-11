package chess.domain.piece;

import static chess.domain.color.Color.*;
import static chess.domain.move.Direction.*;
import static chess.domain.position.File.*;
import static chess.domain.position.Rank.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import chess.domain.color.Color;
import chess.domain.move.Direction;
import chess.domain.position.Position;

public final class Knight extends Piece {
	private static final String name = "n";

	private static final Set<Direction> directions = Set.of(KNIGHT_DOWN_LEFT, KNIGHT_DOWN_RIGHT, KNIGHT_UP_LEFT,
		KNIGHT_UP_RIGHT, KNIGHT_LEFT_UP, KNIGHT_LEFT_DOWN);

	public Knight(final Color color, final Position position) {
		super(color, position);
	}

	public static List<Position> initialBlackPosition() {
		List<Position> positions = new ArrayList<>();
		positions.add(Position.of(B, EIGHT));
		positions.add(Position.of(G, EIGHT));
		return positions;
	}

	public static List<Position> initialWhitePosition() {
		List<Position> positions = new ArrayList<>();
		positions.add(Position.of(B, ONE));
		positions.add(Position.of(G, ONE));
		return positions;
	}

	@Override
	public String name() {
		if (super.color().equals(WHITE)) {
			return name;
		}
		return name.toUpperCase();
	}

	@Override
	public Set<Direction> direction() {
		return ofKnight();
	}

	@Override
	public boolean movable(final Direction direction) {
		return directions.contains(direction);
	}

	@Override
	public boolean movableByCount(final int count) {
		return true;
	}
}
