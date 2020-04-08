package bd.com.nabdroid.makedecision.pojo;

public class Comment {
    String  commentWritterName, postId, commentFor, comment, createTime;

    public String getCommentWritterName() {
        return commentWritterName;
    }

    public void setCommentWritterName(String commentWritterName) {
        this.commentWritterName = commentWritterName;
    }

    public Comment() {

    }

    public Comment(String commentWritterName, String postId, String commentFor, String comment, String createTime) {
        this.commentWritterName = commentWritterName;
        this.postId = postId;
        this.commentFor = commentFor;
        this.comment = comment;
        this.createTime = createTime;
    }




    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentFor() {
        return commentFor;
    }

    public void setCommentFor(String commentFor) {
        this.commentFor = commentFor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String creationTime) {
        this.createTime = creationTime;
    }

}
