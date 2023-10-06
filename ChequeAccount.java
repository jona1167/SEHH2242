public class ChequeAccount extends Account {

    private double chequeBalance;
    private int chequeAccount;
    private int chequeLimit;
    //ChequeAccount constructor

    public ChequeAccount(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
        setChequeLimit(10000);
        setChequeAccount(theAccountNumber);

    }//end of ChequeAccount constructor

    public void setChequeLimit(int checkLimit){
        this.chequeLimit = checkLimit;
    }

    public int getChequeLimit(){
        return chequeLimit;
    }

    //set cheque account value(receiver account number)
    public void setChequeAccount(int chequeAccount) {
        if( chequeAccount >= 0.0)//set if-else function for check the balance whether it is larger than 0
            this.chequeAccount = chequeAccount;
        else
            throw new IllegalArgumentException("Cheque account Balance must be >= 0.0");
    }

    //get cheque account value
    public int getChequeAccount() {
        return chequeAccount;
    }

    //set cheque balance and check the balance whether larger than 0
    public void setChequeBalance(double chequeBalance){
       this.chequeBalance = chequeBalance;
    }// end method chequeBalance

    //return cheque balance
    public double getChequeBalance() {
        return chequeBalance;
    }//end method get cheque balance
}