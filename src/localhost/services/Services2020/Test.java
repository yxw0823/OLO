package localhost.services.Services2020;

import java.rmi.RemoteException;

import com.fh.util.Xml2JsonUtil;

public class Test {
   
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Services2020PortTypeProxy proxy = new Services2020PortTypeProxy();
        try {
        	String xml="<?xml version=\"1.0\" encoding=\"GB2312\"?><document><main>"
        			+ "<zdr>8608</zdr><gksjh>18061771111</gksjh><oppid>210197</oppid><orderSrc>2</orderSrc></main>"
        			+ "<detail1 />"
        			+ "<detail2>"
        			+ "<row><spbh>2</spbh><sl>4</sl><gths></gths>    <mbsj></mbsj>  <mbhs></mbhs>   <mbkx></mbkx>   <kd></kd>    <sd></sd>    <gd></gd>   <wzls></wzls>   <fnls></fnls>   <fnlskd></fnlskd>   <jlpz></jlpz>   <mx></mx>   <fbbx></fbbx>   <ls></ls>   <tsgy></tsgy>   <blhs1></blhs1> <dqgd></dqgd>   <ajgmzk></ajgmzk>   <bz></bz><mxdm></mxdm> <ssymwl></ssymwl>   <key></key>  <ZStyle></ZStyle>   </row><row><spbh>2</spbh> <!-- 商品编号 --><sl>4</sl><gths></gths>    <mbsj></mbsj>  <mbhs></mbhs>   <mbkx></mbkx>   <kd></kd>    <sd></sd>    <gd></gd>   <wzls></wzls>   <fnls></fnls>   <fnlskd></fnlskd>   <jlpz></jlpz>   <mx></mx>   <fbbx></fbbx>   <ls></ls>   <tsgy></tsgy>   <blhs1></blhs1> <dqgd></dqgd>   <ajgmzk></ajgmzk>   <bz></bz><mxdm></mxdm> <ssymwl></ssymwl>   <key></key>  <ZStyle></ZStyle>   </row>"
        			+ "</detail2></document>";
           // System.out.println(proxy.checkCjtUser("olo-0166", "FCEA920F7412B5DA7BE0CF42B8C93759"));
            System.out.println(proxy.createSalebill(xml));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
