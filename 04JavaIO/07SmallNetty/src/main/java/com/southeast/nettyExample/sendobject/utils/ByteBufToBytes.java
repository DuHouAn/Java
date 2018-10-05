package com.southeast.nettyExample.sendobject.utils;

import io.netty.buffer.ByteBuf;

public class ByteBufToBytes {
	/**
	 * 将ByteBuf转换为byte[]
	 * @param datas
	 * @return
	 */
	public byte[] read(ByteBuf datas) {
		byte[] bytes = new byte[datas.readableBytes()];// 创建byte[]
		datas.readBytes(bytes);// 将ByteBuf转换为byte[]
		return bytes;
	}
}
