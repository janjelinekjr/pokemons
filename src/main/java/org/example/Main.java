package org.example;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        final Trainer trainer = new Trainer();
        final Pokemon pokemon = new Pokemon();

        boolean running = true;

        while (running) {
            displayCommandsMenu();
            int input = getIntInput(true);

            switch (input) {
                case 1:
                    trainer.getAllTrainers().forEach(t -> t.printTrainer(t));
                    continue;
                case 2:
                    pokemon.getAllPokemons().forEach(p -> p.printPokemon(p));
                    continue;
                case 3:
                    trainer.getAllTrainers().stream()
                            .sorted(Comparator.comparing((Trainer t) -> t.getCaughtPokemons().size()).reversed())
                            .forEach(t -> t.printTrainer(t));
                    continue;
                case 4:
                    pokemon.getAllPokemons().stream()
                            .filter(p -> p.getTrainer_id() == null)
                            .forEach(p -> p.printPokemon(p));
                    continue;
                case 5:
                    System.out.print("Insert trainer's ID: ");
                    int trainerId = getIntInput(false);
                    Optional<Trainer> wantedTrainer = trainer.getAllTrainers().stream()
                            .filter(t -> t.getId() == trainerId)
                            .findFirst();

                    if (wantedTrainer.isPresent()) {
                        trainer.catchPokemon(wantedTrainer.get());
                    } else {
                        System.out.println("No trainer with selected ID");
                    }
                    continue;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static int getIntInput(boolean choosable) {
        if (choosable) System.out.println("Choose command:");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid option");
            if (choosable) {
                System.out.println("Choose command:");
            } else {
                System.out.println("Try again:");
            }
            scanner.next();
        }

        final int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    public static void displayCommandsMenu() {
        System.out.println("-------GENERAL COMMANDS--------");
        System.out.println("1. PRINT ALL TRAINERS");
        System.out.println("2. PRINT ALL POKEMONS");
        System.out.println("-------SPECIAL COMMANDS--------");
        System.out.println("3. PRINT TRAINERS SORTED BY CAUGHT POKEMONS");
        System.out.println("4. PRINT POKEMONS WITHOUT TRAINER");
        System.out.println("5. CATCH POKEMON");
        System.out.println("-------OTHER--------");
        System.out.println("6. QUIT");
    }
}