/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

import com.salesforce.functions.jvm.sdk.Record;
import java.util.Optional;
import javax.annotation.concurrent.Immutable;

/**
 * Represents a Salesforce bulk job.
 *
 * <p>Implementations must be immutable and therefore thread-safe.
 */
@Immutable
public interface Job {
  String getObjectType();

  Operation getOperation();

  Optional<String> getAssignmentRuleId();

  Optional<String> getExternalIdFieldName();

  Iterable<Record> getRecords();
}
