package gui;

import dao.ReportDAO;

import java.awt.*;
import java.util.List;

public class ReportsPanel extends Panel {

    // =====================================================
    // CAMPUSCHARGE COLORS
    // =====================================================

    private final Color NAVY = new Color(31, 61, 82);
    private final Color BLUE = new Color(57, 103, 132);
    private final Color LIGHT_BG = new Color(242, 245, 247);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(25, 45, 60);
    private final Color GREY = new Color(105, 116, 125);

    // =====================================================
    // COMPONENTS
    // =====================================================

    private java.awt.List reportList;

    private TextField txtVehicle;

    private TextArea output;

    private ReportDAO dao;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ReportsPanel() {

        dao = new ReportDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BG
        );

        createControls();

        createOutput();
    }

    // =====================================================
    // TOP CONTROL SECTION
    // =====================================================

    private void createControls() {

        Panel controlCard =
                new Panel(
                        new BorderLayout()
                );

        controlCard.setBackground(
                WHITE
        );

        // -------------------------------------------------
        // HEADER
        // -------------------------------------------------

        Label title =
                new Label(
                        "REPORTS & ANALYTICS",
                        Label.LEFT
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        title.setForeground(
                WHITE
        );

        title.setBackground(
                NAVY
        );

        controlCard.add(
                title,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // CONTROLS
        // -------------------------------------------------

        Panel controls =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                14
                        )
                );

        controls.setBackground(
                WHITE
        );

        // STATION REPORT
        Button stationReport =
                createButton(
                        "STATION REPORT",
                        BLUE,
                        155
                );

        controls.add(
                stationReport
        );

        // VEHICLE NUMBER LABEL
        Label vehicleLabel =
                new Label(
                        "Vehicle No:"
                );

        vehicleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        vehicleLabel.setForeground(
                TEXT
        );

        controls.add(
                vehicleLabel
        );

        // VEHICLE INPUT
        txtVehicle =
                new TextField(
                        15
                );

        controls.add(
                txtVehicle
        );

        // VEHICLE HISTORY
        Button vehicleReport =
                createButton(
                        "VEHICLE HISTORY",
                        NAVY,
                        155
                );

        controls.add(
                vehicleReport
        );

        // PROCEDURE
        Button procedure =
                createButton(
                        "STATION USAGE",
                        GREY,
                        155
                );

        controls.add(
                procedure
        );

        controlCard.add(
                controls,
                BorderLayout.CENTER
        );

        add(
                controlCard,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // EVENTS
        // -------------------------------------------------

        stationReport.addActionListener(
                e -> showStationReport()
        );

        vehicleReport.addActionListener(
                e -> showVehicleHistory()
        );

        procedure.addActionListener(
                e -> showProcedure()
        );
    }

    // =====================================================
    // OUTPUT AREA
    // =====================================================

    private void createOutput() {

        Panel outputCard =
                new Panel(
                        new BorderLayout()
                );

        outputCard.setBackground(
                WHITE
        );

        // -------------------------------------------------
        // REPORT DATA HEADER
        // -------------------------------------------------

        Label listTitle =
                new Label(
                        "REPORT RESULTS",
                        Label.LEFT
                );

        listTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        listTitle.setForeground(
                WHITE
        );

        listTitle.setBackground(
                NAVY
        );

        outputCard.add(
                listTitle,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // REPORT LIST
        // -------------------------------------------------

        reportList =
                new java.awt.List(
                        14,
                        false
                );

        reportList.setBackground(
                WHITE
        );

        reportList.setForeground(
                TEXT
        );

        reportList.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        12
                )
        );

        outputCard.add(
                reportList,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // SUMMARY / OUTPUT AREA
        // -------------------------------------------------

        Panel summaryCard =
                new Panel(
                        new BorderLayout()
                );

        summaryCard.setBackground(
                new Color(
                        247,
                        249,
                        250
                )
        );

        Label summaryTitle =
                new Label(
                        "REPORT SUMMARY",
                        Label.LEFT
                );

        summaryTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        summaryTitle.setForeground(
                TEXT
        );

        summaryCard.add(
                summaryTitle,
                BorderLayout.NORTH
        );

        output =
                new TextArea(
                        "",
                        4,
                        80,
                        TextArea.SCROLLBARS_VERTICAL_ONLY
                );

        output.setEditable(
                false
        );

        output.setBackground(
                WHITE
        );

        output.setForeground(
                TEXT
        );

        output.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        summaryCard.add(
                output,
                BorderLayout.CENTER
        );

        outputCard.add(
                summaryCard,
                BorderLayout.SOUTH
        );

        add(
                outputCard,
                BorderLayout.CENTER
        );
    }

    // =====================================================
    // STATION REPORT
    // =====================================================

    private void showStationReport() {

        reportList.removeAll();

        List<String[]> list =
                dao.stationReport();

        if (
                list == null
                        || list.isEmpty()
        ) {

            reportList.add(
                    "No station report data available."
            );

            output.setText(
                    "Station Utilization Report\n\n" +
                            "No records were returned."
            );

            return;
        }

        // -------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------

        reportList.add(
                String.format(
                        "%-5s | %-25s | %-18s | %-7s | %-9s | %-12s | %-12s",
                        "ID",
                        "STATION",
                        "LOCATION",
                        "POINTS",
                        "SESSIONS",
                        "ENERGY",
                        "REVENUE"
                )
        );

        reportList.add(
                "-----------------------------------------------------------------------------------------------"
        );

        for (String[] r : list) {

            reportList.add(
                    String.format(
                            "%-5s | %-25s | %-18s | %-7s | %-9s | %-9s kWh | ₹%-10s",
                            r[0],
                            r[1],
                            r[2],
                            r[3],
                            r[4],
                            r[5],
                            r[6]
                    )
            );
        }

        output.setText(
                "Station Utilization Report\n\n" +
                        "Generated from the MySQL station_utilization view.\n" +
                        "This report provides station-level information including charging points, sessions, energy usage and revenue."
        );
    }

    // =====================================================
    // VEHICLE HISTORY
    // =====================================================

    private void showVehicleHistory() {

        String vehicle =
                txtVehicle
                        .getText()
                        .trim();

        if (
                vehicle.isEmpty()
        ) {

            showMessage(
                    "Enter a vehicle number first."
            );

            txtVehicle.requestFocus();

            return;
        }

        reportList.removeAll();

        List<String[]> list =
                dao.vehicleHistory(
                        vehicle
                );

        if (
                list == null
                        || list.isEmpty()
        ) {

            reportList.add(
                    "No charging history found for vehicle: "
                            + vehicle
            );

            output.setText(
                    "Vehicle Charging History\n\n" +
                            "No records were found for vehicle "
                            + vehicle
                            + "."
            );

            return;
        }

        // -------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------

        reportList.add(
                String.format(
                        "%-9s | %-18s | %-15s | %-12s | %-12s | %-12s",
                        "SESSION",
                        "DATE/TIME",
                        "POINT",
                        "START",
                        "ENERGY",
                        "COST"
                )
        );

        reportList.add(
                "--------------------------------------------------------------------------------"
        );

        for (String[] r : list) {

            reportList.add(
                    String.format(
                            "Session %-3s | %-18s | %-15s | %-12s | %-8s kWh | ₹%-8s | %s",
                            r[0],
                            r[1],
                            r[3],
                            r[4],
                            r[5],
                            r[6],
                            r[7]
                    )
            );
        }

        output.setText(
                "Vehicle Charging History\n\n" +
                        "Vehicle Number: "
                        + vehicle
                        + "\n\n"
                        + "Charging sessions, energy consumption and associated costs are displayed above."
        );
    }

    // =====================================================
    // STATION USAGE PROCEDURE
    // =====================================================

    private void showProcedure() {

        Dialog dialog =
                new Dialog(
                        getFrame(),
                        "Station Usage Procedure",
                        true
                );

        dialog.setLayout(
                new BorderLayout()
        );

        dialog.setBackground(
                WHITE
        );

        // -------------------------------------------------
        // HEADER
        // -------------------------------------------------

        Label title =
                new Label(
                        "RUN STATION USAGE PROCEDURE",
                        Label.LEFT
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        title.setForeground(
                WHITE
        );

        title.setBackground(
                NAVY
        );

        dialog.add(
                title,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // FORM
        // -------------------------------------------------

        Panel form =
                new Panel(
                        new GridBagLayout()
                );

        form.setBackground(
                WHITE
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        18,
                        20,
                        18,
                        20
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        Label stationLabel =
                new Label(
                        "Station ID:"
                );

        stationLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        stationLabel.setForeground(
                TEXT
        );

        gbc.gridx = 0;
        gbc.gridy = 0;

        form.add(
                stationLabel,
                gbc
        );

        TextField txtId =
                new TextField();

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                txtId,
                gbc
        );

        dialog.add(
                form,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // BUTTONS
        // -------------------------------------------------

        Panel buttons =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                10
                        )
                );

        buttons.setBackground(
                WHITE
        );

        Button run =
                createButton(
                        "RUN",
                        BLUE,
                        100
                );

        Button cancel =
                createButton(
                        "CANCEL",
                        GREY,
                        100
                );

        buttons.add(run);
        buttons.add(cancel);

        dialog.add(
                buttons,
                BorderLayout.SOUTH
        );

        // -------------------------------------------------
        // RUN EVENT
        // -------------------------------------------------

        run.addActionListener(
                e -> {

                    try {

                        int stationId =
                                Integer.parseInt(
                                        txtId
                                                .getText()
                                                .trim()
                                );

                        String result =
                                dao.stationUsageProcedure(
                                        stationId
                                );

                        output.setText(
                                "Station Usage Procedure Result\n\n"
                                        + result
                        );

                        reportList.removeAll();

                        reportList.add(
                                "Station Usage Procedure executed for Station ID: "
                                        + stationId
                        );

                        dialog.dispose();

                    } catch (
                            NumberFormatException ex
                    ) {

                        showMessage(
                                "Enter a valid station ID."
                        );
                    }
                }
        );

        cancel.addActionListener(
                e -> dialog.dispose()
        );

        dialog.setSize(
                480,
                230
        );

        dialog.setLocationRelativeTo(
                getFrame()
        );

        dialog.setVisible(
                true
        );
    }

    // =====================================================
    // BUTTON
    // =====================================================

    private Button createButton(
            String text,
            Color background,
            int width
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
                        width,
                        38
                )
        );

        return button;
    }

    // =====================================================
    // MESSAGE
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

        Label label =
                new Label(
                        message,
                        Label.CENTER
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        label.setForeground(
                TEXT
        );

        dialog.add(
                label,
                BorderLayout.CENTER
        );

        Panel buttons =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        Button ok =
                new Button(
                        "OK"
                );

        ok.setBackground(
                BLUE
        );

        ok.setForeground(
                WHITE
        );

        ok.addActionListener(
                e -> dialog.dispose()
        );

        buttons.add(ok);

        dialog.add(
                buttons,
                BorderLayout.SOUTH
        );

        dialog.setSize(
                470,
                150
        );

        dialog.setLocationRelativeTo(
                parent
        );

        dialog.setVisible(
                true
        );
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