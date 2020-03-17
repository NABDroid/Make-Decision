package bd.com.nabdroid.makedecision;

public class VoteTime {
    private Long endTime;
    private int voteId;

    public VoteTime() {
    }

    public VoteTime(Long endTime, int voteId) {
        this.endTime = endTime;
        this.voteId = voteId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
}
