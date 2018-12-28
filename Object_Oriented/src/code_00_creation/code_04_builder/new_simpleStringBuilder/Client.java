package code_00_creation.code_04_builder.new_simpleStringBuilder;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Client {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        final int count = 26;
        for (int i = 0; i < count; i++) {
            sb.append((char) ('a' + i));
        }
        System.out.println(sb.toString());
    }
}
