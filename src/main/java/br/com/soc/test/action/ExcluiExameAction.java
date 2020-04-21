package br.com.soc.test.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.soc.test.jdbc.ExameDao;
import br.com.soc.test.modelo.Exame;

public class ExcluiExameAction {

	private Exame exame;

	@Action(value = "/excluiExame",
			results = { @Result(name = "ok", type = "redirectAction", params = {"actionName", "/"}) })
	public String execute() {
		new ExameDao().exclui(exame);
		return "ok";
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

}
