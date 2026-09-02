package gui;

import dao.ReservationDAO;

import java.awt.*;
import java.util.List;

public class ReservationPanel extends Panel {

    private Choice choiceUser;
    private Choice choiceVehicle;
    private Choice choicePoint;

    private TextField txtDate;
    private TextField txtStart;
    private TextField txtEnd;

    private java.awt.List reservationList;

    private ReservationDAO dao;

    // =====================================================
    // COLORS
    // =====================================================

    private final Color NAVY = new Color(24, 45, 68);
    private final Color BLUE = new Color(45, 108, 180);
    private final Color LIGHT_BLUE = new Color(232, 242, 252);
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color BORDER = new Color(215, 222, 230);
    private final Color TEXT = new Color(45, 55, 65);
    private final Color GREEN = new Color(46, 125, 80);
    private final Color RED = new Color(190, 65, 65);

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ReservationPanel() {

        dao = new ReservationDAO();

        setLayout(new BorderLayout(0, 12));
        setBackground(BACKGROUND);

        createHeader();
        createForm();
        createList();

        // IMPORTANT:
        // All Choice components are initialized before
        // loading database data.
        loadUsers();
        loadPoints();
        loadReservations();
    }

    // =====================================================
    // HEADER
    // =====================================================

    private void createHeader() {

        Panel header = new Panel(new BorderLayout());
        header.setBackground(NAVY);

        Label title = new Label(
                "RESERVATION MANAGEMENT",
                Label.LEFT
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        title.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);

        Label subtitle = new Label(
                "Book and manage EV charging reservations",
                Label.RIGHT
        );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        subtitle.setForeground(
                new Color(210, 220, 232)
        );

        header.add(subtitle, BorderLayout.EAST);

        header.setPreferredSize(
                new Dimension(0, 55)
        );

        add(header, BorderLayout.NORTH);
    }

    // =====================================================
    // CREATE FORM
    // =====================================================

    private void createForm() {

        Panel outer = new Panel(new BorderLayout());
        outer.setBackground(Color.WHITE);

        // -------------------------------------------------
        // FORM TITLE
        // -------------------------------------------------

        Panel formTitlePanel =
                new Panel(new BorderLayout());

        formTitlePanel.setBackground(
                new Color(238, 243, 248)
        );

        Label formTitle =
                new Label(
                        "BOOK A CHARGING SLOT",
                        Label.LEFT
                );

        formTitle.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        formTitle.setForeground(NAVY);

        formTitlePanel.add(
                formTitle,
                BorderLayout.WEST
        );

        outer.add(
                formTitlePanel,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // FORM
        // -------------------------------------------------

        Panel form =
                new Panel(new GridBagLayout());

        form.setBackground(Color.WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 12, 7, 12);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // -------------------------------------------------
        // USER
        // -------------------------------------------------

        addFormLabel(
                form,
                "User",
                gbc,
                0,
                0
        );

        choiceUser = new Choice();

        addComponent(
                form,
                choiceUser,
                gbc,
                1,
                0
        );

        // -------------------------------------------------
        // VEHICLE
        // -------------------------------------------------

        addFormLabel(
                form,
                "Vehicle",
                gbc,
                0,
                1
        );

        choiceVehicle = new Choice();

        addComponent(
                form,
                choiceVehicle,
                gbc,
                1,
                1
        );

        // -------------------------------------------------
        // CHARGING POINT
        // -------------------------------------------------

        addFormLabel(
                form,
                "Charging Point",
                gbc,
                0,
                2
        );

        choicePoint = new Choice();

        addComponent(
                form,
                choicePoint,
                gbc,
                1,
                2
        );

        // -------------------------------------------------
        // DATE
        // -------------------------------------------------

        addFormLabel(
                form,
                "Date (YYYY-MM-DD)",
                gbc,
                2,
                0
        );

        txtDate = new TextField();

        addComponent(
                form,
                txtDate,
                gbc,
                3,
                0
        );

        // -------------------------------------------------
        // START TIME
        // -------------------------------------------------

        addFormLabel(
                form,
                "Start Time (HH:MM)",
                gbc,
                2,
                1
        );

        txtStart = new TextField();

        addComponent(
                form,
                txtStart,
                gbc,
                3,
                1
        );

        // -------------------------------------------------
        // END TIME
        // -------------------------------------------------

        addFormLabel(
                form,
                "End Time (HH:MM)",
                gbc,
                2,
                2
        );

        txtEnd = new TextField();

        addComponent(
                form,
                txtEnd,
                gbc,
                3,
                2
        );

        outer.add(
                form,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // BUTTONS
        // -------------------------------------------------

        Panel buttonPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        Button book =
                new Button("CHECK & BOOK");

        styleButton(
                book,
                GREEN
        );

        Button cancel =
                new Button("CANCEL SELECTED");

        styleButton(
                cancel,
                RED
        );

        Button refresh =
                new Button("REFRESH");

        styleButton(
                refresh,
                BLUE
        );

        buttonPanel.add(book);
        buttonPanel.add(cancel);
        buttonPanel.add(refresh);

        outer.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                outer,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // EVENTS
        // -------------------------------------------------

        choiceUser.addItemListener(
                e -> loadVehicles()
        );

        book.addActionListener(
                e -> bookReservation()
        );

        cancel.addActionListener(
                e -> cancelReservation()
        );

        refresh.addActionListener(
                e -> {
                    loadUsers();
                    loadPoints();
                    loadReservations();
                }
        );
    }

    // =====================================================
    // FORM LABEL
    // =====================================================

    private void addFormLabel(
            Panel panel,
            String text,
            GridBagConstraints gbc,
            int x,
            int y
    ) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0.2;

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

        panel.add(label, gbc);
    }

    // =====================================================
    // FORM COMPONENT
    // =====================================================

    private void addComponent(
            Panel panel,
            Component component,
            GridBagConstraints gbc,
            int x,
            int y
    ) {

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private void styleButton(
            Button button,
            Color color
    ) {

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);
    }

    // =====================================================
    // CREATE RESERVATION LIST
    // =====================================================

    private void createList() {

        Panel listCard =
                new Panel(new BorderLayout());

        listCard.setBackground(Color.WHITE);

        // -------------------------------------------------
        // TITLE
        // -------------------------------------------------

        Panel titlePanel =
                new Panel(new BorderLayout());

        titlePanel.setBackground(NAVY);

        Label title =
                new Label(
                        "RESERVATION HISTORY",
                        Label.LEFT
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        title.setForeground(Color.WHITE);

        titlePanel.add(
                title,
                BorderLayout.WEST
        );

        listCard.add(
                titlePanel,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------

        Label tableHeader =
                new Label(
                        String.format(
                                "%-5s %-16s %-15s %-12s %-16s %-12s %-8s %-8s %-10s",
                                "ID",
                                "USER",
                                "VEHICLE",
                                "POINT",
                                "STATION",
                                "DATE",
                                "START",
                                "END",
                                "STATUS"
                        )
                );

        tableHeader.setFont(
                new Font(
                        "Monospaced",
                        Font.BOLD,
                        12
                )
        );

        tableHeader.setForeground(NAVY);

        tableHeader.setBackground(
                new Color(232, 238, 245)
        );

        Panel headerPanel =
                new Panel(new BorderLayout());

        headerPanel.setBackground(
                new Color(232, 238, 245)
        );

        headerPanel.add(
                tableHeader,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // LIST
        // -------------------------------------------------

        reservationList =
                new java.awt.List(
                        12,
                        false
                );

        reservationList.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        12
                )
        );

        reservationList.setBackground(Color.WHITE);
        reservationList.setForeground(TEXT);

        Panel center =
                new Panel(new BorderLayout());

        center.setBackground(Color.WHITE);

        center.add(
                headerPanel,
                BorderLayout.NORTH
        );

        center.add(
                reservationList,
                BorderLayout.CENTER
        );

        listCard.add(
                center,
                BorderLayout.CENTER
        );

        // -------------------------------------------------
        // INFO BAR
        // -------------------------------------------------

        Label info =
                new Label(
                        " Select a reservation from the list to cancel it.",
                        Label.LEFT
                );

        info.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        info.setForeground(
                new Color(90, 100, 110)
        );

        info.setBackground(
                new Color(245, 247, 250)
        );

        listCard.add(
                info,
                BorderLayout.SOUTH
        );

        add(
                listCard,
                BorderLayout.SOUTH
        );
    }

    // =====================================================
    // LOAD USERS
    // =====================================================

    private void loadUsers() {

        // Safety check
        if (choiceUser == null) {
            return;
        }

        choiceUser.removeAll();

        List<String[]> users =
                dao.getUsers();

        for (String[] u : users) {

            choiceUser.add(
                    u[0] + " - " + u[1]
            );
        }

        // choiceVehicle is now guaranteed to
        // have been initialized by createForm()
        loadVehicles();
    }

    // =====================================================
    // GET USER ID
    // =====================================================

    private int getUserId() {

        if (choiceUser == null ||
                choiceUser.getItemCount() == 0) {

            return -1;
        }

        String selected =
                choiceUser.getSelectedItem();

        if (selected == null ||
                selected.trim().isEmpty()) {

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
    // LOAD VEHICLES
    // =====================================================

    private void loadVehicles() {

        if (choiceVehicle == null) {
            return;
        }

        choiceVehicle.removeAll();

        int userId =
                getUserId();

        if (userId == -1) {
            return;
        }

        List<String[]> vehicles =
                dao.getVehicles(userId);

        for (String[] v : vehicles) {

            choiceVehicle.add(
                    v[0] + " - " + v[1]
            );
        }
    }

    // =====================================================
    // LOAD AVAILABLE POINTS
    // =====================================================

    private void loadPoints() {

        if (choicePoint == null) {
            return;
        }

        choicePoint.removeAll();

        List<String[]> points =
                dao.getAvailablePoints();

        for (String[] p : points) {

            choicePoint.add(
                    p[0]
                            + " - "
                            + p[1]
                            + " - "
                            + p[2]
            );
        }
    }

    // =====================================================
    // GET VEHICLE ID
    // =====================================================

    private int getVehicleId() {

        if (choiceVehicle == null ||
                choiceVehicle.getItemCount() == 0) {

            return -1;
        }

        String selected =
                choiceVehicle.getSelectedItem();

        if (selected == null ||
                selected.trim().isEmpty()) {

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
    // GET CHARGING POINT ID
    // =====================================================

    private int getPointId() {

        if (choicePoint == null ||
                choicePoint.getItemCount() == 0) {

            return -1;
        }

        String selected =
                choicePoint.getSelectedItem();

        if (selected == null ||
                selected.trim().isEmpty()) {

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
    // BOOK RESERVATION
    // =====================================================

    private void bookReservation() {

        try {

            // ------------------------------------------------
            // VALIDATE USER
            // ------------------------------------------------

            if (choiceUser.getItemCount() == 0) {

                showMessage(
                        "No users available."
                );

                return;
            }

            // ------------------------------------------------
            // VALIDATE VEHICLE
            // ------------------------------------------------

            if (choiceVehicle.getItemCount() == 0) {

                showMessage(
                        "Selected user has no vehicle."
                );

                return;
            }

            // ------------------------------------------------
            // VALIDATE POINT
            // ------------------------------------------------

            if (choicePoint.getItemCount() == 0) {

                showMessage(
                        "No available charging points."
                );

                return;
            }

            // ------------------------------------------------
            // GET DATE / TIME
            // ------------------------------------------------

            String date =
                    txtDate.getText().trim();

            String start =
                    txtStart.getText().trim();

            String end =
                    txtEnd.getText().trim();

            if (date.isEmpty() ||
                    start.isEmpty() ||
                    end.isEmpty()) {

                showMessage(
                        "Enter date and time."
                );

                return;
            }

            // ------------------------------------------------
            // GET IDs
            // ------------------------------------------------

            int userId =
                    getUserId();

            int vehicleId =
                    getVehicleId();

            int pointId =
                    getPointId();

            if (userId == -1 ||
                    vehicleId == -1 ||
                    pointId == -1) {

                showMessage(
                        "Please select valid user, vehicle and charging point."
                );

                return;
            }

            // ------------------------------------------------
            // CHECK CONFLICT
            // ------------------------------------------------

            if (dao.hasConflict(
                    pointId,
                    date,
                    start,
                    end
            )) {

                showMessage(
                        "RESERVATION CONFLICT! " +
                                "The selected charging point " +
                                "is already booked."
                );

                return;
            }

            // ------------------------------------------------
            // ADD RESERVATION
            // ------------------------------------------------

            if (dao.addReservation(
                    userId,
                    vehicleId,
                    pointId,
                    date,
                    start,
                    end
            )) {

                showMessage(
                        "Reservation created successfully."
                );

                loadPoints();
                loadReservations();

                txtDate.setText("");
                txtStart.setText("");
                txtEnd.setText("");

            } else {

                showMessage(
                        "Unable to create reservation."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Invalid date/time format. " +
                            "Use YYYY-MM-DD and HH:MM."
            );
        }
    }

    // =====================================================
    // LOAD RESERVATIONS
    // =====================================================

    private void loadReservations() {

        if (reservationList == null) {
            return;
        }

        reservationList.removeAll();

        List<String[]> list =
                dao.getAllReservations();

        for (String[] r : list) {

            if (r.length >= 9) {

                String row =
                        String.format(
                                "%-5s %-16s %-15s %-12s %-16s %-12s %-8s %-8s %-10s",
                                r[0],
                                r[1],
                                r[2],
                                r[3],
                                r[4],
                                r[5],
                                r[6],
                                r[7],
                                r[8]
                        );

                reservationList.add(row);
            }
        }
    }

    // =====================================================
    // CANCEL RESERVATION
    // =====================================================

    private void cancelReservation() {

        int index =
                reservationList.getSelectedIndex();

        if (index == -1) {

            showMessage(
                    "Select a reservation first."
            );

            return;
        }

        String selected =
                reservationList.getItem(index);

        try {

            int id =
                    Integer.parseInt(
                            selected
                                    .trim()
                                    .split("\\s+")[0]
                    );

            if (dao.cancelReservation(id)) {

                showMessage(
                        "Reservation cancelled."
                );

                loadPoints();
                loadReservations();

            } else {

                showMessage(
                        "Unable to cancel reservation."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Unable to read the selected reservation."
            );
        }
    }

    // =====================================================
    // SHOW MESSAGE
    // =====================================================

    private void showMessage(String message) {

        Frame frame =
                getFrame();

        if (frame == null) {

            System.out.println(message);
            return;
        }

        Dialog d =
                new Dialog(
                        frame,
                        "CampusCharge",
                        true
                );

        d.setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        Label messageLabel =
                new Label(
                        message.replace(
                                "\n",
                                " "
                        ),
                        Label.CENTER
                );

        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        d.add(
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

        styleButton(
                ok,
                BLUE
        );

        ok.addActionListener(
                e -> d.dispose()
        );

        buttonPanel.add(ok);

        d.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        d.setSize(
                500,
                150
        );

        d.setLocationRelativeTo(frame);

        d.setVisible(true);
    }

    // =====================================================
    // GET PARENT FRAME
    // =====================================================

    private Frame getFrame() {

        Component c = this;

        while (
                c != null &&
                        !(c instanceof Frame)
        ) {

            c = c.getParent();
        }

        if (c instanceof Frame) {

            return (Frame) c;
        }

        return null;
    }
}