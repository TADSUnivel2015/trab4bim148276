package br.com.tezza.model;

import java.io.Serializable;

/**
 * @author Alex Tezza
 * 2 de nov de 2016
 *
 * Esta classe recebe as informações da tela e monta objeto com elas.
 * Usamos o implements Serializable porque iremos trafegas esta objeto pela rede.
 */
public class UsuarioModel implements Serializable{

	// Identificação de serialização da classe.
	private static final long serialVersionUID = -9159222058537609960L;

	// Atributos
	private String codigo;
	private String usuario;
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
