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
class PostRequestHelpRouteTest {

    private PostRequestHelpRoute CuT;
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
        CuT = new PostRequestHelpRoute(gson);
    }

    @Test
    void helpTest() {
        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        final CheckersGame game = new CheckersGame(current, opponent);
        current.setGame(game);

        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        // Invoke the test (ignore the output)
        CuT.handle(request, response);
    }
}
