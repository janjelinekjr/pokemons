package org.example;

import org.example.service.PokemonService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Trainer {
    private int id;
    private String name;
    private Boolean isCatching;
    private List<Pokemon> caughtPokemons;

    public Trainer() {
    }

    public void printTrainer(Trainer t) {
        System.out.println("Trainer's name: " + t.getName() + ". " + "Is catching: " + t.getCatching() + ". " + "With ID: " + t.getId() + ". " + "Caught pokemons count: " + t.getCaughtPokemons().size());
    }

    public void catchPokemon(Trainer t) {
        PokemonService pokemonService = new PokemonService();

        if (t.isCatching) {
            Optional<Pokemon> availPokemon = pokemonService.getAllPokemons().stream()
                    .filter(p -> p.getTrainer_id() == null)
                    .findAny();
            if (availPokemon.isPresent()) {
                pokemonService.update(availPokemon.get(), t.getId());
                System.out.println("Pokemon " + availPokemon.get().getName() + " has been caught by trainer " + t.getName());
            } else {
                System.out.println("No available pokemon to catch!");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCatching() {
        return isCatching;
    }

    public void setCatching(Boolean catching) {
        isCatching = catching;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pokemon> getCaughtPokemons() {
        return caughtPokemons;
    }

    public void setCaughtPokemons(List<Pokemon> caughtPokemons) {
        this.caughtPokemons = caughtPokemons;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return id == trainer.id && Objects.equals(name, trainer.name) && Objects.equals(isCatching, trainer.isCatching) && Objects.equals(caughtPokemons, trainer.caughtPokemons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isCatching, caughtPokemons);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCatching=" + isCatching +
                ", caughtPokemons=" + caughtPokemons +
                '}';
    }

}
