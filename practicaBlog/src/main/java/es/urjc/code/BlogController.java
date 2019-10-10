package es.urjc.code;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import es.urjc.code.model.Comentarios;
import es.urjc.code.model.Entradas;
import es.urjc.code.repository.ComentariosRepository;
import es.urjc.code.repository.EntradasRepository;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private EntradasRepository entradasRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@Value("${property.servicio.analizador}")
	private String servicioAnalizador;
	
	@PostConstruct
	public void init() {
		Entradas entrada1 = new Entradas("Jesus", "Sefirot", "Titulo 1", "Resumen 1", "Texto 1");
		Entradas entrada2 = new Entradas("Isaac", "Isako", "Titulo 2", "Resumen 2", "Texto 2");
		
		entrada1.getComentarios().add(new Comentarios("Facundo", "Contenido", LocalDate.now()));
		entrada1.getComentarios().add(new Comentarios("Miguel", "Contenido", LocalDate.now().minusDays(1)));
		entrada2.getComentarios().add(new Comentarios("Luis", "Contenido", LocalDate.now().minusMonths(1)));
		
		entradasRepository.save(entrada1);
		entradasRepository.save(entrada2);
	}
	
	//GET
	@GetMapping("/entradas")
	public Page<Entradas> entradas(Pageable page){
		Page<Entradas> listaEntradas = entradasRepository.findAll(page);
		
		//Se eliminan los comentarios de cada entrada del blog
		for(Entradas entrada : listaEntradas)
			entrada.setComentarios(null);
		
		return listaEntradas;
	}
	
	@GetMapping("/comentarios")
	public Page<Comentarios> comentarios(Pageable page){
		return comentariosRepository.findAll(page);
	}
	
	
	//GET ID
	@GetMapping("/entradas/{id}")
	public Entradas getEntrada(@PathVariable long id) {
		return entradasRepository.findById(id).get();
	}
	
	@GetMapping("/comentarios/{id}")
	public Comentarios getComentario(@PathVariable long id) {
		return comentariosRepository.findById(id).get();
	}
	
	
	//POST
	@PostMapping("/entradas")
	@ResponseStatus(HttpStatus.CREATED)
	public Entradas nuevaEntrada(@RequestBody Entradas entrada) {
		return entradasRepository.save(entrada);
	}
	
	@PostMapping("/comentarios/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Entradas> nuevoComentario(@PathVariable long id, @RequestBody Comentarios comentario) {
		
		ResponseEntity<Boolean> resultado = new RestTemplate().exchange("http://"+servicioAnalizador+ "/?comentario=" +comentario.getContenido(), HttpMethod.GET, null, boolean.class);
		
		if(resultado.getBody()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Entradas entrada = entradasRepository.findById(id).get();
			entrada.getComentarios().add(comentario);
			
			entradasRepository.save(entrada);
			return new ResponseEntity<>(entrada, HttpStatus.OK);
		}
	}
	
	
	//PUT
	@PutMapping("entradas/{id}")
	public Entradas actualizaEntrada(@PathVariable long id, @RequestBody Entradas entradaActualizada) {

		Entradas entrada = entradasRepository.findById(id).get();
		
		entrada.setNombre(entradaActualizada.getNombre());
		entrada.setNick(entradaActualizada.getNick());
		entrada.setTitulo(entradaActualizada.getTitulo());
		entrada.setResumen(entradaActualizada.getResumen());
		entrada.setTexto(entradaActualizada.getTexto());
		
		return entradasRepository.save(entrada);
	}
	
	@PutMapping("comentarios/{id}")
	public Comentarios actualizaComentario(@PathVariable long id, @RequestBody Comentarios comentarioActualizado) {

		Comentarios comentario = comentariosRepository.findById(id).get();
			
		comentario.setNick(comentarioActualizado.getNick());
		comentario.setContenido(comentarioActualizado.getContenido());
		comentario.setFecha(comentarioActualizado.getFecha());
		
		return comentariosRepository.save(comentario);
	}
	
	
	//DELETE
	@DeleteMapping("/entradas/{id}")
	public void borraEntrada(@PathVariable long id) {
		entradasRepository.deleteById(id);
	}
	
}
