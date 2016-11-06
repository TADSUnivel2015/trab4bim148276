package br.com.tezza.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Este filter será chamado sempre que uma nova solicitação for feita ao Faces Servlet.
 * Usada a implementação Filter pois é necessário filtrar as requisições.
 */
@WebFilter(servletNames ={ "Faces Servlet" }) // Pega a configuração feita no arquivo web.xml
public class JPAFilter implements Filter {

	// Variável responsavél por fazer a transação com o banco de dados.
	private EntityManagerFactory entityManagerFactory;

	//
	private String persistence_unit_name = "unit_app";

	// Método chamado quando quisermos encerrar a transação com o banco de dados.
	public void destroy() {
		this.entityManagerFactory.close();
	}

	//
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// Recebe o contexto de persistência.
		EntityManager entityManager =  this.entityManagerFactory.createEntityManager();

		// Adiciona o entityManegar na requisição.
		request.setAttribute("entityManager", entityManager);

		// Inicia a transação com o banco de dados.
		entityManager.getTransaction().begin();

		// Inicia o Faces Servlet.
		chain.doFilter(request, response);

		try {
			// Se a operação for executada com sucesso, o entityManager realiza o commit.
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			// Caso ocorra algum erro durante a execução da operação, o entityManager realiza o rollback.
			entityManager.getTransaction().rollback();
		}
		finally{
			// Caso o commit ou rollback sejam realizados, o entityManager é encerrado.
			entityManager.close();
		}
	}

	// Método para criar o entityManagerFactory
	public void init(FilterConfig fConfig) throws ServletException {
		// Cria o entityManagerFactory com base no arquivo persistence.xml.
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name);
	}

}
