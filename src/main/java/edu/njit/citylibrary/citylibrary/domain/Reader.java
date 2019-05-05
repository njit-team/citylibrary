package edu.njit.citylibrary.citylibrary.domain;

import java.util.Date;

public class Reader {

    private int readerId;
    private String rType;
    private String rName;
    private String rAddress;
    private long rPhone;
    private String memStart;
    private Double fine;
    private int numResDocs;
    private int numBorDocs;

    public Reader() {
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public long getrPhone() {
        return rPhone;
    }

    public void setrPhone(long rPhone) {
        this.rPhone = rPhone;
    }

    public String getMemStart() {
        return memStart;
    }

    public void setMemStart(String memStart) {
        this.memStart = memStart;
    }

    public Date getMemStartDate() {
        return new Date(Date.parse(memStart));
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public int getNumResDocs() {
        return numResDocs;
    }

    public void setNumResDocs(int numResDocs) {
        this.numResDocs = numResDocs;
    }

    public int getNumBorDocs() {
        return numBorDocs;
    }

    public void setNumBorDocs(int numBorDocs) {
        this.numBorDocs = numBorDocs;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "readerId=" + readerId +
                ", rType='" + rType + '\'' +
                ", rName='" + rName + '\'' +
                ", rAddress='" + rAddress + '\'' +
                ", rPhone=" + rPhone +
                ", memStart=" + memStart +
                ", fine=" + fine +
                ", numResDocs=" + numResDocs +
                ", numBorDocs=" + numBorDocs +
                '}';
    }
}
