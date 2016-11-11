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

	// Carrega as informações da pessoa para ser editada.
	// pessoaModel contém as informações da pessoa.
	public void Editar(PessoaModel pessoaModel){

		// Recebe apenas a primeira letra do sexo para setar M ou F.
		pessoaModel.setSexo(pessoaModel.getSexo().substring(0, 1));

		this.pessoaModel = pessoaModel;
	}

	// Atualiza o restistro da pessoa que foi alterada.
	public void AlterarRegistro(){

		this.pessoaRepository.AlterarRegistro(this.pessoaModel);

		// Recarrega os registros.
		this.init();
	}

	// Método para excluir registro.
	// pessoaModel contém o registro que será excluido.
	public void ExcluirPessoa(PessoaModel pessoaModel){
		//Exclui a pessoa do banco de dados.
		this.pessoaRepository.ExcluirRegistro(pessoaModel.getCodigo());
		//Remove o registro excluido da lista.
		//Logo após o registro ser excluido a tabela de registros é atualizada.
		this.pessoas.remove(pessoaModel);

	}
}