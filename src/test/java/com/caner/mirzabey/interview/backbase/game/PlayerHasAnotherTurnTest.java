package com.caner.mirzabey.interview.backbase.game;

import com.caner.mirzabey.interview.backbase.mancala.Mancala;
import com.caner.mirzabey.interview.backbase.mancala.game.GameEngineService;
import com.caner.mirzabey.interview.backbase.mancala.game.GameRepository;
import com.caner.mirzabey.interview.backbase.mancala.game.data.Game;
import com.caner.mirzabey.interview.backbase.mancala.game.exception.GameException;
import com.caner.mirzabey.interview.backbase.mancala.user.User;
import com.caner.mirzabey.interview.backbase.mancala.ws.GameAction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by ecanmir on 15.06.2016.
 */
@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = Mancala.class)
public class PlayerHasAnotherTurnTest {
    private static final int    SEED_COUNT        = 6;
    private static final String GAME_NAME         = "test";
    private static final String PLAYER_USERNAME   = "player";
    private static final String OPPONENT_USERNAME = "opponent";

    @Autowired
    private GameEngineService gameEngineService;

    @Autowired
    private GameRepository gameRepository;

    @Before
    public void init() {
        System.out.println("init");
        User player   = new User(UUID.randomUUID().toString(), PLAYER_USERNAME);
        User opponent = new User(UUID.randomUUID().toString(), OPPONENT_USERNAME);
        Game game     = new Game(GAME_NAME, SEED_COUNT, player, opponent);
        game.getBoard().getPlayerSideByUsername(PLAYER_USERNAME).setTurnOnMe(true);
        game.getBoard().getOpponentSideByUsername(PLAYER_USERNAME).setTurnOnMe(false);
        gameRepository.insert(game);
    }

    @Test
    public void test() throws GameException {
        System.out.println("test");
        GameAction gameAction = new GameAction(GAME_NAME,
                                               null,
                                               PLAYER_USERNAME,
                                               1,
                                               "First game action for having another turn");
        Game result = gameEngineService.action(gameAction);
        assertEquals(result.getBoard().getPlayerSideByUsername(PLAYER_USERNAME).isTurnOnMe(), true);
    }

}
