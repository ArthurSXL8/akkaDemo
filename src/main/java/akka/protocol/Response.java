package akka.protocol;

import java.io.Serializable;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Response implements Serializable {
  @Builder.Default
  private static final long serialVersionUID = 3048046943684483119L;
  private String id;
  private String body;
}
