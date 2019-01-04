package com.salesforce.function.api.composite.request.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.salesforce.function.api.Method;
import com.salesforce.function.api.composite.request.CompositeSubrequest;
import com.salesforce.function.api.model.SObject;
import com.salesforce.function.api.model.SObjectType;
import com.salesforce.function.api.model.impl.SObjectImpl;
import com.salesforce.function.util.Constants;

/**
 * Implementation of {@link CompositeSubrequest}
 */
public class CompositeSubrequestImpl implements CompositeSubrequest {
    /**
     * Properties all mapp to those documented in the
     * <a href="https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/requests_composite.htm">Composite
     * Subrequest Documentation</a>
     */
    private Method method;
    private Map<String, String> httpHeaders;
    private String referenceId;
    private String url;
    
    /**
     * Certain requests don't have a body. The server will reject the call if the body parameter is provided.
     */
    @JsonInclude(Include.NON_NULL)
    private Map<String, Object> body;

    public CompositeSubrequestImpl() {
        // Jackson constructor
    }
    
    private CompositeSubrequestImpl(CompositeSubrequestBuilder builder) {
        this.method = builder.method;
        this.httpHeaders = builder.httpHeaders;
        this.referenceId = builder.referenceId;
        this.body = builder.values;
        this.url = builder.url;
    }
    
    public static CompositeSubrequestBuilder deleteBuilder() {
        return new CompositeSubrequestBuilder.DeleteCompositeSubrequestBuilder();
    }
    
    public static CompositeSubrequestBuilder describeBuilder() {
        return new CompositeSubrequestBuilder.DescribeCompositeSubrequestBuilder();
    }

    public static CompositeSubrequestBuilder httpGETBuilder() {
        return new CompositeSubrequestBuilder.HttpGETCompositeSubrequestBuilder();
    }

    public static CompositeSubrequestBuilder insertBuilder() {
        return new CompositeSubrequestBuilder.InsertCompositeSubrequestBuilder();
    }

    public static CompositeSubrequestBuilder patchBuilder() {
        return new CompositeSubrequestBuilder.PatchCompositeSubrequestBuilder();
    }

    public static CompositeSubrequestBuilder putBuilder() {
        return new CompositeSubrequestBuilder.PutCompositeSubrequestBuilder();
    }

    @Override
    public Map<String, String> getHttpHeaders() {
        return this.httpHeaders;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public String getReferenceId() {
        return this.referenceId;
    }

    @Override
    public final String getUrl() {
        return this.url;
    }

    @Override
    public Map<String, Object> getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return "CompositeSubrequestImpl [method=" + method + ", httpHeaders=" + httpHeaders + ", referenceId="
                + referenceId + ", url=" + url + ", body=" + body + "]";
    }
    
    /**
     * Used to create different types of CompositeSubrequests based on the HTTPMethod and additional URL information
     * that might be necessary to execute the subrequest.
     */
    public static abstract class CompositeSubrequestBuilder {
        protected final Method method;
        protected final Map<String, String> httpHeaders;
        protected Map<String, Object> values;
        protected String referenceId;

        protected String apiVersion;
        protected String url;
        protected String id;
        protected String sobjectType;
        
        /**
         * This is the first referenceId used for this SObject. For instance if an INSERT and UPDATE occur in the same 
         * request, this will be the INSERT's referenceId
         */
        protected String rootReferenceId;
        
        private CompositeSubrequestBuilder(Method method) {
            this.method = method;
            this.values = new LinkedHashMap<>();
            this.httpHeaders = new LinkedHashMap<>();
            this.apiVersion = Constants.CURRENT_API_VERSION;
        }

        public CompositeSubrequestBuilder sobject(SObject sobject) {
            this.sobjecType(sobject.getSObjectType());
            this.id(SObjectImpl.getId(sobject));
            this.values(sobject.getValues());
            return this;
        }
        
        public CompositeSubrequestBuilder relationship(String fkName, CompositeSubrequest compositeSubrequest) {
            value(fkName, String.format("@{%s.id}", compositeSubrequest.getReferenceId()));
            return this;
        }

        public CompositeSubrequestBuilder id(CompositeSubrequest compositeSubrequest) {
            this.id = String.format("@{%s.id}", compositeSubrequest.getReferenceId());
            return this;
        }
        
        public CompositeSubrequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CompositeSubrequestBuilder sobjecType(String sobjectType) {
            this.sobjectType = sobjectType;
            return this;
        }

        public CompositeSubrequestBuilder sobjecType(SObjectType sObjectType) {
            this.sobjectType = sObjectType.value();
            return this;
        }

        public CompositeSubrequestBuilder value(String key, Object value) {
            this.values.put(key, value);
            return this;
        }

        /**
         * Unions the values with the existing values. DOES NOT replace the map.
         */
        public CompositeSubrequestBuilder values(Map<String, Object> values) {
            this.values.putAll(values);
            return this;
        }

        public CompositeSubrequestBuilder name(String name) {
            value("Name", name);
            return this;
        }

        public CompositeSubrequestBuilder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }
        
        public CompositeSubrequestBuilder header(String key, String value) {
            this.httpHeaders.put(key, value);
            return this;
        }

        /**
         * Unions the values with the existing values. DOES NOT replace the map.
         */
        public CompositeSubrequestBuilder headers(Map<String, String> httpHeaders) {
            this.httpHeaders.putAll(httpHeaders);
            return this;
        }
        
        public CompositeSubrequestBuilder rootReferenceId(String rootReferenceId) {
            this.rootReferenceId = rootReferenceId;
            return this;
        }
        
        public CompositeSubrequestImpl build() {
            if (StringUtils.isBlank(this.sobjectType)) {
                throw new IllegalStateException("Type is required");
            }
            
            if (this.referenceId == null) {
                this.referenceId = SObjectImpl.generateReferenceId(sobjectType);
            }

            this.url = internalGetUrl();
            
            return new CompositeSubrequestImpl(this);
        }

        protected StringBuilder getBaseUrl() {
            StringBuilder sb = new StringBuilder();
            sb.append("/services/data/v").append(this.apiVersion).append("/sobjects/").append(this.sobjectType);
            return sb;
        }

        protected StringBuilder getExistingUrl() {
            StringBuilder sb = getBaseUrl();
            sb.append("/");
            if (id != null) {
                sb.append(id);
            } else if (rootReferenceId != null) {
                sb.append(String.format("@{%s.id}", rootReferenceId));
            } else {
                sb.append(String.format("@{%s.id}", referenceId));
            }
            return sb;
        }

        /**
         * Allows subclasses to construct the URL that is necessary for their operation. This may include references to
         * other subrequests or contain additional information prepended to the default url.
         */
        protected abstract String internalGetUrl();

        /**
         * A type of subrequest that doesn't have an HTTP body. The server will reject any JSON payloads if they
         * specify a "body" when one isn't expected. This class sets the body to null and throws an exception if any code
         * attempts to set a body value value.
         */
        private static abstract class NoBodyCompositeSubrequestBuilder extends CompositeSubrequestBuilder {
            NoBodyCompositeSubrequestBuilder(Method method) {
                super(method);
                // This request can't accept any values
                this.values = null;
            }

            @Override
            public CompositeSubrequestBuilder value(String key, Object value) {
                throw new IllegalStateException("This request doesn't have a body");
            }

            @Override
            public CompositeSubrequestBuilder values(Map<String, Object> values) {
                throw new IllegalStateException("This request doesn't have a body");
            }
        }
        
        private static final class DeleteCompositeSubrequestBuilder extends NoBodyCompositeSubrequestBuilder {
            DeleteCompositeSubrequestBuilder() {
                super(Method.DELETE);
            }

            @Override
            protected String internalGetUrl() {
                return getExistingUrl().toString();
            }
        }

        private static final class DescribeCompositeSubrequestBuilder extends NoBodyCompositeSubrequestBuilder {
            DescribeCompositeSubrequestBuilder() {
                super(Method.GET);
            }

            @Override
            public CompositeSubrequestBuilder sobject(SObject sobject) {
                this.rootReferenceId = sobject.getReferenceId();
                super.sobject(sobject);
                return this;
            }

            @Override
            protected String internalGetUrl() {
                return getBaseUrl() + "/describe";
            }
        }

        private static final class HttpGETCompositeSubrequestBuilder extends NoBodyCompositeSubrequestBuilder {
            HttpGETCompositeSubrequestBuilder() {
                super(Method.GET);
            }

            @Override
            protected String internalGetUrl() {
                return getExistingUrl().toString();
            }
        }

        private static final class InsertCompositeSubrequestBuilder extends CompositeSubrequestBuilder {
            InsertCompositeSubrequestBuilder() {
                super(Method.POST);
            }

            @Override
            public CompositeSubrequestBuilder sobject(SObject sobject) {
                this.referenceId = sobject.getReferenceId();
                super.sobject(sobject);
                return this;
            }
            
            @Override
            protected String internalGetUrl() {
                return getBaseUrl().toString();
            }
        }

        private static final class PatchCompositeSubrequestBuilder extends CompositeSubrequestBuilder {
            PatchCompositeSubrequestBuilder() {
                super(Method.PATCH);
            }

            @Override
            public CompositeSubrequestBuilder sobject(SObject sobject) {
                this.rootReferenceId = sobject.getReferenceId();
                super.sobject(sobject);
                return this;
            }

            @Override
            protected String internalGetUrl() {
                return getExistingUrl().toString();
            }
        }

        private static final class PutCompositeSubrequestBuilder extends CompositeSubrequestBuilder {
            PutCompositeSubrequestBuilder() {
                super(Method.PUT);
            }

            @Override
            public CompositeSubrequestBuilder sobject(SObject sobject) {
                this.rootReferenceId = sobject.getReferenceId();
                super.sobject(sobject);
                return this;
            }

            @Override
            protected String internalGetUrl() {
                return getExistingUrl().toString();
            }
        }
    }
}