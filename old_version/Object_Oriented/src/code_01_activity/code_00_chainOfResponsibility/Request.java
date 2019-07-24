package code_01_activity.code_00_chainOfResponsibility;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Request {
    private RequestType type;
    private String name;


    public Request(RequestType type, String name) {
        this.type = type;
        this.name = name;
    }


    public RequestType getType() {
        return type;
    }


    public String getName() {
        return name;
    }
}
