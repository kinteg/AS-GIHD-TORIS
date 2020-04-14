package ru.iac.ASGIHDTORIS.spring.service.patterTable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.repo.TableRepo;
import ru.iac.ASGIHDTORIS.spring.service.table.TableCreatorService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatternTableServiceImpl implements PatternTableService {

    private final DataModelCreator dataModelCreator;
    private final PatternTableRepo patternTableRepo;
    private final Validator<DataModelList> dataModelListValidator;
    private final TableCreatorService tableCreatorService;
    private final Validator<Long> patternIdValidator;
    private final LoggerSender<PatternTable> patternTableLoggerSender;
    private final BeforeAfter<PatternTable> patternTableBeforeAfter;
    private final TableRepo tableRepo;

    public PatternTableServiceImpl(DataModelCreator dataModelCreator, PatternTableRepo patternTableRepo, Validator<DataModelList> dataModelListValidator, TableCreatorService tableCreatorService, Validator<Long> patternIdValidator, LoggerSender<PatternTable> patternTableLoggerSender, BeforeAfter<PatternTable> patternTableBeforeAfter, TableRepo tableRepo) {
        this.dataModelCreator = dataModelCreator;
        this.patternTableRepo = patternTableRepo;
        this.dataModelListValidator = dataModelListValidator;
        this.tableCreatorService = tableCreatorService;
        this.patternIdValidator = patternIdValidator;
        this.patternTableLoggerSender = patternTableLoggerSender;
        this.patternTableBeforeAfter = patternTableBeforeAfter;
        this.tableRepo = tableRepo;
    }

    @Override
    public PatternTableModelStatus createPatternTable(TableModel tableModel, DataModelList dataModelList, Long patternId) {
        PatternTableModelStatus patternAfter;
        long loggerId;

        if (!patternIdValidator.isValid(patternId)) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else if (patternTableRepo.existsByNameTable(tableModel.getTableName())) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-2")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else if (!dataModelListValidator.isValid(dataModelList)) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else {

            dataModelCreator.setDataModel(dataModelList);
            List<DataModel> dataModels = dataModelCreator.getDataModel();
            tableModel.setModels(dataModels);

            patternAfter = tableCreatorService.addTable(tableModel, patternId);
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());

        }

        if (patternAfter.getPatternTable().getId() > 0) {
            patternTableBeforeAfter.afterCreate(patternAfter.getPatternTable(), loggerId);
        }

        return patternAfter;
    }

    @Override
    public FullTableModelPage getTable(Long id, SearchModel searchModel, Pageable pageable) {
        if (patternTableRepo.existsById(id)) {
            PatternTable patternTable = patternTableRepo.findById((long) id);
            searchModel.setPageable(pageable);
            return tableRepo.getTable(patternTable.getNameTable(), patternTable.getNameFile(), searchModel);
        }

        return new FullTableModelPage();
    }


    @Override
    public PatternTable archivePatternTable(Long id) {
        PatternTable patternBefore, patternAfter;

        if (id == null) {
            patternAfter = PatternTable.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternTableRepo.existsById(id)) {
            patternAfter = PatternTable.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternTableRepo.findById((long) id);
            patternBefore = PatternTable
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();
            patternAfter.setIsArchive(true);
            patternAfter.setDateDeactivation(LocalDateTime.now());

            patternTableRepo.save(patternAfter);

        }

        long loggerId = patternTableLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternTableBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
    }

    @Override
    public PatternTable deArchivePatternTable(Long id) {
        PatternTable patternBefore, patternAfter;

        if (id == null) {
            patternAfter = PatternTable.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternTableRepo.existsById(id)) {
            patternAfter = PatternTable.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternTableRepo.findById((long) id);
            patternBefore = PatternTable
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();
            patternAfter.setIsArchive(false);
            patternAfter.setDateActivation(LocalDateTime.now());

            patternTableRepo.save(patternAfter);

        }

        long loggerId = patternTableLoggerSender.afterDeArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternTableBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
    }

    @Override
    public List<PatternTable> archivePatternTablesByPatternId(Long patternId) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (patternId == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (patternTableRepo.existsByPatternId(patternId)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllByPatternId(patternId)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateDeactivation(v.getDateDeactivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllByPatternId(patternId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternTableLoggerSender.afterArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public List<PatternTable> deArchivePatternTablesByPatternId(Long patternId) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (patternId == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (patternTableRepo.existsByPatternId(patternId)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllByPatternId(patternId)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateActivation(v.getDateActivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllByPatternId(patternId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternTableLoggerSender.afterDeArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterDeArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public List<PatternTable> archivePatternTablesBySourceId(Long id) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (patternTableRepo.existsBySourceId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateDeactivation(v.getDateDeactivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternTableLoggerSender.afterArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public List<PatternTable> deArchivePatternTablesBySourceId(Long id) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (patternTableRepo.existsBySourceId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateActivation(v.getDateActivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternTableLoggerSender.afterDeArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterDeArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public PatternTableModelStatus updatePatternTable(TableModel tableModel, DataModelList dataModelList, Long patternTableId) {
        if (patternTableRepo.existsById(patternTableId)) {

            PatternTable patternTable = patternTableRepo.findById((long) patternTableId);
            PatternTableModelStatus patternAfter;
            long loggerId;

            if (!dataModelListValidator.isValid(dataModelList)) {
                patternAfter = PatternTableModelStatus
                        .builder()
                        .tableModel(TableModelStatus.emptyTableModelStatus())
                        .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                        .build();

            } else {

                dataModelCreator.setDataModel(dataModelList);
                List<DataModel> dataModels = dataModelCreator.getDataModel();
                tableModel.setModels(dataModels);
                tableModel.setTableName(patternTable.getNameTable());

                patternAfter = tableCreatorService.updateTable(tableModel, patternTable);

            }
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());

            if (patternAfter.getPatternTable().getId() > 0) {
                patternTableBeforeAfter.afterCreate(patternAfter.getPatternTable(), loggerId);
            }

            return patternAfter;
        }

        return PatternTableModelStatus
                .builder()
                .tableModel(TableModelStatus.emptyTableModelStatus())
                .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                .build();
    }
}