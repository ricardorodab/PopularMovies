package utilities;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class Movie {

    private int id;
    private String title;
    private String urlImage;
    private String synopsis;
    private double rating;
    private String date;

    public Movie(int id, String title, String urlImage, String synopsis, double rating, String date) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.synopsis = synopsis;
        this.rating = rating;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return this.getId() + " " +
                this.getUrlImage() + " " +
                this.getSynopsis() + " " +
                this.getRating() + " " +
                this.getDate();
    }

    public String getParserText() {
        return (this.getId() + "-#-" +
                this.getTitle() + "-#-" +
                this.getUrlImage() + "-#-" +
                this.getSynopsis() + "-#-" +
                this.getRating() + "-#-" +
                this.getDate());
    }

    public static Movie getMovieFromParserText(String text) {
        String regex = new String("-#-");
        String[] tokens = text.split(regex);
        if(tokens.length != 6)
            return null;
        return (new Movie(Integer.parseInt(tokens[0]),
                        tokens[1],tokens[2],
                        tokens[3],Double.parseDouble(tokens[4]),
                        tokens[5]));
    }

}
