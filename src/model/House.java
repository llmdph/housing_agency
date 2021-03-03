package model;

public class House {
    private int id;
    private String name;
    private String phone;
    private String site;
    private int area;
    private String direct;
    private int price;
    private int rs;
    private int state;
    private String time;

    public House() {
    }

    public House(int id, String name, String phone, String site, int area,
                 String direct, int price, int rs, int state, String time) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.site = site;
        this.area = area;
        this.direct = direct;
        this.price = price;
        this.rs = rs;
        this.state = state;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
