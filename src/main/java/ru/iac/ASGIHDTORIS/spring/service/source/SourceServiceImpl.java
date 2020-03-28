package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.source.SourceDataModel;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

import java.time.LocalDateTime;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;

    public SourceServiceImpl(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @Override
    public Page<Source> findAllSourceByQuery(
            Pageable pageable,
            Source source,
            SourceDataModel sourceDataModel,
            String key,
            String sort
    ) {

        pageable = getPageable(pageable, key, sort);

        return getAll(pageable, source, sourceDataModel);
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

    private Page<Source> getAll(Pageable pageable, Source source, SourceDataModel sourceDataModel) {

        source = fixSource(source);
        sourceDataModel = fixSourceDataModel(sourceDataModel);

        if (source.getIsArchive() == null) {
            return getWithoutArchive(pageable, source, sourceDataModel);
        } else {
            return getWithArchive(pageable, source, sourceDataModel);
        }

    }

    private Source fixSource(Source source) {
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

    private SourceDataModel fixSourceDataModel(SourceDataModel sourceDataModel) {

        if (sourceDataModel.getDateCreation1() == null) {
            sourceDataModel.setDateCreation1(LocalDateTime.MIN);
        }
        if (sourceDataModel.getDateCreation2() == null) {
            sourceDataModel.setDateCreation2(LocalDateTime.MAX);
        }
        if (sourceDataModel.getDateDeactivation1() == null) {
            sourceDataModel.setDateDeactivation1(LocalDateTime.MIN);
        }
        if (sourceDataModel.getDateDeactivation2() == null) {
            sourceDataModel.setDateDeactivation2(LocalDateTime.MAX);
        }
        if (sourceDataModel.getDateActivation1() == null) {
            sourceDataModel.setDateActivation1(LocalDateTime.MIN);
        }
        if (sourceDataModel.getDateActivation2() == null) {
            sourceDataModel.setDateActivation2(LocalDateTime.MAX);
        }
        if (sourceDataModel.getLastUpdate1() == null) {
            sourceDataModel.setLastUpdate1(LocalDateTime.MIN);
        }
        if (sourceDataModel.getLastUpdate2() == null) {
            sourceDataModel.setLastUpdate2(LocalDateTime.MAX);
        }

        return sourceDataModel;
    }

    private Page<Source> getWithArchive(Pageable pageable, Source source, SourceDataModel sourceDataModel) {

        return sourceRepo.findAllSourceByQuery(
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
                source.getDataSource(),
                sourceDataModel.getDateCreation1(),
                sourceDataModel.getDateCreation2(),
                sourceDataModel.getDateDeactivation1(),
                sourceDataModel.getDateDeactivation2(),
                sourceDataModel.getDateActivation1(),
                sourceDataModel.getDateActivation2(),
                sourceDataModel.getLastUpdate1(),
                sourceDataModel.getLastUpdate2()
        );

    }

    private Page<Source> getWithoutArchive(Pageable pageable, Source source, SourceDataModel sourceDataModel) {

        return sourceRepo.findAllSourceByQuery(
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
                source.getDataSource(),
                sourceDataModel.getDateCreation1(),
                sourceDataModel.getDateCreation2(),
                sourceDataModel.getDateDeactivation1(),
                sourceDataModel.getDateDeactivation2(),
                sourceDataModel.getDateActivation1(),
                sourceDataModel.getDateActivation2(),
                sourceDataModel.getLastUpdate1(),
                sourceDataModel.getLastUpdate2(),
                source.getIsArchive()
        );

    }

}
