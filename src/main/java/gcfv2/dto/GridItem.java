package gcfv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GridItem {
    private String title;
    @Builder.Default
    private String textAlignment = "CENTER";
}
