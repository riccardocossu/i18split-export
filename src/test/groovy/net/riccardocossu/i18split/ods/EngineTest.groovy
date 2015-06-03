package net.riccardocossu.i18split.ods

import net.riccardocossu.i18split.base.csv.CsvOutputDriver
import static org.junit.Assert.*
import net.riccardocossu.i18split.base.config.ConfigKeys
import net.riccardocossu.i18split.base.csv.CsvInputDriver
import net.riccardocossu.i18split.base.service.Engine
import net.riccardocossu.i18split.xls.XlsInputDriver;
import net.riccardocossu.i18split.xls.XlsOutputDriver;

import org.apache.commons.configuration.BaseConfiguration
import org.apache.commons.configuration.Configuration
import org.junit.Test

public class EngineTest {

	@Test
	public void readCsvAndWriteOdsShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inCsv")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(ConfigKeys.OUTPUT_FILE, "i18split.ods")
		conf.addProperty(ConfigKeys.INPUT_FILE, "i18split.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, CsvInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, OdsOutputDriver.SHORT_NAME)
		Engine eng = new Engine(conf)
		eng.process()

	}

	@Test
	def void readOdsAndWriteCsvShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(ConfigKeys.INPUT_FILE, "i18split.ods")
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inOds")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(ConfigKeys.OUTPUT_FILE, "i18split-ods.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, OdsInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, CsvOutputDriver.SHORT_NAME)
		Engine eng = new Engine(conf)
		eng.process()

	}

	@Test
	public void readCsvAndWriteXlsShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inCsv")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(ConfigKeys.OUTPUT_FILE, "i18split.xls")
		conf.addProperty(ConfigKeys.INPUT_FILE, "i18split.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, CsvInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, XlsOutputDriver.SHORT_NAME)
		Engine eng = new Engine(conf)
		eng.process()

	}
	@Test
	def void readXlsAndWriteCsvShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(ConfigKeys.INPUT_FILE, "i18split.xls")
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inXls")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(ConfigKeys.OUTPUT_FILE, "i18split-xls.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, XlsInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, CsvOutputDriver.SHORT_NAME)
		Engine eng = new Engine(conf)
		eng.process()

	}
}
