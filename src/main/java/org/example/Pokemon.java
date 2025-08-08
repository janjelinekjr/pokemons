package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pokemon {
    private int id;
    private String name;
    private int level;
    private Integer trainer_id;

    public Pokemon() {
    }

    public List<Pokemon> getAllPokemons() {
        final String query = "SELECT * FROM pokemons";
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(resultSet.getInt("id"));
                pokemon.setName(resultSet.getString("name"));
                pokemon.setLevel(resultSet.getInt("level"));
                int trainerId = resultSet.getInt("trainer_id");
                if (resultSet.wasNull()) {
                    pokemon.setTrainer_id(null);
                } else {
                    pokemon.setTrainer_id(trainerId);
                }

                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pokemons;
    }

    public void add() {
        final String insert = "INSERT INTO pokemons(name, level, trainer_id) VALUES(?, ?, ?)";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, level);
            preparedStatement.setInt(3, trainer_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        final String delete = "DELETE FROM pokemons WHERE id = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, int level, int trainer_id) {
        final String update = "UPDATE pokemons SET name = ?, level = ?, trainer_id = ? WHERE id = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, level);
            preparedStatement.setInt(3, trainer_id);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printPokemon(Pokemon p) {
        Trainer trainer = new Trainer();

        System.out.println("Pokemon's name: " + p.getName() + ". " + "Level: " + p.getLevel() + ". " + "Trainer: " + trainer.getAllTrainers().stream()
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
