/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package com.salesforce.functions.jvm.sdk.bulk;

/** The type of processing operation for a bulk job. */
public enum Operation {
  @SuppressWarnings("unused")
  INSERT("insert"),

  @SuppressWarnings("unused")
  DELETE("delete"),

  @SuppressWarnings("unused")
  HARD_DELETE("hardDelete"),

  @SuppressWarnings("unused")
  UPDATE("update"),

  @SuppressWarnings("unused")
  UPSERT("upsert");

  private final String textValue;

  Operation(String textValue) {
    this.textValue = textValue;
  }

  public static Operation fromTextValue(String textValue) {
    for (Operation operation : values()) {
      if (operation.textValue.equals(textValue)) {
        return operation;
      }
    }
    throw new IllegalArgumentException("No enum constant " + Operation.class.getCanonicalName() + " found with textValue=" + textValue);
  }

  public String getTextValue() {
    return textValue;
  }
}
