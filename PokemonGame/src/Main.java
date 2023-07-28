import model.*;
import model.Character;
import service.GameService;
import service.LoadService;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Hava durumu oluştur
        Weather weather = new Weather();
        weather.randomizeWeather(); // Başlangıçta rastgele hava durumu belirle

        // LoadService sınıfından bir nesne oluştur
        LoadService loadService = new LoadService();

        // Karakterleri ve Pokemonları yükle
        ArrayList<Character> characters = loadService.loadCharacters();
        ArrayList<Pokemon> pokemons = loadService.loadPokemons();

        try (Scanner scanner = new Scanner(System.in)) {
            // Oyuncu 1'i tanımla ve karakterini seç
            System.out.println("Player 1, please select your character:");
            for (int i = 0; i < characters.size(); i++) {
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
            int player1CharacterIndex = scanner.nextInt() - 1;
            Character player1Character = characters.get(player1CharacterIndex);
            characters.remove(player1CharacterIndex);

            // Oyuncu 2'yi tanımla ve karakterini seç
            System.out.println("Player 2, please select your character:");
            for (int i = 0; i < characters.size(); i++) {
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
            int player2CharacterIndex = scanner.nextInt() - 1;
            Character player2Character = characters.get(player2CharacterIndex);
            characters.remove(player2CharacterIndex);

            // Oyuncu 1 için Pokemon seç
            System.out.println("Player 1, please select your Pokemon:");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i).getName());
            }
            int player1PokemonIndex = scanner.nextInt() - 1;
            Pokemon player1Pokemon = pokemons.get(player1PokemonIndex);
            pokemons.remove(player1PokemonIndex);

            // Oyuncu 2 için Pokemon seç
            System.out.println("Player 2, please select your Pokemon:");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i).getName());
            }
            int player2PokemonIndex = scanner.nextInt() - 1;
            Pokemon player2Pokemon = pokemons.get(player2PokemonIndex);
            pokemons.remove(player2PokemonIndex);

            // Rastgele başlayacak oyuncuyu seç
            Random random = new Random();
            Player player1, player2;
            if (random.nextBoolean()) {
                player1 = new Player("Player 1", player1Character, player1Pokemon, weather.getCurrentWeather());
                player2 = new Player("Player 2", player2Character, player2Pokemon, weather.getCurrentWeather());
            } else {
                player1 = new Player("Player 1", player2Character, player1Pokemon, weather.getCurrentWeather());
                player2 = new Player("Player 2", player1Character, player2Pokemon, weather.getCurrentWeather());
            }

            // Oyuncuların turu
            Player currentPlayer = player1;
            boolean isGameOver = false;
            int round = 1;
            while (!isGameOver) {
                // Rastgele hava durumu belirle
                weather.randomizeWeather();

                // Oyuncunun sırası
                System.out.println("\nRound " + round + ": " + currentPlayer.getName() + ", it's your turn!");
                System.out.println("Weather: " + weather.getCurrentWeatherName());

                // Oyuncu saldırı türünü seç
                System.out.println("Choose your attack type:");
                System.out.println("1. Normal Attack");
                System.out.println("2. Special Attack");
                int attackType = scanner.nextInt();

                // Rakip oyuncuyu belirle
                Player opponentPlayer = (currentPlayer == player1) ? player2 : player1;

                // Saldırıyı gerçekleştir
                boolean isPokeSpecialAttack = (attackType == 2);
                boolean isCharSpecialAttack = (attackType == 1);
                GameService gameService = new GameService(weather);
                gameService.attack(currentPlayer, opponentPlayer, isPokeSpecialAttack, isCharSpecialAttack);

                // Sağlık kontrolü yap
                isGameOver = !gameService.healthCheck(opponentPlayer);

                if (isGameOver) {
                    // Oyun bitti, kazanan oyuncunun Pokemon'unu kaybeden oyuncunun Pokemon listesine ekle
                    opponentPlayer.getCharacter().getPokemonList().add(currentPlayer.getPokemon());

                    // 2. tur başlasın
                    currentPlayer = opponentPlayer;
                    System.out.println("\nRound " + (round + 1) + " started!");
                    round++;
                    isGameOver = false;
                } else {
                    // Oyun devam ediyor, oyuncu değiştir
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }

            // Oyun bitti, kazananı belirle
            Player winner = (player1.getPokemonList().isEmpty()) ? player2 : player1;
            System.out.println("\nGame Over!");
            System.out.println("The winner is " + winner.getName() + " with " + winner.getPokemon().getName() + "!");
        }
    }
}