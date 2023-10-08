package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.TestDataUtil;
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
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DepartmentControllerIntegrationTests {

    private DepartmentService departmentService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public DepartmentControllerIntegrationTests(DepartmentService departmentService, MockMvc mockMvc) {
        this.departmentService = departmentService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }


    // Create Endpoint Integration Tests
    @Test
    public void testThatCreateDepartmentSuccessfullyAndReturnHttp201Created() throws Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        departmentEntity.setDeptID(null);
        String departmentJson = objectMapper.writeValueAsString(departmentEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateDepartmentSuccessfullyAndReturnSavedDepartment() throws Exception{
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        departmentEntity.setDeptID(null);
        String departmentJson = objectMapper.writeValueAsString(departmentEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.deptID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(departmentEntity.getName())
        );
    }

    // Read Many Endpoint Integration Tests
    @Test
    public void testThatReadDepartmentsSuccessfullyAndReturnHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatReadDepartmentsSuccessfullyAndReturnSavedDepartment() throws Exception {
        DepartmentEntity testCreateDepartmentA = TestDataUtil.testCreateDepartmentA();
        departmentService.createDepartment(testCreateDepartmentA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].deptID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testCreateDepartmentA.getName())
        );
    }
}
