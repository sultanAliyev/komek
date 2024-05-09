package kz.iitu.iitu.controller;

import kz.iitu.iitu.dto.TransactionDto;
import kz.iitu.iitu.entity.Program;
import kz.iitu.iitu.entity.ProgramType;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.service.ProgramService;
import kz.iitu.iitu.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;
    private final TransactionService transactionService;

    private final String Volunteering = "volunteering";
    private final String Monetary = "monetary";
    private final String RallyPoint = "rallypoint";

    public ProgramController(ProgramService programService,
                             TransactionService transactionService) {
        this.programService = programService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Program> getAllPrograms(@RequestParam("type") String type) {

        if (type.equals(Volunteering)) {
           return programService.getAllPrograms().stream()
                    .filter(program -> program.getType()
                                              .equals(ProgramType.Volunteering))
                    .toList();
        } else if (type.equals(Monetary)) {
            return programService.getAllPrograms().stream()
                                 .filter(program -> program.getType()
                                                           .equals(ProgramType.Monetary))
                                 .toList();
        } else if (type.equals(RallyPoint)) {
            return programService.getAllPrograms().stream()
                                 .filter(program -> program.getType()
                                                           .equals(ProgramType.RallyPoint))
                                 .toList();
        }

        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    public Program getProgram(@PathVariable Long id) {
        return programService.getProgram(id);
    }

    @PostMapping
    public Program createNews(@RequestBody Program program) {
        return programService.createProgram(program);
    }

    @PostMapping("/{id}/donation")
    public TransactionDto donation(@PathVariable Long id,
                                   @RequestBody Transaction transaction) {
        programService.donate(id, transaction);
        return transactionService.createTransaction(transaction);
    }

}
