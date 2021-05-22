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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostValidateMoveRouteTest {

    private PostValidateMoveRoute CuT;
    private Gson gson;
    private Request request;
    private Response response;
    private Session session;

    private Player current;
    private Player opponent;
    private CheckersGame game;

    @Test
    void validateTest() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gson = new Gson();
        // create a unique CuT for each test
        CuT = new PostValidateMoveRoute(gson);

        current = new Player("current");
        opponent = new Player("opponent");
        game = new CheckersGame(current, opponent);

        current.setColor(Player.PlayerColor.RED);
        current.setGame(game);
        opponent.setColor(Player.PlayerColor.WHITE);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        Position start = new Position(new Row(0), new Space(1, Space.SpaceColor.DARK));
        Position end = new Position(new Row(1), new Space(2, Space.SpaceColor.DARK));

        when(request.body()).thenReturn(gson.toJson(new Move(start, end)));
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        CuT.handle(request, response);
    }
}
