package akka.server;

import java.io.FileWriter;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.protocol.Message;

public class Server extends AbstractActor {
  private Cluster cluster = Cluster.get(getContext().getSystem());
  private String file = "/home/xujunhong/akka.log";
  private FileWriter writer;

  public Server() {
    try {
      writer = new FileWriter(file);
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
  }

  @Override
  public void preStart() {
    System.out.println("Server is starting!");
    cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), ClusterEvent.MemberEvent.class,
        ClusterEvent.UnreachableMember.class);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(String.class, message -> {
      System.out.println("Server receive message " + message);
      writer.append(message);
    }).match(ClusterEvent.MemberUp.class, memberUp -> {
      System.out.println("Member is up " + memberUp.member());
    }).match(ClusterEvent.MemberRemoved.class, memberRemoved -> {
      System.out.println("Member is removed " + memberRemoved.member());
    }).match(ClusterEvent.MemberEvent.class, message -> {
      System.out.println("Member event " + message);
    }).build();
  }

  @Override
  public void postStop() {
    cluster.unsubscribe(getSelf());
    System.out.println("Server is stopping!");
  }

}
