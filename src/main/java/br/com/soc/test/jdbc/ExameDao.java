package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.soc.test.modelo.Empresa;
import br.com.soc.test.modelo.Exame;
import br.com.soc.test.modelo.Finalidade;
import br.com.soc.test.modelo.Funcionario;
import br.com.soc.test.modelo.Medico;
import br.com.soc.test.modelo.Resultado;
import br.com.soc.test.modelo.VinculoEmpresaFuncionario;

public class ExameDao {
	
	private Connection connection;
	
	public ExameDao() {
		connection = new ConnectionFactory().getConnection();
	}
	
	public void insere(Exame exame) {
		try {
			String sql =
					"INSERT INTO TB_EXAME (DT_EXAME, ID_FINALIDADE, ID_RESULTADO, ID_MEDICO, ID_EMPRESA_FUNCIONARIO) " +
					"VALUES (?, ?, ?, ?, ?)";

			PreparedStatement st = connection.prepareStatement(sql);
			st.setDate(1, new Date(Calendar.getInstance().getTimeInMillis()));
			st.setInt(2, exame.getFinalidade().getId());
			st.setInt(3, exame.getResultado().getId());
			st.setInt(4, exame.getMedico().getId());
			st.setInt(5, exame.getParticipante().getId());
			
			st.execute();
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Exame> listaTodos() {
		List<Exame> exames = new ArrayList<Exame>();
		
		try {
			String sql =
					"SELECT EXM.ID_EXAME, EXM.DT_EXAME, EMP.ID_EMPRESA, EMP.NM_EMPRESA, EMP.CD_CNPJ " +
					"      ,FUN.ID_FUNCIONARIO, FUN.NM_FUNCIONARIO, FUN.CD_CPF, FUN.CD_RG, FUN.CD_EMISSOR_RG " +
					"      ,VEF.ID_EMPRESA_FUNCIONARIO, VEF.NM_SETOR, VEF.NM_CARGO, MED.ID_MEDICO, MED.NM_MEDICO " +
					"      ,MED.CD_CRM, MED.SG_UF SG_UF_CRM, MED.NM_TITULO NM_TITULO_MEDICO " +
					"      ,FIN.ID_FINALIDADE, FIN.NM_FINALIDADE, RES.ID_RESULTADO, RES.NM_RESULTADO " +
					"FROM TB_EXAME EXM " +
					"  JOIN TB_EMPRESA_FUNCIONARIO VEF " +
					"    ON VEF.ID_EMPRESA_FUNCIONARIO = EXM.ID_EMPRESA_FUNCIONARIO " +
					"  JOIN TB_EMPRESA EMP " +
					"    ON EMP.ID_EMPRESA = VEF.ID_EMPRESA " +
					"  JOIN TB_FUNCIONARIO FUN " +
					"    ON FUN.ID_FUNCIONARIO = VEF.ID_FUNCIONARIO " +
					"  JOIN TB_MEDICO MED " +
					"    ON MED.ID_MEDICO = EXM.ID_MEDICO " +
					"  JOIN TB_FINALIDADE FIN " +
					"    ON FIN.ID_FINALIDADE = EXM.ID_FINALIDADE " +
					"  JOIN TB_RESULTADO RES " +
					"    ON RES.ID_RESULTADO = EXM.ID_RESULTADO;";
			
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
				participante.setId(rs.getInt("ID_EMPRESA_FUNCIONARIO"));
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
				
				Resultado resultado = new Resultado();
				resultado.setId(rs.getInt("ID_RESULTADO"));
				resultado.setNome(rs.getString("NM_RESULTADO"));
	
				Exame exame = new Exame();
				exame.setId(rs.getInt("ID_EXAME"));
				exame.setParticipante(participante);
				exame.setMedico(medico);
				exame.setFinalidade(finalidade);
				exame.setResultado(resultado);

				exames.add(exame);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return exames;
	}
	
	public List<Finalidade> listaFinalidades() {
		List<Finalidade> finalidades = new ArrayList<Finalidade>();
		
		try {
			String sql = "SELECT * FROM TB_FINALIDADE;";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Finalidade finalidade = new Finalidade();
				finalidade.setId(rs.getInt("ID_FINALIDADE"));
				finalidade.setNome(rs.getString("NM_FINALIDADE"));
				
				finalidades.add(finalidade);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return finalidades;
	}
	
	public List<Resultado> listaResultados() {
		List<Resultado> resultados = new ArrayList<Resultado>();
		
		try {
			String sql = "SELECT * FROM TB_RESULTADO;";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Resultado resultado = new Resultado();
				resultado.setId(rs.getInt("ID_RESULTADO"));
				resultado.setNome(rs.getString("NM_RESULTADO"));
				
				resultados.add(resultado);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultados;
	}
	
	public List<Medico> listaMedicos() {
		List<Medico> medicos = new ArrayList<Medico>();
		
		try {
			String sql = "SELECT * FROM TB_MEDICO;";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Medico medico = new Medico();
				medico.setId(rs.getInt("ID_MEDICO"));
				medico.setNome(rs.getString("NM_MEDICO"));
				medico.setCrm(rs.getString("CD_CRM"));
				medico.setUf(rs.getString("SG_UF"));
				medico.setTitulo(rs.getString("NM_TITULO"));
				
				medicos.add(medico);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return medicos;
	}
	
	public List<VinculoEmpresaFuncionario> listaFuncionariosGeral() {
		List<VinculoEmpresaFuncionario> funcionarios = new ArrayList<VinculoEmpresaFuncionario>();
		
		try {
			String sql =
					"SELECT VEF.ID_EMPRESA_FUNCIONARIO, EMP.ID_EMPRESA, EMP.NM_EMPRESA, EMP.CD_CNPJ " +
					"      ,FUN.ID_FUNCIONARIO, FUN.NM_FUNCIONARIO, FUN.CD_CPF, FUN.CD_RG, FUN.CD_EMISSOR_RG " +
					"      ,VEF.NM_SETOR, VEF.NM_CARGO " +
					"FROM TB_EMPRESA_FUNCIONARIO VEF " +
					"  JOIN TB_EMPRESA EMP " +
					"    ON EMP.ID_EMPRESA = VEF.ID_EMPRESA " +
					"  JOIN TB_FUNCIONARIO FUN " +
					"    ON FUN.ID_FUNCIONARIO = VEF.ID_FUNCIONARIO;";
			
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
				participante.setId(rs.getInt("ID_EMPRESA_FUNCIONARIO"));
				participante.setEmpresa(empresa);
				participante.setFuncionario(funcionario);
				participante.setSetor(rs.getString("NM_SETOR"));
				participante.setCargo(rs.getString("NM_CARGO"));
				
				funcionarios.add(participante);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return funcionarios;
	}
	
	public static void main(String[] args) {
		ExameDao dao = new ExameDao();
		dao.listaTodos();
	}
}
