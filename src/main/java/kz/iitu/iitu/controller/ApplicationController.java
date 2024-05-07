package kz.iitu.iitu.controller;

import kz.iitu.iitu.entity.Application;
import kz.iitu.iitu.service.ApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public Application getApplication(@PathVariable Long id) {
        return applicationService.getApplication(id);
    }

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

}
