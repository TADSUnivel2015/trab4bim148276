package br.com.tezza.uteis;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Nesta classe, temos métodos que farão a recuperação do entityManager criado no JPAFilter.
 */
public class Uteis {

	// Método chamado quando queremos recuperar o entityManager
	public static EntityManager JpaEntityManager(){

		// Irá conter todas as informações de estados da instância atual.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Permite que o JavaServerFaces execute em qualquer Servlet.
		ExternalContext externalContext = facesContext.getExternalContext();

		// Cria um objeto HttpServletRequest e o repassa
		// para os métodos de serviço do Servlet.
		HttpServletRequest request  = (HttpServletRequest)externalContext.getRequest();

		// Retorna o atributo e seu estado.
		return (EntityManager)request.getAttribute("entityManager");
	}

	// Método utilizado para exibir mensagem na tela.
	// String mensagem - Parâmetro que recebe a mensagem.
	public static void Mensagem(String mensagem){
		// Irá conter todas as informações de estados da instância atual.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Aciciona a mensagem ao facesContext e exibe na tela.
		facesContext.addMessage(null, new FacesMessage("Alerta",mensagem));
	}

	// Método utilizado para exibir mensagem na tela.
	// String mensagem - Parâmetro que recebe a mensagem.
	public static void MensagemAtencao(String mensagem){
		// Irá conter todas as informações de estados da instância atual.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Aciciona a mensagem ao facesContext e exibe na tela.
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
	}

	// Método utilizado para exibir mensagem na tela.
	// String mensagem - Parâmetro que recebe a mensagem.
	public static void MensagemInfo(String mensagem){
		// Irá conter todas as informações de estados da instância atual.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Aciciona a mensagem ao facesContext e exibe na tela.
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	}

}
