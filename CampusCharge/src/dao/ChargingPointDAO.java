package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargingPointDAO {

    // =====================================================
    // GET ALL CHARGING POINTS
    // =====================================================

    public List<String[]> getAllChargingPoints() {

        List<String[]> points =
                new ArrayList<>();

        String sql =
                "SELECT cp.point_id, " +
                        "cs.station_name, " +
                        "cp.point_name, " +
                        "cp.charger_type, " +
                        "cp.power_kw, " +
                        "cp.status, " +
                        "cp.tariff_rate " +
                        "FROM charging_points cp " +
                        "JOIN charging_stations cs " +
                        "ON cp.station_id = cs.station_id " +
                        "ORDER BY cp.point_id";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                points.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt("point_id")
                                ),

                                rs.getString(
                                        "station_name"
                                ),

                                rs.getString(
                                        "point_name"
                                ),

                                rs.getString(
                                        "charger_type"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "power_kw"
                                        )
                                ),

                                rs.getString(
                                        "status"
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

        return points;
    }


    // =====================================================
    // GET ALL STATIONS
    // =====================================================

    public List<String[]> getAllStations() {

        List<String[]> stations =
                new ArrayList<>();

        String sql =
                "SELECT station_id, station_name " +
                        "FROM charging_stations " +
                        "ORDER BY station_name";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                stations.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "station_id"
                                        )
                                ),

                                rs.getString(
                                        "station_name"
                                )
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return stations;
    }


    // =====================================================
    // ADD CHARGING POINT
    // =====================================================

    public boolean addChargingPoint(
            int stationId,
            String pointName,
            String chargerType,
            double powerKw,
            String status,
            double tariffRate
    ) {

        String sql =
                "INSERT INTO charging_points " +
                        "(station_id, point_name, charger_type, " +
                        "power_kw, status, tariff_rate) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    stationId
            );

            ps.setString(
                    2,
                    pointName
            );

            ps.setString(
                    3,
                    chargerType
            );

            ps.setDouble(
                    4,
                    powerKw
            );

            ps.setString(
                    5,
                    status
            );

            ps.setDouble(
                    6,
                    tariffRate
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE CHARGING POINT
    // =====================================================

    public boolean updateChargingPoint(
            int pointId,
            int stationId,
            String pointName,
            String chargerType,
            double powerKw,
            String status,
            double tariffRate
    ) {

        String sql =
                "UPDATE charging_points SET " +
                        "station_id=?, " +
                        "point_name=?, " +
                        "charger_type=?, " +
                        "power_kw=?, " +
                        "status=?, " +
                        "tariff_rate=? " +
                        "WHERE point_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    stationId
            );

            ps.setString(
                    2,
                    pointName
            );

            ps.setString(
                    3,
                    chargerType
            );

            ps.setDouble(
                    4,
                    powerKw
            );

            ps.setString(
                    5,
                    status
            );

            ps.setDouble(
                    6,
                    tariffRate
            );

            ps.setInt(
                    7,
                    pointId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE CHARGING POINT
    // =====================================================

    public boolean deleteChargingPoint(
            int pointId
    ) {

        String sql =
                "DELETE FROM charging_points " +
                        "WHERE point_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    pointId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE POINT STATUS
    // =====================================================

    public boolean updateStatus(
            int pointId,
            String status
    ) {

        String sql =
                "UPDATE charging_points " +
                        "SET status=? " +
                        "WHERE point_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    pointId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // SEARCH CHARGING POINTS
    // =====================================================

    public List<String[]> searchChargingPoints(
            String keyword
    ) {

        List<String[]> points =
                new ArrayList<>();

        String sql =
                "SELECT cp.point_id, " +
                        "cs.station_name, " +
                        "cp.point_name, " +
                        "cp.charger_type, " +
                        "cp.power_kw, " +
                        "cp.status, " +
                        "cp.tariff_rate " +
                        "FROM charging_points cp " +
                        "JOIN charging_stations cs " +
                        "ON cp.station_id = cs.station_id " +
                        "WHERE cs.station_name LIKE ? " +
                        "OR cp.point_name LIKE ? " +
                        "OR cp.charger_type LIKE ? " +
                        "OR cp.status LIKE ? " +
                        "ORDER BY cp.point_id";

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

            ps.setString(
                    4,
                    search
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                points.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "point_id"
                                        )
                                ),

                                rs.getString(
                                        "station_name"
                                ),

                                rs.getString(
                                        "point_name"
                                ),

                                rs.getString(
                                        "charger_type"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "power_kw"
                                        )
                                ),

                                rs.getString(
                                        "status"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "tariff_rate"
                                        )
                                )
                        }
                );
            }

            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return points;
    }


    // =====================================================
    // GET AVAILABLE POINTS
    // =====================================================

    public List<String[]> getAvailablePoints() {

        List<String[]> points =
                new ArrayList<>();

        String sql =
                "SELECT cp.point_id, " +
                        "cs.station_name, " +
                        "cp.point_name, " +
                        "cp.charger_type, " +
                        "cp.power_kw, " +
                        "cp.tariff_rate " +
                        "FROM charging_points cp " +
                        "JOIN charging_stations cs " +
                        "ON cp.station_id = cs.station_id " +
                        "WHERE cp.status='AVAILABLE' " +
                        "ORDER BY cp.point_id";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                points.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt(
                                                "point_id"
                                        )
                                ),

                                rs.getString(
                                        "station_name"
                                ),

                                rs.getString(
                                        "point_name"
                                ),

                                rs.getString(
                                        "charger_type"
                                ),

                                String.valueOf(
                                        rs.getDouble(
                                                "power_kw"
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

        return points;
    }
}