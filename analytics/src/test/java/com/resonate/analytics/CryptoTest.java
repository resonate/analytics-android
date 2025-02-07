/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Segment.io, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.resonate.analytics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.annotation.Config.NONE;

import java.io.IOException;
import okio.Buffer;
import okio.ByteString;
import okio.Okio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = NONE)
public class CryptoTest {
  @Test
  public void noneCryptoWrite() throws IOException {
    Crypto crypto = Crypto.none();
    ByteString foo = ByteString.encodeUtf8("foo");
    Buffer buffer = new Buffer();

    foo.write(crypto.encrypt(buffer.outputStream()));

    assertThat(buffer.readByteString()).isEqualTo(foo);
  }

  @Test
  public void noneCryptoRead() throws IOException {
    Crypto crypto = Crypto.none();
    ByteString foo = ByteString.encodeUtf8("foo");
    Buffer buffer = new Buffer();

    buffer.write(foo);

    assertThat(Okio.buffer(Okio.source(crypto.decrypt(buffer.inputStream()))).readByteString()) //
        .isEqualTo(foo);
  }
}
