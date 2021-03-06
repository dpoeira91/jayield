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

package org.jayield.advs;

import java.util.Iterator;
import java.util.stream.Stream;

import org.jayield.Advancer;
import org.jayield.Yield;

public class AdvancerStream<U> implements Advancer<U> {
    private final Stream<U> upstream;
    private Iterator<U> current;
    private boolean operated = false;

    public AdvancerStream(Stream<U> data) {
        this.upstream = data;
    }

    public Iterator<U> current() {
        if(operated) return current;
        operated = true;
        current = upstream.iterator();
        return current;
    }

    @Override
    public U next() {
        return current().next();
    }

    @Override
    public boolean hasNext() {
        return current().hasNext();
    }

    @Override
    public void traverse(Yield<? super U> yield) {
        upstream.forEach(yield::ret);
    }
}
