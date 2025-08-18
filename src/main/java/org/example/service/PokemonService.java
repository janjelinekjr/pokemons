package org.example.service;

import org.example.HikariDBSource;
import org.example.Pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PokemonService {

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

    public void add(Pokemon pokemon) {
        final String insert = "INSERT INTO pokemons(name, level, trainer_id) VALUES(?, ?, ?)";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, pokemon.getName());
            preparedStatement.setInt(2, pokemon.getLevel());
            preparedStatement.setInt(3, pokemon.getTrainer_id());
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

    public void update(Pokemon pokemon, int trainerId) {
        final String update = "UPDATE pokemons SET name = ?, level = ?, trainer_id = ? WHERE id = ?";

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, pokemon.getName());
            preparedStatement.setInt(2, pokemon.getLevel());
            preparedStatement.setInt(3, trainerId);
            preparedStatement.setInt(4, pokemon.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
