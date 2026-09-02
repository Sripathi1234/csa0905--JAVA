package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    // ==========================================
    // STATEMENT
    // ==========================================

    public List<String[]> stationReport() {

        List<String[]> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM station_utilization";

        try (Connection con =
                     DBConnection.getConnection();
             Statement st =
                     con.createStatement();
             ResultSet rs =
                     st.executeQuery(sql)) {

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(
                                rs.getInt("station_id")
                        ),
                        rs.getString("station_name"),
                        rs.getString("location"),
                        String.valueOf(
                                rs.getInt("total_points")
                        ),
                        String.valueOf(
                                rs.getInt("total_sessions")
                        ),
                        String.valueOf(
                                rs.getDouble("total_energy")
                        ),
                        String.valueOf(
                                rs.getDouble("total_revenue")
                        )
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================
    // PREPARED STATEMENT
    // ==========================================

    public List<String[]> vehicleHistory(
            String vehicleNumber) {

        List<String[]> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM vehicle_charging_history " +
                        "WHERE vehicle_number LIKE ?";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    "%" + vehicleNumber + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                list.add(new String[]{
                        String.valueOf(
                                rs.getInt("session_id")
                        ),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_model"),
                        rs.getString("station_name"),
                        rs.getString("point_name"),
                        String.valueOf(
                                rs.getDouble("energy_consumed")
                        ),
                        String.valueOf(
                                rs.getDouble("total_cost")
                        ),
                        rs.getString("session_status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================
    // CALLABLE STATEMENT
    // ==========================================

    public String stationUsageProcedure(
            int stationId) {

        StringBuilder result =
                new StringBuilder();

        String sql =
                "{CALL GetStationUsage(?)}";

        try (Connection con =
                     DBConnection.getConnection();
             CallableStatement cs =
                     con.prepareCall(sql)) {

            cs.setInt(1, stationId);

            ResultSet rs =
                    cs.executeQuery();

            if (rs.next()) {

                result.append(
                        "Station: "
                ).append(
                        rs.getString("station_name")
                );

                result.append(
                        "\nLocation: "
                ).append(
                        rs.getString("location")
                );

                result.append(
                        "\nCharging Points: "
                ).append(
                        rs.getInt("total_charging_points")
                );

                result.append(
                        "\nTotal Sessions: "
                ).append(
                        rs.getInt("total_sessions")
                );

                result.append(
                        "\nEnergy Consumed: "
                ).append(
                        rs.getDouble("total_energy_consumed")
                ).append(" kWh");

                result.append(
                        "\nRevenue: ₹"
                ).append(
                        rs.getDouble("total_revenue")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return "Unable to generate station usage.";
        }

        return result.toString();
    }
}