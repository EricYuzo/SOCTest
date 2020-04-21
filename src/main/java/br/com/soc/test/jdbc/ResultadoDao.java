package br.com.soc.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.test.modelo.Resultado;

public class ResultadoDao {

	private static final String SQL_INSERT   = "INSERT INTO TB_RESULTADO (NM_RESULTADO) VALUES (?);";
	private static final String SQL_UPDATE   = "UPDATE TB_RESULTADO SET NM_RESULTADO = ? WHERE ID_RESULTADO = ?;";
	private static final String SQL_DELETE   = "DELETE FROM TB_RESULTADO WHERE ID_RESULTADO = ?;";
	private static final String SQL_FIND_ID  = "SELECT * FROM TB_RESULTADO WHERE ID_RESULTADO = ?;";
	private static final String SQL_FIND_ALL = "SELECT * FROM TB_RESULTADO;";

	private Connection connection;

	public ResultadoDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inclui(Resultado resultado) {
		String sql = SQL_INSERT;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, resultado.getNome());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Resultado resultado) {
		String sql = SQL_UPDATE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, resultado.getNome());
			stmt.setInt(2, resultado.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Resultado resultado) {
		String sql = SQL_DELETE;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, resultado.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Resultado buscaPor(int id) {
		Resultado resultado = null;

		String sql = SQL_FIND_ID;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					resultado = new Resultado();
					resultado.setId(rs.getInt("ID_RESULTADO"));
					resultado.setNome(rs.getString("NM_RESULTADO"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return resultado;
	}

	public List<Resultado> listaTodos() {
		List<Resultado> resultados = new ArrayList<Resultado>();

		String sql = SQL_FIND_ALL;
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
}
