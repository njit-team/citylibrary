package edu.njit.citylibrary.citylibrary.domain;

public class TopTenBorrowedDocument {
    private String title;
    private int count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
