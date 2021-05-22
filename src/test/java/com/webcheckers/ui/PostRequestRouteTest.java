package com.webcheckers.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.*;


public class PostRequestRouteTest {
    // friendly objects
    private Player player1;
    private Player player2;

    private PostRequestRoute CuT;

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

        this.CuT = new PostRequestRoute();
        engine = mock(TemplateEngine.class);
        PlayerLobby.clearAll();
    }

    @Test
    public void valid_request() {
        this.player1 = new Player("PLAYER1");
        PlayerLobby.signin("PLAYER2");

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, this.player1);

        when(request.queryParams(PostRequestRoute.OPPONENT_ATTR)).thenReturn("PLAYER2");
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(player1);

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

    @Test
    public void check_no_op(){
        this.player1 = new Player("PLAYER1");

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, this.player1);

        when(request.queryParams(PostRequestRoute.OPPONENT_ATTR)).thenReturn("PLAYER2");
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(player1);

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        try {
            CuT.handle(request, response);
            //fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void check_op_ingame(){
        this.player1 = new Player("PLAYER1");
        Player opponent = PlayerLobby.signin("PLAYER2");
        opponent.setInGame();

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, this.player1);

        when(request.queryParams(PostRequestRoute.OPPONENT_ATTR)).thenReturn("PLAYER2");
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(player1);

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine

        // Invoke the test
        try {
            CuT.handle(request, response);
            //fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        // Analyze the results:
        //   * redirect to the Game view
        verify(response).redirect(WebServer.HOME_URL);
    }

}
