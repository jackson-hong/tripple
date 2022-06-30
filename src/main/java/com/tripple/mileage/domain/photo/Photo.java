package com.tripple.mileage.domain.photo;

import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.BaseEntity;
import com.tripple.mileage.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "photo_mst")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Photo extends BaseEntity {

    @Id
    private String photoId;

    @Column
    @Enumerated(EnumType.STRING)
    private YnType useYn = YnType.Y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;
}
