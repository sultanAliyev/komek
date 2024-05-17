package kz.iitu.iitu.service;

import kz.iitu.iitu.dto.ProgramDto;
import kz.iitu.iitu.entity.Program;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public List<ProgramDto> getProgramsByApplicationEmail(String email) {
        return programRepository.findAll().stream()
                                .map(program -> mapToProgramDto(program, email))
                                .collect(toList());
    }

    public Program getProgram(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    public Program createProgram(Program program) {
        program.setCreatedAt(LocalDate.now());
        return programRepository.save(program);
    }

    public void donate(Long id, Transaction transaction) {
        var program = this.getProgram(id);

        program.setCurrentAmount(transaction.getAmount() + program.getCurrentAmount());

        programRepository.save(program);
    }

    public void save(Program program) {
        this.programRepository.save(program);
    }

    private ProgramDto mapToProgramDto(Program program, String email) {
        ProgramDto dto = new ProgramDto();

        dto.setId(program.getId());
        dto.setType(program.getType());
        dto.setTitle(program.getTitle());
        dto.setAddress(program.getAddress());
        dto.setDescription(program.getDescription());
        dto.setCreatedAt(program.getCreatedAt());
        dto.setPosterUrl(program.getPosterUrl());
        dto.setMaxAmount(program.getMaxAmount());
        dto.setCurrentAmount(program.getCurrentAmount());
        dto.setLatitude(program.getLatitude());
        dto.setLongitude(program.getLongitude());
        dto.setSubmittedCount(program.getSubmittedCount());

        boolean hasMatchingApplication = program.getApplications().stream()
                                                .anyMatch(application -> application.getEmail().equals(email));

        dto.setSubmited(hasMatchingApplication);

        return dto;
    }

}
