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

    private static Map<String, Integer> initializeScores(List<String> names) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String name : names) {
            scores.put(name, 0);
        }
        return scores;
    }

    private static int readTryTimes() {
        System.out.println("시도할 회수는 몇회인가요?");
        return Integer.parseInt(Console.readLine());
    }

    private static void validateTryTimes(int tryTimes) {
        if (tryTimes <= 0) {
            throw new IllegalArgumentException("시도 횟수는 양의 정수여야 합니다.");
        }
    }

    public static void playRacingGame(
            List<String> carNames,
            Map<String, Integer> carScores,
            int tryTimes,
            Supplier<Integer> randomSupplier
    ) {
        System.out.println("\n실행 결과");

        for (int i = 0; i < tryTimes; i++) {
            raceOnce(carNames, carScores, randomSupplier);
            System.out.println();
        }
    }

    private static void raceOnce(List<String> carNames, Map<String, Integer> carScores, Supplier<Integer> randomSupplier) {
        for (String name : carNames) {
            moveCarIfPossible(carScores, name, randomSupplier.get());
            printCarProgress(name, carScores.get(name));
        }
    }

    private static void moveCarIfPossible(Map<String, Integer> carScores, String name, int randomNumber) {
        if (shouldMoveForward(randomNumber)) {
            carScores.put(name, carScores.get(name) + 1);
        }
    }

    public static boolean shouldMoveForward(int randomNumber) {
        return randomNumber >= 4;
    }

    public static int generateRandomNumber() {
        return Randoms.pickNumberInRange(0, 9);
    }

    private static void printCarProgress(String name, int score) {
        System.out.print(name + " : ");
        for (int i = 0; i < score; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static List<String> findWinners(Map<String, Integer> scores) {
        int maxScore = Collections.max(scores.values());
        List<String> winners = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() == maxScore) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }

    private static void printWinners(List<String> winners) {
        System.out.println("\n최종 우승자 : " + String.join(", ", winners));
    }
}
