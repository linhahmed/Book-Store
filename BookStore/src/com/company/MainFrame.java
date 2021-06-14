import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
//import com.mysql.cj.conf.ConnectionUrlParser.Pair;



public class MainFrame extends JFrame {
    //GENERAL PARAMETERS ---------------------------------------------------------------------------------
    public static final int W = 550;
    public static final int H = 700;
    public static int userType = 2;
    public static String username;
    public static int price = 0;
    public static Manager user = new Manager();
    public static LogIn logger = new LogIn();

    private static String state;

    JMenuBar menuBar = new JMenuBar();


    //private JPanel panel;
    private CardLayout layout = new CardLayout();

    //JMenuBar PARAMETERS ---------------------------------------------------------------------------------
    JMenu fileMenu = new JMenu("File");
    JMenu viewMMenu = new JMenu("View");
    JMenu viewUMenu = new JMenu("View");
    JMenu editMenu = new JMenu("Edit");

    JMenuItem logOutItem = new JMenuItem("Log Out");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem mainItem = new JMenuItem("Main");
    JMenuItem cartItem = new JMenuItem("Cart");
    JMenuItem reportsItem = new JMenuItem("Reports");
    JMenuItem addNewItem = new JMenuItem("Add New Book");
    JMenuItem usersItem = new JMenuItem("Users");

    //SIGN IN PARAMETERS ---------------------------------------------------------------------------------
    private static final String SIGN_IN = "sign_in";
    private JPanel signInPanel;
    private NFButton signInButton;
    private JTextField usernameTF;
    private JPasswordField passTF;
    private JLabel usernameLL;
    private JLabel passLL;
    private JLabel warningLL;

    //USERS PARAMETERS ---------------------------------------------------------------------------------
    private static final String USERS =  "users";
    private JPanel usersPanel = new JPanel();
    private JScrollPane usersPane = new JScrollPane();
    private JPanel innerUsersPanel = new JPanel();

    //REPORTS PARAMETERS ---------------------------------------------------------------------------------
    private static final String REPORTS = "reports";
    private JPanel reportsPanel = new JPanel();


    //MAIN PARAMETERS ---------------------------------------------------------------------------------
    private static final String MAIN = "main";
    private JPanel mainPanel = new JPanel();
    private JScrollPane booksPane = new JScrollPane();
    private JPanel searchPanel = new JPanel();
    private NFButton searchButton = new NFButton("Search");
    private JTextField searchField = new JTextField();
    private String[] attrs = {"Title", "Author", "Publisher", "Publication Year", "Category", "ISBN"};
    private JComboBox attrBox = new JComboBox(attrs);
    private JPanel innerBooksPanel = new JPanel();

    //CART PARAMETERS ---------------------------------------------------------------------------------
    private static final String CART = "cart";
    private JPanel cartPanel = new JPanel();
    private JScrollPane cartBPane = new JScrollPane();
    private JPanel checkoutPanel = new JPanel();
    private NFButton checkoutButton = new NFButton("Checkout");
    private JLabel priceLabel = new JLabel();
    private JPanel innerCartPanel = new JPanel();

    //ADD PARAMETERS ---------------------------------------------------------------------------------
    private static final String ADD = "add";
    private JPanel addPanel = new JPanel();
    private NFButton addBButton = new NFButton("Add Book");

    private JLabel isbnLabel = new JLabel("ISBN");
    private JTextField isbnTF = new JTextField();
    private JLabel titleLabel = new JLabel("Book Title");
    private JTextField titleTF = new JTextField();
    private JLabel pubLabel = new JLabel("Published By");
    private JTextField pubTF = new JTextField();
    private JLabel yLabel = new JLabel("Publication Year");
    private JTextField yTF = new JTextField();
    private JLabel qLabel = new JLabel("Quantity");
    private JTextField qTF = new JTextField();
    private JLabel mQLabel = new JLabel("Minimum Quantity");
    private JTextField mQTF = new JTextField();
    private JLabel priLabel = new JLabel("Price");
    private JTextField priTF = new JTextField();
    private JLabel cateLabel = new JLabel("Category");
    private String[] cateS = {"Science", "Art", "Religion", "History", "Geography"};
    private JComboBox cateTF = new JComboBox(cateS);
    private JLabel authLabel = new JLabel("Author(s) - Separate With Comma If Many");
    private JTextField authTF = new JTextField();

    //CHECKOUT PARAMETERS ---------------------------------------------------------------------------------
    private static final String CHECKOUT = "checkout";
    private JPanel checkPanel = new JPanel();
    private NFButton confirmChButton = new NFButton("Confirm Checkout");
    private JLabel cCNumberLabel = new JLabel("CREDIT CARD NUMBER");
    private JLabel eDateLabel = new JLabel("EXPIRATION DATE");
    private JLabel cVCLabel = new JLabel("CVC");
    private JLabel nOTCardLabel = new JLabel("NAME ON THE CARD");
    private JTextField cCNumberField = new JTextField();
    private JTextField eDateField = new JTextField();
    private JTextField cVCField = new JTextField();
    private JTextField nOTCardField = new JTextField();
    private JLabel warningLabel = new JLabel("");

    Container me = this.getContentPane();
    JFrame realme = this;

    Font labelFont = new Font(Font.SANS_SERIF, Font.ITALIC, 15);
    Font inputFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

    public MainFrame() {
        super("Kindle");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(layout);
        setJMenuBar(menuBar);
        me.setPreferredSize(new Dimension(W, H));
        realme.pack();
        setLocationRelativeTo(null);


        buildMenuBar();
        buildSignIn();
        buildMenuPanel();
        buildCartPanel();
        buildCheckoutPanel();
        buildAddPanel();
        buildUsersPanel();
        buildReportsPanel();

        showSignIn();
    }


    private void buildUsersPane() {
        ArrayList<String> users = (ArrayList<String>) user.all_customers();
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        innerUsersPanel.removeAll();
        innerUsersPanel.setLayout(null);
        innerUsersPanel.setPreferredSize(new Dimension(500, 95 * users.size() + 10));

        for (int i = 0; i < users.size(); i++) {
            String currUser = users.get(i);
            JLabel title = new JLabel(user.individual_Userattribute(users.get(i), "fname") + " " + user.individual_Userattribute(users.get(i), "lname"));
            title.setFont(titleFont);
            title.setBounds(10, 95 * i + 10, 400, 40);
            innerUsersPanel.add(title);
            //System.out.println(title.getMinimumSize().width);

            JLabel username = new JLabel("Username: " + users.get(i));
            username.setFont(labelFont);
            username.setBounds(10, 95 * i + 50, 250, 35);
            innerUsersPanel.add(username);

            NFButton promote = new NFButton(Integer.parseInt(user.individual_Userattribute(currUser, "ismanager")) == 1 ? "Give Access" : "Access Given");
            promote.setFont(labelFont);
            promote.setBounds(380, 95 * i + 50, 130, 35);
            if (Integer.parseInt(user.individual_Userattribute(currUser, "ismanager")) == 2) promote.setEnabled(false);
            innerUsersPanel.add(promote);

            promote.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    user.promote(currUser);
                    showUsersPanel();
                }
            });
        }
        usersPane = new JScrollPane(innerUsersPanel);
        usersPane.setAutoscrolls(true);
        usersPane.setPreferredSize(new Dimension(500, 500));
    }
    private void buildCartPane() {
        ArrayList<Pair> cart = (ArrayList<Pair>) user.view_items();
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        innerCartPanel.removeAll();

        innerCartPanel.setLayout(null);
        innerCartPanel.setPreferredSize(new Dimension(500, 95 * cart.size() + 10));

        for (int i = 0; i < cart.size(); i++) {
            JLabel title = new JLabel(user.individual_attribute(cart.get(i).left, "title"));
            title.setFont(titleFont);
            title.setBounds(10, 95 * i + 10, 400, 40);
            innerCartPanel.add(title);

            JLabel price = new JLabel("$" + user.individual_price(cart.get(i).left) + ".00");
            price.setFont(labelFont);
            price.setBounds(450, 95 * i + 10, 100, 40);
            innerCartPanel.add(price);

            JLabel quantity = new JLabel("Quantity: " + cart.get(i).right);
            quantity.setFont(labelFont);
            quantity.setBounds(10, 95 * i + 50, 250, 35);
            innerCartPanel.add(quantity);

            NFButton remove = new NFButton("Remove");
            remove.setFont(labelFont);
            remove.setBounds(390, 95 * i + 50, 120, 35);
            innerCartPanel.add(remove);
            String currentISBN = cart.get(i).left;

            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    user.remove_item(currentISBN);
                    showCartPanel();
                }
            });
        }

        cartBPane = new JScrollPane(innerCartPanel);
        cartBPane.setAutoscrolls(true);
        cartBPane.setPreferredSize(new Dimension(500, 500));
    }
    private void buildBooksPane() {
        String attr;
        if (attrs[attrBox.getSelectedIndex()].contentEquals("Title")) attr = "Title";
        else if (attrs[attrBox.getSelectedIndex()].contentEquals("Publisher")) attr = "Publisher";
        else if (attrs[attrBox.getSelectedIndex()].contentEquals("Publication Year")) attr = "Publication_Year";
        else if (attrs[attrBox.getSelectedIndex()].contentEquals("Category")) attr = "Category";
        else if (attrs[attrBox.getSelectedIndex()].contentEquals("Author")) attr = "author";
        else attr = "ISBN";
        ArrayList<String> dispBooks = (ArrayList<String>) user.search_for_book(attr, searchField.getText());
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

        innerBooksPanel.removeAll();
        innerBooksPanel.setLayout(null);
        innerBooksPanel.setPreferredSize(new Dimension(500, 130 * dispBooks.size() + 10));

        for (int i = 0; i < dispBooks.size(); i++) {
            JLabel title = new JLabel(user.individual_attribute(dispBooks.get(i), "title"));
            title.setFont(titleFont);
            title.setBounds(10, 130 * i + 10, 400, 40);
            innerBooksPanel.add(title);

            JLabel publisher = new JLabel("Published By: " + user.individual_attribute(dispBooks.get(i), "publisher"));
            publisher.setFont(labelFont);
            publisher.setBounds(10, 130 * i + 50, 250, 23);
            innerBooksPanel.add(publisher);

            JLabel category = new JLabel("Category: " + user.individual_attribute(dispBooks.get(i), "category"));
            category.setFont(labelFont);
            category.setBounds(10, 130 * i + 73, 250, 23);
            innerBooksPanel.add(category);

            JLabel author = new JLabel("By: " + user.individual_attribute(dispBooks.get(i), "author"));
            author.setFont(labelFont);
            author.setBounds(10, 130 * i + 96, 250, 23);
            innerBooksPanel.add(author);

            JLabel price = new JLabel("$" + user.individual_price(dispBooks.get(i)) + ".00");
            price.setFont(labelFont);
            price.setBounds(450, 130 * i + 10, 100, 40);
            innerBooksPanel.add(price);

            JTextField numBuy = new JTextField();
            numBuy.setFont(labelFont);
            numBuy.setBounds(390, 130 * i + 50, 120, 35);
            innerBooksPanel.add(numBuy);

            NFButton buy = new NFButton("Add To Cart");
            buy.setFont(labelFont);
            buy.setBounds(390, 130 * i + 85, 120, 35);
            innerBooksPanel.add(buy);

            String currentISBN = dispBooks.get(i);

            buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isNumeric(numBuy.getText())) {
                        JOptionPane.showMessageDialog(realme, "Please enter a valid amount to buy");
                    } else {
                        user.add_item(currentISBN, Integer.parseInt(numBuy.getText()));
                        System.out.println(user.cart);
                        numBuy.setText("");
                    }
                }
            });
            NFButton edit = new NFButton("Edit Quantity");

            JTextField quantity = new JTextField();
            quantity.setFont(labelFont);
            quantity.setBounds(260, 130 * i + 50, 120, 35);
            quantity.setVisible(false);
            innerBooksPanel.add(quantity);

            NFButton save = new NFButton("Save");
            save.setFont(labelFont);
            save.setBounds(260, 130 * i + 85, 120, 35);
            save.setVisible(false);
            innerBooksPanel.add(save);
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isNumeric(quantity.getText())) {
                        JOptionPane.showMessageDialog(realme, "Please enter a valid amount for quantity");
                    } else {
                        user.modify_existing_book(currentISBN, Integer.parseInt(quantity.getText()));
                        quantity.setText("");
                        quantity.setVisible(false);
                        save.setVisible(false);
                        edit.setVisible(true);
                        JOptionPane.showMessageDialog(realme, "Edited successfully");
                    }
                }
            });
            edit.setFont(labelFont);
            if (user.get_isManager() != 2) edit.setVisible(false);
            edit.setBounds(260, 130 * i + 85, 120, 35);
            innerBooksPanel.add(edit);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    edit.setVisible(false);
                    quantity.setVisible(true);
                    save.setVisible(true);
                }
            });
        }


        booksPane = new JScrollPane(innerBooksPanel);
        booksPane.setAutoscrolls(true);
        booksPane.setPreferredSize(new Dimension(500, 500));
    }


    private void buildMenuBar() {
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMMenu);
        menuBar.add(viewUMenu);

        fileMenu.add(logOutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        editMenu.add(addNewItem);
        editMenu.add(usersItem);

        logOutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.log_out();
                searchField.setText("");
                showSignIn();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.log_out();
                realme.dispose();
            }
        });
        cartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartPanel();
            }
        });
        addNewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPanel();
            }
        });
        usersItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUsersPanel();
            }
        });
        reportsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportsPanel();
            }
        });
        mainItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuPanel();
            }
        });


    }
    private void setMenuBar() {
        fileMenu.setVisible(true);
        if (user.get_isManager() == 1) {
            viewUMenu.setVisible(true);
            viewUMenu.add(mainItem);
            viewUMenu.add(cartItem);
        } else if (user.get_isManager() == 2) {
            viewMMenu.setVisible(true);
            editMenu.setVisible(true);
            viewMMenu.removeAll();
            viewMMenu.add(mainItem);
            viewMMenu.add(cartItem);
            viewMMenu.addSeparator();
            viewMMenu.add(reportsItem);
        }
    }
    private void hidMenuBar() {
        fileMenu.setVisible(false);
        viewMMenu.setVisible(false);
        viewUMenu.setVisible(false);
        editMenu.setVisible(false);
    }

    private void buildSignIn() {
        signInPanel = new JPanel();
        signInPanel.setLayout(null);
        me.add(signInPanel, SIGN_IN);

        signInButton = new NFButton("Sign In");
        signInButton.setBounds(110, 350,  115, 35);
        signInPanel.add(signInButton);
        usernameLL = new JLabel("Username");

        usernameTF = new JTextField();
        passLL = new JLabel("Password");
        passTF = new JPasswordField();
        warningLL = new JLabel("");
        usernameLL.setBounds(110, 200, 330, 35);
        usernameLL.setFont(labelFont);
        usernameTF.setBounds(110, 235, 330, 35);
        usernameTF.setFont(inputFont);
        passLL.setBounds(110, 270, 330, 35);
        passLL.setFont(labelFont);
        passTF.setBounds(110, 305, 330, 35);
        passTF.setFont(inputFont);
        warningLL.setBounds(110, 385, 330, 35);
        warningLL.setFont(labelFont);
        warningLL.setForeground(Color.RED);

        signInPanel.add(usernameLL);
        signInPanel.add(usernameTF);
        signInPanel.add(passLL);
        signInPanel.add(passTF);
        signInPanel.add(warningLL);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int checkRes = logger.check(usernameTF.getText(), passTF.getText());
                if (checkRes == 0) {
                    warningLL.setText("Invalid username or password.");
                } else {
                    user.set_isManager(checkRes);
                    user.set_UserName(usernameTF.getText());

                    usernameTF.setText("");
                    passTF.setText("");
                    warningLL.setText("");

                    setMenuBar();
                    showMenuPanel();
                }
            }
        });
    }
    private void showSignIn() {
        hidMenuBar();
        layout.show(me, SIGN_IN);
    }

    private void buildMenuPanel() {
        mainPanel.setLayout(new BorderLayout());
        me.add(mainPanel, MAIN);
        searchPanel.setLayout(null);
        searchPanel.setPreferredSize(new Dimension(500, 55));
        attrBox.setBounds(13, 10, 100, 35);
        attrBox.setFont(inputFont);
        attrBox.setFocusable(false);
        searchPanel.add(attrBox);
        searchField.setBounds(123, 10, 290, 35);
        searchField.setFont(inputFont);
        searchPanel.add(searchField);
        searchButton.setBounds(423, 10, 100, 35);
        searchButton.setFont(inputFont);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(booksPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuPanel();
            }
        });





    }
    private void showMenuPanel() {
        mainPanel.remove(booksPane);
        buildBooksPane();
        mainPanel.add(booksPane, BorderLayout.CENTER);
        layout.show(me, MAIN);
        realme.revalidate();
    }

    private void buildCartPanel() {
        cartPanel.setLayout(new BorderLayout());
        me.add(cartPanel, CART);
        checkoutPanel.setLayout(null);
        checkoutPanel.setPreferredSize(new Dimension(500, 55));
        checkoutButton.setBounds(13, 10, 100, 35);
        checkoutButton.setFont(inputFont);
        checkoutPanel.add(checkoutButton);
        priceLabel.setBounds(363, 10, 150, 35);
        priceLabel.setFont(inputFont);
        priceLabel.setText("Total Price is $" + user.total_price() + ".00");
        checkoutPanel.add(priceLabel);
        cartPanel.add(checkoutPanel, BorderLayout.SOUTH);
        cartPanel.add(cartBPane, BorderLayout.CENTER);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.view_items().isEmpty()) {
                    JOptionPane.showMessageDialog(realme, "Your cart is empty.");
                } else {
                    showCheckoutPanel();
                }
            }
        });
    }
    private void showCartPanel() {
        cartPanel.remove(cartBPane);
        buildCartPane();
        cartPanel.add(cartBPane, BorderLayout.CENTER);
        priceLabel.setText("Total Price is $" + user.total_price() + ".00");
        layout.show(me, CART);
        realme.revalidate();
    }

    private void buildCheckoutPanel() {
        checkPanel = new JPanel();
        checkPanel.setLayout(null);
        me.add(checkPanel, CHECKOUT);
        cCNumberLabel.setBounds(110, 200, 330, 35);
        cCNumberLabel.setFont(labelFont);
        cCNumberField.setBounds(110, 235, 330, 35);
        cCNumberField.setFont(inputFont);
        eDateLabel.setBounds(110, 270, 160, 35);
        eDateLabel.setFont(labelFont);
        eDateField.setBounds(110, 305, 160, 35);
        eDateField.setFont(inputFont);

        cVCLabel.setBounds(280, 270, 160, 35);
        cVCLabel.setFont(labelFont);
        cVCField.setBounds(280, 305, 160, 35);
        cVCField.setFont(inputFont);
        nOTCardLabel.setBounds(110, 340, 330, 35);
        nOTCardLabel.setFont(labelFont);
        nOTCardField.setBounds(110, 375, 330, 35);
        nOTCardField.setFont(inputFont);

        confirmChButton.setBounds(110, 420, 165, 35);
        confirmChButton.setFont(inputFont);

        warningLabel.setBounds(110, 455, 165, 35);
        warningLabel.setFont(labelFont);
        warningLabel.setForeground(Color.RED);

        checkPanel.add(cCNumberLabel);
        checkPanel.add(cCNumberField);
        checkPanel.add(eDateLabel);
        checkPanel.add(eDateField);
        checkPanel.add(cVCLabel);
        checkPanel.add(cVCField);
        checkPanel.add(nOTCardLabel);
        checkPanel.add(nOTCardField);
        checkPanel.add(confirmChButton);
        checkPanel.add(warningLabel);

        confirmChButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNumeric(cCNumberField.getText())
                        && (cCNumberField.getText().matches("^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                        "(?<mastercard>5[1-5][0-9]{14})|" +
                        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                        "(?<amex>3[47][0-9]{13})|" +
                        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$"))
                        && eDateField.getText().matches("(?:0[1-9]|1[0-2])/[0-9]{2}")
                        && isNumeric(cVCField.getText()) && cVCField.getText().length() == 3
                        && nOTCardField.getText().length() > 1) {
                    user.checkout();
                    showCheckoutPanel();
                    JOptionPane.showMessageDialog(realme, "Your order has been placed successfully");
                    showCartPanel();
                } else {
                    JOptionPane.showMessageDialog(realme, "Please enter your credit card's info correctly");
                }
            }
        });
    }
    private void showCheckoutPanel() {
        cCNumberField.setText("");
        eDateField.setText("");
        cVCField.setText("");
        nOTCardField.setText("");
        layout.show(me, CHECKOUT);
    }

    private void buildAddPanel() {
        addPanel = new JPanel();
        addPanel.setLayout(null);
        me.add(addPanel, ADD);
        titleLabel.setBounds(110, 100, 330, 35);
        titleLabel.setFont(labelFont);
        titleTF.setBounds(110, 135, 330, 35);
        titleTF.setFont(inputFont);
        isbnLabel.setBounds(110, 170, 330, 35);
        isbnLabel.setFont(labelFont);
        isbnTF.setBounds(110, 205, 330, 35);
        isbnTF.setFont(inputFont);
        pubLabel.setBounds(110, 240, 160, 35);
        pubLabel.setFont(labelFont);
        pubTF.setBounds(110, 275, 160, 35);
        pubTF.setFont(inputFont);
        yLabel.setBounds(280, 240, 160, 35);
        yLabel.setFont(labelFont);
        yTF.setBounds(280, 275, 160, 35);
        yTF.setFont(inputFont);
        qLabel.setBounds(110, 310, 160, 35);
        qLabel.setFont(labelFont);
        qTF.setBounds(110, 345, 160, 35);
        qTF.setFont(inputFont);
        mQLabel.setBounds(280, 310, 160, 35);
        mQLabel.setFont(labelFont);
        mQTF.setBounds(280, 345, 160, 35);
        mQTF.setFont(inputFont);
        priLabel.setBounds(110, 380, 160, 35);
        priLabel.setFont(labelFont);
        priTF.setBounds(110, 415, 160, 35);
        priTF.setFont(inputFont);
        cateLabel.setBounds(280, 380, 160, 35);
        cateLabel.setFont(labelFont);
        cateTF.setBounds(280, 415, 160, 35);
        cateTF.setFont(inputFont);
        cateTF.setFocusable(false);
        authLabel.setBounds(110, 450, 330, 35);
        authLabel.setFont(labelFont);
        authTF.setBounds(110, 485, 330, 35);
        authTF.setFont(inputFont);

        addBButton.setBounds(110, 530, 165, 35);
        addBButton.setFont(inputFont);

        addPanel.add(titleLabel);
        addPanel.add(titleTF);
        addPanel.add(isbnLabel);
        addPanel.add(isbnTF);
        addPanel.add(pubLabel);
        addPanel.add(pubTF);
        addPanel.add(yLabel);
        addPanel.add(yTF);
        addPanel.add(qLabel);
        addPanel.add(qTF);
        addPanel.add(mQLabel);
        addPanel.add(mQTF);
        addPanel.add(priLabel);
        addPanel.add(priTF);
        addPanel.add(cateLabel);
        addPanel.add(cateTF);
        addPanel.add(authLabel);
        addPanel.add(authTF);
        addPanel.add(addBButton);

        addBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isbnTF.getText().isEmpty() && !titleTF.getText().isEmpty()
                && !pubTF.getText().isEmpty() && !yTF.getText().isEmpty() && isNumeric(yTF.getText())
                && !qTF.getText().isEmpty() && !mQTF.getText().isEmpty()
                && !priTF.getText().isEmpty() && isNumeric(priTF.getText())
                && isNumeric(qTF.getText()) && isNumeric(mQTF.getText())
                && authTF.getText().matches("([\\w ]+)(,\\s*[\\w ]+)*")) {
                    user.add_book(isbnTF.getText(), titleTF.getText(), pubTF.getText(), yTF.getText(),
                            Integer.parseInt(qTF.getText()), Integer.parseInt(mQTF.getText()),
                            Integer.parseInt(priTF.getText()), cateS[cateTF.getSelectedIndex()],
                            Arrays.asList(authTF.getText().split(",")));
                    showAddPanel();
                    JOptionPane.showMessageDialog(realme, "Added successfully.");
                } else {
                    JOptionPane.showMessageDialog(realme, "Please enter valid book information.");
                }
            }
        });

    }
    private void showAddPanel() {
        isbnTF.setText("");
        titleTF.setText("");
        pubTF.setText("");
        yTF.setText("");
        qTF.setText("");
        mQTF.setText("");
        priTF.setText("");
        authTF.setText("");
        cateTF.setSelectedIndex(0);
        layout.show(me, ADD);

    }

    private void buildUsersPanel() {
        usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        me.add(usersPanel, USERS);
    }
    private void showUsersPanel() {
        usersPanel.remove(usersPane);
        buildUsersPane();
        usersPanel.add(usersPane, BorderLayout.CENTER);
        layout.show(me, USERS);
        realme.revalidate();
    }

    private void buildReportsPane() {
        reportsPanel.removeAll();
        reportsPanel.setLayout(null);
        reportsPanel.setPreferredSize(new Dimension(550, 700));
        int totalSales = user.total_sales();
        List<Pair> topCus = user.top_customers();
        List<Pair> topSell = user.top_selling_books();
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);

        int dis = -100;
        JLabel tSalesL0 = new JLabel("Total Sales");
        tSalesL0.setBounds(110, dis + 150, 165, 35);
        tSalesL0.setFont(inputFont);
        reportsPanel.add(tSalesL0);
        JLabel tSalesL1 = new JLabel(String.valueOf(totalSales));
        tSalesL1.setBounds(440 - tSalesL1.getMinimumSize().width, dis + 150, 165, 35);
        tSalesL1.setFont(inputFont);
        reportsPanel.add(tSalesL1);
        JLabel tCusL = new JLabel("Top Customers");
        tCusL.setFont(titleFont);
        tCusL.setBounds(275 - tCusL.getMinimumSize().width / 2, dis + 185, 330, 35);
        reportsPanel.add(tCusL);
        for (int i = 0; i < topCus.size(); i++) {
            JLabel cus = new JLabel(topCus.get(i).left);
            cus.setFont(inputFont);
            cus.setBounds(110, dis + 220 + i * 35, 165, 35);
            reportsPanel.add(cus);

            JLabel cusA = new JLabel(String.valueOf(topCus.get(i).right));
            cusA.setFont(inputFont);
            cusA.setBounds(440 - cusA.getMinimumSize().width, dis + 220 + i * 35, 165, 35);
            reportsPanel.add(cusA);
        }
        JLabel tBoL = new JLabel("Top Selling Books");
        tBoL.setFont(titleFont);
        tBoL.setBounds(275 - tBoL.getMinimumSize().width / 2, dis + 220 + topCus.size() * 35, 330, 35);
        reportsPanel.add(tBoL);
        for (int i = 0; i < topSell.size(); i++) {
            JLabel cus = new JLabel(topSell.get(i).left);
            cus.setFont(inputFont);
            cus.setBounds(110, dis + 255 + topCus.size() * 35 + i * 35, 165, 35);
            reportsPanel.add(cus);

            JLabel cusA = new JLabel(String.valueOf(topSell.get(i).right));
            cusA.setFont(inputFont);
            cusA.setBounds(440 - cusA.getMinimumSize().width, dis + 255 + topCus.size() * 35 + i * 35, 165, 35);
            reportsPanel.add(cusA);
        }

    }
    private void buildReportsPanel() {
        reportsPanel = new JPanel();
        me.add(reportsPanel, REPORTS);
    }
    private void showReportsPanel() {
        buildReportsPane();
        layout.show(me, REPORTS);
        realme.revalidate();
    }




    public boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]+");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }





}
