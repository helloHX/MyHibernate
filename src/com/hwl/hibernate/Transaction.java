package com.hwl.hibernate;

/**
  * class Transaction
  * @author huangWenLong
  * @date 2017��12��8��
  */
public interface Transaction {
	public boolean isActive();
	public void commit();
	public void rollback();
}
