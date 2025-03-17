package backend.shopapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductImageDTO {
    private UUID id;
    private byte[] image;
}
