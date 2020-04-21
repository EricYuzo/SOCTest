package br.com.soc.test.ws.tipo;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.sun.xml.txw2.annotation.XmlElement;

import br.com.soc.test.modelo.Exame;

@XmlType
public class ListaExames {

	private List<Exame> exames;

	@XmlElement
	public List<Exame> getExames() {
		return exames;
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

}
