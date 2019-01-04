package com.salesforce.function.api.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.salesforce.function.api.model.SObjectType;

/**
 * <p>
 * A strongly typed SObject that was generated via WsdlToJava. The following nodes should be inserted in the
 * enterprise.wsdl
 * </p>
 * <code>
 * &lt;!-- support for Function SDK integration --&gt;<br/>
 * &lt;complexType name="WsdlSObject"&gt;<br/>
 *     &lt;annotation&gt;<br/>
 *         &lt;appinfo&gt;<br/>
 *             &lt;jaxb:class ref="com.salesforce.function.api.model.impl.WsdlSObject" /&gt;<br/>
 *         &lt;/appinfo&gt;<br/>
 *     &lt;/annotation&gt;<br/>
 * &lt;/complexType&gt;<br/>
 * 
 * &lt;!-- Base sObject (abstract) --&gt;<br/>
 * &lt;complexType name="sObject"&gt;<br/>
 * &lt;complexContent&gt;<br/>
 *     &lt;extension base="ens:WsdlSObject"&gt;<br/>
 *         &lt;sequence&gt;<br/>
 *             &lt;element name="fieldsToNull" type="xsd:string" nillable="true" minOccurs="0" maxOccurs="unbounded"/&gt;<br/>
 *             &lt;element name="Id" type="tns:ID" nillable="true" /&gt;<br/>
 *         &lt;/sequence&gt;<br/>
 *     &lt;/extension&gt;<br/>
 * &lt;/complexContent&gt;<br/>
 * &lt;/complexType&gt;<br/>
 * </code>
 *
 * @since 218
 */
public class WsdlSObject extends SObjectImpl {
    public WsdlSObject() {
        String tmpSobjectType = getClass().getSimpleName();
        if (WsdlSObject.class.getSimpleName()
                .equals(tmpSobjectType)) { throw new IllegalArgumentException("Invalid SObject type provided."); }
    }

    public WsdlSObject(SObjectType sObjectType) {
        super(sObjectType);
    }

    public WsdlSObject(String sobjectType) {
        super(sobjectType);
    }

    @Override
    public Object setValue(String key, Object value) {
        throw new UnsupportedOperationException("Use setters.");
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new LinkedHashMap<>();
        Class<?> sforceClass = this.getClass();

        // REVIEWME: could look at properties instead
        Method[] methods = sforceClass.getMethods();

        for (Method method : methods) {
            try {
                if (isFieldGetter(sforceClass, method)) {
                    String name = method.getName().startsWith("get") ? method.getName().substring(3)
                            : method.getName().substring(2);
                    Object value = method.invoke(this);
                    // REVIEWME: this isn't right, eg for prop was set to null; need to figure out a better
                    // strategy, eg setSendNullValues(true)
                    if (value != null) {
                        if (value instanceof WsdlSObject) {
                            // Reduce any complex relationships to their id. For instance if the user has called
                            // Contact#setAccount, replace the pair {Account,account} with {AccountId/account#getFkId()}
                            Method getIdMethod = sforceClass.getMethod(method.getName() + "Id");
                            String id = (String)getIdMethod.invoke(this);
                            if (id != null) {
                                String objectSetterName = getSObjectType() + "#set" + name;
                                String idSetterName = getSObjectType() + "#set" + name + "Id";
                                throw new RuntimeException(String.format(
                                        "You can't set a relationship using the both '%s' and '%s'. Call one of these methods to set the value back to null.",
                                        objectSetterName, idSetterName));
                            }
                            values.put(name + "Id", ((WsdlSObject)value).getFkId());
                        } else {
                            values.put(name, value);
                        }
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                // FIXME: throw FunctionException?
                throw new RuntimeException("Unable to invoke " + method.getName(), e);
            }
        }
        return Collections.unmodifiableMap(values);
    }

    private boolean isFieldGetter(Class<?> sforceClass, Method method) throws NoSuchMethodException, SecurityException {
        // is getter?
        if ((method.getName().startsWith("get") || method.getName().startsWith("is"))
                && method.getParameterTypes().length == 0
                // filter out randmon get* methods and QueryResult fields (string to avoid wsdl2java dependency)
                && (!void.class.equals(method.getReturnType())
                        && !method.getReturnType().getName().contains("com.sforce.soap.enterprise.QueryResult"))) {

            // has setter?
            String name = "set" + (method.getName().startsWith("get") ? method.getName().substring(3)
                    : method.getName().substring(2));
            for (Method setterMethod : sforceClass.getMethods()) {
                if (name.equals(setterMethod.getName()) && setterMethod.getParameterTypes().length == 1
                        && setterMethod.getReturnType().equals(void.class)) { return true; }
            }
        }

        return false;
    }
}