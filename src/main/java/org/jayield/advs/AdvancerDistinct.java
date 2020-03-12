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

import org.jayield.Advancer;
import org.jayield.Yield;

import java.util.HashSet;

public class AdvancerDistinct<T> extends AbstractAdvancer<T> {
    private final Advancer<T> upstream;
    final HashSet<T> mem = new HashSet<>();

    public AdvancerDistinct(Advancer<T> adv) {
        this.upstream = adv;
    }

    /**
     * Returns true if it moves successfully. Otherwise returns false
     * signaling it has finished.
     */
    public boolean move() {
        while(upstream.hasNext()) {
            curr = upstream.next();
            if(mem.add(curr))
                return true;
        }
        return false;
    }

    @Override
    public void traverse(Yield<? super T> yield) {
        upstream.traverse(item -> {
            if(mem.add(item)) yield.ret(item);
        });
    }
}