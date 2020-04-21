package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Finalidade;

public class FinalidadeDao {

	private static final String SQL_INSERT   = "INSERT INTO TB_FINALIDADE (NM_FINALIDADE) VALUES (?);";
	private static final String SQL_UPDATE   = "UPDATE TB_FINALIDADE SET NM_FINALIDADE = ? WHERE ID_FINALIDADE = ?;";
	private static final String SQL_DELETE   = "DELETE FROM TB_FINALIDADE WHERE ID_FINALIDADE = ?;";
	private static final String SQL_FIND_ID  = "SELECT * FROM TB_FINALIDADE WHERE ID_FINALIDADE = ?;";
	private static final String SQL_FIND_ALL = "SELECT * FROM TB_FINALIDADE;";

	private Connection connection;

	public FinalidadeDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(Finalidade finalidade) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, finalidade.getNome());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Finalidade finalidade) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, finalidade.getNome());
			stmt.setInt(2, finalidade.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Finalidade finalidade) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, finalidade.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Finalidade buscaPor(int id) {
		Finalidade finalidade = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					finalidade = new Finalidade();
					finalidade.setId(rs.getInt("ID_FINALIDADE"));
					finalidade.setNome(rs.getString("NM_FINALIDADE"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return finalidade;
	}

	public List<Finalidade> listaTodos() {
		List<Finalidade> finalidades = new ArrayList<Finalidade>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
}
