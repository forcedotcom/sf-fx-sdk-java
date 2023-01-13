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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleIngestFunction4 implements SalesforceFunction<String, List<String>> {
  @Override
  public List<String> apply(InvocationEvent<String> event, Context context) throws Exception {
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    // Demonstrates the ability to use relationship fields, see:
    // https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/relationship_fields_in_a_header_row__2_0.htm
    DataTable table =
        bulkApi
            .createDataTableBuilder("FirstName", "LastName", "ReportsTo.Email")
            .addRow("Annie", "Anderson", "ceo@example.com")
            .addRow("Frank", "Friday", "gerda@company.de")
            .build();

    DataTable table2 =
        bulkApi
            .createDataTableBuilder("FirstName", "LastName", "ReportsTo.Email")
            .addRow("Lissie", "Lightyear", "peter.parsec@spacestuff.org")
            .build();

    // Instead of using bulkApi.ingest(), we use manual steps here that align closely with the
    // underlying REST API
    IngestJobReference reference = bulkApi.createIngestJob("Account", IngestOperation.INSERT);
    // If required, multiple tables can be uploaded separately:
    bulkApi.uploadJobData(reference, table);
    bulkApi.uploadJobData(reference, table2);
    bulkApi.closeJob(reference);

    bulkApi.waitForJobState(reference, IngestJobState.COMPLETE);

    // getSuccessfulRecordResults returns a DataTable which implements Java's Collection interface,
    // allowing for easy iteration:
    for (Map<String, String> row : bulkApi.getSuccessfulRecordResults(reference)) {
      System.out.printf("Ingested: %s", row.get("sf__Id"));
    }

    // The Collection interface that is present on DataTable also allows for a more functional style
    // of working
    // with the data using streaming.
    List<String> errors =
        bulkApi.getFailedRecordResults(reference).stream()
            .map(row -> row.get("sf__Error"))
            .collect(Collectors.toList());

    return errors;
  }
}
