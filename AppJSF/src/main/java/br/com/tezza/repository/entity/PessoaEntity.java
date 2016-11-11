package br.com.tezza.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Alex Tezza
 * 5 de nov de 2016
 *
 *	Esta classe contém todos os atributos referentes à uma Pessoa.
 */
@Entity // Diz que esta classe é uma entidade
@Table(name="tb_pessoa") // Nome desta tabela no banco de dados.
// Query para buscar todos os registros cadastradas na tabela Pessoa.
@NamedQueries({
	@NamedQuery(name = "PessoaEntity.findAll",query= "SELECT p FROM PessoaEntity p"),
	// Vai retornar o total de pessoas por origem de cadastro
	@NamedQuery(name="PessoaEntity.GroupByOrigemCadastro",query= "SELECT p.origemCadastro, count(p) as total FROM PessoaEntity p GROUP By p.origemCadastro")
})
public class PessoaEntity {

	@Id              // Este atributo será a chave primária da tabela tb_pessoa.
	@GeneratedValue  // Este atrubuto tem seu valor setado automaticamente.
	@Column(name = "id_pessoa") // Este atributo terá o nome "id_pessoa" no banco de dados.
	private Integer codigo;

	@Column(name = "nm_pessoa") // Este atributo terá o nome "nm_pessoa" no banco de dados.
	private String  nome;

	@Column(name = "fl_sexo") // Este atributo terá o nome "fl_sexo" no banco de dados.
	private String  sexo;

	@Column(name = "dt_cadastro") // Este atributo terá o nome "dt_cadastro" no banco de dados.
	private LocalDateTime	dataCadastro;

	@Column(name = "ds_email") // Este atributo terá o nome "ds_email" no banco de dados.
	private String  email;

	@Column(name = "ds_endereco") // Este atributo terá o nome "ds_endereco" no banco de dados.
	private String  endereco;

	@Column(name = "fl_origemCadastro") // Este atributo terá o nome "fl_origemCadastro" no banco de dados.
	private String  origemCadastro;

	@OneToOne // Diz que esta classe(Tabela) fará relacionamento de 1 para 1 com a tabela Pessoa.
	@JoinColumn(name="id_usuario_cadastro") // Este atributo terá o nome "id_usuario_cadastro" no banco de dados.
	private UsuarioEntity usuarioEntity;

	/*
	 * Getters and Setters
	 */

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getOrigemCadastro() {
		return origemCadastro;
	}
	public void setOrigemCadastro(String origemCadastro) {
		this.origemCadastro = origemCadastro;
	}
	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}
	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

}
