package br.com.tezza.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Esta classe é responsável por persistir usuários no banco de dados
 *
 * Usamos o implements Serializable porque iremos trafegas esta objeto pela rede.
 */

// Annotations
@Table(name="tb_usuario") // Nome da tabela no banco de dados.
@Entity                   // Diz que será uma tabela.
@NamedQuery               // Possui a query para fazer a autenticação do usuário, quando passado usuário e senha.
	(name = "UsuarioEntity.findUser",
		    query= "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario AND u.senha = :senha")
public class UsuarioEntity implements Serializable{

	// Identificação de serialização da classe.
	private static final long serialVersionUID = -2868643310553647472L;

	@Id                         // Identifica este atributo como Primary Key da tabela.
	@GeneratedValue             // Identifica este atributo para receber valor automático.
	@Column(name="id_usuario")  // Nome que este atributo no banco de dados.
	private String codigo;

	@Column(name="ds_login") // Nome que este atributo no banco de dados.
	private String usuario;

	@Column(name="ds_senha") // Nome que este atributo no banco de dados.
	private String senha;

	/*
	 * Getters
	 */

	public String getCodigo() {
		return codigo;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSenha() {
		return senha;
	}

	/*
	 * Setters
	 */

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
