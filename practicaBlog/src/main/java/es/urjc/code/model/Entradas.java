package es.urjc.code.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
public class Entradas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id = -1;
	
	private String nombre;
	private String nick;
	private String titulo;
	private String resumen;
	private String texto;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToMany(cascade=CascadeType.ALL)
	private List<Comentarios> comentarios;

	public Entradas() {
	}
	
	public Entradas(String nombre, String nick, String titulo, String resumen, String texto) {
		super();
		this.nombre = nombre;
		this.nick = nick;
		this.titulo = titulo;
		this.resumen = resumen;
		this.texto = texto;
		this.comentarios = new ArrayList<Comentarios>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
