package org.fastcampus.admin.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fastcampus.admin.ui.dto.GetDailyRegisterUserResponseDto;
import org.fastcampus.admin.ui.query.UserStatsQueryRepository;
import org.fastcampus.common.TimeCalculator;
import org.fastcampus.user.repository.entity.QUserEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStatsQueryRepositoryImpl implements UserStatsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private static final QUserEntity userEntity = QUserEntity.userEntity;

    @Override
    public List<GetDailyRegisterUserResponseDto> getDailyRegisterUserStats(int beforeDays) {
        return jpaQueryFactory
            .select(
                Projections.fields(
                    GetDailyRegisterUserResponseDto.class,
                    userEntity.regDate.as("date"),
                    userEntity.count().as("count")
                )
            )
            .from(userEntity)
            .where(userEntity.regDate.after(TimeCalculator.getDateDaysAgo(beforeDays)))
            .groupBy(userEntity.regDate)
            .orderBy(userEntity.regDate.asc())
            .fetch();
    }
}
