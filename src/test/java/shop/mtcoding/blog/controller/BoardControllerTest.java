package shop.mtcoding.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.blog.board.BoardRequest;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BoardControllerTest {
    private final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    public void createBoard_test() throws Exception {
        // given
        BoardRequest.CreateDTO reqDTO = new BoardRequest.CreateDTO();
        reqDTO.setTitle("New Board");
        reqDTO.setContent("This is a new board content.");

        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.post("/boards")
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isCreated());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(201));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Board created successfully"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("New Board"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("This is a new board content."));
    }

    @Test
    public void getBoard_test() throws Exception {
        // given
        Long boardId = 1L;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.get("/boards/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Board retrieved successfully"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(boardId));
    }

    @Test
    public void updateBoard_test() throws Exception {
        // given
        Long boardId = 1L;
        BoardRequest.UpdateDTO reqDTO = new BoardRequest.UpdateDTO();
        reqDTO.setTitle("Updated Board");
        reqDTO.setContent("This is updated board content.");

        String reqBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.put("/boards/" + boardId)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Board updated successfully"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("Updated Board"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("This is updated board content."));
    }

    @Test
    public void deleteBoard_test() throws Exception {
        // given
        Long boardId = 1L;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.delete("/boards/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isNoContent());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(204));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Board deleted successfully"));
    }
}
