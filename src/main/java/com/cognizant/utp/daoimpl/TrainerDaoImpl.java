package com.cognizant.utp.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.utp.dao.TrainerDao;
import com.cognizant.utp.models.Trainer;
import com.cognizant.utp.util.DBConnection;

public class TrainerDaoImpl implements TrainerDao {

    // ---------------- CREATE ----------------

    @Override
    public long createTrainer(Trainer trainer) {
        String sql = """
            INSERT INTO trainers (trainer_name, email, expertise, trainer_type)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, trainer.getTrainerName());
            ps.setString(2, trainer.getEmail());
            ps.setString(3, trainer.getExpertise());
            ps.setString(4, trainer.getTrainerType());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating trainer", e);
        }
        return -1;
    }

    // ---------------- READ ----------------

    @Override
    public Trainer getTrainerById(long trainerId) {
        String sql = """
            SELECT * FROM trainers
            WHERE trainer_id = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, trainerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTrainer(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching trainer by id", e);
        }
        return null;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        String sql = "SELECT * FROM trainers WHERE is_deleted = FALSE";
        List<Trainer> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapTrainer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching trainers", e);
        }
        return list;
    }

    // ---------------- UPDATE ----------------

    @Override
    public boolean updateTrainer(Trainer trainer) {
        String sql = """
            UPDATE trainers
            SET trainer_name = ?, email = ?, expertise = ?, trainer_type = ?
            WHERE trainer_id = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trainer.getTrainerName());
            ps.setString(2, trainer.getEmail());
            ps.setString(3, trainer.getExpertise());
            ps.setString(4, trainer.getTrainerType());
            ps.setLong(5, trainer.getTrainerId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating trainer", e);
        }
    }

    // ---------------- SOFT DELETE ----------------

    @Override
    public boolean deleteTrainer(long trainerId) {
        String sql = "UPDATE trainers SET is_deleted = TRUE WHERE trainer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, trainerId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting trainer", e);
        }
    }

    // ---------------- MAPPING ----------------

    private Trainer mapTrainer(ResultSet rs) throws SQLException {
        Trainer t = new Trainer();

        t.setTrainerId(rs.getLong("trainer_id"));
        t.setTrainerName(rs.getString("trainer_name"));
        t.setEmail(rs.getString("email"));
        t.setExpertise(rs.getString("expertise"));
        t.setTrainerType(rs.getString("trainer_type"));

        t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        t.setDeleted(rs.getBoolean("is_deleted"));

        return t;
    }
}