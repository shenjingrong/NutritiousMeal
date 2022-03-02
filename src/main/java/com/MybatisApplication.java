package com;

import com.mapper.SysUserMapper;
import com.model.SysUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * description:
 *
 * @author shenjr
 * create_date: 2022/2/25 17:18
 **/
public class MybatisApplication {

    public static final String URL = "jdbc:mysql://localhost:3306/mblog?useUnicode=true";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession = null;
        try {
            //读取mybatis-config.xml
            inputStream = Resources.getResourceAsStream(resource);
            //解析mybatis-config.xml配置文件，创建sqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            //创建sqlSession
            sqlSession = sqlSessionFactory.openSession();
            SqlSession sqlSession2 = sqlSessionFactory.openSession();
            //创建userMapper对象（UserMapper并没有实现类）
            SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserMapper userMapper2 = sqlSession2.getMapper(SysUserMapper.class);
            //调用userMapper对象的方法
            SysUser user = userMapper.selectById(1L);
            sqlSession.commit();
            SysUser user2 = userMapper2.selectById(1L);
            System.out.println(user);
            System.out.println(user2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sqlSession.close();
        }
    }
}
