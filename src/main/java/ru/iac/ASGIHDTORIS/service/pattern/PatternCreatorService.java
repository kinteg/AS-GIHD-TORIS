package ru.iac.ASGIHDTORIS.service.pattern;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.domain.Pattern;

import java.util.List;

@Service
public interface PatternCreatorService {

    String create(List<String> jsons, Pattern pattern);

}
