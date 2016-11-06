package br.com.tezza.repository;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.repository.entity.PessoaEntity;
import br.com.tezza.repository.entity.UsuarioEntity;
import br.com.tezza.uteis.Uteis;

/**
 *
 * @author Alex Tezza
 * 5 de nov de 2016
 *
 *	Esta classe é responsável por persistir a entidade PessoaEntity.
 */
public class PessoaRepository {

	@Inject  // Realiza a injeção de dependência no variável pessoaEntity.
	PessoaEntity pessoaEntity;

	// Declara a variável entityManeger que será utilizada no momento de realizar
	// alguma operação com o banco de dados.
	EntityManager entityManager;

	// Método chamado toda vez que mandamos salvar uma nova pessoa.
	// Parâmetro pessoaModel contem os dados informados no formulário.
	public void SalvarNovoRegistro(PessoaModel pessoaModel){

		// Atribui valor para a variável entityManager.
		// Agora podemos utiliza-la para realizar operações com o banco de dados.
		entityManager =  Uteis.JpaEntityManager();

		// Instância um novo objeto do tipo PessoaEntity.
		pessoaEntity = new PessoaEntity();

		// Atribui valor aos atributos do objeto pessoaEntity.
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		// Faz a busca pelo usuário vínculado à pessoa.
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo());

		// pessoaEntity recebe o valor de usuarioEntity.
		// Agora pessoaEntity possui a chave entrangeira.
		pessoaEntity.setUsuarioEntity(usuarioEntity);

		// Grava no banco de dados o objeto pessoaEntity.
		entityManager.persist(pessoaEntity);

	}
}
