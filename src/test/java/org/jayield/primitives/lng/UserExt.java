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

package org.jayield.primitives.lng;

import org.jayield.boxes.LongBox;

/**
 * @author Miguel Gamboa
 * created on 03-06-2017
 */
public class UserExt {
    static LongTraverser collapse(LongQuery src) {
        return yield -> {
            LongBox box = new LongBox();
            src.traverse(item -> {
                if (!box.isPresent() || box.getValue() != item) {
                    box.turnPresent(item);
                    yield.ret(item);
                }
            });
        };
    }
}
