package code_01_activity.code_07_state.vote;

import java.util.HashMap;
import java.util.Map;

/**
 * 投票管理器
 */
public class VoteManager {
    //持有状态处理对象
    private VoteState state = null;

    //统计用户投票数
    private Map<String,Integer> mapVoteCount = new HashMap<String,Integer>();

    public Map<String,Integer> getMapVoteCount(){
        return mapVoteCount;
    }

    /**
     * 投票
     * @param user 投票人，为了简单，就是用户名称
     */
    public void vote(String user){
        //1：先为该用户增加投票的次数
        int voteCount=mapVoteCount.getOrDefault(user,0);
        mapVoteCount.put(user, ++voteCount);

        //2：判断该用户投票的类型，就相当于是判断对应的状态
        //到底是正常投票、重复投票、恶意投票还是上黑名单的状态
        if(voteCount==1){
            state = new NormalVoteState();
        }else if(voteCount>1 && voteCount<5){
            state = new RepeatVoteState();
        }else if(voteCount >= 5 && voteCount<8){
            state = new SpiteVoteState();
        }else if(voteCount>=8){
            state = new BlackVoteState();
        }
        //然后转调状态对象来进行相应的操作
        state.vote(user, this);
    }
}
