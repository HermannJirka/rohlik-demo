package cz.tut.rohlik.rohlikdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tut.rohlik.rohlikdemo.dto.ItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
                  @Sql(scripts = {"/sql/init/clean-up.sql", "/sql/init/init.sql"},
                       executionPhase = BEFORE_TEST_METHOD),
                  @Sql(scripts = {"/sql/init/clean-up.sql"}, executionPhase = AFTER_TEST_METHOD)
          })
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void addItem() throws Exception {
        CreateItemDto itemDto = new CreateItemDto();
        itemDto.setCount(2);
        itemDto.setPrice(BigDecimal.valueOf(35));
        itemDto.setDeleted(false);
        itemDto.setDescription("White chocolate");
        itemDto.setName("chocolate");
        itemDto.setItemCategory("itemCat5");
        mvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(itemDto))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    public void updateItem() throws Exception {
        CreateItemDto itemDto = new CreateItemDto();
        itemDto.setCount(2);
        itemDto.setPrice(BigDecimal.valueOf(35));
        itemDto.setDeleted(false);
        itemDto.setDescription("White chocolate");
        itemDto.setName("chocolate");
        itemDto.setItemCategory("itemCat5");
        String savedItemDto = mvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(itemDto))
                .accept(MediaType.APPLICATION_JSON))
                                 .andDo(print())
                                 .andExpect(status().isOk())
                                 .andReturn().getResponse().getContentAsString();

        String id = mapper.readValue(savedItemDto, ItemDto.class).getId();

        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                                                   .count(3)
                                                   .name("white chocolate")
                                                   .build();

        mvc.perform(put("/item/"+id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(updateItemDto))
                .accept(MediaType.APPLICATION_JSON))
                                   .andDo(print())
                                   .andExpect(status().isOk())
                                   .andReturn().getResponse().getContentAsString();

    }
}