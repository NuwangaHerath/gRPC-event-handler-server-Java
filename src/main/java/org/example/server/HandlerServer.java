/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.example.server;

import org.example.service.HandlerService;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;
import java.io.IOException;

public class HandlerServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        File certChainFile = new File("/home/nuwanga/wso2/event-handler-server/src/main/java/org/example/server/cert1/server-cert.pem");
        File privateKeyFile = new File("/home/nuwanga/wso2/event-handler-server/src/main/java/org/example/server/cert1/server-key.pem");

        // SSL/TLS Authenticated server.
        Server server = ServerBuilder.forPort(8030)
                .useTransportSecurity(certChainFile, privateKeyFile)
                .addService(new HandlerService())
                .build();
//        Server server = ServerBuilder.forPort(8020)
//                .addService(new HandlerService())
//                .build();

        server.start();
        System.out.println("Handler Server started at " + server.getPort());
        server.awaitTermination();
        server.shutdown();


    }

}
