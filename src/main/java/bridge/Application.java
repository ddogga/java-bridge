package bridge;

import java.util.List;

public class Application{

    public static void main(String[] args) {
        // TODO: 프로그램 구현

        BridgeGame bridgeGame = new BridgeGame();
        NewGame newGame = new NewGame(bridgeGame);

        newGame.startNewGame();


    }





}
