package kz.iitu.iitu.service;

import kz.iitu.iitu.entity.Application;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplication(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application createApplication(Application application, User user) {
        application.setCreatedAt(LocalDate.now());
        application.setSubmited(true);
        application.setEmail(user.getEmail());
        application.setFirstName(user.getFirstName()==null ? "Firstname was not provided" : user.getFirstName());
        application.setLastName(user.getLastName()==null ? "Lastname was not provided" : user.getLastName());

        return applicationRepository.save(application);
    }
}
