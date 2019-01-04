package com.salesforce.function.api.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.salesforce.function.api.APIFactory;
import com.salesforce.function.api.springml.ForceAPI;
import com.salesforce.function.api.springml.model.SOQLResult;
import com.salesforce.function.api.util.OrgConfig;

/**
 * Tests that URL encoding works as expected
 *
 * @since 218
 */
public class EncodingTest extends Assert {
    /**
     * Integration test for the following error where the q= was being encoded to q%3D 15:51:06:765 USER_DEBUG
     * [77]|DEBUG|StatusCode: 400, response: {"message":"Unable to query Account: Accessing
     * https://unitepocdh1.mobile01.blitz.salesforce.com/services/data/v44.0/query?q%3DSELECT+Name+FROM+Account+WHERE+Id+%3D+%27001R0000003U8G7IAK%27
     * failed. Status 400. Reason Bad Request \n Error from server [{\"message\":\"A query string has to be
     * specified\",\"errorCode\":\"MALFORMED_QUERY\"}]","type":"FunctionException","stacktrace":"com.salesforce.function.FunctionException:
     * Unable to query Account: Accessing
     * https://unitepocdh1.mobile01.blitz.salesforce.com/services/data/v44.0/query?q%3DSELECT+Name+FROM+Account+WHERE+Id+%3D+%27001R0000003U8G7IAK%27
     * failed. Status 400. Reason Bad Request \n Error from server [{\"message\":\"A query string has to be
     * specified\",\"errorCode\":\"MALFORMED_QUERY\"}]\n\tat
     * com.unite.poc.account.AccountFunction.getAccountName(AccountFunction.java:89)\n\tat
     * com.unite.poc.account.AccountFunction.invoke(AccountFunction.java:62)\n This is an integration more than a unit
     * test
     */
    // @Test
    public void testIntegrationTestQueryEncoding() throws Exception {
        String id = "001R0000003U8G7IAK";
        String instanceUrl = "https://unitepocdh1.mobile01.blitz.salesforce.com";
        String apiVersion = "44.0";
        String sessionId = "00DR00000001Env!AR4AQL0uDd1cPCVMiCBm0y20XoRtBK1.9PBLH2pKtzq2Ag4C1pCpFecvLD2RKYp_XYItOmOSF.scJnzJDHwy.MC5w3EACzlP";

        ForceAPI force = APIFactory.getInstance().forceAPI(instanceUrl, apiVersion, sessionId);
        String query = "SELECT Name FROM Account WHERE Id = '" + id + "'";
        SOQLResult result = force.query(query);
        String name = (String)result.getRecords().get(0).get("Name");
        assertEquals("Acme", name);
    }

    /**
     * Unit test for the following error where the q= was being encoded to q%3D 15:51:06:765 USER_DEBUG
     * [77]|DEBUG|StatusCode: 400, response: {"message":"Unable to query Account: Accessing
     * https://unitepocdh1.mobile01.blitz.salesforce.com/services/data/v44.0/query?q%3DSELECT+Name+FROM+Account+WHERE+Id+%3D+%27001R0000003U8G7IAK%27
     * failed. Status 400. Reason Bad Request \n Error from server [{\"message\":\"A query string has to be
     * specified\",\"errorCode\":\"MALFORMED_QUERY\"}]","type":"FunctionException","stacktrace":"com.salesforce.function.FunctionException:
     * Unable to query Account: Accessing
     * https://unitepocdh1.mobile01.blitz.salesforce.com/services/data/v44.0/query?q%3DSELECT+Name+FROM+Account+WHERE+Id+%3D+%27001R0000003U8G7IAK%27
     * failed. Status 400. Reason Bad Request \n Error from server [{\"message\":\"A query string has to be
     * specified\",\"errorCode\":\"MALFORMED_QUERY\"}]\n\tat
     * com.unite.poc.account.AccountFunction.getAccountName(AccountFunction.java:89)\n\tat
     * com.unite.poc.account.AccountFunction.invoke(AccountFunction.java:62)\n
     */
    @Test
    public void testEncoding() throws Exception {
        String id = "001R0000003U8G7IAK";
        String instanceUrl = "https://myserver.salesforce.com";
        String apiVersion = "44.0";
        String sessionId = "00DR00000001Env!AR4AQL0uDd1cPCVMiCBm0y20XoRtBK1.9PBLH2pKtzq2Ag4C1pCpFecvLD2RKYp_XYItOmOSF.scJnzJDHwy.MC5w3EACzlP";

        OrgConfig orgConfig = new OrgConfig(instanceUrl, apiVersion, sessionId);

        String soql = "SELECT Name FROM Account WHERE Id = '" + id + "'";
        String queryPath = "/services/data/v44.0/query";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", soql);

        URI queryURI = orgConfig.getRequestURI(queryPath, parameters);
        assertEquals(
                "https://myserver.salesforce.com/services/data/v44.0/query?q=SELECT+Name+FROM+Account+WHERE+Id+%3D+%27001R0000003U8G7IAK%27",
                queryURI.toString());
    }
}
