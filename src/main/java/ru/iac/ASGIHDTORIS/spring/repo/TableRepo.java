package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;


@Repository
public interface TableRepo {

    FullTableModelPage getTable(String tableName, String fileName, SearchModel searchModel);

}
