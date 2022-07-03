package com.tripple.mileage.domain.review;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.controller.param.EventPointParam;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.photo.Photo;
import com.tripple.mileage.domain.place.Place;
import com.tripple.mileage.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "review_mst")
@Getter
@Builder
@NoArgsConstructor
@Where(clause = "use_yn = 'Y'")
public class Review extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID reviewId;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn = YnType.Y;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "placeId")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    public Review(UUID reviewId, String content, YnType useYn, Place place, User user, List<Photo> photos) {
        this.reviewId = reviewId;
        this.content = content;
        this.useYn = YnType.Y;
        this.place = place;
        this.user = user;
        this.photos = new ArrayList<>();
    }

    public static Review of(EventPointParam param, Place place, User user){
        return Review.builder()
                .reviewId(param.getReviewId())
                .content(param.getContent())
                .place(place)
                .user(user)
                .useYn(YnType.Y)
                .build();
    }

    public void addPhotos(List<Photo> photos){
        this.photos.addAll(photos);
    }

    public void replacePhotos(List<Photo> photos){
        this.photos = photos;
    }

    public void replaceContent(String content){
        this.content = content;
    }

    public void delete(){
        this.useYn = YnType.N;
    }

}
