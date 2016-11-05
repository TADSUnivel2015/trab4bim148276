package br.com.tezza.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.tezza.model.UsuarioModel;
import br.com.tezza.repository.entity.UsuarioEntity;
import br.com.tezza.uteis.Uteis;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Classe utilizada para encontrar (no banco de dados)o usuário através de seu usuário e senha.
 * Usamos o implements Serializable porque iremos trafegas esta objeto pela rede.
 */
public class UsuarioRepository implements Serializable{

	// Identificação de serialização da classe.
	private static final long serialVersionUID = 7686913973678634108L;

	// Metodo utulizado para retornar o usuário caso ele seja encontrado.
	// UsuarioModel usuarioModel - Este parâmetro recebe as informações do usuário que queremo encontrar.
	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel){
		try {
			// Recebe a query que será executada.
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");

			// Parâmetros da query.
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());

			// Caso o usuário for encontrado, ele é retornado.
			return (UsuarioEntity)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
