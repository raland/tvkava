package tv.kava.spring.controller;

import org.hibernate.Hibernate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tv.kava.spring.model.Channel;
import tv.kava.spring.model.Program;
import tv.kava.spring.service.ChannelService;
import tv.kava.spring.service.ProgramService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controller to manage views and requests.
 *
 * @author Raul Land
 * @version 1.0
 * @since 1 /29/17
 */
@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProgramService programService;

    /**
     * Sets channel service.
     *
     * @param channelService the channel service
     */
    @Autowired()
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    /**
     * Sets program service.
     *
     * @param programService the program service
     */
    @Autowired()
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    /**
     * Index page.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Model model) {
        Map<String, String> genres = channelService.listChannelGenres();
        Map<String, String> types = programService.listProgramTypes();
        model.addAttribute("channel", new Channel());
        model.addAttribute("genreList", genres);
        model.addAttribute("typeList", types);
        return "index";
    }

    /**
     * List channels.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    public String listChannels(Model model) {
        model.addAttribute("channel", new Channel());
        model.addAttribute("listChannels", channelService.listChannels());
        return "channels";
    }

    /**
     * Add channel.
     *
     * @param channel the channel to add.
     * @return the string
     */
    @RequestMapping(value = "/channel/add", method = RequestMethod.POST)
    public String addChannel(@ModelAttribute("channel") Channel channel) {
        this.channelService.addChannel(channel);
        return "redirect:/channels";
    }

    /**
     * Remove channel.
     *
     * @param id the id
     * @return the string
     */
    @RequestMapping("/remove/{id}")
    public String removeChannel(@PathVariable("id") int id) {
        this.channelService.removeChannel(id);
        return "redirect:/channels";
    }

    /**
     * Edit channel.
     *
     * @param id    the id of channel
     * @param model the model
     * @return the string
     */
    @RequestMapping("/channel/{id}")
    @Transactional
    public String editChannel(@PathVariable("id") int id, Model model) {
        Channel channel = channelService.getChannelById(id);
        Map<String, String> types = programService.listProgramTypes();
        List<List<Program>> programs = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            programs.add(channelService.listProgramsByDay(id, i));
            programs.get(programs.size() - 1).forEach(Program::convertDate);
            Collections.sort(programs.get(programs.size() - 1));
        }
        Hibernate.initialize(programs);
        model.addAttribute("typeList", types);
        model.addAttribute("channel", channel);
        model.addAttribute("program", new Program());
        model.addAttribute("sundayList", programs.get(0));
        model.addAttribute("mondayList", programs.get(1));
        model.addAttribute("tuesdayList", programs.get(2));
        model.addAttribute("wednesdayList", programs.get(3));
        model.addAttribute("thursdayList", programs.get(4));
        model.addAttribute("fridayList", programs.get(5));
        model.addAttribute("saturdayList", programs.get(6));
        return "channel";
    }

    /**
     * Add program to channel.
     *
     * @param program the program
     * @param id      the id of channel
     * @return the string
     */
    @RequestMapping(value = "/program/add", method = RequestMethod.POST)
    @Transactional
    public String addProgram(@ModelAttribute("program") Program program, @ModelAttribute("channel") int id) {
        Channel channel = channelService.getChannelById(id);
        channel.addProgram(program);
        channelService.updateChannel(channel);
        this.programService.addProgram(program);
        return "redirect:/channel/" + id;
    }

    /**
     * Remove program string.
     *
     * @param programId the program id
     * @return the string
     */
    @RequestMapping("/program/remove/{id}")
    @Transactional
    public String removeProgram(@PathVariable("id") int programId) {
        Program program = programService.getProgramById(programId);
        Channel channel = program.getChannel();
        int channelId = channel.getChannelId();
        channel.removeProgram(program);
        programService.removeProgram(programId);
        return "redirect:/channel/" + channelId;
    }

    /**
     * Edit program.
     *
     * @param programId the program id
     * @param model     the model
     * @return the string
     */
    @RequestMapping("/program/edit/{id}")
    @Transactional
    public String editProgram(@PathVariable("id") int programId, Model model) {
        Program program = this.programService.getProgramById(programId);
        Channel channel = program.getChannel();
        model.addAttribute("channel", channel);
        model.addAttribute("program", program);
        model.addAttribute("listChannels", channelService.listChannels());
        model.addAttribute("typeList", programService.listProgramTypes());
        model.addAttribute("channelList", channelService.getAvailableChannels());
        model.addAttribute("repeatingPrograms", programService.searchByName(program.getProgramName()));
        return "editprogram";
    }

    /**
     * Update program data.
     *
     * @param programId         the program id to edit
     * @param program           the program object
     * @param previousChannelId the previous channel id the program was attached to
     * @param newId             the new channel to attatch program to.
     * @return the string
     */
    @RequestMapping(value = "/program/update/{id}", method = RequestMethod.POST)
    @Transactional
    public String updateProgram(
            @PathVariable("id") int programId, @ModelAttribute("program") Program program,
            @RequestParam(value = "previousId") int previousChannelId, @RequestParam(value = "channels") int newId) {
        Channel newChannel = channelService.getChannelById(newId);
        program.setChannel(newChannel);
        programService.updateProgram(program);
        return "redirect:/channel/" + previousChannelId;
    }

    /**
     * Search programs used in AJAX call.
     *
     * @param type      the type of program to search for
     * @param startdate the startdate in searchable time frame
     * @param enddate   the enddate in searchable time frame
     * @return the string
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/programs/search/bytype/", method = RequestMethod.POST)
    public
    @ResponseBody
    String searchPrograms(@RequestParam String type, @RequestParam long startdate, @RequestParam long enddate) {
        List<Program> results = programService.searchByType(type, startdate, enddate);
        Collections.sort(results);
        JSONArray jsonArray = new JSONArray();
        for (Program program : results) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("programName", program.getProgramName());
            jsonObject.put("channelName", program.getChannel().getChannelName());
            jsonObject.put("startTime", program.getStartTime().toString());
            jsonObject.put("channelId", program.getChannel().getChannelId());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

}