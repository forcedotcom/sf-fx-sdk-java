/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

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

  String getAssignmentRuleId();

  ColumnDelimiter getColumnDelimiter();

  ContentType getContentType();

  String getExternalIdFieldName();

  LineEnding getLineEnding();
}
