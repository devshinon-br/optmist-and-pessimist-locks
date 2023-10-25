package iftm.pessimisticlock.models.dto;

import iftm.pessimisticlock.models.SockItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SockItemDto implements Serializable {
    private Long id;
    private int quantity;
    private String name;

    public SockItem toSockItem() {
        final SockItem item = new SockItem();
        item.setQuantity(this.getQuantity());
        item.setName(this.getName());
        return item;
    }
}
