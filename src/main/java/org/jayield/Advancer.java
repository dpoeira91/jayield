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

package org.jayield;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Sequential traverser with both internal and external iteration approach.
 */
public interface Advancer<T> extends Iterator<T>, Traverser<T> {
    /**
     * An Advancer object without elements.
     */
    static <R> Advancer<R> empty() {
        return new Advancer<R>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public R next() {
                throw new NoSuchElementException("No such elements available for iteration!");
            }

            @Override
            public void traverse(Yield<? super R> yield) {
                /* Do nothing. Since there are no elements, thus there is nothing to do. */
            }
        };
    }


}
