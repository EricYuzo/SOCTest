package br.com.soc.test.modelo;

import java.util.Calendar;

public class Exame {

	private Integer id;
	private Calendar data;
	private Finalidade finalidade;
	private Resultado resultado;
	private VinculoEmpresaFuncionario participante;
	private Medico medico;

	public Exame() {
		data = Calendar.getInstance();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exame other = (Exame) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
