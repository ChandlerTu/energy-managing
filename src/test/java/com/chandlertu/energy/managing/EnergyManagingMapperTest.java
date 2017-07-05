package com.chandlertu.energy.managing;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;

public class EnergyManagingMapperTest {

	public void testSelectEnergyManagingWhereEndTimeIsNull() {
		SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			EnergyManagingMapper mapper = session.getMapper(EnergyManagingMapper.class);
			EnergyManaging energyManaging = mapper.selectEnergyManagingWhereEndtimeIsNull();
			Assert.assertEquals(Integer.valueOf(1), energyManaging.getId());
		}
	}

}
