package ru.iac.ASGIHDTORIS.api.db;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbTest {

//    private final String TABLE_NAME = "testDbCreator1";
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Test
//    void createTable() throws SQLException {
//        PostgreSqlCreator dbPostgreSQLCreator = new PostgreSqlCreator(dataSource.getConnection());
//        createDataModels();
//
//        assertTrue(dbPostgreSQLCreator.createTable(TABLE_NAME, createDataModels()));
//    }
//
//    @Test
//    void testInsert() throws SQLException {
//        CSVLoader csvLoader = new CSVLoader(dataSource.getConnection());
//        File file = new File("/home/nikita/Загрузки/Telegram Desktop/data-20200120T140800-structure-20141226T1550271.csv");
//
//        long start = System.currentTimeMillis();
//        System.out.println("start: " + start);
//        assertTrue(csvLoader.insert(file, TABLE_NAME, createDataModels()));
//        long end = System.currentTimeMillis() - start;
//        System.out.println("end:" + end);
//    }
//
//    private List<DataModel> createDataModels() {
//        List<DataModel> dataModels = new ArrayList<>();
//
//        dataModels.add(new DataModel("number", "integer", true));
//        dataModels.add(new DataModel("indicator", "text", false));
//        dataModels.add(new DataModel("reportperiod", "text", false));
//        dataModels.add(new DataModel("bud_value", "text", false));
//        dataModels.add(new DataModel("value", "text", false));
//        dataModels.add(new DataModel("budget", "text", false));
//        dataModels.add(new DataModel("implpercent", "text", false));
//
//        return dataModels;
//    }

}