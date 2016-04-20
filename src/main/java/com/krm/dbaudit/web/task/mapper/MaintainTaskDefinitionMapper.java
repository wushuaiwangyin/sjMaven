

package com.krm.dbaudit.web.task.mapper;

import com.github.abel533.mapper.Mapper;
import com.krm.dbaudit.web.task.model.MaintainTaskDefinition;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author if
 */

public interface MaintainTaskDefinitionMapper extends Mapper<MaintainTaskDefinition> {


	public List<MaintainTaskDefinition> findMaintainTaskDefinitionList(Map<String, Object> params);
   

}
