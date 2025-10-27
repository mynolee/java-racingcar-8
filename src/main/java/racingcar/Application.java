package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Application {

    public static void main(String[] args) {
        printStartMessage();

        List<String> carNames = readCarNames();
        validateCarNames(carNames);

        Map<String, Integer> carScores = initializeScores(carNames);

        int tryTimes = readTryTimes();
        validateTryTimes(tryTimes);

        playRacingGame(carNames, carScores, tryTimes, Application::generateRandomNumber);

        List<String> winners = findWinners(carScores);
        printWinners(winners);
    }

    private static void printStartMessage() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }

    private static List<String> readCarNames() {
        String input = Console.readLine();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .toList();
    }

    private static void validateCarNames(List<String> names) {
        for (String name : names) {
            if (name.isEmpty() || name.length() > 5) {
                throw new IllegalArgumentException("자동차 이름은 1자 이상 5자 이하만 가능합니다.");
            }
        }
    }
}
