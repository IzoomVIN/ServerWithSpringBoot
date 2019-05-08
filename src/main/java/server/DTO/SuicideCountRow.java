package server.DTO;

public class SuicideCountRow {
    private int year;
    private String country;
    private int suicidesCount;

    public SuicideCountRow(int year, String country, int suicidesCount) {
        this.country = country;
        this.year = year;
        this.suicidesCount = suicidesCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSuicidesCount() {
        return suicidesCount;
    }

    public void setSuicidesCount(int suicidesCount) {
        this.suicidesCount = suicidesCount;
    }
}
