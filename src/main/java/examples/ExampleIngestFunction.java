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
import com.salesforce.functions.jvm.sdk.data.bulk.BulkApi;
import com.salesforce.functions.jvm.sdk.data.bulk.DataTable;
import com.salesforce.functions.jvm.sdk.data.bulk.IngestJobReference;
import com.salesforce.functions.jvm.sdk.data.bulk.IngestOperation;

public class ExampleIngestFunction implements SalesforceFunction<String, String> {
  @Override
  public String apply(InvocationEvent<String> event, Context context) throws Exception {
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    // This is the simplest way of building a bulk ingest table. Field names are specified once and
    // then rows are
    // added one-by-one using positional data. If the amount of values differs from the amount of
    // field names,
    // values will either be null or discarded respectively.
    DataTable table =
        bulkApi
            .createDataTableBuilder("FirstName", "LastName")
            .addRow("Annie", "Anderson")
            .addRow("Frank", "Friday")
            .addRow("Lissie", "Lightyear")
            .build();

    // In this example, we just "fire and forget" and return the bulk ingest id
    IngestJobReference reference =
        bulkApi.createSimpleIngestJob("Account", IngestOperation.INSERT, table);
    return reference.getId();
  }
}
