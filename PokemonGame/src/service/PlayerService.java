package service;

import model.Character;
import model.Player;
import model.Pokemon;
import model.WeatherType;

public class PlayerService {
    public Player createPlayer(String name, Character character, Pokemon pokemon, WeatherType weatherType) {
        return new Player(name, character, pokemon, weatherType);
    }
}
