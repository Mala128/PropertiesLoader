import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Company implements ISetValue {
    private static Company instance;
    private Properties properties;
    private static final Logger log = Logger.getLogger(Company.class);
    @Property(propertyName = "myCompanyName")
    private String myCompanyName;
    @Property(propertyName = "myCompanyOwner", defaultValue = "Default owner")
    private String myCompanyOwner;
    @Property(propertyName = "address")
    private Address address;

    //region getters setters
    public String getMyCompanyName() {
        return myCompanyName;
    }

    public void setMyCompanyName(String myCompanyName) {
        this.myCompanyName = myCompanyName;
    }

    public String getMyCompanyOwner() {
        return myCompanyOwner;
    }

    public void setMyCompanyOwner(String myCompanyOwner) {
        this.myCompanyOwner = myCompanyOwner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    //endregion

    private Company(String path) {
        this.address = new Address();
        doRefresh(path);
    }

    //метод реализующий паттерн проектирования Singleton
    public static Company getInstance(String path) {
        if (instance == null)
            instance = new Company(path);
        return instance;
    }

    //метод для считывания списка свойств(ключ-значение) из файла
    public void load(InputStream io) {
        try {
            this.properties = new Properties();
            this.properties.load(io);
        } catch (IOException e) {
            log.log(Level.ERROR, e);
        }
    }

    //метод для получения значения свойства по ключу
    public String getValue(String key) {
        return this.properties.getProperty(key);
    }

    //метод заполнения аннотированных полей класса из файла *.properties
    public synchronized void doRefresh(String path) {
        File file = new File(path);
        try {
            FileInputStream io = new FileInputStream(file);
            load(io);
            io.close();
        } catch (IOException e) {
            System.out.println("Невозможно открыть указанный файл " + file + " (подробнее см. log-файл).");
            log.log(Level.ERROR, e);
            return;
        }
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                String valueProperty = field.getAnnotation(Property.class).propertyName();
                String defaultProperty = field.getAnnotation(Property.class).defaultValue();
                String value = getValue(valueProperty);
                if (value == null)
                    value = defaultProperty;
                if (field.getType() == String.class || field.getType() == Integer.class || field.getType() == Double.class || field.getType() == Long.class || value.equals("null")) {
                    try {
                        field.setAccessible(true);
                        SetValue(field, value);
                    } catch (IllegalAccessException e) {
                        try {
                            log.log(Level.ERROR, e);
                            SetValue(field, null);
                        } catch (IllegalAccessException ex) {
                            System.out.println("Некорректный тип данных (подробнее см. log-файл).");
                            log.log(Level.ERROR, ex);
                        }
                    }
                } else {
                    try {
                        JsonElement jsonParser = new JsonParser().parse(value);
                        JsonObject jsonObject = jsonParser.getAsJsonObject();
                        Method setJSONValue = field.getType().getMethod("SetValueField", JsonObject.class);
                        setJSONValue.invoke(this.address, jsonObject);
                    } catch (IllegalStateException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | JsonSyntaxException e) {
                        if (e instanceof JsonSyntaxException)
                            System.out.println("Некорректный json (подробнее см. log-файл):\n" + value);
                        else if (e instanceof NoSuchMethodException)
                            System.out.println("Вызываемый метод не найден (подробнее см. log-файл).");
                        else if (e instanceof InvocationTargetException)
                            System.out.println("Вызываемый метод отработал с ошибками (подробнее см. log-файл).");
                        log.log(Level.ERROR, e);
                        SetDefaultValue(field, null, log);
                    }
                }
            }
        }
    }
}


