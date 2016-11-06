package br.com.tezza.pessoa.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

	// Variável que irá receber o arquivo xml.
	private UploadedFile file;

	/*
	 * Getter and Setter
	 */

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
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

	// Metódo que carrega o arquivo xml
	public void UploadRegistros() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			if(this.file.getFileName().equals("")){
				Uteis.MensagemAtencao("Nenhum arquivo selecionado!");
				return;
			}

			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(this.file.getInputstream());

			Element element = doc.getDocumentElement();

			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node  = nodes.item(i);

				if(node.getNodeType() == Node.ELEMENT_NODE){

					Element elementPessoa =(Element) node;

					// Pega os valores do arquivo xml.
					String nome     = elementPessoa.getElementsByTagName("nome").item(0).getChildNodes().item(0).getNodeValue();
					String sexo     = elementPessoa.getElementsByTagName("sexo").item(0).getChildNodes().item(0).getNodeValue();
					String email    = elementPessoa.getElementsByTagName("email").item(0).getChildNodes().item(0).getNodeValue();
					String endereco = elementPessoa.getElementsByTagName("endereco").item(0).getChildNodes().item(0).getNodeValue();

					// Instância um novo objeto do tipo PessoaModel.
					PessoaModel newPessoaModel = new PessoaModel();

					// Popula o objeto com os valores retirados do xml.
					newPessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
					newPessoaModel.setEmail(email);
					newPessoaModel.setEndereco(endereco);
					newPessoaModel.setNome(nome);
					newPessoaModel.setOrigemCadastro("X");
					newPessoaModel.setSexo(sexo);

					// Manda salvar o objeto.
					pessoaRepository.SalvarNovoRegistro(newPessoaModel);
				}
			}

			// Notifica o usuário de que a ação foi realizada.
			Uteis.MensagemInfo("Registros cadastrados com sucesso!");

			// Em caso de erro, lança as exeções abaixo.
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
