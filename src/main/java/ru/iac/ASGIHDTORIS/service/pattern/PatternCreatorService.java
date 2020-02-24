package ru.iac.ASGIHDTORIS.service.pattern;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.domain.Pattern;

@Service
public interface PatternCreatorService {

    String create(String jsons, Pattern pattern);

}
