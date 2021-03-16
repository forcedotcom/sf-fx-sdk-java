/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.functions.jvm.sdk;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Represents the connection to the the execution environment and the Customer 360 instance that the function is
 * associated with.
 */
public interface Context {
    /**
     * Returns the unique identifier for a given execution of a function.
     *
     * @return The unique identifier for a given execution of a function.
     */
    @Nonnull
    String getId();

    /**
     * Returns information about the invoking Salesforce organization and user in Customer 360.
     *
     * @return Information about the invoking Salesforce organization and user in Customer 360.
     */
    @Nonnull
    Optional<Org> getOrg();
}
