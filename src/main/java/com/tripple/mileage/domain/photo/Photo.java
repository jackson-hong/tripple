package com.tripple.mileage.domain.photo;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "photo_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Photo extends BaseEntity {

    @Id
    @Type(type = "uuid-char")
    private UUID photoId;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn = YnType.Y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    public static Photo of(UUID photoId, Review review){
        return Photo.builder()
                .review(review)
                .photoId(photoId)
                .useYn(YnType.Y)
                .build();
    }

    public static List<Photo> makePhotoList(List<UUID> photoIds, Review review){
        if(ObjectUtils.isEmpty(photoIds) || photoIds.size() == 0) return new ArrayList<>();
        return photoIds.stream().map(photoId -> of(photoId, review)).collect(Collectors.toList());
    }
}
