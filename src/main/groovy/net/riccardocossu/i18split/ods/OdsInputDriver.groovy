package net.riccardocossu.i18split.ods

import net.riccardocossu.i18split.base.config.ConfigKeys
import net.riccardocossu.i18split.base.driver.InputDriver
import net.riccardocossu.i18split.base.model.DataRow

import org.apache.commons.configuration.Configuration
import org.jopendocument.dom.spreadsheet.Cell
import org.jopendocument.dom.spreadsheet.Row
import org.jopendocument.dom.spreadsheet.Sheet
import org.jopendocument.dom.spreadsheet.SpreadSheet

/**
 * Created by riccardo on 04/09/14.
 */
class OdsInputDriver implements InputDriver {

	private static final String SHORT_NAME = "ods.input"
	private String fileName
	private File baseDir
	private Reader input
	private Iterator<String[]> lineIt
	private String[] inputs

    @Override
    DataRow readNext() {
		if(lineIt.hasNext()) {
			String[] line = lineIt.next()
			DataRow row = new DataRow()
			if(line.length > 0 && line[0].trim().length() > 0) {
				row.values = [:]
				row.key = line[0]
				for (int col = 1; col <= inputs.length; col++) {
					row.values[inputs[col - 1]] = line[col]
				}
			}
			return row
		} else {
			return null
		}
    }

    @Override
    String[] init(Configuration configuration) {
        fileName = configuration.getString(ConfigKeys.INPUT_FILE)
        baseDir = new File(configuration.getString(ConfigKeys.INPUT_BASE_PATH))
		if(!fileName) {
			throw new NullPointerException('Filename cannot be null')
		}
		File input = new File (baseDir,fileName)
		final Sheet sheet = SpreadSheet.createFromFile(input).getSheet(0)
		int rowCount = sheet.getRowCount()
		List<String[]> lines = new ArrayList<String[]>()
		0.upto(rowCount - 1, {r ->
			Row<SpreadSheet> row = sheet.getRow(r)
			Cell<ODDocument>[] cells = row.cells
				if(r == 0) {
					// it's the header
					inputs = new String[cells.length -1]
					for(int i = 0; i < inputs.length; i++) {
						inputs[i] = cells[i+1].textValue
					}
				} else {
					String[] line = new String[cells.length]
					for(int i = 0; i < cells.length; i++) {
						line[i] = cells[i].textValue
					}
					lines.add(line)
				}
			}
		);

		lineIt = lines.iterator()
		return inputs
    }


    @Override
    String getShortName() {
        return SHORT_NAME
    }

    @Override
    void close() throws IOException {
		if(input) {
			input.close()
		}
    }
}
