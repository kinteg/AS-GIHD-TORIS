package ru.iac.ASGIHDTORIS.common.validator.Model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.validator.Validator;

import java.util.List;

@Component
public class DataModelListValidator implements Validator<DataModelList> {

    private final Validator<List<String>> listStringValidator;
    private final Validator<List<Boolean>> listBooleanValidator;

    public DataModelListValidator(
            @Qualifier("listStringValidator") Validator<List<String>> listStringValidator,
            @Qualifier("listBooleanValidator") Validator<List<Boolean>> listBooleanValidator
    ) {
        this.listStringValidator = listStringValidator;
        this.listBooleanValidator = listBooleanValidator;
    }

    @Override
    public boolean isValid(DataModelList name) {
        return
                isNotNull(name)
                 && isValidNames(name.getNames())
                 && isValidTypes(name.getTypes())
                 && isValidPrimaries(name.getPrimaries())
                ;
    }

    private boolean isNotNull(DataModelList name) {
        return name != null;
    }

    private boolean isValidNames(List<String> names) {
        return listStringValidator.isValid(names);
    }

    private boolean isValidTypes(List<String> types) {
        return listStringValidator.isValid(types);
    }

    private boolean isValidPrimaries(List<Boolean> primaries) {
        return listBooleanValidator.isValid(primaries);
    }

}
