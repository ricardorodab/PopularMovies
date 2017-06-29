package rodab.ciencias.unam.mx.android.popularmovies.utilities;

/**
 * Created by ricardo_rodab on 27/06/17.
 */

public class Movie {

    private int id;
    private String title;
    private String urlImage;
    private String synopsis;
    private double raking;
    private String date;
    private String[] videos;
    private Review[] reviews;
    private boolean favorite;

    public Movie(int id, String title, String urlImage, String synopsis, double raking, String date) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.synopsis = synopsis;
        this.raking = raking;
        this.date = date;
        this.videos = null;
        this.reviews = null;
        this.favorite = false;
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

    public double getRaking() {
        return raking;
    }

    public void setRaking(double raking) {
        this.raking = raking;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String toString() {
        return this.getId() + " " +
                this.getUrlImage() + " " +
                this.getSynopsis() + " " +
                this.getRaking() + " " +
                this.getDate();
    }

    public String getParserText() {
        return (this.getId() + "-#-" +
                this.getTitle() + "-#-" +
                this.getUrlImage() + "-#-" +
                this.getSynopsis() + "-#-" +
                this.getRaking() + "-#-" +
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
