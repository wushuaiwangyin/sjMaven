package com.krm.dbaudit.web.entitydocument.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.dbaudit.web.entitydocument.model.LinkManInfo;
import com.krm.dbaudit.web.entitydocument.model.OtherThings;

public interface EntityDocService {
/**获取所有其他信息的记录，并将记录的唯一id放到 set中
 * @return
 */
public Set getAllOtherIds();
/**获取所有联系人信息的记录，并将记录的唯一id放入set中
 * @return
 */
public Set getAllLinkIds();
public List<OtherThings> getOtherList();
public List<LinkManInfo> getLinkList();
/**比对list与set中id，如果id相同则做更新操作，如果id不通则进行增加操作 
 *这个方法其实类似于merge操作，但merge貌似不能适用行云数据库
 * 所以只能从最底层的save，update走起
 * @param ids 数据库中所有记录的id集合
 * @param list 本次请求的所有行记录（包括新增和更新）
 * @return 操作结果message
 */
public String  saveOrUpdateOthers(Set ids,List<OtherThings> list);
/**比对list与set中id，如果id相同则做更新操作，如果id不通则进行增加操作
 * 这个方法其实类似于merge操作，但merge貌似不能适用行云数据库
 * 所以只能从最底层的save，update走起
 * @param ids 数据库中所有记录的id集合
 * @param list 本次请求的所有行记录（包括新增和更新）
 * @return 操作结果message
 */
public String saveOrUpdateLinks(Set ids,List<LinkManInfo> list);


public String getZbjyList(Map<String, Object> params)throws ParseException;

}
