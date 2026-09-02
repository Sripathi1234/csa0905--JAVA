package gui;

import db.DBConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DashboardFrame extends Frame {

    // =====================================================
    // THEME
    // =====================================================

    private static final Color NAVY =
            new Color(30, 55, 75);

    private static final Color SIDEBAR =
            new Color(35, 48, 61);

    private static final Color SIDEBAR_HOVER =
            new Color(48, 70, 88);

    private static final Color ACTIVE =
            new Color(52, 92, 120);

    private static final Color PAGE_BG =
            new Color(244, 247, 249);

    private static final Color CARD_BG =
            Color.WHITE;

    private static final Color TEXT_DARK =
            new Color(35, 45, 55);

    private static final Color TEXT_MUTED =
            new Color(105, 115, 125);

    private static final Color ACCENT =
            new Color(41, 105, 135);


    // =====================================================
    // VARIABLES
    // =====================================================

    private final String loggedInUser;

    private CardLayout cardLayout;
    private Panel contentPanel;

    private NavButton activeButton;

    private StatCard totalStationsCard;
    private StatCard availablePointsCard;
    private StatCard chargingCard;
    private StatCard reservedCard;

    private Panel activityRows;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public DashboardFrame(String loggedInUser) {

        this.loggedInUser = loggedInUser;

        setTitle(
                "CampusCharge - Smart EV Charging Management System"
        );

        setSize(1250, 760);

        setMinimumSize(
                new Dimension(1100, 650)
        );

        setLayout(
                new BorderLayout()
        );

        setBackground(PAGE_BG);

        setLocationRelativeTo(null);

        createHeader();

        createMainArea();

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e
                    ) {

                        dispose();

                        System.exit(0);
                    }
                }
        );

        setVisible(true);
    }


    // =====================================================
    // HEADER
    // =====================================================

    private void createHeader() {

        Panel header =
                new Panel(
                        new BorderLayout()
                );

        header.setBackground(NAVY);

        header.setPreferredSize(
                new Dimension(100, 76)
        );


        // -----------------------------
        // Brand area
        // -----------------------------

        Panel brandPanel =
                new Panel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        brandPanel.setBackground(NAVY);


        Label brand =
                new Label(
                        "   CAMPUSCHARGE"
                );

        brand.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        brand.setForeground(
                Color.WHITE
        );


        Label tagline =
                new Label(
                        "   Smart EV Charging Management System"
                );

        tagline.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        tagline.setForeground(
                new Color(
                        205,
                        216,
                        225
                )
        );


        brandPanel.add(brand);
        brandPanel.add(tagline);


        // -----------------------------
        // User area
        // -----------------------------

        Panel userPanel =
                new Panel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        userPanel.setBackground(NAVY);


        Label welcome =
                new Label(
                        "Welcome, "
                                + loggedInUser
                                + "     ",
                        Label.RIGHT
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        welcome.setForeground(
                Color.WHITE
        );


        Label role =
                new Label(
                        "CampusCharge Administrator     ",
                        Label.RIGHT
                );

        role.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        role.setForeground(
                new Color(
                        205,
                        216,
                        225
                )
        );


        userPanel.add(welcome);
        userPanel.add(role);


        header.add(
                brandPanel,
                BorderLayout.WEST
        );

        header.add(
                userPanel,
                BorderLayout.EAST
        );


        add(
                header,
                BorderLayout.NORTH
        );
    }


    // =====================================================
    // MAIN AREA
    // =====================================================

    private void createMainArea() {

        Panel main =
                new Panel(
                        new BorderLayout()
                );

        main.add(
                createSidebar(),
                BorderLayout.WEST
        );

        main.add(
                createContent(),
                BorderLayout.CENTER
        );

        add(
                main,
                BorderLayout.CENTER
        );
    }


    // =====================================================
    // SIDEBAR
    // =====================================================

    private Panel createSidebar() {

        Panel outer =
                new Panel(
                        new BorderLayout()
                );

        outer.setBackground(SIDEBAR);

        outer.setPreferredSize(
                new Dimension(205, 100)
        );


        // -----------------------------
        // Sidebar heading
        // -----------------------------

        Panel top =
                new Panel(
                        new BorderLayout()
                );

        top.setBackground(SIDEBAR);

        top.setPreferredSize(
                new Dimension(205, 62)
        );


        Label menuLabel =
                new Label(
                        "    MAIN MENU"
                );

        menuLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        menuLabel.setForeground(
                new Color(
                        150,
                        165,
                        175
                )
        );


        top.add(
                menuLabel,
                BorderLayout.CENTER
        );


        outer.add(
                top,
                BorderLayout.NORTH
        );


        // -----------------------------
        // Navigation
        // -----------------------------

        Panel menu =
                new Panel(
                        new GridLayout(
                                9,
                                1,
                                0,
                                2
                        )
                );

        menu.setBackground(SIDEBAR);


        addNavigation(
                menu,
                "Dashboard",
                "DASHBOARD"
        );

        addNavigation(
                menu,
                "Users",
                "USERS"
        );

        addNavigation(
                menu,
                "Vehicles",
                "VEHICLES"
        );

        addNavigation(
                menu,
                "Stations",
                "STATIONS"
        );

        addNavigation(
                menu,
                "Charging Points",
                "CHARGING POINTS"
        );

        addNavigation(
                menu,
                "Reservations",
                "RESERVATIONS"
        );

        addNavigation(
                menu,
                "Charging",
                "CHARGING"
        );

        addNavigation(
                menu,
                "Payments",
                "PAYMENTS"
        );

        addNavigation(
                menu,
                "Reports",
                "REPORTS"
        );


        outer.add(
                menu,
                BorderLayout.CENTER
        );


        // -----------------------------
        // Logout
        // -----------------------------

        Panel bottom =
                new Panel(
                        new BorderLayout()
                );

        bottom.setBackground(SIDEBAR);

        bottom.setPreferredSize(
                new Dimension(
                        205,
                        72
                )
        );


        NavButton logout =
                new NavButton(
                        "LOGOUT",
                        true
                );


        logout.addActionListener(
                e -> logout()
        );


        bottom.add(
                logout,
                BorderLayout.CENTER
        );


        outer.add(
                bottom,
                BorderLayout.SOUTH
        );


        return outer;
    }


    // =====================================================
    // ADD NAVIGATION BUTTON
    // =====================================================

    private void addNavigation(
            Panel menu,
            String cardName,
            String text
    ) {

        NavButton button =
                new NavButton(
                        text,
                        false
                );


        button.addActionListener(
                e -> {

                    showCard(
                            cardName
                    );

                    setActiveButton(
                            button
                    );
                }
        );


        menu.add(button);


        if (
                cardName.equals(
                        "Dashboard"
                )
        ) {

            activeButton = button;

            button.setActive(true);
        }
    }


    // =====================================================
    // SET ACTIVE BUTTON
    // =====================================================

    private void setActiveButton(
            NavButton button
    ) {

        if (
                activeButton != null
        ) {

            activeButton.setActive(
                    false
            );
        }


        activeButton = button;

        activeButton.setActive(
                true
        );
    }


    // =====================================================
    // CONTENT
    // =====================================================

    private Panel createContent() {

        cardLayout =
                new CardLayout();


        contentPanel =
                new Panel(
                        cardLayout
                );


        contentPanel.setBackground(
                PAGE_BG
        );


        contentPanel.add(
                createDashboardPanel(),
                "Dashboard"
        );


        contentPanel.add(
                new UserPanel(),
                "Users"
        );


        contentPanel.add(
                new VehiclePanel(),
                "Vehicles"
        );


        contentPanel.add(
                new StationPanel(),
                "Stations"
        );


        contentPanel.add(
                new ChargingPointPanel(),
                "Charging Points"
        );


        contentPanel.add(
                new ReservationPanel(),
                "Reservations"
        );


        contentPanel.add(
                new ChargingPanel(),
                "Charging"
        );


        contentPanel.add(
                new PaymentPanel(),
                "Payments"
        );


        contentPanel.add(
                new ReportsPanel(),
                "Reports"
        );


        return contentPanel;
    }


    // =====================================================
    // DASHBOARD PAGE
    // =====================================================

    private Panel createDashboardPanel() {

        Panel page =
                new Panel(
                        new BorderLayout(
                                0,
                                18
                        )
                );


        page.setBackground(
                PAGE_BG
        );


        // =================================================
        // PAGE HEADER
        // =================================================

        Panel pageHeader =
                new Panel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        pageHeader.setBackground(
                PAGE_BG
        );

        pageHeader.setPreferredSize(
                new Dimension(
                        100,
                        100
                )
        );


        Label title =
                new Label(
                        "     Dashboard"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                TEXT_DARK
        );


        Label subtitle =
                new Label(
                        "     Overview of campus EV charging operations"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                TEXT_MUTED
        );


        pageHeader.add(title);
        pageHeader.add(subtitle);


        page.add(
                pageHeader,
                BorderLayout.NORTH
        );


        // =================================================
        // CENTER
        // =================================================

        Panel center =
                new Panel(
                        new BorderLayout(
                                20,
                                25
                        )
                );

        center.setBackground(
                PAGE_BG
        );


        // -----------------------------
        // Statistics cards
        // -----------------------------

        Panel cards =
                new Panel(
                        new GridLayout(
                                1,
                                4,
                                18,
                                0
                        )
                );

        cards.setBackground(
                PAGE_BG
        );

        cards.setPreferredSize(
                new Dimension(
                        100,
                        145
                )
        );


        totalStationsCard =
                new StatCard(
                        "TOTAL STATIONS",
                        "Campus locations"
                );


        availablePointsCard =
                new StatCard(
                        "AVAILABLE POINTS",
                        "Ready for charging"
                );


        chargingCard =
                new StatCard(
                        "ACTIVE CHARGING",
                        "Currently occupied"
                );


        reservedCard =
                new StatCard(
                        "RESERVED",
                        "Booked charging points"
                );


        cards.add(
                totalStationsCard
        );

        cards.add(
                availablePointsCard
        );

        cards.add(
                chargingCard
        );

        cards.add(
                reservedCard
        );


        center.add(
                cards,
                BorderLayout.NORTH
        );


        // =================================================
        // ACTIVITY SECTION
        // =================================================

        Panel activityCard =
                new Panel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        activityCard.setBackground(
                CARD_BG
        );


        Panel activityHeader =
                new Panel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        activityHeader.setBackground(
                CARD_BG
        );

        activityHeader.setPreferredSize(
                new Dimension(
                        100,
                        68
                )
        );


        Label activityTitle =
                new Label(
                        "   Recent Charging Activity"
                );

        activityTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        activityTitle.setForeground(
                TEXT_DARK
        );


        Label activitySubtitle =
                new Label(
                        "   Latest charging sessions across the campus"
                );

        activitySubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        activitySubtitle.setForeground(
                TEXT_MUTED
        );


        activityHeader.add(
                activityTitle
        );

        activityHeader.add(
                activitySubtitle
        );


        activityCard.add(
                activityHeader,
                BorderLayout.NORTH
        );


        Panel table =
                new Panel(
                        new BorderLayout()
                );

        table.setBackground(
                CARD_BG
        );


        Panel tableHeader =
                new Panel(
                        new GridLayout(
                                1,
                                5
                        )
                );

        tableHeader.setBackground(
                new Color(
                        235,
                        241,
                        245
                )
        );

        tableHeader.setPreferredSize(
                new Dimension(
                        100,
                        38
                )
        );


        tableHeader.add(
                tableHeading(
                        "VEHICLE"
                )
        );

        tableHeader.add(
                tableHeading(
                        "STATION"
                )
        );

        tableHeader.add(
                tableHeading(
                        "POINT"
                )
        );

        tableHeader.add(
                tableHeading(
                        "ENERGY"
                )
        );

        tableHeader.add(
                tableHeading(
                        "STATUS"
                )
        );


        table.add(
                tableHeader,
                BorderLayout.NORTH
        );


        activityRows =
                new Panel(
                        new GridLayout(
                                5,
                                1,
                                0,
                                1
                        )
                );

        activityRows.setBackground(
                new Color(
                        225,
                        230,
                        234
                )
        );


        table.add(
                activityRows,
                BorderLayout.CENTER
        );


        activityCard.add(
                table,
                BorderLayout.CENTER
        );


        center.add(
                activityCard,
                BorderLayout.CENTER
        );


        // Padding around center
        Panel wrapper =
                new Panel(
                        new BorderLayout()
                );

        wrapper.setBackground(
                PAGE_BG
        );


        Panel leftSpace =
                new Panel();

        leftSpace.setBackground(
                PAGE_BG
        );

        leftSpace.setPreferredSize(
                new Dimension(
                        35,
                        10
                )
        );


        Panel rightSpace =
                new Panel();

        rightSpace.setBackground(
                PAGE_BG
        );

        rightSpace.setPreferredSize(
                new Dimension(
                        35,
                        10
                )
        );


        Panel bottomSpace =
                new Panel();

        bottomSpace.setBackground(
                PAGE_BG
        );

        bottomSpace.setPreferredSize(
                new Dimension(
                        10,
                        35
                )
        );


        wrapper.add(
                leftSpace,
                BorderLayout.WEST
        );

        wrapper.add(
                rightSpace,
                BorderLayout.EAST
        );

        wrapper.add(
                bottomSpace,
                BorderLayout.SOUTH
        );

        wrapper.add(
                center,
                BorderLayout.CENTER
        );


        page.add(
                wrapper,
                BorderLayout.CENTER
        );


        updateStatistics();

        refreshActivity();


        return page;
    }


    // =====================================================
    // TABLE HEADING
    // =====================================================

    private Label tableHeading(
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
                new Color(
                        80,
                        95,
                        105
                )
        );

        return label;
    }


    // =====================================================
    // REFRESH ACTIVITY
    // =====================================================

    private void refreshActivity() {

        if (
                activityRows == null
        ) {

            return;
        }


        activityRows.removeAll();


        String sql =
                "SELECT " +
                        "v.vehicle_number, " +
                        "cs.station_name, " +
                        "cp.point_name, " +
                        "s.energy_consumed, " +
                        "s.session_status " +

                        "FROM charging_sessions s " +

                        "JOIN vehicles v " +
                        "ON s.vehicle_id = v.vehicle_id " +

                        "JOIN charging_points cp " +
                        "ON s.point_id = cp.point_id " +

                        "JOIN charging_stations cs " +
                        "ON cp.station_id = cs.station_id " +

                        "ORDER BY s.session_id DESC " +

                        "LIMIT 5";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            int count = 0;


            while (
                    rs.next()
            ) {

                Panel row =
                        new Panel(
                                new GridLayout(
                                        1,
                                        5
                                )
                        );


                row.setBackground(
                        Color.WHITE
                );


                row.add(
                        rowLabel(
                                rs.getString(
                                        "vehicle_number"
                                )
                        )
                );


                row.add(
                        rowLabel(
                                rs.getString(
                                        "station_name"
                                )
                        )
                );


                row.add(
                        rowLabel(
                                rs.getString(
                                        "point_name"
                                )
                        )
                );


                row.add(
                        rowLabel(
                                String.format(
                                        "%.2f kWh",
                                        rs.getDouble(
                                                "energy_consumed"
                                        )
                                )
                        )
                );


                row.add(
                        statusLabel(
                                rs.getString(
                                        "session_status"
                                )
                        )
                );


                activityRows.add(
                        row
                );


                count++;
            }


            while (
                    count < 5
            ) {

                Panel empty =
                        new Panel(
                                new GridLayout(
                                        1,
                                        5
                                )
                        );


                empty.setBackground(
                        Color.WHITE
                );


                if (
                        count == 0
                ) {

                    empty.add(
                            rowLabel(
                                    "No recent charging activity"
                            )
                    );

                } else {

                    empty.add(
                            rowLabel("")
                    );
                }


                empty.add(
                        rowLabel("")
                );

                empty.add(
                        rowLabel("")
                );

                empty.add(
                        rowLabel("")
                );

                empty.add(
                        rowLabel("")
                );


                activityRows.add(
                        empty
                );


                count++;
            }


        } catch (
                SQLException e
        ) {

            Panel row =
                    new Panel(
                            new GridLayout(
                                    1,
                                    1
                            )
                    );


            row.setBackground(
                    Color.WHITE
            );


            row.add(
                    rowLabel(
                            "Unable to load charging activity."
                    )
            );


            activityRows.add(
                    row
            );
        }


        activityRows.validate();

        activityRows.repaint();
    }


    // =====================================================
    // ROW LABEL
    // =====================================================

    private Label rowLabel(
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
                        Font.PLAIN,
                        11
                )
        );

        label.setForeground(
                TEXT_DARK
        );

        return label;
    }


    // =====================================================
    // STATUS LABEL
    // =====================================================

    private Label statusLabel(
            String status
    ) {

        Label label =
                new Label(
                        status,
                        Label.CENTER
                );


        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );


        if (
                "COMPLETED".equals(
                        status
                )
        ) {

            label.setForeground(
                    new Color(
                            35,
                            125,
                            75
                    )
            );

        } else if (
                "CHARGING".equals(
                        status
                )
        ) {

            label.setForeground(
                    new Color(
                            30,
                            105,
                            160
                    )
            );

        } else {

            label.setForeground(
                    new Color(
                            160,
                            100,
                            40
                    )
            );
        }


        return label;
    }


    // =====================================================
    // UPDATE STATISTICS
    // =====================================================

    private void updateStatistics() {

        try (
                Connection con =
                        DBConnection.getConnection();

                Statement statement =
                        con.createStatement()
        ) {

            int stations =
                    getCount(
                            statement,
                            "SELECT COUNT(*) " +
                                    "FROM charging_stations"
                    );


            int available =
                    getCount(
                            statement,
                            "SELECT COUNT(*) " +
                                    "FROM charging_points " +
                                    "WHERE status='AVAILABLE'"
                    );


            int charging =
                    getCount(
                            statement,
                            "SELECT COUNT(*) " +
                                    "FROM charging_points " +
                                    "WHERE status='OCCUPIED'"
                    );


            int reserved =
                    getCount(
                            statement,
                            "SELECT COUNT(*) " +
                                    "FROM charging_points " +
                                    "WHERE status='RESERVED'"
                    );


            totalStationsCard.setValue(
                    String.valueOf(
                            stations
                    )
            );


            availablePointsCard.setValue(
                    String.valueOf(
                            available
                    )
            );


            chargingCard.setValue(
                    String.valueOf(
                            charging
                    )
            );


            reservedCard.setValue(
                    String.valueOf(
                            reserved
                    )
            );


        } catch (
                SQLException e
        ) {

            totalStationsCard.setValue("-");
            availablePointsCard.setValue("-");
            chargingCard.setValue("-");
            reservedCard.setValue("-");

            System.out.println(
                    "Dashboard Error: "
                            + e.getMessage()
            );
        }
    }


    // =====================================================
    // GET COUNT
    // =====================================================

    private int getCount(
            Statement statement,
            String sql
    ) throws SQLException {

        try (
                ResultSet rs =
                        statement.executeQuery(
                                sql
                        )
        ) {

            if (
                    rs.next()
            ) {

                return rs.getInt(1);
            }
        }


        return 0;
    }


    // =====================================================
    // SHOW CARD
    // =====================================================

    private void showCard(
            String cardName
    ) {

        cardLayout.show(
                contentPanel,
                cardName
        );


        if (
                "Dashboard".equals(
                        cardName
                )
        ) {

            updateStatistics();

            refreshActivity();
        }
    }


    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        dispose();

        new LoginFrame();
    }


    // =====================================================
    // CUSTOM NAV BUTTON
    // =====================================================

    private class NavButton
            extends Canvas {

        private final String text;

        private boolean active = false;

        private boolean hover = false;

        private final boolean logout;

        private ActionListener listener;


        public NavButton(
                String text,
                boolean logout
        ) {

            this.text = text;

            this.logout = logout;


            setPreferredSize(
                    new Dimension(
                            205,
                            52
                    )
            );


            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );


            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            hover = true;

                            repaint();
                        }


                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            hover = false;

                            repaint();
                        }


                        @Override
                        public void mouseClicked(
                                MouseEvent e
                        ) {

                            if (
                                    listener != null
                            ) {

                                listener.actionPerformed(
                                        new ActionEvent(
                                                NavButton.this,
                                                ActionEvent
                                                        .ACTION_PERFORMED,
                                                text
                                        )
                                );
                            }
                        }
                    }
            );
        }


        public void addActionListener(
                ActionListener listener
        ) {

            this.listener = listener;
        }


        public void setActive(
                boolean active
        ) {

            this.active = active;

            repaint();
        }


        @Override
        public void paint(
                Graphics g
        ) {

            int width =
                    getWidth();

            int height =
                    getHeight();


            Color background;


            if (
                    active
            ) {

                background =
                        ACTIVE;

            } else if (
                    hover
            ) {

                background =
                        SIDEBAR_HOVER;

            } else {

                background =
                        SIDEBAR;
            }


            g.setColor(
                    background
            );

            g.fillRect(
                    0,
                    0,
                    width,
                    height
            );


            // Active indicator
            if (
                    active
                            && !logout
            ) {

                g.setColor(
                        new Color(
                                80,
                                170,
                                205
                        )
                );

                g.fillRect(
                        0,
                        0,
                        4,
                        height
                );
            }


            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );


            if (
                    logout
            ) {

                g.setColor(
                        new Color(
                                225,
                                165,
                                165
                        )
                );

            } else {

                g.setColor(
                        Color.WHITE
                );
            }


            g.drawString(
                    text,
                    25,
                    (height / 2) + 5
            );
        }
    }


    // =====================================================
    // CUSTOM STAT CARD
    // =====================================================

    private class StatCard
            extends Canvas {

        private final String title;

        private final String subtitle;

        private String value = "0";


        public StatCard(
                String title,
                String subtitle
        ) {

            this.title = title;

            this.subtitle = subtitle;


            setPreferredSize(
                    new Dimension(
                            180,
                            130
                    )
            );
        }


        public void setValue(
                String value
        ) {

            this.value = value;

            repaint();
        }


        @Override
        public void paint(
                Graphics g
        ) {

            int width =
                    getWidth();

            int height =
                    getHeight();


            // Shadow
            g.setColor(
                    new Color(
                            220,
                            225,
                            229
                    )
            );

            g.fillRoundRect(
                    4,
                    6,
                    width - 8,
                    height - 10,
                    18,
                    18
            );


            // Card
            g.setColor(
                    CARD_BG
            );

            g.fillRoundRect(
                    0,
                    0,
                    width - 8,
                    height - 10,
                    18,
                    18
            );


            // Accent bar
            g.setColor(
                    ACCENT
            );

            g.fillRoundRect(
                    0,
                    0,
                    6,
                    height - 10,
                    8,
                    8
            );


            // Title
            g.setColor(
                    TEXT_MUTED
            );

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            g.drawString(
                    title,
                    22,
                    30
            );


            // Value
            g.setColor(
                    TEXT_DARK
            );

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            32
                    )
            );

            g.drawString(
                    value,
                    22,
                    72
            );


            // Subtitle
            g.setColor(
                    TEXT_MUTED
            );

            g.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            10
                    )
            );

            g.drawString(
                    subtitle,
                    22,
                    101
            );
        }
    }
}