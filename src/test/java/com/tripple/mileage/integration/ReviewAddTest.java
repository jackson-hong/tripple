package com.tripple.mileage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.place.PlaceRepository;
import com.tripple.mileage.domain.point.PointHistory;
import com.tripple.mileage.domain.point.PointHistoryRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.tripple.mileage.common.type.event.EventActionType.ADD;
import static com.tripple.mileage.common.type.event.EventType.REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewAddTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @BeforeEach
    void setUp(){
        User user = User.builder()
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .build();

        Place place = Place.builder()
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .build();

        userRepository.save(user);
        placeRepository.save(place);
    }

    @Test
    @DisplayName("사진 X 리뷰 O 최초 리뷰 O 테스트")
    @Transactional
    void testReviewAddWithoutPhotos() throws Exception {
        // GIVEN
        EventPointParam param = EventPointParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId(UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"))
                .content("좋아요!")
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .build();

        // WHEN
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findById(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745")).get();
        List<PointHistory> pointHistories = pointHistoryRepository.findAllByUser(user);

        // THEN
        assertThat(user.getTotalPoint()).isEqualTo(2);
        assertThat(pointHistories.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사진 O 리뷰 O 최초 리뷰 O 테스트")
    @Transactional
    void testReviewAddWithPhotos() throws Exception {
        // GIVEN
        EventPointParam param = EventPointParam.builder()
                .type(REVIEW)
                .action(ADD)
                .reviewId(UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"))
                .content("좋아요!")
                .userId(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .placeId(UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .attachedPhotoIds(Arrays.asList(UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
                        (UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332"))))
                .build();

        // WHEN
        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findById(UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745")).get();
        List<PointHistory> pointHistories = pointHistoryRepository.findAllByUser(user);

        // THEN
        assertThat(user.getTotalPoint()).isEqualTo(3);
        assertThat(pointHistories.size()).isEqualTo(3);
    }
}
