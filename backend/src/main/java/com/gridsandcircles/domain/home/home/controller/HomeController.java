package com.gridsandcircles.domain.home.home.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
@Hidden
public class HomeController {

  @GetMapping(value = "/", produces = TEXT_HTML_VALUE)
  public String root() {
    return """
        <h1>API 서버</h1>
        <div>
            <a href="/swagger-ui/index.html">API 문서로 이동</a>
        </div>
        """;
  }
}
