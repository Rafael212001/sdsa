package br.com.entities;

public class Professor {
	private Integer id;
	private String nome;
	private String disciplina_le;
	private int carga_hora;
	private int restante;
	private String tipo;
	private int login;
	private String senha;
	private int foto;
	private int id_disciplina;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDisciplina_le() {
		return disciplina_le;
	}
	public void setDisciplina_le(String disciplina_le) {
		this.disciplina_le = disciplina_le;
	}
	public int getCarga_hora() {
		return carga_hora;
	}
	public void setCarga_hora(int carga_hora) {
		this.carga_hora = carga_hora;
	}
	public int getRestante() {
		return restante;
	}
	public void setRestante(int restante) {
		this.restante = restante;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getFoto() {
		return foto;
	}
	public void setFoto(int foto) {
		this.foto = foto;
	}
	public int getId_disciplina() {
		return id_disciplina;
	}
	public void setId_disciplina(int id_disciplina) {
		this.id_disciplina = id_disciplina;
	}
	
	
}
