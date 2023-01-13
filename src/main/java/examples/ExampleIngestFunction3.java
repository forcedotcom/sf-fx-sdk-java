/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */
package examples;

import com.salesforce.functions.jvm.sdk.Context;
import com.salesforce.functions.jvm.sdk.InvocationEvent;
import com.salesforce.functions.jvm.sdk.SalesforceFunction;
import com.salesforce.functions.jvm.sdk.data.bulk.*;
import java.util.Optional;
import javax.annotation.Nonnull;

public class ExampleIngestFunction3 implements SalesforceFunction<String, String> {
  @Override
  public String apply(InvocationEvent<String> event, Context context) throws Exception {
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    // This example demonstrates adding arbitrary objects as row data. This is common when a
    // function is used to
    // ingest data from an external system. In this example the values are hardcoded, but could
    // easily come from
    // an external system via a database connection or HTTP request.
    ExternalAccountObject externalAccountObject1 =
        new ExternalAccountObject("Harald Hollister", "Harald manages the toy division", 43);
    ExternalAccountObject externalAccountObject2 =
        new ExternalAccountObject(
            "Getchen Gerstlauer", "Product owner for the vending machine software", 51);
    ExternalAccountObject externalAccountObject3 =
        new ExternalAccountObject("Janine Sparks", "3D printing influencer", 26);

    // This is the mapper we use to transform the external objects into rows. See the class
    // implementation for
    // details.
    DataTableFieldValueExtractor<ExternalAccountObject> mapper =
        new MyCustomRowDataTableFieldValueExtractor();

    DataTable table =
        bulkApi
            .createDataTableBuilder("FirstName", "LastName")
            .addRow(externalAccountObject1, mapper)
            .addRow(externalAccountObject2, mapper)
            .addRow(externalAccountObject3, mapper)
            .build();

    // In this example, we just "fire and forget" and return the bulk ingest id
    IngestJobReference reference =
        bulkApi.createSimpleIngestJob("Account", IngestOperation.INSERT, table);
    return reference.getId();
  }

  private static class MyCustomRowDataTableFieldValueExtractor
      implements DataTableFieldValueExtractor<ExternalAccountObject> {
    @Nonnull
    @Override
    public Optional<String> extractFieldValue(ExternalAccountObject data, String fieldName) {
      switch (fieldName) {
        case "FirstName":
          return Optional.ofNullable(data.getName().split(" ")[0]);
        case "LastName":
          return Optional.ofNullable(data.getName().split(" ")[1]);
      }

      return Optional.empty();
    }
  }

  // Just here for demonstration purposes. In practice, this will likely come from a library or at
  // least separate
  // non-inner class.
  private static class ExternalAccountObject {
    private final String name;
    private final String description;
    private final int age;

    public ExternalAccountObject(String name, String description, int age) {
      this.name = name;
      this.description = description;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }

    public int getAge() {
      return age;
    }
  }
}
