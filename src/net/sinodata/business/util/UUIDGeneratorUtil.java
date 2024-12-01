package net.sinodata.business.util;

import java.util.UUID;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

public class UUIDGeneratorUtil {
	
	public static String getUUID() {
		UUIDGenerator generator = new UUIDGenerator();
		UUID uuid = generator.generateId(null);
		String strUuid = uuid.toString();
		strUuid = strUuid.substring(0, 8) + strUuid.substring(9, 13) + strUuid.substring(14, 18)
				+ strUuid.substring(19, 23) + strUuid.substring(24);
		return strUuid;
	}
	/**
	 * 获得指定数目的UUID。
	 * <p>根据传入的数量，得到指定数量的UUID数组</p>
	 * @param number int 需要获得的UUID数量 
	 * @return String[]  UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] strUuids = new String[number];
		for (int i = 0; i < number; i++) {
			strUuids[i] = getUUID();
		}
		return strUuids;
	}
}
