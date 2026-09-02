package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public List<String[]> getCompletedSessions() {

        List<String[]> list =
                new ArrayList<>();

        String sql =
                "SELECT s.session_id, " +
                        "v.vehicle_number, " +
                        "s.total_cost " +
                        "FROM charging_sessions s " +
                        "JOIN vehicles v " +
                        "ON s.vehicle_id=v.vehicle_id " +
                        "WHERE s.session_status='COMPLETED' " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 FROM payments p " +
                        "WHERE p.session_id=s.session_id)";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(
                                rs.getInt("session_id")
                        ),
                        rs.getString("vehicle_number"),
                        String.valueOf(
                                rs.getDouble("total_cost")
                        )
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean makePayment(
            int sessionId,
            double amount,
            String method,
            String transactionId) {

        String sql =
                "INSERT INTO payments " +
                        "(session_id, amount, payment_method, " +
                        "payment_status, transaction_id, payment_date) " +
                        "VALUES (?, ?, ?, 'PAID', ?, NOW())";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ps.setDouble(2, amount);
            ps.setString(3, method);
            ps.setString(4, transactionId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getPayments() {

        List<String[]> list =
                new ArrayList<>();

        String sql =
                "SELECT payment_id, transaction_id, " +
                        "amount, payment_method, payment_status, " +
                        "payment_date " +
                        "FROM payments " +
                        "ORDER BY payment_id DESC";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(
                                rs.getInt("payment_id")
                        ),
                        rs.getString("transaction_id"),
                        String.valueOf(
                                rs.getDouble("amount")
                        ),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        String.valueOf(
                                rs.getTimestamp("payment_date")
                        )
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}