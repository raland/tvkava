package tv.kava.spring.service;


import tv.kava.spring.model.Channel;
import tv.kava.spring.model.Program;

import java.util.List;
import java.util.Map;

/**
 * Service interface to access Channel-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
public interface ChannelService {
    /**
     * Add channel.
     *
     * @param channel the channel
     */
    void addChannel(Channel channel);

    /**
     * Update channel.
     *
     * @param channel the channel
     */
    void updateChannel(Channel channel);

    /**
     * List channels list.
     *
     * @return the list
     */
    List<Channel> listChannels();

    /**
     * Gets channel by id.
     *
     * @param id the channel id
     * @return the channel by id
     */
    Channel getChannelById(int id);

    /**
     * Remove channel.
     *
     * @param id the id of the channel to remove
     */
    void removeChannel(int id);

    /**
     * List all programs in a specified channel.
     *
     * @param id channel id
     * @return the list of programs aired on channel
     */
    List<Program> listChannelPrograms(int id);

    /**
     * List programs aired on channel by day of week.
     *
     * @param id  the id of channel.
     * @param day the day of week. Values 1-7 represent days Sunday-Saturday.
     * @return the list
     */
    List<Program> listProgramsByDay(int id, int day);

    /**
     * List genres possible genres for channels.
     *
     * @return the map of genres
     */
    Map<String, String> listChannelGenres();

    /**
     * Gets available channel objects with their names in a map.
     *
     * @return the available channels
     */
    Map<Channel, String> getAvailableChannels();
}
