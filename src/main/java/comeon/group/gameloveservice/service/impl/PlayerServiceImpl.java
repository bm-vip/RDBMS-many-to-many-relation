package comeon.group.gameloveservice.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import comeon.group.gameloveservice.entity.PlayerEntity;
import comeon.group.gameloveservice.entity.QPlayerEntity;
import comeon.group.gameloveservice.mapping.PlayerMapper;
import comeon.group.gameloveservice.model.PlayerModel;
import comeon.group.gameloveservice.repository.PlayerRepository;
import comeon.group.gameloveservice.service.PlayerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static comeon.group.gameloveservice.util.MapperHelper.option;

@Service
public class PlayerServiceImpl extends BaseServiceImpl<PlayerModel, PlayerEntity, Long> implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        super(playerRepository, playerMapper);
        this.playerRepository = playerRepository;
    }

    @Override
    public JpaRepository<PlayerEntity, Long> getRepository() {
        return playerRepository;
    }

    @Override
    public Predicate queryBuilder(PlayerModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QPlayerEntity path = QPlayerEntity.playerEntity;

        option(() -> filter.getId())
                .ifPresent(id -> builder.and(path.id.eq(id)));
        option(() -> filter.getBirthDate())
                .ifPresent(birthDate -> builder.and(path.birthDate.eq(birthDate)));

        if (StringUtils.hasText(filter.getFirstName()))
            builder.and(path.firstName.contains(filter.getFirstName()));

        if (StringUtils.hasText(filter.getLastName()))
            builder.and(path.lastName.contains(filter.getLastName()));

        if (StringUtils.hasText(filter.getEmail()))
            builder.and(path.email.contains(filter.getEmail()));

        return builder;
    }
}
