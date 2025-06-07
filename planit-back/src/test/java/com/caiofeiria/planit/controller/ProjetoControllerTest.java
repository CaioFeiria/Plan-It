package com.caiofeiria.planit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.caiofeiria.planit.dtos.projeto.ProjetoRequestDTO;
import com.caiofeiria.planit.dtos.projeto.ProjetoResponseDTO;
import com.caiofeiria.planit.mappers.ProjetoMapper;
import com.caiofeiria.planit.models.Projeto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ProjetoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void T001_getAllNoContent() throws Exception {
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/projetos")
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);
		
		int code = resultJSON.get("code").asInt();
		
		Assert.assertEquals(204, code);
	}
	
	@Test
	public void T002_getByNameNoContent() throws Exception {
		
		String queryString = "Nome test";
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/projetos-buscar?nome=" + queryString)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);
		
		int code = resultJSON.get("code").asInt();
		
		Assert.assertEquals(204, code);
	}
	
	@Test
	public void T003_save() throws Exception {
		Projeto proj = new Projeto();
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(projRequest)))
				.andReturn();
		
		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);
		
		Assert.assertEquals("Nome test", resultJSON.get("nome").asText());
	}
	
	@Test
	public void T004_saveErroNome() throws Exception {
		Projeto proj = new Projeto();
	    proj.setNome("");                   
	    proj.setDescricao("Descricao test");

	    ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);

	    MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(projRequest)))
				.andReturn();

	  
	    String jsonString = result.getResponse().getContentAsString();
	    JsonNode resultJSON = getExecutResult(jsonString);
	    
	    String status = resultJSON.get("status").asText();
	    Assert.assertEquals("BAD_REQUEST", status);
	}
	
	@Test
	public void T005_saveErroDescricao() throws Exception {
		Projeto proj = new Projeto();
		proj.setNome("Nome test");                   
		proj.setDescricao("");

		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);

		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

	  
	    String jsonString = result.getResponse().getContentAsString();
	    JsonNode resultJSON = getExecutResult(jsonString);

	    String status = resultJSON.get("status").asText();
	    Assert.assertEquals("BAD_REQUEST", status);
	}
	
	
	@Test
	public void T006_getById() throws Exception {
		
		Projeto proj = new Projeto();
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);
		
		MvcResult resultSave = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonStringSave = resultSave.getResponse().getContentAsString();
		JsonNode resultJSONSave = getExecutResult(jsonStringSave);
		
		Integer id = resultJSONSave.get("id").asInt();
		
		MvcResult resultGet = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/projetos/{id}", + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String jsonStringGet = resultGet.getResponse().getContentAsString();
		JsonNode resultJSONGet = getExecutResult(jsonStringGet);

		Assert.assertEquals(true, resultJSONGet.get("nome").toString().contains("Nome test"));
	}
	
	@Test
	public void T007_getByIdErro() throws Exception {
		Integer id = 34585;
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/projetos/{id}", + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);

		Long erro = resultJSON.get("code").asLong();
	    Assert.assertNotEquals(true, erro.equals(200));
	}
	
	@Test
	public void T008_update() throws Exception {
		Projeto proj = new Projeto();
		proj.setNome("Nome testtttt");
		proj.setDescricao("Descricao testtt");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);
		
		MvcResult resultSave = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonStringSave = resultSave.getResponse().getContentAsString();
		JsonNode resultJSONSave = getExecutResult(jsonStringSave);
		
		Long id = resultJSONSave.get("id").asLong();
		
		Projeto projUp = new Projeto();
		projUp.setId(id);
		projUp.setNome("Nome test");
		projUp.setDescricao("Descricao test");
		
		ProjetoResponseDTO projResponse = ProjetoMapper.toResponseDTO(projUp);
		
		String contentUp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projResponse);
		
		MvcResult resultUpdate = mockMvc.perform(
				MockMvcRequestBuilders.put("/api/projetos/{id}", + id).content(contentUp)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String jsonStringUpdate = resultUpdate.getResponse().getContentAsString();
		JsonNode resultJSONUpdate = getExecutResult(jsonStringUpdate);

		Assert.assertTrue(resultJSONUpdate.get("nome").asText().contains("Nome test"));
	}
	
	@Test
	public void T009_updateErroId() throws Exception {
		Integer id = 534534;
		Long idLong = Long.MAX_VALUE;
		
		Projeto proj = new Projeto();
		proj.setId(idLong);
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoResponseDTO projResponse = ProjetoMapper.toResponseDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projResponse);
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put("/api/projetos/{id}", + id).content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);
		Integer code = resultJSON.get("code").asInt();

		Assert.assertNotEquals(true, code.equals(200));
	}
	
	
	@Test
	public void T010_delete() throws Exception {
		
		Projeto proj = new Projeto();
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSONSave = getExecutResult(jsonString);
		
		Integer id = resultJSONSave.get("id").asInt();
		
		MvcResult resultDelete = mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/projetos/{id}", + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Integer codeStatus = resultDelete.getResponse().getStatus();

		Assert.assertEquals(true, codeStatus.equals(204));
		
	}
	
	@Test
	public void T011_deleteErroId() throws Exception {
		Integer id = 534534;
		
		MvcResult resultDelete = mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/projetos/{id}", + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		Integer codeStatus = resultDelete.getResponse().getStatus();

		Assert.assertNotEquals(true, codeStatus.equals(204));
	}
	
	@Test
	public void T012_getAll() throws Exception {
		
		Projeto proj = new Projeto();
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/projetos")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void T013_getByName() throws Exception {
		
		Projeto proj = new Projeto();
		proj.setNome("Nome test");
		proj.setDescricao("Descricao test");
		
		ProjetoRequestDTO projRequest = ProjetoMapper.toRequestDTO(proj);
		
		String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projRequest);
		
		MvcResult resultSave = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/projetos").content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonStringSave = resultSave.getResponse().getContentAsString();
		JsonNode resultJSONSave = getExecutResult(jsonStringSave);
		
		String queryString = resultJSONSave.get("nome").asText();
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/projetos-buscar?nome=" + queryString)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String jsonString = result.getResponse().getContentAsString();
		JsonNode resultJSON = getExecutResult(jsonString);
		
		String nome = resultJSON.get(0).get("nome").asText();
		
		Assert.assertEquals(true, nome.equals("Nome test"));
		
	}

	
	protected JsonNode getExecutResult(String jsonString) throws JsonProcessingException, IOException {
	    JsonNode resultEmbKYC = mapper.readTree(jsonString);	
	    
	    return resultEmbKYC;
	}
}
