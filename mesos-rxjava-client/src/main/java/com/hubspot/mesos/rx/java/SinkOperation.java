/*
 *    Copyright (C) 2015 Mesosphere, Inc
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

package com.hubspot.mesos.rx.java;

import org.jetbrains.annotations.NotNull;
import rx.functions.Action0;
import rx.functions.Action1;

import static com.hubspot.mesos.rx.java.util.Validations.checkNotNull;

/**
 * A simple object that represents a {@link T} that is to be sent to Mesos.
 * <p>
 * For example, when communicating with the Mesos HTTP Scheduler API, any
 * <a href="https://github.com/apache/mesos/blob/master/docs/scheduler-http-api.md#calls" target="_blank">Call</a>
 * that isn't a
 * <a href="https://github.com/apache/mesos/blob/master/docs/scheduler-http-api.md#subscribe" target="_blank">SUBSCRIBE</a>
 * will result in a semi-blocking request.
 * <p>
 * This means things like request validation (including body deserialization and field validation) are
 * performed synchronously during the request. Due to this behavior, this class exists to inform the
 * user of the success or failure of requests sent to the master.
 * <p>
 * It should be noted that this object doesn't represent the means of detecting and handling connection errors
 * with Mesos. The intent is that it will be communicated to the whole event stream rather than an
 * individual {@code SinkOperation}.
 * <p>
 * <i>NOTE</i>
 * The semantics of which thread a callback ({@code onComplete} or {@code onError}) will be invoked on are undefined
 * and should not be relied upon. This means that all standard thread safety/guards should be in place for the actions
 * performed inside the callback.
 *
 * @see SinkOperations#create
 */
public final class SinkOperation<T> {
    @NotNull
    private final T thingToSink;
    @NotNull
    private final Action1<Throwable> onError;
    @NotNull
    private final Action0 onCompleted;

    /**
     * This constructor is considered an internal API and should not be used directly, instead use one of the
     * factory methods defined in {@link SinkOperations}.
     * @param thingToSink    The {@link T} to send to Mesos
     * @param onCompleted    The callback invoked when HTTP 202 is returned by Mesos
     * @param onError        The callback invoked for an HTTP 400 or 500 status code returned by Mesos
     */
    SinkOperation(
        @NotNull final T thingToSink,
        @NotNull final Action0 onCompleted,
        @NotNull final Action1<Throwable> onError
    ) {
        this.thingToSink = checkNotNull(thingToSink, "argument thingToSink can not be null");
        this.onCompleted = checkNotNull(onCompleted, "argument onCompleted can not be null");
        this.onError = checkNotNull(onError, "argument onError can not be null");
    }

    public void onCompleted() {
        onCompleted.call();
    }

    public void onError(final Throwable e) {
        onError.call(e);
    }

    @NotNull
    public T getThingToSink() {
        return thingToSink;
    }
}
