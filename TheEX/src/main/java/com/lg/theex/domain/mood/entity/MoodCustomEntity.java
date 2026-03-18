package com.lg.theex.domain.mood.entity;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mood_custom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoodCustomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mood_id", nullable = false)
    private Long moodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UsersInfoEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colorset_id")
    private MoodColorsetEntity colorsetId;

    @Column(name = "mood_name", nullable = false)
    private String moodName;

    @Column(name = "mood_memo")
    private String moodMemo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "custom_product", columnDefinition = "JSON")
    private String customProduct;

    @Column(name = "is_shared", nullable = false)
    private Boolean isShared = false;

    @Column(name = "save_count", nullable = false)
    private Integer saveCount = 0;

    @Builder
    public MoodCustomEntity(Long moodId, UsersInfoEntity userId, MoodColorsetEntity colorsetId,
                            String moodName, String moodMemo, String customProduct,
                            Boolean isShared, Integer saveCount) {
        this.moodId = moodId;
        this.userId = userId;
        this.colorsetId = colorsetId;
        this.moodName = moodName;
        this.moodMemo = moodMemo;
        this.customProduct = customProduct;
        this.isShared = isShared;
        this.saveCount = saveCount;
    }
}