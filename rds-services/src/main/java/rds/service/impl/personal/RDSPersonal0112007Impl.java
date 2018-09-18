package rds.service.impl.personal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clife.basalt.core.exception.BusiException;
import clife.basalt.core.exception.BusiRuleException;
import clife.basalt.core.exception.DaoException;
import clife.basalt.core.util.DateTimeConst;
import rds.dto.personal.CustomerDTO;
import rds.mapper.PersonalMapper;
import rds.model.request.PersonalRequestConst;
import rds.model.response.SysEnvelop;
import rds.model.vo.personal.CustomerVO;
import rds.service.impl.AbstractRdsBusiService;
import rds.service.personal.RDSPersonal0112007;
import rds.service.util.SrvErrorCode;

/**
 * 描述：受益人客户信息 服务接口 <b>注意</b>：如果本Service下面有
 * insert/update/delete语句,请停止开发此服务并登记到负责人那里
 *
 * @author Pengfei.Zhang,Created by generator, Maintenance: Xiaodong.Huang
 * @date 2018-07-20
 */

@Service(value = "RDSPersonal0112007")
public class RDSPersonal0112007Impl extends AbstractRdsBusiService<CustomerVO> implements RDSPersonal0112007 {

	private static final Logger logger = LoggerFactory.getLogger(RDSPersonal0112007Impl.class);
	@Autowired
	private PersonalMapper personalMapper;

	@Override
	protected boolean validateRequestPo(Map<String, String> params) throws BusiException {
		if (!commValidNullRequestPo(params, PersonalRequestConst.CONTNO)) {
			throw new BusiRuleException(SOURCE, SrvErrorCode.ERR_RDSNULL_CODE_R0, SrvErrorCode.ERR_RDSNULL_MSG_R0);
		}
		logger.info("请求参数校验通过.");
		return true;
	}

	@Override
	protected SysEnvelop<CustomerVO> qryDataList(Map<String, String> params) throws DaoException {
		logger.info("执行{}查询数据库并处理业务逻辑.", getClass().getAnnotation(Service.class));
		SysEnvelop<CustomerVO> sysElp = new SysEnvelop<CustomerVO>();
		List<CustomerVO> voList = new ArrayList<CustomerVO>();
		try {
			List<CustomerDTO> customerDTOList = personalMapper.queryServicePersonal0112007(params);
			for (CustomerDTO customerDTO : customerDTOList) {
				CustomerVO rtnVo = new CustomerVO();
				BeanUtils.copyProperties(customerDTO, rtnVo);
				voList.add(rtnVo);
			}
			sysElp.setRespPo(voList);
			sysElp.setDateTime(DateTime.now().toString(DateTimeConst.PATTERN_DATETIME_COMPACT_MS));
			sysElp.setRetNO("00000001");
			sysElp.setRetMeg("查询成功");
			return sysElp;
		} catch (Exception e) {
			logger.info("查询数据库失败:{}",DaoException.valueOf(e));
			e.printStackTrace();
			throw new BusiRuleException(SOURCE, SrvErrorCode.ERR_DAO_CALL_CODE,SrvErrorCode.ERR_DAO_CALL_MESSAGE);

		}
	}
}
