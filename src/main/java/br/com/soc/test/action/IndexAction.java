package br.com.soc.test.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.soc.test.jdbc.ExameDao;
import br.com.soc.test.modelo.Exame;

public class IndexAction {
	private List<Exame> exames;
	
	@Action(value = "/", results = { @Result(location = "lista.jsp", name = "ok") })
	public String execute() {
		exames = new ExameDao().listaTodos();
		return "ok";
	}
	
	public List<Exame> getExames() {
		return exames;
	}

}
