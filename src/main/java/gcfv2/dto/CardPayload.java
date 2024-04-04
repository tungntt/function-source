package gcfv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardPayload {
    private String cardId;
    private CardMessage card;
}
