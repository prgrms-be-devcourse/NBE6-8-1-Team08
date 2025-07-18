package com.gridsandcircles.domain.admin.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gridsandcircles.domain.admin.admin.controller.AdminController;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AdminControllerTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private MockMvc mvc;

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
        .andExpect(jsonPath("$.length()").value(orders.size()));
  }

  @Test
  @DisplayName("주문 삭제, by order")
  @WithMockUser(username = "admin")
  void deleteOrder() throws Exception {
    int id = 1;

    ResultActions resultActions = mvc
            .perform(
                    delete("/admin/orders/" + id)
            )
            .andDo(print());

    resultActions
            .andExpect(handler().handlerType(AdminController.class))
            .andExpect(handler().methodName("deleteOrder"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("주문 삭제, by product")
  @WithMockUser(username = "admin")
  void deleteOrderDetail() throws Exception {
    int orderId = 1;
    int id = 1;

    ResultActions resultActions = mvc
            .perform(
                    delete("/admin/orders/%d/%d".formatted(orderId, id))
            )
            .andDo(print());

    resultActions
            .andExpect(handler().handlerType(AdminController.class))
            .andExpect(handler().methodName("deleteOrderDetail"))
            .andExpect(status().isOk());
  }
}