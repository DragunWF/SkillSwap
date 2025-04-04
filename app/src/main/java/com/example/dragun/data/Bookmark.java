package com.example.dragun.data;

public class Bookmark extends Model {
    private int userId;
    private Posting posting;

    public Bookmark(int userId, Posting posting) {
        this.userId = userId;
        this.posting = posting;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "userId=" + userId +
                ", posting=" + posting +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }
}
