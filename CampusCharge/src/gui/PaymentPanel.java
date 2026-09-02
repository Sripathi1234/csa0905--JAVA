package gui;

import dao.PaymentDAO;

import java.awt.*;
import java.util.List;

public class PaymentPanel extends Panel {

    // =====================================================
    // COLORS
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

    private Choice choiceSession;
    private Choice choiceMethod;

    private TextField txtTransaction;

    private java.awt.List paymentList;

    private PaymentDAO dao;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public PaymentPanel() {

        dao = new PaymentDAO();

        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);

        createForm();
        createList();

        loadSessions();
        loadPayments();
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
                        "PAYMENT INFORMATION",
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
                        12,
                        20,
                        12,
                        20
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // COMPLETED SESSION
        addLabel(
                form,
                gbc,
                "Completed Session:",
                0,
                0
        );

        choiceSession =
                new Choice();

        addComponent(
                form,
                gbc,
                choiceSession,
                1,
                0
        );

        // PAYMENT METHOD
        addLabel(
                form,
                gbc,
                "Payment Method:",
                0,
                1
        );

        choiceMethod =
                new Choice();

        choiceMethod.add("UPI");
        choiceMethod.add("CARD");
        choiceMethod.add("CAMPUS_WALLET");
        choiceMethod.add("CASH");

        addComponent(
                form,
                gbc,
                choiceMethod,
                1,
                1
        );

        // TRANSACTION ID
        addLabel(
                form,
                gbc,
                "Transaction ID:",
                0,
                2
        );

        txtTransaction =
                new TextField();

        addComponent(
                form,
                gbc,
                txtTransaction,
                1,
                2
        );

        // INFORMATION
        Label info =
                new Label(
                        "Leave Transaction ID empty to generate one automatically."
                );

        info.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        info.setForeground(GREY);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;

        form.add(
                info,
                gbc
        );

        formCard.add(
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
                                14,
                                12
                        )
                );

        buttons.setBackground(WHITE);

        Button pay =
                createButton(
                        "PROCESS PAYMENT",
                        BLUE
                );

        Button refresh =
                createButton(
                        "REFRESH",
                        GREY
                );

        buttons.add(pay);
        buttons.add(refresh);

        formCard.add(
                buttons,
                BorderLayout.SOUTH
        );

        add(
                formCard,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // EVENTS
        // -------------------------------------------------

        pay.addActionListener(
                e -> processPayment()
        );

        refresh.addActionListener(
                e -> {
                    loadSessions();
                    loadPayments();
                }
        );
    }

    // =====================================================
    // PAYMENT LIST
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

        Label title =
                new Label(
                        "PAYMENT HISTORY",
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

        listCard.add(
                title,
                BorderLayout.NORTH
        );

        // -------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------

        Panel tableHeader =
                new Panel(
                        new GridLayout(
                                1,
                                6
                        )
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
                "PAYMENT ID"
        );

        addHeader(
                tableHeader,
                "TRANSACTION"
        );

        addHeader(
                tableHeader,
                "AMOUNT"
        );

        addHeader(
                tableHeader,
                "METHOD"
        );

        addHeader(
                tableHeader,
                "STATUS"
        );

        addHeader(
                tableHeader,
                "DATE"
        );

        // -------------------------------------------------
        // PAYMENT LIST
        // -------------------------------------------------

        paymentList =
                new java.awt.List(
                        12,
                        false
                );

        paymentList.setBackground(
                WHITE
        );

        paymentList.setForeground(
                TEXT
        );

        paymentList.setFont(
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
                paymentList,
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
                new Color(
                        247,
                        249,
                        250
                )
        );

        Label note =
                new Label(
                        "Completed payments are displayed here for transaction tracking."
                );

        note.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        12
                )
        );

        note.setForeground(
                GREY
        );

        bottomBar.add(note);

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
    // LOAD COMPLETED SESSIONS
    // =====================================================

    private void loadSessions() {

        if (choiceSession == null) {
            return;
        }

        choiceSession.removeAll();

        List<String[]> sessions =
                dao.getCompletedSessions();

        if (sessions == null) {
            return;
        }

        for (String[] s : sessions) {

            choiceSession.add(
                    s[0]
                            + " - "
                            + s[1]
                            + " - ₹"
                            + s[2]
            );
        }
    }

    // =====================================================
    // PROCESS PAYMENT
    // =====================================================

    private void processPayment() {

        if (
                choiceSession
                        .getItemCount()
                        == 0
        ) {

            showMessage(
                    "No unpaid completed sessions are available."
            );

            return;
        }

        try {

            String selected =
                    choiceSession
                            .getSelectedItem();

            String[] p =
                    selected.split(
                            " - "
                    );

            int sessionId =
                    Integer.parseInt(
                            p[0].trim()
                    );

            double amount =
                    Double.parseDouble(
                            p[p.length - 1]
                                    .replace(
                                            "₹",
                                            ""
                                    )
                                    .trim()
                    );

            String transaction =
                    txtTransaction
                            .getText()
                            .trim();

            if (transaction.isEmpty()) {

                transaction =
                        "EVPAY"
                                + System.currentTimeMillis();
            }

            if (
                    dao.makePayment(
                            sessionId,
                            amount,
                            choiceMethod
                                    .getSelectedItem(),
                            transaction
                    )
            ) {

                showMessage(
                        "Payment successful. Amount: ₹"
                                + String.format(
                                "%.2f",
                                amount
                        )
                );

                txtTransaction.setText("");

                loadSessions();
                loadPayments();

            } else {

                showMessage(
                        "Payment failed."
                );
            }

        } catch (NumberFormatException e) {

            showMessage(
                    "Invalid session or payment amount."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Unable to process payment."
            );
        }
    }

    // =====================================================
    // LOAD PAYMENT HISTORY
    // =====================================================

    private void loadPayments() {

        if (paymentList == null) {
            return;
        }

        paymentList.removeAll();

        List<String[]> payments =
                dao.getPayments();

        if (payments == null) {
            return;
        }

        for (String[] p : payments) {

            String display =
                    String.format(
                            "%-10s | %-18s | ₹%-10s | %-15s | %-10s | %s",
                            p[0],
                            p[1],
                            p[2],
                            p[3],
                            p[4],
                            p[5]
                    );

            paymentList.add(
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
        gbc.gridheight = 1;
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
        gbc.gridheight = 1;
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
                        160,
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

            c = c.getParent();
        }

        return (Frame) c;
    }
}