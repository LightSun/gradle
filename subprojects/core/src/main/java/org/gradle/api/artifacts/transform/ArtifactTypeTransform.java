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

import org.gradle.api.Incubating;
import org.gradle.api.attributes.AttributeContainer;

import java.io.File;
import java.util.Collection;

import static org.gradle.api.internal.artifacts.ArtifactAttributes.ARTIFACT_FORMAT;

/**
 * Base class for simple type-based artifact transformations.
 */
@Incubating
public abstract class ArtifactTypeTransform extends ArtifactTransform {

    protected abstract String getInputType();

    protected abstract Collection<String> getOutputTypes();

    protected abstract File transform(File input, String outputType);

    @Override
    public void configure(AttributeContainer from, ArtifactTransformTargets targetRegistry) {
        from.attribute(ARTIFACT_FORMAT, getInputType());

        for (String outputType : getOutputTypes()) {
            targetRegistry.newTarget().attribute(ARTIFACT_FORMAT, outputType);
        }
    }

    @Override
    public File transform(File input, AttributeContainer target) {
        String targetType = target.getAttribute(ARTIFACT_FORMAT);
        return transform(input, targetType);
    }
}
