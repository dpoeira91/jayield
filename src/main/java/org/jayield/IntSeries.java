/*
 * Copyright (c) 2017, Fernando Miguel Carvalho, mcarvalho@cc.isel.ipl.pt
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

import org.jayield.boxes.IntBox;

import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author Miguel Gamboa
 *         created on 03-07-2017
 */
public class IntSeries {
    private final IntTraversable bulk;
    private final IntAdvancer advancer;

    public IntSeries(IntTraversable bulk, IntAdvancer advancer) {
        this.bulk = bulk;
        this.advancer = advancer;
    }

    public OptionalInt max(){
        IntBox b = new IntBox();
        this.bulk.traverse(e -> {
            if(!b.isPresent()) b.turnPresent(e);
            else if(e > b.getValue()) b.setValue(e);
        });
        return b.isPresent()
                ? OptionalInt.of(b.getValue())
                : OptionalInt.empty();
    }
}