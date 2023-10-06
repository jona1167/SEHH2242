import java.util.Date;
import java.text.SimpleDateFormat;
//the subclass under account
public class SavingAccount extends Account {

    private double interestRate ;//set the intereset rate
    private double savingBalance;//set the saving balance
    // saving account constructor invoked by subclasses using super()
    public SavingAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        setSavingBalance(theTotalBalance);
        setInterestRate(0.001);//set rete to 0.1%
    }// end saving account constructor

    //set saving balance and check the balance whether larger than 0
    public void setSavingBalance(double balance){
        if( balance >= 0.0)//set if-else function for check the balance whether it is larger than 0
            savingBalance = balance;
        else
            throw new IllegalArgumentException("Saving Balance must be >= 0.0");
    }// end method SavingBalance

    //return the saving balance
    public double getSavingBalance(){
        return savingBalance;
    }  // end method getSavingBalance

    //set the interest rate
    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    } //end method set Interest Rate

    //return the Interest Rate
    public double getInterestRate() {
        return interestRate;
    }// end method get interest rate
}// end class saving account
