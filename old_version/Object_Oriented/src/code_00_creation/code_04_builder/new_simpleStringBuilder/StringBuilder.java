package code_00_creation.code_04_builder.new_simpleStringBuilder;

/**
 * Created by 18351 on 2018/12/28.
 */
public class StringBuilder extends AbstractStringBuilder {
    public StringBuilder() {
        super(16);
    }

    @Override
    public String toString() {
        // Create a copy, don't share the array
        return new String(value, 0, count);
    }
}
