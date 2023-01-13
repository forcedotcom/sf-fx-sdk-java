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
import java.io.IOException;

public class ExampleBulkQueryFunction2 implements SalesforceFunction<String, String> {
  @Override
  public String apply(InvocationEvent<String> event, Context context) throws Exception {
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    QueryJobReference reference =
        bulkApi.createQueryJob(QueryOperation.QUERY, "SELECT Name FROM Accounts");

    bulkApi.waitForJobState(reference, IngestJobState.COMPLETE);

    QueryJobResult result = bulkApi.getQueryJobResults(reference, 100);
    sendToExternalSystem(result.getDataTable());

    while (!result.isDone()) {
      result = bulkApi.getMoreQueryResults(result);
      sendToExternalSystem(result.getDataTable());
    }

    return "DONE";
  }

  private void sendToExternalSystem(DataTable table) throws IOException {
    // In practice, this could send the data via HTTP to another service or write it to a
    // database...
    // For this example, it's unimplemented.
  }
}
