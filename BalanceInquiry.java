// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

import javax.swing.*;
import java.awt.*;

public class BalanceInquiry extends Transaction
{
   private JPanel subPanel2; //panel to display the panel
   // BalanceInquiry constructor
   public BalanceInquiry( int userAccountNumber, Screen atmScreen,
      BankDatabase atmBankDatabase,JPanel Panel )
   {
      super( userAccountNumber, atmScreen, atmBankDatabase );
     subPanel2 = Panel;
   } // end BalanceInquiry constructor

   public int takevalue(){
      return 0;
   }

   // performs the transaction
   public void execute()
   {
      Color atmBlue  = new Color(79, 178, 209);//set the background color
      Color  choiceBlue  = new Color(63, 139, 166);//set the choice color

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();

      // get the available balance for the account involved
      double availableBalance = 
         bankDatabase.getAvailableBalance( getAccountNumber() );

      // get the total balance for the account involved
      double totalBalance = 
         bankDatabase.getTotalBalance( getAccountNumber() );

      //set the border layout of the whole panel
      BorderLayout layout = (BorderLayout)subPanel2.getLayout();
      subPanel2.remove(layout.getLayoutComponent(BorderLayout.CENTER));


      JPanel balancePanel = new JPanel();//set the panel name
      balancePanel.setLayout(new GridLayout(4,1));// set the panel layout



      JLabel title = new JLabel("Balance Information:");//set the output
      title.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
      title.setHorizontalAlignment(JLabel.CENTER);//set the position
      title.setVerticalAlignment(JLabel.CENTER);//set the position
      JPanel accountPanelBlank1 = new JPanel();//set panel name
      accountPanelBlank1.setLayout(new BorderLayout());//set back the panel
      accountPanelBlank1.add(title);//add the panel using
      accountPanelBlank1.setBackground(atmBlue);//add the panel color

      JLabel first = new JLabel("Available balance:  $"+availableBalance);//set the output
      first.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
      first.setHorizontalAlignment(JLabel.CENTER);//set the position
      first.setVerticalAlignment(JLabel.CENTER);//set the position
      JPanel accountPanel1 = new JPanel();//set panel name
      accountPanel1.setLayout(new BorderLayout());//set back the panel
      accountPanel1.setBackground(choiceBlue);//add the panel using
      accountPanel1.add(first);//add the panel color

      JLabel second = new JLabel("Total balance:  $"+totalBalance);//set the output
      second.setFont(new Font("Serif", Font.PLAIN, 30));// set the font
      second.setHorizontalAlignment(JLabel.CENTER);//set the position
      second.setVerticalAlignment(JLabel.CENTER);//set the position

      JPanel accountPanel2 = new JPanel();//set panel name
      accountPanel2.setLayout(new BorderLayout());//set back the panel
      accountPanel2.setBackground(choiceBlue);//add the panel color
      accountPanel2.add(second);//add the panel using

      JLabel messagelabel = new JLabel("Please press \"Enter\" to back.");//set the output
      messagelabel.setFont(new Font("Serif", Font.PLAIN, 25));// set the font
      messagelabel.setHorizontalAlignment(JLabel.RIGHT);//set the position
      messagelabel.setVerticalAlignment(JLabel.CENTER);//set the position


      JPanel accountPanel3 = new JPanel();//set panel name
      accountPanel3.setLayout(new BorderLayout());//set back the panel
      accountPanel3.setBackground(atmBlue);//add the panel color
      accountPanel3.add(messagelabel);//add the panel using

      balancePanel.add(accountPanelBlank1);//add the panel using
      balancePanel.add(accountPanel1);//add the panel using
      balancePanel.add(accountPanel2);//add the panel using
      balancePanel.add(accountPanel3);//add the panel using

      subPanel2.add(balancePanel, BorderLayout.CENTER);//add the panel using
      balancePanel.setVisible(true);
      subPanel2.revalidate();





      // display the balance information on the screen
      screen.displayMessageLine( "\nBalance Information:" );
      screen.displayMessage( " - Available balance: " ); 
      screen.displayDollarAmount( availableBalance );
      screen.displayMessage( "\n - Total balance:     " );
      screen.displayDollarAmount( totalBalance );
      screen.displayMessageLine( "" );
   } // end method execute
} // end class BalanceInquiry



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