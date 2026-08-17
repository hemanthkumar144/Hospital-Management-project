package com.crimsonlogic.hospitalmanagement.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {

        try {


            Reader reader =
                    Resources.getResourceAsReader("mybatis-config.xml");

            sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public static SqlSession getSqlSession() {

        return sqlSessionFactory.openSession();

    }
}