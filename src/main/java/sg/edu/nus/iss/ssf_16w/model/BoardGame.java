package sg.edu.nus.iss.ssf_16w.model;

import java.util.Random;

public class BoardGame {
    private int id;
    private String name;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;
    private static final Random RANDOM = new Random();
    
    public BoardGame(int id, String name, int year, int ranking, int users_rated, String url, String image) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.users_rated = users_rated;
        this.url = url;
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                id, name, year, ranking, users_rated, url, image);
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public int getRanking() {return ranking;}
    public void setRanking(int ranking) {this.ranking = ranking;}

    public int getUsers_rated() {return users_rated;}
    public void setUsers_rated(int users_rated) {this.users_rated = users_rated;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    
}
