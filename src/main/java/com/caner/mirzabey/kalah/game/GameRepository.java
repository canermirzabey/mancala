package com.caner.mirzabey.kalah.game;

import com.caner.mirzabey.kalah.game.data.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ecanmir on 12.06.2016.
 */
@Service
public class GameRepository {
    public static final Logger logger = LoggerFactory.getLogger(GameRepository.class);

    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public Game find(String name) {
        Game game = null;
        if (!StringUtils.isEmpty(name)) {
            game = games.get(name);
        }
        return game;
    }

    public boolean insert(Game game) {
        if (!StringUtils.isEmpty(game.getName()) && !games.containsKey(game.getName())) {
            games.put(game.getName(), game);
            return true;
        }
        return false;
    }

    public void update(Game game) {
        remove(game.getName());
        insert(game);
    }

    public Collection<Game> findAll() {
        return games.values();
    }

    public Game remove(Game game) {
        return games.remove(game.getName());
    }

    public Game remove(String name) {
        return games.remove(name);
    }

}
