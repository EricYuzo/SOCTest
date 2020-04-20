package br.com.soc.test.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.soc.test.jdbc.ExameDao;
import br.com.soc.test.modelo.Finalidade;
import br.com.soc.test.modelo.Medico;
import br.com.soc.test.modelo.Resultado;
import br.com.soc.test.modelo.VinculoEmpresaFuncionario;

public class InclusaoAction {
	private List<Finalidade> finalidades;
	private List<Resultado> resultados;
	private List<Medico> medicos;
	private List<VinculoEmpresaFuncionario> funcionarios;
	
	@Action(value = "/novo", results = { @Result(location = "formulario.jsp", name = "ok") })
	public String execute() {
		ExameDao dao = new ExameDao();
		
		finalidades = dao.listaFinalidades();
		resultados = dao.listaResultados();
		medicos = dao.listaMedicos();
		funcionarios = dao.listaFuncionariosGeral();
		return "ok";
	}

	public List<Finalidade> getFinalidades() {
		return finalidades;
	}
	
	public List<Resultado> getResultados() {
		return resultados;
	}
	
	public List<Medico> getMedicos() {
		return medicos;
	}
	
	public List<VinculoEmpresaFuncionario> getFuncionarios() {
		return funcionarios;
	}

}
