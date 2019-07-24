package code_01_activity.code_07_state.vote;

/**
 * Created by 18351 on 2018/12/31.
 */
public class Client {
    public static void main(String[] args) {
        VoteManager vm = new VoteManager();
        for (int i = 0; i < 9; i++) {
            vm.vote("u1");
        }
    }
}
