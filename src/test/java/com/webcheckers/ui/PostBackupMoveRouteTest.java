package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostBackupMoveRouteTest {

    private PostBackupMoveRoute CuT;
    private Gson gson;
    private Request request;
    private Response response;
    private Session session;

    private Player current;
    private Player opponent;
    private CheckersGame game;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        // create a unique CuT for each test
        CuT = new PostBackupMoveRoute(gson);

        current = new Player("current");
        opponent = new Player("opponent");
        game = new CheckersGame(current, opponent);
        current.setGame(game);

        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);
    }

    /**
     *
     */
    @Test
    void backup() {
        String msg = (String) CuT.handle(request, response);
        assertNotNull(msg);
    }

}
