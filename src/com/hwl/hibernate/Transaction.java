package com.hwl.hibernate;

/**
  * class Transaction
  * @author huangWenLong
  * @date 2017Äê12ÔÂ8ÈÕ
  */
public interface Transaction {
	public boolean isActive();
	public Transaction beginTranscation();
	public void commit();
	public void rollback();
}
