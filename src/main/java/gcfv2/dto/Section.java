package gcfv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Section {
    private String header;
    private boolean collapsible;
    private int uncollapsibleWidgetsCount;
    private List<Widget> widgets;
}
