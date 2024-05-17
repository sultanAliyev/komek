package kz.iitu.iitu.service;

import kz.iitu.iitu.entity.Application;
import kz.iitu.iitu.entity.Program;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProgramService programService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              ProgramService programService) {
        this.applicationRepository = applicationRepository;
        this.programService = programService;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplication(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application createApplication(Application application, User user) {
        application.setCreatedAt(LocalDate.now());
        application.setEmail(user.getEmail());
        application.setFirstName(user.getFirstName()==null ? "Firstname was not provided" : user.getFirstName());
        application.setLastName(user.getLastName()==null ? "Lastname was not provided" : user.getLastName());

        Program program = programService.getProgram(application.getProgramRefId());

        if (program == null) {
            throw new RuntimeException("Program was not found with id " + application.getProgramRefId());
        }

        var submittedCount = getAllApplications().stream()
                            .filter(appli -> appli.getProgramRefId().equals(program.getId()))
                            .count() + 1;
        program.setSubmittedCount((int) submittedCount);

        programService.save(program);

        application.setProgram(program);
        applicationRepository.save(application);

        return application;
    }
}
