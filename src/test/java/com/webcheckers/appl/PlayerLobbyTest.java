package com.webcheckers.appl;

import com.webcheckers.model.AI;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  The unit test suite for the {@link PlayerLobby} component
 *
 * @author Fayez Mehdad + Chris Abajian
 */
class PlayerLobbyTest {

    /**
     * Clears the player lobby before each test
     */
    @BeforeEach
    void setup() {
        PlayerLobby.clearAll();
    }

    /**
     * Tests the sign in method for the PlayerLobby
     */
    @Test
    void signin() {
       assertNull(PlayerLobby.signin("__Chris@"));
       assertNotNull(PlayerLobby.signin("Chris"));
       assertNull(PlayerLobby.signin("Chris"));
       assertNull(PlayerLobby.signin(" Chris"));
       assertNotNull(PlayerLobby.signin("chris"));
    }

    /**
     * Tests the display names method
     */
    @Test
    void displayNames() {
        PlayerLobby.signin("Becky");
        PlayerLobby.signin("Kevin");
        assertNotNull(PlayerLobby.displayNames());
        assert PlayerLobby.displayNames().size() == 2;
    }

    /**
     * Tests getPlayer method
     */
    @Test
    void getPlayer() {
        PlayerLobby.signin("Becky");
        assertNull(PlayerLobby.getPlayer("Kevin"));
        assertNotNull(PlayerLobby.getPlayer("Becky"));
    }

    /**
     * Tests the signout method
     */
    @Test
    void signout() {
        PlayerLobby.signin("Becky");
        Player becky = PlayerLobby.getPlayer("Becky");
        assertNotNull(becky);
        PlayerLobby.signout(becky);
        assert PlayerLobby.displayNames().size() == 0;
        assert !becky.isInGame();

        PlayerLobby.signin("Becky1");
        Player becky1 = PlayerLobby.getPlayer("Becky1");
        becky1.setInGame();
        becky1.setColor(Player.PlayerColor.RED);
        becky1.setGame(new CheckersGame(becky1, new Player("James")));
        PlayerLobby.signout(becky1);
    }
}