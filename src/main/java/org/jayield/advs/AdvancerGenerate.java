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

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.jayield.Advancer;
import org.jayield.Yield;

public class AdvancerGenerate<U> implements Advancer<U> {
    private final Supplier<U> s;

    public AdvancerGenerate(Supplier<U> s) {
        this.s = s;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public U next() {
        if(!hasNext()) throw new NoSuchElementException("No more elements available on iteration!");
        return s.get();
    }

    @Override
    public void traverse(Yield<? super U> yield) {
        while (hasNext()) {
            yield.ret(s.get());
        }
    }
}
