package dynamic_top_k.controller;

import cn.hutool.json.JSONUtil;
import dynamic_top_k.common.ResultEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DynamicRankControllerTest {

    private final String BASE_URL = "/api/v1";
    @Resource
    private MockMvc mockMvc;
    @Resource
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateNoArgs() throws Exception {
        String url = BASE_URL + "/update";
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    void updateNoTarget() throws Exception {
        String url = BASE_URL + "/update";
        Map<String, Object> requestBody = new HashMap<>();
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("Cannot find the target."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void updateNullTarget() throws Exception {
        String url = BASE_URL + "/update";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("target", null);
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The target is null."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void updateTargetWrongType() throws Exception {
        String url = BASE_URL + "/update";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("target", 1);
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The target is not a string."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void updateContainsOtherArgs() throws Exception {
        String url = BASE_URL + "/update";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("target", "test");
        requestBody.put("target2", "windows");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value
                        ("The JSON object contains invalid number of keys."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void updateTarget() throws Exception {
        String url = BASE_URL + "/update";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("target", "test");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryNoArgs() throws Exception {
        String url = BASE_URL + "/query";
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void queryNoTarget() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("Cannot find the target."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryNullTarget() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", null);
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The target is null."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryTargetWrongType() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", 1);
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The target is not a string."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryContainsOtherArgs() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "17");
        requestBody.put("target", "test");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The JSON object contains invalid number of keys."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryIllFormedRank() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "7a");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The rank number is ill-formated."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryNegativeRank() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "-1");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.msg").value("The rank to query cannot be less than 0."))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryZeroRank() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "0");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryRankOutOfRange() throws Exception {
        String url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "100");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    void queryRank() throws Exception {
        // Update
        String url = BASE_URL + "/update";
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "word");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "word");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "new");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        // Query
        url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "2");
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void queryRank2() throws Exception {
        String url = BASE_URL + "/update";
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "word");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "new");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        // Query
        url = BASE_URL + "/query";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("top", "2");
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }
    @Test
    void repeatedQuery() throws Exception {
        // Update
        String url = BASE_URL + "/update";
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("target", "word");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        // Query
        url = BASE_URL + "/query";
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("top", "1");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
        // Query
        request = MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(new HashMap<String, Object>(){{put("top", "1");}}))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mvcResult = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultEnum.SUCCESS.getCode()))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.print(JSONUtil.toJsonPrettyStr(mvcResult.getResponse().getContentAsString()));
    }

}