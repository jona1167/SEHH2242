// Deposit.java
// Represents a deposit ATM transaction

import javax.swing.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transfer extends Transaction
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

    private String line1 = "";//for enter amount
    private String line2 = "";//for enter amount

    private JTextField transferTextfield2 =new JTextField(50);//set for text
    private JTextField transferTextfield = new JTextField(50);//set for text


    private JPanel subPanel2;//set for panel
    private JPanel subPanel;//set for panel
    private ButtonHandler handlerT;//set for handler
    private boolean transRecreturn= false;//set for boolean
    private boolean transerror = false;//set for boolean
    private boolean transGUIReturn = false;//set for boolean
    private boolean transGUIReturn1 = false;//set for boolean
    private boolean confirm = false;//set for boolean
    private boolean transRecreturnchoose = false;//set for boolean

    //set for the variables
    private int edit = -1;
    private int choice = -1;
    private int canceledNum = 0;
    private int receiverAc =-1;
    private double amount; // amount to transfer
    private double check = -1; // amount to transfer
    private final Keypad keypad; // reference to keypad

    private final static int CANCELED = 0; // constant for cancel option


    // Deposit constructor
    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad,  JPanel panel ,JPanel panel1 )
    {
        // initialize superclass variables
        super( userAccountNumber, atmScreen, atmBankDatabase );



        // initialize references to keypad
        //set the panel
        keypad = atmKeypad;
        subPanel2 = panel;
        subPanel = panel1;
        //set layout
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.WEST));
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.EAST));
        BorderLayout layout1 = (BorderLayout)subPanel.getLayout();
        subPanel.remove(layout1.getLayoutComponent(BorderLayout.EAST));
        subPanel.remove(layout1.getLayoutComponent(BorderLayout.CENTER));

        leftButtons = new JButton[4]; // create array of JButtons
        leftButtonJPanel = new JPanel(); // set up panel
        leftButtonJPanel.setLayout(new GridLayout(5, 1, 0, 10));//set Gridlayout
        JLabel blankLabel = new JLabel();//set panel
        leftButtonJPanel.add(blankLabel);
        for (int count = 0; count < 4; count++) {
            leftButtons[count] = new JButton();
            leftButtonJPanel.add(leftButtons[count]); // add button to JFrame
        } // end for
        //set left buttons
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
        //set right buttons
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

        //set three buttons
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
        handlerT = new ButtonHandler();
        subPanel2.add(leftButtonJPanel, BorderLayout.WEST);//add the panel with layout
        subPanel2.add(rightButtonJPanel, BorderLayout.EAST);//add the panel with layout
        subPanel.add(extraButtonJPanel, BorderLayout.EAST);//add the panel with layout
        subPanel.add(buttonJPanel, BorderLayout.CENTER);//add the panel with layout
        // register event handlers
        for (int i = 0; i <= 11; i++) {
            buttons[i].addActionListener(handlerT);
        }
        for (int i = 0; i <= 3; i++) {
            leftButtons[i].addActionListener(handlerT);
            rightButtons[i].addActionListener(handlerT);
        }
        for (int i = 0; i <= 2; i++) {
            extraButtons[i].addActionListener(handlerT);
        }


    } // end Transfer constructor


    public int takevalue(){
        return canceledNum;
    }

    // perform transaction
    public void execute()
    {
        BankDatabase bankDatabase = getBankDatabase(); // get reference
        Screen screen = getScreen(); // get reference
        double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber()); // amount available for transfer
        int TransferAccountNumber;//set variables
        int x;//set variables

        screen.displayMessage( "\nPlease enter receiver's account number (or 0 to cancel): " );
        do{
                transferReceive();//call the gui
            while(transGUIReturn== true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {

                }
            }
            //TransferAccountNumber = keypad.getInput();
             TransferAccountNumber = receiverAc;
            if(TransferAccountNumber != -1){
                screen.displayMessage( "\n1. Receiver's Saving account" );
                screen.displayMessage( "\n2. Receiver's Cheque account\n" );
            }
            else
                break;
            do{
                transferChoose();//call the gui
                while(transRecreturnchoose == true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }
                x = edit;
                System.out.println(x);
               // x = keypad.getInput();

                screen.displayMessage("\nInValid input. Please try again.");
                if(x != 1 && x != 2) {
                    transInvalidMessage();//call the gui
                    while (transerror == true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }

            }while(x != 1 && x != 2);
            if(x == 1)
                TransferAccountNumber = TransferAccountNumber*1000+1;
            else
                TransferAccountNumber = TransferAccountNumber*1000+2;
            if(!bankDatabase.authenticateAccountNumber(TransferAccountNumber) && TransferAccountNumber != -1 || TransferAccountNumber == getAccountNumber()){
                if(TransferAccountNumber == getAccountNumber()){
                    receivenum();//call the gui
                    while(transerror == true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {

                        }
                    }
                    screen.displayMessage( "\nReceiver's account cannot be your account." );
                }
                else {
                    transInvlid();//call the gui
                    while (transerror == true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {

                        }
                    }
                    screen.displayMessage("\nInvalid account number.");
                }
                screen.displayMessage( "\nPlease enter receiver's account number again (or 0 to cancel).\n" );
            }

        }while(!bankDatabase.authenticateAccountNumber(TransferAccountNumber) && TransferAccountNumber != 0 || TransferAccountNumber == getAccountNumber());

        if(TransferAccountNumber != -1)
            amount = promptForTransferAmount(availableBalance); // get transfer amount from user

        // check whether user entered a transfer amount or canceled
        if ( amount != CANCELED && TransferAccountNumber != -1)
        {
            // request the transfer amount
            screen.displayMessage(
                    "\nTransfer amount is " );
            screen.displayDollarAmount( amount );
            screen.displayMessageLine( "." );

            screen.displayMessageLine( "Input 1 to confirm the transfer amount, " );
            screen.displayMessageLine( "or input 0 to canceled your transaction." );

            //Confirm the transaction
            transferConfirm();//call the gui
            while(confirm == true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {

                }
            }
            //int TransferReceived = keypad.getInput();
            int TransferReceived = choice;

            while(TransferReceived != 0 && TransferReceived != 1){

                screen.displayMessageLine( "\nOnly accept 0 or 1. Please try again: " );
                transOnly();//call the gui
                while(confirm == true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }
                //TransferReceived = keypad.getInput();
                TransferReceived = choice;
            }

            // check whether transaction was accepted
            if ( TransferReceived == 1)
            {
                screen.displayMessageLine( "\nYour transaction has been accepted." );
                // credit account to reflect the transfer
                bankDatabase.debit( getAccountNumber(), amount );
                bankDatabase.debit( TransferAccountNumber, -amount );

                transferAccept();//call the gui

            } // end if
            else // transaction is not accepted
            {
                screen.displayMessageLine( "\nThe ATM has canceled your transaction." );
                transCancel();//call the gui

            } // end else
        } // end if
        else// user canceled instead of entering amount
        {
            screen.displayMessageLine( "\nCanceling transaction..." );
            transCancel();//call the gui
        } // end else
    } // end method execute


    public JLabel setLabelTitle(String name){
        JLabel assignTitleLabel = new JLabel();//set the panel name
        assignTitleLabel.setText(name);// set the font
        assignTitleLabel.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
        assignTitleLabel.setHorizontalAlignment(JLabel.CENTER);//set the position
        assignTitleLabel.setVerticalAlignment(JLabel.CENTER);//set the position
        return assignTitleLabel;
    }
    //gui method
    public void transInvalidMessage(){
        transerror=true;//set boolean
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();//set layout
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));//set the position
        JPanel invalidPanel = new JPanel();//set the panel name
        invalidPanel.setLayout(new GridLayout(4,1));//set the panel row
        invalidPanel.setBackground(atmBlue);//set the panel color



        JLabel invalidTitle = setLabelTitle("Transaction");//set the output
        JPanel accountPanelBlank1 = new JPanel();//set the panel name
        accountPanelBlank1.setLayout(new BorderLayout());//set the panel
        invalidPanel.add(accountPanelBlank1);//set the panel

        JLabel invalidLabel = setLabelTitle("Invalid selection.");//set the output
        JPanel accountPanel1 = new JPanel();//set the panel name
        accountPanel1.setLayout(new BorderLayout());//set the panel
        invalidPanel.add(accountPanel1);//set the panel

        JLabel invalidLabel2 = setLabelTitle("Please try again.");//set the output
        JPanel accountPanel3 = new JPanel();//set the panel
        accountPanel3.setLayout(new BorderLayout());//set the panel
        invalidPanel.add(accountPanel3);//add the panel


        JLabel invalidLabel3 = new JLabel();//set the panel
        invalidLabel3.setText("Please press \"Enter\" to continue.");//set the output
        invalidLabel3.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
        invalidLabel3.setHorizontalAlignment(JLabel.RIGHT);//set the position
        invalidLabel3.setVerticalAlignment(JLabel.CENTER);//set the position
        JPanel accountPanel5 = new JPanel();//set back the panel
        accountPanel5.setLayout(new BorderLayout());//add the panel using
        invalidPanel.add(accountPanel5);//add the panel using

        accountPanelBlank1.setLayout(new BorderLayout());//add the panel using
        accountPanelBlank1.add(invalidTitle);//add the panel
        accountPanelBlank1.setBackground(atmBlue);//add the panel color
        accountPanel1.setLayout(new BorderLayout());//add the panel using
        accountPanel1.add(invalidLabel);//add the panel
        accountPanel1.setBackground(atmBlue);//add the panel color
        accountPanel3.setLayout(new BorderLayout());//add the panel using
        accountPanel3.add(invalidLabel2);//add the panel
        accountPanel3.setBackground(atmBlue);//add the panel color
        accountPanel5.setLayout(new BorderLayout());//add the panel using
        accountPanel5.add(invalidLabel3);//add the panel
        accountPanel5.setBackground(atmBlue);//add the panel color

        //add the panel using
        subPanel2.add(invalidPanel, BorderLayout.CENTER);
        invalidPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }

    //gui method
    public void receivenum(){
        transerror=true;//set boolean
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();//set layout
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));//set the position
        JPanel invalidPanel = new JPanel();//set the panel name
        invalidPanel.setLayout(new GridLayout(4,1));//set the panel row
        invalidPanel.setBackground(atmBlue);//set the panel color

        JLabel invalidLabel = setLabelTitle("Receive number cannot be your account.");//set the output
        JPanel accountPanel1 = new JPanel();//set panel name
        accountPanel1.setLayout(new BorderLayout());//set panel name
        invalidPanel.add(accountPanel1);//add the panel using

        JLabel invalidLabel2 = setLabelTitle("Please press \"Enter\" to return.");//set the output
        JPanel accountPanel3 = new JPanel();//set panel name
        accountPanel3.setLayout(new BorderLayout());//set panel name
        invalidPanel.add(accountPanel3);//add the panel using

        accountPanel1.setLayout(new BorderLayout());//add the panel using
        accountPanel1.add(invalidLabel);//add the panel using
        accountPanel1.setBackground(atmBlue);//add the panel using
        accountPanel3.setLayout(new BorderLayout());//add the panel using
        accountPanel3.add(invalidLabel2);//add the panel using
        accountPanel3.setBackground(atmBlue);//add the panel using

        //add the panel using
        subPanel2.add(invalidPanel, BorderLayout.CENTER);
        invalidPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }
    //gui method
    public void transInvlid(){
        transerror=true;//set boolean
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();//set layout
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));//set the position
        JPanel invalidPanel = new JPanel();//set the panel name
        invalidPanel.setLayout(new GridLayout(4,1));//set the panel row
        invalidPanel.setBackground(atmBlue);//set the panel color

        JLabel invalidLabel = setLabelTitle("Invalid account number.");//set the output
        JPanel accountPanel1 = new JPanel();// set the font
        accountPanel1.setLayout(new BorderLayout());//add the panel using
        invalidPanel.add(accountPanel1);//add the panel using

        JLabel invalidLabel2 = setLabelTitle("Please press \"Enter\" return to menu.");//set the output
        JPanel accountPanel3 = new JPanel();// set the font
        accountPanel3.setLayout(new BorderLayout());//add the panel using
        invalidPanel.add(accountPanel3);//add the panel using

        accountPanel1.setLayout(new BorderLayout());//add the panel using
        accountPanel1.add(invalidLabel);//add the panel using
        accountPanel1.setBackground(atmBlue);//add the panel color
        accountPanel3.setLayout(new BorderLayout());//add the panel using
        accountPanel3.add(invalidLabel2);//add the panel using
        accountPanel3.setBackground(atmBlue);//add the panel color

        //add the panel using
        subPanel2.add(invalidPanel, BorderLayout.CENTER);
        invalidPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();

    }
    //gui method
    public void transferReceive() {
        transGUIReturn = true;//set boolean
        BorderLayout layout = (BorderLayout) subPanel2.getLayout();//set layout
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));//set the position

        JPanel transferenterReceivePanel = new JPanel();//set the output
        transferenterReceivePanel.setLayout(new GridBagLayout());//set layout
        transferenterReceivePanel.setBackground(atmBlue);//set color
        JPanel accountPanelBlank1 = new JPanel();//set the panel name
        JPanel accountPanel1 = new JPanel();//set the panel name
        JPanel accountPanel2 = new JPanel();//set the panel name
        transferTextfield2 = new JTextField();//set the panel name
        accountPanel2.setBackground(choiceBlue);//set the panel color

        JLabel messageLabel1 = new JLabel();//set panel name
        messageLabel1.setText("Please enter the receiver \'s account number");//set the output
        messageLabel1.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
        messageLabel1.setHorizontalAlignment(JLabel.CENTER);//set the position
        messageLabel1.setVerticalAlignment(JLabel.CENTER);//set the position
        accountPanelBlank1.setLayout(new BorderLayout());//set panel name
        accountPanelBlank1.setBackground(atmBlue);//add the panel using
        accountPanelBlank1.add(messageLabel1);//add the panel color

        JLabel messageLabel2 = new JLabel();//set panel name
        messageLabel2.setText("After input the amount then press \"Enter\" ");//set the output
        messageLabel2.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
        messageLabel2.setHorizontalAlignment(JLabel.CENTER);//set the position
        messageLabel2.setVerticalAlignment(JLabel.CENTER);//set the position
        accountPanel1.setLayout(new BorderLayout());//set panel name
        accountPanel1.setBackground(atmBlue);//add the panel using
        accountPanel1.add(messageLabel2);//add the panel color

        JLabel userLabel = new JLabel();//set panel name
        userLabel.setText("Account number");//set the output
        userLabel.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
        userLabel.setHorizontalAlignment(JLabel.RIGHT);//set the position
        userLabel.setVerticalAlignment(JLabel.CENTER);//set the position
        accountPanel2.setLayout(new BorderLayout());//set panel name
        accountPanel2.setBackground(atmBlue);//add the panel using
        accountPanel2.add(userLabel);//add the panel color

        //each label size
        GridBagConstraints c0 = new GridBagConstraints();
        c0.insets = new Insets(0, 0, 0, 0);
        c0.gridx = 0;
        c0.gridy = 0;
        c0.gridwidth = 4;
        c0.gridheight = 1;
        c0.weightx = 0.47;
        c0.weighty = 0.47;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.WEST;
        transferenterReceivePanel.add(accountPanelBlank1, c0);
        //each label size
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(0, 0, 0, 0);
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 4;
        c1.gridheight = 1;
        c1.weightx = 0.47;
        c1.weighty = 0.47;
        c1.fill = GridBagConstraints.BOTH;
        c1.anchor = GridBagConstraints.WEST;
        transferenterReceivePanel.add(accountPanel1, c1);
        //each label size
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(0, 0, 0, 5);
        c2.gridx = 0;
        c2.gridy = 2;
        c2.gridwidth = 1;
        c2.gridheight = 1;
        c2.weightx = 0.50;
        c2.weighty = 0.50;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;
        transferenterReceivePanel.add(accountPanel2, c2);
        //each label size

        transferTextfield2.setEditable(false);
        line2 = "";
        transferTextfield2.setText(line2);  // display line1 in textField

        GridBagConstraints c3 = new GridBagConstraints();
        c3.insets = new Insets(0, 0, 0, 0);
        c3.gridx = 1;
        c3.gridy = 2;
        c3.gridwidth = 3;
        c3.gridheight = 1;
        c3.weightx = 0.50;
        c3.weighty = 0.50;
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.anchor = GridBagConstraints.CENTER;
        transferenterReceivePanel.add(transferTextfield2, c3);
        //each label size

        //add panel
        subPanel2.add(transferenterReceivePanel, BorderLayout.CENTER);
        transferenterReceivePanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }
    //gui method
    public void transferChoose(){
        transRecreturnchoose = true;//set boolean
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();//set layout
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));//set position
        JPanel accountPanel = new JPanel();//set layout
        accountPanel.setLayout(new GridLayout(5,2,10,10));//set panel
        accountPanel.setBackground(atmBlue);//set panel
        JPanel accountPanelBlank1 = new JPanel();//set panel
        JPanel accountPanelBlank2 = new JPanel();//set panel
        JPanel accountPanel1 = new JPanel();//set panel
        JPanel accountPanel2 = new JPanel();//set panel
        JPanel accountPanel3 = new JPanel();//set panel
        JPanel accountPanel4 = new JPanel();//set panel
        JPanel accountPanel5 = new JPanel();//set panel
        JPanel accountPanel6 = new JPanel();//set panel
        JPanel accountPanel7 = new JPanel();//set panel
        JPanel accountPanel8 = new JPanel();//set panel

        accountPanel1.setBackground(choiceBlue);//set panel color
        accountPanel2.setBackground(choiceBlue);//set panel color

        //declare JLabel
        JLabel accountLabel = new JLabel();
        accountLabel.setText("Please select");
        accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel.setHorizontalAlignment(JLabel.RIGHT);
        accountLabel.setVerticalAlignment(JLabel.CENTER);
        //declare JLabel
        JLabel accountLabel1 = new JLabel();
        accountLabel1.setText("transaction account.");
        accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel1.setHorizontalAlignment(JLabel.LEFT);
        accountLabel1.setVerticalAlignment(JLabel.CENTER);

        //declare JLabel
        JLabel savingLabel = new JLabel();
        savingLabel.setText("Saving account");
        savingLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        savingLabel.setHorizontalAlignment(JLabel.CENTER);
        savingLabel.setVerticalAlignment(JLabel.CENTER);

        //declare JLabel
        JLabel chequeLabel = new JLabel();
        chequeLabel.setText("Cheque account");
        chequeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        chequeLabel.setHorizontalAlignment(JLabel.CENTER);
        chequeLabel.setVerticalAlignment(JLabel.CENTER);

        //declare JLabel
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(accountLabel);
        accountPanelBlank2.setLayout(new BorderLayout());
        accountPanelBlank2.add(accountLabel1);

        //declare JLabel
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(savingLabel);
        accountLabel1.setBackground(atmBlue);
        accountPanel2.setLayout(new BorderLayout());
        accountPanel2.add(chequeLabel);
        accountPanel2.setBackground(atmBlue);

        //set JLabel
        accountPanelBlank1.setBackground(atmBlue);
        accountPanelBlank2.setBackground(atmBlue);
        accountPanel.setBackground(atmBlue);
        accountPanel1.setBackground(choiceBlue);
        accountPanel2.setBackground(choiceBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel4.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);
        accountPanel6.setBackground(atmBlue);
        accountPanel7.setBackground(atmBlue);
        accountPanel8.setBackground(atmBlue);

        //add JLabel
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

        //add JLabel
        subPanel2.add(accountPanel, BorderLayout.CENTER);
        accountPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }
    //gui method
    public void transferAmount(){
        //set layout
        transGUIReturn1= true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(atmBlue);
        JPanel accountPanelBlank1 = new JPanel();
        JPanel accountPanel1 = new JPanel();
        JPanel accountPanel2 = new JPanel();
        transferTextfield = new JTextField();

        //set color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(atmBlue);
        accountPanel2.setBackground(choiceBlue);

        //declare JLabel
        JLabel messageLabel1 = new JLabel();
        messageLabel1.setText("Please enter the transfer amount");
        messageLabel1.setFont(new Font("Serif", Font.PLAIN, 30));
        messageLabel1.setHorizontalAlignment(JLabel.CENTER);
        messageLabel1.setVerticalAlignment(JLabel.CENTER);
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(messageLabel1);

        //declare JLabel
        JLabel messageLabel2 = new JLabel();
        messageLabel2.setText("After input the amount then press \"Enter\" ");
        messageLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        messageLabel2.setHorizontalAlignment(JLabel.CENTER);
        messageLabel2.setVerticalAlignment(JLabel.CENTER);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(messageLabel2);

        //declare JLabel
        JLabel userLabel = new JLabel();
        userLabel.setText("transfer amount");
        userLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        userLabel.setHorizontalAlignment(JLabel.RIGHT);
        userLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanel2.setLayout(new BorderLayout());
        accountPanel2.add(userLabel);

        //declare JLabel
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

        //declare JLabel
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
        userPanel.add(accountPanel1, c1);

        //declare JLabel
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
        userPanel.add(accountPanel2, c2);

        transferTextfield.setEditable(false);
        line1 = "";
        transferTextfield.setText(line1);  // display line1 in textField

        //declare JLabel
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
        userPanel.add(transferTextfield, c3);
        //declare JLabel
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(choiceBlue);
        accountPanel2.setBackground(choiceBlue);

        subPanel2.add(userPanel, BorderLayout.CENTER);
        userPanel.setVisible(true);
        subPanel2.revalidate();
    }
    //gui method
    public void transferConfirm(){
        confirm = true;
        //set layout
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(atmBlue);
        JPanel accountPanelBlank1 = new JPanel();
        JPanel accountPanelBlank2 = new JPanel();
        JPanel accountPanel1 = new JPanel();
        JPanel accountPanel2 = new JPanel();
        JPanel accountPanel3 = new JPanel();
        JPanel accountPanel4 = new JPanel();
        JPanel accountPanel5 = new JPanel();
        JPanel accountPanel6 = new JPanel();
        JPanel accountPanel7 = new JPanel();
        JPanel accountPanel8 = new JPanel();
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new GridLayout(5,2,10,10));

        //declare JLabel
        JLabel accountLabel = new JLabel();
        accountLabel.setText("Transaction amount : ");
        accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel.setHorizontalAlignment(JLabel.RIGHT);
        accountLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(accountLabel);

        //declare JLabel
        JLabel accountLabel1 = new JLabel();
        accountLabel1.setText("$"+amount);
        accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel1.setHorizontalAlignment(JLabel.LEFT);
        accountLabel1.setVerticalAlignment(JLabel.CENTER);
        accountPanelBlank2.setLayout(new BorderLayout());
        accountPanelBlank2.add(accountLabel1);

        //declare JLabel
        JLabel savingLabel = new JLabel();
        savingLabel.setText("Confirm");
        savingLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        savingLabel.setHorizontalAlignment(JLabel.CENTER);
        savingLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(savingLabel);

        //declare JLabel
        JLabel chequeLabel = new JLabel();
        chequeLabel.setText("Cancel");
        chequeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        chequeLabel.setHorizontalAlignment(JLabel.CENTER);
        chequeLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanel2.setLayout(new BorderLayout());
        accountPanel2.add(chequeLabel);

        //add panel
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

        //add panel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanelBlank2.setBackground(atmBlue);
        accountPanel.setBackground(atmBlue);
        accountPanel1.setBackground(choiceBlue);
        accountPanel2.setBackground(choiceBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel4.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);
        accountPanel6.setBackground(atmBlue);
        accountPanel7.setBackground(atmBlue);
        accountPanel8.setBackground(atmBlue);

        subPanel2.add(accountPanel, BorderLayout.CENTER);
        accountPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }
    //gui method
    public void transCancel(){
        //set layout
        transerror=true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(4,1));
        errorPanel.setBackground(atmBlue);

        //declare JLabel
        JLabel errortitle = new JLabel();
        errortitle.setText("Transaction");
        errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
        errortitle.setHorizontalAlignment(JLabel.CENTER);
        errortitle.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanelBlank1 = new JPanel();
        accountPanelBlank1.setLayout(new BorderLayout());
        errorPanel.add(accountPanelBlank1);

        //declare JLabel
        JLabel errorLabel = new JLabel();
        errorLabel.setText("The ATM has canceled your transaction.");
        errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel1 = new JPanel();
        accountPanel1.setLayout(new BorderLayout());
        errorPanel.add(accountPanel1);

        //declare JLabel
        JLabel errorLabel2 = new JLabel();
        errorLabel2.setText("");
        errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel2.setHorizontalAlignment(JLabel.CENTER);
        errorLabel2.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel3 = new JPanel();
        accountPanel3.setLayout(new BorderLayout());
        errorPanel.add(accountPanel3);

        //declare JLabel
        JLabel errorLabel3 = new JLabel();
        errorLabel3.setText("Please press \"Enter\" to continue.");
        errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
        errorLabel3.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel5 = new JPanel();
        accountPanel5.setLayout(new BorderLayout());
        errorPanel.add(accountPanel5);

        //add JLabel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(atmBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);

        //declare JLabel
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(errortitle);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(errorLabel);
        accountPanel3.setLayout(new BorderLayout());
        accountPanel3.add(errorLabel2);
        accountPanel5.setLayout(new BorderLayout());
        accountPanel5.add(errorLabel3);

        //add JPanel
        subPanel2.add(errorPanel, BorderLayout.CENTER);
        errorPanel.setVisible(true);
        subPanel2.revalidate();
    }
    //gui method
    public void transferAccept(){
        //set layout
        transerror=true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(4,1));
        errorPanel.setBackground(atmBlue);

        //declare JLabel
        JLabel errortitle = new JLabel();
        errortitle.setText("Transaction");
        errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
        errortitle.setHorizontalAlignment(JLabel.CENTER);
        errortitle.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanelBlank1 = new JPanel();
        accountPanelBlank1.setLayout(new BorderLayout());
        errorPanel.add(accountPanelBlank1);

        //declare JLabel
        JLabel errorLabel = new JLabel();
        errorLabel.setText("Your transaction is successful.");
        errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel1 = new JPanel();
        accountPanel1.setLayout(new BorderLayout());
        errorPanel.add(accountPanel1);

        //declare JLabel
        JLabel errorLabel2 = new JLabel();
        errorLabel2.setText("");
        errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel2.setHorizontalAlignment(JLabel.CENTER);
        errorLabel2.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel3 = new JPanel();
        accountPanel3.setLayout(new BorderLayout());
        errorPanel.add(accountPanel3);

        //declare JLabel
        JLabel errorLabel3 = new JLabel();
        errorLabel3.setText("Please press \"Enter\" to continue.");
        errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
        errorLabel3.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel5 = new JPanel();
        accountPanel5.setLayout(new BorderLayout());
        errorPanel.add(accountPanel5);

        //add JLabel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(atmBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);

        //add JLabel
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(errortitle);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(errorLabel);
        accountPanel3.setLayout(new BorderLayout());
        accountPanel3.add(errorLabel2);
        accountPanel5.setLayout(new BorderLayout());
        accountPanel5.add(errorLabel3);

        //add JPanel
        subPanel2.add(errorPanel, BorderLayout.CENTER);
        errorPanel.setVisible(true);
        subPanel2.revalidate();
    }
    //gui method
    public void transNomoneyacc(){
        //set layout
        transerror=true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(4,1));
        errorPanel.setBackground(atmBlue);

        //declare JLabel
        JLabel errortitle = new JLabel();
        errortitle.setText("Transaction");
        errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
        errortitle.setHorizontalAlignment(JLabel.CENTER);
        errortitle.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanelBlank1 = new JPanel();
        accountPanelBlank1.setLayout(new BorderLayout());
        errorPanel.add(accountPanelBlank1);

        //declare JLabel
        JLabel errorLabel = new JLabel();
        errorLabel.setText("Insufficient cash available in your account.");
        errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel1 = new JPanel();
        accountPanel1.setLayout(new BorderLayout());
        errorPanel.add(accountPanel1);

        //declare JLabel
        JLabel errorLabel2 = new JLabel();
        errorLabel2.setText("Please choose a smaller amount.");
        errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel2.setHorizontalAlignment(JLabel.CENTER);
        errorLabel2.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel3 = new JPanel();
        accountPanel3.setLayout(new BorderLayout());
        errorPanel.add(accountPanel3);

        //declare JLabel
        JLabel errorLabel3 = new JLabel();
        errorLabel3.setText("Please press \"Enter\" to continue.");
        errorLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel3.setHorizontalAlignment(JLabel.RIGHT);
        errorLabel3.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel5 = new JPanel();
        accountPanel5.setLayout(new BorderLayout());
        errorPanel.add(accountPanel5);

        //add panel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(atmBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);

        //add JLabel
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(errortitle);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(errorLabel);
        accountPanel3.setLayout(new BorderLayout());
        accountPanel3.add(errorLabel2);
        accountPanel5.setLayout(new BorderLayout());
        accountPanel5.add(errorLabel3);

        //add JPanel
        subPanel2.add(errorPanel, BorderLayout.CENTER);
        errorPanel.setVisible(true);
        subPanel2.revalidate();
    }
    //gui method
    public void transInvaenagain(){
        //set layout
        transerror=true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new GridLayout(4,1));
        errorPanel.setBackground(atmBlue);

        //declare JLabel
        JLabel errortitle = new JLabel();
        errortitle.setText("Transaction");
        errortitle.setFont(new Font("Serif", Font.PLAIN, 35));
        errortitle.setHorizontalAlignment(JLabel.CENTER);
        errortitle.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanelBlank1 = new JPanel();
        accountPanelBlank1.setLayout(new BorderLayout());
        errorPanel.add(accountPanelBlank1);

        //declare JLabel
        JLabel errorLabel = new JLabel();
        errorLabel.setText("Invalid transfer number. ");
        errorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel1 = new JPanel();
        accountPanel1.setLayout(new BorderLayout());
        errorPanel.add(accountPanel1);

        //declare JLabel
        JLabel errorLabel2 = new JLabel();
        errorLabel2.setText("Please \"Enter\" to continue");
        errorLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        errorLabel2.setHorizontalAlignment(JLabel.RIGHT);
        errorLabel2.setVerticalAlignment(JLabel.CENTER);
        JPanel accountPanel3 = new JPanel();
        accountPanel3.setLayout(new BorderLayout());
        errorPanel.add(accountPanel3);

        //add JLabel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanel1.setBackground(atmBlue);
        accountPanel3.setBackground(atmBlue);

        //add JLabel
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(errortitle);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(errorLabel);
        accountPanel3.setLayout(new BorderLayout());
        accountPanel3.add(errorLabel2);

        //add JLabel
        subPanel2.add(errorPanel, BorderLayout.CENTER);
        errorPanel.setVisible(true);
        subPanel2.revalidate();
    }
    //gui method
    public void transOnly(){
        //set layout
        confirm = true;
        BorderLayout layout = (BorderLayout)subPanel2.getLayout();
        subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(atmBlue);

        //declare JPanel
        JPanel accountPanelBlank1 = new JPanel();
        JPanel accountPanelBlank2 = new JPanel();
        JPanel accountPanel1 = new JPanel();
        JPanel accountPanel2 = new JPanel();
        JPanel accountPanel3 = new JPanel();
        JPanel accountPanel4 = new JPanel();
        JPanel accountPanel5 = new JPanel();
        JPanel accountPanel6 = new JPanel();
        JPanel accountPanel7 = new JPanel();
        JPanel accountPanel8 = new JPanel();
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new GridLayout(5,2,10,10));

        //declare JLabel
        JLabel accountLabel = new JLabel();
        accountLabel.setText(" Only accept ");
        accountLabel.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel.setHorizontalAlignment(JLabel.RIGHT);
        accountLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanelBlank1.setLayout(new BorderLayout());
        accountPanelBlank1.add(accountLabel);

        //declare JPanel
        JLabel accountLabel1 = new JLabel();
        accountLabel1.setText("Confirm or Cancel");
        accountLabel1.setFont(new Font("Serif", Font.PLAIN, 35));
        accountLabel1.setHorizontalAlignment(JLabel.LEFT);
        accountLabel1.setVerticalAlignment(JLabel.CENTER);
        accountPanelBlank2.setLayout(new BorderLayout());
        accountPanelBlank2.add(accountLabel1);

        //declare JPanel
        JLabel savingLabel = new JLabel();
        savingLabel.setText("Confirm");
        savingLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        savingLabel.setHorizontalAlignment(JLabel.CENTER);
        savingLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanel1.setLayout(new BorderLayout());
        accountPanel1.add(savingLabel);

        //declare JPanel
        JLabel chequeLabel = new JLabel();
        chequeLabel.setText("Cancel");
        chequeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        chequeLabel.setHorizontalAlignment(JLabel.CENTER);
        chequeLabel.setVerticalAlignment(JLabel.CENTER);
        accountPanel2.setLayout(new BorderLayout());
        accountPanel2.add(chequeLabel);

        //add JPanel
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

        //add JPanel color
        accountPanelBlank1.setBackground(atmBlue);
        accountPanelBlank2.setBackground(atmBlue);
        accountPanel.setBackground(atmBlue);
        accountPanel1.setBackground(choiceBlue);
        accountPanel2.setBackground(choiceBlue);
        accountPanel3.setBackground(atmBlue);
        accountPanel4.setBackground(atmBlue);
        accountPanel5.setBackground(atmBlue);
        accountPanel6.setBackground(atmBlue);
        accountPanel7.setBackground(atmBlue);
        accountPanel8.setBackground(atmBlue);

        //add Panel
        subPanel2.add(accountPanel, BorderLayout.CENTER);
        accountPanel.setVisible(true);
        subPanel2.revalidate();
        subPanel2.repaint();
    }


    // prompt user to enter a deposit amount in cents
    private double promptForTransferAmount(double availableBalance)
    {

        Screen screen = getScreen(); // get reference to screen
        Scanner scan = new Scanner( System.in );

        // display the prompt
        screen.displayMessage( "\nPlease enter a transfer amount which up to 2 decimal places in " +
                "HKD (or 0 to cancel): " );
        transferAmount();//call gui
        while(transGUIReturn1== true){
            try {
                Thread.sleep(100);
                System.out.println(transGUIReturn1);
            } catch (InterruptedException ex) {
            }
        }
        //double input = scan.nextDouble(); // receive input of transfer amount
        double input = check; // receive input of transfer amount

        // check whether the user canceled or entered a valid amount
        while (((input*100)%1 != 0 && input != 0) || input < 0 || input > availableBalance)
        {
            if (input > availableBalance) {
                screen.displayMessageLine("\nInsufficient amount available in your account.\nPlease choose a smaller amount (or 0 to cancel).");
                transNomoneyacc();//call gui
                while(transerror== true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
            }
            else {
                screen.displayMessage("\nInvalid transfer number. Please enter again (or 0 to cancel): ");
                transInvaenagain();//call gui
                while(transerror== true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
            }
            transferAmount();//call gui
            while(transGUIReturn1== true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            //input = scan.nextDouble();
            input = check;
        } // end while

        // return the amount in HKD
        // end else
        return input;
    } // end method promptForTransferAmount

    //method for the buttonHandler
    public class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (transRecreturnchoose == true) {//choose account
                if (e.getActionCommand().equals("B4")) {
                    edit = 2;
                } else if (e.getActionCommand().equals("B0")) {
                    edit = 1;
                }
                else edit = -1;
                transRecreturnchoose = false;
            }//button for side

            //button setting condition
            if(transerror == true && e.getSource().equals(extraButtons[1])){
                transerror = false;
            }
            //button setting condition
            if(transRecreturn == true && e.getSource().equals(extraButtons[1])){
                transRecreturn = false;
            }

            //button setting condition
            if(confirm == true){
                    if (e.getSource().equals(leftButtons[0])) {
                        choice = 1;
                    } else if (e.getSource().equals(rightButtons[0])) {
                        choice = 0;
                    }
                System.out.println(choice);
                confirm = false;
            }

            //keypad setting condition
            for(int i=0;i<=11;i++){//enter amount
                if (e.getSource().equals(extraButtons[2])) {
                    transferTextfield2.setText("");
                    line2 = "";// display line2 in textArea
                }
                else if(e.getActionCommand().equals(names[i])) {
                    line2=line2.concat( e.getActionCommand());
                    transferTextfield2.setText(line2); // display line2 in textArea
                }
                else{
                    line2=line2.concat("");
                }
            }//button setting condition
            if(transGUIReturn == true && e.getSource().equals(extraButtons[1])){
                String input = transferTextfield2.getText();//the input
                receiverAc = Integer.parseInt(input); //for return the value back to withdrawal
                transGUIReturn = false;

            }
            else if (transGUIReturn == true && e.getSource().equals(extraButtons[0])) {//cancel
                canceledNum = 1;
                receiverAc = -1; //for return the value back to withdrawal
                transGUIReturn = false;
            }

            //button setting condition
            for(int i=0;i<=11;i++){//enter amount
                if (e.getSource().equals(extraButtons[2])) {
                    transferTextfield.setText("");
                    line1 = "";
                }
                else if(e.getActionCommand().equals(names[i])) {
                    line1 = line1.concat( e.getActionCommand());
                    transferTextfield.setText(line1); // display line1 in textArea
                }
                else{
                    line1=line1.concat("");
                }
            }
            if(transGUIReturn1 == true && e.getSource().equals(extraButtons[1])){
                String input = transferTextfield.getText();//the input
                check = Double.parseDouble(input); //for return the value back to withdrawal
                transGUIReturn1 = false;

            }
            else if (transGUIReturn1 == true && e.getSource().equals(extraButtons[0])) {//cancel
                canceledNum = 1;
                check = 0; //for return the value back to withdrawal
                transGUIReturn1 = false;
            }
        }
    }
} // end class

