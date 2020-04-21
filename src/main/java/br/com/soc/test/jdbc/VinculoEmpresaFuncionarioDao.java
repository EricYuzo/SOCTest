package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Empresa;
import br.com.soc.test.modelo.Funcionario;
import br.com.soc.test.modelo.VinculoEmpresaFuncionario;

public class VinculoEmpresaFuncionarioDao {

	private static final String SQL_INSERT   =
			"INSERT INTO TB_EMPRESA_FUNCIONARIO (ID_EMPRESA, ID_FUNCIONARIO, NM_SETOR, NM_CARGO) " +
			"VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE   =
			"UPDATE TB_EMPRESA_FUNCIONARIO " +
			"SET ID_EMPRESA = ?, ID_FUNCIONARIO = ?, NM_SETOR = ?, NM_CARGO = ? " +
			"WHERE ID_EMPRESA_FUNCIONARIO = ?;";
	private static final String SQL_DELETE   =
			"DELETE FROM TB_EMPRESA_FUNCIONARIO WHERE ID_EMPRESA_FUNCIONARIO = ?;";
	private static final String SQL_FIND_ID  =
			"SELECT VEF.ID_EMPRESA_FUNCIONARIO, EMP.ID_EMPRESA, EMP.NM_EMPRESA, EMP.CD_CNPJ " +
			"      ,FUN.ID_FUNCIONARIO, FUN.NM_FUNCIONARIO, FUN.CD_CPF, FUN.CD_RG, FUN.CD_EMISSOR_RG " +
			"      ,VEF.NM_SETOR, VEF.NM_CARGO " +
			"FROM TB_EMPRESA_FUNCIONARIO VEF " +
			"  JOIN TB_EMPRESA EMP " +
			"    ON EMP.ID_EMPRESA = VEF.ID_EMPRESA " +
			"  JOIN TB_FUNCIONARIO FUN " +
			"    ON FUN.ID_FUNCIONARIO = VEF.ID_FUNCIONARIO " +
			"WHERE VEF.ID_EMPRESA_FUNCIONARIO = ?;";
	private static final String SQL_FIND_ALL =
			"SELECT VEF.ID_EMPRESA_FUNCIONARIO, EMP.ID_EMPRESA, EMP.NM_EMPRESA, EMP.CD_CNPJ " +
			"      ,FUN.ID_FUNCIONARIO, FUN.NM_FUNCIONARIO, FUN.CD_CPF, FUN.CD_RG, FUN.CD_EMISSOR_RG " +
			"      ,VEF.NM_SETOR, VEF.NM_CARGO " +
			"FROM TB_EMPRESA_FUNCIONARIO VEF " +
			"  JOIN TB_EMPRESA EMP " +
			"    ON EMP.ID_EMPRESA = VEF.ID_EMPRESA " +
			"  JOIN TB_FUNCIONARIO FUN " +
			"    ON FUN.ID_FUNCIONARIO = VEF.ID_FUNCIONARIO;";

	private Connection connection;

	public VinculoEmpresaFuncionarioDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(VinculoEmpresaFuncionario participante) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, participante.getEmpresa().getId());
			stmt.setInt(2, participante.getFuncionario().getId());
			stmt.setString(3, participante.getSetor());
			stmt.setString(4, participante.getCargo());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(VinculoEmpresaFuncionario participante) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, participante.getEmpresa().getId());
			stmt.setInt(2, participante.getFuncionario().getId());
			stmt.setString(3, participante.getSetor());
			stmt.setString(4, participante.getCargo());
			stmt.setInt(5, participante.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(VinculoEmpresaFuncionario participante) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, participante.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public VinculoEmpresaFuncionario buscaPor(int id) {
		VinculoEmpresaFuncionario participante = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
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

					participante = new VinculoEmpresaFuncionario();
					participante.setId(rs.getInt("ID_EMPRESA_FUNCIONARIO"));
					participante.setEmpresa(empresa);
					participante.setFuncionario(funcionario);
					participante.setSetor(rs.getString("NM_SETOR"));
					participante.setCargo(rs.getString("NM_CARGO"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return participante;
	}

	public List<VinculoEmpresaFuncionario> listaTodos() {
		List<VinculoEmpresaFuncionario> participantes = new ArrayList<VinculoEmpresaFuncionario>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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

				participantes.add(participante);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return participantes;
	}
}
