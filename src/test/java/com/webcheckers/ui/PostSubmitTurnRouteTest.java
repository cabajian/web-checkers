package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSubmitTurnRouteTest {

    @Test
    void submitTurn() {
        Request request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        Response response = mock(Response.class);
        Gson gson = new Gson();
        // create a unique CuT for each test
        PostSubmitTurnRoute CuT = new PostSubmitTurnRoute(gson);

        Player current = new Player("current");
        Player opponent = new Player("opponent");
        CheckersGame game = new CheckersGame(current, opponent);

        current.setColor(Player.PlayerColor.RED);
        current.setGame(game);
        opponent.setColor(Player.PlayerColor.WHITE);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        CuT.handle(request, response);
    }

    @Test
    void AISubmitTest() {
        Request request = mock(Request.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);
        Response response = mock(Response.class);
        Gson gson = new Gson();
        // create a unique CuT for each test
        PostSubmitTurnRoute CuT = new PostSubmitTurnRoute(gson);

        Player current = new Player("current");
        AI opponent = new AI();
        CheckersGame game = new CheckersGame(current, opponent);

        current.setColor(Player.PlayerColor.RED);
        current.setGame(game);
        opponent.setGame(game);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        Position start = new Position(new Row(5), new Space(0, Space.SpaceColor.DARK));
        Position end = new Position(new Row(4), new Space(1, Space.SpaceColor.DARK));

        game.validateMove(new Move(start, end));

        CuT.handle(request, response);
    }

}
