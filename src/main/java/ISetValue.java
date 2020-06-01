import java.lang.reflect.Field;

public interface ISetValue {
    //метод для установки переданного значения переданному полю
    default public void SetValue(Field field, String value) throws IllegalAccessException {
        if(value == null)
            field.set(this, null);
        else if(field.getType() == String.class)
            field.set(this, value);
        else if(field.getType() == Integer.class)
            field.set(this, Integer.parseInt(value));
        else if(field.getType() == Double.class)
            field.set(this, Double.parseDouble(value));
        else if(field.getType() == Long.class)
            field.set(this, Long.parseLong(value));
    }
}