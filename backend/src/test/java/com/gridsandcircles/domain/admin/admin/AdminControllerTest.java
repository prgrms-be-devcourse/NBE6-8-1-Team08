package com.gridsandcircles.domain.admin.admin;

import com.gridsandcircles.domain.order.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import com.gridsandcircles.domain.order.order.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("모든 주문 조회")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void orderFindAll() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/admin/orders")
                )
                .andDo(print());

        List<Order> orders = orderService.findAll();

        resultActions
                .andExpect(handler().handlerType(AdminController.class))
                .andExpect(handler().methodName("getOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orders.size()));
    }
}
