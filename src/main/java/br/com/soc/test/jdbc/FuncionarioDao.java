package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Funcionario;

public class FuncionarioDao {

	private static final String SQL_INSERT   =
			"INSERT INTO TB_FUNCIONARIO (CD_CPF, CD_RG, CD_EMISSOR_RG, NM_FUNCIONARIO) " +
			"VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE   =
			"UPDATE TB_FUNCIONARIO " +
			"SET CD_CPF = ?, CD_RG = ?, CD_EMISSOR_RG = ?, NM_FUNCIONARIO = ? " +
			"WHERE ID_FUNCIONARIO = ?;";
	private static final String SQL_DELETE   = "DELETE FROM TB_FUNCIONARIO WHERE ID_FUNCIONARIO = ?;";
	private static final String SQL_FIND_ID  = "SELECT * FROM TB_FUNCIONARIO WHERE ID_FUNCIONARIO = ?;";
	private static final String SQL_FIND_ALL = "SELECT * FROM TB_FUNCIONARIO;";

	private Connection connection;

	public FuncionarioDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(Funcionario funcionario) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, funcionario.getCpf());
			stmt.setString(2, funcionario.getRg());
			stmt.setString(3, funcionario.getOrgaoEmissorRg());
			stmt.setString(4, funcionario.getNome());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Funcionario funcionario) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, funcionario.getCpf());
			stmt.setString(2, funcionario.getRg());
			stmt.setString(3, funcionario.getOrgaoEmissorRg());
			stmt.setString(4, funcionario.getNome());
			stmt.setInt(5, funcionario.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Funcionario funcionario) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, funcionario.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Funcionario buscaPor(int id) {
		Funcionario funcionario = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					funcionario = new Funcionario();
					funcionario.setId(rs.getInt("ID_FUNCIONARIO"));
					funcionario.setNome(rs.getString("NM_FUNCIONARIO"));
					funcionario.setCpf(rs.getString("CD_CPF"));
					funcionario.setRg(rs.getString("CD_RG"));
					funcionario.setOrgaoEmissorRg(rs.getString("CD_EMISSOR_RG"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return funcionario;
	}

	public List<Funcionario> listaTodos() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID_FUNCIONARIO"));
				funcionario.setNome(rs.getString("NM_FUNCIONARIO"));
				funcionario.setCpf(rs.getString("CD_CPF"));
				funcionario.setRg(rs.getString("CD_RG"));
				funcionario.setOrgaoEmissorRg(rs.getString("CD_EMISSOR_RG"));

				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return funcionarios;
	}
}
