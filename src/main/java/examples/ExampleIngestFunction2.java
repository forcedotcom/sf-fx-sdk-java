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
import com.salesforce.functions.jvm.sdk.data.DataApi;
import com.salesforce.functions.jvm.sdk.data.Record;
import com.salesforce.functions.jvm.sdk.data.bulk.BulkApi;
import com.salesforce.functions.jvm.sdk.data.bulk.DataTable;
import com.salesforce.functions.jvm.sdk.data.bulk.IngestJobReference;
import com.salesforce.functions.jvm.sdk.data.bulk.IngestOperation;

public class ExampleIngestFunction2 implements SalesforceFunction<String, String> {
  @Override
  public String apply(InvocationEvent<String> event, Context context) throws Exception {
    DataApi dataApi = context.getOrg().get().getDataApi();
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    // In this example, we use existing records for the row values. Since this is just an example,
    // we create some
    // records here to demonstrate.
    Record firstRecord =
        dataApi
            .newRecordBuilder("Account")
            .withField("FirstName", "Peter")
            .withField("LastName", "Fergusson")
            .withField("Description", "CEO of Fergusson Inc.")
            .build();

    Record secondRecord =
        dataApi
            .newRecordBuilder("Account")
            .withField("FirstName", "Jeannie")
            .withField("LastName", "Richards")
            .withField("Description", "Founder and CTO of Richards Aerospace")
            .build();

    // Note that the table does not contain a description field, but the records do. In this case,
    // the
    // description field of the records will not be added to the table. They're matched by field
    // names. This
    // prevents accidental ingest of fields.
    DataTable table =
        bulkApi
            .createDataTableBuilder("FirstName", "LastName")
            .addRow(firstRecord)
            .addRow(secondRecord)
            .build();

    // In this example, we just "fire and forget" and return the bulk ingest id
    IngestJobReference reference =
        bulkApi.createSimpleIngestJob("Account", IngestOperation.INSERT, table);
    return reference.getId();
  }
}
