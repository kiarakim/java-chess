package chess.domain;

import static chess.domain.color.Color.*;
import static chess.domain.piece.PieceType.*;
import static chess.domain.position.File.*;
import static chess.domain.position.Rank.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chess.domain.color.Color;
import chess.domain.move.Direction;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.initial.BoardFactory;

public class Board {

	private static final String PAWN_NAME = "P";
	private static final String KING_NAME = "K";
	private static final int NO_PAWN = 0;
	private static final int ONE_PAWN = 1;
	private static final int TARGET_POINT = 1;
	private static Color turn;
	private final Map<Position, Piece> board;

	private Board(final Map<Position, Piece> board) {
		this.board = board;
		turn = WHITE;
	}

	public Board(final Map<Position, Piece> board, Color turn) {
		this.board = board;
		Board.turn = turn;
	}

	public static Board create() {
		return new Board(BoardFactory.create());
	}

	public Piece move(final Position source, final Position target) {
		validateSourceNotEmpty(source);
		isTurn(source);
		validateDifferentPosition(source, target);
		validateTargetNotSameColor(source, target);

		Piece piece = board.get(source);
		Direction unit = Direction.calculateDirection(piece, target);
		validateMovable(piece, unit);
		validatePath(source, target, unit, piece);

		return movePiece(source, target, piece);
	}

	private void validateSourceNotEmpty(final Position source) {
		if (isEmptyPosition(source)) {
			throw new IllegalArgumentException("출발점에 체스말이 존재하지 않습니다");
		}
	}

	private boolean isEmptyPosition(final Position source) {
		return board.get(source).isEmpty();
	}

	private void isTurn(final Position source) {
		final Piece piece = board().get(source);
		if (!piece.isSameColor(turn)) {
			throw new IllegalArgumentException("상대팀의 순서입니다");
		}
	}

	private void validateDifferentPosition(final Position source, final Position target) {
		if (source.equals(target)) {
			throw new IllegalArgumentException("출발지와 도착지는 같을 수 없습니다");
		}
	}

	private void validateTargetNotSameColor(final Position source, final Position target) {
		final Piece sourcePiece = board.get(source);
		final Piece targetPiece = board.get(target);

		if (sourcePiece.isSameColor(targetPiece.color())) {
			throw new IllegalArgumentException("같은 팀은 공격할 수 없습니다");
		}
	}

	private void validateMovable(final Piece piece, final Direction unit) {
		if (!piece.movable(unit)) {
			throw new IllegalArgumentException("체스말이 이동할 수 없는 위치입니다");
		}
	}

	private void validatePath(final Position source, final Position target, final Direction unit, final Piece piece) {
		List<Position> path = calculatePath(source, target, unit);
		validatePathIsEmpty(path);
		validateMovableByCount(piece, path.size());
	}

	private List<Position> calculatePath(Position source, Position target, Direction unit) {
		List<Position> path = new ArrayList<>();
		Position pathPosition = source;
		while (!pathPosition.isSame(target)) {
			pathPosition = pathPosition.nextPosition(unit);
			path.add(pathPosition);
		}

		return path.subList(0, path.size() - TARGET_POINT);
	}

	private void validatePathIsEmpty(final List<Position> path) {
		for (final Position position : path) {
			validatePositionIsEmpty(position);
		}
	}

	private void validatePositionIsEmpty(final Position position) {
		if (!isEmptyPosition(position)) {
			throw new IllegalArgumentException("이동할 경로에 체스말이 존재합니다.");
		}
	}

	private void validateMovableByCount(final Piece piece, final int pathSize) {
		if (!piece.movableByCount(pathSize)) {
			throw new IllegalArgumentException("한 칸만 움직일 수 있는 체스말입니다");
		}
	}

	private Piece movePiece(final Position source, final Position target, final Piece piece) {
		final Piece candidateKing = board.get(target);
		piece.move(target);
		board.put(target, piece);
		board.put(source, new Empty(NONE, source));
		turn = turn.switchTurn();

		return candidateKing;
	}

	public boolean isKing(final Piece king) {
		return king.name().equalsIgnoreCase(KING_NAME);
	}

	public double blackStatus() {
		double score = 0.0;
		for (File file : files()) {
			score += blackScores(file);
		}
		return score;
	}

	private double blackScores(final File file) {
		double newScore = 0;
		int count = 0;
		for (Rank rank : ranks()) {
			Piece piece = board.get(Position.of(file, rank));
			newScore += updateScore(piece, BLACK);
			count += pawnCount(piece.name(), BLACK);
		}
		return newScore + addPawnScore(count);
	}

	private double updateScore(final Piece piece, final Color color) {
		if (piece.color() == color) {
			return pieceScore(piece.type());
		}
		return 0;
	}

	private double pieceScore(final PieceType type) {
		if (type == ROOK) {
			return ROOK.score();
		}
		if (type == KNIGHT) {
			return KNIGHT.score();
		}
		if (type == BISHOP) {
			return BISHOP.score();
		}
		if (type == QUEEN) {
			return QUEEN.score();
		}
		return 0;
	}

	private int pawnCount(final String name, final Color color) {
		if (color == BLACK && name.equals(PAWN_NAME)) {
			return 1;
		}
		if (color == WHITE && name.equals(PAWN_NAME.toLowerCase())) {
			return 1;
		}
		return 0;
	}

	private double addPawnScore(final int count) {
		if (count == NO_PAWN) {
			return 0;
		}
		if (count == ONE_PAWN) {
			return PAWN.score();
		}
		return PAWNS.score() * count;
	}

	public double whiteStatus() {
		double score = 0.0;
		for (File file : files()) {
			score += whiteScores(file);
		}
		return score;
	}

	private double whiteScores(final File file) {
		double newScore = 0;
		int count = 0;
		for (Rank rank : ranks()) {
			Piece piece = board.get(Position.of(file, rank));
			newScore += updateScore(piece, WHITE);
			count += pawnCount(piece.name(), WHITE);
		}
		return newScore + addPawnScore(count);
	}

	public Map<Position, Piece> board() {
		return board;
	}

	public Color turn(){
		return turn;
	}
}
