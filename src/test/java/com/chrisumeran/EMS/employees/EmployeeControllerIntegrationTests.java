package com.chrisumeran.EMS.employees;

import com.chrisumeran.EMS.TestDataUtil;
import com.chrisumeran.EMS.employee.EmployeeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testThatCreateEmployeeSuccessfullyAndReturnHttp201Created() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        employeeEntity.setEmpID(null);
        String employeeJson = objectMapper.writeValueAsString(employeeEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void testThatCreateEmployeeSuccessfullyAndReturnSavedEmployee() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        employeeEntity.setEmpID(null);
        String employeeJson = objectMapper.writeValueAsString(employeeEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.empID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value(employeeEntity.getFirstName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value(employeeEntity.getLastName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(employeeEntity.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.salary").value(employeeEntity.getSalary())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.department").value(employeeEntity.getDepartment())
        );
    }
}