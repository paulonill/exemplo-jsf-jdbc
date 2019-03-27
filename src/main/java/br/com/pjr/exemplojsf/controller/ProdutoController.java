package br.com.pjr.exemplojsf.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.pjr.exemplojsf.dao.ProdutoDAO;
import br.com.pjr.exemplojsf.entidade.Produto;
import br.com.pjr.exemplojsf.util.FacesUtil;

@ManagedBean
@SessionScoped
public class ProdutoController {

	private Produto produto;
	private ProdutoDAO dao;

	private final String PAGINA_CADASTRO = "cadastro.xhtml";
	private final String PAGINA_LISTA = "lista.xhtml";

	public ProdutoController() {
		produto = new Produto();
		dao = new ProdutoDAO();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void limparCampos() {
		produto = new Produto();
	}
	
	public String salvar() {
		try {
			if (produto.getId() == null) {
				dao.incluir(produto);
				FacesUtil.exibirMensagemInfo("Inclusão realizada com sucesso!");
			} else {
				dao.alterar(produto);
				FacesUtil.exibirMensagemInfo("Alteração realizada com sucesso!");				
			}
			limparCampos();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagemErro("Erro ao realizar a operação. Tente novamente mais tarde!");			
		}
		return PAGINA_CADASTRO;
	}
	
	public String editar() {
		return PAGINA_CADASTRO;
	}
	
	public String excluir() {
		try {
			dao.excluir(produto);
			FacesUtil.exibirMensagemInfo("Exclusão realizada com sucesso!");	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagemErro("Erro ao realizar a operação. Tente novamente mais tarde!");
		}
		
		return PAGINA_LISTA;
	}
	
	public List<Produto> getLista() {
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			produtos = dao.listar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}
}
