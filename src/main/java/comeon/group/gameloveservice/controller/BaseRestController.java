package comeon.group.gameloveservice.controller;

import comeon.group.gameloveservice.validation.Save;
import comeon.group.gameloveservice.validation.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
public interface BaseRestController<M, ID extends Serializable> {

    @GetMapping(value = {"/{id}"})
    ResponseEntity<M> findById(@PathVariable("id") ID id);

    @GetMapping
    ResponseEntity<Page<M>> findAll(@RequestParam(value = "model", required = false) Optional<String> json
            , @PageableDefault Pageable pageable);

    @GetMapping(value = {"/count"})
    ResponseEntity<Long> countAll(@RequestParam(value = "model", required = false) Optional<String> json);

    @DeleteMapping(value = {"/{id}"})
    ResponseEntity<Void> deleteById(@PathVariable("id") ID id);

    @PostMapping
    @ResponseBody
    ResponseEntity<M> save(@Validated(Save.class) @RequestBody M model);

    @PatchMapping
    @ResponseBody
    ResponseEntity<M> update(@Validated(Update.class) @RequestBody M model);
}
