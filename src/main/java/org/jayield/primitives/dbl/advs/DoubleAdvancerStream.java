/*
 * Copyright (c) 2020, Fernando Miguel Carvalho, mcarvalho@cc.isel.ipl.pt
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

package org.jayield.primitives.dbl.advs;

import java.util.stream.DoubleStream;

import org.jayield.primitives.dbl.DoubleAdvancer;
import org.jayield.primitives.dbl.DoubleIterator;
import org.jayield.primitives.dbl.DoubleYield;

public class DoubleAdvancerStream implements DoubleAdvancer {
    private final DoubleStream upstream;
    private DoubleIterator current;
    private boolean operated = false;

    public DoubleAdvancerStream(DoubleStream data) {
        this.upstream = data;
    }

    @Override
    public double nextDouble() {
        return current().nextDouble();
    }

    public DoubleIterator current() {
        if (operated) {
            return current;
        }
        operated = true;
        current = DoubleIterator.from(upstream.iterator());
        return current;
    }

    @Override
    public boolean hasNext() {
        return current().hasNext();
    }

    @Override
    public void traverse(DoubleYield yield) {
        upstream.forEach(yield::ret);
    }
}
