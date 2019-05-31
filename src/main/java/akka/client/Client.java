package akka.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.protocol.Response;

public class Client extends AbstractActor {

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Response.class, response -> {
      System.out.println("Client ===> The response is " + response);
    }).build();
  }

  @Override
  public void preStart() {
    System.out.println("Client preStart!");
  }

  @Override
  public void postStop() {
    System.out.println("Client postStop");
  }

}
