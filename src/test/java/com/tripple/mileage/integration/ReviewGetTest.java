package com.tripple.mileage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tripple.mileage.controller.ResponseData;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.controller.payload.PointHistoryListPayload;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.place.PlaceRepository;
import com.tripple.mileage.domain.point.PointHistoryRepository;
import com.tripple.mileage.domain.review.ReviewRepository;
import com.tripple.mileage.domain.user.User;
import com.tripple.mileage.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.tripple.mileage.common.type.event.EventActionType.ADD;
import static com.tripple.mileage.common.type.event.EventType.REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewGetTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    private UUID reviewId = UUID.randomUUID();
    private UUID userId = UUID.randomUUID();
    private UUID placeId = UUID.randomUUID();
    private List<UUID> attachedPhotoIds = Arrays.asList(UUID.randomUUID(),
            UUID.randomUUID());

    @BeforeEach
    void setUp() throws Exception {
        User user = User.builder()
                .userId(userId)
                .build();

        Place place = Place.builder()
                .placeId(placeId)
                .build();

        userRepository.save(user);
        placeRepository.save(place);

        EventPointParam param = EventPointParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId(reviewId)
                .content("좋아요!")
                .userId(userId)
                .placeId(placeId)
                .attachedPhotoIds(attachedPhotoIds)
                .build();

        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Point History 조회 테스트")
    void testGetPointHistory() throws Exception {
        // GIVEN
        String userId = this.userId.toString();

        // WHEN
        MvcResult result = mockMvc.perform(get("/users/"+userId+"/points")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        ResponseData<PointHistoryListPayload> payload = convert(result, ResponseData.class);
        PointHistoryListPayload finalResult = convert(payload, PointHistoryListPayload.class);
        // THEN
        assertThat(finalResult.getTotalPointAmount()).isEqualTo(3);
        assertThat(finalResult.getUserId().toString()).isEqualTo(userId);
        assertThat(finalResult.getPointHistoryList().size()).isEqualTo(3);
    }

    public <T> T convert(ResponseData result, Class<T> clazz) {
        return new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(result.getResultData(), clazz);
    }

    public <T> T convert(MvcResult result, Class<T> clazz) throws Exception {
        return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(result.getResponse().getContentAsString(), clazz);
    }


}
