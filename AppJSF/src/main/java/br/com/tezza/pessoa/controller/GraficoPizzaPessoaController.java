package br.com.tezza.pessoa.controller;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.tezza.repository.PessoaRepository;
/**
 * author Alex Tezza
 * 11 de nov de 2016
 *
 *	Esta classe ira realizar o controle do gráfico de visualização de dados.
 *
 */
@Named(value="graficoPizzaPessoaController") // Transforma a classe em um bean gerenciado pelo CDI e passa o nome dele através do value.
@RequestScoped // Cada solicitação do Servlet gera um novo contexto.
public class GraficoPizzaPessoaController {

	@Inject // Realiza a injeção de dependência na variável pessoaRepository.
	private PessoaRepository pessoaRepository;

	// Cria a variável pieChartModel.
	private PieChartModel pieChartModel;

	// Método simples para retornar o valor da variável global piechartModel.
	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	@PostConstruct // Após a construçao da classe, executa este método.
	// Método para iniciar instânciar a variável pieChatModel e chamar o método MontaGrafico.
	public  void init(){

		this.pieChartModel = new PieChartModel();

		this.MontaGrafico();
	}

	// Método realiza a montagem do gráfico na tela.
	private void MontaGrafico(){

		//Realiza a consulta dos dados para montar o gráfico.
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();

		//Informa os valores para montar o gráfico.
		hashtableRegistros.forEach((chave,valor) -> {
			pieChartModel.set(chave, valor);
		});

		pieChartModel.setTitle("Total de Pessoas cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");
	}
}
