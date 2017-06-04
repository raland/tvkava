package tv.kava.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.kava.spring.dao.ChannelDAO;
import tv.kava.spring.model.Channel;
import tv.kava.spring.model.Program;

import java.util.List;
import java.util.Map;

/**
 * Service implementation to access Channel-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDAO channelDAO;

    /**
     * Sets channel dao.
     *
     * @param programDAO the program dao
     */
    public void setChannelDAO(ChannelDAO programDAO) {
        this.channelDAO = programDAO;
    }

    @Override
    @Transactional
    public void addChannel(Channel c) {
        channelDAO.addChannel(c);
    }

    @Override
    @Transactional
    public void updateChannel(Channel c) {
        channelDAO.updateChannel(c);
    }

    @Override
    @Transactional
    public List<Channel> listChannels() {
        return channelDAO.listChannels();
    }

    @Override
    @Transactional
    public Channel getChannelById(int id) {
        return channelDAO.getChannelById(id);
    }

    @Override
    @Transactional
    public void removeChannel(int id) {
        channelDAO.removeChannel(id);
    }

    @Override
    @Transactional
    public List<Program> listChannelPrograms(int id) {
        return channelDAO.listChannelPrograms(id);
    }

    @Override
    @Transactional
    public List<Program> listProgramsByDay(int id, int day) {
        return channelDAO.listProgramsByDay(id, day);
    }

    @Override
    public Map<String, String> listChannelGenres() {
        return channelDAO.listGenres();
    }

    @Override
    @Transactional
    public Map<Channel, String> getAvailableChannels() {
        return channelDAO.getAvailableChannels();
    }
}
