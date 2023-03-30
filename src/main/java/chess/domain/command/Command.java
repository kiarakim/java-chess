package chess.domain.command;

import static chess.domain.command.CommandCase.*;

import java.util.Arrays;
import java.util.List;

public final class Command {
	private static final String DELIMITER = " ";
	private static final int POSITION_SIZE = 2;
	private static final int COMMAND_INDEX = 0;
	private static final int MOVE_COMMAND_SIZE = 3;
	private static final int SOURCE_POSITION_INDEX = 1;
	private static final int TARGET_POSITION_INDEX = 2;
	private static String source;
	private static String target;
	private final CommandCase commandCase;

	private Command(final CommandCase commandCase) {
		this.commandCase = commandCase;
	}

	public static Command ofStart(final String input) {
		CommandCase value = from(input);
		validateStart(value);
		return new Command(value);
	}

	private static void validateStart(final CommandCase value) {
		if (!value.equals(START)) {
			throw new IllegalArgumentException("게임을 시작하려면 start만 입력해야합니다");
		}
	}

	public static Command ofCommand(final String input) {
		List<String> inputs = Arrays.asList(input.split(DELIMITER));
		CommandCase firstCommand = from(inputs.get(COMMAND_INDEX));

		if (firstCommand.equals(END)) {
			return ofEnd(input);
		}
		if (firstCommand.equals(MOVE)) {
			return ofMove(firstCommand, inputs);
		}
		if (firstCommand.equals(STATUS)) {
			return ofStatus(input);
		}
		throw new IllegalArgumentException("게임 진행중에는 end와 move, status 커맨드 입력만 가능합니다");
	}

	private static Command ofStatus(String input) {
		CommandCase value = from(input.trim());

		if (!value.equals(STATUS)) {
			throw new IllegalArgumentException("게임의 점수를 알려면 status만 입력해야합니다");
		}
		return new Command(value);
	}

	private static Command ofEnd(final String input) {
		CommandCase value = from(input.trim());

		if (!value.equals(END)) {
			throw new IllegalArgumentException("게임을 종료하려면 end만 입력해야합니다");
		}
		return new Command(value);
	}

	private static Command ofMove(final CommandCase commandCase, final List<String> values) {
		validateInputSize(values);
		validateEachPosition(values);
		source = values.get(SOURCE_POSITION_INDEX);
		target = values.get(TARGET_POSITION_INDEX);
		return new Command(commandCase);
	}

	private static void validateInputSize(final List<String> values) {
		if (!(values.size() == MOVE_COMMAND_SIZE)) {
			throw new IllegalArgumentException("게임 이동은 move source target 형식으로 입력해야 합니다.");
		}
	}

	private static void validateEachPosition(final List<String> values) {
		for (int i = SOURCE_POSITION_INDEX; i <= TARGET_POSITION_INDEX; i++) {
			validateInputPositionSize(values.get(i));
		}
	}

	private static void validateInputPositionSize(final String value) {
		if (value.length() != POSITION_SIZE) {
			throw new IllegalArgumentException("게임 이동은 move source target 형식으로 입력해야 합니다.");
		}
	}

	public boolean isEnd() {
		return commandCase.equals(END);
	}

	public boolean isMove() {
		return commandCase.equals(MOVE);
	}

	public boolean isStatus() {
		return commandCase.equals(STATUS);
	}

	public String source() {
		return source;
	}

	public String target() {
		return target;
	}
}
