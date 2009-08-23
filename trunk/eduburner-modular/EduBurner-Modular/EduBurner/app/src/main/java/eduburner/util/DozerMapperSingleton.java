package eduburner.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * DTO复制辅助工具Dozer的单例wrapper.
 * 
 * Dozer在同一JVM里使用单例即可,无需重复创建.
 * 但Dozer自带的DozerBeanMapperSingletonWrapper必须使用dozerBeanMapping.xml作参数初始化,因此重新实现无配置文件的版本.
 * 
 * @author calvin
 */
public final class DozerMapperSingleton {

	private static Mapper instance = new DozerBeanMapper();//使用预初始化避免并发问题.

	public static Mapper getInstance() {
		return instance;
	}
}
