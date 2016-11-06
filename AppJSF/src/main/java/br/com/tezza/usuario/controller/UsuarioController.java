package br.com.tezza.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.tezza.model.UsuarioModel;
import br.com.tezza.repository.UsuarioRepository;
import br.com.tezza.repository.entity.UsuarioEntity;
import br.com.tezza.uteis.Uteis;

@Named(value="usuarioController") // Transforma a classe em um bean gerenciado pelo CDI.
@SessionScoped // O contexto será compartilhado entre todas as solicitações do Servlet que ocorrem na mesma sessão.
public class UsuarioController implements Serializable { // Ao utilizar o CDI é necessário implementar Serializable.

	// Identificação de serialização da classe.
	private static final long serialVersionUID = -5592868244498065394L;

	@Inject   // Variável usuarioModel receberá injeção de dependência.
	private UsuarioModel usuarioModel;

	@Inject  // Variável usuarioRepository receberá injeção de dependência.
	private UsuarioRepository usuarioRepository;

	@Inject // Variável usuarioEntity receberá injeção de dependência.
	private UsuarioEntity usuarioEntity;

	/*
	 * Getter and Setter
	 */
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	// Este método retorna o usuário logado no sistema
	public UsuarioModel GetUsuarioSession(){

		// Irá conter todas as informações de estados da instância atual.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Retorna o usuário que esta logado.
		return (UsuarioModel)facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	// Finaliza a sessão do usuário logado
	public String Logout(){

		// Encerra a sessão do usuário.
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		// Exibe para o usuário a tela de login.
		return "/index.xhtml?faces-redirect=true";
	}

	// Realiza a autenticação do usuário.
	public String EfetuarLogin(){

		// Verifica se o login foi informado.
		if(StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())){
			// Exibe a mensagem para o usuário.
			Uteis.Mensagem("Favor informar o login!");
			// Retorna null pois não foi possível proseguir.
			return null;
		}
		else
			// Verifica se a senha foi informada.
			if(StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())){
				// Exibe a mensagem para o usuário.
				Uteis.Mensagem("Favor informara senha!");
				// Retorna null pois não foi possível proseguir.
				return null;
		}
		else{
			// Login e Senha informadas.

			// Valida o login e senha para saber se o usuário esta cadastrado.
			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);

			// Se for diferente de null, o usuário possui cadastro.
			if(usuarioEntity!= null){
				// Seta valor nulo para a variável senha.
				usuarioModel.setSenha(null);
				// Seta o valor do código do usuário para o código do model.
				usuarioModel.setCodigo(usuarioEntity.getCodigo());

				// Irá conter todas as informações de estados da instância atual.
				FacesContext facesContext = FacesContext.getCurrentInstance();

				// Adiciona no facesContext o usuário e diz que foi autenticado.
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				// Redireciona o usuário para tela principal(home).
				return "sistema/home?faces-redirect=true";
			}
			else{
				// Caso o login e senha não existam.

				// Exibe a mensagem para o usuário.
				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				// retorna null pois não foi possível realizar a operaç
				return null;
			}
		}
	}
}