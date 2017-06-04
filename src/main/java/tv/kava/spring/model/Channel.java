package tv.kava.spring.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entity class to store values related to current TV channel.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Entity
public class Channel {
    private static final Map<String, String> channelGenres;

    static {
        Map<String, String> genres = new LinkedHashMap<>();
        genres.put("News", "News");
        genres.put("Sport", "Sport");
        genres.put("Generic", "Generic");
        genres.put("Music", "Music");
        genres.put("Science & History", "Science & History");
        genres.put("Movies", "Movies");
        genres.put("Other", "Other");
        channelGenres = genres;
    }

    @Id
    @GeneratedValue
    private int channelId;
    private String channelName;
    private String channelDescription;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "channel")
    @OrderBy("epochTime DESC")
    private List<Program> programs = new ArrayList<>();
    private String genre;

    /**
     * Instantiates a new Channel with specified Name.
     *
     * @param channelName the channel name
     */
    public Channel(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Instantiates a new Channel.
     */
    public Channel() {
    }

    /**
     * Gets possible channel genres.
     *
     * @return the channel genres
     */
    public static Map<String, String> getChannelGenres() {
        return channelGenres;
    }

    /**
     * Filter by program name.
     *
     * @param programName the program name to search for
     * @return the set of programs that match specified name
     */
    public Set<Program> filterByProgram(String programName) {
        return programs.stream().filter(program -> Objects.equals(program.getProgramName(), programName)).collect(Collectors.toSet());
    }

    /**
     * Filter programs by day.
     * Parameter dayOfWeek int values 1-7 represent days Sunday - Saturday eg. 2 = Monday.
     *
     * @param dayOfWeek the day of week to search
     * @return the set
     */
    public Set<Program> filterByDay(int dayOfWeek) {
        return programs.stream().filter(program -> program.getDayOfWeek() == dayOfWeek).collect(Collectors.toSet());
    }

    /**
     * Add program to current channel.
     *
     * @param program the program to add
     */
    public void addProgram(Program program) {
        program.setChannel(this);
        programs.add(program);
    }

    /**
     * Remove program from current channel.
     *
     * @param program the program to remove
     */
    public void removeProgram(Program program) {
        program.removeChannel();
        programs.remove(program);
    }

    /**
     * Gets programs that are being shown on current channel.
     *
     * @return the programs
     */
    public List<Program> getPrograms() {
        return programs;
    }

    /**
     * Sets programs to be shown on channel.
     *
     * @param programs the programs
     */
    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    /**
     * Gets channel id.
     *
     * @return the channel id
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     * Sets channel id.
     *
     * @param channelId the channel id
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     * Gets channel name.
     *
     * @return the channel name
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Sets channel name.
     *
     * @param channelName the channel name
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Gets channel description.
     *
     * @return the channel description
     */
    public String getChannelDescription() {
        return channelDescription;
    }

    /**
     * Sets channel description.
     *
     * @param channelDescription the channel description
     */
    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + channelId +
                ", channelName='" + channelName + '\'' +
                ", channelDescription='" + channelDescription + '\'' +
                ", programs=" + programs +
                ", genre='" + genre + '\'' +
                '}';
    }
}
