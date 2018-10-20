package com.khieuthichien.qunlsinhvin.model;

public class Lop {
    private int idlop;
    private String malop;
    private String tenlop;

    public Lop(int idlop, String malop, String tenlop) {
        this.idlop = idlop;
        this.malop = malop;
        this.tenlop = tenlop;
    }

    public Lop() {
    }

    public int getIdlop() {
        return idlop;
    }

    public void setIdlop(int idlop) {
        this.idlop = idlop;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }
}
