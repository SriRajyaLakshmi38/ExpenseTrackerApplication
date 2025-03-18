package com.expensetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseTrackerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddExpense() throws Exception {
        String expenseJson = "{\"name\":\"Groceries\",\"amount\":50.0,\"date\":\"2025-03-18\"}";

        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Groceries"))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.date").value("2025-03-18"));
    }

    @Test
    void testGetExpenses() throws Exception {
        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteExpense() throws Exception {
        // First, add an expense
        String expenseJson = "{\"name\":\"Dinner\",\"amount\":30.0,\"date\":\"2025-03-18\"}";
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expenseJson))
                .andExpect(status().isOk());

        // Delete the first expense (ID = 1)
        mockMvc.perform(delete("/expenses/1"))
                .andExpect(status().isOk());
    }
}

