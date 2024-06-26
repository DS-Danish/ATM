public class CashDispenser {
    private final static int INITIAL_COUNT = 500; // the default initial number of bills in the cash dispenser
    private int count; // number of $20 bills remaining

    // no-argument CashDispenser constructor initializes count to default
    public CashDispenser() {
        count = INITIAL_COUNT; // set count attribute to default
    }

    // simulates dispensing of specified amount of cash
    public void dispenseCash(int amount) {
        int billsRequired = amount / 20; // number of $20 bills required
        count -= billsRequired; // update the count of bills
    }

    // indicates whether cash dispenser can dispense desired amount
    public boolean isSufficientCashAvailable(int amount) {
        int billsRequired = amount / 20; // number of $20 bills required
        return count >= billsRequired; // enough bills available
    }
}
