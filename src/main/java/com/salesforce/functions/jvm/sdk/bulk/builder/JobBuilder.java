/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk.builder;

import com.salesforce.functions.jvm.sdk.bulk.Job;
import javax.annotation.Nonnull;

/** Builder for {@link Job}. */
public interface JobBuilder {
  /**
   * Sets the assignment rule ID for this job.
   *
   * @param assignmentRuleId The assignment rule ID for this job.
   * @return This {@link JobBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  JobBuilder withAssignmentRuleId(String assignmentRuleId);

  /**
   * Sets the external ID field name for this job.
   *
   * @param externalIdFieldName The external ID field name for this job.
   * @return This {@link JobBuilder} instance to allow method chaining.
   */
  @Nonnull
  @SuppressWarnings("unused")
  JobBuilder withExternalIdFieldName(String externalIdFieldName);

  /**
   * Returns a new and immutable {@link Job} instance based on the information stored in this
   * builder.
   *
   * @return The new {@link Job} instance.
   */
  @Nonnull
  @SuppressWarnings("unused")
  Job build();
}
