package com.gridsandcircles.domain.admin.admin.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
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
    ResultActions resultActions = mvc
        .perform(
            patch("/admin/orders/cancel")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                             "email" : "order1@example.com"
                            }
                            """)
        )
        .andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("cancelOrder"))
        .andExpect(jsonPath("$.msg").value("Cancel order successful"))
        .andExpect(status().isOk());

    String email = "order1@example.com";
    List<Order> orders = orderService.getOrdersByEmail(email);
    Order order = orders.get(0);
    assertThat(order.isOrderStatus()).isFalse(); // orderStatus false 값 검증
  }

  @Test
  @DisplayName("상품 취소, by orderItem")
  @WithMockUser(username = "admin")
  void cancelOrderDetail() throws Exception {
    int orderId = 1;
    int id = 1;

    ResultActions resultActions = mvc
        .perform(
            patch("/admin/orders/cancel-detail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                             "email" : "order1@example.com",
                             "productId" : 1
                            }
                            """)
        )
        .andDo(print());

    resultActions
        .andExpect(handler().handlerType(AdminController.class))
        .andExpect(handler().methodName("cancelOrderDetail"))
        .andExpect(jsonPath("$.msg").value("Cancel product successful"))
        .andExpect(status().isOk());

    Order order = orderService.getOrder(orderId);
    OrderItem orderItem = order.findItemById(id).get();
    assertThat(orderItem.isOrderItemStatus()).isFalse(); // orderItemStatus false 값 검증
  }
}