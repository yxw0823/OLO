package localhost.services.Services2020;

public class Services2020PortTypeProxy implements localhost.services.Services2020.Services2020PortType {
  private String _endpoint = null;
  private localhost.services.Services2020.Services2020PortType services2020PortType = null;
  
  public Services2020PortTypeProxy() {
    _initServices2020PortTypeProxy();
  }
  
  public Services2020PortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initServices2020PortTypeProxy();
  }
  
  private void _initServices2020PortTypeProxy() {
    try {
      services2020PortType = (new localhost.services.Services2020.Services2020Locator()).getServices2020HttpPort();
      if (services2020PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)services2020PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)services2020PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (services2020PortType != null)
      ((javax.xml.rpc.Stub)services2020PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public localhost.services.Services2020.Services2020PortType getServices2020PortType() {
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType;
  }
  
  public java.lang.String setSalebillXML(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.setSalebillXML(in0, in1);
  }
  
  public java.lang.String createSalebill(java.lang.String in0) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.createSalebill(in0);
  }
  
  public java.lang.String checkUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.checkUser(in0, in1);
  }
  
  public java.lang.String getCustomerInfo(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.getCustomerInfo(in0, in1);
  }
  
  public java.lang.String checkCjtUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.checkCjtUser(in0, in1);
  }
  
  public java.lang.String createSalebill1(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.createSalebill1(in0, in1, in2);
  }
  
  public java.lang.String addIntention(java.lang.String in0) throws java.rmi.RemoteException{
    if (services2020PortType == null)
      _initServices2020PortTypeProxy();
    return services2020PortType.addIntention(in0);
  }
  
  
}