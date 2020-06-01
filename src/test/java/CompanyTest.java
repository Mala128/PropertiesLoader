import org.junit.Test;

public class CompanyTest {
    //данный тест демонстрирует то, что нельзя создать 2 объекта класса Company
    //реализован паттерн проектирования - Singleton
    @Test
    public void CheckingTheDesignPatternSingleton() {
        Company company = Company.getInstance("FilesProperty\\test.properties");
        System.out.println("myCompanyName: " + company.getMyCompanyName());
        System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
        System.out.println("address.street: " + company.getAddress().getStreet());
        System.out.println("address.house: " + company.getAddress().getHouse() + "\n");
        Company company1 = Company.getInstance("FilesProperty\\test1.properties");
        System.out.println("myCompanyName: " + company1.getMyCompanyName());
        System.out.println("myCompanyOwner: " + company1.getMyCompanyOwner());
        System.out.println("address.street: " + company1.getAddress().getStreet());
        System.out.println("address.house: " + company1.getAddress().getHouse() + "\n");
    }

    //данный тест домонстрирует работоспособность метода doRefresh()
    @Test
    public void ValidationDoRefresh() {
        Company company = Company.getInstance("FilesProperty\\test.properties");
        System.out.println("myCompanyName: " + company.getMyCompanyName());
        System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
        System.out.println("address.street: " + company.getAddress().getStreet());
        System.out.println("address.house: " + company.getAddress().getHouse() + "\n");
        company.doRefresh("FilesProperty\\test1.properties");
        System.out.println("myCompanyName: " + company.getMyCompanyName());
        System.out.println("myCompanyOwner: " + company.getMyCompanyOwner());
        System.out.println("address.street: " + company.getAddress().getStreet());
        System.out.println("address.house: " + company.getAddress().getHouse() + "\n");
    }

    //данный тест показывает корректную обработку некорректного файла
    @Test
    public void ChekingInvalidFile() {
        Company company = Company.getInstance("FilesProperty\\test23.properties");
    }
}