/**
 * Services2020Locator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package localhost.services.Services2020;

public class Services2020Locator extends org.apache.axis.client.Service implements localhost.services.Services2020.Services2020 {

    public Services2020Locator() {
    }


    public Services2020Locator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Services2020Locator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Services2020HttpPort
    private java.lang.String Services2020HttpPort_address = "http://192.168.2.86//services/Services2020";

    public java.lang.String getServices2020HttpPortAddress() {
        return Services2020HttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Services2020HttpPortWSDDServiceName = "Services2020HttpPort";

    public java.lang.String getServices2020HttpPortWSDDServiceName() {
        return Services2020HttpPortWSDDServiceName;
    }

    public void setServices2020HttpPortWSDDServiceName(java.lang.String name) {
        Services2020HttpPortWSDDServiceName = name;
    }

    public localhost.services.Services2020.Services2020PortType getServices2020HttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Services2020HttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServices2020HttpPort(endpoint);
    }

    public localhost.services.Services2020.Services2020PortType getServices2020HttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            localhost.services.Services2020.Services2020HttpBindingStub _stub = new localhost.services.Services2020.Services2020HttpBindingStub(portAddress, this);
            _stub.setPortName(getServices2020HttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServices2020HttpPortEndpointAddress(java.lang.String address) {
        Services2020HttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (localhost.services.Services2020.Services2020PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                localhost.services.Services2020.Services2020HttpBindingStub _stub = new localhost.services.Services2020.Services2020HttpBindingStub(new java.net.URL(Services2020HttpPort_address), this);
                _stub.setPortName(getServices2020HttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Services2020HttpPort".equals(inputPortName)) {
            return getServices2020HttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost/services/Services2020", "Services2020");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost/services/Services2020", "Services2020HttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Services2020HttpPort".equals(portName)) {
            setServices2020HttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
