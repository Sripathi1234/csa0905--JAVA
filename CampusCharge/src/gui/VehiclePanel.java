package gui;

import dao.VehicleDAO;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VehiclePanel extends Panel {

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

    private TextField txtNumber;
    private TextField txtModel;
    private TextField txtBattery;

    private Choice choiceOwner;
    private Choice choiceType;


    // =====================================================
    // VEHICLE LIST
    // =====================================================

    private java.awt.List vehicleList;


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

    private VehicleDAO dao;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public VehiclePanel() {

        dao = new VehicleDAO();

        setLayout(
                new BorderLayout()
        );

        setBackground(
                LIGHT_BG
        );

        createPage();

        loadVehicles();
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
                        "   VEHICLE MANAGEMENT"
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
                        "   Register and manage electric vehicles on campus"
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


        header.add(
                title
        );

        header.add(
                subtitle
        );


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
        // VEHICLE INFORMATION CARD
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
                        "   VEHICLE INFORMATION"
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


        // -----------------------------------------------
        // FORM
        // -----------------------------------------------

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
                        7,
                        18,
                        7,
                        18
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // OWNER

        choiceOwner =
                new Choice();

        choiceOwner.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        choiceOwner.setBackground(
                Color.WHITE
        );

        choiceOwner.setForeground(
                TEXT
        );

        loadOwners();


        addFormRow(
                formArea,
                gbc,
                0,
                "Vehicle Owner",
                choiceOwner
        );


        // VEHICLE NUMBER

        txtNumber =
                new TextField();

        addFormRow(
                formArea,
                gbc,
                1,
                "Vehicle Number",
                txtNumber
        );


        // VEHICLE MODEL

        txtModel =
                new TextField();

        addFormRow(
                formArea,
                gbc,
                2,
                "Vehicle Model",
                txtModel
        );


        // VEHICLE TYPE

        choiceType =
                new Choice();

        choiceType.add(
                "Electric Car"
        );

        choiceType.add(
                "Electric Bike"
        );

        choiceType.add(
                "Electric Scooter"
        );


        addFormRow(
                formArea,
                gbc,
                3,
                "Vehicle Type",
                choiceType
        );


        // BATTERY

        txtBattery =
                new TextField();

        addFormRow(
                formArea,
                gbc,
                4,
                "Battery Capacity (kWh)",
                txtBattery
        );


        formCard.add(
                formArea,
                BorderLayout.CENTER
        );


        // -----------------------------------------------
        // BUTTONS
        // -----------------------------------------------

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


        buttons.add(
                btnAdd
        );

        buttons.add(
                btnUpdate
        );

        buttons.add(
                btnDelete
        );

        buttons.add(
                btnClear
        );


        formCard.add(
                buttons,
                BorderLayout.SOUTH
        );


        main.add(
                formCard,
                BorderLayout.NORTH
        );


        // =================================================
        // VEHICLE LIST
        // =================================================

        main.add(
                createVehicleList(),
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
    // VEHICLE LIST
    // =====================================================

    private Panel createVehicleList() {

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
                        "   REGISTERED VEHICLES"
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


        Label columns =
                new Label(
                        "ID        |        Vehicle Number        |        Model        |        Type        |        Battery        |        Owner"
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

        vehicleList =
                new java.awt.List(
                        10,
                        false
                );


        vehicleList.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        12
                )
        );


        vehicleList.setBackground(
                Color.WHITE
        );

        vehicleList.setForeground(
                TEXT
        );


        card.add(
                vehicleList,
                BorderLayout.SOUTH
        );


        vehicleList.addItemListener(
                new ItemListener() {

                    @Override
                    public void itemStateChanged(
                            ItemEvent e
                    ) {

                        selectVehicle();
                    }
                }
        );


        return card;
    }


    // =====================================================
    // BUTTON
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
    // LOAD OWNERS
    // =====================================================

    private void loadOwners() {

        List<String[]> users =
                dao.getUsers();


        for (
                String[] user :
                users
        ) {

            choiceOwner.add(
                    user[0]
                            + " - "
                            + user[1]
            );
        }
    }


    // =====================================================
    // GET OWNER ID
    // =====================================================

    private int getOwnerId() {

        String selected =
                choiceOwner.getSelectedItem();


        return Integer.parseInt(
                selected
                        .split(" - ")[0]
        );
    }


    // =====================================================
    // ADD VEHICLE
    // =====================================================

    private void addVehicle() {

        if (!validateFields()) {

            return;
        }


        try {

            double battery =
                    Double.parseDouble(
                            txtBattery
                                    .getText()
                                    .trim()
                    );


            if (
                    dao.addVehicle(
                            getOwnerId(),
                            txtNumber
                                    .getText()
                                    .trim(),
                            txtModel
                                    .getText()
                                    .trim(),
                            choiceType
                                    .getSelectedItem(),
                            battery
                    )
            ) {

                showMessage(
                        "Vehicle added successfully."
                );


                clearForm();

                loadVehicles();

            } else {

                showMessage(
                        "Unable to add vehicle."
                );
            }


        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Battery capacity must be a number."
            );
        }
    }


    // =====================================================
    // UPDATE VEHICLE
    // =====================================================

    private void updateVehicle() {

        if (selectedId == -1) {

            showMessage(
                    "Select a vehicle first."
            );

            return;
        }


        if (!validateFields()) {

            return;
        }


        try {

            double battery =
                    Double.parseDouble(
                            txtBattery
                                    .getText()
                                    .trim()
                    );


            if (
                    dao.updateVehicle(
                            selectedId,
                            getOwnerId(),
                            txtNumber
                                    .getText()
                                    .trim(),
                            txtModel
                                    .getText()
                                    .trim(),
                            choiceType
                                    .getSelectedItem(),
                            battery
                    )
            ) {

                showMessage(
                        "Vehicle updated successfully."
                );


                clearForm();

                loadVehicles();

            } else {

                showMessage(
                        "Unable to update vehicle."
                );
            }


        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Battery capacity must be a number."
            );
        }
    }


    // =====================================================
    // DELETE VEHICLE
    // =====================================================

    private void deleteVehicle() {

        if (selectedId == -1) {

            showMessage(
                    "Select a vehicle first."
            );

            return;
        }


        if (
                dao.deleteVehicle(
                        selectedId
                )
        ) {

            showMessage(
                    "Vehicle deleted successfully."
            );


            clearForm();

            loadVehicles();

        } else {

            showMessage(
                    "Vehicle cannot be deleted because it may have related records."
            );
        }
    }


    // =====================================================
    // LOAD VEHICLES
    // =====================================================

    private void loadVehicles() {

        if (vehicleList == null) {

            return;
        }


        vehicleList.removeAll();


        List<String[]> vehicles =
                dao.getAllVehicles();


        for (
                String[] v :
                vehicles
        ) {

            vehicleList.add(
                    v[0]
                            + "        |        "
                            + v[1]
                            + "        |        "
                            + v[2]
                            + "        |        "
                            + v[3]
                            + "        |        "
                            + v[4]
                            + "        |        "
                            + v[5]
            );
        }
    }


    // =====================================================
    // SELECT VEHICLE
    // =====================================================

    private void selectVehicle() {

        int index =
                vehicleList
                        .getSelectedIndex();


        if (index == -1) {

            return;
        }


        String[] p =
                vehicleList
                        .getItem(index)
                        .split(
                                "\\s*\\|\\s*"
                        );


        if (p.length < 6) {

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
                    "Invalid vehicle ID."
            );

            return;
        }


        txtNumber.setText(
                p[1].trim()
        );


        txtModel.setText(
                p[2].trim()
        );


        choiceType.select(
                p[3].trim()
        );


        txtBattery.setText(
                p[4].trim()
        );


        for (
                int i = 0;
                i < choiceOwner.getItemCount();
                i++
        ) {

            if (
                    choiceOwner
                            .getItem(i)
                            .endsWith(
                                    " - "
                                            + p[5].trim()
                            )
            ) {

                choiceOwner.select(i);

                break;
            }
        }
    }


    // =====================================================
    // VALIDATE
    // =====================================================

    private boolean validateFields() {

        if (
                choiceOwner.getItemCount() == 0
        ) {

            showMessage(
                    "No users are available as vehicle owners."
            );

            return false;
        }


        if (
                txtNumber
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter the vehicle number."
            );

            return false;
        }


        if (
                txtModel
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter the vehicle model."
            );

            return false;
        }


        if (
                txtBattery
                        .getText()
                        .trim()
                        .isEmpty()
        ) {

            showMessage(
                    "Please enter battery capacity."
            );

            return false;
        }


        return true;
    }


    // =====================================================
    // CLEAR
    // =====================================================

    private void clearForm() {

        txtNumber.setText("");

        txtModel.setText("");

        txtBattery.setText("");


        if (
                choiceOwner.getItemCount() > 0
        ) {

            choiceOwner.select(0);
        }


        choiceType.select(
                "Electric Car"
        );


        selectedId = -1;


        int index =
                vehicleList
                        .getSelectedIndex();


        if (index >= 0) {

            vehicleList.deselect(
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