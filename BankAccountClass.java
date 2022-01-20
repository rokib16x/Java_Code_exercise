class BankAccount {
    private String accName;
    private String accId;
    private double balance;
    BankAccount(String accName, String accId, double balance) {
        this.accName=accName;
        this.accId=accId;
        this.balance=balance;
    }

    void deposit(double amount)
    {
        balance = balance + amount;
    }

    void withdraw(double amount)
    {
        if (balance>=amount)
            balance = balance - amount;
    }

    void checkBalance()
    {
        System.out.println("Latest Balance: "+this.balance);
    }
    void printInfo(){
        System.out.println("Name of the User: "+accName+"\nAccount number of the user: "+accId+"\nCurrent Balance:"+balance);
    }

}

public class DemoOfBankAccount {
    public static void main(String[] args) {
        BankAccount bank= new BankAccount("Adolf Fiasco","1122",20000.00);
        bank.printInfo();
        bank.deposit(1500);
        bank.withdraw(7000);
        bank.checkBalance();
    }
}
