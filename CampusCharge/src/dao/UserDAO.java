package dao;

import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<String[]> getAllUsers() {

        List<String[]> users =
                new ArrayList<>();

        String sql =
                "SELECT user_id, name, email, phone, user_type " +
                        "FROM users " +
                        "ORDER BY user_id";

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
                                        rs.getInt("user_id")
                                ),

                                rs.getString("name"),

                                rs.getString("email"),

                                rs.getString("phone"),

                                rs.getString("user_type")
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return users;
    }


    // =====================================================
    // ADD USER
    // =====================================================

    public boolean addUser(
            String name,
            String email,
            String phone,
            String userType,
            String password
    ) {

        String sql =
                "INSERT INTO users " +
                        "(name, email, phone, user_type, password) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    name
            );

            ps.setString(
                    2,
                    email
            );

            ps.setString(
                    3,
                    phone
            );

            ps.setString(
                    4,
                    userType
            );

            ps.setString(
                    5,
                    password
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE USER
    // =====================================================

    public boolean updateUser(
            int userId,
            String name,
            String email,
            String phone,
            String userType,
            String password
    ) {

        String sql =
                "UPDATE users SET " +
                        "name=?, " +
                        "email=?, " +
                        "phone=?, " +
                        "user_type=?, " +
                        "password=? " +
                        "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    name
            );

            ps.setString(
                    2,
                    email
            );

            ps.setString(
                    3,
                    phone
            );

            ps.setString(
                    4,
                    userType
            );

            ps.setString(
                    5,
                    password
            );

            ps.setInt(
                    6,
                    userId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE USER
    // =====================================================

    public boolean deleteUser(
            int userId
    ) {

        String sql =
                "DELETE FROM users " +
                        "WHERE user_id=?";

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

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // SEARCH USERS
    // =====================================================

    public List<String[]> searchUsers(
            String keyword
    ) {

        List<String[]> users =
                new ArrayList<>();

        String sql =
                "SELECT user_id, name, email, phone, user_type " +
                        "FROM users " +
                        "WHERE name LIKE ? " +
                        "OR email LIKE ? " +
                        "OR phone LIKE ? " +
                        "OR user_type LIKE ? " +
                        "ORDER BY user_id";

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

                users.add(
                        new String[]{

                                String.valueOf(
                                        rs.getInt("user_id")
                                ),

                                rs.getString("name"),

                                rs.getString("email"),

                                rs.getString("phone"),

                                rs.getString("user_type")
                        }
                );
            }

            rs.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return users;
    }


    // =====================================================
    // LOGIN
    // =====================================================

    public String login(
            String email,
            String password
    ) {

        String sql =
                "SELECT name, user_type " +
                        "FROM users " +
                        "WHERE email=? AND password=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    email
            );

            ps.setString(
                    2,
                    password
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getString("name");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =====================================================
    // GET USER TYPE
    // =====================================================

    public String getUserType(
            String email,
            String password
    ) {

        String sql =
                "SELECT user_type " +
                        "FROM users " +
                        "WHERE email=? AND password=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    email
            );

            ps.setString(
                    2,
                    password
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getString(
                        "user_type"
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =====================================================
    // GET USER BY ID
    // =====================================================

    public String[] getUserById(
            int userId
    ) {

        String sql =
                "SELECT user_id, name, email, phone, user_type " +
                        "FROM users " +
                        "WHERE user_id=?";

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

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return new String[]{

                        String.valueOf(
                                rs.getInt("user_id")
                        ),

                        rs.getString("name"),

                        rs.getString("email"),

                        rs.getString("phone"),

                        rs.getString("user_type")
                };
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}