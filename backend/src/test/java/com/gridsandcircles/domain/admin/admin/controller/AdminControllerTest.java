package com.gridsandcircles.domain.admin.admin.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gridsandcircles.domain.admin.admin.entity.Admin;
import com.gridsandcircles.domain.admin.admin.service.AdminService;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.global.ServiceException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AdminControllerTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private AdminService adminService;

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("회원가입: 관리자 ID 길이 4 미만 시 400 Bad Request 발생")
  void signUp_1() throws Exception {
    ResultActions resultActions = mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                 "adminId": "tes",
                 "inputPassword": "1234pw1234",
                 "confirmPassword": "1234pw1234"
                }
                """)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("adminId")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 관리자 ID 길이 10 초과 시 400 Bad Request 발생")
  void signUp_2() throws Exception {
    ResultActions resultActions = mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                 "adminId": "test1234567",
                 "inputPassword": "1234pw1234",
                 "confirmPassword": "1234pw1234"
                }
                """)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("adminId")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 관리자 비밀번호 길이 10 미만 시 400 Bad Request 발생")
  void signUp_3() throws Exception {
    ResultActions resultActions = mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                 "adminId": "test",
                 "inputPassword": "1234pw123",
                 "confirmPassword": "1234pw123"
                }
                """)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("inputPassword")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("confirmPassword")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 관리자 비밀번호 길이 20 초과 시 400 Bad Request 발생")
  void signUp_4() throws Exception {
    ResultActions resultActions = mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                 "adminId": "test",
                 "inputPassword": "this password is more than 20 length",
                 "confirmPassword": "this password is more than 20 length"
                }
                """)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("inputPassword")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("confirmPassword")))
        .andExpect(jsonPath("$.msg").value(Matchers.containsString("Size")))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
            result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 비밀번호와 비밀번호 확인 불일치 시 400 Bad Request 발생")
  void signUp_5() throws Exception {
    ResultActions resultActions = mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                 "adminId": "test",
                 "inputPassword": "this password",
                 "confirmPassword": "this passwords"
                }
                """)
    ).andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.msg").value("Password does not match"))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(
            result -> assertInstanceOf(ServiceException.class, result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 가입하려는 아이디 이미 존재 시 409 Conflict 발생")
  void signUp_6() throws Exception {
    doSignup("test", "pwd1234pwd");

    ResultActions resultActions = doSignup("test", "secret number");

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.msg").value("Admin id test already exists"))
        .andExpect(jsonPath("$.data").value(Matchers.nullValue()))
        .andExpect(
            result -> assertInstanceOf(ServiceException.class, result.getResolvedException()));
  }

  @Test
  @DisplayName("회원가입: 201 Created 성공")
  void signUp_7() throws Exception {
    ResultActions resultActions = doSignup("test", "secret number");

    Admin admin = adminService.getAdmin("test");

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("signup"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.msg").value("Sign up successful"))
        .andExpect(jsonPath("$.data.adminId").value(admin.getAdminId()));
  }

  @Test
  @DisplayName("모든 주문 조회")
  @WithMockUser(username = "admin")
  void getOrders() throws Exception {
    ResultActions resultActions = mvc
        .perform(
            get("/admin/orders")
        )
        .andDo(print());

    List<Order> orders = orderService.getOrders();

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("getOrders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.msg").value("Get all orders successful"))
        .andExpect(jsonPath("$.data.length()").value(orders.size()));

    for (int i = 0; i < orders.size(); i++) {
      Order order = orders.get(i);
      resultActions
          .andExpect(jsonPath("$.data[%d].orderId".formatted(i)).value(order.getOrderId()))
          .andExpect(jsonPath("$.data[%d].email".formatted(i)).value(order.getEmail()))
          .andExpect(jsonPath("$.data[%d].address".formatted(i)).value(order.getAddress()))
          .andExpect(jsonPath("$.data[%d].createdAt".formatted(i)).value(
              Matchers.startsWith(order.getCreatedAt().toString().substring(0, 10))))
          .andExpect(jsonPath("$.data[%d].orderStatus".formatted(i)).value(order.isOrderStatus()))
          .andExpect(
              jsonPath("$.data[%d].deliveryStatus".formatted(i)).value(order.isDeliveryStatus()));

      for (int j = 0; j < order.getOrderItems().size(); j++) {
        resultActions
            .andExpect(jsonPath("$.data[%d].orderItems[%d].orderItemId".formatted(i, j)).value(
                order.getOrderItems().get(j).getOrderItemId()))
            .andExpect(jsonPath("$.data[%d].orderItems[%d].productName".formatted(i, j)).value(
                order.getOrderItems().get(j).getProduct().getName()))
            .andExpect(jsonPath("$.data[%d].orderItems[%d].orderCount".formatted(i, j)).value(
                order.getOrderItems().get(j).getOrderCount()))
            .andExpect(jsonPath("$.data[%d].orderItems[%d].productPrice".formatted(i, j)).value(
                order.getOrderItems().get(j).getProduct().getPrice()));
      }
    }
  }

  @Test
  @DisplayName("주문 취소, by order")
  @WithMockUser(username = "admin")
  void cancelOrder() throws Exception {
    int id = 1;

    ResultActions resultActions = mvc
        .perform(
            patch("/admin/orders/cancel/" + id)
        )
        .andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("cancelOrder"))
        .andExpect(jsonPath("$.msg").value("Cancel order successful"))
        .andExpect(status().isOk());

    Order order = orderService.getOrder(id);
    assertThat(order.isOrderStatus()).isFalse(); // orderStatus false 값 검증
  }

  @Test
  @DisplayName("주문 취소, by orderItem")
  @WithMockUser(username = "admin")
  void cancelOrderDetail() throws Exception {
    int orderId = 1;
    int id = 1;

    ResultActions resultActions = mvc
        .perform(
            patch("/admin/orders/cancel/%d/%d".formatted(orderId, id))
        )
        .andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("cancelOrderDetail"))
        .andExpect(jsonPath("$.msg").value("Cancel orderItem successful"))
        .andExpect(status().isOk());

    Order order = orderService.getOrder(orderId);
    OrderItem orderItem = order.findItemById(id).get();
    assertThat(orderItem.isOrderItemStatus()).isFalse(); // orderItemStatus false 값 검증
  }

  private ResultActions doSignup(String adminId, String password) throws Exception {
    return mvc.perform(
        post("/admin/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {
                    "adminId": "%s",
                    "inputPassword": "%s",
                    "confirmPassword": "%s"
                }
                """, adminId, password, password)
            )
    ).andDo(print());
  }
}