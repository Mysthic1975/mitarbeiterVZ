package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.entity.PositionEntity;
import com.example.mitarbeiter.entity.ProfilePictureEntity;
import com.example.mitarbeiter.service.EmployeeService;
import com.example.mitarbeiter.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionService positionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    @GetMapping
    public String getAllEmployees(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  @RequestParam(required = false) String search) {

        Page<EmployeeEntity> employees;

        if (search != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection.equals("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
            employees = employeeService.searchEmployees(search, pageable);
        } else {
            employees = employeeService.getAllEmployeesSortedBy(sortBy, sortDirection, page, size);
        }


        model.addAttribute("employees", employees.getContent());
        model.addAttribute("page", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("search", search);
        model.addAttribute("sortField", sortBy);

        return "employee/list";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee/view";
    }


    @PostMapping
    public String saveEmployee(@ModelAttribute EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());

        List<PositionEntity> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);

        return "employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    @Transactional
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);

        List<PositionEntity> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);

        String base64Code = null;
        if (employee.getProfilePicture() != null) {
            base64Code = employee.getProfilePicture().getPictureBase64Encoded();
        }
        model.addAttribute("imageBase64Src", base64Code);

        return "employee/edit";
    }

    @PostMapping("/{id}/edit")
    public String editEmployee(
            @PathVariable Long id,
            @ModelAttribute EmployeeEntity employee,
            @RequestParam("picture") MultipartFile pictureFile
    ) {
        employeeService.updateEmployee(employee, id, pictureFile);
        return "redirect:/employee/" + id;
    }

    @GetMapping(value = "/{id}/profile-picture", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public @ResponseBody byte[] getEmployeeProfilePicture(@PathVariable Long id) {
        ProfilePictureEntity image = employeeService.getEmployeeById(id).getProfilePicture();
        if (image != null) {
            try {
                return image.getPictureData().getBinaryStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no image");
    }

    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam String search,
                                  Model model) {
        Pageable pageable = Pageable.ofSize(10);

        Page<EmployeeEntity> employees = employeeService.searchEmployees(search, pageable);
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("page", employees.getNumber());
        return "employee/list";
    }
}
