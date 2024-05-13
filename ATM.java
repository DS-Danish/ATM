public class ATM {
    private boolean userAuthenticated; // whether user is authenticated
    private String currentAccountNumber; // current user's account number
    private Screen screen; // ATM's screen
    private Keypad keypad; // ATM's keypad
    private CashDispenser cashDispenser; // ATM's cash dispenser
    private DepositSlot depositSlot; // ATM's deposit slot
    private BankDatabase bankDatabase; // account information database
    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;
    private int accountNumber;
    private int amount;

    // no-argument ATM constructor initializes instance variables
    public ATM() {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = null; // no current account number to start
        screen = new Screen(); // create screen
        keypad = new Keypad(); // create keypad
        cashDispenser = new CashDispenser(); // create cash dispenser
        depositSlot = new DepositSlot(); // create deposit slot
        bankDatabase = new BankDatabase(); // create acct info database
    }

    // start ATM
    public void run() {
        // welcome and authenticate user; perform transactions
        while (true) {
            // loop while user is not yet authenticated
            while (!userAuthenticated) {
                screen.displayMessageLine("\nWelcome!");
                authenticateUser(); // authenticate user
            }

            performTransactions(); // user is now authenticated
            userAuthenticated = false; // reset before next ATM session
            currentAccountNumber = null; // reset before next ATM session
            screen.displayMessageLine("\nThank you! Goodbye!");
        }
    }

    // attempts to authenticate user against database
    private void authenticateUser() {
        screen.displayMessage("\nPlease enter your account number: ");
        String accountNumberStr = keypad.getInput(); // input account number as string
        screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
        String pinStr = keypad.getInput(); // input PIN as string
        // Convert the account number and PIN strings to integers
        int accountNumber = Integer.parseInt(accountNumberStr);
        int pin = Integer.parseInt(pinStr);
        // set userAuthenticated to boolean value returned by database
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
        // check whether authentication succeeded
        if (userAuthenticated) {
            currentAccountNumber = accountNumberStr; // save user's account number
        } else {
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
        }
    }

    // display the main menu and perform transactions
    private void performTransactions() {
        // local variable to store transaction currently being processed
        Transaction currentTransaction = null;

        boolean userExited = false; // user has not chosen to exit

        // loop while user has not chosen option to exit system
        while (!userExited) {
            // show main menu and get user selection
            int mainMenuSelection = displayMainMenu();
            // decide how to proceed based on user's menu selection
            switch (mainMenuSelection) {
                // user chose to perform one of three transaction types
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                    // initialize as new object of chosen type
                    currentTransaction = createTransaction(mainMenuSelection);
                    if (currentTransaction != null) {
                        currentTransaction.execute(); // execute transaction
                    }
                    break;
                case EXIT: // user chose to terminate session
                    screen.displayMessageLine("\nExiting the system...");
                    userExited = true; // this ATM session should end
                    break;
                default: // user did not enter an integer from 1-4
                    screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
                    break;
            }
        }
    }

    // display the main menu and return an input selection
    private int displayMainMenu() {
        screen.displayMessageLine("\nMain menu:");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - Deposit funds");
        screen.displayMessageLine("4 - Exit\n");
        screen.displayMessage("Enter a choice: ");

        // Read the input as a string
        String inputStr = keypad.getInput();

        try {
            // Parse the string input to an integer
            int choice = Integer.parseInt(inputStr);
            return choice;
        } catch (NumberFormatException e) {
            // If parsing fails, handle the error (e.g., return a default choice)
            screen.displayMessageLine("Invalid input. Please enter a number.");
            return -1; // Default choice or error code
        }
    }

    // return object of specified Transaction subclass
    private Transaction createTransaction(int type) {
        Transaction temp = null; // temporary Transaction variable

        // determine which type of Transaction to create
        switch (type) {
            case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                if (currentAccountNumber != null) {
                    temp = new BalanceInquiry(Integer.parseInt(currentAccountNumber), screen, bankDatabase);
                }
                break;
            case WITHDRAWAL: // create new Withdrawal transaction
                if (currentAccountNumber != null) {
                    temp = new Withdrawal(Integer.parseInt(currentAccountNumber), screen, bankDatabase, keypad, cashDispenser);
                }
                break;
            case DEPOSIT: // create new Deposit transaction
                if (currentAccountNumber != null) {
                    temp = new Deposit(Integer.parseInt(currentAccountNumber), screen, bankDatabase, keypad, depositSlot);
                }
                break;
        }
        return temp; // return the newly created object
    }

    public static void main(String[] args) {
        ATM theATM = new ATM();
        theATM.run();
    }
}
