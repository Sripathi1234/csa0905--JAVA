package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    // =====================================================
    // GET ALL VEHICLES
    // =====================================================

    public List<String[]> getAllVehicles() {

        List<String[]> vehicles =
                new ArrayList<>();

        String sql =
                "SELECT v.vehicle_id, " +
                        "v.vehicle_number, " +
                        "v.vehicle_model, " +
                        "v.vehicle_type, " +
                        "v.battery_capacity, " +
                        "u.name " +
                        "FROM vehicles v " +
                        "JOIN users u " +
                        "ON v.user_id = u.user_id " +
                        "ORDER BY v.vehicle_id";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                vehicles.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "vehicle_id"
                                        )
                                ),

                                rs.getString(
                                        "vehicle_number"
                                ),

                                rs.getString(
                                        "vehicle_model"
                                ),

                                rs.getString(
                                        "vehicle_type"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "battery_capacity"
                                        )
                                ),

                                rs.getString(
                                        "name"
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return vehicles;
    }


    // =====================================================
    // GET USERS
    // =====================================================

    public List<String[]> getUsers() {

        List<String[]> users =
                new ArrayList<>();


        String sql =
                "SELECT user_id, name " +
                        "FROM users " +
                        "ORDER BY name";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                users.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "user_id"
                                        )
                                ),

                                rs.getString(
                                        "name"
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return users;
    }


    // =====================================================
    // ADD VEHICLE
    // =====================================================

    public boolean addVehicle(
            int userId,
            String vehicleNumber,
            String vehicleModel,
            String vehicleType,
            double batteryCapacity
    ) {

        String sql =
                "INSERT INTO vehicles " +
                        "(user_id, vehicle_number, " +
                        "vehicle_model, vehicle_type, " +
                        "battery_capacity) " +
                        "VALUES (?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    userId
            );

            ps.setString(
                    2,
                    vehicleNumber
            );

            ps.setString(
                    3,
                    vehicleModel
            );

            ps.setString(
                    4,
                    vehicleType
            );

            ps.setDouble(
                    5,
                    batteryCapacity
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE VEHICLE
    // =====================================================

    public boolean updateVehicle(
            int vehicleId,
            int userId,
            String vehicleNumber,
            String vehicleModel,
            String vehicleType,
            double batteryCapacity
    ) {

        String sql =
                "UPDATE vehicles SET " +
                        "user_id=?, " +
                        "vehicle_number=?, " +
                        "vehicle_model=?, " +
                        "vehicle_type=?, " +
                        "battery_capacity=? " +
                        "WHERE vehicle_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    userId
            );

            ps.setString(
                    2,
                    vehicleNumber
            );

            ps.setString(
                    3,
                    vehicleModel
            );

            ps.setString(
                    4,
                    vehicleType
            );

            ps.setDouble(
                    5,
                    batteryCapacity
            );

            ps.setInt(
                    6,
                    vehicleId
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE VEHICLE
    // =====================================================

    public boolean deleteVehicle(
            int vehicleId
    ) {

        String sql =
                "DELETE FROM vehicles " +
                        "WHERE vehicle_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    vehicleId
            );


            return ps.executeUpdate() > 0;


        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // SEARCH VEHICLES
    // =====================================================

    public List<String[]> searchVehicles(
            String keyword
    ) {

        List<String[]> vehicles =
                new ArrayList<>();


        String sql =
                "SELECT v.vehicle_id, " +
                        "v.vehicle_number, " +
                        "v.vehicle_model, " +
                        "v.vehicle_type, " +
                        "v.battery_capacity, " +
                        "u.name " +
                        "FROM vehicles v " +
                        "JOIN users u " +
                        "ON v.user_id = u.user_id " +
                        "WHERE v.vehicle_number LIKE ? " +
                        "OR v.vehicle_model LIKE ? " +
                        "OR u.name LIKE ? " +
                        "ORDER BY v.vehicle_id";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            String search =
                    "%" + keyword + "%";


            ps.setString(
                    1,
                    search
            );

            ps.setString(
                    2,
                    search
            );

            ps.setString(
                    3,
                    search
            );


            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {

                vehicles.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "vehicle_id"
                                        )
                                ),

                                rs.getString(
                                        "vehicle_number"
                                ),

                                rs.getString(
                                        "vehicle_model"
                                ),

                                rs.getString(
                                        "vehicle_type"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "battery_capacity"
                                        )
                                ),

                                rs.getString(
                                        "name"
                                )
                        }
                );
            }


            rs.close();


        } catch (SQLException e) {

            e.printStackTrace();
        }


        return vehicles;
    }
}