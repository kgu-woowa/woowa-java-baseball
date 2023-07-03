package baseball.controller;

import baseball.model.Computer;
import baseball.model.User;
import camp.nextstep.edu.missionutils.Console;
import java.util.List;

import static baseball.view.InputView.inputNumber;
import static baseball.view.OutputView.*;

public class BaseballController {

    public static final String NOT_RESTART_STATUS = "2";
    public static final String RESTART_OR_END_ONE_OR_TWO_EXCEPTION = "재시작은 1, 완전 종료는 2 입니다.";
    public static final String RESTART_OR_END_NOT_NUMBER_BECAUSE_STRING_EXCEPTION = "재시작은 1, 완전 종료는 2인 정수입니다.";
    public static final String RESTART_OR_END_NOT_NUMBER_BECAUSE_DOUBLE_EXCEPTION = "재시작은 1, 완전 종료는 2인 정수로 소수를 입력할 수 없습니다.";

    private User user;
    private Computer computer;
    private int strikeCount;
    private int ballCount;

    public BaseballController() {
        this.strikeCount = 0;
        this.ballCount = 0;
    }

    public void run() {
        printGameStartMessage();
        do {
            computer = gameStart();
            playGame();
        } while (gameOver());
    }

    public Computer gameStart() {
        return new Computer();
    }

    public void playGame() {
        user = new User();
        while (true) {
            inputUserNumber();
            strikeCount = getStrikeCount(user, computer);
            ballCount = getBallCount(user, computer);

            printGameResultMessage(strikeCount, ballCount);
            if (strikeCount == 3) {
                break;
            }
        }
    }

    public boolean gameOver() {
        if (isTerminate(terminateSignUserInput())) {
            return true;
        } else {
            return false;
        }
    }

    private void inputUserNumber() {
        user.setNumber(inputNumber());
    }

    public int getStrikeCount(User user, Computer computer) {
        List<Integer> computerRandomNumber = computer.getRandomNumber();
        List<Integer> userNumber = user.getNumber();
        strikeCount = 0;
        for (int i = 0; i < computerRandomNumber.size(); i++) {
            if (isStrike(computerRandomNumber.get(i), userNumber.get(i))) {
                strikeCount += 1;
            }
        }
        return strikeCount;
    }

    public boolean isStrike(Integer computerRandomNumberDigit, Integer userNumberDigit) {
        if (computerRandomNumberDigit == userNumberDigit) {
            return true;
        }
        return false;
    }

    public int getBallCount(User user, Computer computer) {
        List<Integer> computerRandomNumber = computer.getRandomNumber();
        List<Integer> userNumber = user.getNumber();
        ballCount = 0;

        for (int i = 1; i <= 9; i++) {
            if (isBall(computerRandomNumber, userNumber, i)) {
                ballCount += 1;
            }
        }
        return ballCount;
    }

    public boolean isBall(List<Integer> computerRandomNumber, List<Integer> userNumber, int idx) {
        if (hasBothNumberContain(computerRandomNumber, userNumber, idx)) {
            if (computerRandomNumber.indexOf(idx) != userNumber.indexOf(idx)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBothNumberContain(List<Integer> computerRandomNumber, List<Integer> userNumber, int idx) {
        if (computerRandomNumber.contains(idx) && userNumber.contains(idx)) {
            return true;
        }
        return false;
    }

    public boolean isTerminate(String restartStatus) {
        validateRangeRestartStatus(restartStatus);
        validateNotStringRestartStatus(restartStatus);
        validateNotDoubleRestartStatus(restartStatus);

        if(restartStatus.equals(NOT_RESTART_STATUS)){
            return false;
        } else {
            return true;
        }
    }

    public void validateRangeRestartStatus(final String restartStatus) {
        if (Integer.parseInt(restartStatus) < 1 || Integer.parseInt(restartStatus) > 2) {
            throw new IllegalArgumentException(RESTART_OR_END_ONE_OR_TWO_EXCEPTION);
        }
    }

    public void validateNotStringRestartStatus(final String restartStatus) {
        if (!(restartStatus != null && restartStatus.matches("[-+]?\\d*\\.?\\d+"))) {
            throw new IllegalArgumentException(RESTART_OR_END_NOT_NUMBER_BECAUSE_STRING_EXCEPTION);
        }
    }

    public void validateNotDoubleRestartStatus(final String restartStatus) {
        if (!restartStatus.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException(RESTART_OR_END_NOT_NUMBER_BECAUSE_DOUBLE_EXCEPTION);
        }
    }

    private String terminateSignUserInput() {
        return Console.readLine();
    }
}
