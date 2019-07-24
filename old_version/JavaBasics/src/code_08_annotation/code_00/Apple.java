package code_08_annotation.code_00;

/**
 * 定义一个实例类
 * 这里使用注解来初始化
 */
public class Apple {

    @FruitName(fruitName = "苹果")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.红色)
    private String appleColor;

    @FruitProvider(id=1,name="红富士",address="陕西省西安市延安路89号红富士大厦")
    private String appleProvider;

    public String getAppleName() {
        return appleName;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }
}
