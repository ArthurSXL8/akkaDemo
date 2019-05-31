package akka.server;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorPaths;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientReceptionist;
import akka.cluster.client.ClusterClientSettings;
import akka.util.LocalConfig;

public class MainServer {
  public static void main(String[] args) {
    System.out.println("This is main in server!");
    Config conf = ConfigFactory.load("server");
    ActorSystem system = ActorSystem.create(LocalConfig.getActorSystemName(), conf);
    ActorRef server = system.actorOf(Props.create(Server.class), "server");
    ClusterClientReceptionist.get(system).registerService(server);


  }
}
