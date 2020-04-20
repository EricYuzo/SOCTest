package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Empresa;
import br.com.soc.test.modelo.Exame;
import br.com.soc.test.modelo.Finalidade;
import br.com.soc.test.modelo.Funcionario;
import br.com.soc.test.modelo.Medico;
import br.com.soc.test.modelo.VinculoEmpresaFuncionario;

public class ExameDao {
	
	private Connection connection;
	
	public ExameDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public List<Exame> listaTodos() {
		List<Exame> exames = new ArrayList<Exame>();
		
		try {
			String sql =
					"SELECT EXM.ID_EXAME, EXM.DT_EXAME, EMP.ID_EMPRESA, EMP.NM_EMPRESA, EMP.CD_CNPJ " +
					"      ,FUN.ID_FUNCIONARIO, FUN.NM_FUNCIONARIO, FUN.CD_CPF, FUN.CD_RG, FUN.CD_EMISSOR_RG " +
					"      ,VEF.NM_SETOR, VEF.NM_CARGO, MED.ID_MEDICO, MED.NM_MEDICO, MED.CD_CRM " +
					"      ,MED.SG_UF SG_UF_CRM, MED.NM_TITULO NM_TITULO_MEDICO, FIN.ID_FINALIDADE, FIN.NM_FINALIDADE " +
					"FROM TB_EXAME EXM " +
					"  JOIN TB_EMPRESA_FUNCIONARIO VEF " +
					"    ON VEF.ID_EMPRESA = EXM.ID_EMPRESA AND VEF.ID_FUNCIONARIO = EXM.ID_FUNCIONARIO " +
					"  JOIN TB_EMPRESA EMP " +
					"    ON EMP.ID_EMPRESA = EXM.ID_EMPRESA " +
					"  JOIN TB_FUNCIONARIO FUN " +
					"    ON FUN.ID_FUNCIONARIO = EXM.ID_FUNCIONARIO " +
					"  JOIN TB_MEDICO MED " +
					"    ON MED.ID_MEDICO = EXM.ID_MEDICO " +
					"  JOIN TB_FINALIDADE FIN " +
					"    ON FIN.ID_FINALIDADE = EXM.ID_FINALIDADE " +
					"WHERE EXM.IN_ATIVO = 1;";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt("ID_EMPRESA"));
				empresa.setNome(rs.getString("NM_EMPRESA"));
				empresa.setCnpj(rs.getString("CD_CNPJ"));
	
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID_FUNCIONARIO"));
				funcionario.setNome(rs.getString("NM_FUNCIONARIO"));
				funcionario.setCpf(rs.getString("CD_CPF"));
				funcionario.setRg(rs.getString("CD_RG"));
				funcionario.setOrgaoEmissorRg(rs.getString("CD_EMISSOR_RG"));
				
				VinculoEmpresaFuncionario participante = new VinculoEmpresaFuncionario();
				participante.setEmpresa(empresa);
				participante.setFuncionario(funcionario);
				participante.setSetor(rs.getString("NM_SETOR"));
				participante.setCargo(rs.getString("NM_CARGO"));
				
				Medico medico = new Medico();
				medico.setId(rs.getInt("ID_MEDICO"));
				medico.setNome(rs.getString("NM_MEDICO"));
				medico.setCrm(rs.getString("CD_CRM"));
				medico.setUf(rs.getString("SG_UF_CRM"));
				medico.setTitulo(rs.getString("NM_TITULO_MEDICO"));
				
				Finalidade finalidade = new Finalidade();
				finalidade.setId(rs.getInt("ID_FINALIDADE"));
				finalidade.setNome(rs.getString("NM_FINALIDADE"));
	
				Exame exame = new Exame();
				exame.setId(rs.getInt("ID_EXAME"));
				exame.setParticipante(participante);
				exame.setMedico(medico);
				exame.setFinalidade(finalidade);

				exames.add(exame);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return exames;
	}
	
	public static void main(String[] args) {
		ExameDao dao = new ExameDao();
		dao.listaTodos();
	}
}
