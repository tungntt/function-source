package gcfv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CardRequest {
    private List<CardPayload> cardsV2;
}
