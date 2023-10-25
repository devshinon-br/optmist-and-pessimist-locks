package iftm.optimisticlock.controllers;

import iftm.optimisticlock.models.SockItem;
import iftm.optimisticlock.models.dto.SockItemDto;
import iftm.optimisticlock.repositories.SockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sock")
public class SockController {

    @Autowired
    private SockItemRepository repository;

    @PostMapping("/item")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> createSockItem(@RequestBody final SockItemDto request) {
        // create a new item
        final SockItem item = request.toSockItem();

        repository.save(item);

        return ResponseEntity.ok("Successfully created quantity for new item with ID " + item.getId());
    }

}
