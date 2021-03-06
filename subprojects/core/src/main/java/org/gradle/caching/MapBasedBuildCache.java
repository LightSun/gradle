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

package org.gradle.caching;

import org.gradle.api.Incubating;
import org.gradle.internal.io.StreamByteBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * Simple build cache implementation that delegates to a {@link ConcurrentMap}.
 *
 * @since 3.3
 */
@Incubating
public class MapBasedBuildCache implements BuildCache {
    private final String description;
    private final ConcurrentMap<String, byte[]> delegate;

    public MapBasedBuildCache(String description, ConcurrentMap<String, byte[]> delegate) {
        this.description = description;
        this.delegate = delegate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean load(BuildCacheKey key, BuildCacheEntryReader reader) throws IOException {
        final byte[] bytes = delegate.get(key.getHashCode());
        if (bytes == null) {
            return false;
        }
        reader.readFrom(new ByteArrayInputStream(bytes));
        return true;
    }

    @Override
    public void store(BuildCacheKey key, BuildCacheEntryWriter output) throws IOException {
        StreamByteBuffer buffer = new StreamByteBuffer();
        output.writeTo(buffer.getOutputStream());
        delegate.put(key.getHashCode(), buffer.readAsByteArray());
    }

    @Override
    public void close() throws IOException {
        // Do nothing
    }
}
