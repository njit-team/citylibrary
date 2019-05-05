package edu.njit.citylibrary.citylibrary.domain;

public class Branch {
    int libID;
    String lName;
    String lLocation;

    public int getLibID() {
        return libID;
    }

    public void setLibID(int libID) {
        this.libID = libID;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlLocation() {
        return lLocation;
    }

    public void setlLocation(String lLocation) {
        this.lLocation = lLocation;
    }
}
