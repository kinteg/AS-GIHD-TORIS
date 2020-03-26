package ru.iac.ASGIHDTORIS.common.validator.domain;

import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

public class SourceValidator implements Validator<Source> {

    @Override
    public boolean isValid(Source source) {
        return
                isValidName(source.getName())
                        && isValidLongName(source.getName())
                        && isValidShortName(source.getShortName())
                        && isValidDescription(source.getDescription())
                        && isValidAddDescription(source.getAddDescription())
                        && isValidScope(source.getScope())
                        && isValidPeriodicity(source.getPeriodicity())
                        && isValidRenewalPeriod(source.getRenewalPeriod())
                        && isValidType(source.getType())
                        && isValidTags(source.getTags())
                        && isValidProviderLink(source.getProviderLink())
                        && isValidDataSource(source.getDataSource())
                        && isValidIsArchive(source.getIsArchive());
    }

    private boolean isValidName(String name) {
        return name != null && !name.equals("");
    }

    private boolean isValidLongName(String longName) {
        return longName != null && !longName.equals("");
    }

    private boolean isValidShortName(String shortName) {
        return shortName != null && !shortName.equals("");
    }

    private boolean isValidDescription(String description) {
        return description != null && !description.equals("");
    }

    private boolean isValidAddDescription(String addDescription) {
        return addDescription != null && !addDescription.equals("");
    }

    private boolean isValidScope(String scope) {
        return scope != null && !scope.equals("");
    }

    private boolean isValidPeriodicity(String periodicity) {
        return periodicity != null && !periodicity.equals("");
    }

    private boolean isValidRenewalPeriod(String renewalPeriod) {
        return renewalPeriod != null && !renewalPeriod.equals("");
    }

    private boolean isValidType(String type) {
        return type != null && !type.equals("");
    }

    private boolean isValidTags(String tags) {
        return tags != null && !tags.equals("");
    }

    private boolean isValidProviderLink(String providerLink) {
        return providerLink != null && !providerLink.equals("");
    }

    private boolean isValidDataSource(String dataSource) {
        return dataSource != null && !dataSource.equals("");
    }

    private boolean isValidIsArchive(Boolean isArchive) {
        return isArchive != null;
    }

}
