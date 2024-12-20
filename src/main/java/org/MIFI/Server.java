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

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.stub.StreamObserver;
import org.MIFI.protobuf.GreeterGrpc;
import org.MIFI.protobuf.HelloReply;
import org.MIFI.protobuf.HelloRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server {
  private static final Logger logger = Logger.getLogger(Server.class.getName());
  private io.grpc.Server server;
  private void start() throws IOException {
    int port = 50051;
    server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
        .addService(new GreeterImpl())
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          Server.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    final Server server = new Server();
    server.start();
    server.blockUntilShutdown();
  }
  static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
      HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }
}
