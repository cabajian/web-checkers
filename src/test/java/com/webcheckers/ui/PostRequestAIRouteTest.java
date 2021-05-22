package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.AI;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import spark.*;

public class PostRequestAIRouteTest {
    //Friendly objects
    private Player player;

    private PostRequestAIRoute CuT;

    private Request request;
    private Session session;
    private Response response;

    private TemplateEngine engine;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        this.CuT = new PostRequestAIRoute();
        engine = mock(TemplateEngine.class);
        PlayerLobby.clearAll();

    }

    @Test
    public void invalid_request() {
        // Invoke the test
        Message msg;
        try {
            msg = (Message) CuT.handle(request, response);
            //fail("Redirects invoke halt exceptions.");
            assertEquals(Message.MsgType.error, msg.getType());
        } catch (HaltException e) {
            // expected
        }
    }

    @Test
    public void valid_request() {
        this.player = new Player("PLAYER1");

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, this.player);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(player);

        // Invoke the test
        try {
            CuT.handle(request, response);
            //fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.GAME_URL);
    }


}
