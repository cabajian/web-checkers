package com.webcheckers.ui;

import com.webcheckers.appl.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Chris Abajian
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    private GetGameRoute CuT;
    private TemplateEngine engine;
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
        engine = mock(TemplateEngine.class);
        // create a unique CuT for each test
        CuT = new GetGameRoute(engine);
    }

    /**
     * Tests a valid, new game
     */
    @Test
    public void new_game() {
        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);
        CheckersGame game = new CheckersGame(current, opponent);
        current.setGame(game);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);

        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test (ignore the output)
        CuT.handle(request, response);

        // Analyze the content passed into the render method
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetGameRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, current);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.MODE_OPT_ATTR, null);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.viewMode.PLAY);
        //   * test view name
        testHelper.assertViewName(GetGameRoute.VIEW_NAME);
    }

    /**
     * Tests a game that has ended with a resign
     */
    @Test
    void end_game_resign() {
        end_game_helper(0);
        // check that session redirects
        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            assert true;
        }
    }

    /**
     * Tests a game that has ended with the current player winning
     */
    @Test
    void end_game_curr() {
        end_game_helper(1);
        // check that session redirects
        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    /**
     * Tests a game that has ended with the opponent player winning
     */
    @Test
    void end_game_opp() {
        end_game_helper(2);
        // check that session redirects
        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    /**
     * Tests null player
     */
    @Test
    void null_player() {
        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, null);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(null);

        // check that session redirects
        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

    private void end_game_helper(int condition) {
        final Player current = new Player("current");
        final Player opponent = new Player("opponent");
        current.setColor(Player.PlayerColor.RED);
        opponent.setColor(Player.PlayerColor.WHITE);
        CheckersGame game = new CheckersGame(current, opponent);

        switch(condition) {
            case 0:
                game.setResign(current.getColor());
            case 1:
                game.setWinner(current.getColor());
            case 2:
                game.setWinner(opponent.getColor());
        }

        current.setGame(game);

        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, current);

        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(current);
    }

}