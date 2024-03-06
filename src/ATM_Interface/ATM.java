package ATM_Interface;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ATM {
	 private static final String USERNAME = "user";
	    private static final String PASSWORD = "password";
	    private static final String PIN = "8553";

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(LoginFrame::new);
	    }

	    static class LoginFrame extends JFrame {
	        private JTextField usernameField;
	        private JPasswordField passwordField;

	        LoginFrame() {
	            super("ATM Login");
	            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            setResizable(false);

	            ImageIcon icon = new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT));
	            setIconImage(icon.getImage());

	            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
	            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	            JLabel usernameLabel = new JLabel("Username:");
	            usernameField = new JTextField(20);
	            panel.add(usernameLabel);
	            panel.add(usernameField);

	            JLabel passwordLabel = new JLabel("Password:");
	            passwordField = new JPasswordField(20);
	            panel.add(passwordLabel);
	            panel.add(passwordField);

	            JButton loginButton = new JButton("Login");
	            loginButton.addActionListener(new LoginListener());
	            panel.add(loginButton);

	            add(panel);
	            pack();
	            setLocationRelativeTo(null);
	            setVisible(true);
	        }

	        class LoginListener implements ActionListener {
	            public void actionPerformed(ActionEvent e) {
	                String usernameInput = usernameField.getText();
	                String passwordInput = new String(passwordField.getPassword());

	                if (usernameInput.equals(USERNAME) && passwordInput.equals(PASSWORD)) {
	                    dispose();
	                    new ATMFrame(); 
	                } else {
	                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    }

	    static class ATMFrame extends JFrame {
	        private BankAccount bankAccount;
	        private JTextArea displayArea;

	        ATMFrame() {
	            super("ATM Machine");
	            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            setResizable(false);

	            bankAccount = new BankAccount();

	            JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
	            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	            JLabel welcomeLabel = new JLabel("Welcome to SBI ATM");
	            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	            panel.add(welcomeLabel);

	            displayArea = new JTextArea(10, 40);
	            displayArea.setEditable(false);
	            panel.add(new JScrollPane(displayArea));

	            JButton withdrawButton = new JButton("Withdraw");
	            withdrawButton.addActionListener(new WithdrawListener());
	            panel.add(withdrawButton);

	            JButton depositButton = new JButton("Deposit");
	            depositButton.addActionListener(new DepositListener());
	            panel.add(depositButton);

	            JButton balanceButton = new JButton("Check Balance");
	            balanceButton.addActionListener(new BalanceListener());
	            JButton exitButton = new JButton("Exit");
	            exitButton.addActionListener(e -> System.exit(0));
	            panel.add(exitButton);
	            panel.add(balanceButton);

	            add(panel);
	            pack();
	            setLocationRelativeTo(null);
	            setVisible(true);
	        }

	        class WithdrawListener implements ActionListener {
	            public void actionPerformed(ActionEvent e) {
	                String enteredPin = JOptionPane.showInputDialog(ATMFrame.this, "Enter your PIN code:");
	                if (enteredPin != null && enteredPin.equals(PIN)) {
	                    String amountStr = JOptionPane.showInputDialog(ATMFrame.this, "Enter amount to withdraw:");
	                    try {
	                        double amount = Double.parseDouble(amountStr);
	                        if (amount <= 0) {
	                            displayArea.setText("Please enter a valid amount");
	                            return;
	                        }
	                        if (bankAccount.getBalance() < amount) {
	                            displayArea.setText("Insufficient balance");
	                            return;
	                        }
	                        bankAccount.withdraw(amount);
	                        displayArea.setText("Withdrawal successful");
	                    } catch (NumberFormatException ex) {
	                        displayArea.setText("Please enter a valid amount");
	                    }
	                } else {
	                    displayArea.setText("Incorrect PIN");
	                }
	            }
	        }

	        class DepositListener implements ActionListener {
	            public void actionPerformed(ActionEvent e) {
	                String enteredPin = JOptionPane.showInputDialog(ATMFrame.this, "Enter your PIN code:");
	                if (enteredPin != null && enteredPin.equals(PIN)) {
	                    String amountStr = JOptionPane.showInputDialog(ATMFrame.this, "Enter amount to deposit:");
	                    try {
	                        double amount = Double.parseDouble(amountStr);
	                        if (amount <= 0) {
	                            displayArea.setText("Please enter a valid amount");
	                            return;
	                        }
	                        bankAccount.deposit(amount);
	                        displayArea.setText("Deposit successful");
	                    } catch (NumberFormatException ex) {
	                        displayArea.setText("Please enter a valid amount");
	                    }
	                } else {
	                    displayArea.setText("Incorrect PIN");
	                }
	            }
	        }

	        class BalanceListener implements ActionListener {
	            public void actionPerformed(ActionEvent e) {
	                String enteredPin = JOptionPane.showInputDialog(ATMFrame.this, "Enter your PIN code:");
	                if (enteredPin != null && enteredPin.equals(PIN)) {
	                    displayArea.setText("Your balance is: $" + bankAccount.getBalance());
	                } else {
	                    displayArea.setText("Incorrect PIN");
	                }
	            }
	        }
	    }

	    static class BankAccount {
	        private double balance;

	        BankAccount() {
	            balance = 0;
	        }

	        public double getBalance() {
	            return balance;
	        }

	        public void deposit(double amount) {
	            balance += amount;
	        }

	        public void withdraw(double amount) {
	            balance -= amount;
	        }
	    }
}