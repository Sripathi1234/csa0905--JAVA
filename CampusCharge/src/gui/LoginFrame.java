package gui;

import java.awt.*;
import java.awt.event.*;
import db.DBConnection;
import java.sql.*;

public class LoginFrame extends Frame {

    private TextField txtUsername;
    private TextField txtPassword;
    private Button btnLogin;

    public LoginFrame() {

        setTitle("CampusCharge - Login");

        setSize(700, 450);

        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        createLoginScreen();

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void createLoginScreen() {

        // ============================
        // LEFT PANEL
        // ============================

        Panel leftPanel = new Panel();

        leftPanel.setLayout(
                new GridBagLayout()
        );

        leftPanel.setBackground(
                new Color(30, 55, 75)
        );

        GridBagConstraints leftGbc =
                new GridBagConstraints();

        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.insets =
                new Insets(10, 20, 10, 20);

        Label title =
                new Label(
                        "CAMPUSCHARGE",
                        Label.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(Color.WHITE);

        leftPanel.add(title, leftGbc);


        leftGbc.gridy++;

        Label subtitle =
                new Label(
                        "Smart EV Charging",
                        Label.CENTER
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        subtitle.setForeground(Color.WHITE);

        leftPanel.add(subtitle, leftGbc);


        leftGbc.gridy++;

        Label campus =
                new Label(
                        "Management System",
                        Label.CENTER
                );

        campus.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        campus.setForeground(Color.WHITE);

        leftPanel.add(campus, leftGbc);


        leftGbc.gridy++;

        Label tagline =
                new Label(
                        "Smart Energy • Smart Campus",
                        Label.CENTER
                );

        tagline.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        13
                )
        );

        tagline.setForeground(Color.LIGHT_GRAY);

        leftPanel.add(tagline, leftGbc);


        // ============================
        // RIGHT PANEL
        // ============================

        Panel rightPanel = new Panel();

        rightPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(8, 25, 8, 25);


        // Login heading

        Label loginTitle =
                new Label(
                        "ADMIN LOGIN",
                        Label.CENTER
                );

        loginTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        rightPanel.add(
                loginTitle,
                gbc
        );


        // Username label

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        Label lblUsername =
                new Label("Username:");

        lblUsername.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        rightPanel.add(
                lblUsername,
                gbc
        );


        // Username field

        txtUsername =
                new TextField(20);

        gbc.gridx = 1;

        rightPanel.add(
                txtUsername,
                gbc
        );


        // Password label

        gbc.gridx = 0;
        gbc.gridy = 2;

        Label lblPassword =
                new Label("Password:");

        lblPassword.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        rightPanel.add(
                lblPassword,
                gbc
        );


        // Password field

        txtPassword =
                new TextField(20);

        txtPassword.setEchoChar('*');

        gbc.gridx = 1;

        rightPanel.add(
                txtPassword,
                gbc
        );


        // Login button

        btnLogin =
                new Button("LOGIN");

        btnLogin.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        gbc.insets =
                new Insets(20, 25, 8, 25);

        rightPanel.add(
                btnLogin,
                gbc
        );


        // ============================
        // LOGIN EVENT
        // ============================

        btnLogin.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        login();
                    }
                }
        );


        // ============================
        // ADD PANELS TO FRAME
        // ============================

        add(
                leftPanel,
                BorderLayout.WEST
        );

        add(
                rightPanel,
                BorderLayout.CENTER
        );
    }


    private void login() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (username.isEmpty()) {
            showMessage("Please enter username.");
            return;
        }

        if (password.isEmpty()) {
            showMessage("Please enter password.");
            return;
        }

        boolean loginSuccessful = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM users WHERE name=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                loginSuccessful = true;

            } else {

                showMessage("Invalid Username or Password");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            showMessage("Database Error: " + e.getMessage());

            return;
        }

        // ==========================================
        // OPEN DASHBOARD ONLY AFTER DATABASE LOGIN
        // ==========================================

        if (loginSuccessful) {

            showMessage("Login Successful!");

            dispose();

            // Dashboard is OUTSIDE the database try-catch
            new DashboardFrame(username);
        }
    }


    private void showMessage(String message) {

        Dialog dialog =
                new Dialog(
                        this,
                        "Message",
                        true
                );

        dialog.setLayout(
                new FlowLayout()
        );

        Label label =
                new Label(message);

        Button okButton =
                new Button("OK");

        okButton.addActionListener(
                e -> dialog.dispose()
        );

        dialog.add(label);
        dialog.add(okButton);

        dialog.setSize(300, 120);

        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
    }
}