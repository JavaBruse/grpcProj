/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.MIFI;

import io.grpc.*;
import org.MIFI.protobuf.GreeterGrpc;
import org.MIFI.protobuf.HelloReply;
import org.MIFI.protobuf.HelloRequest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
  private static final Logger logger = Logger.getLogger(Client.class.getName());

  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  public Client(Channel channel) {
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Greeting: " + response.getMessage());
  }

  public static void main(String[] args) throws Exception {
    String user = "world";
    String target = "localhost:50051";
    if (args.length > 0) {
      if ("--help".equals(args[0])) {
        System.err.println("Usage: [name [target]]");
        System.err.println("");
        System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
        System.err.println("  target  The server to connect to. Defaults to " + target);
        System.exit(1);
      }
      user = args[0];
    }
    if (args.length > 1) {
      target = args[1];
    }

    ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
        .build();
    try {
      Client client = new Client(channel);
      client.greet(user);
    } finally {
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
