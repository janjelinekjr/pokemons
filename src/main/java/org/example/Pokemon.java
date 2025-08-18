package org.example;

import org.example.service.TrainerService;

import java.util.Objects;

public class Pokemon {
    private int id;
    private String name;
    private int level;
    private Integer trainer_id;

    public Pokemon() {
    }

    public void printPokemon(Pokemon p) {
        TrainerService trainerService = new TrainerService();

        System.out.println("Pokemon's name: " + p.getName() + ". " + "Level: " + p.getLevel() + ". " + "Trainer: " + trainerService.getAllTrainers().stream()
                .filter(t -> p.getTrainer_id() != null && t.getId() == p.getTrainer_id()).map(Trainer::getName).findFirst().orElse("None"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(Integer trainer_id) {
        this.trainer_id = trainer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", trainer_id=" + trainer_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && level == pokemon.level && trainer_id == pokemon.trainer_id && Objects.equals(name, pokemon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, trainer_id);
    }
}
