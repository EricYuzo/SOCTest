package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Medico;

public class MedicoDao {

	private static final String SQL_INSERT   =
			"INSERT INTO TB_MEDICO (CD_CRM, SG_UF, NM_TITULO, NM_MEDICO) " +
			"VALUES (?, ?, ?, ?);";
	private static final String SQL_UPDATE   =
			"UPDATE TB_MEDICO " +
			"SET CD_CRM = ?, SG_UF = ?, NM_TITULO = ?, NM_MEDICO = ? " +
			"WHERE ID_MEDICO = ?;";
	private static final String SQL_DELETE   = "DELETE FROM TB_MEDICO WHERE ID_MEDICO = ?;";
	private static final String SQL_FIND_ID  = "SELECT * FROM TB_MEDICO WHERE ID_MEDICO = ?;";
	private static final String SQL_FIND_ALL = "SELECT * FROM TB_MEDICO;";

	private Connection connection;

	public MedicoDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(Medico medico) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, medico.getCrm());
			stmt.setString(2, medico.getUf());
			stmt.setString(3, medico.getTitulo());
			stmt.setString(4, medico.getNome());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Medico medico) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, medico.getCrm());
			stmt.setString(2, medico.getUf());
			stmt.setString(3, medico.getTitulo());
			stmt.setString(4, medico.getNome());
			stmt.setInt(5, medico.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Medico medico) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, medico.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Medico buscaPor(int id) {
		Medico medico = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					medico = new Medico();
					medico.setId(rs.getInt("ID_MEDICO"));
					medico.setNome(rs.getString("NM_MEDICO"));
					medico.setCrm(rs.getString("CD_CRM"));
					medico.setUf(rs.getString("SG_UF"));
					medico.setTitulo(rs.getString("NM_TITULO"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return medico;
	}

	public List<Medico> listaTodos() {
		List<Medico> medicos = new ArrayList<Medico>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
}
