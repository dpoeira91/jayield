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
import org.jayield.Query;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class AsyncQueryTest {

    @Test
    public void testOfArrayAndBlockingSubscribe() {
        Query<String> expected = Query.of("a", "b", "c", "d");
        AsyncQuery
            .of("a b c d".split(" "))
            .onNext((item, err) -> {
                assertNull(err);
                assertEquals(expected.next(), item);
            })
            .blockingSubscribe();
        assertFalse(expected.hasNext());
    }
    @Test
    public void testOfIteratorAndBlockingSubscribe() {
        Iterable<String> source = Arrays.asList("a", "b", "c", "d");
        Iterator<String> expected = source.iterator();
        AsyncQuery
            .of(source.iterator())
            .onNext((item, err) -> {
                assertNull(err);
                assertEquals(expected.next(), item);
            })
            .blockingSubscribe();
        assertFalse(expected.hasNext());
    }

    @Test
    public void testSkip() {
        Query<Integer> expected = Query.of(4, 5, 6, 7);
        AsyncQuery
            .fork(1, 2, 3, 4, 5, 6, 7)
            .skip(3)
            .subscribe((item, err) -> {
                assertNull(err);
                assertEquals(expected.next(), item);
            })
            .join();
        assertFalse(expected.hasNext());
    }

    @Test
    public void testFilter() {
        Query<Integer> expected = Query.of(1, 3, 5, 7, 9);
        AsyncQuery
            .fork(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .filter(n -> n % 2 != 0)
            .subscribe((item, err) -> {
                assertNull(err);
                assertEquals(expected.next(), item);
            })
            .join();
        assertFalse(expected.hasNext());
    }
    @Test
    public void testFilterAndMap() {
        Query<Integer> expected = Query.of(3, 3, 5);
        AsyncQuery
            .fork("abc", "abcd", "ab", "bad", "super", "isel")
            .map(String::length)
            .filter(n -> n % 2 != 0)
            .subscribe((item, err) -> {
                assertNull(err);
                assertEquals(expected.next(), item);
            })
            .join();
        assertFalse(expected.hasNext());
    }

    @Test
    public void testTakeWhile() {
        Query<Integer> expected1 = Query.of(1, 2, 3, 4, 5);
        Query<Integer> expected2 = Query.of(1, 2, 3, 4);
        AsyncQuery
            .fork(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .onNext((item, err) -> {
                assertNull(err);
                assertEquals(expected1.next(), item);
            })
            .takeWhile(n -> n < 5)
            .subscribe((item, err) -> {
                assertNull(err);
                assertEquals(expected2.next(), item);
            })
            .join();
        assertFalse(expected1.hasNext());
        assertFalse(expected2.hasNext());
    }
    @Test
    public void testFlatMapConcat() {
        Query<Integer> expected = Query.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        AsyncQuery
            .fork(2, 5, 8)
            .flatMapConcat(nr -> AsyncQuery.fork(nr - 1, nr, nr + 1))
            .subscribe((item, err) -> {
                assertNull(err);
                Integer e = expected.next();
                assertEquals(e , item);
            })
            .join();
        assertFalse(expected.hasNext());
    }
    @Test
    public void testFlatMapMerge() {
        Queue<Integer> expected = new ConcurrentLinkedQueue<>();
        expected.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        AsyncQuery
            .fork(2, 5, 8)
            .flatMapMerge(nr -> AsyncQuery.of(nr - 1, nr, nr + 1))
            .subscribe((item, err) -> {
                assertNull(err);
                assertTrue(expected.remove(item), "Missing item " + item + "!");
            })
            .join();
        assertTrue(expected.isEmpty());
    }
}
