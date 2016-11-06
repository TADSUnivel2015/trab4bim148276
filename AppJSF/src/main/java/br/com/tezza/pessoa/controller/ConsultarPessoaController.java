package br.com.tezza.pessoa.controller;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.repository.PessoaRepository;
/**
 * @author Alex Tezza
 * 6 de nov de 2016
 *
 *	Classe responsável por controlar a consulta de pessoas.
 */
@Named(value="consultarPessoaController") // Transforma a classe em um bean gerenciado pelo CDI e passa o nome dele através do value.
@ViewScoped // O contexto só vala para a mesma sessão.
// Implementa Serializable porque irá trafegar o objeto.
public class ConsultarPessoaController implements Serializable {

	// Identificação de serialização da classe.
	private static final long serialVersionUID = 1L;

	@Inject transient // Realiza a injeção de dependência na variável pessoaModel.
	private PessoaModel pessoaModel;

	@Produces // Todos os pontos de injeção que precisarem de pessoas irão invocar este.
	private List<PessoaModel> pessoas;

	@Inject transient // Realiza a injeção de dependência na variável pessoaRepository.
	private PessoaRepository pessoaRepository;

	/*
	 * Getters and Setters
	 */

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}
	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	@PostConstruct // Após a construçao da classe, executa este método.
	// Carrega as pessoas.
	public void init(){
		// Retorna todas as pessoa cadastradas.
		this.pessoas = pessoaRepository.GetPessoas();
	}

}