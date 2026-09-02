package gui;

import dao.StationDAO;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StationPanel extends Panel {

    // =====================================================
    // THEME
    // =====================================================

    private static final Color NAVY =
            new Color(30, 55, 75);

    private static final Color BLUE =
            new Color(52, 92, 120);

    private static final Color LIGHT_BG =
            new Color(244, 247, 249);

    private static final Color CARD =
            Color.WHITE;

    private static final Color TEXT =
            new Color(35, 45, 55);

    private static final Color MUTED =
            new Color(105, 115, 125);


    // =====================================================
    // FORM FIELDS
    // =====================================================

    private TextField txtName;
    private TextField txtLocation;
    private TextField txtPoints;


    // =====================================================
    // STATION LIST
    // =====================================================

    private java.awt.List stationList;


    // =====================================================
    // BUTTONS
    // =====================================================

    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnClear;


    // =====================================================
    // VARIABLES
    // =====================================================

    private int selectedId = -1;

    private StationDAO dao;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public StationPanel() {

        dao = new StationDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BG
        );

        createPage();

        loadStations();
    }


    // =====================================================
    // CREATE PAGE
    // =====================================================

    private void createPage() {

        add(
                createPageHeader(),
                BorderLayout.NORTH
        );

        add(
                createMainContent(),
                BorderLayout.CENTER
        );
    }


    // =====================================================
    // PAGE HEADER
    // =====================================================

    private Panel createPageHeader() {

        Panel header =
                new Panel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        header.setBackground(
                LIGHT_BG
        );

        header.setPreferredSize(
                new Dimension(
                        100,
                        92
                )
        );


        Label title =
                new Label(
                        "   STATION MANAGEMENT"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        title.setForeground(
                TEXT
        );


        Label subtitle =
                new Label(
                        "   Manage campus EV charging stations and their locations"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                MUTED
        );


        header.add(title);
        header.add(subtitle);


        return header;
    }


    // =====================================================
    // MAIN CONTENT
    // =====================================================

    private Panel createMainContent() {

        Panel main =
                new Panel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        main.setBackground(
                LIGHT_BG
        );


        // =================================================
        // STATION INFORMATION CARD
        // =================================================

        Panel formCard =
                new Panel(
                        new BorderLayout()
                );

        formCard.setBackground(
                CARD
        );


        // -----------------------------------------------
        // CARD HEADER
        // -----------------------------------------------

        Panel formHeader =
                new Panel(
                        new BorderLayout()
                );

        formHeader.setBackground(
                NAVY
        );

        formHeader.setPreferredSize(
                new Dimension(
                        100,
                        42
                )
        );


        Label formTitle =
                new Label(
                        "   STATION INFORMATION"
                );

        formTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        formTitle.setForeground(
                Color.WHITE
        );


        formHeader.add(
                formTitle,
                BorderLayout.CENTER
        );


        formCard.add(
                formHeader,
                BorderLayout.NORTH
        );


        // =================================================
        // FORM AREA
        // =================================================

        Panel formArea =
                new Panel(
                        new GridBagLayout()
                );

        formArea.setBackground(
                CARD
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        18,
                        8,
                        18
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // Station Name

        txtName =
                new TextField();


        addFormRow(
                formArea,
                gbc,
                0,
                "Station Name",
                txtName
        );


        // Location

        txtLocation =
                new TextField();


        addFormRow(
                formArea,
                gbc,
                1,
                "Location",
                txtLocation
        );


        // Total Points

        txtPoints =
                new TextField();


        addFormRow(
                formArea,
                gbc,
                2,
                "Total Charging Points",
                txtPoints
        );


        formCard.add(
                formArea,
                BorderLayout.CENTER
        );


        // =================================================
        // ACTION BUTTONS
        // =================================================

        Panel buttons =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                12
                        )
                );

        buttons.setBackground(
                CARD
        );


        btnAdd =
                createButton(
                        "ADD",
                        BLUE
                );


        btnUpdate =
                createButton(
                        "UPDATE",
                        NAVY
                );


        btnDelete =
                createButton(
                        "DELETE",
                        new Color(
                                145,
                                65,
                                65
                        )
                );


        btnClear =
                createButton(
                        "CLEAR",
                        new Color(
                                100,
                                110,
                                120
                        )
                );


        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);


        formCard.add(
                buttons,
                BorderLayout.SOUTH
        );


        main.add(
                formCard,
                BorderLayout.NORTH
        );


        // =================================================
        // STATION LIST
        // =================================================

        main.add(
                createStationList(),
                BorderLayout.CENTER
        );


        // =================================================
        // OUTER PADDING
        // =================================================

        Panel wrapper =
                new Panel(
                        new BorderLayout(
                                22,
                                0
                        )
                );

        wrapper.setBackground(
                LIGHT_BG
        );


        Panel left =
                new Panel();

        left.setBackground(
                LIGHT_BG
        );

        left.setPreferredSize(
                new Dimension(
                        22,
                        10
                )
        );


        Panel right =
                new Panel();

        right.setBackground(
                LIGHT_BG
        );

        right.setPreferredSize(
                new Dimension(
                        22,
                        10
                )
        );


        wrapper.add(
                left,
                BorderLayout.WEST
        );

        wrapper.add(
                right,
                BorderLayout.EAST
        );

        wrapper.add(
                main,
                BorderLayout.CENTER
        );


        return wrapper;
    }


    // =====================================================
    // FORM ROW
    // =====================================================

    private void addFormRow(
            Panel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component field
    ) {

        gbc.gridx = 0;
        gbc.gridy = row;

        gbc.weightx = 0.25;


        Label label =
                new Label(
                        labelText
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                TEXT
        );


        panel.add(
                label,
                gbc
        );


        gbc.gridx = 1;

        gbc.weightx = 0.75;


        if (
                field instanceof TextField
        ) {

            TextField textField =
                    (TextField) field;

            textField.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            textField.setBackground(
                    Color.WHITE
            );

            textField.setForeground(
                    TEXT
            );
        }


        panel.add(
                field,
                gbc
        );
    }


    // =====================================================
    // STATION LIST
    // =====================================================

    private Panel createStationList() {

        Panel card =
                new Panel(
                        new BorderLayout()
                );

        card.setBackground(
                CARD
        );


        // -----------------------------------------------
        // HEADER
        // -----------------------------------------------

        Panel header =
                new Panel(
                        new BorderLayout()
                );

        header.setBackground(
                NAVY
        );

        header.setPreferredSize(
                new Dimension(
                        100,
                        42
                )
        );


        Label title =
                new Label(
                        "   REGISTERED STATIONS"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        title.setForeground(
                Color.WHITE
        );


        header.add(
                title,
                BorderLayout.CENTER
        );


        card.add(
                header,
                BorderLayout.NORTH
        );


        // -----------------------------------------------
        // COLUMN HEADER
        // -----------------------------------------------

        Panel columnHeader =
                new Panel(
                        new BorderLayout()
                );

        columnHeader.setBackground(
                new Color(
                        250,
                        251,
                        252
                )
        );


        columnHeader.setPreferredSize(
                new Dimension(
                        100,
                        35
                )
        );


        Label columns =
                new Label(
                        "ID        |        Station Name        |        Location        |        Total Points"
                );

        columns.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        columns.setForeground(
                TEXT
        );


        columnHeader.add(
                columns,
                BorderLayout.CENTER
        );


        card.add(
                columnHeader,
                BorderLayout.CENTER
        );


        // -----------------------------------------------
        // LIST
        // -----------------------------------------------

        stationList =
                new java.awt.List(
                        10,
                        false
                );


        stationList.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        12
                )
        );


        stationList.setBackground(
                Color.WHITE
        );

        stationList.setForeground(
                TEXT
        );


        card.add(
                stationList,
                BorderLayout.SOUTH
        );


        stationList.addItemListener(
                new ItemListener() {

                    @Override
                    public void itemStateChanged(
                            ItemEvent e
                    ) {

                        selectStation();
                    }
                }
        );


        return card;
    }


    // =====================================================
    // BUTTON CREATION
    // =====================================================

    private Button createButton(
            String text,
            Color color
    ) {

        Button button =
                new Button(
                        text
                );


        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );


        button.setBackground(
                color
        );


        button.setForeground(
                Color.WHITE
        );


        button.setPreferredSize(
                new Dimension(
                        95,
                        34
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =====================================================
    // ADD STATION
    // =====================================================

    private void addStation() {

        if (!validateFields()) {

            return;
        }


        try {

            int points =
                    Integer.parseInt(
                            txtPoints
                                    .getText()
                                    .trim()
                    );


            if (points <= 0) {

                showMessage(
                        "Total charging points must be greater than zero."
                );

                return;
            }


            if (
                    dao.addStation(
                            txtName
                                    .getText()
                                    .trim(),
                            txtLocation
                                    .getText()
                                    .trim(),
                            points
                    )
            ) {

                showMessage(
                        "Station added successfully."
                );


                clearForm();

                loadStations();

            } else {

                showMessage(
                        "Unable to add station."
                );
            }


        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Total points must be a whole number."
            );
        }
    }


    // =====================================================
    // UPDATE STATION
    // =====================================================

    private void updateStation() {

        if (selectedId == -1) {

            showMessage(
                    "Select a station first."
            );

            return;
        }


        if (!validateFields()) {

            return;
        }


        try {

            int points =
                    Integer.parseInt(
                            txtPoints
                                    .getText()
                                    .trim()
                    );


            if (points <= 0) {

                showMessage(
                        "Total charging points must be greater than zero."
                );

                return;
            }


            if (
                    dao.updateStation(
                            selectedId,
                            txtName
                                    .getText()
                                    .trim(),
                            txtLocation
                                    .getText()
                                    .trim(),
                            points
                    )
            ) {

                showMessage(
                        "Station updated successfully."
                );


                clearForm();

                loadStations();

            } else {

                showMessage(
                        "Unable to update station."
                );
            }


        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Total points must be a whole number."
            );
        }
    }


    // =====================================================
    // DELETE STATION
    // =====================================================

    private void deleteStation() {

        if (selectedId == -1) {

            showMessage(
                    "Select a station first."
            );

            return;
        }


        if (
                dao.deleteStation(
                        selectedId
                )
        ) {

            showMessage(
                    "Station deleted successfully."
            );


            clearForm();

            loadStations();

        } else {

            showMessage(
                    "Station cannot be deleted because it has charging points."
            );
        }
    }


    // =====================================================
    // LOAD STATIONS
    // =====================================================

    private void loadStations() {

        if (stationList == null) {

            return;
        }


        stationList.removeAll();


        List<String[]> stations =
                dao.getAllStations();


        for (
                String[] s :
                stations
        ) {

            stationList.add(
                    s[0]
                            + "        |        "
                            + s[1]
                            + "        |        "
                            + s[2]
                            + "        |        "
                            + s[3]
            );
        }
    }


    // =====================================================
    // SELECT STATION
    // =====================================================

    private void selectStation() {

        int index =
                stationList
                        .getSelectedIndex();


        if (index == -1) {

            return;
        }


        String[] p =
                stationList
                        .getItem(index)
                        .split(
                                "\\s*\\|\\s*"
                        );


        if (p.length < 4) {

            return;
        }


        try {

            selectedId =
                    Integer.parseInt(
                            p[0].trim()
                    );

        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Invalid station ID."
            );

            return;
        }


        txtName.setText(
                p[1].trim()
        );


        txtLocation.setText(
                p[2].trim()
        );


        txtPoints.setText(
                p[3].trim()
        );
    }


    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateFields() {

        if (
                txtName
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter the station name."
            );

            return false;
        }


        if (
                txtLocation
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter the station location."
            );

            return false;
        }


        if (
                txtPoints
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter the total number of charging points."
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

        txtLocation.setText("");

        txtPoints.setText("");


        selectedId = -1;


        int index =
                stationList
                        .getSelectedIndex();


        if (index >= 0) {

            stationList.deselect(
                    index
            );
        }
    }


    // =====================================================
    // MESSAGE
    // =====================================================

    private void showMessage(
            String message
    ) {

        Frame parent =
                getFrame();


        Dialog dialog =
                new Dialog(
                        parent,
                        "CampusCharge",
                        true
                );


        dialog.setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );


        dialog.setBackground(
                Color.WHITE
        );


        Panel messagePanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                25
                        )
                );


        messagePanel.setBackground(
                Color.WHITE
        );


        Label label =
                new Label(
                        message
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


        messagePanel.add(
                label
        );


        dialog.add(
                messagePanel,
                BorderLayout.CENTER
        );


        Panel buttonPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );


        buttonPanel.setBackground(
                Color.WHITE
        );


        Button ok =
                createButton(
                        "OK",
                        NAVY
                );


        ok.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        dialog.dispose();
                    }
                }
        );


        buttonPanel.add(
                ok
        );


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
    // GET FRAME
    // =====================================================

    private Frame getFrame() {

        Component c =
                this;


        while (
                c != null
                        && !(c instanceof Frame)
        ) {

            c =
                    c.getParent();
        }


        return (Frame) c;
    }
}