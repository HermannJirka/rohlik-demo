package cz.tut.rohlik.rohlikdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tut.rohlik.rohlikdemo.RohlikDemoApplication;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateOrderDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateOrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
                  @Sql(scripts = {"/sql/init/clean-up.sql", "/sql/init/init.sql"},
                       executionPhase = BEFORE_TEST_METHOD),
                  @Sql(scripts = {"/sql/init/clean-up.sql"}, executionPhase = AFTER_TEST_METHOD)
          })
@SpringBootTest(classes = RohlikDemoApplication.class)
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getOrder() throws Exception {
        mvc.perform(get("/order/order2"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("COMPLETED"))
           .andExpect(jsonPath("$.priceSum").value("75.0"))
           .andExpect(jsonPath("$.items[0].itemName").value("apple"))
           .andExpect(jsonPath("$.items[0].itemDescription").value("apple"))
           .andExpect(jsonPath("$.items[0].count").value("2"))
           .andExpect(jsonPath("$.items[0].itemPrice").value("10.0"))
           .andExpect(jsonPath("$.items[0].itemPriceSum").value("20.0"))
           .andExpect(jsonPath("$.items[1].itemName").value("carrot"))
           .andExpect(jsonPath("$.items[1].itemDescription").value("carrot"))
           .andExpect(jsonPath("$.items[1].count").value("4"))
           .andExpect(jsonPath("$.items[1].itemPrice").value("5.0"))
           .andExpect(jsonPath("$.items[1].itemPriceSum").value("20.0"))
           .andExpect(jsonPath("$.items[2].itemName").value("chocolate"))
           .andExpect(jsonPath("$.items[2].itemDescription").value("chocolate"))
           .andExpect(jsonPath("$.items[2].count").value("1"))
           .andExpect(jsonPath("$.items[2].itemPrice").value("35.0"))
           .andExpect(jsonPath("$.items[2].itemPriceSum").value("35.0"));
    }

    @Test
    void createOrder() throws Exception {
        List<CreateOrderDto> createOrders = new ArrayList<>();
        createOrders.add(CreateOrderDto.builder()
                                       .itemCount(3)
                                       .itemId("item1")
                                       .build());
        createOrders.add(CreateOrderDto.builder()
                                       .itemCount(4)
                                       .itemId("item2")
                                       .build());

        mvc.perform(post("/order")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(createOrders))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("NEW"))
           .andExpect(jsonPath("$.priceSum").value("505.0"))
           .andExpect(jsonPath("$.items[0].itemName").value("chocolate"))
           .andExpect(jsonPath("$.items[0].itemDescription").value("chocolate"))
           .andExpect(jsonPath("$.items[0].count").value("3"))
           .andExpect(jsonPath("$.items[0].itemPrice").value("35.0"))
           .andExpect(jsonPath("$.items[0].itemPriceSum").value("105.0"))
           .andExpect(jsonPath("$.items[1].itemName").value("pork meat"))
           .andExpect(jsonPath("$.items[1].itemDescription").value("pork meat"))
           .andExpect(jsonPath("$.items[1].count").value("4"))
           .andExpect(jsonPath("$.items[1].itemPrice").value("100.0"))
           .andExpect(jsonPath("$.items[1].itemPriceSum").value("400.0"));
    }

    @Test
    public void createOrderItemCountException() throws Exception {
        List<CreateOrderDto> createOrders = new ArrayList<>();
        createOrders.add(CreateOrderDto.builder()
                                       .itemCount(11)
                                       .itemId("item1")
                                       .build());

        mvc.perform(post("/order")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(createOrders))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isBadRequest());
    }

    @Test
    public void getOldOrderException() throws Exception {
        mvc.perform(get("/order/order3"))
           .andDo(print())
           .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrderItems() throws Exception {
        List<UpdateOrderDto> updateOrderDtos = new ArrayList<>();
        updateOrderDtos.add(UpdateOrderDto.builder()
                                          .itemId("item3")
                                          .count(2)
                                          .build());
        updateOrderDtos.add(UpdateOrderDto.builder()
                                          .itemId("item4")
                                          .count(3)
                                          .build());
        mvc.perform(put("/order/order1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(updateOrderDtos))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("NEW"))
           .andExpect(jsonPath("$.priceSum").value("35.0"))
           .andExpect(jsonPath("$.items[0].itemName").value("apple"))
           .andExpect(jsonPath("$.items[0].itemDescription").value(
                   "apple"))
           .andExpect(jsonPath("$.items[0].count").value("2"))
           .andExpect(jsonPath("$.items[0].itemPrice").value("10.0"))
           .andExpect(jsonPath("$.items[0].itemPriceSum").value("20.0"))
           .andExpect(jsonPath("$.items[1].itemName").value("carrot"))
           .andExpect(jsonPath("$.items[1].itemDescription").value(
                   "carrot"))
           .andExpect(jsonPath("$.items[1].count").value("3"))
           .andExpect(jsonPath("$.items[1].itemPrice").value("5.0"))
           .andExpect(jsonPath("$.items[1].itemPriceSum").value("15.0"));
    }

    @Test
    void completeOrder() throws Exception {
        mvc.perform(post("/order/order2/complete"))
           .andDo(print())
           .andExpect(status().isOk());

        mvc.perform(get("/order/order2"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void cancelOrder() throws Exception {
        mvc.perform(post("/order/order2/cancel"))
           .andDo(print())
           .andExpect(status().isOk());

        mvc.perform(get("/order/order2"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("CANCELED"));
    }
}