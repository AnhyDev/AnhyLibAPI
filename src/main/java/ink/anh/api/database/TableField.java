package ink.anh.api.database;

/**
 * Represents a field in a database table with a key, field name, and field value.
 *
 * @param <T> the type of the key.
 */
public class TableField<T> {

    private T key;
    private String fieldName;
    private String fieldValue;

    /**
     * Constructs an instance of {@code TableField} with the specified key, field name, and field value.
     *
     * @param key the key associated with the field.
     * @param fieldName the name of the field.
     * @param fieldValue the value of the field.
     */
    public TableField(T key, String fieldName, String fieldValue) {
        this.key = key;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Gets the key associated with the field.
     *
     * @return the key associated with the field.
     */
    public T getKey() {
        return key;
    }

    /**
     * Sets the key associated with the field.
     *
     * @param key the key to be set.
     */
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * Gets the name of the field.
     *
     * @return the name of the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the name of the field.
     *
     * @param fieldName the name to be set.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the value of the field.
     *
     * @return the value of the field.
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the value of the field.
     *
     * @param fieldValue the value to be set.
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
