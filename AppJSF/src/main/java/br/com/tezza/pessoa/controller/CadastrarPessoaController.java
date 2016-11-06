package br.com.tezza.pessoa.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.repository.PessoaRepository;
import br.com.tezza.usuario.controller.UsuarioController;
import br.com.tezza.uteis.Uteis;

/**
 * @author Alex Tezza
 * 5 de nov de 2016
 *
 * 	Classe responsável por controlar a persistência de objetos do tipo PessoaEntity.
 */
@Named(value="cadastrarPessoaController") // Transforma a classe em um bean gerenciado pelo CDI e passa o nome dele através do value.
@RequestScoped  // Cada solicitação do Servlet gera um novo contexto.
public class CadastrarPessoaController {

	@Inject  // Realiza a injeção de dependência na variável pessoaModel.
	PessoaModel pessoaModel;

	@Inject // Realiza a injeção de dependência na variável usuarioController.
	UsuarioController usuarioController;

	@Inject // Realiza a injeção de dependência na variável pessoaRepository.
	PessoaRepository pessoaRepository;

	/*
	 * Getter and Setter
	 */

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	// Executa a ação de salvar
	public void SalvarNovaPessoa(){

		// Resgata a sessão do usuário.
		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());

		// Infoma que o cadastro foi via Input
		pessoaModel.setOrigemCadastro("I");

		// Chama o método salvar da classe PessoaRepository.
		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);

		// Atribiu valor nulo para a variável global da classe.
		this.pessoaModel = null;

		// Informa que a operação foi realizada.
		Uteis.MensagemInfo("Registro cadastrado com sucesso");

	}

}
