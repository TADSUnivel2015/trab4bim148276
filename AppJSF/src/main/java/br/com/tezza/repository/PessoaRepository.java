package br.com.tezza.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.model.UsuarioModel;
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

	// Lista todos os registros da tabela Pessoa.
	public List<PessoaModel> GetPessoas(){
		// Cria uma lista para adicionar registros do tipo PessoaModel.
		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();

		// Inicia a variável. Agora é possível realizar operação com o banco de dados.
		entityManager =  Uteis.JpaEntityManager();

		// Passa a query que deseja utilizar na consulta.
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");

		@SuppressWarnings("unchecked")
		// Recebe todos os registros encontrados na consulta.
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>)query.getResultList();

		// Seta valor nulo para pessoaModel.
		PessoaModel pessoaModel = null;

		// Percorre a lista pessoasEntity.
		for (PessoaEntity pessoaEntity : pessoasEntity) {

			// Instância uma nova PessoaModel.
			pessoaModel = new PessoaModel();
			//Seta os objetos da lista pessoasEntity para o objeto pessoaModel.
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());

			// Altera o tipo de origem para quando for exibido mostrar o nome inteiro é não só a inicial.
			if(pessoaEntity.getOrigemCadastro().equals("X"))
				pessoaModel.setOrigemCadastro("XML");
			else
				pessoaModel.setOrigemCadastro("INPUT");

			// Adiciona uma 'mascara' para quando for exibir o resultado mostrar Masculino e Feminino ao invez de M e F.
			if(pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			else
				pessoaModel.setSexo("Feminino");

			// Cria um novo objeto para receber o valor do objeto pessoaEntity.
			UsuarioEntity usuarioEntity =  pessoaEntity.getUsuarioEntity();

			// Instância um novo UsuarioModel.
			UsuarioModel usuarioModel = new UsuarioModel();
			// Seta usuarioEntity para o usuarioModel.
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			// Seta usuarioMode para pessoaModel.
			pessoaModel.setUsuarioModel(usuarioModel);

			// Adiciona pessoaModel a lista pessoasModel.
			pessoasModel.add(pessoaModel);
		}

		// Retorna uma lista contendo todos os registros do tipo Pessoa encontrados no banco de dados.
		return pessoasModel;
	}

	// Constulta uma pessoa pelo seu código.
	// codigo contém o código da pessoa.
	private PessoaEntity GetPessoa(int codigo){

		entityManager =  Uteis.JpaEntityManager();

		return entityManager.find(PessoaEntity.class, codigo);
	}

	// Altera a pessoa no banco de dados.
	// pessoaModel contém a pessoa que será alterada.
	public void AlterarRegistro(PessoaModel pessoaModel){

		entityManager =  Uteis.JpaEntityManager();

		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());

		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSexo(pessoaModel.getSexo());

		entityManager.merge(pessoaEntity);
	}

	// Método para realizar a exclusão de registro
	// codigo contém a informação de qual registro será excluido.
	public void ExcluirRegistro(int codigo){

		entityManager =  Uteis.JpaEntityManager();

		PessoaEntity pessoaEntity = this.GetPessoa(codigo);

		entityManager.remove(pessoaEntity);
	}

	// Retorna a quantidade de registros por origem de cadastro.
	public Hashtable<String, Integer> GetOrigemPessoa(){

		Hashtable<String, Integer> hashtableRegistros = new Hashtable<String,Integer>();

		entityManager =  Uteis.JpaEntityManager();

		Query query = entityManager.createNamedQuery("PessoaEntity.GroupByOrigemCadastro");

		@SuppressWarnings("unchecked")
		Collection<Object[]> collectionRegistros  = (Collection<Object[]>)query.getResultList();

		for (Object[] objects : collectionRegistros) {

			String tipoPessoa 		= (String)objects[0];
			int	   totalDeRegistros = ((Number)objects[1]).intValue();

			if(tipoPessoa.equals("X"))
				tipoPessoa = "XML";
			else
				tipoPessoa = "INPUT";

			hashtableRegistros.put(tipoPessoa, totalDeRegistros);

		}
		return hashtableRegistros;
	}
}
