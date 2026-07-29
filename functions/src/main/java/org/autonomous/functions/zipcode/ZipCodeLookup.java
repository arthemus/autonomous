package org.autonomous.functions.zipcode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * Invokes the zip code (CEP) lookup available on the Correios website and
 * extracts the results from the returned HTML. This implementation uses
 * HtmlUnit to make the GET request to the Correios service and also to
 * extract data from the HTML via XPath.
 *
 * The service invoked by this implementation runs at the following URL:
 *
 * http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do
 *
 * @author Fabio Franco Uechi
 */
class ZipCodeLookup implements ZipCodeService {

	private static final String HYPHEN = "-";
	private static final String XPATH_RESULT_TABLE = "//*[@id='lamina']/div[2]/div[2]/div[2]/div/table[1]";
	private static final String ZIP_CODE_SERVICE_BASE_URL = "http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do?Metodo=listaLogradouro&TipoConsulta=relaxation&StartRow=1&EndRow=10&relaxation=";

	private WebClient getWebClient() {
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		webClient.setCssEnabled(false);
		return webClient;
	}

	@Override
	public ZipCode findByZipCode(String zipCode) {
		HtmlTable table = getHtmlTableWithResults(zipCode);
		HtmlTableRow row = table.getRow(0);
		return createZipCode(row);
	}

	private HtmlTable getHtmlTableWithResults(String query) {
		try {
			HtmlPage page = getWebClient()
					.getPage(ZIP_CODE_SERVICE_BASE_URL + query);
			HtmlTable table = (HtmlTable) page.getByXPath(
					XPATH_RESULT_TABLE).get(
					0);
			return table;
		} catch (FailingHttpStatusCodeException e) {
			throw new ZipCodeServiceFailureException(e);
		} catch (MalformedURLException e) {
			throw new ZipCodeServiceFailureException(e);
		} catch (IndexOutOfBoundsException e) {
			throw new ZipCodeNotFoundException(query, e);
		} catch (IOException e) {
			throw new ZipCodeServiceFailureException(e);
		} finally {
			getWebClient().closeAllWindows();
		}
	}

	@Override
	public List<ZipCode> findByAddress(String query) {
		HtmlTable table = getHtmlTableWithResults(query);
		ArrayList<ZipCode> zipCodes = new ArrayList<ZipCode>(table.getRows().size());
		for (HtmlTableRow row : table.getRows()) {
			zipCodes.add(createZipCode(row));
		}
		return zipCodes;
	}

	private ZipCode createZipCode(HtmlTableRow row) {
		return new ZipCode(
				StringUtils.remove(row.getCell(4).asText(), HYPHEN),
				row.getCell(0).asText(),
				row.getCell(1).asText(),
				row.getCell(2).asText(),
				row.getCell(3).asText()
		);
	}
}
