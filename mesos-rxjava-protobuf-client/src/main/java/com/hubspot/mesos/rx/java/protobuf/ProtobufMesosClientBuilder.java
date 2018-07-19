/*
 *    Copyright (C) 2016 Mesosphere, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hubspot.mesos.rx.java.protobuf;

import com.hubspot.mesos.rx.java.MesosClientBuilder;
import org.apache.mesos.v1.scheduler.Protos;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of methods that have some pre-defined {@link MesosClientBuilder} configurations.
 */
public final class ProtobufMesosClientBuilder {
    private ProtobufMesosClientBuilder() {}

    /**
     * @return  An initial {@link MesosClientBuilder} that will use protobuf
     *          for the {@link org.apache.mesos.v1.scheduler.Protos.Call Call} and
     *          {@link org.apache.mesos.v1.scheduler.Protos.Event Event} messages.
     */
    @NotNull
    public static MesosClientBuilder<Protos.Call, Protos.Event> schedulerUsingProtos() {
        return MesosClientBuilder.<Protos.Call, Protos.Event>newBuilder()
            .sendCodec(ProtobufMessageCodecs.SCHEDULER_CALL)
            .receiveCodec(ProtobufMessageCodecs.SCHEDULER_EVENT)
            ;
    }

    /**
     * @return  An initial {@link MesosClientBuilder} that will use protobuf
     *          for the {@link org.apache.mesos.v1.executor.Protos.Call Call} and
     *          {@link org.apache.mesos.v1.executor.Protos.Event Event} messages.
     */
    @NotNull
    public static MesosClientBuilder<
        org.apache.mesos.v1.executor.Protos.Call,
        org.apache.mesos.v1.executor.Protos.Event
        > executorUsingProtos() {
        return MesosClientBuilder.<org.apache.mesos.v1.executor.Protos.Call, org.apache.mesos.v1.executor.Protos.Event>newBuilder()
            .sendCodec(ProtobufMessageCodecs.EXECUTOR_CALL)
            .receiveCodec(ProtobufMessageCodecs.EXECUTOR_EVENT)
            ;
    }
}
