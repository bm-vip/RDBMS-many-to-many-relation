package comeon.group.gameloveservice.service.impl;

import com.querydsl.core.types.Predicate;
import comeon.group.gameloveservice.model.BaseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import comeon.group.gameloveservice.entity.BaseEntity;
import comeon.group.gameloveservice.exception.NotFoundException;
import comeon.group.gameloveservice.mapping.BaseMapper;
import comeon.group.gameloveservice.repository.BaseRepository;
import comeon.group.gameloveservice.service.BaseService;

import java.io.Serializable;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<M extends BaseModel<ID>, E extends BaseEntity<ID>, ID extends Serializable> implements BaseService<M, ID> {

    public final BaseRepository<E, ID> repository;
    public final BaseMapper<M, E> mapper;

    public abstract Predicate queryBuilder(M filter);

    @Override
    @Transactional(readOnly = true)
    public Page<M> findAll(M filter, Pageable pageable) {
        return repository.findAll(queryBuilder(filter), pageable).map(mapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAll(M filter) {
        return repository.count(queryBuilder(filter));
    }

    @Override
    @Transactional(readOnly = true)
    public M findById(ID id) {
        E entity = repository.findById(id).orElseThrow(() -> new NotFoundException("id: " + id));
        return mapper.toModel(entity);
    }

    @Override
    public M save(M model) {
        if (Objects.nonNull(model.getId())) {
            E entity = repository.findById(model.getId()).orElseThrow(() -> new NotFoundException(String.format("%s not found by id %s", model.getClass().getName(), model.getId().toString())));
            return mapper.toModel(repository.save(mapper.updateEntity(model, entity)));
        }
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public void deleteById(ID id) {
        E entity = repository.findById(id).orElseThrow(() -> new NotFoundException("id: " + id));
        repository.delete(entity);
    }
}
