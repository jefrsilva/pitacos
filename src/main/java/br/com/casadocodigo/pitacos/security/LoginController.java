package br.com.casadocodigo.pitacos.security;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {

	@PostMapping(value = "/api/v1/public/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<LoginDTO> request = RequestEntity.post(URI.create("http://localhost:8080/api/v1/login"))
				.contentType(MediaType.APPLICATION_JSON).body(loginDTO);
		try {
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);
			List<String> headers = response.getHeaders().get(TokenAuthenticationService.AUTH_HEADER_NAME);
			if (headers.isEmpty()) {
				throw new RuntimeException("Sem Authorization header na resposta do login");
			}
				
			String token = headers.get(0).replace("Bearer ", "");
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
