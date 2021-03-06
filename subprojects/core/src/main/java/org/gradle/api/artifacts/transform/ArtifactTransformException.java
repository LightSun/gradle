/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.api.artifacts.transform;

import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.GradleException;
import org.gradle.api.Incubating;
import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.internal.exceptions.Contextual;

import java.io.File;

/**
 * An exception to report a problem during transformation execution.
 */
@Contextual
@Incubating
public class ArtifactTransformException extends GradleException {

    public ArtifactTransformException(File input, AttributeContainer expectedAttributes, ArtifactTransform transform, Throwable cause) {
        super(format(input, expectedAttributes, transform), cause);
    }

    private static String format(File input, AttributeContainer expectedAttributes, ArtifactTransform transform) {
        return String.format("Error while transforming '%s' to match attributes '%s' using '%s'",
            input.getName(), ((AttributeContainerInternal) expectedAttributes).asImmutable().toString(), transform.getClass().getSimpleName());
    }
}
