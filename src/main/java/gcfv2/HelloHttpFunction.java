package gcfv2;

import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import gcfv2.config.WebClientConfig;
import gcfv2.dto.*;
import gcfv2.service.GoogleChatService;
import reactor.core.publisher.Mono;

public class HelloHttpFunction implements HttpFunction {
  public void service(final HttpRequest request, final HttpResponse response) throws Exception {
    final BufferedWriter writer = response.getWriter();
    writer.write("Sending Message To Google Chat");
    GoogleChatService chatService = new GoogleChatService(new ObjectMapper(), WebClientConfig.init());

    DecoratedText claimId = new DecoratedText().setText(DecoratedText.CLAIM_ID_LABEL, "JVJUUCEEZZMTCUZAMNLU");
    DecoratedText visitId = new DecoratedText().setText(DecoratedText.VISIT_ID_LABEL, "W1O2LGVUOIBFIJCIEB4M");
    DecoratedText patientName = new DecoratedText().setText(DecoratedText.PATIENT_NAME_LABEL, "Stacy Morris");
    Widget claimWidget = Widget.builder().decoratedText(claimId).build();
    Widget visitWidget = Widget.builder().decoratedText(visitId).build();
    Widget patientWidget = Widget.builder().decoratedText(patientName).build();

    GridItem errorPayer = GridItem.builder().title("Payer ID Not Found").build();
    GridItem errorInsurance = GridItem.builder().title("Insurance ID Not Found").build();

    Grid grid = Grid.builder()
            .title("Errors")
            .items(Arrays.asList(errorPayer, errorInsurance))
            .build();
    Widget gridWidget = Widget.builder()
            .grid(grid)
            .build();

    List<Widget> widgets = Arrays.asList(claimWidget, visitWidget, patientWidget, gridWidget);

    Section section = Section.builder()
            .header("Failed Rule Engine Claim")
            .collapsible(true)
            .uncollapsibleWidgetsCount(1)
            .widgets(widgets)
            .build();
    CardMessage cardMessage = CardMessage.builder()
            .sections(Arrays.asList(section))
            .build();
    CardPayload payload = CardPayload.builder()
            .cardId(UUID.randomUUID().toString())
            .card(cardMessage)
            .build();
    CardRequest cardRequest = CardRequest.builder()
            .cardsV2(Arrays.asList(payload))
            .build();
    Mono<String> mentionUserMono = chatService.sendMessage(SingleMessage.builder()
            //.text("Hi <users/110339958766966197618>, We got an invalid claim. Please view detail in below").build());
            .text("Hi <users/all>, We got an invalid claim. Please view detail in below").build());
    mentionUserMono.subscribe(responseBody -> {
      try {
        chatService.sendMessage(cardRequest);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
