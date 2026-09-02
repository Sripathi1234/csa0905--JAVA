package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargingDAO {

    // =====================================================
    // GET VEHICLES
    // =====================================================

    public List<String[]> getVehicles() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT vehicle_id, vehicle_number " +
                        "FROM vehicles " +
                        "ORDER BY vehicle_number";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        new String[]{
                                String.valueOf(
                                        rs.getInt("vehicle_id")
                                ),
                                rs.getString(
                                        "vehicle_number"
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    // =====================================================
    // GET CHARGING POINTS
    // =====================================================

    public List<String[]> getPoints() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT point_id, point_name, tariff_rate " +
                        "FROM charging_points " +
                        "WHERE status IN ('AVAILABLE','RESERVED') " +
                        "ORDER BY point_name";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        new String[]{
                                String.valueOf(
                                        rs.getInt("point_id")
                                ),
                                rs.getString(
                                        "point_name"
                                ),
                                String.valueOf(
                                        rs.getDouble("tariff_rate")
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    // =====================================================
    // CHECK-IN
    // =====================================================

    public boolean checkIn(
            int vehicleId,
            int pointId,
            int reservationId,
            double tariff
    ) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);


            String sql =
                    "INSERT INTO charging_sessions " +
                            "(reservation_id, vehicle_id, point_id, " +
                            "check_in, tariff_rate, session_status) " +
                            "VALUES (?, ?, ?, NOW(), ?, 'CHARGING')";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                if (reservationId > 0) {

                    ps.setInt(
                            1,
                            reservationId
                    );

                } else {

                    ps.setNull(
                            1,
                            Types.INTEGER
                    );
                }


                ps.setInt(
                        2,
                        vehicleId
                );


                ps.setInt(
                        3,
                        pointId
                );


                ps.setDouble(
                        4,
                        tariff
                );


                ps.executeUpdate();
            }


            // Mark charging point occupied

            String updatePoint =
                    "UPDATE charging_points " +
                            "SET status='OCCUPIED' " +
                            "WHERE point_id=?";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updatePoint
                            )
            ) {

                ps.setInt(
                        1,
                        pointId
                );

                ps.executeUpdate();
            }


            con.commit();

            return true;


        } catch (SQLException e) {

            if (con != null) {

                try {

                    con.rollback();

                } catch (SQLException ignored) {
                }
            }

            e.printStackTrace();

            return false;


        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ignored) {
                }
            }
        }
    }


    // =====================================================
    // GET ACTIVE CHARGING SESSIONS
    // =====================================================

    public List<String[]> getActiveSessions() {

        List<String[]> list =
                new ArrayList<>();


        String sql =
                "SELECT s.session_id, " +
                        "v.vehicle_number, " +
                        "cp.point_name, " +
                        "s.check_in, " +
                        "s.energy_consumed, " +
                        "s.tariff_rate " +
                        "FROM charging_sessions s " +
                        "JOIN vehicles v " +
                        "ON s.vehicle_id = v.vehicle_id " +
                        "JOIN charging_points cp " +
                        "ON s.point_id = cp.point_id " +
                        "WHERE s.session_status='CHARGING' " +
                        "ORDER BY s.session_id";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "session_id"
                                        )
                                ),

                                rs.getString(
                                        "vehicle_number"
                                ),

                                rs.getString(
                                        "point_name"
                                ),

                                String.valueOf(
                                        rs.getTimestamp(
                                                "check_in"
                                        )
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "energy_consumed"
                                        )
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "tariff_rate"
                                        )
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }


    // =====================================================
    // CHECK-OUT
    // =====================================================

    public double checkout(
            int sessionId,
            int initialBattery,
            int finalBattery,
            double energy
    ) {

        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();

            con.setAutoCommit(false);


            double tariff;

            int pointId;


            // ---------------------------------------------
            // Get session details
            // ---------------------------------------------

            String selectSql =
                    "SELECT point_id, tariff_rate " +
                            "FROM charging_sessions " +
                            "WHERE session_id=? " +
                            "AND session_status='CHARGING'";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    selectSql
                            )
            ) {

                ps.setInt(
                        1,
                        sessionId
                );


                ResultSet rs =
                        ps.executeQuery();


                if (!rs.next()) {

                    con.rollback();

                    return -1;
                }


                pointId =
                        rs.getInt(
                                "point_id"
                        );


                tariff =
                        rs.getDouble(
                                "tariff_rate"
                        );


                rs.close();
            }


            // ---------------------------------------------
            // Calculate total cost
            // ---------------------------------------------

            double totalCost =
                    energy * tariff;


            // ---------------------------------------------
            // Update charging session
            // ---------------------------------------------

            String updateSession =
                    "UPDATE charging_sessions " +
                            "SET check_out=NOW(), " +
                            "energy_consumed=?, " +
                            "total_cost=?, " +
                            "session_status='COMPLETED' " +
                            "WHERE session_id=?";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    updateSession
                            )
            ) {

                ps.setDouble(
                        1,
                        energy
                );

                ps.setDouble(
                        2,
                        totalCost
                );

                ps.setInt(
                        3,
                        sessionId
                );

                ps.executeUpdate();
            }


            // ---------------------------------------------
            // Insert energy usage
            // ---------------------------------------------

            String energySql =
                    "INSERT INTO energy_usage " +
                            "(session_id, initial_battery, " +
                            "final_battery, energy_consumed, " +
                            "duration_minutes) " +
                            "SELECT ?, ?, ?, ?, " +
                            "TIMESTAMPDIFF(MINUTE, check_in, NOW()) " +
                            "FROM charging_sessions " +
                            "WHERE session_id=?";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    energySql
                            )
            ) {

                ps.setInt(
                        1,
                        sessionId
                );

                ps.setInt(
                        2,
                        initialBattery
                );

                ps.setInt(
                        3,
                        finalBattery
                );

                ps.setDouble(
                        4,
                        energy
                );

                ps.setInt(
                        5,
                        sessionId
                );

                ps.executeUpdate();
            }


            // ---------------------------------------------
            // Make charging point available
            // ---------------------------------------------

            String pointSql =
                    "UPDATE charging_points " +
                            "SET status='AVAILABLE' " +
                            "WHERE point_id=?";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    pointSql
                            )
            ) {

                ps.setInt(
                        1,
                        pointId
                );

                ps.executeUpdate();
            }


            con.commit();

            return totalCost;


        } catch (SQLException e) {

            if (con != null) {

                try {

                    con.rollback();

                } catch (SQLException ignored) {
                }
            }

            e.printStackTrace();

            return -1;


        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ignored) {
                }
            }
        }
    }
}