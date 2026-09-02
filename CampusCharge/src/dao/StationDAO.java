package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    // =====================================================
    // GET ALL STATIONS
    // =====================================================

    public List<String[]> getAllStations() {

        List<String[]> list = new ArrayList<>();

        String sql =
                "SELECT station_id, station_name, location, total_points " +
                        "FROM charging_stations " +
                        "ORDER BY station_id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        new String[]{
                                String.valueOf(
                                        rs.getInt("station_id")
                                ),

                                rs.getString(
                                        "station_name"
                                ),

                                rs.getString(
                                        "location"
                                ),

                                String.valueOf(
                                        rs.getInt("total_points")
                                )
                        }
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "ERROR: Unable to load stations."
            );

            e.printStackTrace();
        }

        return list;
    }


    // =====================================================
    // ADD STATION
    // =====================================================

    public boolean addStation(
            String name,
            String location,
            int points
    ) {

        String sql =
                "INSERT INTO charging_stations " +
                        "(station_name, location, total_points) " +
                        "VALUES (?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            // ---------------------------------------------
            // VALIDATION
            // ---------------------------------------------

            if (name == null ||
                    name.trim().isEmpty()) {

                System.err.println(
                        "Station name cannot be empty."
                );

                return false;
            }

            if (location == null ||
                    location.trim().isEmpty()) {

                System.err.println(
                        "Station location cannot be empty."
                );

                return false;
            }

            if (points <= 0) {

                System.err.println(
                        "Total points must be greater than 0."
                );

                return false;
            }


            // ---------------------------------------------
            // SET VALUES
            // ---------------------------------------------

            ps.setString(
                    1,
                    name.trim()
            );

            ps.setString(
                    2,
                    location.trim()
            );

            ps.setInt(
                    3,
                    points
            );


            // ---------------------------------------------
            // EXECUTE
            // ---------------------------------------------

            int rows =
                    ps.executeUpdate();


            System.out.println(
                    "Station INSERT executed. Rows affected: "
                            + rows
            );


            return rows > 0;

        } catch (SQLException e) {

            System.err.println(
                    "ERROR WHILE ADDING STATION:"
            );

            System.err.println(
                    "Message: "
                            + e.getMessage()
            );

            System.err.println(
                    "SQL State: "
                            + e.getSQLState()
            );

            System.err.println(
                    "Error Code: "
                            + e.getErrorCode()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE STATION
    // =====================================================

    public boolean updateStation(
            int id,
            String name,
            String location,
            int points
    ) {

        String sql =
                "UPDATE charging_stations SET " +
                        "station_name=?, " +
                        "location=?, " +
                        "total_points=? " +
                        "WHERE station_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            if (id <= 0) {

                System.err.println(
                        "Invalid station ID."
                );

                return false;
            }

            if (name == null ||
                    name.trim().isEmpty()) {

                System.err.println(
                        "Station name cannot be empty."
                );

                return false;
            }

            if (location == null ||
                    location.trim().isEmpty()) {

                System.err.println(
                        "Station location cannot be empty."
                );

                return false;
            }

            if (points <= 0) {

                System.err.println(
                        "Total points must be greater than 0."
                );

                return false;
            }


            ps.setString(
                    1,
                    name.trim()
            );

            ps.setString(
                    2,
                    location.trim()
            );

            ps.setInt(
                    3,
                    points
            );

            ps.setInt(
                    4,
                    id
            );


            int rows =
                    ps.executeUpdate();


            System.out.println(
                    "Station UPDATE executed. Rows affected: "
                            + rows
            );


            return rows > 0;

        } catch (SQLException e) {

            System.err.println(
                    "ERROR WHILE UPDATING STATION:"
            );

            System.err.println(
                    "Message: "
                            + e.getMessage()
            );

            System.err.println(
                    "SQL State: "
                            + e.getSQLState()
            );

            System.err.println(
                    "Error Code: "
                            + e.getErrorCode()
            );

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE STATION
    // =====================================================

    public boolean deleteStation(
            int id
    ) {

        String sql =
                "DELETE FROM charging_stations " +
                        "WHERE station_id=?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            if (id <= 0) {

                System.err.println(
                        "Invalid station ID."
                );

                return false;
            }


            ps.setInt(
                    1,
                    id
            );


            int rows =
                    ps.executeUpdate();


            System.out.println(
                    "Station DELETE executed. Rows affected: "
                            + rows
            );


            return rows > 0;

        } catch (SQLException e) {

            System.err.println(
                    "ERROR WHILE DELETING STATION:"
            );

            System.err.println(
                    "Message: "
                            + e.getMessage()
            );

            System.err.println(
                    "SQL State: "
                            + e.getSQLState()
            );

            System.err.println(
                    "Error Code: "
                            + e.getErrorCode()
            );

            e.printStackTrace();

            return false;
        }
    }
}