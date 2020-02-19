package ru.iac.ASGIHDTORIS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.api.parser.db.ParseJsonInBd;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class AsGihdTorisApplicationTests {

	@Autowired
	private DataSource dataSource;

	private String json = "{\n" +
			"\"content\":{\n" +
			"\"nameTable\":\"gfdgdfgfd\",\n" +
			"\"table\":[\n" +
			"{\n" +
			"\"number\":\"1\",\n" +
			"\"indicator\":\"Национальная оборона\",\n" +
			"\"reportperiod\":\"на 1 декабря 2019 г.\",\n" +
			"\"bud_value\":\"86755.9\",\n" +
			"\"value\":\"51158.2719\",\n" +
			"\"budget\":\"Первоначально утвержденный бюджет 2019 г.\",\n" +
			"\"implpercent\":\"58.3102583245\"\n" +
			"},\n" +
			"{\n" +
			"\"number\":\"2\",\n" +
			"\"indicator\":\"Обслуживание государственного и муниципального долга\",\n" +
			"\"reportperiod\":\"на 1 декабря 2019 г.\",\n" +
			"\"bud_value\":\"2336030.0\",\n" +
			"\"value\":\"1155515.0\",\n" +
			"\"budget\":\"Первоначально утвержденный бюджет 2019 г.\",\n" +
			"\"implpercent\":\"49.4649041322\"\n" +
			"}\n" +
			"],\n" +
			"\"columnTable\":[\n" +
			"{\n" +
			"\"name\":\"\uFEFFnumber\",\n" +
			"\"type\":\"Integer\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"indicator\",\n" +
			"\"type\":\"Text\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"reportperiod\",\n" +
			"\"type\":\"Text\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"bud_value\",\n" +
			"\"type\":\"Text\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"value\",\n" +
			"\"type\":\"Text\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"budget\",\n" +
			"\"type\":\"Text\"\n" +
			"},\n" +
			"{\n" +
			"\"name\":\"implpercent\",\n" +
			"\"type\":\"Text\"\n" +
			"}\n" +
			"]\n" +
			"}\n" +
			"}";


	@Test
	void contextLoads() throws SQLException {

//		ParseJsonInBd parseJsonInBd = new ParseJsonInBd(dataSource.getConnection());
//		System.out.println(parseJsonInBd.push(json));

	}

}
