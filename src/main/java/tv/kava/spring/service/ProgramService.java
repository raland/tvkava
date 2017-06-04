package tv.kava.spring.service;


import tv.kava.spring.model.Program;

import java.util.List;
import java.util.Map;

/**
 * Service interface to access Program-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
public interface ProgramService {

    /**
     * Add program.
     *
     * @param program the p
     */
    public void addProgram(Program program);

    /**
     * Update program.
     *
     * @param program the p
     */
    public void updateProgram(Program program);

    /**
     * List all programs.
     *
     * @return the list
     */
    public List<Program> listPrograms();

    /**
     * Gets program by id.
     *
     * @param id the id
     * @return the program by id
     */
    public Program getProgramById(int id);

    /**
     * Remove program.
     *
     * @param id the id of program to remove
     */
    public void removeProgram(int id);

    /**
     * Search by name list.
     *
     * @param name the name
     * @return the list
     */
    public List<Program> searchByName(String name);

    /**
     * Search programs by their type in a time frame.
     *
     * @param type      the type of program
     * @param startDate the start date in epoch time
     * @param endDate   the end date in epoch time
     * @return the list
     */
    public List<Program> searchByType(String type, long startDate, long endDate);

    /**
     * List program types in a map.
     *
     * @return the map of program types
     */
    Map<String, String> listProgramTypes();

}
