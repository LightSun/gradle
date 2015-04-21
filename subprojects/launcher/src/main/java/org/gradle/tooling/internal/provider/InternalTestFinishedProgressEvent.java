/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.tooling.internal.provider;

import org.gradle.tooling.internal.protocol.TestFinishedProgressEventVersion1;

import static org.gradle.tooling.internal.protocol.TestResultVersion1.*;

public class InternalTestFinishedProgressEvent extends InternalTestProgressEvent implements TestFinishedProgressEventVersion1 {
    public InternalTestFinishedProgressEvent(long eventTime, InternalTestDescriptor descriptor, InternalTestResult result) {
        super(eventTime, descriptor, result);
    }

    protected String typeDisplayName() {
        if (getResult().getResultType().equals(RESULT_SUCCESSFUL)) {
            return "succeeded";
        }
        if (getResult().getResultType().equals(RESULT_FAILED)) {
            return "failed";
        }
        if (getResult().getResultType().equals(RESULT_SKIPPED)) {
            return "skipped";
        }
        throw new IllegalArgumentException("Unknown event type.");
    }
}
