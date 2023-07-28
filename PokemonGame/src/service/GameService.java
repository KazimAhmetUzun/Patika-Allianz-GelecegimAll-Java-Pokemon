package service;

import model.Player;
import model.Pokemon;
import model.Weather;
import model.WeatherType;
import model.TypeEnum;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameService {
    private Weather weather;

    public GameService(Weather weather) {
        this.weather = weather;
    }

    public GameService() {
    }

    public void attack(Player attacker, Player defender, boolean isPokeSpecialAttack, boolean isCharSpecialAttack) {
        ArrayList<Pokemon> attackerPokemonList = attacker.getPokemonList();
        ArrayList<Pokemon> defenderPokemonList = defender.getPokemonList();

        // Check if the attacker's Pokemon list is empty
        if (attackerPokemonList.isEmpty()) {
            System.out.println(attacker.getName() + " has no Pokemon to attack with!");
            return;
        }

        // Check if the defender's Pokemon list is empty
        if (defenderPokemonList.isEmpty()) {
            System.out.println(defender.getName() + " has no Pokemon to defend with!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Pokemon attackingPokemon = attackerPokemonList.get(0);
        Pokemon defendingPokemon = defenderPokemonList.get(0);

        while (true) {
            // Rastgele saldırı türü seç (Normal veya Special Attack)
            boolean isNormalAttack = random.nextBoolean();
            boolean isSpecialAttack = !isNormalAttack;

            // Saldırı türünü ekrana yazdır ve oyuncudan seçmesini iste
            System.out.println(attacker.getName() + ", lütfen saldırı türünüzü seçin:");
            System.out.println("1. Normal Attack");
            System.out.println("2. Special Attack");
            int attackChoice = scanner.nextInt();

            if (attackChoice == 1) {
                isNormalAttack = true;
                isSpecialAttack = false;
            } else if (attackChoice == 2) {
                isNormalAttack = false;
                isSpecialAttack = true;
            } else {
                System.out.println("Geçersiz seçim! Lütfen 1 veya 2 tuşlarından birini seçin.");
                continue;
            }

            // Hava durumuna göre zarar verme durumunu ayarla
            int damage = calculateDamage(attackingPokemon, defendingPokemon);

            // Özel saldırıları kontrol et ve zararı buna göre ayarla
            if (isSpecialAttack) {
                if (attackingPokemon.getSpecialPower().getRemainingRights() > 0 && attacker.getCharacter().getSpecialPower().getRemainingRights() > 0) {
                    damage += attackingPokemon.specialAttack() + attacker.getCharacter().getSpecialPower().getExtraDamage();
                    attacker.getCharacter().getSpecialPower().setRemainingRights(attacker.getCharacter().getSpecialPower().getRemainingRights() - 1);
                }
            } else {
                damage += attackingPokemon.getDamage();
            }

            // Savunmacı Pokemon'un sağlığını azalt
            defendingPokemon.setHealth(defendingPokemon.getHealth() - damage);

            // Saldırı sonrası durumu ekrana yazdır
            System.out.println(attacker.getName() + " attacked with " + attackingPokemon.getName());
            System.out.println(defender.getName() + "'s " + defendingPokemon.getName() + " health: " + defendingPokemon.getHealth());

            // Savunmacı Pokemon'u öldü mü kontrol et
            if (defendingPokemon.getHealth() <= 0) {
                System.out.println(defender.getName() + "'s " + defendingPokemon.getName() + " fainted!");
                break;
            }
        }
    }

    private int calculateDamage(Pokemon attackingPokemon, Pokemon defendingPokemon) {
        int baseDamage = attackingPokemon.getDamage();
        if (weather.getCurrentWeather() == WeatherType.SUNNY && attackingPokemon.getType() == TypeEnum.FIRE) {
            baseDamage += 5; // Güneşli hava durumunda Fire tipi Pokemona ekstra zarar verme
        } else if (weather.getCurrentWeather() == WeatherType.RAINY && attackingPokemon.getType() == TypeEnum.WATER) {
            baseDamage += 5; // Yağmurlu hava durumunda Water tipi Pokemona ekstra zarar verme
        } else if (weather.getCurrentWeather() == WeatherType.CLOUDY && attackingPokemon.getType() == TypeEnum.GRASS) {
            baseDamage += 5; // Bulutlu hava durumunda Grass tipi Pokemona ekstra zarar verme
        } else if (weather.getCurrentWeather() == WeatherType.ELECTRIC_STORM && attackingPokemon.getType() == TypeEnum.ELECTRICY) {
            baseDamage += 5; // Elektrik fırtınası hava durumunda Electric tipi Pokemona ekstra zarar verme
        } else if (weather.getCurrentWeather() == WeatherType.EARTHQUAKE && attackingPokemon.getType() == TypeEnum.EARTH) {
            baseDamage += 5; // Deprem hava durumunda Earth tipi Pokemona ekstra zarar verme
        }
        return baseDamage;
    }

    public boolean healthCheck(Player player) {
        ArrayList<Pokemon> pokemonList = player.getPokemonList();
        if (pokemonList.isEmpty()) {
            System.out.println(player.getName() + " has no Pokemon left!");
            return false;
        } else {
            Pokemon pokemon = pokemonList.get(0);
            if (pokemon.getHealth() > 0) {
                System.out.println(player.toString());
                System.out.println("Oyun devam ediyor.");
                return true;
            } else {
                // Oyuncunun tüm Pokemonları ölmüşse, oyunu kaybetti.
                System.out.println(player.toString());
                System.out.println(player.getName() + " oyunu kaybetti.");
                return false;
            }
        }
    }

    public void handleRoundOutcome(Player winner, Player loser) {
        // 2. turda kazanan oyuncunun Pokemon'unu kaybeden oyuncunun Pokemon listesine ekle
        loser.getPokemonList().add(winner.getPokemon());
    }
}
