import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public interface ISetValue {
    //метод для установки переданного значения переданному полю
    default void SetValue(Field field, String value) throws IllegalAccessException {
        if (value == null)
            field.set(this, null);
        else if (field.getType() == String.class)
            field.set(this, value);
        else if (field.getType() == Integer.class)
            field.set(this, Integer.parseInt(value));
        else if (field.getType() == Double.class)
            field.set(this, Double.parseDouble(value));
        else if (field.getType() == Long.class)
            field.set(this, Long.parseLong(value));
    }

    default void SetDefaultValue(Field field, String value, Logger log) {
        try {
            SetValue(field, null);
        } catch (IllegalAccessException ex) {
            System.out.println("Некорректный тип данных (подробнее см. log-файл).");
            log.log(Level.ERROR, ex);
        }
    }
}