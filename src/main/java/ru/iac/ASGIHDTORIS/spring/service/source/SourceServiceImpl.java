package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceDateModel;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

import java.time.LocalDate;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;

    public SourceServiceImpl(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @Override
    public Page<Source> findAllSourceByQuery(Pageable pageable, SourceModel source) {

        pageable = getPageable(pageable, source.getKey(), source.getSort());

        return getAll(pageable, source);
    }

    private Pageable getPageable(Pageable pageable, String key, String sort) {
        if (sort.equalsIgnoreCase("desc")) {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(key).descending()
            );
        } else {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(key).ascending()
            );
        }
    }

    private Page<Source> getAll(Pageable pageable, SourceModel source) {

        source = fixSource(source);
        source = fixSourceDataModel(source);

        if (source.getIsArchive() == null && !(source.getDateDeactivation1() == null && source.getDateDeactivation2() == null)) {
            return getWithoutArchiveWithDeactivation(pageable, source);
        } else if (source.getIsArchive() != null && !(source.getDateDeactivation1() == null && source.getDateDeactivation2() == null)) {
            return getWithArchiveAndDeactivation(pageable, source);
        } else if (source.getIsArchive() == null && (source.getDateDeactivation1() == null && source.getDateDeactivation2() == null)) {
            return getWithoutArchiveAndDeactivation(pageable, source);
        } else {
            return getWithArchiveWithoutDeactivation(pageable, source);
        }

    }

    private SourceModel fixSource(SourceModel source) {
        if (source.getId() == null) {
            source.setId("");
        }
        if (source.getName() == null) {
            source.setName("");
        }
        if (source.getLongName() == null) {
            source.setLongName("");
        }
        if (source.getShortName() == null) {
            source.setShortName("");
        }
        if (source.getDescription() == null) {
            source.setDescription("");
        }
        if (source.getAddDescription() == null) {
            source.setAddDescription("");
        }
        if (source.getScope() == null) {
            source.setScope("");
        }
        if (source.getPeriodicity() == null) {
            source.setPeriodicity("");
        }
        if (source.getRenewalPeriod() == null) {
            source.setRenewalPeriod("");
        }
        if (source.getType() == null) {
            source.setType("");
        }
        if (source.getTags() == null) {
            source.setTags("");
        }
        if (source.getProviderLink() == null) {
            source.setProviderLink("");
        }
        if (source.getDataSource() == null) {
            source.setDataSource("");
        }

        return source;
    }

    private SourceModel fixSourceDataModel(SourceModel source) {

        if (source.getDateCreation1() == null) {
            source.setDateCreation1(LocalDate.of(1970, 1, 1));
        }
        if (source.getDateCreation2() == null) {
            source.setDateCreation2(LocalDate.of(3000, 1, 1));
        }
        if (source.getDateActivation1() == null) {
            source.setDateActivation1(LocalDate.of(1970, 1, 1));
        }
        if (source.getDateActivation2() == null) {
            source.setDateActivation2(LocalDate.of(3000, 1, 1));
        }
        if (source.getLastUpdate1() == null) {
            source.setLastUpdate1(LocalDate.of(1970, 1, 1));
        }
        if (source.getLastUpdate2() == null) {
            source.setLastUpdate2(LocalDate.of(3000, 1, 1));
        }

        return source;
    }

    private Page<Source> getWithArchiveAndDeactivation(Pageable pageable, SourceModel source) {

        return sourceRepo.findAllSourceByQuery(
                pageable,
                source.getId(),
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
                source.getDataSource(),
                source.getDateCreation1(),
                source.getDateCreation2(),
                source.getDateDeactivation1(),
                source.getDateDeactivation2(),
                source.getDateActivation1(),
                source.getDateActivation2(),
                source.getLastUpdate1(),
                source.getLastUpdate2(),
                source.getIsArchive()
        );

    }

    private Page<Source> getWithoutArchiveWithDeactivation(Pageable pageable, SourceModel source) {

        return sourceRepo.findAllSourceByQuery(
                pageable,
                source.getId(),
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
                source.getDataSource(),
                source.getDateCreation1(),
                source.getDateCreation2(),
                source.getDateDeactivation1(),
                source.getDateDeactivation2(),
                source.getDateActivation1(),
                source.getDateActivation2(),
                source.getLastUpdate1(),
                source.getLastUpdate2()
        );

    }

    private Page<Source> getWithArchiveWithoutDeactivation(Pageable pageable, SourceModel source) {

        return sourceRepo.findAllSourceByQuery(
                pageable,
                source.getId(),
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
                source.getDataSource(),
                source.getDateCreation1(),
                source.getDateCreation2(),
                source.getDateActivation1(),
                source.getDateActivation2(),
                source.getLastUpdate1(),
                source.getLastUpdate2(),
                source.getIsArchive()
        );

    }

    private Page<Source> getWithoutArchiveAndDeactivation(Pageable pageable, SourceModel source) {

        return sourceRepo.findAllSourceByQuery(
                pageable,
                source.getId(),
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
                source.getDataSource(),
                source.getDateCreation1(),
                source.getDateCreation2(),
                source.getDateActivation1(),
                source.getDateActivation2(),
                source.getLastUpdate1(),
                source.getLastUpdate2()
        );

    }

}
