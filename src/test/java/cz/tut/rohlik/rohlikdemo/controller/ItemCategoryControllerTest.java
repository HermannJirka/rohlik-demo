package cz.tut.rohlik.rohlikdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tut.rohlik.rohlikdemo.dto.request.CreateItemCategoryDto;
import cz.tut.rohlik.rohlikdemo.dto.request.UpdateItemCategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
@SpringBootTest
@AutoConfigureMockMvc
class ItemCategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void addItemCategory() throws Exception {
        CreateItemCategoryDto createItemCategoryDto = CreateItemCategoryDto.builder()
                                                                           .name("tools")
                                                                           .build();
        mvc.perform(post("/item-category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(createItemCategoryDto))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("tools"));
    }

    @Test
    void updateItemCategory() throws Exception {
        UpdateItemCategoryDto updateItemCategoryDto = UpdateItemCategoryDto.builder()
                                                                           .name("test")
                                                                           .build();
        mvc.perform(put("/item-category/itemCat5")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(updateItemCategoryDto))
                .accept(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    void deleteItemCategory() throws Exception {
        mvc.perform(delete("/item-category/itemCat5"))
           .andDo(print())
           .andExpect(status().isOk());

        mvc.perform(get("/item-category/itemCat5"))
           .andDo(print())
           .andExpect(status().isNotFound());
    }

    @Test
    void getItemCategory() throws Exception {
        mvc.perform(get("/item-category/itemCat4"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("drugs"));
    }

    @Test
    void getAllItemCategories() throws Exception {
        mvc.perform(get("/item-category"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(5)));
    }
}