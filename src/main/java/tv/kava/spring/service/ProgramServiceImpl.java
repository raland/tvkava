package tv.kava.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.kava.spring.dao.ProgramDAO;
import tv.kava.spring.model.Program;

import java.util.List;
import java.util.Map;

/**
 * Service implementation to access Program-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramDAO programDAO;

    /**
     * Sets program dao.
     *
     * @param programDAO the program dao
     */
    public void setProgramDAO(ProgramDAO programDAO) {
        this.programDAO = programDAO;
    }

    @Override
    @Transactional
    public void addProgram(Program p) {
        programDAO.addProgram(p);
    }

    @Override
    @Transactional
    public void updateProgram(Program p) {
        programDAO.updateProgram(p);
    }

    @Override
    @Transactional
    public List<Program> listPrograms() {
        return programDAO.listPrograms();
    }

    @Override
    @Transactional
    public Program getProgramById(int id) {
        return programDAO.getProgramById(id);
    }

    @Override
    @Transactional
    public void removeProgram(int id) {
        programDAO.removeProgram(id);
    }

    @Override
    @Transactional
    public List<Program> searchByName(String name) {
        return programDAO.searchByName(name);
    }

    @Override
    @Transactional
    public List<Program> searchByType(String type, long startDate, long endDate) {
        return programDAO.searchByType(type, startDate, endDate);
    }

    @Override
    public Map<String, String> listProgramTypes() {
        return programDAO.listProgramTypes();
    }
}
