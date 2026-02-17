package com.example.conflicttracker.controller;

import com.example.conflicttracker.model.Conflict;
import com.example.conflicttracker.model.ConflictStatus;
import com.example.conflicttracker.service.ConflictService;
import com.example.conflicttracker.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web")
public class ThymeleafConflictController {

    @Autowired
    private ConflictService conflictService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/conflicts")
    public String listAllConflicts(Model model) {
        List<Conflict> conflicts = conflictService.findAll();

        model.addAttribute("conflicts", conflicts);
        model.addAttribute("totalConflicts", conflictService.count());
        model.addAttribute("activeConflicts", conflictService.countActive());
        model.addAttribute("currentView", "llistat");

        return "list";  // Buscarà list.html directament a templates/
    }

    @GetMapping("/conflicts/new")
    public String showCreateForm(Model model) {
        Conflict conflict = new Conflict();
        conflict.setStartDate(LocalDate.now());
        conflict.setStatus(ConflictStatus.ACTIVE);

        model.addAttribute("conflict", conflict);
        model.addAttribute("allCountries", countryService.findAll());
        model.addAttribute("formAction", "create");
        model.addAttribute("allStatuses", ConflictStatus.values());

        return "form";  // Buscarà form.html directament a templates/
    }

    @GetMapping("/conflicts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,
                               RedirectAttributes redirectAttributes) {

        return conflictService.findById(id)
                .map(conflict -> {
                    model.addAttribute("conflict", conflict);
                    model.addAttribute("allCountries", countryService.findAll());
                    model.addAttribute("formAction", "update");
                    model.addAttribute("allStatuses", ConflictStatus.values());
                    return "form";  // Buscarà form.html directament a templates/
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error",
                            "No s'ha trobat el conflicte amb ID: " + id);
                    return "redirect:/web/conflicts";
                });
    }

    @PostMapping("/conflicts/create")
    public String createConflict(@Valid @ModelAttribute Conflict conflict,
                                 BindingResult result,
                                 @RequestParam(required = false) List<Long> selectedCountries,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("allCountries", countryService.findAll());
            model.addAttribute("allStatuses", ConflictStatus.values());
            model.addAttribute("formAction", "create");
            return "form";  // Buscarà form.html directament a templates/
        }

        if (selectedCountries != null && !selectedCountries.isEmpty()) {
            selectedCountries.forEach(countryId ->
                    countryService.findById(countryId).ifPresent(conflict::addCountry)
            );
        }

        try {
            Conflict savedConflict = conflictService.save(conflict);
            redirectAttributes.addFlashAttribute("success",
                    "Conflicte \"" + savedConflict.getName() + "\" creat correctament!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error en crear el conflicte: " + e.getMessage());
        }

        return "redirect:/web/conflicts";
    }

    @PostMapping("/conflicts/update/{id}")
    public String updateConflict(@PathVariable Long id,
                                 @Valid @ModelAttribute Conflict conflict,
                                 BindingResult result,
                                 @RequestParam(required = false) List<Long> selectedCountries,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("allCountries", countryService.findAll());
            model.addAttribute("allStatuses", ConflictStatus.values());
            model.addAttribute("formAction", "update");
            return "form";  // Buscarà form.html directament a templates/
        }

        conflict.setId(id);
        conflict.getCountries().clear();

        if (selectedCountries != null && !selectedCountries.isEmpty()) {
            selectedCountries.forEach(countryId ->
                    countryService.findById(countryId).ifPresent(conflict::addCountry)
            );
        }

        try {
            Conflict updatedConflict = conflictService.save(conflict);
            redirectAttributes.addFlashAttribute("success",
                    "Conflicte \"" + updatedConflict.getName() + "\" actualitzat correctament!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error en actualitzar el conflicte: " + e.getMessage());
        }

        return "redirect:/web/conflicts";
    }

    @PostMapping("/conflicts/delete/{id}")
    public String deleteConflict(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            String conflictName = conflictService.findById(id)
                    .map(Conflict::getName)
                    .orElse("Conflicte desconegut");

            conflictService.deleteById(id);
            redirectAttributes.addFlashAttribute("success",
                    "Conflicte \"" + conflictName + "\" eliminat correctament!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error en eliminar el conflicte: " + e.getMessage());
        }

        return "redirect:/web/conflicts";
    }

    @GetMapping("/conflicts/status/{status}")
    public String listByStatus(@PathVariable ConflictStatus status, Model model) {
        List<Conflict> conflicts = conflictService.findByStatus(status);

        model.addAttribute("conflicts", conflicts);
        model.addAttribute("currentFilter", status);
        model.addAttribute("totalConflicts", conflicts.size());
        model.addAttribute("activeConflicts", conflictService.countActive());
        model.addAttribute("currentView", "filtrat");

        return "list";  // Buscarà list.html directament a templates/
    }

    @GetMapping("/conflicts/{id}")
    public String viewConflictDetail(@PathVariable Long id,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        return conflictService.findById(id)
                .map(conflict -> {
                    model.addAttribute("conflict", conflict);
                    model.addAttribute("countries", conflict.getCountries());
                    return "detail";  // Buscarà detail.html directament a templates/
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error",
                            "No s'ha trobat el conflicte amb ID: " + id);
                    return "redirect:/web/conflicts";
                });
    }

    @GetMapping("/test")
    public String testThymeleaf(Model model) {
        model.addAttribute("message", "✅ Thymeleaf funciona correctament!");
        model.addAttribute("timestamp", LocalDate.now());
        model.addAttribute("totalConflicts", conflictService.count());
        return "test";  // Buscarà test.html directament a templates/
    }

    @GetMapping("/simple")
    public String simple() {
        return "simple";  // Buscarà simple.html directament a templates/
    }
    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        return "DEBUG: El controlador funciona";
    }
}