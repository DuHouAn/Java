package code_01_activity.code_07_state.vote;

/**
 * 封装一个投票状态相关的行为
 */
public interface VoteState {
    /**
     * TODO:处理状态对应的行为
     * @param voter 投票人
     * @param voteManager 投票上下文，用来在实现状态对应的功能处理的时候,可以回调上下文的数据
     */
    void vote(String voter, VoteManager voteManager);
}
