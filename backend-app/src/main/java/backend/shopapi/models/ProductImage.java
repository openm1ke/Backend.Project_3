package backend.shopapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductImage {
    private UUID id;
    private byte[] image;
}
