package baseball.controller;

import baseball.model.Computer;
import baseball.model.User;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class BaseballController {

    public void gameStart(User user) {
        String inputNumber = Console.readLine();
        List<Integer> number = new ArrayList<>();

        for (int i = 0; i < inputNumber.length(); i++) {
            number.add(Integer.valueOf(inputNumber.charAt(i)));
        }

        user.setNumber(number);
    }

    public int getStrikeCount(User user, Computer computer) {
        List<Integer> computerRandomNumber = computer.getRandomNumber();
        List<Integer> userNumber = user.getNumber();
        int strikeCnt = 0;
        for (int i = 0; i < computerRandomNumber.size(); i++) {
            if (isStrike(computerRandomNumber.get(i), userNumber.get(i))) {
                strikeCnt += 1;
            }
        }

        return strikeCnt;
    }

    public boolean isStrike(Integer computerRandomNumberDigit, Integer userNumberDigit) {
        if (computerRandomNumberDigit == userNumberDigit) {
            return true;
        }

        return false;
    }

    /*
    1 ~ 9까지보면서 컴퓨터와 사용자가 둘 다 가지고 있는데 contain
    인덱스 자리는 다른 거의 갯수 세기
    */
    public int getBallCount(User user, Computer computer) {
        List<Integer> computerRandomNumber = computer.getRandomNumber();
        List<Integer> userNumber = user.getNumber();
        int ballCnt = 0;

        for (int i = 1; i <= 9; i++) {
            if (isBall(computerRandomNumber, userNumber, i)) {
                ballCnt += 1;
            }
        }

        return ballCnt;
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

    public void terminateGame(User user) {
        if (isTerminate(terminateSignUserInput())) {
            user.terminate();
            System.out.println("완전 종료");
        } else {
            user.restart();
        }
    }

    public boolean isTerminate(String restartStatus) {
        validateRangeRestartStatus(restartStatus);
        validateNotStringRestartStatus(restartStatus);
        validateNotDoubleRestartStatus(restartStatus);

        if(restartStatus.equals("2") ){
            return true;
        } else {
            return false;
        }
    }



    public void validateRangeRestartStatus(final String restartStatus) {
        if (Integer.parseInt(restartStatus) < 1 || Integer.parseInt(restartStatus) > 2) {
            throw new IllegalArgumentException("재시작은 1, 완전 종료는 2 입니다.");
        }
    }

    public void validateNotStringRestartStatus(final String restartStatus) {
        if ( !(restartStatus != null && restartStatus.matches("[-+]?\\d*\\.?\\d+")) ) {
            throw new IllegalArgumentException("재시작은 1, 완전 종료는 2인 정수입니다.");
        }
    }

    public void validateNotDoubleRestartStatus(final String restartStatus) {
        if (!restartStatus.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("재시작은 1, 완전 종료는 2인 정수로 소수를 입력할 수 없습니다.");
        }
    }

    private String terminateSignUserInput() {
        return Console.readLine();
    }

    public void restartGame() {
        // 게임을 끝내지 않을 것이라는 신호를 줌
    }
}
