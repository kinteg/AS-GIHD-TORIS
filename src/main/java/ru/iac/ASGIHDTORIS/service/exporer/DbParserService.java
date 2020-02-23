package ru.iac.ASGIHDTORIS.service.exporer;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface DbParserService {

    String getData(Long patternId, long limit) throws SQLException;

}
