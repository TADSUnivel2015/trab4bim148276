package br.com.tezza.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.repository.PessoaRepository;
/**
 * author Alex Tezza
 * 11 de nov de 2016
 *
 * Esta classe é responsável por controlar o carousel.
 *
 */
@Named(value="consultarPessoaCarouselController") // Transforma a classe em um bean gerenciado pelo CDI e passa o nome dele através do value.
@ViewScoped // O contexto só vala para a mesma sessão.
//Implementa Serializable porque irá trafegar o objeto.
public class ConsultarPessoaCarouselController implements Serializable {

	// Identificação de serialização da classe.
	private static final long serialVersionUID = 1L;

	@Inject transient // Realiza a injeção de dependência na variável pessoaRepository.
	private PessoaRepository pessoaRepository;

	@Produces // Todos os pontos de injeção que precisarem de pessoas irão invocar este.
	private List<PessoaModel> pessoas;

	// Método simples para retornar uma lista de pessoas.
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	// Métoto chamado logo após a construção da classe.
	// Método atribui valor para a variável global da classe.
	@PostConstruct
	private void init(){
		this.pessoas = pessoaRepository.GetPessoas();
	}
}
