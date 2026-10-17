package com.pupr.Dto;

public class DashBoardResponse {
    private String jenisTransaksi;
    private String nilaiTransaksi;

    public DashBoardResponse(String jenisTransaksi, String nilaiTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
        this.nilaiTransaksi = nilaiTransaksi;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public String getNilaiTransaksi() {
        return nilaiTransaksi;
    }

    public void setNilaiTransaksi(String nilaiTransaksi) {
        this.nilaiTransaksi = nilaiTransaksi;
    }
}
