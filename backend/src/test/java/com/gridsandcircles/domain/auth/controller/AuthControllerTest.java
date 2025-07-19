package com.gridsandcircles.domain.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasLength;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridsandcircles.domain.admin.admin.service.AdminService;
import com.gridsandcircles.global.ServiceException;
import java.util.NoSuchElementException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired
  private AdminService adminService;

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("로그인: 관리자 ID 길이 4 미만 시 400 Bad Request 발생")
  void login_1() throws Exception {
    doLogin("tes", "secret number")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("adminId")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 관리자 ID 길이 10 초과 시 400 Bad Request 발생")
  void login_2() throws Exception {
    doLogin("test1234567", "secret number")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("adminId")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 관리자 비밀번호 길이 10 미만 시 400 Bad Request 발생")
  void login_3() throws Exception {
    doLogin("test", "secret")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("password")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 관리자 비밀번호 길이 20 초과 시 400 Bad Request 발생")
  void login_4() throws Exception {
    doLogin("tes", "secret number maybe 20 lengths over")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("password")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 비밀번호 불일치 시 401 Unauthorized 발생")
  void login_5() throws Exception {
    adminService.createAdmin("test", "secret number");

    doLogin("test", "password invalid")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Password does not match")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(
            result -> assertInstanceOf(ServiceException.class, result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 아이디 잘못 입력 시 404 Not Found 발생")
  void login_6() throws Exception {
    doLogin("test", "password valid")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Admin not found")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(NoSuchElementException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("로그인: 200 Ok 성공")
  void login_7() throws Exception {
    adminService.createAdmin("test", "secret number");

    doLogin("test", "secret number")
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("login"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Login successful")))
        .andExpect(jsonPath("$.data.adminId").value("test"))
        .andExpect(jsonPath("$.data.accessToken").value(Matchers.notNullValue()))
        .andExpect(jsonPath("$.data.refreshToken", hasLength(36)));
  }

  @Test
  @DisplayName("로그아웃: 204 No Content 성공")
  void logout_1() throws Exception {
    adminService.createAdmin("test", "secret number");

    String loginResponse = doLogin("test", "secret number")
        .andReturn()
        .getResponse()
        .getContentAsString();

    String accessToken = new ObjectMapper()
        .readTree(loginResponse)
        .path("data")
        .path("accessToken")
        .asText();

    ResultActions resultActions = mvc.perform(
        delete("/auth/logout")
            .header("Authorization", "Bearer " + accessToken)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AuthController.class))
        .andExpect(handler().methodName("logout"))
        .andExpect(status().isNoContent())
        .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEmpty());
  }

  private ResultActions doLogin(String adminId, String password) throws Exception {
    return mvc.perform(
        post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {
                    "adminId": "%s",
                    "password": "%s"
                }
                """, adminId, password)
            )
    ).andDo(print());
  }
}