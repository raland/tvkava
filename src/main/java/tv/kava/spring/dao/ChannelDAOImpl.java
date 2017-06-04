package tv.kava.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tv.kava.spring.model.Channel;
import tv.kava.spring.model.Program;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access object implementation to access Channel-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Repository
@Transactional
public class ChannelDAOImpl implements ChannelDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Sets session factory.
     *
     * @param sessionFactory the session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addChannel(Channel c) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(c);
    }

    @Override
    public void updateChannel(Channel c) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(c);
    }

    @Override
    public List<Channel> listChannels() {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "from tv.kava.spring.model.Channel";
        List<Channel> channelList = session.createQuery(hql).list();
        for (Channel p : channelList) {
            // TODO: 22/01/17 something
        }
        return channelList;
    }

    @Override
    public Channel getChannelById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.load(Channel.class, id);
    }

    @Override
    public void removeChannel(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Channel c = session.load(Channel.class, id);
        if (null != c) {
            session.delete(c);
        }
    }

    @Override
    public List<Program> listChannelPrograms(int id) {
        return getChannelById(id).getPrograms();
    }

    @Override
    public List<Program> listProgramsByDay(int id, int day) {
        return new ArrayList<>(getChannelById(id).filterByDay(day));
    }

    @Override
    public Map<String, String> listGenres() {
        return Channel.getChannelGenres();
    }

    @Override
    public Map<Channel, String> getAvailableChannels() {
        Map<Channel, String> availableChannels = new LinkedHashMap<>();
        this.listChannels().forEach(channel1 -> availableChannels.put(channel1, channel1.getChannelName()));
        return availableChannels;
    }


}
