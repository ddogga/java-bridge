package bridge;

import java.util.List;

public class Application{

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("다리 건너기 게임을 시작합니다.");
        BridgeGame bridgeGame = new BridgeGame();
        String gameResult = "실패";
        int tryCount = 0;
        while (true){
            tryCount += 1;
            gameResult = bridgeGame.play();
            if (!gameResult.equals("R")) {
                break;
            }
        }


    }


}
