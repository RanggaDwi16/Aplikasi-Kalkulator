package com.example.kalkulatoruas;

public class ListItem {

    private final String Number1;
    private final String Operasi;
    private final String Number2;
    private final String Hasil;

    public ListItem(String number1, String operasi, String number2, String hasil) {
        Number1 = number1;
        Operasi = operasi;
        Number2 = number2;
        Hasil = hasil;
    }

    public String getNumber1() {
        return Number1;
    }

    public String getOperasi() {
        return Operasi;
    }

    public String getNumber2() {
        return Number2;
    }

    public String getHasil() {
        return Hasil;
    }


}
