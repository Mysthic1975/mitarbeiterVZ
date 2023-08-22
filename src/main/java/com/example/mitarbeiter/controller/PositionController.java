package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.PositionEntity;
import com.example.mitarbeiter.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/position")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public String getAllPositions(Model model) {
        List<PositionEntity> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);
        return "position/list";
    }

    @GetMapping("/create")
    public String showCreatePositionForm(Model model) {
        model.addAttribute("position", new PositionEntity());
        return "position/create";
    }

    @PostMapping("/create")
    public String createPosition(@ModelAttribute PositionEntity position) {
        positionService.savePosition(position);
        return "redirect:/position";
    }

    @GetMapping("/{id}/edit")
    public String showEditPositionForm(@PathVariable Long id, Model model) {
        PositionEntity position = positionService.getPositionById(id);
        model.addAttribute("position", position);
        return "position/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPosition(@ModelAttribute PositionEntity position, @PathVariable Long id) {
        positionService.updatePosition(position, id);
        return "redirect:/position";
    }

    @GetMapping("/{id}/delete")
    public String deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return "redirect:/position";
    }
}
