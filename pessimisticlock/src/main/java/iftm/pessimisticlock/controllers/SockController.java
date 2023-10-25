package iftm.pessimisticlock.controllers;

import iftm.pessimisticlock.models.SockItem;
import iftm.pessimisticlock.models.dto.SockItemDto;
import iftm.pessimisticlock.repositories.SockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/item/{id}/update")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> updateSockItemQuantity(
        @PathVariable Long id,
        @RequestParam int incrementAmount) {

        final SockItem item = repository.findById(id);

        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found with ID " + id);
        }

        int currentQuantity = item.getQuantity();
        int updatedQuantity = incrementAmount + currentQuantity;

        if (updatedQuantity < 0) {
            return ResponseEntity.badRequest().body("Updated quantity cannot be negative");
        }

        item.setQuantity(updatedQuantity);
        repository.save(item);

        return ResponseEntity.ok("Successfully updated quantity for item with ID " + id + " to " + updatedQuantity);
    }

}
