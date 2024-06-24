package io.mountblue.reddit_clone.dto;

public class VoteResponse {
    private int postId;
    private int upvotes;
    private int downvotes;

    public VoteResponse() {
    }

    public VoteResponse(int postId, int upvotes, int downvotes) {
        this.postId = postId;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    @Override
    public String toString() {
        return "VoteResponse{" +
                "postId=" + postId +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                '}';
    }
}
