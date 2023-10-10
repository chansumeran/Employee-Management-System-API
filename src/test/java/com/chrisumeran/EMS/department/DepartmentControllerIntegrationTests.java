package com.chrisumeran.EMS.department;

import com.chrisumeran.EMS.TestDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        departmentService.save(testCreateDepartmentA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].deptID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testCreateDepartmentA.getName())
        );
    }

    // Read One Endpoint Integration Tests
    @Test
    public void testThatReadDepartmentSuccessfullyAndReturnHttp200Ok() throws Exception {
        DepartmentEntity testCreateDepartmentA = TestDataUtil.testCreateDepartmentA();
        departmentService.save(testCreateDepartmentA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatReadDepartmentSuccessfullyAndReturnSavedDepartment() throws Exception {
        DepartmentEntity testCreateDepartmentA = TestDataUtil.testCreateDepartmentA();
        departmentService.save(testCreateDepartmentA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.deptID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testCreateDepartmentA.getName())
        );
    }

    // Full Update Integration Tests
    @Test
    public void testThatFullUpdateDepartmentReturnsHttpStatus404WhenNoDepartmentExists() throws  Exception {
        DepartmentDTO departmentDTO = TestDataUtil.testCreateDepartmentDtoA();
        String departmentDtoJson = objectMapper.writeValueAsString(departmentDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/departments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateDepartmentReturnsHttpStatus200WhenDepartmentExists() throws  Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        DepartmentDTO departmentDTO = TestDataUtil.testCreateDepartmentDtoA();
        String departmentDtoJson = objectMapper.writeValueAsString(departmentDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/departments/" + savedDepartmentEntity.getDeptID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdatesExistingAuthor() throws Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        DepartmentEntity departmentDTO = TestDataUtil.testCreateDepartmentB();
        String savedDepartmentDTO = objectMapper.writeValueAsString(departmentDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/departments/" + savedDepartmentEntity.getDeptID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(savedDepartmentDTO)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.deptID").value(savedDepartmentEntity.getDeptID())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(departmentDTO.getName())
        );
    }

    // Partial Update Integration Tests
    @Test
    public void testThatPartialUpdateDepartmentReturnsHttpStatus200() throws Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        DepartmentDTO departmentDTO = TestDataUtil.testCreateDepartmentDtoA();
        departmentDTO.setName("UPDATED");
        String departmentJson = objectMapper.writeValueAsString(departmentDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/departments/" + savedDepartmentEntity.getDeptID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateReturnsUpdatedDepartment() throws Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        DepartmentDTO departmentDTO = TestDataUtil.testCreateDepartmentDtoA();
        departmentDTO.setName("UPDATED");
        String departmentJson = objectMapper.writeValueAsString(departmentDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/departments/" + savedDepartmentEntity.getDeptID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.deptID").value(savedDepartmentEntity.getDeptID())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        );
    }

    // Delete Integration Tests
    @Test
    public void testThatDeleteDepartmentReturnsHttpStatus204WhenNotExistingDepartment() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/departments/1213")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteDepartmentActuallyDeletesDepartment() throws Exception {
        DepartmentEntity departmentEntity = TestDataUtil.testCreateDepartmentA();
        DepartmentEntity savedDepartmentEntity = departmentService.save(departmentEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/departments/" + savedDepartmentEntity.getDeptID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
