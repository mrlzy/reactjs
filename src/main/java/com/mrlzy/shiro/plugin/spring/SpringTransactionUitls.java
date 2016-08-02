package com.mrlzy.shiro.plugin.spring;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SpringTransactionUitls
{
  public static Connection getConnectionTransaction(Object dataSource)
  {
    if (TransactionSynchronizationManager.hasResource(dataSource))
    {
      ConnectionHolder holder = (ConnectionHolder)TransactionSynchronizationManager.getResource(dataSource);
      return holder.getConnection();
    }
    return null;
  }
  

}
