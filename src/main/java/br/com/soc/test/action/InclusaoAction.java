package br.com.soc.test.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.soc.test.jdbc.FinalidadeDao;
import br.com.soc.test.jdbc.MedicoDao;
import br.com.soc.test.jdbc.ResultadoDao;
import br.com.soc.test.jdbc.VinculoEmpresaFuncionarioDao;
import br.com.soc.test.modelo.Finalidade;
import br.com.soc.test.modelo.Medico;
import br.com.soc.test.modelo.Resultado;
import br.com.soc.test.modelo.VinculoEmpresaFuncionario;

public class InclusaoAction {

	private List<Finalidade> finalidades;
	private List<Resultado> resultados;
	private List<Medico> medicos;
	private List<VinculoEmpresaFuncionario> funcionarios;

	@Action(value = "/novo",
			results = { @Result(location = "formulario.jsp", name = "ok") })
	public String execute() {
		finalidades = new FinalidadeDao().listaTodos();
		resultados = new ResultadoDao().listaTodos();
		medicos = new MedicoDao().listaTodos();
		funcionarios = new VinculoEmpresaFuncionarioDao().listaTodos();
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
