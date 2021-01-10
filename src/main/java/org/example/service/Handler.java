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

package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.grpc.Service;
import org.example.grpc.serviceGrpc;

import java.util.Map;

public class Handler extends serviceGrpc.serviceImplBase {

    @Override
    public void getName(Service.Empty request, StreamObserver<Service.HandlerName> responseObserver) {

        System.out.println("getName method is called");
        Service.HandlerName handlerName = Service.HandlerName.newBuilder().setName("grpcBasedEventHandler").build();
        responseObserver.onNext(handlerName);
        responseObserver.onCompleted();

    }

    @Override
    public void getPriority(Service.MessageContext request, StreamObserver<Service.Priority> responseObserver) {

        System.out.println("getPriority method is called");
        Service.Priority priority = Service.Priority.newBuilder().setPriority(58).build();
        responseObserver.onNext(priority);
        responseObserver.onCompleted();


    }

    @Override
    public void handleEvent(Service.Event request, StreamObserver<Service.Log> responseObserver) {

        System.out.println("handleEvent method is called");
        Service.Log.Builder serverLog = Service.Log.newBuilder();

        for (Map.Entry<String, String> entry : request.getEventPropertiesMap().entrySet()) {
            System.out.println("Key: "+entry.getKey()+" Value: "+ entry.getValue());
        }

        if (request.getEvent().equals("POST_ADD_USER")) {
            serverLog.setLog("testing POST_ADD_USER event using GrpcEventHandler on Java grpc server : " +
                    "Successfully added the user with UserName- " + request.getEventPropertiesOrThrow("user-name")
                    + ", TenantDomain- " + request.getEventPropertiesOrThrow("tenant-domain"));

            System.out.println("testing POST_ADD_USER event using GrpcEventHandler on Java grpc server : " +
                    "Successfully added the user with UserName- " + request.getEventPropertiesOrThrow("user-name")
                    + ", TenantDomain- " + request.getEventPropertiesOrThrow("tenant-domain"));
        }
        responseObserver.onNext(serverLog.build());
        responseObserver.onCompleted();
    }
}
