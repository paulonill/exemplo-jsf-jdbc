package br.com.pjr.exemplojsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.pjr.exemplojsf.entidade.Produto;
import br.com.pjr.exemplojsf.util.Conexao;

public class ProdutoDAO {
	
	public void incluir(final Produto produto) throws ClassNotFoundException, SQLException {
		
		Connection connection = Conexao.obterConexao();
		
		final String sql = "INSERT INTO produto (nome, codigo, descricao, valor) VALUES (?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			Conexao.executarSQL(preparedStatement, produto.getNome().trim(), produto.getCodigo().trim(), produto.getDescricao().trim(), produto.getValor());
			
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			Conexao.fecharConexao(preparedStatement, connection, null);
		}
		
	}
	
	public void alterar(final Produto produto) throws ClassNotFoundException, SQLException {
		
		Connection connection = Conexao.obterConexao();
		
		final String sql = "UPDATE produto SET nome = ?, codigo = ?, descricao = ?, valor = ? WHERE id = ?";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			Conexao.executarSQL(preparedStatement, produto.getNome().trim(), produto.getCodigo().trim(), produto.getDescricao().trim(), produto.getValor(), produto.getId());
			
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			Conexao.fecharConexao(preparedStatement, connection, null);
		}
		
	}
	
	public void excluir(final Produto produto) throws ClassNotFoundException, SQLException {
		
		Connection connection = Conexao.obterConexao();
		
		final String sql = "DELETE FROM produto WHERE id = ?";
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			Conexao.executarSQL(preparedStatement, produto.getId());
			
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			Conexao.fecharConexao(preparedStatement, connection, null);
		}
	}
	
	public Produto pesquisarPorId(final Long id) throws ClassNotFoundException, SQLException {
		
		Connection connection = Conexao.obterConexao();
		
		final String sql = "SELECT id, nome, codigo, descricao, valor FROM produto WHERE id = ?";
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Produto produto = new Produto();
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = Conexao.consultarSQL(preparedStatement, id);
			
			if (resultSet.next()) {
				produto = popularProduto(resultSet);
			}
			
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			Conexao.fecharConexao(preparedStatement, connection, null);
		}
		
		return produto;
	}
	
	public List<Produto> listar() throws ClassNotFoundException, SQLException {
		
		Connection connection = Conexao.obterConexao();
		
		final String sql = "SELECT id, nome, codigo, descricao, valor FROM produto";
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Produto> produtos = new ArrayList<Produto>();
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = Conexao.consultarSQL(preparedStatement);
			
			if (resultSet.next()) {
				produtos.add(popularProduto(resultSet));
			}
			
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			Conexao.fecharConexao(preparedStatement, connection, null);
		}
		
		return produtos;
	}
	
	private Produto popularProduto(final ResultSet resultSet) throws SQLException {
		Produto produto = new Produto();

		if (resultSet != null) {
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome").trim());
			produto.setCodigo(resultSet.getString("codigo").trim());
			produto.setDescricao(resultSet.getString("descricao").trim());
			produto.setValor(resultSet.getBigDecimal("valor"));
		}
		
		return produto;
	}

}
