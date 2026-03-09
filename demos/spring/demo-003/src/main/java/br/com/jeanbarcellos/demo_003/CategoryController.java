package br.com.jeanbarcellos.demo_003;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @GetMapping("/")
    public String index() {
        return "Teste";
    }

    @GetMapping("/teste1")
    public CategoryResponse teste1() {

        var response = new CategoryResponse();
        response.setId(1L);
        response.setName("Test");

        return response;
    }

    @GetMapping("/teste2")
    public ResponseEntity<CategoryResponse> teste2() {

        var response = new CategoryResponse();
        response.setId(1L);
        response.setName("Test");

        return ResponseEntity.ok(response);
    }

}
