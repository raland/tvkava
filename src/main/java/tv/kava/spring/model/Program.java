package tv.kava.spring.model;


import javax.persistence.*;
import java.util.*;

/**
 * Entity class to store values for a single TV program.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Entity
public class Program implements Comparator<Program>, Comparable<Program> {
    private static final Map<String, String> programTypes;

    static {
        Map<String, String> types = new LinkedHashMap<>();
        types.put("Sport", "Sport");
        types.put("Sitcom", "Sitcom");
        types.put("Documentary", "Documentary");
        types.put("Cartoon", "Cartoon");
        types.put("Drama", "Drama");
        types.put("News", "News");
        types.put("Talk Show", "Talk Show");
        types.put("Music", "Music");
        types.put("Lifestyle", "Lifestyle");
        types.put("Other", "Other");
        programTypes = Collections.unmodifiableMap(types);
    }

    @Id
    @GeneratedValue
    private int programId;
    private String programName;
    private String programType;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Channel channel;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    //in minutes
    private int programLength;
    private Long epochTime;


    /**
     * Instantiates a new Program.
     *
     * @param programName   the program name
     * @param programLength the program length
     */
    public Program(String programName, int programLength) {
        this.programName = programName;
        this.programLength = programLength;
    }

    /**
     * Instantiates a new Program.
     */
    public Program() {
    }

    /**
     * Gets program types.
     *
     * @return the program types
     */
    public static Map<String, String> getProgramTypes() {
        return programTypes;
    }

    /**
     * Gets epoch time of startTime.
     *
     * @return the epoch time
     */
    public Long getEpochTime() {
        return epochTime;
    }

    /**
     * Sets epoch time.
     *
     * @param epochtime the epochtime
     */
    public void setEpochTime(Long epochtime) {
        this.epochTime = epochtime;
    }

    /**
     * Gets channel where current program is aired on.
     *
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Sets channel where program is aired on.
     *
     * @param channel the channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * Remove airing channel from current program.
     */
    public void removeChannel() {
        channel = null;
    }

    /**
     * Gets program id.
     *
     * @return the program id
     */
    public int getProgramId() {
        return programId;
    }

    /**
     * Sets program id.
     *
     * @param programId the program id
     */
    public void setProgramId(int programId) {
        this.programId = programId;
    }

    /**
     * Gets program name.
     *
     * @return the program name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets program name.
     *
     * @param programName the program name
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Gets program type.
     *
     * @return the program type
     */
    public String getProgramType() {
        return programType;
    }

    /**
     * Sets program type.
     *
     * @param programType the program type
     */
    public void setProgramType(String programType) {
        this.programType = programType;
    }

    /**
     * Gets program length.
     *
     * @return the program length
     */
    public int getProgramLength() {
        return programLength;
    }

    /**
     * Sets program length.
     *
     * @param programLength the program length
     */
    public void setProgramLength(int programLength) {
        this.programLength = programLength;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the day of week when the program is aired on.
     *
     * @return the day of week
     */
    public int getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Converts epochTime to Date Object.
     */
    public void convertDate() {
        if (epochTime != null) {
            startTime = new Date(epochTime);
        }
    }

    @Override
    public int compare(Program p1, Program p2) {
        return (int) (p1.getEpochTime() - p2.getEpochTime());
    }

    @Override
    public int compareTo(Program program) {
        return this.startTime.compareTo(program.getStartTime());
    }
}
