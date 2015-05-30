package net.riccardocossu.i18split.xls;

import javax.swing.table.DefaultTableModel
import javax.swing.table.TableModel

import jxl.Workbook;
import jxl.write.Label
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.riccardocossu.i18split.base.config.ConfigKeys
import net.riccardocossu.i18split.base.driver.OutputDriver
import net.riccardocossu.i18split.base.model.DataRow

import org.apache.commons.configuration.Configuration
import org.jopendocument.dom.spreadsheet.SpreadSheet

public class XlsOutputDriver implements OutputDriver {
	private static final String FILE_NAME = "i18split.output.xls.fileName"
	private String[] keys
	private static final String SHORT_NAME = "xls.output"
	private List<Object[]> lines = []
	private String[] header = null
	private File fileOut = null
	@Override
	public String[] init(Configuration configuration) {
		keys = configuration.getStringArray(ConfigKeys.INPUT_KEYS)
		fileOut = new File("${configuration.getString(ConfigKeys.OUTPUT_BASE_PATH)}".toString(),"${configuration.getString(FILE_NAME)}".toString())
		header = new String[keys.length]+1
		header[0] = 'KEY'
		int i = 1
		keys.each { k ->
			header[i++] = k
		}
		return keys

	}

	@Override
	public String getShortName() {
		return SHORT_NAME
	}

	@Override
	public void close() throws IOException {
		WritableWorkbook workbook = Workbook.createWorkbook(fileOut)
		WritableSheet sheet = workbook.createSheet("i18split", 0)
		def h = 0
		header.each { col ->
			sheet.addCell new Label(h++, 0, col)
		}
		def row = 1
		lines.each {line ->
			def column = 0
			line.each { col ->
				sheet.addCell new Label(column++, row, col)
			}
			row++
		}
		workbook.write()
		workbook.close()
	}

	@Override
	public void writeRow(DataRow line) {
		String[] values = new String[keys.length]+1
		values[0] = line.key
		int i = 1
		keys.each { k ->
			values[i++] = line.values[k]
		}
		lines.add(values)

	}

}
