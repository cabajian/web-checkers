package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostCheckTurnRouteTest {

    private PostCheckTurnRoute CuT;
    private Gson gson;
    private Request request;
    private Response response;
    private Session session;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        // create a unique CuT for each test
        CuT = new PostCheckTurnRoute(gson);
    }

    /**
     * Test for a resigned or won/lost game
     */
    @Test
    void game_ended() {
        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        final CheckersGame game = new CheckersGame(current, opponent);
        current.setGame(game);

        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);
        game.setWinner(current.getColor());

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        // Invoke the test (ignore the output)
        String msg = (String) CuT.handle(request, response);
        // game has winner
        assertEquals(msg, gson.toJson(new Message("true", Message.MsgType.info)));

        // TEST: for resignation
        game.setResign(opponent.getColor());
        // Invoke the test
        String msg2 = (String) CuT.handle(request, response);
        // game has winner
        assertEquals(msg2, gson.toJson(new Message("true", Message.MsgType.info)));
    }

    /**
     *
     */
    @Test
    void is_turn() {
        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        final CheckersGame game = new CheckersGame(current, opponent);
        current.setGame(game);

        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        // TEST: for player's turn
        game.nextTurn();
        // Invoke the test
        String msg = (String) CuT.handle(request, response);
        // not current player's turn
        assertEquals(msg, gson.toJson(new Message("false", Message.MsgType.info)));

        // TEST: for not player's turn
        game.nextTurn();
        // Invoke the test
        String msg2 = (String) CuT.handle(request, response);
        // not current player's turn
        assertEquals(msg2, gson.toJson(new Message("true", Message.MsgType.info)));
    }
}
