package bridge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BridgeGameTest {
    private InputStream in;
    private OutputStream out;
    private PrintStream standardOut;
    private BridgeGame bridgeGame;

    @BeforeEach
    final void init() {
        in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
        standardOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        this.bridgeGame = new BridgeGame();
    }


    @AfterEach
    final void printOutput() {
        System.setOut(standardOut);
        System.out.println(out.toString().trim());
    }

    String useMove(String input,String moveDir,int index){

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        bridgeGame.bridge = new ArrayList<>(List.of("U", "D", "U"));
        String result = bridgeGame.move(index, moveDir);
        return result;
    }


    //이동 입력값에 따라 적적한 결과가 반환되는지 테스트
    @DisplayName("사용자 이동 입력 결과에 따른 반환값 테스트")
    @ParameterizedTest
    @CsvSource({"R,U,0,C","R,D,0,R","Q,U,1,Q"})
    void checkMoveReturnValue(String input, String moveDir, int index, String expected) {

        String result = useMove(input,moveDir,index);
        assertThat(result).isEqualTo(expected);
        bridgeGame.retry();
    }

    //이동 입력값에 따라 적절한 결과가 출력되는지 테스트
    @DisplayName("사용자 이동 입력 결과에 따른 출력값 테스트")
    @Test
    void checkMovePrintValue() {

        useMove("R","D",0);
        assertThat(out.toString()).contains(
            "다리의 길이를 입력해주세요.",
            "[ X ]",
            "[   ]"
        );
        bridgeGame.retry();
    }


    //게임을 재시작 할 때, outputView 필드를 제대로 초기화 하는지 테스트
    @DisplayName("게임을 재시작하면 OutputView의 결과맵 필드를 '['로 초기화 한다.")
    @Test
    void checkInitOutputViewField() {
        useMove("R","U",0);
        System.setOut(new PrintStream(out));
        bridgeGame.checkGameResult("R");
        bridgeGame.outputView.printMap();
        assertThat(out.toString()).contains(
            "[",
            "["
        );

    }


    //게임을 끝낼때 성공, 실패 여부에 따른 결과를 잘 출력하는지 테스트
    @DisplayName("result값이 F면 결과를 실패로, S면 성공으로 출력한다.")
    @Test
    void checkFinishResult() {
        bridgeGame.finish("S",3);
        assertThat(out.toString()).contains(
                "[",
                "[",
                "게임 성공 여부: 성공",
                "총 시도한 횟수: 3"
        );
    }

    //play메서드에서 2번째 턴 부터 ]대신 |로 잘 바꿔서 출력 되는지 테스트

    //play메서드에서 게임 결과에 따라 적절한 값이 반환되는지 테스트
}

