import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Company company = Company.getInstance(args[0]);
            System.out.println("Path: " + args[0]);
            System.out.println("myCompanyName: " + company.getMyCompanyName());
            System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
            System.out.println("address.street: " + company.getAddress().getStreet());
            System.out.println("address.house: " + company.getAddress().getHouse() + "\n");
            for (int i = 1; i < args.length; i++) {
                company.doRefresh(args[i]);
                System.out.println("Path: " + args[i]);
                System.out.println("myCompanyName: " + company.getMyCompanyName());
                System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
                System.out.println("address.street: " + company.getAddress().getStreet());
                System.out.println("address.house: " + company.getAddress().getHouse() + "\n");
            }

        } catch (Exception e) {
            System.out.println("Для корректного запуска программы необходимо передать параметры (путь до файла *.properties)");
            System.out.println("-jar Task1.jar (путь до файла *.properties)");
            System.out.println("При передаче нескольких путей программа будет вызывать метод doRefresh() столько раз, сколько будет передано параметров");
            System.out.println("Результат работы: демонстрация заполнения полей соответствующими значениями");
            log.log(Level.ERROR, e);
        }
    }
}

