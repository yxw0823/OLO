package com.fh.service.olo.olopdsku;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.olo.olopdabsku.OlopdabskuService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.StringUtils;
import com.fh.util.UuidUtil;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("olopdskuService")
public class OlopdskuService {
    final BASE64Encoder encoder = new BASE64Encoder();

    final BASE64Decoder decoder = new BASE64Decoder();

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "olopdabskuService")
    private OlopdabskuService olopdabskuService;

    /*
     * 新增
     */
    public void save(PageData pd) throws Exception {
        dao.save("OlopdskuMapper.save", pd);
    }

    /*
     * 删除
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("OlopdskuMapper.delete", pd);
    }

    /*
     * 删除
     */
    public void deleteGoodsId(PageData pd) throws Exception {
        olopdabskuService.deleteGoodsId(pd); //先删除SKU关系
        
        dao.delete("OlopdskuMapper.deleteGoodsId", pd);
        
    }

    /*
     * 修改
     */
    public void edit(PageData pd) throws Exception {
        dao.update("OlopdskuMapper.edit", pd);
    }

    /*
     * 列表
     */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OlopdskuMapper.datalistPage", page);
    }

    /*
     * 列表(全部)
     */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopdskuMapper.listAll", pd);
    }

    /*
     * 通过id获取数据
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OlopdskuMapper.findById", pd);
    }

    /*
     * 通过SKU字符串处理商品与SKU之间的关系
     * 
     * 未解码的base64字符串
     * W3siQmFzZTY0VmFsdWUiOiJleUoyWVd4MVpTSTZJakkyWkRreFpHWm1NV1U1TXpSbE5HVTROekpqTUdRMU0yVmtNR1ZtT1dRd0lpd2liMmxrSWpvaU9HUTJOMlExWkdZMk5HSTNOR1F3WW1KaFpEUTVZVEJoTldKbFl6UTROamdpTENKdmJtRnRaU0k2SXVtcm1PVzZwaUlzSW1OZmJtRnRaU0k2SWpVd1kyMGlmUT09LGV5SjJZV3gxWlNJNklqQmhZelJqTWpZd00yRTFNRFEyWmpKaU1HUXdaV00xTWpnMU1tRTNORFExSWl3aWIybGtJam9pTXpWalpXSmhaRGRrWmpNeU5HTXlaV0ZsWkdVMFpXUm1ZMlV3TURaak1qWWlMQ0p2Ym1GdFpTSTZJdW1pbk9pSnNpSXNJbU5mYm1GdFpTSTZJdWU2b3VpSnNpSjkiLCJnek5hbWUiOiI1MGNtK
     * +
     * e6ouiJsiIsIkJhc2U2NGlkIjoiTlRCamJTdm51cUxvaWJJMSIsIkJhc2U2NE90aGVyQXR0cmlidXRlcyI6ImV5SlFVa2xEUlNJNklqRWlMQ0pRVWs5RVZVTlVYME5QUkVVaU9pSXpJaXdpVTFSUFVrVWlPaUkxSW4wPSJ9LHsiQmFzZTY0VmFsdWUiOiJleUoyWVd4MVpTSTZJakkyWkRreFpHWm1NV1U1TXpSbE5HVTROekpqTUdRMU0yVmtNR1ZtT1dRd0lpd2liMmxrSWpvaU9HUTJOMlExWkdZMk5HSTNOR1F3WW1KaFpEUTVZVEJoTldKbFl6UTROamdpTENKdmJtRnRaU0k2SXVtcm1PVzZwaUlzSW1OZmJtRnRaU0k2SWpVd1kyMGlmUT09LGV5SjJZV3gxWlNJNklqUmlNelkyWmprMFpEZG1NelJpWXpaaE1qWTFZekZoWmpCak1EbG1OVFZsSWl3aWIybGtJam9pTXpWalpXSmhaRGRrWmpNeU5HTXlaV0ZsWkdVMFpXUm1ZMlV3TURaak1qWWlMQ0p2Ym1GdFpTSTZJdW1pbk9pSnNpSXNJbU5mYm1GdFpTSTZJdW03aE9pSnNpSjkiLCJnek5hbWUiOiI1MGNtK
     * +
     * m7hOiJsiIsIkJhc2U2NGlkIjoiTlRCamJTdnB1NFRvaWJJMSIsIkJhc2U2NE90aGVyQXR0cmlidXRlcyI6ImV5SlFVa2xEUlNJNklqSWlMQ0pRVWs5RVZVTlVYME5QUkVVaU9pSTBJaXdpVTFSUFVrVWlPaUkySW4wPSJ9XQ
     * ==
     * 
     * 解码过后的json字符串
     * 
     * [{ "Base64Value":
     * "eyJ2YWx1ZSI6IjI2ZDkxZGZmMWU5MzRlNGU4NzJjMGQ1M2VkMGVmOWQwIiwib2lkIjoiOGQ2N2Q1ZGY2NGI3NGQwYmJhZDQ5YTBhNWJlYzQ4NjgiLCJvbmFtZSI6IumrmOW6piIsImNfbmFtZSI6IjUwY20ifQ==,eyJ2YWx1ZSI6IjBhYzRjMjYwM2E1MDQ2ZjJiMGQwZWM1Mjg1MmE3NDQ1Iiwib2lkIjoiMzVjZWJhZDdkZjMyNGMyZWFlZGU0ZWRmY2UwMDZjMjYiLCJvbmFtZSI6IuminOiJsiIsImNfbmFtZSI6Iue6ouiJsiJ9",
     * "gzName": "50cm+红色", "Base64id": "NTBjbSvnuqLoibI1",
     * "Base64OtherAttributes":
     * "eyJQUklDRSI6IjEiLCJQUk9EVUNUX0NPREUiOiIzIiwiU1RPUkUiOiI1In0=" }, {
     * "Base64Value":
     * "eyJ2YWx1ZSI6IjI2ZDkxZGZmMWU5MzRlNGU4NzJjMGQ1M2VkMGVmOWQwIiwib2lkIjoiOGQ2N2Q1ZGY2NGI3NGQwYmJhZDQ5YTBhNWJlYzQ4NjgiLCJvbmFtZSI6IumrmOW6piIsImNfbmFtZSI6IjUwY20ifQ==,eyJ2YWx1ZSI6IjRiMzY2Zjk0ZDdmMzRiYzZhMjY1YzFhZjBjMDlmNTVlIiwib2lkIjoiMzVjZWJhZDdkZjMyNGMyZWFlZGU0ZWRmY2UwMDZjMjYiLCJvbmFtZSI6IuminOiJsiIsImNfbmFtZSI6Ium7hOiJsiJ9",
     * "gzName": "50cm+黄色", "Base64id": "NTBjbSvpu4ToibI1",
     * "Base64OtherAttributes":
     * "eyJQUklDRSI6IjIiLCJQUk9EVUNUX0NPREUiOiI0IiwiU1RPUkUiOiI2In0=" }]
     * 
     */
    public void saveAllSku(PageData pd) throws Exception {

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);

        String SKU = pd.getString("SKU");
        String GOODS_ID = pd.getString("GOODS_ID");
        PageData temppdGoods = new PageData();
        temppdGoods.put("GOODS_ID", GOODS_ID);
        this.deleteGoodsId(temppdGoods);
        List<PageData> list = new ArrayList<PageData>();
        if (!StringUtils.isEmpty(SKU) && !StringUtils.isEmpty(GOODS_ID)) {
            SKU = new String(decoder.decodeBuffer(SKU), "UTF-8");
            try {
                JSONArray JN = JSONArray.fromObject(SKU);
                for (int i = 0; i < JN.size(); i++) {
                    PageData temppd = new PageData();
                    temppd.put("SPECIFICATIONS", JN.getJSONObject(i).getString("gzName"));
                    temppd.put("GOODS_ID", GOODS_ID);
                    temppd.put("SKU_ID", UuidUtil.get32UUID());
                    temppd.put("SEALED", "0");// 默认 0 不封存 1封存
                    temppd.put("SPREAD1", JN.getJSONObject(i).getString("Base64Value"));// 组合的规格
                    String Base64OtherAttributes = JN.getJSONObject(i).getString("Base64OtherAttributes");
                    temppd.put("SPREAD2", Base64OtherAttributes);//
                    String tempOtherAttributes = new String(decoder.decodeBuffer(Base64OtherAttributes), "UTF-8");
                    JSONObject tempJson = JSONObject.fromObject(tempOtherAttributes);

                    temppd.put("PRICE", tempJson.getString("PRICE"));// 价格
                    temppd.put("PRODUCT_CODE", tempJson.getString("PRODUCT_CODE"));// 编码
                    temppd.put("STORE", tempJson.getString("STORE"));// 库存

                    if (user != null) {
                        temppd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
                        temppd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    }
                    // 保存到SKU关联表中
                    olopdabskuService.saveAllSku(temppd);
                    list.add(temppd);

                }
                this.saveAll(list);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("SKU解码后的格式有问题 ！" + e.getMessage());
            }

            return;
        }
        throw new Exception("缺少必要的属性 必要的属性有SKU、GOODS_ID");
    }

    /*
     * 删除
     * 
     */
    public void saveAll(List<PageData> list) throws Exception {
        dao.batchSave("OlopdskuMapper.batchSave", list);
        // dao.delete("OlopdskuMapper.deleteAll", ArrayDATA_IDS);
    }

    /*
     * 删除
     * 
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("OlopdskuMapper.deleteAll", ArrayDATA_IDS);
    }
    
    

}
