package org.example;

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

    public List<Trainer> getAllTrainers() {
        final String query = "SELECT * FROM trainers";
        List<Trainer> trainers = new ArrayList<>();
        Pokemon pokemon = new Pokemon();

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("id") != 0 ? resultSet.getInt("id") : null);
                trainer.setName(resultSet.getString("name"));
                trainer.setCatching(resultSet.getBoolean("isCatching"));
                trainer.setCaughtPokemons(pokemon.getAllPokemons().stream().filter(e -> e.getTrainer_id() != null && e.getTrainer_id() == trainer.getId()).toList());

                trainers.add(trainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainers;
    }

    public void add(String name, Boolean isCatching) {
        final String insert = "INSERT INTO trainers(name, isCatching) VALUES(?, ?)";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, isCatching);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        final String delete = "DELETE FROM trainers WHERE id = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String name, Boolean isCatching) {
        final String update = "UPDATE trainers SET name = ?, isCatching = ? WHERE id = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, isCatching);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printTrainer(Trainer t) {
        System.out.println("Trainer's name: " + t.getName() + ". " + "Is catching: " + t.getCatching() + ". " + "With ID: " + t.getId() + ". " + "Caught pokemons count: " + t.getCaughtPokemons().size());
    }

    public void catchPokemon(Trainer t) {
        Pokemon pokemons = new Pokemon();
        if (t.isCatching) {
            Optional<Pokemon> availPokemon = pokemons.getAllPokemons().stream()
                    .filter(p -> p.getTrainer_id() == null)
                    .findAny();
            if (availPokemon.isPresent()) {
                pokemons.update(availPokemon.get().getId(), availPokemon.get().getLevel(), t.getId());
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
