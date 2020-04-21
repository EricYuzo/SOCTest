package br.com.soc.test.ws.servico;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.com.soc.test.jdbc.ExameDao;
import br.com.soc.test.ws.tipo.ListaExames;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public class ListagemExames {

	@WebMethod
	public ListaExames listarExames() {
		ListaExames exames = new ListaExames();
		exames.setExames(new ExameDao().listaTodos());

		return exames;
	}
}
