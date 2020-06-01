import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    private static void HelpMessage(){
        System.out.println("В качестве аргумента jar-файлу необходимо передать файл со значениями полей класса, например:");
        System.out.println("java -jar PropertiesLoader.jar Company.properties");
        System.out.println("Программа выводит прочитанные поля из файла *.properties. Если поле не задано или некорректно задано (и при этом не имеет значения по умолчанию), то выводится null.");
    }
    private static void PrintFields(Company company){
        System.out.println("myCompanyName: " + company.getMyCompanyName());
        System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
        System.out.println("address.street: " + company.getAddress().getStreet());
        System.out.println("address.house: " + company.getAddress().getHouse());
    }
    public static void main(String[] args) {
        if (args.length > 0) {
            if(args[0].equals("-?") || args[0].equals("/?") || args[0].equals("-h") || args[0].equals("-help") || args[0].equals("--help")) {
                HelpMessage();
                return;
            }
            try {
                Company company = Company.getInstance(args[0]);
                System.out.println("\nВходной файл: " + args[0] + "\nПоля:");
                PrintFields(company);
            } catch (Exception e) {
                HelpMessage();
                log.log(Level.ERROR, e);
            }
        } else {
            HelpMessage();
        }
    }
}

