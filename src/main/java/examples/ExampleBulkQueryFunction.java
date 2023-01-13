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
import java.util.stream.Collectors;

public class ExampleBulkQueryFunction implements SalesforceFunction<String, List<String>> {
  @Override
  public List<String> apply(InvocationEvent<String> event, Context context) throws Exception {
    BulkApi bulkApi = context.getOrg().get().getBulkDataApi();

    QueryJobReference reference =
        bulkApi.createQueryJob(QueryOperation.QUERY, "SELECT Name FROM Accounts");

    bulkApi.waitForJobState(reference, IngestJobState.COMPLETE);

    QueryJobResult result = bulkApi.getQueryJobResults(reference, 1000);
    return result.getDataTable().stream().map(row -> row.get("Name")).collect(Collectors.toList());
  }
}
