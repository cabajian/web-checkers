package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Chris Abajian
 */
@Tag("UI-tier")
public class GetSignoutRouteTest {

    private GetSignoutRoute CuT;
    private Request request;
    private Response response;
    private Session session;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        // create a unique CuT for each test
        CuT = new GetSignoutRoute();
    }

    @Test
    void signoutTest() {

        Player p = PlayerLobby.signin("current");
        session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR, p);
        when(session.attribute(GetHomeRoute.CURRENT_PLAYER_ATTR)).thenReturn(p);

        try {
            CuT.handle(request, response);
        } catch (HaltException ex) {
            verify(response).redirect(WebServer.HOME_URL);
        }
    }

}
