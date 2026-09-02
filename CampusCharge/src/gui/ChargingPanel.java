package gui;

import dao.ChargingDAO;

import java.awt.*;
import java.util.List;

public class ChargingPanel extends Panel {

    // =====================================================
    // CAMPUSCHARGE COLORS
    // =====================================================

    private final Color NAVY = new Color(31, 61, 82);
    private final Color BLUE = new Color(57, 103, 132);
    private final Color LIGHT_BG = new Color(242, 245, 247);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(25, 45, 60);
    private final Color RED = new Color(160, 65, 65);
    private final Color GREY = new Color(105, 116, 125);

    // =====================================================
    // COMPONENTS
    // =====================================================

    private Choice choiceVehicle;
    private Choice choicePoint;

    private TextField txtReservation;
    private TextField txtInitialBattery;
    private TextField txtFinalBattery;

    private java.awt.List sessionList;

    private ChargingDAO dao;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ChargingPanel() {

        dao = new ChargingDAO();

        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);

        createForm();
        createList();

        loadChoices();
        loadSessions();
    }

    // =====================================================
    // FORM
    // =====================================================

    private void createForm() {

        Panel formCard =
                new Panel(new BorderLayout());

        formCard.setBackground(WHITE);

        // -------------------------------------------------
        // SECTION HEADER
        // -------------------------------------------------

        Label title =
                new Label(
                        "CHARGING SESSION",
                        Label.LEFT
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        title.setForeground(WHITE);
        title.setBackground(NAVY);

        formCard.add(
                title,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // FORM AREA
        // -------------------------------------------------

        Panel form =
                new Panel(
                        new GridBagLayout()
                );

        form.setBackground(WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        18,
                        10,
                        18
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // VEHICLE
        addLabel(
                form,
                gbc,
                "Vehicle:",
                0,
                0
        );

        choiceVehicle =
                new Choice();

        addComponent(
                form,
                gbc,
                choiceVehicle,
                1,
                0
        );

        // CHARGING POINT
        addLabel(
                form,
                gbc,
                "Charging Point:",
                2,
                0
        );

        choicePoint =
                new Choice();

        addComponent(
                form,
                gbc,
                choicePoint,
                3,
                0
        );

        // RESERVATION
        addLabel(
                form,
                gbc,
                "Reservation ID:",
                0,
                1
        );

        txtReservation =
                new TextField("0");

        addComponent(
                form,
                gbc,
                txtReservation,
                1,
                1
        );

        // INITIAL BATTERY
        addLabel(
                form,
                gbc,
                "Initial Battery (%):",
                2,
                1
        );

        txtInitialBattery =
                new TextField();

        addComponent(
                form,
                gbc,
                txtInitialBattery,
                3,
                1
        );

        // FINAL BATTERY
        addLabel(
                form,
                gbc,
                "Final Battery (%):",
                0,
                2
        );

        txtFinalBattery =
                new TextField();

        addComponent(
                form,
                gbc,
                txtFinalBattery,
                1,
                2
        );

        // INFORMATION LABEL
        Label note =
                new Label(
                        "Start a session using CHECK-IN. Select an active session and enter battery values before CHECK-OUT."
                );

        note.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        note.setForeground(GREY);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        form.add(
                note,
                gbc
        );

        formCard.add(
                form,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // BUTTONS
        // -------------------------------------------------

        Panel buttonPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                14,
                                12
                        )
                );

        buttonPanel.setBackground(WHITE);

        Button btnCheckIn =
                createButton(
                        "CHECK-IN",
                        BLUE
                );

        Button btnCheckOut =
                createButton(
                        "CHECK-OUT",
                        NAVY
                );

        Button btnRefresh =
                createButton(
                        "REFRESH",
                        GREY
                );

        buttonPanel.add(btnCheckIn);
        buttonPanel.add(btnCheckOut);
        buttonPanel.add(btnRefresh);

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                formCard,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // EVENTS
        // -------------------------------------------------

        btnCheckIn.addActionListener(
                e -> checkIn()
        );

        btnCheckOut.addActionListener(
                e -> checkOut()
        );

        btnRefresh.addActionListener(
                e -> {
                    loadChoices();
                    loadSessions();
                }
        );
    }

    // =====================================================
    // SESSION LIST
    // =====================================================

    private void createList() {

        Panel listCard =
                new Panel(
                        new BorderLayout()
                );

        listCard.setBackground(WHITE);

        // -------------------------------------------------
        // HEADER
        // -------------------------------------------------

        Label heading =
                new Label(
                        "ACTIVE CHARGING SESSIONS",
                        Label.LEFT
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        heading.setForeground(WHITE);
        heading.setBackground(NAVY);

        listCard.add(
                heading,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------

        Panel tableHeader =
                new Panel(
                        new GridLayout(1, 6)
                );

        tableHeader.setBackground(
                new Color(
                        230,
                        236,
                        240
                )
        );

        addHeader(
                tableHeader,
                "SESSION ID"
        );

        addHeader(
                tableHeader,
                "VEHICLE"
        );

        addHeader(
                tableHeader,
                "POINT"
        );

        addHeader(
                tableHeader,
                "CHECK-IN"
        );

        addHeader(
                tableHeader,
                "ENERGY"
        );

        addHeader(
                tableHeader,
                "TARIFF"
        );

        // -------------------------------------------------
        // LIST
        // -------------------------------------------------

        sessionList =
                new java.awt.List(
                        12,
                        false
                );

        sessionList.setBackground(
                WHITE
        );

        sessionList.setForeground(
                TEXT
        );

        sessionList.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        Panel center =
                new Panel(
                        new BorderLayout()
                );

        center.setBackground(WHITE);

        center.add(
                tableHeader,
                BorderLayout.NORTH
        );

        center.add(
                sessionList,
                BorderLayout.CENTER
        );

        listCard.add(
                center,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // BOTTOM INFO BAR
        // -------------------------------------------------

        Panel bottomBar =
                new Panel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                18,
                                9
                        )
                );

        bottomBar.setBackground(
                new Color(
                        247,
                        249,
                        250
                )
        );

        Label info =
                new Label(
                        "Select an active session to complete the charging process."
                );

        info.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        info.setForeground(GREY);

        bottomBar.add(info);

        listCard.add(
                bottomBar,
                BorderLayout.SOUTH
        );

        add(
                listCard,
                BorderLayout.CENTER
        );
    }

    // =====================================================
    // LOAD VEHICLES AND POINTS
    // =====================================================

    private void loadChoices() {

        if (
                choiceVehicle == null
                        || choicePoint == null
        ) {
            return;
        }

        choiceVehicle.removeAll();
        choicePoint.removeAll();

        List<String[]> vehicles =
                dao.getVehicles();

        if (vehicles != null) {

            for (String[] v : vehicles) {

                choiceVehicle.add(
                        v[0]
                                + " - "
                                + v[1]
                );
            }
        }

        List<String[]> points =
                dao.getPoints();

        if (points != null) {

            for (String[] p : points) {

                choicePoint.add(
                        p[0]
                                + " - "
                                + p[1]
                                + " - ₹"
                                + p[2]
                );
            }
        }
    }

    // =====================================================
    // GET VEHICLE ID
    // =====================================================

    private int getVehicleId() {

        String selected =
                choiceVehicle
                        .getSelectedItem();

        return Integer.parseInt(
                selected
                        .split(
                                " - ",
                                2
                        )[0]
        );
    }

    // =====================================================
    // GET POINT ID
    // =====================================================

    private int getPointId() {

        String selected =
                choicePoint
                        .getSelectedItem();

        return Integer.parseInt(
                selected
                        .split(
                                " - ",
                                2
                        )[0]
        );
    }

    // =====================================================
    // CHECK-IN
    // =====================================================

    private void checkIn() {

        try {

            if (
                    choiceVehicle
                            .getItemCount()
                            == 0
            ) {

                showMessage(
                        "No vehicle is available."
                );

                return;
            }

            if (
                    choicePoint
                            .getItemCount()
                            == 0
            ) {

                showMessage(
                        "No charging point is available."
                );

                return;
            }

            int reservation =
                    Integer.parseInt(
                            txtReservation
                                    .getText()
                                    .trim()
                    );

            if (reservation < 0) {

                showMessage(
                        "Reservation ID cannot be negative."
                );

                return;
            }

            String selected =
                    choicePoint
                            .getSelectedItem();

            String[] p =
                    selected.split(
                            " - "
                    );

            double tariff =
                    Double.parseDouble(
                            p[p.length - 1]
                                    .replace(
                                            "₹",
                                            ""
                                    )
                    );

            if (
                    dao.checkIn(
                            getVehicleId(),
                            getPointId(),
                            reservation,
                            tariff
                    )
            ) {

                showMessage(
                        "Vehicle checked in successfully. Charging session started."
                );

                loadChoices();
                loadSessions();

            } else {

                showMessage(
                        "Unable to start charging session."
                );
            }

        } catch (NumberFormatException e) {

            showMessage(
                    "Reservation ID must be a valid number."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Please enter valid check-in details."
            );
        }
    }

    // =====================================================
    // CHECK-OUT
    // =====================================================

    private void checkOut() {

        int index =
                sessionList
                        .getSelectedIndex();

        if (index == -1) {

            showMessage(
                    "Select an active charging session first."
            );

            return;
        }

        try {

            String selected =
                    sessionList
                            .getItem(index);

            int sessionId =
                    Integer.parseInt(
                            selected
                                    .split(
                                            "\\|"
                                    )[0]
                                    .trim()
                    );

            String initialText =
                    txtInitialBattery
                            .getText()
                            .trim();

            String finalText =
                    txtFinalBattery
                            .getText()
                            .trim();

            if (
                    initialText.isEmpty()
                            || finalText.isEmpty()
            ) {

                showMessage(
                        "Enter initial and final battery percentages."
                );

                return;
            }

            int initial =
                    Integer.parseInt(
                            initialText
                    );

            int finalBattery =
                    Integer.parseInt(
                            finalText
                    );

            if (
                    initial < 0
                            || initial > 100
                            || finalBattery < 0
                            || finalBattery > 100
                            || finalBattery < initial
            ) {

                showMessage(
                        "Invalid battery values. Final battery must be greater than or equal to initial battery."
                );

                return;
            }

            double energy =
                    Double.parseDouble(
                            calculateEnergy(
                                    initial,
                                    finalBattery
                            )
                    );

            double cost =
                    dao.checkout(
                            sessionId,
                            initial,
                            finalBattery,
                            energy
                    );

            if (cost >= 0) {

                showMessage(
                        "Charging completed successfully. " +
                                "Energy Used: "
                                + String.format(
                                "%.2f",
                                energy
                        )
                                + " kWh | Total Cost: ₹"
                                + String.format(
                                "%.2f",
                                cost
                        )
                );

                txtInitialBattery.setText("");
                txtFinalBattery.setText("");

                loadChoices();
                loadSessions();

            } else {

                showMessage(
                        "Unable to complete charging session."
                );
            }

        } catch (NumberFormatException e) {

            showMessage(
                    "Battery values must be valid numbers."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Unable to complete the charging session."
            );
        }
    }

    // =====================================================
    // ENERGY CALCULATION
    // =====================================================

    private String calculateEnergy(
            int initial,
            int finalBattery
    ) {

        double batteryDifference =
                finalBattery - initial;

        /*
         * Academic implementation:
         * Assumes approximately 0.5 kWh
         * for every 1% battery increase.
         */

        double energy =
                batteryDifference * 0.5;

        return String.format(
                "%.2f",
                energy
        );
    }

    // =====================================================
    // LOAD ACTIVE SESSIONS
    // =====================================================

    private void loadSessions() {

        if (sessionList == null) {
            return;
        }

        sessionList.removeAll();

        List<String[]> sessions =
                dao.getActiveSessions();

        if (sessions == null) {
            return;
        }

        for (String[] s : sessions) {

            String display =
                    String.format(
                            "%-10s | %-12s | %-8s | %-20s | %7s kWh | ₹%-7s",
                            s[0],
                            s[1],
                            s[2],
                            s[3],
                            s[4],
                            s[5]
                    );

            sessionList.add(
                    display
            );
        }
    }

    // =====================================================
    // LABEL
    // =====================================================

    private void addLabel(
            Panel panel,
            GridBagConstraints gbc,
            String text,
            int x,
            int y
    ) {

        Label label =
                new Label(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(TEXT);

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0;

        panel.add(
                label,
                gbc
        );
    }

    // =====================================================
    // COMPONENT
    // =====================================================

    private void addComponent(
            Panel panel,
            GridBagConstraints gbc,
            Component component,
            int x,
            int y
    ) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 1;

        panel.add(
                component,
                gbc
        );
    }

    // =====================================================
    // BUTTON
    // =====================================================

    private Button createButton(
            String text,
            Color background
    ) {

        Button button =
                new Button(text);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                WHITE
        );

        button.setBackground(
                background
        );

        button.setPreferredSize(
                new Dimension(
                        130,
                        38
                )
        );

        return button;
    }

    // =====================================================
    // TABLE HEADER
    // =====================================================

    private void addHeader(
            Panel panel,
            String text
    ) {

        Label label =
                new Label(
                        text,
                        Label.CENTER
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(
                TEXT
        );

        panel.add(label);
    }

    // =====================================================
    // MESSAGE DIALOG
    // =====================================================

    private void showMessage(
            String message
    ) {

        Frame parent =
                getFrame();

        if (parent == null) {

            System.out.println(message);

            return;
        }

        Dialog dialog =
                new Dialog(
                        parent,
                        "CampusCharge",
                        true
                );

        dialog.setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        Label messageLabel =
                new Label(
                        message,
                        Label.CENTER
                );

        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        messageLabel.setForeground(
                TEXT
        );

        dialog.add(
                messageLabel,
                BorderLayout.CENTER
        );

        Panel buttonPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        Button ok =
                new Button("OK");

        ok.setBackground(BLUE);
        ok.setForeground(WHITE);

        ok.addActionListener(
                e -> dialog.dispose()
        );

        buttonPanel.add(ok);

        dialog.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        dialog.setSize(
                500,
                160
        );

        dialog.setLocationRelativeTo(
                parent
        );

        dialog.setVisible(true);
    }

    // =====================================================
    // GET FRAME
    // =====================================================

    private Frame getFrame() {

        Component c =
                this;

        while (
                c != null
                        && !(c instanceof Frame)
        ) {

            c = c.getParent();
        }

        return (Frame) c;
    }
}