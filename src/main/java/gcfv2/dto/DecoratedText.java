package gcfv2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecoratedText {

    public static final String CLAIM_ID_LABEL = "Claim ID: ";
    public static final String VISIT_ID_LABEL = "Visit ID: ";
    public static final String PATIENT_NAME_LABEL = "Patient Name: ";

    private String text;

    public DecoratedText setText(String label, String text) {
        this.text = label + text;
        return this;
    }
}
