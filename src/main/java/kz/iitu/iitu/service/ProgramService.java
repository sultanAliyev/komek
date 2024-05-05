package kz.iitu.iitu.service;

import kz.iitu.iitu.entity.Program;
import kz.iitu.iitu.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Program getProgram(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    public Program createProgram(Program program) {
        program.setCreatedAt(LocalDate.now());
        return programRepository.save(program);
    }

}
