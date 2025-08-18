package org.example.service;

import org.example.HikariDBSource;
import org.example.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {

    public List<Trainer> getAllTrainers() {
        final String query = "SELECT * FROM trainers";
        List<Trainer> trainers = new ArrayList<>();
        PokemonService pokemonService = new PokemonService();

        try (Connection connection = HikariDBSource.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("id") != 0 ? resultSet.getInt("id") : null);
                trainer.setName(resultSet.getString("name"));
                trainer.setCatching(resultSet.getBoolean("is_catching"));
                trainer.setCaughtPokemons(pokemonService.getAllPokemons().stream().filter(e -> e.getTrainer_id() != null && e.getTrainer_id() == trainer.getId()).toList());

                trainers.add(trainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainers;
    }

    public void add(String name, Boolean isCatching) {
        final String insert = "INSERT INTO trainers(name, is_catching) VALUES(?, ?)";

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
        final String update = "UPDATE trainers SET name = ?, is_catching = ? WHERE id = ?";

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
}
