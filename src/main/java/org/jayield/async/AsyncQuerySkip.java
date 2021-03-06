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

package org.jayield.async;

import org.jayield.AsyncQuery;
import org.jayield.AsyncTraverser;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class AsyncQuerySkip<T> extends AsyncQuery<T> {
    private final AsyncTraverser<T> upstream;
    private final int skip;
    private int count = 0;

    public AsyncQuerySkip(AsyncTraverser<T> upstream, int skip) {
        this.upstream = upstream;
        this.skip = skip;
    }

    @Override
    public CompletableFuture<Void> subscribe(BiConsumer<? super T, ? super Throwable> cons) {
        return upstream.subscribe((item, err) -> {
            if(err != null) {
                cons.accept(null, err);
                return;
            }
            if(count >= skip) cons.accept(item, err);
            else count++;
        });
    }
}
