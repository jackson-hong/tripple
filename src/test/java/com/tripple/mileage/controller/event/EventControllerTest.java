package com.tripple.mileage.controller.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripple.mileage.common.type.event.EventType;
import com.tripple.mileage.controller.param.EventRequestParam;
import com.tripple.mileage.manager.EventManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.tripple.mileage.common.type.event.EventActionType.ADD;
import static com.tripple.mileage.common.type.event.EventType.REVIEW;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventManager eventManager;

    @Test
    @DisplayName("리퀘스트에 유효한 파라미터가 들어오는 확인")
    void testParam() throws Exception {
        // GIVEN
        EventRequestParam param = EventRequestParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // WHEN & THEN
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("userId가 없는 경우 test")
    void testParamWithoutUserId() throws Exception {
        // GIVEN
        EventRequestParam param = EventRequestParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // WHEN & THEN
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}