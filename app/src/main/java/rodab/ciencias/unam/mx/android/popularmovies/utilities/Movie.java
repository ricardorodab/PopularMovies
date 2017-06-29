package rodab.ciencias.unam.mx.android.popularmovies.utilities;

/**
 * @author Jose Ricardo Rodriguez-Abreu
 * @version 1.0
 * @since Jun 26 2017.
 * <p>
 *    A movie model class.
 * </p>
 *
 * <p>
 *     For a good MVC model, this class model a Movie for our app.
 * </p>
 */
public class Movie {

    // This must be unique.
    private int id;
    private String title;
    // This variable is for fetch the image in the api.
    private String urlImage;
    private String synopsis;
    // Number between 0-10.
    private double raking;
    private String date;
    //This can be null if the movie doesn't have videos.
    private String[] videos;
    //This can be null if the movie doesn't have reviews.
    private Review[] reviews;
    //If this movie is in the db, is a favorite, we add this value.
    private boolean favorite;

    /**
     * It's just easier to have all the data we want to fetch in a object, a Movie object.
     * Not all the attributes are listed in this default constructor.
     *
     * @param id - The unique identifier of the movie.
     * @param title - Every movie must have a title, otherwise we have information incomplete.
     * @param urlImage - Because is easy use picasso, just save the url of the image as String.
     * @param synopsis - The synopsis says a little more about the movie plots.
     * @param raking - The average of votes. A digit between 0 and 10 is the usual.
     * @param date - The date the film was released.
     */
    public Movie(int id, String title,String urlImage,String synopsis, double raking, String date) {
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

    /**
     * Return the id of the film.
     *
     * @return the id of the movie.
     */
    public int getId() {
        return id;
    }

    /**
     * This method set a new movie.
     *
     * @param id The new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the title's film.
     *
     * @return the title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method set a new movie title.
     *
     * @param title the new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the  url of the image's film.
     *
     * @return the url image of the movie.
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * This method set a new movie url image.
     *
     * @param urlImage The new url image.
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * Return the the synopsis String of the film.
     *
     * @return The movie synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * This method set a new movie synopsis.
     *
     * @param synopsis The new synopsis.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the ranking of the film.
     *
     * @return The movie's ranking.
     */
    public double getRaking() {
        return raking;
    }

    /**
     * This method set a new movie
     *
     * @param raking the new ranking.
     */
    public void setRaking(double raking) {
        this.raking = raking;
    }

    /**
     * Return the date release of the film.
     *
     * @return The date release as a String.
     */
    public String getDate() {
        return date;
    }

    /**
     * This method set a new movie date.
     *
     * @param date The new date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Return the array of url for the videos of the film.
     *
     * @return the collections of video in a String array.
     */
    public String[] getVideos() {
        return videos;
    }

    /**
     * This method set a new movie set of videos.
     *
     * @param videos The new set of videos.
     */
    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    /**
     * Return the set of reviews of the film.
     *
     * @return the collections of reviews in a String array.
     */
    public Review[] getReviews() {
        return reviews;
    }

    /**
     * This method set a new movie set of reviews.
     *
     * @param reviews The new collection of reviews.
     */
    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    /**
     * Return if is a favorite film.
     *
     * @return True if it's favorite.
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * This method set a new movie favorite state.
     *
     * @param favorite The new state.
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * The String representation of the film.
     *
     * @return The Movie ready to be printed.
     */
    public String toString() {
        return this.getId() + " " +
                this.getUrlImage() + " " +
                this.getSynopsis() + " " +
                this.getRaking() + " " +
                this.getDate();
    }

    /**
     * I needed a way to parse a film when passing a resources and I was waaay to lazy. :(
     *
     * @return A string representation of the movie with some "-#-" strings between each attr.
     */
    public String getParserText() {
        return (this.getId() + "-#-" +
                this.getTitle() + "-#-" +
                this.getUrlImage() + "-#-" +
                this.getSynopsis() + "-#-" +
                this.getRaking() + "-#-" +
                this.getDate());
    }

    /**
     * This method restore a movie from the string to parser data.
     *
     * @param text The string representation ready to be parser.
     * @return the movie created again.
     */
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

} //End of Movie.java