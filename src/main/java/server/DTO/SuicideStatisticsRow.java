package server.DTO;

public class SuicideStatisticsRow {

    private String country;
    private int year;
    private String sex;
    private String age;
    private int suicidesCount;
    private int population;
    private double suicidesTo100KPopulation;

    public SuicideStatisticsRow() {
    }

    public SuicideStatisticsRow(
            String country, int year, String sex,
            String age, int suicidesCount, int population,
            double suicidesTo100KPopulation) {

        this.country = country;
        this.year = year;
        this.sex = sex;
        this.age = age;
        this.suicidesCount = suicidesCount;
        this.population = population;
        this.suicidesTo100KPopulation = suicidesTo100KPopulation;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSuicidesCount() {
        return suicidesCount;
    }

    public void setSuicidesCount(int suicidesCount) {
        this.suicidesCount = suicidesCount;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getSuicidesTo100KPopulation() {
        return suicidesTo100KPopulation;
    }

    public void setSuicidesTo100KPopulation(double suicidesTo100KPopulation) {
        this.suicidesTo100KPopulation = suicidesTo100KPopulation;
    }

    //        this.country = country;
//        this.year = year;
//        this.sex = sex;
//        this.age = age;
//        this.suicidesCount = suicidesCount;
//        this.population = population;
//        this.suicidesTo100KPopulation = suicidesTo100KPopulation;
}

