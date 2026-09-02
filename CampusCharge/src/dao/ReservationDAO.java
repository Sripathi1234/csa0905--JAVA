package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public List<String[]> getUsers() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT user_id, name FROM users ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("name")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String[]> getVehicles(int userId) {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT vehicle_id, vehicle_number " +
                        "FROM vehicles WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(rs.getInt("vehicle_id")),
                        rs.getString("vehicle_number")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String[]> getAvailablePoints() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT cp.point_id, cp.point_name, " +
                        "cp.tariff_rate, cs.station_name " +
                        "FROM charging_points cp " +
                        "JOIN charging_stations cs " +
                        "ON cp.station_id=cs.station_id " +
                        "WHERE cp.status='AVAILABLE' " +
                        "ORDER BY cs.station_name, cp.point_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(rs.getInt("point_id")),
                        rs.getString("point_name"),
                        rs.getString("station_name"),
                        String.valueOf(rs.getDouble("tariff_rate"))
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean hasConflict(
            int pointId,
            String date,
            String start,
            String end) {

        String sql =
                "SELECT COUNT(*) FROM reservations " +
                        "WHERE point_id=? " +
                        "AND reservation_date=? " +
                        "AND status IN ('BOOKED','ACTIVE') " +
                        "AND start_time < ? " +
                        "AND end_time > ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pointId);
            ps.setDate(
                    2,
                    Date.valueOf(date)
            );
            ps.setTime(
                    3,
                    Time.valueOf(end + ":00")
            );
            ps.setTime(
                    4,
                    Time.valueOf(start + ":00")
            );

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean addReservation(
            int userId,
            int vehicleId,
            int pointId,
            String date,
            String start,
            String end) {

        String sql =
                "INSERT INTO reservations " +
                        "(user_id, vehicle_id, point_id, " +
                        "reservation_date, start_time, end_time) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, vehicleId);
            ps.setInt(3, pointId);
            ps.setDate(4, Date.valueOf(date));
            ps.setTime(5, Time.valueOf(start + ":00"));
            ps.setTime(6, Time.valueOf(end + ":00"));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getAllReservations() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT r.reservation_id, u.name, " +
                        "v.vehicle_number, cp.point_name, " +
                        "cs.station_name, r.reservation_date, " +
                        "r.start_time, r.end_time, r.status " +
                        "FROM reservations r " +
                        "JOIN users u ON r.user_id=u.user_id " +
                        "JOIN vehicles v ON r.vehicle_id=v.vehicle_id " +
                        "JOIN charging_points cp ON r.point_id=cp.point_id " +
                        "JOIN charging_stations cs ON cp.station_id=cs.station_id " +
                        "ORDER BY r.reservation_date, r.start_time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(rs.getInt("reservation_id")),
                        rs.getString("name"),
                        rs.getString("vehicle_number"),
                        rs.getString("point_name"),
                        rs.getString("station_name"),
                        rs.getDate("reservation_date").toString(),
                        rs.getTime("start_time").toString(),
                        rs.getTime("end_time").toString(),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean cancelReservation(int id) {

        String sql =
                "UPDATE reservations SET status='CANCELLED' " +
                        "WHERE reservation_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}