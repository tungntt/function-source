package gcfv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Grid {
    private String title;
    @Builder.Default
    private int columnCount = 1;
    private List<GridItem> items;
}
