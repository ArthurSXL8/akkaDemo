package akka.client;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorPaths;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;
import akka.protocol.Message;
import akka.util.LocalConfig;

public class MainClient {
  public static void main(String[] args) {
    System.out.println("This is main in Client class!");
    Config conf = ConfigFactory.load("client");
    ActorSystem system = ActorSystem.create(LocalConfig.getClientSystemName(), conf);
    Set<ActorPath> initialContacts =
        conf.getStringList("akka.cluster.client.initial-contacts").stream().map(ActorPaths::fromString)
            .collect(Collectors.toSet());

    ActorRef receptionist = system.actorOf(ClusterClient
            .props(ClusterClientSettings.create(system).withInitialContacts(initialContacts)),
        "client");

    int i = 0;
    while (true) {
      long timestamp = System.currentTimeMillis();
      ++i;
      Message message = Message.builder().id(String.valueOf(i)).body("Hi " + i).build();
      receptionist.tell(
          new ClusterClient.Send("/user/server" , String.valueOf(i)),
          receptionist);
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        System.out.println(e.getLocalizedMessage());
      }
    }

  }
}
