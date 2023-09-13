package ru.client.restRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.client.models.Ingredient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Slf4j
/*@Component("clnt")*/
public class Client {
    private RestTemplate restTemplate;

    public Client(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] bytes,
                                                ClientHttpRequestExecution execution) throws IOException, IOException {
                request.getHeaders().add("Authorization", "Bearer " + accessToken);
                return execution.execute(request, bytes);
            }
        };
        return interceptor;
    }

    public List<Ingredient> getIngredientList() {
        String urlRq = "http://localhost:8080/api/ingredients";
        String authData = "user:1";
        byte[] base64EncodeAuth = Base64.getEncoder().encode(authData.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64EncodeAuth));
        log.info("Base64 date: " + new String(base64EncodeAuth));

        HttpEntity<String> rq = new HttpEntity<>("", headers);

        ResponseEntity<Ingredient[]> response = restTemplate.exchange(
                urlRq, HttpMethod.GET, rq, Ingredient[].class
        );
        log.info("body{}:" + Arrays.stream(response.getBody()).toList());
        return Arrays.asList(response.getBody());
    }

    public Ingredient addIngredient(Ingredient ingredient){
        String urlRq = "http://localhost:8080/api/ingredients";
  /*      String authData = "user:1";
        byte[] base64EncodeAuth = Base64.getEncoder().encode(authData.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64EncodeAuth));

        HttpEntity<Ingredient> rq = new HttpEntity<>(ingredient, headers);
        ResponseEntity<Ingredient> response = restTemplate.exchange(
                urlRq, HttpMethod.POST, rq, Ingredient.class);
        log.info("result :{}",response.getBody());
        return response.getBody();*/
        return restTemplate.postForObject(urlRq,ingredient, Ingredient.class);
    }

    public void deleteIngredient(String id){
        String urlRq = "http://localhost:8080/api/ingredients/"+ id;
        /*String authData = "user:1";
        byte[] base64EncodeAuth = Base64.getEncoder().encode(authData.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + new String(base64EncodeAuth));

        HttpEntity<String> rq = new HttpEntity<>("", headers);*/
        ResponseEntity<String> response = restTemplate.exchange(
                urlRq, HttpMethod.DELETE, new HttpEntity<String>(""), String.class);
    }
}
