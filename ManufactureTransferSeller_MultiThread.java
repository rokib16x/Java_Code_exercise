import java.util.Scanner;

class Fiasco {
    final int manufactureCapacity = MainThread.manufactureCapacity;
    final int transCapacity = MainThread.transCapacity;
    final int sellerCapacity = MainThread.sellerCapacity;
    int manufactureCapacityTemp = 0;
    int transCapacityTemp = 0;
    boolean sellerPickup = true;

    //Method for Manufacture Class
    synchronized void manufactureMethod() {
        if (manufactureCapacity == manufactureCapacityTemp) {
            try {
                System.out.println("WareHouse is Full");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        manufactureCapacityTemp++;
        System.out.println("Manufacturer produced a smartphone, Warehouse cap: : " + manufactureCapacityTemp);
        notify();
    }

    //Method for Transportation Class
    synchronized void transMethod() {
        if (manufactureCapacityTemp == 0) {
            try {
                System.out.println("Manufacture warehouse is Empty ");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (transCapacityTemp == transCapacity) {
            try {
                System.out.println("Truck is Full");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        manufactureCapacityTemp--;
        transCapacityTemp++;
        System.out.println("Truck picked one from the warehouse, Warehouse cap:" + manufactureCapacityTemp + ", Truck loaded: " + transCapacityTemp);
        notify();
    }

    //Method for Seller1 and Seller2 Class

    synchronized void sellerMethod() {
        if (manufactureCapacity == manufactureCapacityTemp) {
            if (sellerCapacity <= transCapacityTemp) {
                transCapacityTemp -= MainThread.sellerCapacity;
                if (sellerPickup) {
                    System.out.println("Truck Reaches to Seller 1 and Seller Got " + sellerCapacity + " Smartphones");
                    sellerPickup = false;
                } else if (!sellerPickup) {
                    System.out.println("Truck Reaches to Seller 2 and Seller Got " + sellerCapacity + " Smartphones");
                    sellerPickup = true;
                }

            }
            notify();
        }
    }

}

class Manufacture implements Runnable {
    Fiasco fc;

    Manufacture(Fiasco fc) {
        this.fc = fc;
        new Thread(this, "Manufacture").start();
    }

    public void run() {
        while (true) {
            fc.manufactureMethod();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class TransX implements Runnable {
    Fiasco fc;

    TransX(Fiasco fc) {
        this.fc = fc;
        new Thread(this, "Transportation").start();
    }

    public void run() {
        while (true) {
            fc.transMethod();
        }
    }
}

class Seller1 implements Runnable {
    Fiasco fc;

    Seller1(Fiasco fc) {
        this.fc = fc;
        new Thread(this, "Seller 1").start();
    }

    public void run() {
        while (true) {
            fc.sellerMethod();
        }
    }
}

class Seller2 implements Runnable {
    Fiasco fc;

    Seller2(Fiasco fc) {
        this.fc = fc;
        new Thread(this, "Seller 2").start();
    }

    public void run() {
        while (true) {
            fc.sellerMethod();        }
    }
}

public class MainThread {
    static int manufactureCapacity, transCapacity, sellerCapacity;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Manufacture Warehouse Capacity: ");
        manufactureCapacity = sc.nextInt();

        System.out.print("TransX Warehouse Capacity: ");
        transCapacity = sc.nextInt();

        System.out.print("Seller  Capacity: ");
        sellerCapacity = sc.nextInt();

        Fiasco fc = new Fiasco();

        new Manufacture(fc);
        new TransX(fc);

        new Seller1(fc);
        new Seller2(fc);

    }
}
