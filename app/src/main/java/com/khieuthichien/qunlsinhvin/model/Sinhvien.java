package com.khieuthichien.qunlsinhvin.model;

public class Sinhvien {
    private String masinhvien;
    private String tensinhvien;
    private String malop;

    public Sinhvien(String masinhvien, String tensinhvien, String malop) {
        this.masinhvien = masinhvien;
        this.tensinhvien = tensinhvien;
        this.malop = malop;
    }

    public Sinhvien() {
    }

    public String getMasinhvien() {
        return masinhvien;
    }

    public void setMasinhvien(String masinhvien) {
        this.masinhvien = masinhvien;
    }

    public String getTensinhvien() {
        return tensinhvien;
    }

    public void setTensinhvien(String tensinhvien) {
        this.tensinhvien = tensinhvien;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }
}
