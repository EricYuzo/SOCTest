package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Empresa;

public class EmpresaDao {

	private static final String SQL_INSERT   =
			"INSERT INTO TB_EMPRESA (CD_CNPJ, NM_EMPRESA) " +
			"VALUES (?, ?);";
	private static final String SQL_UPDATE   =
			"UPDATE TB_EMPRESA " +
			"SET CD_CNPJ = ?, NM_EMPRESA = ? " +
			"WHERE ID_EMPRESA = ?;";
	private static final String SQL_DELETE   = "DELETE FROM TB_EMPRESA WHERE ID_EMPRESA = ?;";
	private static final String SQL_FIND_ID  = "SELECT * FROM TB_EMPRESA WHERE ID_EMPRESA = ?;";
	private static final String SQL_FIND_ALL = "SELECT * FROM TB_EMPRESA;";

	private Connection connection;

	public EmpresaDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(Empresa empresa) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, empresa.getCnpj());
			stmt.setString(2, empresa.getNome());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Empresa empresa) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, empresa.getCnpj());
			stmt.setString(2, empresa.getNome());
			stmt.setInt(3, empresa.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Empresa empresa) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, empresa.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Empresa buscaPor(int id) {
		Empresa empresa = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					empresa = new Empresa();
					empresa.setId(rs.getInt("ID_EMPRESA"));
					empresa.setNome(rs.getString("NM_EMPRESA"));
					empresa.setCnpj(rs.getString("CD_CNPJ"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return empresa;
	}

	public List<Empresa> listaTodos() {
		List<Empresa> empresas = new ArrayList<Empresa>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt("ID_EMPRESA"));
				empresa.setNome(rs.getString("NM_EMPRESA"));
				empresa.setCnpj(rs.getString("CD_CNPJ"));

				empresas.add(empresa);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return empresas;
	}
}
