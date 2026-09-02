package gui;

import dao.UserDAO;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserPanel extends Panel {

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

    private static final Color BORDER =
            new Color(218, 224, 229);


    // =====================================================
    // FORM COMPONENTS
    // =====================================================

    private TextField txtName;
    private TextField txtEmail;
    private TextField txtPhone;
    private TextField txtPassword;

    private Choice choiceUserType;

    private TextField txtSearch;


    // =====================================================
    // USER LIST
    // =====================================================

    private java.awt.List userList;


    // =====================================================
    // BUTTONS
    // =====================================================

    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnClear;

    private Button btnSearch;
    private Button btnRefresh;


    // =====================================================
    // VARIABLES
    // =====================================================

    private int selectedUserId = -1;

    private UserDAO userDAO;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public UserPanel() {

        userDAO = new UserDAO();

        setLayout(
                new BorderLayout(
                        0,
                        0
                )
        );

        setBackground(
                LIGHT_BG
        );

        createPage();

        loadUsers();
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
                        "   USER MANAGEMENT"
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
                        "   Manage campus users, roles and account information"
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


        // -----------------------------------------------
        // FORM CARD
        // -----------------------------------------------

        Panel formCard =
                new Panel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        formCard.setBackground(
                CARD
        );


        Panel formTitle =
                new Panel(
                        new BorderLayout()
                );

        formTitle.setBackground(
                NAVY
        );

        formTitle.setPreferredSize(
                new Dimension(
                        100,
                        42
                )
        );


        Label formLabel =
                new Label(
                        "   USER INFORMATION"
                );

        formLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        formLabel.setForeground(
                Color.WHITE
        );


        formTitle.add(
                formLabel,
                BorderLayout.CENTER
        );


        formCard.add(
                formTitle,
                BorderLayout.NORTH
        );


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


        // NAME
        addFormRow(
                formArea,
                gbc,
                0,
                "Full Name",
                txtName = new TextField()
        );


        // EMAIL
        addFormRow(
                formArea,
                gbc,
                1,
                "Email Address",
                txtEmail = new TextField()
        );


        // PHONE
        addFormRow(
                formArea,
                gbc,
                2,
                "Phone Number",
                txtPhone = new TextField()
        );


        // USER TYPE
        choiceUserType =
                new Choice();

        choiceUserType.add(
                "Student"
        );

        choiceUserType.add(
                "Faculty"
        );

        choiceUserType.add(
                "Staff"
        );

        choiceUserType.add(
                "Admin"
        );


        addFormRow(
                formArea,
                gbc,
                3,
                "User Type",
                choiceUserType
        );


        // PASSWORD
        txtPassword =
                new TextField();

        txtPassword.setEchoChar(
                '*'
        );


        addFormRow(
                formArea,
                gbc,
                4,
                "Password",
                txtPassword
        );


        formCard.add(
                formArea,
                BorderLayout.CENTER
        );


        // -----------------------------------------------
        // ACTION BUTTONS
        // -----------------------------------------------

        Panel actionPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                12
                        )
                );

        actionPanel.setBackground(
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


        actionPanel.add(
                btnAdd
        );

        actionPanel.add(
                btnUpdate
        );

        actionPanel.add(
                btnDelete
        );

        actionPanel.add(
                btnClear
        );


        formCard.add(
                actionPanel,
                BorderLayout.SOUTH
        );


        main.add(
                formCard,
                BorderLayout.NORTH
        );


        // -----------------------------------------------
        // USER LIST CARD
        // -----------------------------------------------

        main.add(
                createUserList(),
                BorderLayout.CENTER
        );


        // -----------------------------------------------
        // OUTER PADDING
        // -----------------------------------------------

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


        Panel leftSpace =
                new Panel();

        leftSpace.setBackground(
                LIGHT_BG
        );

        leftSpace.setPreferredSize(
                new Dimension(
                        22,
                        10
                )
        );


        Panel rightSpace =
                new Panel();

        rightSpace.setBackground(
                LIGHT_BG
        );

        rightSpace.setPreferredSize(
                new Dimension(
                        22,
                        10
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


        if (
                field instanceof Choice
        ) {

            Choice choice =
                    (Choice) field;

            choice.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            choice.setBackground(
                    Color.WHITE
            );

            choice.setForeground(
                    TEXT
            );
        }


        panel.add(
                field,
                gbc
        );
    }


    // =====================================================
    // CREATE USER LIST
    // =====================================================

    private Panel createUserList() {

        Panel card =
                new Panel(
                        new BorderLayout(
                                0,
                                0
                        )
                );

        card.setBackground(
                CARD
        );


        // -----------------------------------------------
        // CARD HEADER
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
                        "   REGISTERED USERS"
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
        // SEARCH BAR
        // -----------------------------------------------

        Panel searchPanel =
                new Panel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                10
                        )
                );

        searchPanel.setBackground(
                new Color(
                        250,
                        251,
                        252
                )
        );


        Label searchLabel =
                new Label(
                        "Search users:"
                );

        searchLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        searchLabel.setForeground(
                TEXT
        );


        txtSearch =
                new TextField(
                        25
                );

        txtSearch.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );


        btnSearch =
                createButton(
                        "SEARCH",
                        BLUE
                );


        btnRefresh =
                createButton(
                        "REFRESH",
                        NAVY
                );


        searchPanel.add(
                searchLabel
        );

        searchPanel.add(
                txtSearch
        );

        searchPanel.add(
                btnSearch
        );

        searchPanel.add(
                btnRefresh
        );


        card.add(
                searchPanel,
                BorderLayout.CENTER
        );


        // -----------------------------------------------
        // LIST
        // -----------------------------------------------

        Panel listContainer =
                new Panel(
                        new BorderLayout()
                );

        listContainer.setBackground(
                Color.WHITE
        );


        userList =
                new java.awt.List(
                        10,
                        false
                );


        userList.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        12
                )
        );


        userList.setBackground(
                Color.WHITE
        );


        userList.setForeground(
                TEXT
        );


        listContainer.add(
                userList,
                BorderLayout.CENTER
        );


        card.add(
                listContainer,
                BorderLayout.SOUTH
        );


        // -----------------------------------------------
        // EVENTS
        // -----------------------------------------------

        btnSearch.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        searchUsers();
                    }
                }
        );


        btnRefresh.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        txtSearch.setText("");

                        loadUsers();
                    }
                }
        );


        userList.addItemListener(
                new ItemListener() {

                    @Override
                    public void itemStateChanged(
                            ItemEvent e
                    ) {

                        selectUser();
                    }
                }
        );


        // -----------------------------------------------
        // BUTTON EVENTS
        // -----------------------------------------------

        btnAdd.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        addUser();
                    }
                }
        );


        btnUpdate.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        updateUser();
                    }
                }
        );


        btnDelete.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        deleteUser();
                    }
                }
        );


        btnClear.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        clearForm();
                    }
                }
        );


        return card;
    }


    // =====================================================
    // CREATE BUTTON
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
    // ADD USER
    // =====================================================

    private void addUser() {

        if (!validateFields()) {

            return;
        }


        boolean success =
                userDAO.addUser(
                        txtName.getText().trim(),
                        txtEmail.getText().trim(),
                        txtPhone.getText().trim(),
                        choiceUserType.getSelectedItem(),
                        txtPassword.getText()
                );


        if (success) {

            showMessage(
                    "User added successfully."
            );

            clearForm();

            loadUsers();

        } else {

            showMessage(
                    "Unable to add user. " +
                            "Email may already exist."
            );
        }
    }


    // =====================================================
    // UPDATE USER
    // =====================================================

    private void updateUser() {

        if (selectedUserId == -1) {

            showMessage(
                    "Please select a user first."
            );

            return;
        }


        if (!validateFields()) {

            return;
        }


        boolean success =
                userDAO.updateUser(
                        selectedUserId,
                        txtName.getText().trim(),
                        txtEmail.getText().trim(),
                        txtPhone.getText().trim(),
                        choiceUserType.getSelectedItem(),
                        txtPassword.getText()
                );


        if (success) {

            showMessage(
                    "User updated successfully."
            );

            clearForm();

            loadUsers();

        } else {

            showMessage(
                    "Unable to update user."
            );
        }
    }


    // =====================================================
    // DELETE USER
    // =====================================================

    private void deleteUser() {

        if (selectedUserId == -1) {

            showMessage(
                    "Please select a user first."
            );

            return;
        }


        boolean success =
                userDAO.deleteUser(
                        selectedUserId
                );


        if (success) {

            showMessage(
                    "User deleted successfully."
            );

            clearForm();

            loadUsers();

        } else {

            showMessage(
                    "Unable to delete user. " +
                            "The user may have related records."
            );
        }
    }


    // =====================================================
    // LOAD USERS
    // =====================================================

    private void loadUsers() {

        userList.removeAll();


        List<String[]> users =
                userDAO.getAllUsers();


        for (
                String[] user :
                users
        ) {

            userList.add(
                    user[0]
                            + "    |    "
                            + user[1]
                            + "    |    "
                            + user[2]
                            + "    |    "
                            + user[3]
                            + "    |    "
                            + user[4]
            );
        }
    }


    // =====================================================
    // SEARCH USERS
    // =====================================================

    private void searchUsers() {

        String keyword =
                txtSearch.getText().trim();


        if (keyword.isEmpty()) {

            loadUsers();

            return;
        }


        userList.removeAll();


        List<String[]> users =
                userDAO.searchUsers(
                        keyword
                );


        for (
                String[] user :
                users
        ) {

            userList.add(
                    user[0]
                            + "    |    "
                            + user[1]
                            + "    |    "
                            + user[2]
                            + "    |    "
                            + user[3]
                            + "    |    "
                            + user[4]
            );
        }
    }


    // =====================================================
    // SELECT USER
    // =====================================================

    private void selectUser() {

        int index =
                userList.getSelectedIndex();


        if (index == -1) {

            return;
        }


        String selected =
                userList.getItem(
                        index
                );


        String[] parts =
                selected.split(
                        "\\s*\\|\\s*"
                );


        if (parts.length < 5) {

            return;
        }


        try {

            selectedUserId =
                    Integer.parseInt(
                            parts[0].trim()
                    );

        } catch (
                NumberFormatException e
        ) {

            showMessage(
                    "Invalid user ID."
            );

            return;
        }


        txtName.setText(
                parts[1].trim()
        );


        txtEmail.setText(
                parts[2].trim()
        );


        txtPhone.setText(
                parts[3].trim()
        );


        choiceUserType.select(
                parts[4].trim()
        );


        txtPassword.setText("");
    }


    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateFields() {

        String name =
                txtName.getText().trim();


        String email =
                txtEmail.getText().trim();


        String phone =
                txtPhone.getText().trim();


        String password =
                txtPassword.getText();


        if (name.isEmpty()) {

            showMessage(
                    "Name cannot be empty."
            );

            return false;
        }


        if (
                email.isEmpty()
                        || !email.contains("@")
                        || !email.contains(".")
        ) {

            showMessage(
                    "Please enter a valid email."
            );

            return false;
        }


        if (
                !phone.matches(
                        "\\d{10}"
                )
        ) {

            showMessage(
                    "Phone number must contain 10 digits."
            );

            return false;
        }


        if (password.isEmpty()) {

            showMessage(
                    "Password cannot be empty."
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

        txtEmail.setText("");

        txtPhone.setText("");

        txtPassword.setText("");


        choiceUserType.select(
                "Student"
        );


        selectedUserId = -1;


        int selectedIndex =
                userList.getSelectedIndex();


        if (
                selectedIndex >= 0
        ) {

            userList.deselect(
                    selectedIndex
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
                getParentFrame();


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
                        message.replace(
                                "\n",
                                " "
                        )
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


        Button okButton =
                createButton(
                        "OK",
                        NAVY
                );


        okButton.addActionListener(
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
                okButton
        );


        dialog.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        dialog.setSize(
                430,
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
                        && !(component instanceof Frame)
        ) {

            component =
                    component.getParent();
        }


        return (Frame) component;
    }
}