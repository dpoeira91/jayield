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

package org.jayield.primitives.lng.advs;

import org.jayield.primitives.lng.LongAdvancer;
import org.jayield.primitives.lng.LongYield;

public class LongAdvancerSkip implements LongAdvancer {
    private final LongAdvancer upstream;
    private final int n;
    int index;

    public LongAdvancerSkip(LongAdvancer adv, int n) {
        this.upstream = adv;
        this.n = n;
        index = 0;
    }

    @Override
    public long nextLong() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No such elements on iteration!");
        }
        return upstream.nextLong();
    }

    @Override
    public boolean hasNext() {
        for (; upstream.hasNext() && index < n; index++)
            upstream.nextLong();
        return upstream.hasNext();
    }

    /**
     * Continues from the polong where tryAdvance or next left the
     * longernal iteration.
     *
     * @param yield
     */
    @Override
    public void traverse(LongYield yield) {
        upstream.traverse(item -> {
            if (index++ >= n) {
                yield.ret(item);
            }
        });
    }
}
