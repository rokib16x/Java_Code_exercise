
class Salaried{
    private String name;
    private String id;
    private double salary;
    public Salaried(String name, String id){
        setName(name);
        setId(id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String phoneNumber) {
        this.id = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    void printInfo(){
    }
}
class HourlySalaried extends Salaried{
    private double SalaryPerHour;
    private double NoOfHours;

    public HourlySalaried(String name, String id,double SalaryPerHour, double NoOfHours) {
        super(name, id);
        setSalaryPerHour(getSalaryPerHour()+ SalaryPerHour);
        setNoOfHours(getNoOfHours()+ NoOfHours);
    }

    public double getSalaryPerHour() {
        return SalaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        SalaryPerHour = salaryPerHour;
    }

    public double getNoOfHours() {
        return NoOfHours;
    }

    public void setNoOfHours(double noOfHours) {
        NoOfHours = noOfHours;
    }

    public void salary()
    {
        setSalary(SalaryPerHour*NoOfHours);
    }
    void printInfo(){
        salary();
        System.out.println("Name: "+getName()+"; ID: "+getId()+"\nSalary is: "+getSalary());
        System.out.println();
    }
}

class Commissioned extends Salaried{
    private double baseSalary;
    private double commission;
    public Commissioned(String name, String phoneNumber, double baseSalary,int sold) {
        super(name, phoneNumber);
        this.baseSalary=baseSalary;
        this.commission=sold*40;
    }

    public double salary(){
        setSalary(baseSalary + commission);
        return 0;
    }
    void printInfo(){
        salary();
        System.out.println("Name: "+getName()+"; ID: "+getId()+"\nSalary is: "+getSalary());
        System.out.println();
    }

}

public class EmployeeRecordSystem {
    public static void main( String args[] )
    {
        Salaried[] employee = new HourlySalaried[2];
        employee[0]= new HourlySalaried("Rokibul Hasan","1122",2000,18);
        employee[1]= new HourlySalaried("Fiasco"       ,"1133",1200,20);
        System.out.println("--Hourly Salaried Job Holder--");
        for(int i=0; i<employee.length;i++){
            employee[i].printInfo();
        }
        Salaried[] employee1= new Commissioned[3];
        employee1[0]= new Commissioned("Adolf", "2244",22000,200);
        employee1[1]= new Commissioned("Andres","2255",25000,150);
        employee1[2]= new Commissioned("Meao",  "2266",28000,300);
        System.out.println("\n--Commissioned Salaried Job Holder--");
        for(int i=0; i<employee1.length;i++){
            employee1[i].printInfo();
        }
    }
}

