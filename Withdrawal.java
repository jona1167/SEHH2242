// Withdrawal.java
// Represents a withdrawal ATM transaction

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Withdrawal extends Transaction
{
   private static final String[] names =
           { "1", "2", "3", "4", "5", "6","7","8","9",".","0","00" };
   Color  atmBlue  = new Color(79, 178, 209);
   Color  choiceBlue  = new Color(63, 139, 166);
   private JButton[] leftButtons; // array of buttons
   private JPanel leftButtonJPanel; // panel to hold buttons

   private JButton[] rightButtons; // array of buttons
   private JPanel rightButtonJPanel; // panel to hold buttons

   private JButton[] extraButtons; // array of buttons
   private JPanel extraButtonJPanel; // panel to hold buttons

   private JButton[] buttons; // array of buttons
   private JPanel buttonJPanel; // panel to hold buttons

   private String lineamount = "";//for enter amount
   private JTextField enterTextField =new JTextField(50);

   private int amount; // amount to withdraw
   private double check =-1; // amount to withdraw

   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   private JPanel subPanel2;
   private JPanel subPanel;
   private ButtonHandler handlerX;
   private boolean withdrawalReturn = false;
   private boolean checkerror = false;
   private boolean enterGUIReturn = false;

   private int edit = -1;
   private int canceledNum = 0;


   // constant corresponding to menu option to cancel
   private final static int CANCELED = 5;
   // constant corresponding to wrong input amount
   private final static int WRONG = 6;
   // constant corresponding to error input amount
   private final static int ERROR = 7;
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      boolean checkCase = true;
      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 100, 500, 1000,0};

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {





         // display the menu with hkd
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - HKD100" );
         screen.displayMessageLine( "2 - HKD500" );
         screen.displayMessageLine( "3 - HKD1000" );
         screen.displayMessageLine( "4 - Enter the amounts" );
         screen.displayMessageLine( "5 - Cancel transaction" );
         screen.displayMessage( "\nChoose a withdrawal amount: " );



        withdrawalChooseGUI();
        //int input = keypad.getInput(); // get user input through keypad
         while(withdrawalReturn == true){
            try {
               Thread.sleep(100);
            } catch (InterruptedException ex) {

               }
         }


         // determine how to proceed based on the input value
         switch ( edit )
         {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 ), return the
            case 3: // corresponding amount from amounts array
            case 4:
               userChoice = amounts[ edit ];
               if(edit==4){ // used to find out user input 4 and let user to input
                  enterGUI();
                  while(enterGUIReturn == true){
                     try {
                        Thread.sleep(100);
                     } catch (InterruptedException ex) {

                     }
                  }
                  screen.displayMessage("Please enter the amount: ");
                     //check = keypad.getDouble();//store the amount
                  if(check<0)//check negative value
                     screen.displayMessageLine("\nNegative value!!\nInvalid selection. Try again." );
                  else {
                     if (check % 1 != 0) {//check decimal place
                        //check is it valid
                        checkCase = false;
                     }
                     userChoice = (int) check;//put back the value to user's choice
                     if (userChoice % 100 != 0) {//checking
                        userChoice = WRONG;//return wrong
                     }
                  }
               }
                // save user's choice
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-4
               invalidMessage();
               while(checkerror == true) {
                  try {
                     Thread.sleep(100);
                  } catch (InterruptedException ex) {

                  }
               }

               screen.displayMessageLine( 
                  "\nInvalid selection. Try again." );
               invalidMessage();
         } // end switch
      } // end while
         if(checkCase == false) {
            userChoice = ERROR;//return error
         }
      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen,
                     BankDatabase atmBankDatabase, Keypad atmKeypad,
                     CashDispenser atmCashDispenser, JPanel panel ,JPanel panel1)
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );
      subPanel2 = panel;
      subPanel = panel1;

      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;

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



      handlerX = new ButtonHandler();
      subPanel2.add(leftButtonJPanel, BorderLayout.WEST);
      subPanel2.add(rightButtonJPanel, BorderLayout.EAST);
      subPanel.add(extraButtonJPanel, BorderLayout.EAST);
      subPanel.add(buttonJPanel, BorderLayout.CENTER);
      // register event handlers
      for (int i = 0; i <= 11; i++) {
         buttons[i].addActionListener(handlerX);
      }
      for (int i = 0; i <= 3; i++) {
         leftButtons[i].addActionListener(handlerX);
         rightButtons[i].addActionListener(handlerX);
      }
      for (int i = 0; i <= 2; i++) {
         extraButtons[i].addActionListener(handlerX);
      }





   } // end Withdrawal constructor

   // perform transaction

   public int takevalue(){
      return canceledNum;
   }


   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal
      int num1 = 0,num2 = 0,num3 = 0;//set num1 is HKD100 num2 HKD 500 num3 HKD 1000
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase(); 
      Screen screen = getScreen();

      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user 
         amount = displayMenuOfAmounts();
         // check whether user enter a wrong amount
         if(amount == ERROR){//if error show error message
            errorMessage();
            while(checkerror == true){
               try {
                  Thread.sleep(100);
               } catch (InterruptedException ex) {

               }
            }
            screen.displayMessageLine( "\nERROR transaction..." );
            screen.displayMessageLine( "\nOnly accept integer." );
            screen.displayMessageLine( "\nPlease try enter." );


         }
         else if(amount == WRONG){//if wrong display wrong
            wrongMessage();
            while(checkerror == true){
               try {
                  Thread.sleep(100);
               } catch (InterruptedException ex) {

               }
            }
            screen.displayMessageLine( "\nWRONG transaction..." );
            screen.displayMessageLine( "\nPlease enter the amount with multiples of HKD100." );


         }
         // check whether user chose a withdrawal amount or canceled
         else if ( amount != CANCELED )
         {
            // get available balance of account involved
            availableBalance = 
               bankDatabase.getAvailableBalance( getAccountNumber() );


            // check whether the user has enough money in the account 
            if ( amount <= availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( getAccountNumber(), amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed
                  do{//calculate output how many piece(s) of $100,$500,$1000
                     if(amount>=1000){ //if the input value larger or equal $1000
                        amount-=1000;//the amount of input will minus $1000
                        num3++;//the $1000 will add one
                     }
                     else if(amount>=500){// if the input value larger or equal $500
                        amount-=500;//the amount of input will minus $500
                        num2++;//the $500 will add one
                     }
                     else if(amount >=100){// if the input value larger or equal $100
                        amount-=100;//the amount of input will minus $100
                        num1++;//the $100 will add one
                     }
                  }while(amount!=0);

                  // instruct user to take cash
                  screen.displayMessageLine("\nPlease take your cash now." );//display message of how many cash the user takes,
                                                      // if 0 number of that cash dollar, the piece will not show
                  if(num1>0)// Display the message of $100
                     screen.displayMessageFd("\nNumber of HKD 100: %d piece(s)",num1 );
                  if(num2>0)// Display the message of $500
                     screen.displayMessageFd("\nNumber of HKD 500: %d piece(s)", num2 );
                  if(num3>0)// Display the message of $1000
                     screen.displayMessageFd("\nNumber of HKD 1000: %d piece(s)", num3 );


                  {

                     checkerror=true;
                     BorderLayout layout = (BorderLayout)subPanel2.getLayout();
                     subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                     JPanel numberPanel = new JPanel();
                     numberPanel.setLayout(new GridLayout(5,1));
                     numberPanel.setBackground(atmBlue);

                     JPanel accountPanel1 = new JPanel();
                     accountPanel1.setLayout(new BorderLayout());
                     accountPanel1.setBackground(atmBlue);

                     JPanel accountPanel3 = new JPanel();
                     accountPanel3.setLayout(new BorderLayout());
                     accountPanel3.setBackground(atmBlue);

                     JPanel accountPanel4 = new JPanel();
                     accountPanel4.setLayout(new BorderLayout());
                     accountPanel4.setBackground(atmBlue);


                     JLabel title = new JLabel();
                     title.setText("Number of Money");
                     title.setFont(new Font("Serif", Font.PLAIN, 35));
                     title.setHorizontalAlignment(JLabel.CENTER);
                     title.setVerticalAlignment(JLabel.CENTER);
                     JPanel accountPanelBlank1 = new JPanel();
                     accountPanelBlank1.setLayout(new BorderLayout());
                     accountPanelBlank1.setBackground(atmBlue);
                     numberPanel.add(accountPanelBlank1);

                     JLabel oneHun = new JLabel("");
                     JLabel fiveHun = new JLabel("");
                     JLabel oneTh = new JLabel("");
                     if(num1>0)// Display the message of $100
                     {
                        oneHun.setText("Number of HKD 100: " +num1+ " piece(s)");
                        oneHun.setFont(new Font("Serif", Font.PLAIN, 30));
                        oneHun.setHorizontalAlignment(JLabel.CENTER);
                        oneHun.setVerticalAlignment(JLabel.CENTER);
                     }

                     numberPanel.add(accountPanel1);
                     if(num2>0)// Display the message of $500
                     {
                        fiveHun.setText("Number of HKD 500: " +num2+ " piece(s)");
                        fiveHun.setFont(new Font("Serif", Font.PLAIN, 30));
                        fiveHun.setHorizontalAlignment(JLabel.CENTER);
                        fiveHun.setVerticalAlignment(JLabel.CENTER);


                     }
                     numberPanel.add(accountPanel3);
                     if(num3>0)// Display the message of $1000
                     {
                        oneTh.setText("Number of HKD 1000: " +num3+ " piece(s)");
                        oneTh.setFont(new Font("Serif", Font.PLAIN, 30));
                        oneTh.setHorizontalAlignment(JLabel.CENTER);
                        oneTh.setVerticalAlignment(JLabel.CENTER);
                     }
                     numberPanel.add(accountPanel4);

                     JLabel Label3 = new JLabel();
                     Label3.setText("Please press \"Enter\" to confirm.");
                     Label3.setFont(new Font("Serif", Font.PLAIN, 30));
                     Label3.setHorizontalAlignment(JLabel.RIGHT);
                     Label3.setVerticalAlignment(JLabel.CENTER);
                     JPanel accountPanel5 = new JPanel();
                     accountPanel5.setLayout(new BorderLayout());
                     accountPanel5.setBackground(atmBlue);
                     numberPanel.add(accountPanel5);

                     accountPanelBlank1.setLayout(new BorderLayout());
                     accountPanelBlank1.add(title);
                     accountPanel1.setLayout(new BorderLayout());
                     accountPanel1.add(oneHun);
                     accountPanel3.setLayout(new BorderLayout());
                     accountPanel3.add(fiveHun);
                     accountPanel4.setLayout(new BorderLayout());
                     accountPanel4.add(oneTh);
                     accountPanel5.setLayout(new BorderLayout());
                     accountPanel5.add(Label3);

                     subPanel2.add(numberPanel, BorderLayout.CENTER);
                     numberPanel.setVisible(true);
                     subPanel2.revalidate();
                     return;
                  }

               } // end if
               else // cash dispenser does not have enough cash
               {
                  nomoneyatm();
                  while (checkerror == true) {
                     try {
                        Thread.sleep(100);
                     } catch (InterruptedException ex) {

                     }
                  }
                  screen.displayMessageLine(
                          "\nInsufficient cash available in the ATM." +
                                  "\nPlease choose a smaller amount.");
               }
            } // end if
            else // not enough money available in user's account
            {
               nomoneyacc();
               while (checkerror == true) {
                  try {
                     Thread.sleep(100);
                  } catch (InterruptedException ex) {

                  }
               }
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            withCancel();
            return; // return to main menu because user canceled
         } // end else
      } while ( !cashDispensed );

   } // end method execute

   public JLabel setLabelTitle(String name){
      JLabel assignTitleLabel = new JLabel();
      assignTitleLabel.setText(name);
      assignTitleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      assignTitleLabel.setHorizontalAlignment(JLabel.CENTER);
      assignTitleLabel.setVerticalAlignment(JLabel.CENTER);
      return assignTitleLabel;
   }

   public void withdrawalChooseGUI()
   {
      withdrawalReturn = true;

      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      JPanel withdrawalPanel = new JPanel();
      withdrawalPanel.setLayout(new GridBagLayout());
      withdrawalPanel.setBackground(atmBlue);


      JLabel title = setLabelTitle("Select the amount to withdrawal");
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(title);

      JLabel blank = new JLabel("");
      JPanel accountPanelBlank2 = new JPanel();
      accountPanelBlank2.setLayout(new BorderLayout());
      accountPanelBlank2.add(blank);


      JLabel first = setLabelTitle("HKD $100");
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(first);

      JLabel second = setLabelTitle("HKD $500");
      JPanel accountPanel2 = new JPanel();
      accountPanel2.setLayout(new BorderLayout());
      accountPanel2.add(second);


      JLabel three = setLabelTitle("HKD $1000");
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(three);


      JLabel bottom = setLabelTitle("Or");
      JPanel accountPanel4 = new JPanel();
      accountPanel4.setLayout(new BorderLayout());
      accountPanel4.add(bottom);


      JLabel bottom1 = setLabelTitle("Press \"Enter\" continue to input the amount.");
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(bottom1);

      accountPanelBlank1.setBackground(atmBlue);
      accountPanelBlank2.setBackground(atmBlue);
      accountPanel1.setBackground(choiceBlue);
      accountPanel2.setBackground(choiceBlue);
      accountPanel3.setBackground(choiceBlue);
      accountPanel4.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);

      GridBagConstraints c0 = new GridBagConstraints();
      c0.insets = new Insets(0,0,1,0);
      c0.gridx = 0;
      c0.gridy = 0;
      c0.gridwidth = 2;
      c0.gridheight = 1;
      c0.weightx = 0.5;
      c0.weighty = 0.5;
      c0.fill = GridBagConstraints.BOTH;
      c0.anchor = GridBagConstraints.NORTH;
      withdrawalPanel.add(accountPanelBlank1, c0);

      GridBagConstraints c1 = new GridBagConstraints();
      c1.insets = new Insets(3,0,8,5);
      c1.gridx = 0;
      c1.gridy = 1;
      c1.gridwidth = 1;
      c1.gridheight = 1;
      c1.weightx = 0.47;
      c1.weighty = 0.47;
      c1.fill = GridBagConstraints.BOTH;
      c1.anchor = GridBagConstraints.WEST;
      withdrawalPanel.add(accountPanel1, c1);

      GridBagConstraints c2 = new GridBagConstraints();
      c2.insets = new Insets(3,5,8,0);
      c2.gridx = 1;
      c2.gridy = 1;
      c2.gridwidth = 1;
      c2.gridheight = 1;
      c2.weightx = 0.47;
      c2.weighty = 0.47;
      c2.fill = GridBagConstraints.BOTH;
      c2.anchor = GridBagConstraints.EAST;
      withdrawalPanel.add(accountPanel2, c2);

      GridBagConstraints c3 = new GridBagConstraints();
      c3.insets = new Insets(0,0,8,5);
      c3.gridx = 0;
      c3.gridy = 2;
      c3.gridwidth = 1;
      c3.gridheight = 1;
      c3.weightx = 0.47;
      c3.weighty = 0.47;
      c3.fill = GridBagConstraints.BOTH;
      c3.anchor = GridBagConstraints.WEST;
      withdrawalPanel.add(accountPanel3, c3);

      GridBagConstraints c4 = new GridBagConstraints();
      c3.insets = new Insets(0,5,8,0);
      c4.gridx = 1;
      c4.gridy = 2;
      c4.gridwidth = 1;
      c4.gridheight = 1;
      c4.weightx = 0.47;
      c4.weighty = 0.47;
      c4.fill = GridBagConstraints.BOTH;
      c4.anchor = GridBagConstraints.SOUTH;
      withdrawalPanel.add(accountPanelBlank2, c4);

      GridBagConstraints c5 = new GridBagConstraints();
      c5.gridx = 0;
      c5.gridy = 4;
      c5.gridwidth = 2;
      c5.gridheight = 1;
      c5.weightx = 0.5;
      c5.weighty = 0.5;
      c5.fill = GridBagConstraints.BOTH;
      c5.anchor = GridBagConstraints.WEST;
      withdrawalPanel.add(accountPanel4, c5);

      GridBagConstraints c6 = new GridBagConstraints();
      c6.gridx = 0;
      c6.gridy = 5;
      c6.gridwidth = 2;
      c6.gridheight = 1;
      c6.weightx = 0.5;
      c6.weighty = 0.5;
      c6.fill = GridBagConstraints.BOTH;
      c6.anchor = GridBagConstraints.WEST;
      withdrawalPanel.add(accountPanel5, c6);

      subPanel2.add(withdrawalPanel, BorderLayout.CENTER);
      withdrawalPanel.setVisible(true);
      subPanel2.revalidate();
      subPanel2.repaint();

   }

   public void invalidMessage(){
      checkerror=true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel invalidPanel = new JPanel();
      invalidPanel.setLayout(new GridLayout(4,1));
      invalidPanel.setBackground(atmBlue);



      JLabel invalidTitle = setLabelTitle("Withdrawal");
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      invalidPanel.add(accountPanelBlank1);

      JLabel invalidLabel = setLabelTitle("Invalid selection.");
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel1);

      JLabel invalidLabel2 = setLabelTitle("Please try again.");
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel3);


      JLabel invalidLabel3 = new JLabel();
      invalidLabel3.setText("Please press \"Enter\" to continue.");
      invalidLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      invalidLabel3.setHorizontalAlignment(JLabel.RIGHT);
      invalidLabel3.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      invalidPanel.add(accountPanel5);


      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(invalidTitle);
      accountPanelBlank1.setBackground(atmBlue);
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

   public void enterGUI(){
      enterGUIReturn = true;

      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));

      JPanel enterPanel = new JPanel();
      enterPanel.setLayout(new GridBagLayout());
      enterPanel.setBackground(atmBlue);


      JLabel titleLabel = new JLabel("Withdrawal");
      titleLabel.setFont(new Font("Serif", Font.PLAIN, 25));
      titleLabel.setHorizontalAlignment(JLabel.CENTER);
      titleLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.setBackground(atmBlue);
      accountPanelBlank1.add(titleLabel);

      JLabel titleLabel2 = new JLabel("Enter the amount(HKD $)");
      titleLabel2.setFont(new Font("Serif", Font.PLAIN, 25));
      titleLabel2.setHorizontalAlignment(JLabel.CENTER);
      titleLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.setBackground(choiceBlue);
      accountPanel1.add(titleLabel2);

      // declaration of textArea for displaying output
      enterTextField.setEditable(false);
      enterTextField.setText(lineamount);  // display line1 in textArea
      lineamount = "";
      enterTextField.setText("");

      JPanel accountPanel2 = new JPanel();
      accountPanel2.setLayout(new BorderLayout());

      accountPanel2.add(enterTextField);

      JLabel blankLabel = new JLabel("");
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(blankLabel);
      accountPanel3.setBackground(atmBlue);

      GridBagConstraints c0 = new GridBagConstraints();
      c0.insets = new Insets(0,0,1,0);
      c0.gridx = 0;
      c0.gridy = 0;
      c0.gridwidth = 2;
      c0.gridheight = 1;
      c0.weightx = 0.5;
      c0.weighty = 0.5;
      c0.fill = GridBagConstraints.BOTH;
      c0.anchor = GridBagConstraints.NORTH;
      enterPanel.add(accountPanelBlank1, c0);

      GridBagConstraints c1 = new GridBagConstraints();
      c1.insets = new Insets(3,0,8,5);
      c1.gridx = 0;
      c1.gridy = 1;
      c1.gridwidth = 2;
      c1.gridheight = 1;
      c1.weightx = 0.47;
      c1.weighty = 0.47;
      c1.fill = GridBagConstraints.BOTH;
      c1.anchor = GridBagConstraints.WEST;
      enterPanel.add(accountPanel1, c1);

      GridBagConstraints c2 = new GridBagConstraints();
      c2.insets = new Insets(3,5,8,0);
      c2.gridx = 0;
      c2.gridy = 2;
      c2.gridwidth = 2;
      c2.gridheight = 1;
      c2.weightx = 0.47;
      c2.weighty = 0.47;
      c2.fill = GridBagConstraints.NONE;
      c2.anchor = GridBagConstraints.WEST;
      enterPanel.add(accountPanel2, c2);

      GridBagConstraints c3 = new GridBagConstraints();
      c3.insets = new Insets(0,0,8,5);
      c3.gridx = 0;
      c3.gridy = 3;
      c3.gridwidth = 2;
      c3.gridheight = 2;
      c3.weightx = 0.47;
      c3.weighty = 0.47;
      c3.fill = GridBagConstraints.BOTH;
      c3.anchor = GridBagConstraints.WEST;
      enterPanel.add(accountPanel3, c3);


      subPanel2.add(enterPanel, BorderLayout.CENTER);
      enterPanel.setVisible(true);
      subPanel2.revalidate();
      subPanel2.repaint();
   }

   public void errorMessage(){
      checkerror=true;

      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(4,1));
      errorPanel.setBackground(atmBlue);

      JLabel errortitle = new JLabel();
      errortitle.setText("Withdrawal");
      errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
      errortitle.setHorizontalAlignment(JLabel.CENTER);
      errortitle.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.setBackground(atmBlue);
      errorPanel.add(accountPanelBlank1);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Error input, please input integer only.");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.setBackground(atmBlue);
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("Please press \"Enter\" to continue.");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel2.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.setBackground(atmBlue);
      errorPanel.add(accountPanel3);


      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(errortitle);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel2);


      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);


      subPanel2.revalidate();

   }

   public void wrongMessage(){
      checkerror=true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(4,1));
      errorPanel.setBackground(atmBlue);


      JLabel errortitle = new JLabel();
      errortitle.setText("Withdrawal");
      errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
      errortitle.setHorizontalAlignment(JLabel.CENTER);
      errortitle.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      errorPanel.add(accountPanelBlank1);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Wrong input");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("Please enter the amount with multiples of HKD100.");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel2.setHorizontalAlignment(JLabel.CENTER);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      JLabel errorLabel3 = new JLabel();
      errorLabel3.setText("Please press \"Enter\" to continue.");
      errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel3.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      errorPanel.add(accountPanel5);

      accountPanelBlank1.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(errortitle);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel2);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(errorLabel3);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();

   }

   public void nomoneyatm(){
      checkerror=true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(4,1));
      errorPanel.setBackground(atmBlue);


      JLabel errortitle = new JLabel();
      errortitle.setText("Withdrawal");
      errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
      errortitle.setHorizontalAlignment(JLabel.CENTER);
      errortitle.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      errorPanel.add(accountPanelBlank1);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Insufficient cash available in the ATM.");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("Please choose a smaller amount.");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel2.setHorizontalAlignment(JLabel.CENTER);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      JLabel errorLabel3 = new JLabel();
      errorLabel3.setText("Please press \"Enter\" to continue.");
      errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel3.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      errorPanel.add(accountPanel5);

      accountPanelBlank1.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(errortitle);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel2);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(errorLabel3);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();

   }

   public void nomoneyacc(){
      checkerror=true;
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(4,1));
      errorPanel.setBackground(atmBlue);


      JLabel errortitle = new JLabel();
      errortitle.setText("Withdrawal");
      errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
      errortitle.setHorizontalAlignment(JLabel.CENTER);
      errortitle.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      errorPanel.add(accountPanelBlank1);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("Insufficient cash available in your account.");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("Please choose a smaller amount.");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel2.setHorizontalAlignment(JLabel.CENTER);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      JLabel errorLabel3 = new JLabel();
      errorLabel3.setText("Please press \"Enter\" to continue.");
      errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel3.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      errorPanel.add(accountPanel5);


      accountPanelBlank1.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(errortitle);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel2);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(errorLabel3);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();


   }

   public void withCancel(){

      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
      JPanel errorPanel = new JPanel();
      errorPanel.setLayout(new GridLayout(4,1));
      errorPanel.setBackground(atmBlue);


      JLabel errortitle = new JLabel();
      errortitle.setText("Withdrawal");
      errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
      errortitle.setHorizontalAlignment(JLabel.CENTER);
      errortitle.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanelBlank1 = new JPanel();
      accountPanelBlank1.setLayout(new BorderLayout());
      errorPanel.add(accountPanelBlank1);

      JLabel errorLabel = new JLabel();
      errorLabel.setText("The withdrawal has canceled.");
      errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel.setHorizontalAlignment(JLabel.CENTER);
      errorLabel.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel1 = new JPanel();
      accountPanel1.setLayout(new BorderLayout());
      errorPanel.add(accountPanel1);

      JLabel errorLabel2 = new JLabel();
      errorLabel2.setText("");
      errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel2.setHorizontalAlignment(JLabel.CENTER);
      errorLabel2.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel3 = new JPanel();
      accountPanel3.setLayout(new BorderLayout());
      errorPanel.add(accountPanel3);

      JLabel errorLabel3 = new JLabel();
      errorLabel3.setText("Please press \"Enter\" to continue.");
      errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
      errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
      errorLabel3.setVerticalAlignment(JLabel.CENTER);
      JPanel accountPanel5 = new JPanel();
      accountPanel5.setLayout(new BorderLayout());
      errorPanel.add(accountPanel5);


      accountPanelBlank1.setBackground(atmBlue);
      accountPanel1.setBackground(atmBlue);
      accountPanel3.setBackground(atmBlue);
      accountPanel5.setBackground(atmBlue);

      accountPanelBlank1.setLayout(new BorderLayout());
      accountPanelBlank1.add(errortitle);
      accountPanel1.setLayout(new BorderLayout());
      accountPanel1.add(errorLabel);
      accountPanel3.setLayout(new BorderLayout());
      accountPanel3.add(errorLabel2);
      accountPanel5.setLayout(new BorderLayout());
      accountPanel5.add(errorLabel3);

      subPanel2.add(errorPanel, BorderLayout.CENTER);
      errorPanel.setVisible(true);
      subPanel2.revalidate();

   }

   public class ButtonHandler implements ActionListener {
      public void actionPerformed(ActionEvent e) {

         String[] buttonsNames =
                 {"B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7"};



         if (withdrawalReturn == true) {//page withdrawal
            if (e.getSource().equals(extraButtons[1])) {
               edit = 4;//set index will be 9 when enter press
            }
            else if (e.getSource().equals(extraButtons[0])) {//cancel
               edit = 5;
               canceledNum = 1;
            }

            else if (e.getActionCommand().equals("B4")) {//B4 ie invalid selection
               edit = 2;
            } else if (e.getActionCommand().equals("B0")) {
               edit = 1;
            } else if (e.getActionCommand().equals("B1")) {
               edit = 3;
            } else edit = -1;
            withdrawalReturn = false;
         }


         if (checkerror == true) {//page invalid selection
            if (e.getSource().equals(extraButtons[1])) {

               checkerror = false;


            }
         }



         for(int i=0;i<=11;i++){//enter gui
            if (e.getSource().equals(extraButtons[2])) {
               enterTextField.setText("");
               lineamount = "";
            }
            else if(e.getActionCommand().equals(names[i])) {
               lineamount=lineamount.concat( e.getActionCommand());
               enterTextField.setText(lineamount); // display line1 in textArea
            }
            else{
               lineamount=lineamount.concat("");
            }
         }
         if(enterGUIReturn == true && e.getSource().equals(extraButtons[1])){
            String input = enterTextField.getText();//the input
            System.out.println(input);
            check = Double.parseDouble(input); //for return the value back to withdrawal
            System.out.println(check);
            enterGUIReturn = false;

         }










      }
   }

} // end class Withdrawal+



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