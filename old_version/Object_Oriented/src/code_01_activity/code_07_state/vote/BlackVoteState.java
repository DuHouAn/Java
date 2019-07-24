package code_01_activity.code_07_state.vote;

/**
 * 黑名单
 * 记入黑名单中，禁止登录系统了
 */
public class BlackVoteState implements VoteState{
    @Override
    public void vote(String voter, VoteManager voteManager) {
        System.out.println("进入黑名单，将禁止登录和使用本系统");
    }
}
