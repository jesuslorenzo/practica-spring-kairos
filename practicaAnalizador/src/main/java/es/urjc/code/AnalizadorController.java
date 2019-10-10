package es.urjc.code;

import java.util.Arrays;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalizadorController{

	@GetMapping("/")
	public boolean analizarTexto(@RequestParam String comentario) {
		String[] palabras = {"teta", "pedo", "culo", "pis"};
	    return Arrays.stream(palabras).parallel().anyMatch(comentario::contains);
	}
}