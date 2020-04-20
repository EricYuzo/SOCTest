package br.com.soc.test.modelo;

public class Exame {

	private Integer id;
	private Finalidade finalidade;
	private VinculoEmpresaFuncionario participante;
	private Medico medico;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Finalidade getFinalidade() {
		return finalidade;
	}
	
	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	public VinculoEmpresaFuncionario getParticipante() {
		return participante;
	}

	public void setParticipante(VinculoEmpresaFuncionario participante) {
		this.participante = participante;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
