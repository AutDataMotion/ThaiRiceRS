package com.junit.platform.tool.security.md;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.platform.tools.security.md.ToolMD4;

/**
 * MD4加密组件
 */
public class TestMD4 {

	/**
	 * 测试MD4
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testEncodeMD4() throws Exception {
		String str = "MD4消息摘要";

		// 获得摘要信息
		byte[] data1 = ToolMD4.encodeMD4(str.getBytes());
		byte[] data2 = ToolMD4.encodeMD4(str.getBytes());

		// 校验
		assertArrayEquals(data1, data2);
	}

	/**
	 * 测试MD4Hex
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testEncodeMD4Hex() throws Exception {
		String str = "MD4Hex消息摘要";

		// 获得摘要信息
		String data1 = ToolMD4.encodeMD4Hex(str.getBytes());
		String data2 = ToolMD4.encodeMD4Hex(str.getBytes());

		System.err.println("原文：\t" + str);

		System.err.println("MD4Hex-1：\t" + data1);
		System.err.println("MD4Hex-2：\t" + data2);

		// 校验
		assertEquals(data1, data2);
	}

}
