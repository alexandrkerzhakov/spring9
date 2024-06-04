package ru.gb.virusTotal.entityController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.virusTotal.entity.ipVotes.IpVotesEntity;
import ru.gb.virusTotal.entityService.VirusTotalEntityService;
import ru.gb.virusTotal.service.VirusTotalService;

import java.util.List;

@Controller
@RequestMapping("/virusTotalEntity")
@AllArgsConstructor
@Tag(name = "virusTotalEntity", description = "Save, Delete, View operation for IpVotesEntity")
public class VirusTotalEntityController {
    @Autowired
    private VirusTotalService virusTotalService;
    @Autowired
    VirusTotalEntityService virusTotalEntityService;

    @PostMapping("/saveIpVotes")
    @Operation(summary = "Save IpVotesEntity", description = "Save, operation for IpVotesEntity.")
    public String saveIpVotes(@ModelAttribute("json") String json) {
        System.out.println("json" + json);
        IpVotesEntity ipVotesEntityInstance = virusTotalService.getInstance(json, IpVotesEntity.class);
        System.out.println("ipVotesEntityInstance" + ipVotesEntityInstance);
        virusTotalEntityService.save(ipVotesEntityInstance);

        return "redirect:/virusTotalEntity/allIpVotes";
    }

    @GetMapping("deleteIpVotes/{id}")
    @Operation(summary = "Delete IpVotesEntity by id", description = "Delete for IpVotesEntity.")
    public String deleteUser(@PathVariable("id") Long id) {
        virusTotalEntityService.deleteById(id);

        return "redirect:/virusTotalEntity/allIpVotes";
    }

    @GetMapping("/allIpVotes")
    @Operation(summary = "Find All IpVotesEntity", description = "View operation for IpVotesEntity.")
    public String findByAll(Model model) {
        List<IpVotesEntity> ipVotesEntityList = virusTotalEntityService.findAll();
        System.out.println("ipVotesEntityList" + ipVotesEntityList);
        model.addAttribute("ipVotesEntityList", ipVotesEntityList);

        return "virus_total/ip_votes_list";
    }
}