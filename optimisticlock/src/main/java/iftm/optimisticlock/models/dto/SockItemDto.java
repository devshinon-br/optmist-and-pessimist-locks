package iftm.optimisticlock.models.dto;

import iftm.optimisticlock.models.SockItem;
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
    private int version;
    private String name;

    public SockItem toSockItem() {
        final SockItem item = new SockItem();
        item.setQuantity(this.getQuantity());
        item.setVersion(this.getVersion());
        item.setName(this.getName());
        return item;
    }
}
