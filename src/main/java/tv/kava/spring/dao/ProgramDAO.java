package tv.kava.spring.dao;


import tv.kava.spring.model.Program;

import java.util.List;
import java.util.Map;

/**
 * Data access object interface to access Program-related data.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
public interface ProgramDAO {

    /**
     * Add program.
     *
     * @param program the program
     */
    void addProgram(Program program);

    /**
     * Update program.
     *
     * @param program the program
     */
    void updateProgram(Program program);

    /**
     * List all programs.
     *
     * @return the list
     */
    List<Program> listPrograms();

    /**
     * Gets program by id.
     *
     * @param id the id
     * @return the program by id
     */
    Program getProgramById(int id);

    /**
     * Remove program.
     *
     * @param id the id of program to remove
     */
    void removeProgram(int id);

    /**
     * Search for all programs by the same name.
     *
     * @param name the name of program
     * @return the list of results
     */
    List<Program> searchByName(String name);

    /**
     * Search programs by their type in a time frame.
     *
     * @param type      the type of program
     * @param startDate the start date in epoch time
     * @param endDate   the end date in epoch time
     * @return the list
     */
    List<Program> searchByType(String type, long startDate, long endDate);

    /**
     * List program types in a map.
     *
     * @return the map of program types
     */
    Map<String, String> listProgramTypes();
}