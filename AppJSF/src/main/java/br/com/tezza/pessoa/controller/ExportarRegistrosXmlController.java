package br.com.tezza.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.tezza.model.PessoaModel;
import br.com.tezza.repository.PessoaRepository;
/**
 *
 * author Alex Tezza
 * 11 de nov de 2016
 *
 * 	Esta classe é responsável por controlar a exportação de dados para xml.
 */
@Named(value="exportarRegistrosXmlController") // Transforma a classe em um bean gerenciado pelo CDI e passa o nome dele através do value.
@RequestScoped // Cada solicitação do Servlet gera um novo contexto.
//Implementa Serializable porque irá trafegar o objeto.
public class ExportarRegistrosXmlController implements Serializable {

	// Identificação de serialização da classe.
	private static final long serialVersionUID = 1L;

	@Inject transient // Realiza a injeção de dependência na variável pessoaRepository.
	PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	// Este método realiza o download do arquivo XML.
	public StreamedContent getArquivoDownload() {

		this.DownlaodArquivoXmlPessoa();

		return arquivoDownload;
	}

	// Este método gera o arquivo XML para exportação.
	private File GerarXmlPessoas(){

		// Mascara para formatar a data que será adicionada no arquivo XML.
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		// Lista que armazena todos as pessoas cadastradas no banco de dados.
		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();

		// Elemento raiz do arquivo XML.
		Element elementPessoas = new Element("Pessoas");

		Document documentoPessoas = new Document(elementPessoas);

		pessoasModel.forEach(pessoa -> {

			// Monta as tags do XML com seus valores.
			Element elementPessoa = new Element("Pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo()));

			// Formata a data.
			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);

			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));

			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("endereco").setText(pessoa.getEndereco()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));

			elementPessoas.addContent(elementPessoa);
		});

		XMLOutputter xmlGerado = new XMLOutputter();

		try {


			// Gera o nome do arquivo XML que será exportado.
			String nomeArquivo =  "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");

			// Caminho onde será gerado o arquivo XML.
			File arquivo = new File("./".concat(nomeArquivo));

			FileWriter fileWriter =  new FileWriter(arquivo);

			xmlGerado.output(documentoPessoas, fileWriter);

			return arquivo;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	// Método que prepara o arquivo XML para download.
	public void DownlaodArquivoXmlPessoa(){

		File arquivoXml = this.GerarXmlPessoas();

		InputStream inputStream;

		try {

			inputStream = new FileInputStream(arquivoXml.getPath());

			arquivoDownload = new DefaultStreamedContent(inputStream,"application/xml",arquivoXml.getName());

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
}