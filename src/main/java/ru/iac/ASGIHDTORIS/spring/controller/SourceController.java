package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.source.SourceDataModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final Validator<Source> validator;

    public SourceController(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator
    ) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
    }

    @PostMapping("/create")
    @ResponseBody
    public Source createSource (@ModelAttribute Source source){

        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        return validator.isValid(source)
                ? sourceRepo.save(source)
                : new Source();
    }

    @GetMapping("/{id}")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long)id);
    }

    @GetMapping("/getAll")
    public Page<Source> getAll(@PageableDefault Pageable pageable){
        return sourceRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Source> getAll(@ModelAttribute Source source, @PageableDefault Pageable pageable, @RequestParam String sort, @RequestParam String key) {
        log.info(source.toString());
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).descending());
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).ascending());
        }
        log.info(sourceRepo.findAll(pageable).toString());
        return sourceRepo.findAllNative(
                pageable,
                source.getName(),
                source.getLongName(),
                source.getShortName(),
                source.getDescription(),
                source.getAddDescription(),
                source.getScope(),
                source.getPeriodicity(),
                source.getRenewalPeriod(),
                source.getType(),
                source.getTags(),
                source.getProviderLink(),
                source.getDataSource()
//                source.getDateCreation(),
//                sourceDataModel.getDateCreation1(),
//                sourceDataModel.getDateCreation2(),
//                source.getDateDeactivation(),
//                sourceDataModel.getDateDeactivation1(),
//                sourceDataModel.getDateDeactivation2(),
//                source.getDateActivation(),
//                sourceDataModel.getDateActivation1(),
//                sourceDataModel.getDateActivation2(),
//                source.getLastUpdate(),
//                sourceDataModel.getLastUpdate1(),
//                sourceDataModel.getLastUpdate2(),
//                source.getIsArchive()
        );

    }

    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault Pageable pageable){
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault Pageable pageable){
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(true);
            source.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(false);
            source.setDateActivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @PostMapping("/update")
    @ResponseBody
    public Source update(@ModelAttribute Source source) {

        if (sourceRepo.existsById(source.getId()) && validator.isValid(source)) {
            sourceRepo.save(source);
            source.setLastUpdate(LocalDateTime.now());

            return source;
        }

        return new Source();
    }

}
