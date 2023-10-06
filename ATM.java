// ATM.java
// Represents an automated teller machine
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Authenticator;
import javax.swing.*;
import javax.swing.ImageIcon;

public class ATM extends JFrame
{
   private Color  atmBlue  = new Color(79, 178, 209);
   private Color  choiceBlue  = new Color(63, 139, 166);
   private Color  choiceRed  = new Color(201, 18, 18);

   private JButton[] buttons; // array of buttons
   private static final String[] names =
           { "1", "2", "3", "4", "5", "6","7","8","9",".","0","00" };

   private JButton[] leftButtons; // array of buttons
   private JPanel leftButtonJPanel; // panel to hold buttons

   private JButton[] rightButtons; // array of buttons
   private JPanel rightButtonJPanel; // panel to hold buttons

   private JButton[] extraButtons; // array of buttons
   private JPanel extraButtonJPanel; // panel to hold buttons

   private JButton[] checkButtons; // array of buttons
   private JPanel checkButtonJPanel; // panel to hold buttons

   private JPanel buttonJPanel; // panel to hold buttons

   private String line1 = "";
   private String linePass = "";

   private JPanel accountPanel;
   private JPanel accountPanelBlank1 = new JPanel();
   private JPanel accountPanelBlank2 = new JPanel();
   private JPanel accountPanel1 = new JPanel();
   private JPanel accountPanel2 = new JPanel();
   private JPanel accountPanel3 = new JPanel();
   private JPanel accountPanel4 = new JPanel();
   private JPanel accountPanel5 = new JPanel();
   private JPanel accountPanel6 = new JPanel();
   private JPanel accountPanel7 = new JPanel();
   private JPanel accountPanel8 = new JPanel();


   private JPanel savingPanel;
   private JPanel chequePanel;

   private int stepCount =0;
   private int index = -1;
   private int accountindex = -1;

   private int accountNumberInput = -1;//for enter the amount
   private String pinInput;//for enter the amount
   private ButtonHandler handler = new ButtonHandler();

   private JLabel label = new JLabel();
   private boolean chooseAccount = false;
   private int withdrawalconfirm = 0;
   private boolean takeCard = false;
   private boolean takeMoney = false;
   private boolean cancelPressed = false;

   private JPanel subPanel = new JPanel();
   private JPanel subPanel2 = new JPanel();
   private JPanel subPanel3 = new JPanel();

   private JTextField passTextField = new JTextField(50); //text box for the account number
   private JPasswordField passwordText = new JPasswordField(); //text box for the PIN

   private boolean checkCardStatus = false; //insert card or not
   private boolean enterPassReturn = false; //waiting user enter account number
   private boolean enterPINReturn = false; //waiting user enter PIN
   private boolean invalidconfirm = false;  //waiting user to confirm the wrong message
   private boolean enteReturn = false; //waiting user to press enter
   private boolean chooseSaving = false; //waiting user choose the options in saving account menu
   private boolean chooseCheque = false; //waiting user choose the options in cheque account menu

   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database
   private int x; //store is it saving or cheque account

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   private static final int EXIT = 4;

   // no-argument ATM constructor initializes instance variables
   public ATM()
   {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database

      JFrame frame = new JFrame();
      frame.setTitle("ATM");
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setBounds(200,150,1000,1000);//set size
      frame.setLayout(new BorderLayout(10,10));
      frame.setVisible(true);

      accountPanelBlank1.setBackground(atmBlue);
      accountPanelBlank2.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel2.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel4.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);
      accountPanel6.setBackground(atmBlue);
      accountPanel7.setBackground(atmBlue);
      accountPanel8.setBackground(atmBlue);

      buttons = new JButton[names.length]; // create array of JButtons
      buttonJPanel = new JPanel(); // set up panel
      buttonJPanel.setLayout(new GridLayout(4, buttons.length, 3, 3));

      leftButtons = new JButton[4]; // create array of JButtons
      leftButtonJPanel = new JPanel(); // set up panel
      leftButtonJPanel.setLayout(new GridLayout(5, 1, 0, 10));

      JLabel blankLabel = new JLabel();
      leftButtonJPanel.add(blankLabel);

      for (int count = 0; count < 4; count++) {
         leftButtons[count] = new JButton();
         leftButtonJPanel.add(leftButtons[count]); // add button to JFrame
      } // end for

      leftButtons[0].setActionCommand("B0");
      leftButtons[0].setText(">>");
      leftButtons[1].setActionCommand("B1");
      leftButtons[1].setText(">>");
      leftButtons[2].setActionCommand("B2");
      leftButtons[2].setText(">>");
      leftButtons[3].setActionCommand("B3");
      leftButtons[3].setText(">>");

      rightButtons = new JButton[4]; // create array of JButtons
      rightButtonJPanel = new JPanel(); // set up panel
      rightButtonJPanel.setLayout(new GridLayout(5, 1, 0, 10));
      JLabel blankLabel1 = new JLabel();
      rightButtonJPanel.add(blankLabel1);

      for (int count = 0; count < 4; count++) {
         rightButtons[count] = new JButton();
         rightButtonJPanel.add(rightButtons[count]); // add button to JFrame
      } // end for

      rightButtons[0].setActionCommand("B4");
      rightButtons[0].setText("<<");
      rightButtons[1].setActionCommand("B5");
      rightButtons[1].setText("<<");
      rightButtons[2].setActionCommand("B6");
      rightButtons[2].setText("<<");
      rightButtons[3].setActionCommand("B7");
      rightButtons[3].setText("<<");

      extraButtons = new JButton[3]; // create array of JButtons
      extraButtonJPanel = new JPanel(); // set up panel
      extraButtonJPanel.setLayout(new GridLayout(3, 1, 0, 3));

      for (int count = 0; count < 3; count++) {
         extraButtons[count] = new JButton();
         extraButtonJPanel.add(extraButtons[count]); // add button to JFrame
      } // end for

      extraButtons[0].setActionCommand("Cancel");
      extraButtons[0].setText("Cancel");
      extraButtons[0].setBackground(Color.red);
      extraButtons[1].setActionCommand("\n");
      extraButtons[1].setText("Enter");
      extraButtons[1].setBackground(Color.green);
      extraButtons[2].setText("Clear");
      extraButtons[2].setText("Clear");
      extraButtons[2].setBackground(Color.yellow);

      checkButtons = new JButton[2]; // create array of JButtons
      checkButtonJPanel = new JPanel(); // set up panel
      checkButtonJPanel.setLayout(new GridLayout(2, 1, 0, 3));

      for (int count = 0; count < 2; count++) {
         checkButtons[count] = new JButton();
         checkButtonJPanel.add(checkButtons[count]); // add button to JFrame
      } // end for
      checkButtons[0].setActionCommand("Card");
      checkButtons[0].setText("card");
      checkButtons[1].setActionCommand("Money");
      checkButtons[1].setText("money");

      for (int count = 0; count < names.length; count++) {
         buttons[count] = new JButton(names[count]);
         buttonJPanel.add(buttons[count]); // add button to JFrame
      } // end for

      frame.add(subPanel, BorderLayout.SOUTH);
      frame.add(subPanel2, BorderLayout.CENTER);
      frame.add(checkButtonJPanel, BorderLayout.EAST);

      subPanel2.setLayout(new BorderLayout());

      welcome();

      subPanel2.add(leftButtonJPanel, BorderLayout.WEST);
      subPanel2.add(rightButtonJPanel, BorderLayout.EAST);

      subPanel.setLayout(new BorderLayout(20, 10));
      subPanel.add(buttonJPanel, BorderLayout.CENTER);
      subPanel.add(extraButtonJPanel, BorderLayout.EAST);

      // register event handlers
      for (int i = 0; i <= 11; i++) {
         buttons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 3; i++) {
         leftButtons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 3; i++) {
         rightButtons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 2; i++) {
         extraButtons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 1; i++) {
         checkButtons[i].addActionListener(handler);
      }
      frame.validate();
      frame.pack();
   } // end no-argument ATM constructor

   public void welcome(){ //welcome page
      subPanel3.setLayout(new BorderLayout());
      label.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("1.JPG")).getImage().getScaledInstance(700, 800, Image.SCALE_SMOOTH)));
      label.setBackground(atmBlue);
      label.setOpaque(true);
      subPanel3.add(label, BorderLayout.CENTER);
      subPanel2.add(subPanel3, BorderLayout.CENTER);
      subPanel3.setVisible(true);
      subPanel2.revalidate();
   }

   // start ATM
   public void run()
   {
      // welcome and authenticate user; perform transactions
      while ( true )
      {
         // loop while user is not yet authenticated
         while ( !userAuthenticated )
         {
            welcome();
            cancelPressed = false; //initialize cancelPressed
            index = -1; //initialize index
            withdrawalconfirm = 0;//initalize withdrawal count
            screen.displayMessageLine( "\nWelcome!" );
            while(checkCardStatus == false){ //stop when the card is not inserted
               try {
                  Thread.sleep(100);
               } catch (InterruptedException ex) {
               }
            }
            authenticateUser(); // authenticate user
         } // end while

         performAccount(); // user is now authenticated

         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         goodbye();//set time
         screen.displayMessageLine( "\nThank you! Goodbye!" );
      } // end while
   } // end method run

   public void resetButtons(){
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.WEST));
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.EAST));
      BorderLayout layout1 = (BorderLayout)subPanel.getLayout();
      subPanel.remove(layout1.getLayoutComponent(BorderLayout.EAST));
      subPanel.remove(layout1.getLayoutComponent(BorderLayout.CENTER));

      leftButtons = new JButton[4]; // create array of JButtons
      leftButtonJPanel = new JPanel(); // set up panel
      leftButtonJPanel.setLayout(new GridLayout(5, 1, 0, 10));
      JLabel blankLabel = new JLabel();
      leftButtonJPanel.add(blankLabel);
      for (int count = 0; count < 4; count++) {
         leftButtons[count] = new JButton();
         leftButtonJPanel.add(leftButtons[count]); // add button to JFrame
      } // end for
      leftButtons[0].setActionCommand("B0");
      leftButtons[0].setText(">>");
      leftButtons[1].setActionCommand("B1");
      leftButtons[1].setText(">>");
      leftButtons[2].setActionCommand("B2");
      leftButtons[2].setText(">>");
      leftButtons[3].setActionCommand("B3");
      leftButtons[3].setText(">>");

      rightButtons = new JButton[4]; // create array of JButtons
      rightButtonJPanel = new JPanel(); // set up panel
      rightButtonJPanel.setLayout(new GridLayout(5, 1, 0, 10));
      JLabel blankLabel1 = new JLabel();
      rightButtonJPanel.add(blankLabel1);
      for (int count = 0; count < 4; count++) {
         rightButtons[count] = new JButton();
         rightButtonJPanel.add(rightButtons[count]); // add button to JFrame
      } // end for

      rightButtons[0].setActionCommand("B4");
      rightButtons[0].setText("<<");
      rightButtons[1].setActionCommand("B5");
      rightButtons[1].setText("<<");
      rightButtons[2].setActionCommand("B6");
      rightButtons[2].setText("<<");
      rightButtons[3].setActionCommand("B7");
      rightButtons[3].setText("<<");

      extraButtons = new JButton[3]; // create array of JButtons
      extraButtonJPanel = new JPanel(); // set up panel
      extraButtonJPanel.setLayout(new GridLayout(3, 1, 0, 3));
      for (int count = 0; count < 3; count++) {
         extraButtons[count] = new JButton();
         extraButtonJPanel.add(extraButtons[count]); // add button to JFrame
      } // end for
      extraButtons[0].setActionCommand("Cancel");
      extraButtons[0].setText("Cancel");
      extraButtons[0].setBackground(Color.red);
      extraButtons[1].setActionCommand("\n");
      extraButtons[1].setText("Enter");
      extraButtons[1].setBackground(Color.green);
      extraButtons[2].setText("Clear");
      extraButtons[2].setText("Clear");
      extraButtons[2].setBackground(Color.yellow);

      buttons = new JButton[names.length]; // create array of JButtons
      buttonJPanel = new JPanel(); // set up panel
      buttonJPanel.setLayout(new GridLayout(4, buttons.length, 3, 3));
      for (int count = 0; count < names.length; count++) {
         buttons[count] = new JButton(names[count]);
         buttonJPanel.add(buttons[count]); // add button to JFrame
      } // end for

      subPanel2.add(leftButtonJPanel, BorderLayout.WEST);
      subPanel2.add(rightButtonJPanel, BorderLayout.EAST);
      subPanel.add(extraButtonJPanel, BorderLayout.EAST);
      subPanel.add(buttonJPanel, BorderLayout.CENTER);

      handler = new ButtonHandler();
      for (int i = 0; i <= 11; i++) {
         buttons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 3; i++) {
         leftButtons[i].addActionListener(handler);
         rightButtons[i].addActionListener(handler);
      }
      for (int i = 0; i <= 2; i++) {
         extraButtons[i].addActionListener(handler);
      }

      subPanel2.revalidate();
      subPanel.revalidate();
   }

   // attempts to authenticate user against database
   private void authenticateUser()
   {
      pass(); //Showing the page which entering PIN
      while(enterPassReturn == true) { //Stop until the user enters the account and presses "Enter"
         try {
            Thread.sleep(100);
         } catch (InterruptedException ex) {
         }
      }
      screen.displayMessage( "\nPlease enter your account number: " );
      int accountNumber = accountNumberInput; //get the account number from gui
      System.out.println(accountNumber); //display in terminal

      //If user press cancel, leave the page of entering account number
      if(cancelPressed == true){
         enterPINReturn = false;
         accountNumber = 0;
         userAuthenticated = true;
      }
      else
         enterPINReturn = true;

      screen.displayMessage( "\nEnter your PIN: " ); // prompt for PIN

      while(enterPINReturn == true) { //Stop until the user enters the PIN and presses "Enter"
         try {
            Thread.sleep(100);
         } catch (InterruptedException ex) {
         }
      }

      System.out.println(pinInput); //display in terminal

      int pin;
      //If user press cancel, leave the page of entering PIN
      if(cancelPressed == true && enterPINReturn == false){
         pin = 0;
         userAuthenticated = true;
      }
      else{ //
         pin = Integer.parseInt(pinInput); //get the PIN from gui
         System.out.println(pinInput);
         // set userAuthenticated to boolean value returned by database
         userAuthenticated =
                 bankDatabase.authenticateUser( accountNumber*1000+1, pin );
      }

      // check whether authentication succeeded
      if ( userAuthenticated )
      {
         currentAccountNumber = accountNumber; // save user's account #
      } // end if
      else {
         screen.displayMessageLine(
                 "Invalid account number or PIN. Please try again.");
         invalidconfirm = true;
         invalidpin(); //Show the wrong message
         do {
            try {
               Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
         }while(invalidconfirm);
         BorderLayout layout = (BorderLayout)subPanel2.getLayout();
         subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
         checkCardStatus = false; //return the card
         checkButtons[0].setBackground(null); //off the red light of "card" buttion
      }
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions()
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      boolean userExited = false; // user has not chosen to exit
      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case TRANSFER:
               // initialize as new object of chosen type
               currentTransaction =
                       createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction

               //determine the user press cancel or not during withdrawing or transfering money
               if(mainMenuSelection == WITHDRAWAL || mainMenuSelection == TRANSFER){
                  currentTransaction.takevalue();
                  System.out.println(currentTransaction.takevalue());
               }
               resetButtons();
               enteReturn = true;
               while(enteReturn == true){
                  try {
                     Thread.sleep(100);
                  } catch (InterruptedException ex) {
                  }
               }
               //let user choose to print the advice after withdrawing money
               if(mainMenuSelection == 2){
                  withdrawalconfirm = 1;
                  while(withdrawalconfirm == 1){
                     try {
                        if(currentTransaction.takevalue() == 1)
                           withdrawalconfirm = 2;
                        else
                           advice();
                        Thread.sleep(100);
                     } catch (InterruptedException ex) {
                     }
                  }

                  if(currentTransaction.takevalue() == 0) {
                     //Asking user take the card
                     takeCard = true;
                     while(takeCard ==true){
                        try {
                           CardAndMoney();
                           Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                     }
                     //Asking user take the money
                     takeMoney = true;
                     while(takeMoney ==true){
                        try {
                           CardAndMoney();
                           Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                     }
                  }
                  //return the card after the user press cancel
                  else if(currentTransaction.takevalue() == 1) {
                     checkCardStatus = false;
                     checkButtons[0].setBackground(null);
                  }
               }
               //let user choose to print the advice after transfering money
               if(mainMenuSelection == 3) {
                  withdrawalconfirm = 1;
                  while(withdrawalconfirm == 1) {
                     try {
                        if (currentTransaction.takevalue() == 1)
                           withdrawalconfirm = 2;
                        else
                           advice();
                        Thread.sleep(100);
                     } catch (InterruptedException ex) {
                     }
                  }
                  reset();
                  checkCardStatus = false;
                  checkButtons[0].setBackground(null);
               }

               break;

            case EXIT: // user chose to terminate session

               screen.displayMessageLine( "\nExiting the system..." );
               userExited = true; // this ATM session should end
               currentAccountNumber=(currentAccountNumber-x)/1000;

               break;

            default: // user did not enter an integer from 1-4
            {
               invalidMessage();
               enteReturn = true;
               while(enteReturn == true){
                  try {
                     Thread.sleep(100);
                  } catch (InterruptedException ex) {

                  }
               }
               screen.displayMessageLine(
                       "\nYou did not enter a valid selection. Try again.");

            }
               break;
         } // end switch
      } // end while
   } // end method performTransactions

   private void performAccount()//display account menu
   {

      boolean userExited = false; // user has not chosen to exit
      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
         x = displayAccount();
         switch (x) {
            case 1, 2 -> {
               currentAccountNumber =currentAccountNumber*1000+x;
               performTransactions();
            }
            // user chose to terminate session
            case 3 -> {
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end

            }
            default -> {
               invalidMessage();
               enteReturn = true;
               while(enteReturn == true){
                  try {
                     Thread.sleep(100);
                  } catch (InterruptedException ex) {

                  }
               }
               screen.displayMessageLine(
                       "\nYou did not enter a valid selection. Try again.");

            }
         } // end switch
      } // end while
   } // end method performTransactions

   private int displayMainMenu()
   {
      reset();
      //return exit when the user withdrew the money or pressed cancel
      if(withdrawalconfirm == 2 || cancelPressed == true)
         return 4;
      //showing the menu of saving account or cheque account
      if(accountindex == 1)
         savingMenu();
      else if(accountindex == 2)
         chequeMenu();

      screen.displayMessageLine( "\nMain menu:" );
      screen.displayMessageLine( "1 - View my balance" );
      screen.displayMessageLine( "2 - Withdraw cash" );
      screen.displayMessageLine( "3 - Transfer" );
      screen.displayMessageLine( "4 - Exit\n" );
      screen.displayMessage( "Enter a choice: " );

      if(accountindex == 1){
         while(chooseSaving == true) {//stop until user choose the options
            try {
               Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
         }
      }
      else if(accountindex == 2){
         while(chooseCheque == true) {//stop until user choose the options
            try {
               Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
         }
      }
      return index;// return user's selection
   } // end method displayMainMenu

   private int displayAccount()
   {
      //return exit when the user withdrew the money or pressed cancel
      if(withdrawalconfirm == 2 || cancelPressed == true)
         return 3;

      screen.displayMessageLine( "\nAccount menu:" );
      screen.displayMessageLine( "1 - View my Saving account" );
      screen.displayMessageLine( "2 - View my Cheque account" );
      screen.displayMessageLine( "3 - Exit\n" );
      screen.displayMessage( "Enter a choice: " );

      while(chooseAccount == true || index == -1) {//stop until user choose the options
         try {
            accountMenu();
            Thread.sleep(100);
         } catch (InterruptedException ex) {
         }
      }
      accountindex = index; //record the choice of account
      return index;
   } // end method displayMainMenu


   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
           temp = new BalanceInquiry(
                    currentAccountNumber, screen, bankDatabase,subPanel2 );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen,
                    bankDatabase, keypad, cashDispenser ,subPanel2, subPanel);
            break;
         case TRANSFER: // create new Transfer transaction
            temp = new Transfer( currentAccountNumber, screen,
                    bankDatabase, keypad, subPanel2,subPanel);
            break;
      } // end switch
      return temp; // return the newly created object
   } // end method createTransaction

   //GUI of entering account number and PIN
   public void pass(){
      enterPassReturn = true;
      reset();
      JPanel userPanel = new JPanel();
      userPanel.setLayout(new GridBagLayout());
      userPanel.setBackground(atmBlue);

      accountPanel1.setBackground(choiceBlue);
      accountPanel2.setBackground(choiceBlue);

      JLabel messageLabel1 = new JLabel();
      messageLabel1.setText("Step 1 Please type in the account name and PIN");
      messageLabel1.setFont(new Font("Serif", Font.PLAIN, 30));
      messageLabel1.setHorizontalAlignment(JLabel.CENTER);
      messageLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel messageLabel2 = new JLabel();
      messageLabel2.setText("Step 2 then press \"Enter\" to correct press \"Clear\"");
      messageLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      messageLabel2.setHorizontalAlignment(JLabel.CENTER);
      messageLabel2.setVerticalAlignment(JLabel.CENTER);

      JLabel userLabel = new JLabel();
      userLabel.setText("Account Number");
      userLabel.setFont(new Font("Serif", Font.PLAIN, 25));
      userLabel.setHorizontalAlignment(JLabel.RIGHT);
      userLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel passwordLabel = new JLabel();
      passwordLabel.setText("PIN");
      passwordLabel.setFont(new Font("Serif", Font.PLAIN, 25));
      passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
      passwordLabel.setVerticalAlignment(JLabel.CENTER);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(messageLabel1);
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(messageLabel2);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(userLabel);
      accountPanel2.setLayout(new BorderLayout());
      accountPanel2.add(passwordLabel);

      GridBagConstraints c0 = new GridBagConstraints();
      c0.insets = new Insets(0,0,0,0);
      c0.gridx = 0;
      c0.gridy = 0;
      c0.gridwidth = 4;
      c0.gridheight = 1;
      c0.weightx = 0.47;
      c0.weighty = 0.47;
      c0.fill = GridBagConstraints.BOTH;
      c0.anchor = GridBagConstraints.WEST;
      userPanel.add(accountPanelBlank1, c0);

      GridBagConstraints c1 = new GridBagConstraints();
      c1.insets = new Insets(0,0,0,0);
      c1.gridx = 0;
      c1.gridy = 1;
      c1.gridwidth = 4;
      c1.gridheight = 1;
      c1.weightx = 0.47;
      c1.weighty = 0.47;
      c1.fill = GridBagConstraints.BOTH;
      c1.anchor = GridBagConstraints.WEST;
      userPanel.add(accountPanelBlank2, c1);

      GridBagConstraints c2 = new GridBagConstraints();
      c2.insets = new Insets(0,0,0,5);
      c2.gridx = 0;
      c2.gridy = 2;
      c2.gridwidth = 1;
      c2.gridheight = 1;
      c2.weightx = 0.50;
      c2.weighty = 0.50;
      c2.fill = GridBagConstraints.HORIZONTAL;
      c2.anchor = GridBagConstraints.WEST;
      userPanel.add(accountPanel1, c2);

      // declaration of textField for displaying output
      passTextField.setEditable(false);
      passTextField.setText(line1);  // display line1 in textField
      line1 = "";
      passTextField.setText("");

      GridBagConstraints c3 = new GridBagConstraints();
      c3.insets = new Insets(0,0,0,0);
      c3.gridx = 1;
      c3.gridy = 2;
      c3.gridwidth = 3;
      c3.gridheight = 1;
      c3.weightx = 0.50;
      c3.weighty = 0.50;
      c3.fill = GridBagConstraints.HORIZONTAL;
      c3.anchor = GridBagConstraints.CENTER;
      userPanel.add(passTextField, c3);

      GridBagConstraints c4 = new GridBagConstraints();
      c4.insets = new Insets(0,0,0,5);
      c4.gridx = 0;
      c4.gridy = 3;
      c4.gridwidth = 1;
      c4.gridheight = 1;
      c4.weightx = 0.47;
      c4.weighty = 0.47;
      c4.fill = GridBagConstraints.HORIZONTAL;
      c4.anchor = GridBagConstraints.WEST;
      userPanel.add(accountPanel2, c4);

      passwordText.setEditable(false);
      passwordText.setText(linePass);  // display line1 in textField
      linePass = "";
      passwordText.setText("");

      GridBagConstraints c5 = new GridBagConstraints();
      c5.insets = new Insets(0,0,0,0);
      c5.gridx = 1;
      c5.gridy = 3;
      c5.gridwidth = 3;
      c5.gridheight = 1;
      c5.weightx = 0.47;
      c5.weighty = 0.47;
      c5.fill = GridBagConstraints.HORIZONTAL;
      c5.anchor = GridBagConstraints.WEST;
      userPanel.add(passwordText, c5);


      subPanel2.add(userPanel, BorderLayout.CENTER);
      userPanel.setVisible(true);
      subPanel2.revalidate();
   }

   //GUI of account menu
   public void accountMenu(){
      chooseAccount = true;
      reset();
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      accountPanel = new JPanel();
      accountPanel.setLayout(new GridLayout(5,2,10,10));

      accountPanel.setBackground(atmBlue);
      accountPanel1.setBackground(choiceBlue);
      accountPanel2.setBackground(choiceBlue);

      JLabel accountLabel = new JLabel();
      accountLabel.setText("Account");
      accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel.setHorizontalAlignment(JLabel.RIGHT);
      accountLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel accountLabel1 = new JLabel();
      accountLabel1.setText("Menu");
      accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel1.setHorizontalAlignment(JLabel.LEFT);
      accountLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel savingLabel = new JLabel();
      savingLabel.setText("Saving account");
      savingLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      savingLabel.setHorizontalAlignment(JLabel.CENTER);
      savingLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel chequeLabel = new JLabel();
      chequeLabel.setText("Cheque account");
      chequeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      chequeLabel.setHorizontalAlignment(JLabel.CENTER);
      chequeLabel.setVerticalAlignment(JLabel.CENTER);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(accountLabel);
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(accountLabel1);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(savingLabel);
      accountPanel2.setLayout(new BorderLayout());
      accountPanel2.add(chequeLabel);

      accountPanel.add(accountPanelBlank1);
      accountPanel.add(accountPanelBlank2);
      accountPanel.add(accountPanel1);
      accountPanel.add(accountPanel2);
      accountPanel.add(accountPanel3);
      accountPanel.add(accountPanel4);
      accountPanel.add(accountPanel5);
      accountPanel.add(accountPanel6);
      accountPanel.add(accountPanel7);
      accountPanel.add(accountPanel8);

      subPanel2.add(accountPanel, BorderLayout.CENTER);
      accountPanel.setVisible(true);
      subPanel2.revalidate();
   }

   //GUI of saving account's menu
   public void savingMenu(){
      chooseSaving = true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      savingPanel = new JPanel();
      savingPanel.setLayout(new GridLayout(5,2,10,10));
      savingPanel.setBackground(atmBlue);

      accountPanel1.setBackground(choiceBlue);
      accountPanel3.setBackground(choiceBlue);
      accountPanel5.setBackground(choiceBlue);

      JLabel accountLabel = new JLabel();
      accountLabel.setText("Saving");
      accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel.setHorizontalAlignment(JLabel.RIGHT);
      accountLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel accountLabel1 = new JLabel();
      accountLabel1.setText("Menu");
      accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel1.setHorizontalAlignment(JLabel.LEFT);
      accountLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel balanceLabel = new JLabel();
      balanceLabel.setText("View My Balance");
      balanceLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      balanceLabel.setHorizontalAlignment(JLabel.CENTER);
      balanceLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel withdrawLabel = new JLabel();
      withdrawLabel.setText("Withdraw Cash");
      withdrawLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      withdrawLabel.setHorizontalAlignment(JLabel.CENTER);
      withdrawLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel transferLabel = new JLabel();
      transferLabel.setText("Transfer");
      transferLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      transferLabel.setHorizontalAlignment(JLabel.CENTER);
      transferLabel.setVerticalAlignment(JLabel.CENTER);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(accountLabel);
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(accountLabel1);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(balanceLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(withdrawLabel);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(transferLabel);


      savingPanel.add(accountPanelBlank1);
      savingPanel.add(accountPanelBlank2);
      savingPanel.add(accountPanel1);
      savingPanel.add(accountPanel2);
      savingPanel.add(accountPanel3);
      savingPanel.add(accountPanel4);
      savingPanel.add(accountPanel5);
      savingPanel.add(accountPanel6);
      savingPanel.add(accountPanel7);
      savingPanel.add(accountPanel8);

      subPanel2.add(savingPanel, BorderLayout.CENTER);
      savingPanel.setVisible(true);
      subPanel2.revalidate();
   }

   //GUI of cheque account's menu
   public void chequeMenu(){
      chooseCheque = true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      chequePanel = new JPanel();
      chequePanel.setLayout(new GridLayout(5,2,10,10));
      chequePanel.setBackground(atmBlue);

      accountPanel1.setBackground(choiceBlue);
      accountPanel3.setBackground(choiceBlue);
      accountPanel5.setBackground(choiceBlue);

      JLabel accountLabel = new JLabel();
      accountLabel.setText("Cheque");
      accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel.setHorizontalAlignment(JLabel.RIGHT);
      accountLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel accountLabel1 = new JLabel();
      accountLabel1.setText("Menu");
      accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      accountLabel1.setHorizontalAlignment(JLabel.LEFT);
      accountLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel balanceLabel = new JLabel();
      balanceLabel.setText("View My Balance");
      balanceLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      balanceLabel.setHorizontalAlignment(JLabel.CENTER);
      balanceLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel withdrawLabel = new JLabel();
      withdrawLabel.setText("Withdraw Cash");
      withdrawLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      withdrawLabel.setHorizontalAlignment(JLabel.CENTER);
      withdrawLabel.setVerticalAlignment(JLabel.CENTER);

      JLabel transferLabel = new JLabel();
      transferLabel.setText("Transfer");
      transferLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      transferLabel.setHorizontalAlignment(JLabel.CENTER);
      transferLabel.setVerticalAlignment(JLabel.CENTER);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(accountLabel);
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(accountLabel1);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(balanceLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(withdrawLabel);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(transferLabel);

      chequePanel.add(accountPanelBlank1);
      chequePanel.add(accountPanelBlank2);
      chequePanel.add(accountPanel1);
      chequePanel.add(accountPanel2);
      chequePanel.add(accountPanel3);
      chequePanel.add(accountPanel4);
      chequePanel.add(accountPanel5);
      chequePanel.add(accountPanel6);
      chequePanel.add(accountPanel7);
      chequePanel.add(accountPanel8);

      subPanel2.add(chequePanel, BorderLayout.CENTER);
      chequePanel.setVisible(true);
      subPanel2.revalidate();
   }

   //remove the panel which are showing
   private void reset(){

      accountPanelBlank1.removeAll();
      accountPanelBlank2.removeAll();
      accountPanel1.removeAll();
      accountPanel2.removeAll();
      accountPanel3.removeAll();
      accountPanel4.removeAll();
      accountPanel5.removeAll();
      accountPanel6.removeAll();
      accountPanel7.removeAll();
      accountPanel8.removeAll();
      accountPanelBlank1.setBackground(atmBlue);
      accountPanelBlank2.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel2.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel4.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);
      accountPanel6.setBackground(atmBlue);
      accountPanel7.setBackground(atmBlue);
      accountPanel8.setBackground(atmBlue);

   }

   //GUI of wrong message
   public void invalidMessage(){
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel invalidPanel = new JPanel();
      invalidPanel.setLayout(new GridLayout(3,1));
      invalidPanel.setBackground(atmBlue);

      JLabel invalidLabel = new JLabel();
      invalidLabel.setText("Invalid selection.");
      invalidLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      invalidLabel.setHorizontalAlignment(JLabel.CENTER);
      invalidLabel.setVerticalAlignment(JLabel.CENTER);
      accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel1);

      JLabel invalidLabel2 = new JLabel();
      invalidLabel2.setText("Please try again.");
      invalidLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      invalidLabel2.setHorizontalAlignment(JLabel.CENTER);
      invalidLabel2.setVerticalAlignment(JLabel.CENTER);
      accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel3);

      JLabel invalidLabel3 = new JLabel();
      invalidLabel3.setText("Please press \"Enter\" to continue.");
      invalidLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      invalidLabel3.setHorizontalAlignment(JLabel.RIGHT);
      invalidLabel3.setVerticalAlignment(JLabel.CENTER);
      accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel5);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(invalidLabel);
      accountPanel1.setBackground(atmBlue);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(invalidLabel2);
      accountPanel3.setBackground(atmBlue);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(invalidLabel3);
      accountPanel5.setBackground(atmBlue);

      subPanel2.add(invalidPanel, BorderLayout.CENTER);
      invalidPanel.setVisible(true);
      subPanel2.revalidate();
      subPanel2.repaint();
   }

   //GUI of wrong pin message
   public void invalidpin(){
      reset();
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(3,1));
      errorPanel.setBackground(atmBlue);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Invalid account number or PIN");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 35));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel1 = new JLabel();
      errorLabel1.setText("Please try again");
      errorLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      errorLabel1.setHorizontalAlignment(JLabel.CENTER);
      errorLabel1.setVerticalAlignment(JLabel.CENTER);
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel1);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();
   }

   //GUI of printing advice
   public void advice(){
      reset();
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      JPanel advicePanel = new JPanel();
      advicePanel.setLayout(new GridLayout(5,2,10,10));
      advicePanel.setBackground(atmBlue);

      accountPanel3.setBackground(choiceBlue);
      accountPanel4.setBackground(choiceBlue);

      JLabel blankTitleLabel1 = new JLabel();
      blankTitleLabel1.setText("Your action");
      blankTitleLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      blankTitleLabel1.setHorizontalAlignment(JLabel.RIGHT);
      blankTitleLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel blankTitleLabel2 = new JLabel();
      blankTitleLabel2.setText("is processed.");
      blankTitleLabel2.setFont(new Font("Serif", Font.PLAIN, 35));
      blankTitleLabel2.setHorizontalAlignment(JLabel.LEFT);
      blankTitleLabel2.setVerticalAlignment(JLabel.CENTER);

      JLabel importantLabel1 = new JLabel();
      importantLabel1.setText("Please");
      importantLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
      importantLabel1.setHorizontalAlignment(JLabel.RIGHT);
      importantLabel1.setVerticalAlignment(JLabel.CENTER);

      JLabel importantLabel2 = new JLabel();
      importantLabel2.setText("Select");
      importantLabel2.setFont(new Font("Serif", Font.PLAIN, 35));
      importantLabel2.setHorizontalAlignment(JLabel.LEFT);
      importantLabel2.setVerticalAlignment(JLabel.CENTER);

      JLabel step1Label = new JLabel();
      step1Label.setText("Print Advice");
      step1Label.setFont(new Font("Serif", Font.PLAIN, 30));
      step1Label.setHorizontalAlignment(JLabel.CENTER);
      step1Label.setVerticalAlignment(JLabel.CENTER);

      JLabel step2Label = new JLabel();
      step2Label.setText("Without Advice");
      step2Label.setFont(new Font("Serif", Font.PLAIN, 30));
      step2Label.setHorizontalAlignment(JLabel.CENTER);
      step2Label.setVerticalAlignment(JLabel.CENTER);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(blankTitleLabel1);
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(blankTitleLabel2);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(importantLabel1);
      accountPanel2.setLayout(new BorderLayout());
      accountPanel2.add(importantLabel2);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(step1Label);
      accountPanel4.setLayout(new BorderLayout());
      accountPanel4.add(step2Label);
      accountPanel5.setLayout(new BorderLayout());

      advicePanel.add(accountPanelBlank1);
      advicePanel.add(accountPanelBlank2);
      advicePanel.add(accountPanel1);
      advicePanel.add(accountPanel2);
      advicePanel.add(accountPanel3);
      advicePanel.add(accountPanel4);

      subPanel2.add(advicePanel, BorderLayout.CENTER);
      advicePanel.setVisible(true);
      subPanel2.revalidate();
   }
   //GUI of asking user to take the card before taking the money
   public void CardAndMoney(){
      reset();
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      JPanel CardAndMoneyPanel = new JPanel();
      CardAndMoneyPanel.setLayout(new GridLayout(5,2,10,10));
      CardAndMoneyPanel.setBackground(atmBlue);

      JLabel CMLabel = new JLabel();

      if(takeCard == true){
         CMLabel .setText("Step 1: Please take your card.");
         CMLabel .setFont(new Font("Serif", Font.PLAIN, 40));
         CMLabel .setHorizontalAlignment(JLabel.CENTER);
         CMLabel .setVerticalAlignment(JLabel.CENTER);
         accountPanel1.setLayout(new BorderLayout());
         CardAndMoneyPanel.add(accountPanel1);
      }

      if(takeMoney == true){
         CMLabel.setText("Step 2: Please take the money.");
         CMLabel.setFont(new Font("Serif", Font.PLAIN, 40));
         CMLabel.setHorizontalAlignment(JLabel.CENTER);
         CMLabel.setVerticalAlignment(JLabel.CENTER);
         accountPanel1.setLayout(new BorderLayout());
         CardAndMoneyPanel.add(accountPanel1);
      }

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(CMLabel);

      CardAndMoneyPanel.add(accountPanelBlank1);
      CardAndMoneyPanel.add(accountPanelBlank2);
      CardAndMoneyPanel.add(accountPanel1);

      subPanel2.add(CardAndMoneyPanel, BorderLayout.CENTER);
      CardAndMoneyPanel.setVisible(true);
      subPanel2.revalidate();
   }

   //GUI of saying goodbye to user
   public void goodbye(){
      reset();
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(3,1));
      errorPanel.setBackground(atmBlue);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Thank You");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 35));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("Please using our ATM next time.");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 35));
      errorLabel2.setHorizontalAlignment(JLabel.CENTER);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      accountPanel2.setLayout(new BorderLayout());
      errorPanel.add(accountPanel2);

      JLabel errorLabel3 = new JLabel();
      errorLabel3.setText("Please wait 5 seconds!");
      errorLabel3.setFont(new Font("Serif", Font.PLAIN, 35));
      errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel3.setVerticalAlignment(JLabel.CENTER);
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel2.setLayout(new BorderLayout());
      accountPanel2.add(errorLabel2);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel3);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();
      try {
         Thread.sleep(5000);
      } catch (InterruptedException ex) {
      }
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
   }

   // inner class for button event handling
   public class ButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent event )
      {
         //the action of pressing "cancel" button.
         //leave the gui and return the card.
         if(event.getActionCommand().equals("Cancel") && (enterPassReturn == true || enterPINReturn == true || chooseAccount == true || chooseSaving == true || chooseCheque == true)){
            cancelPressed = true;
            enterPassReturn = false;
            enterPINReturn = false;
            if(chooseAccount == true)
               index = 3; //exit
            chooseAccount = false;
            if(chooseSaving == true || chooseCheque == true)
               index = 4; //exit
            chooseSaving = false;
            chooseCheque = false;
            checkCardStatus = false;
            checkButtons[0].setBackground(null);
         }
         else{// if user does not press the "cancel" button

            if(event.getSource().equals(checkButtons[0])){//"card" button
               subPanel3.setVisible(false);
               checkCardStatus = true;
               checkButtons[0].setBackground(choiceRed);
            }

            if(enteReturn == true  ){//balance equiry press enter to return
               if(event.getSource().equals(extraButtons[1]))
                  enteReturn = false;
            }

            if(chooseAccount == true && cancelPressed == false)//account menu button
            {
               if(event.getSource().equals(leftButtons[0])){//saving account button
                  index = 1;
                  chooseAccount = false;
               }
               else if(event.getSource().equals(rightButtons[0])){//cheque account button
                  index = 2;
                  chooseAccount = false;
               }
               else //other buttons
                  index = -1;
            }

            if(chooseSaving == true)//saving account's button
            {
               if(event.getSource().equals(leftButtons[0])){//balance equiry button
                  index = 1;
                  chooseSaving = false;
               }
               else if(event.getSource().equals(leftButtons[1])){//withdraw button
                  index = 2;
                  chooseSaving = false;
               }
               else if(event.getSource().equals(leftButtons[2])){//transfer button
                  index = 3;
                  chooseSaving = false;
               }
               else //other buttons
                  index = -1;
            }

            if(chooseCheque == true)//cheque account's button
            {
               if(event.getSource().equals(leftButtons[0])){//balance equiry button
                  index = 1;
                  chooseCheque = false;
               }
               else if(event.getSource().equals(leftButtons[1])){//withdraw button
                  index = 2;
                  chooseCheque = false;
               }
               else if(event.getSource().equals(leftButtons[2])){//transfer button
                  index = 3;
                  chooseCheque = false;
               }
               else //other buttons
                  index = -1;
            }

            if(withdrawalconfirm  == 1)//print advice's button
            {
               if(event.getSource().equals(leftButtons[1])){//print advice
                  withdrawalconfirm = 2;
               }
               else if(event.getSource().equals(rightButtons[1])){//not print advice
                  withdrawalconfirm = 2;
               }
            }

            if(takeCard == true)//take card button
            {
               if(event.getSource().equals(checkButtons[0])){//card click
                  checkButtons[0].setBackground(null);
                  checkCardStatus = false;
                  checkButtons[1].setBackground(choiceRed);
                  takeCard = false;
               }
            }

            if(takeMoney == true)//take money button
            {
               if(event.getSource().equals(checkButtons[1])){//card click
                  checkButtons[1].setBackground(null);
                  takeMoney = false;
               }
            }

            if(enterPassReturn == true) { //account number gui enter button
               for (int i = 0; i <= 11; i++) {
                  if ( event.getSource().equals(extraButtons[2])) {
                     passTextField.setText("");
                     line1 = "";
                  } else if ( event.getActionCommand().equals(names[i])) {
                     line1 = line1.concat(event.getActionCommand());
                     passTextField.setText(line1); // display line1 in jTextField
                  } else {
                     line1 = line1.concat("");
                  }

               }

               if (enterPassReturn == true && event.getSource().equals(extraButtons[1])) {
                  String input = passTextField.getText();//the input account name
                  accountNumberInput = Integer.parseInt(input);
                  enterPassReturn = false;
               }
            }

            //pin
            if(enterPINReturn == true) { //PIN gui enter button

               for (int i = 0; i <= 11; i++) {//pin gui
                  if (event.getSource().equals(extraButtons[2])) {
                     passwordText.setText("");
                     linePass = "";
                  } else if (event.getActionCommand().equals(names[i])) {
                     linePass = linePass.concat(event.getActionCommand());
                     passwordText.setText(linePass); // display line1 in jTextField
                  } else {
                     linePass = linePass.concat("");
                  }
               }
               if ( event.getSource().equals(extraButtons[1])) {
                  pinInput = passwordText.getText();//the input account name

                  enterPINReturn = false;
                  passwordText.setText("");
                  linePass = "";
                  passTextField.setText("");
                  line1 = "";
               }
            }
            if(invalidconfirm == true && event.getSource().equals(extraButtons[1])){
               invalidconfirm = false;
               reset();
            }
         }
      }
   }
} // end class ATM



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/