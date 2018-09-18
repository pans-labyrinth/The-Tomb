//好像没有查到servic的VO,应该是使用了不正确的设计方式,检查你的PO看是否有String属性的字段,将其打包到新的VO中去,再打开注释
package rds.service.impl.personal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import clife.basalt.core.exception.BusiException;
import clife.basalt.core.exception.BusiRuleException;
import clife.basalt.core.exception.DaoException;

import rds.mapper.PersonalMapper;
import rds.model.response.SysEnvelop;
import rds.model.request.PersonalRequestConst;
import rds.model.vo.personal.Personal0112012VO;
import rds.service.personal.RDSPersonal0112012;
import rds.service.impl.AbstractRdsBusiService;
import rds.service.util.SrvErrorCode;
import rds.service.util.UpdateMapParam;


/**
 *保单失效日查询 服务接口
 * <b>注意</b>：如果本Service下面有 insert/update/delete语句,请停止开发此服务并登记到负责人那里
 *
 * @author fumou,Created by generator, Maintenance: Xiaodong.Huang
 * @date 2018-07-17 18:58:47
*/



@Service(value = "RDSPersonal0112012")
//TODO 好像没有查到servic的VO,应该是使用了不正确的设计方式,检查你的PO看是否有String属性的字段,将其打包到新的VO中去
public class RDSPersonal0112012Impl extends AbstractRdsBusiService<Personal0112012VO> implements RDSPersonal0112012 {

	private static final Logger logger = LoggerFactory.getLogger(RDSPersonal0112012Impl.class);
	@Autowired
	private PersonalMapper personalMapper;
	
	@Override
	protected boolean validateRequestPo(Map<String, String> params) throws BusiException {
		if (!commValidNullRequestPo(params, PersonalRequestConst.POLNO)) {//TODO 在这里新增你需要非空校验的参数,如 PersonalRequestConst.CONTNO
			throw new BusiRuleException(SOURCE, SrvErrorCode.ERR_RDSNULL_CODE_R0,SrvErrorCode.ERR_RDSNULL_MSG_R0);
		}
		logger.info("执行请求参数个性化处理.");

  		if(params.get("POLNO")==null || "".equals(params.get("POLNO"))){
  			logger.info("险种号不能为空");
  			return false;
		}if (params.get("POLNO").length()>20){
  			logger.info("险种号起过最大长度");
  			return false;
		}
		//TODO 如果有,在这里加入查询参数的个性化校验
		logger.info("请求参数校验通过.");
		return true;
	}

	@Override	
//TODO 好像没有查到servic的VO,应该是使用了不正确的设计方式,检查你的PO看是否有String属性的字段,将其打包到新的VO中去，替换泛型Personal0112012VO
	protected SysEnvelop<Personal0112012VO> qryDataList(Map<String, String> params) throws DaoException {
		logger.info("执行{}查询数据库并处理业务逻辑.", getClass().getAnnotation(Service.class));
		SysEnvelop<Personal0112012VO> sysElp = new SysEnvelop<Personal0112012VO>();
		List<Personal0112012VO> voList = new ArrayList<Personal0112012VO>();
		//params = UpdateMapParam.updateReqMap(params);

		//TODO 除去下面的java代码注释并修改,或有ObjectList返回，用循环处理

		try {
			String resultVO = personalMapper.queryServicePersonal0112012(params);//替换Object并且根据情况将Obj的值set到rtnVO
			if (resultVO==null||"".equals(resultVO)){
				sysElp.setRetNO("00000000");
				sysElp.setRetMeg("查询失败");
				return sysElp;
			}
			sysElp.setAllPage(10);//样例,具体根据你的查询结果或业务需求
			Personal0112012VO rtnVo = new Personal0112012VO();
			rtnVo.setStartDate(resultVO);
			voList.add(rtnVo);
			sysElp.setRespPo(voList);
			//TODO 如果有业务逻辑,在此填充处理逻辑
			sysElp.setRetNO("00000001");
			sysElp.setRetMeg("查询成功");
			return sysElp;
		} catch (Exception e) {
			logger.info(e.getMessage(), getClass().getAnnotation(Service.class));
			e.printStackTrace();
			throw new BusiRuleException(SOURCE, SrvErrorCode.ERR_DAO_CALL_CODE,SrvErrorCode.ERR_DAO_CALL_MESSAGE);
		}
	}
}

