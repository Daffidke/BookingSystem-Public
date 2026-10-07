package application.model;

public class Review {
    private int id;
    private int szallasId;
    private int felhasznaloId;
    private String mikor;
    private int ertekeles;
    private String uzenet;

    public Review() {
    }

    public Review(int id, int szallasId, int felhasznaloId, String mikor, int ertekeles, String uzenet) {
        this.id = id;
        this.szallasId = szallasId;
        this.felhasznaloId = felhasznaloId;
        this.mikor = mikor;
        this.ertekeles = ertekeles;
        this.uzenet = uzenet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSzallasId() {
        return szallasId;
    }

    public void setSzallasId(int szallasId) {
        this.szallasId = szallasId;
    }

    public int getFelhasznaloId() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(int felhasznaloId) {
        this.felhasznaloId = felhasznaloId;
    }

    public String getMikor() {
        return mikor;
    }

    public void setMikor(String mikor) {
        this.mikor = mikor;
    }

    public int getErtekeles() {
        return ertekeles;
    }

    public void setErtekeles(int ertekeles) {
        this.ertekeles = ertekeles;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }

    @Override
    public String toString() {
        return "Velemeny{" +
                "id=" + id +
                ", szallasId=" + szallasId +
                ", felhasznaloId=" + felhasznaloId +
                ", mikor='" + mikor + '\'' +
                ", ertekeles=" + ertekeles +
                ", uzenet='" + uzenet + '\'' +
                '}';
    }
}
