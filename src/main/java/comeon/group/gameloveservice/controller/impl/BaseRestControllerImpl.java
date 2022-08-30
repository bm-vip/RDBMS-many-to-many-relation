package comeon.group.gameloveservice.controller.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import comeon.group.gameloveservice.controller.BaseRestController;
import comeon.group.gameloveservice.exception.BadRequestException;
import comeon.group.gameloveservice.model.BaseModel;
import comeon.group.gameloveservice.service.BaseService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
public abstract class BaseRestControllerImpl<M extends BaseModel<ID>, ID extends Serializable> implements BaseRestController<M, ID> {
    protected BaseService<M, ID> service;
    protected Class<M> clazz;
    protected ObjectMapper objectMapper;

    public BaseRestControllerImpl(BaseService<M, ID> service, Class<M> clazz) {
        this.clazz = clazz;
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ResponseEntity<M> findById(ID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Page<M>> findAll(Optional<String> json, Pageable pageable) {
        M model = objectMapper.readValue(json.orElse("{}"), clazz);
        return ResponseEntity.ok(service.findAll(model, pageable));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Long> countAll(Optional<String> json) {
        M model = objectMapper.readValue(json.orElse("{}"), clazz);
        return ResponseEntity.ok(service.countAll(model));
    }

    @Override
    public ResponseEntity<Void> deleteById(ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<M> save(M model) {
        if (model.getId() != null) {
            throw new BadRequestException("The id must not be provided when creating a new record");
        }
        return ResponseEntity.ok(service.save(model));
    }

    @Override
    public ResponseEntity<M> update(M model) {
        return ResponseEntity.ok(service.save(model));
    }

}
