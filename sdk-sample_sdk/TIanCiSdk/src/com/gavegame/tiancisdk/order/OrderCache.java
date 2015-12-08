package com.gavegame.tiancisdk.order;

import java.util.List;

public interface OrderCache<T> {

	/**
	 * 放数据
	 */
	void put(T t);

	/**
	 * 根据索引取数据
	 */
	T get(int index);

	/**
	 * 返回所有数据的集合
	 * 
	 * @return
	 */
	List<T> getAll();
}
