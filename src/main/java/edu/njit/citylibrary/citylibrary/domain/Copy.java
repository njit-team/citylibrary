package edu.njit.citylibrary.citylibrary.domain;

public class Copy {
    int docId;
    int copyNo;
    int libId;
    String position;

    public Copy() {
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getCopyNo() {
        return copyNo;
    }

    public void setCopyNo(int copyNo) {
        this.copyNo = copyNo;
    }

    public int getLibId() {
        return libId;
    }

    public void setLibId(int libId) {
        this.libId = libId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
