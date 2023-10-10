package com.chrisumeran.EMS.employees;

import com.chrisumeran.EMS.TestDataUtil;
import com.chrisumeran.EMS.employee.EmployeeDTO;
import com.chrisumeran.EMS.employee.EmployeeEntity;
import com.chrisumeran.EMS.employee.EmployeeService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTests {

    private EmployeeService employeeService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeControllerIntegrationTests(EmployeeService employeeService, MockMvc mockMvc) {
        this.employeeService = employeeService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    // Create Endpoint Integration Tests
    @Test
    public void testThatCreateEmployeeSuccessfullyAndReturnHttp201Created() throws Exception {
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
    public void testThatCreateEmployeeSuccessfullyAndReturnSavedEmployee() throws Exception {
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

    // Find Many Endpoint Integration Tests
    @Test
    public void testThatFindEmployeesSuccessfullyAndReturnHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindEmployeesSuccessfullyAndReturnSavedEmployee() throws Exception {
        EmployeeEntity testCreateEmployeeA = TestDataUtil.testCreateEmployeeA();
        employeeService.save(testCreateEmployeeA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].empID").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].firstName").value(testCreateEmployeeA.getFirstName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].lastName").value(testCreateEmployeeA.getLastName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value(testCreateEmployeeA.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].salary").value(testCreateEmployeeA.getSalary())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].hireDate").value(testCreateEmployeeA.getHireDate())
        );
    }

    // Fine One Endpoint Integration Tests
    @Test
    public void testThatFindEmployeeSuccessfullyAndReturnHttp200Ok() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        employeeService.save(employeeEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindEmployeeSuccessfullyAndReturnSavedEmployee() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        employeeService.save(employeeEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
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
                MockMvcResultMatchers.jsonPath("$.hireDate").value(employeeEntity.getHireDate())
        );
    }

    // Full Update Endpoint Integration Tests
   @Test
    public void testThatFullUpdateEmployeeReturnsHttpStatus404WhenNoEmployeeExists() throws Exception {
       EmployeeDTO employeeDTO = TestDataUtil.testCreateEmployeeDtoA();
       String savedEmployeeDto = objectMapper.writeValueAsString(employeeDTO);

       mockMvc.perform(
               MockMvcRequestBuilders.put("/employees/99")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(savedEmployeeDto)
       ).andExpect(
               MockMvcResultMatchers.status().isNotFound()
       );
   }

    @Test
    public void testThatFullUpdateEmployeeReturnsHttpStatus200WhenEmployeeExists() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        EmployeeEntity savedEmployeeEntity = employeeService.save(employeeEntity);

        EmployeeDTO employeeDTO = TestDataUtil.testCreateEmployeeDtoA();
        String savedEmployeeDto = objectMapper.writeValueAsString(employeeDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/" + savedEmployeeEntity.getEmpID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(savedEmployeeDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdatesExistingEmployee() throws Exception {
        EmployeeEntity employeeA = TestDataUtil.testCreateEmployeeA();
        EmployeeEntity savedEmployeeA = employeeService.save(employeeA);

        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeB();
        String employeeEntityJson = objectMapper.writeValueAsString(employeeEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/" + savedEmployeeA.getEmpID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeEntityJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.empID").value(savedEmployeeA.getEmpID())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value(employeeEntity.getFirstName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value(employeeEntity.getLastName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(employeeEntity.getEmail())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.salary").value(employeeEntity.getSalary())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hireDate").value(employeeEntity.getHireDate())
        );
    }

    // Partial Update Integration Tests
    @Test
    public void testThatPartialUpdateEmployeeReturnsHttpStatus200() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        EmployeeEntity savedEmployeeEntity = employeeService.save(employeeEntity);

        EmployeeDTO employeeDTO = TestDataUtil.testCreateEmployeeDtoA();
        String employeeJson = objectMapper.writeValueAsString(employeeDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/employees/" + savedEmployeeEntity.getEmpID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateUpdatesExistingEmployee() throws Exception {
        EmployeeEntity employeeEntity = TestDataUtil.testCreateEmployeeA();
        EmployeeEntity savedEmployeeEntity = employeeService.save(employeeEntity);

        EmployeeDTO employeeDTO = TestDataUtil.testCreateEmployeeDtoA();
        employeeDTO.setFirstName("CHRISTIAN");
        String employeeJson = objectMapper.writeValueAsString(employeeDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/employees/" + savedEmployeeEntity.getEmpID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.empID").value(savedEmployeeEntity.getEmpID())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("CHRISTIAN")
        );
    }
}
