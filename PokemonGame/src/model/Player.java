package model;

import java.util.ArrayList;

public class Player {
    private String name;
    private Character character;
    private boolean isWinner;
    private WeatherType weatherEffect; // Hava durumu etkisini tutacak değişken

    public Player(String name, Character character, Pokemon pokemon, WeatherType weatherType) {
        this.name = name;
        this.character = character;
        this.character.setActivePokemon(pokemon);
        this.weatherEffect = weatherType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public WeatherType getWeatherEffect() {
        return weatherEffect;
    }

    public void setWeatherEffect(WeatherType weatherEffect) {
        this.weatherEffect = weatherEffect;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", character=" + character +
                ", isWinner=" + isWinner +
                ", weatherEffect=" + weatherEffect +
                '}';
    }

    public Pokemon getPokemon() {
        return character.getActivePokemon();
    }

    public void setPokemon(Pokemon pokemon) {
        character.setActivePokemon(pokemon);
    }

    public ArrayList<Pokemon> getPokemonList() {
        return character.getPokemonList();
    }

    public void setPokemonList(ArrayList<Pokemon> pokemonList) {
        character.setPokemonList(pokemonList);
    }
}
