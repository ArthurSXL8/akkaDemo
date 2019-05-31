package akka.protocol;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message implements Serializable {
  @Builder.Default
  private static final long serialVersionUID = 5980257823662986456L;

  private String id;
  private String body;
}
