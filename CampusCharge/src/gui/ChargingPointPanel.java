package gui;

import dao.ChargingPointDAO;

import java.awt.*;
import java.util.List;

public class ChargingPointPanel extends Panel {

    // =====================================================
    // CAMPUSCHARGE COLORS
    // =====================================================

    private final Color NAVY = new Color(31, 61, 82);
    private final Color DARK_NAVY = new Color(34, 49, 63);
    private final Color BLUE = new Color(57, 103, 132);
    private final Color LIGHT_BG = new Color(242, 245, 247);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(25, 45, 60);
    private final Color RED = new Color(160, 65, 65);
    private final Color GREY = new Color(105, 116, 125);

    // =====================================================
    // COMPONENTS
    // =====================================================

    private Choice choiceStation;
    private Choice choiceCharger;
    private Choice choiceStatus;

    private TextField txtName;
    private TextField txtPower;
    private TextField txtTariff;

    private java.awt.List pointList;

    private ChargingPointDAO dao;

    private int selectedId = -1;

    private List<String[]> stations;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ChargingPointPanel() {

        dao = new ChargingPointDAO();

        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);

        createForm();
        createList();

        loadStations();
        loadPoints();
    }

    // =====================================================
    // FORM
    // =====================================================

    private void createForm() {

        Panel formCard = new Panel(new BorderLayout());
        formCard.setBackground(WHITE);

        // -------------------------------------------------
        // TITLE
        // -------------------------------------------------

        Label title = new Label(
                "CHARGING POINT INFORMATION",
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

        Panel form = new Panel(
                new GridBagLayout()
        );

        form.setBackground(WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 18, 10, 18);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Station
        addLabel(
                form,
                gbc,
                "Station:",
                0,
                0
        );

        choiceStation = new Choice();

        addComponent(
                form,
                gbc,
                choiceStation,
                1,
                0
        );

        // Point Name
        addLabel(
                form,
                gbc,
                "Point Name:",
                2,
                0
        );

        txtName = new TextField();

        addComponent(
                form,
                gbc,
                txtName,
                3,
                0
        );

        // Charger Type
        addLabel(
                form,
                gbc,
                "Charger Type:",
                0,
                1
        );

        choiceCharger = new Choice();

        choiceCharger.add("AC Slow");
        choiceCharger.add("AC Fast");
        choiceCharger.add("DC Fast");

        addComponent(
                form,
                gbc,
                choiceCharger,
                1,
                1
        );

        // Power
        addLabel(
                form,
                gbc,
                "Power (kW):",
                2,
                1
        );

        txtPower = new TextField();

        addComponent(
                form,
                gbc,
                txtPower,
                3,
                1
        );

        // Status
        addLabel(
                form,
                gbc,
                "Status:",
                0,
                2
        );

        choiceStatus = new Choice();

        choiceStatus.add("AVAILABLE");
        choiceStatus.add("OCCUPIED");
        choiceStatus.add("RESERVED");
        choiceStatus.add("MAINTENANCE");

        addComponent(
                form,
                gbc,
                choiceStatus,
                1,
                2
        );

        // Tariff
        addLabel(
                form,
                gbc,
                "Tariff Rate (₹/kWh):",
                2,
                2
        );

        txtTariff = new TextField();

        addComponent(
                form,
                gbc,
                txtTariff,
                3,
                2
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

        Button btnAdd =
                createButton("ADD", BLUE);

        Button btnUpdate =
                createButton("UPDATE", NAVY);

        Button btnDelete =
                createButton("DELETE", RED);

        Button btnClear =
                createButton("CLEAR", GREY);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

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

        btnAdd.addActionListener(
                e -> addPoint()
        );

        btnUpdate.addActionListener(
                e -> updatePoint()
        );

        btnDelete.addActionListener(
                e -> deletePoint()
        );

        btnClear.addActionListener(
                e -> clearForm()
        );
    }

    // =====================================================
    // LOWER SECTION
    // =====================================================

    private void createList() {

        Panel listCard =
                new Panel(
                        new BorderLayout()
                );

        listCard.setBackground(WHITE);

        // -------------------------------------------------
        // SECTION HEADER
        // -------------------------------------------------

        Label heading =
                new Label(
                        "REGISTERED CHARGING POINTS",
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
                        new GridLayout(1, 7)
                );

        tableHeader.setBackground(
                new Color(230, 236, 240)
        );

        addHeader(tableHeader, "ID");
        addHeader(tableHeader, "STATION");
        addHeader(tableHeader, "POINT");
        addHeader(tableHeader, "CHARGER");
        addHeader(tableHeader, "POWER");
        addHeader(tableHeader, "STATUS");
        addHeader(tableHeader, "TARIFF");

        // -------------------------------------------------
        // LIST
        // -------------------------------------------------

        pointList =
                new java.awt.List(
                        14,
                        false
                );

        pointList.setBackground(WHITE);
        pointList.setForeground(TEXT);

        /*
         * Monospaced font is important here.
         * It makes all columns line up properly.
         */
        pointList.setFont(
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
                pointList,
                BorderLayout.CENTER
        );

        listCard.add(
                center,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // BOTTOM INFORMATION BAR
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
                new Color(247, 249, 250)
        );

        Label info =
                new Label(
                        "Select a charging point to edit or delete its details."
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

        // -------------------------------------------------
        // SELECT EVENT
        // -------------------------------------------------

        pointList.addItemListener(
                e -> selectPoint()
        );
    }

    // =====================================================
    // FORM LABEL
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
        gbc.weightx = 0;

        panel.add(
                label,
                gbc
        );
    }

    // =====================================================
    // FORM COMPONENT
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

        button.setForeground(WHITE);
        button.setBackground(background);

        button.setPreferredSize(
                new Dimension(
                        105,
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
                        12
                )
        );

        label.setForeground(TEXT);

        panel.add(label);
    }

    // =====================================================
    // LOAD STATIONS
    // =====================================================

    private void loadStations() {

        stations =
                dao.getAllStations();

        choiceStation.removeAll();

        if (stations == null) {
            return;
        }

        for (String[] station : stations) {

            choiceStation.add(
                    station[0]
                            + " - "
                            + station[1]
            );
        }
    }

    // =====================================================
    // GET STATION ID
    // =====================================================

    private int getSelectedStationId() {

        if (
                choiceStation.getItemCount()
                        == 0
        ) {
            return -1;
        }

        String selected =
                choiceStation.getSelectedItem();

        if (
                selected == null
                        || selected.trim().isEmpty()
        ) {
            return -1;
        }

        try {

            return Integer.parseInt(
                    selected
                            .split(
                                    " - ",
                                    2
                            )[0]
                            .trim()
            );

        } catch (Exception e) {

            return -1;
        }
    }

    // =====================================================
    // ADD POINT
    // =====================================================

    private void addPoint() {

        if (!validateFields()) {
            return;
        }

        try {

            int stationId =
                    getSelectedStationId();

            if (stationId == -1) {

                showMessage(
                        "Please select a charging station."
                );

                return;
            }

            double powerKw =
                    Double.parseDouble(
                            txtPower
                                    .getText()
                                    .trim()
                    );

            double tariffRate =
                    Double.parseDouble(
                            txtTariff
                                    .getText()
                                    .trim()
                    );

            boolean success =
                    dao.addChargingPoint(
                            stationId,
                            txtName
                                    .getText()
                                    .trim(),
                            choiceCharger
                                    .getSelectedItem(),
                            powerKw,
                            choiceStatus
                                    .getSelectedItem(),
                            tariffRate
                    );

            if (success) {

                showMessage(
                        "Charging point added successfully."
                );

                clearForm();
                loadPoints();

            } else {

                showMessage(
                        "Unable to add charging point."
                );
            }

        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Power and tariff must contain valid numbers."
            );
        }
    }

    // =====================================================
    // UPDATE POINT
    // =====================================================

    private void updatePoint() {

        if (selectedId == -1) {

            showMessage(
                    "Please select a charging point first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            int stationId =
                    getSelectedStationId();

            if (stationId == -1) {

                showMessage(
                        "Please select a charging station."
                );

                return;
            }

            double powerKw =
                    Double.parseDouble(
                            txtPower
                                    .getText()
                                    .trim()
                    );

            double tariffRate =
                    Double.parseDouble(
                            txtTariff
                                    .getText()
                                    .trim()
                    );

            boolean success =
                    dao.updateChargingPoint(
                            selectedId,
                            stationId,
                            txtName
                                    .getText()
                                    .trim(),
                            choiceCharger
                                    .getSelectedItem(),
                            powerKw,
                            choiceStatus
                                    .getSelectedItem(),
                            tariffRate
                    );

            if (success) {

                showMessage(
                        "Charging point updated successfully."
                );

                clearForm();
                loadPoints();

            } else {

                showMessage(
                        "Unable to update charging point."
                );
            }

        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Power and tariff must contain valid numbers."
            );
        }
    }

    // =====================================================
    // DELETE POINT
    // =====================================================

    private void deletePoint() {

        if (selectedId == -1) {

            showMessage(
                    "Please select a charging point first."
            );

            return;
        }

        boolean success =
                dao.deleteChargingPoint(
                        selectedId
                );

        if (success) {

            showMessage(
                    "Charging point deleted successfully."
            );

            clearForm();
            loadPoints();

        } else {

            showMessage(
                    "Unable to delete charging point."
            );
        }
    }

    // =====================================================
    // LOAD POINTS
    // =====================================================

    private void loadPoints() {

        if (pointList == null) {
            return;
        }

        pointList.removeAll();

        List<String[]> points =
                dao.getAllChargingPoints();

        if (points == null) {
            return;
        }

        for (String[] point : points) {

            /*
             * Fixed-width formatting makes the list
             * look like a proper table instead of
             * random text.
             */

            String display =
                    String.format(
                            "%-4s | %-32s | %-7s | %-10s | %7s kW | %-12s | ₹%-6s",
                            point[0],
                            point[1],
                            point[2],
                            point[3],
                            point[4],
                            point[5],
                            point[6]
                    );

            pointList.add(display);
        }
    }

    // =====================================================
    // SELECT POINT
    // =====================================================

    private void selectPoint() {

        int index =
                pointList.getSelectedIndex();

        if (index == -1) {
            return;
        }

        String selected =
                pointList.getItem(index);

        try {

            /*
             * Because the row is now formatted
             * with | separators, splitting is much
             * cleaner and safer.
             */

            String[] parts =
                    selected.split(
                            "\\|"
                    );

            if (parts.length < 7) {

                showMessage(
                        "Unable to read selected charging point."
                );

                return;
            }

            // -------------------------------------------------
            // ID
            // -------------------------------------------------

            selectedId =
                    Integer.parseInt(
                            parts[0].trim()
                    );

            // -------------------------------------------------
            // STATION
            // -------------------------------------------------

            String station =
                    parts[1].trim();

            for (
                    int i = 0;
                    i < choiceStation.getItemCount();
                    i++
            ) {

                String item =
                        choiceStation.getItem(i);

                if (
                        item.endsWith(
                                " - " + station
                        )
                ) {

                    choiceStation.select(i);

                    break;
                }
            }

            // -------------------------------------------------
            // POINT NAME
            // -------------------------------------------------

            txtName.setText(
                    parts[2].trim()
            );

            // -------------------------------------------------
            // CHARGER
            // -------------------------------------------------

            choiceCharger.select(
                    parts[3].trim()
            );

            // -------------------------------------------------
            // POWER
            // -------------------------------------------------

            String power =
                    parts[4]
                            .replace(
                                    "kW",
                                    ""
                            )
                            .trim();

            txtPower.setText(power);

            // -------------------------------------------------
            // STATUS
            // -------------------------------------------------

            choiceStatus.select(
                    parts[5].trim()
            );

            // -------------------------------------------------
            // TARIFF
            // -------------------------------------------------

            String tariff =
                    parts[6]
                            .replace(
                                    "₹",
                                    ""
                            )
                            .trim();

            txtTariff.setText(tariff);

        } catch (Exception e) {

            selectedId = -1;

            showMessage(
                    "Unable to read the selected charging point."
            );
        }
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateFields() {

        if (
                choiceStation.getItemCount()
                        == 0
        ) {

            showMessage(
                    "No charging station is available."
            );

            return false;
        }

        if (
                txtName
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Point name cannot be empty."
            );

            return false;
        }

        if (
                txtPower
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Power cannot be empty."
            );

            return false;
        }

        if (
                txtTariff
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Tariff rate cannot be empty."
            );

            return false;
        }

        try {

            double power =
                    Double.parseDouble(
                            txtPower
                                    .getText()
                                    .trim()
                    );

            if (power <= 0) {

                showMessage(
                        "Power must be greater than zero."
                );

                return false;
            }

            double tariff =
                    Double.parseDouble(
                            txtTariff
                                    .getText()
                                    .trim()
                    );

            if (tariff < 0) {

                showMessage(
                        "Tariff cannot be negative."
                );

                return false;
            }

        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Power and tariff must be valid numbers."
            );

            return false;
        }

        return true;
    }

    // =====================================================
    // CLEAR FORM
    // =====================================================

    private void clearForm() {

        txtName.setText("");
        txtPower.setText("");
        txtTariff.setText("");

        selectedId = -1;

        int index =
                pointList.getSelectedIndex();

        if (index >= 0) {

            pointList.deselect(index);
        }

        if (
                choiceCharger.getItemCount()
                        > 0
        ) {

            choiceCharger.select(0);
        }

        if (
                choiceStatus.getItemCount()
                        > 0
        ) {

            choiceStatus.select(
                    "AVAILABLE"
            );
        }
    }

    // =====================================================
    // MESSAGE DIALOG
    // =====================================================

    private void showMessage(
            String message
    ) {

        Frame parent =
                getParentFrame();

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

        Button okButton =
                new Button("OK");

        okButton.setBackground(BLUE);
        okButton.setForeground(WHITE);

        okButton.addActionListener(
                e -> dialog.dispose()
        );

        buttonPanel.add(okButton);

        dialog.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        dialog.setSize(
                450,
                150
        );

        dialog.setLocationRelativeTo(
                parent
        );

        dialog.setVisible(true);
    }

    // =====================================================
    // GET PARENT FRAME
    // =====================================================

    private Frame getParentFrame() {

        Component component =
                this;

        while (
                component != null
        ) {

            if (
                    component instanceof Frame
            ) {

                return (Frame) component;
            }

            component =
                    component.getParent();
        }

        return null;
    }
}