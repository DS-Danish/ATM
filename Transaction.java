public abstract class Transaction {
    // attributes
    private int accountNumber; // indicates account involved
    private Screen screen; // ATM’s screen
    private BankDatabase bankDatabase; // account info database

    // Constructor
    public Transaction(int accountNumber, Screen screen, BankDatabase bankDatabase) {
        this.accountNumber = accountNumber;
        this.screen = screen;
        this.bankDatabase = bankDatabase;
    }

    // return account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // return reference to screen
    public Screen getScreen() {
        return screen;
    }

    // return reference to bank database
    public BankDatabase getBankDatabase() {
        return bankDatabase;
    }

    // abstract method overridden by subclasses
    public abstract void execute();
}