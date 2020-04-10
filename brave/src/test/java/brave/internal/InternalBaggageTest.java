/*
 * Copyright 2013-2020 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package brave.internal;

import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig.SingleBaggageField;
import brave.propagation.B3SinglePropagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.Propagation;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalBaggageTest {
  @Test public void allKeyNames_baggagePropagation() {
    Propagation.Factory factory = BaggagePropagation.newFactoryBuilder(B3SinglePropagation.FACTORY)
      .add(SingleBaggageField.local(BaggageField.create("redacted")))
      .add(SingleBaggageField.remote(BaggageField.create("user-id")))
      .add(SingleBaggageField.remote(BaggageField.create("session-id"))).build();
    assertThat(InternalBaggage.instance.allKeyNames(factory))
      .containsExactly("b3", "user-id", "session-id");
  }

  @Test public void allKeyNames_extraFieldPropagation() {
    ExtraFieldPropagation.Factory factory =
      ExtraFieldPropagation.newFactory(B3SinglePropagation.FACTORY, "user-id", "session-id");
    assertThat(InternalBaggage.instance.allKeyNames(factory))
      .containsExactly("b3", "user-id", "session-id");
  }
}