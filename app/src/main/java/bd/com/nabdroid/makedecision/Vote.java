package bd.com.nabdroid.makedecision;

public class Vote {
    private String topic, creatorId, creatorName, endtimeString;
    private int yesVote, noVote, voteCode;
    long endTime;



    public Vote(int voteCode, String topic, String creatorId, String creatorName, long endTime, int yesVote, int noVote, String endtimeString) {
        this.voteCode = voteCode;
        this.topic = topic;
        this.creatorId = creatorId;
        this.endTime = endTime;
        this.yesVote = yesVote;
        this.noVote = noVote;
        this.creatorName = creatorName;
        this.endtimeString = endtimeString;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Vote() {
    }

    public int getVoteCode() {
        return voteCode;
    }

    public void setVoteCode(int voteCode) {
        this.voteCode = voteCode;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getYesVote() {
        return yesVote;
    }

    public void setYesVote(int yesVote) {
        this.yesVote = yesVote;
    }

    public int getNoVote() {
        return noVote;
    }

    public void setNoVote(int noVote) {
        this.noVote = noVote;
    }

    public String getEndtimeString() {
        return endtimeString;
    }

    public void setEndtimeString(String endtimeString) {
        this.endtimeString = endtimeString;
    }

}
