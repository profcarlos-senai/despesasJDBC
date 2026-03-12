package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Despesa;
import util.ConexaoDB;

public class DespesaRepository {
	
	private static Connection conexao;
	
	public DespesaRepository() {
		 if(conexao == null) {
			 conexao = ConexaoDB.getConexao();
		 }// TODO Auto-generated constructor stub
	}

    private Despesa mapearDespesa(ResultSet rs) throws SQLException {

        int categoriaId = rs.getInt("categoria_id");
        Categoria categoria = (new CategoriaRepository()).buscarPorId(categoriaId);
        return new Despesa(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getBigDecimal("valor"),
                rs.getDate("data"),
                categoria
        );
    }

    public List<Despesa> listarTodas() {

        List<Despesa> despesas = new ArrayList<>();

        String sql = """
            SELECT d.id, d.descricao, d.valor, d.data,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM despesa d
            INNER JOIN categoria c ON d.categoria_id = c.id
            ORDER BY d.data DESC
        """;

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                despesas.add(mapearDespesa(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar despesas");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return despesas;
    }

    public Despesa buscarPorId(int id) {

        if (id <= 0) {
            return null;
        }

        String sql = """
            SELECT d.id, d.descricao, d.valor, d.data,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM despesa d
            INNER JOIN categoria c ON d.categoria_id = c.id
            WHERE d.id = ?
        """;

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearDespesa(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar despesa por id");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return null;
    }

    public boolean salvar(Despesa despesa) {

        if (despesa == null ||
            despesa.getDescricao() == null ||
            despesa.getValor() == null ||
            despesa.getData() == null ||
            despesa.getCategoria() == null) {
            return false;
        }

        String sql = """
            INSERT INTO despesa (descricao, valor, data, categoria_id)
            VALUES (?, ?, ?, ?)
        """;

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, despesa.getDescricao());
            stmt.setBigDecimal(2, despesa.getValor());
            stmt.setDate(3, despesa.getData());
            stmt.setInt(4, despesa.getCategoria().getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar despesa");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }

    public boolean atualizar(Despesa despesa) {

        if (despesa == null || despesa.getId() <= 0) {
            return false;
        }

        String sql = """
            UPDATE despesa
            SET descricao = ?, valor = ?, data = ?, categoria_id = ?
            WHERE id = ?
        """;

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, despesa.getDescricao());
            stmt.setBigDecimal(2, despesa.getValor());
            stmt.setDate(3, despesa.getData());
            stmt.setInt(4, despesa.getCategoria().getId());
            stmt.setInt(5, despesa.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar despesa");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }

    public boolean deletar(int id) {

        if (id <= 0) {
            return false;
        }

        String sql = "DELETE FROM despesa WHERE id = ?";

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar despesa");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }
    
    public List<Despesa> buscarPorCategoria(int categoriaId) {

        List<Despesa> despesas = new ArrayList<>();

        String sql = """
            SELECT d.id, d.descricao, d.valor, d.data,
                   c.id AS categoria_id, c.nome AS categoria_nome
            FROM despesa d
            INNER JOIN categoria c ON d.categoria_id = c.id
            WHERE c.id = ?
            ORDER BY d.data DESC
        """;

        try (
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, categoriaId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    despesas.add(mapearDespesa(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar despesas por categoria");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return despesas;
    }
}