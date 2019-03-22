package com.niit.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.models.BlogComment;
import com.niit.models.BlogPost;
import com.niit.models.Friend;
import com.niit.models.Job;
import com.niit.models.Notification;
import com.niit.models.ProfilePicture;
import com.niit.models.User;


@Configuration
@EnableTransactionManagement
public class DBConfiguration {
  public DBConfiguration(){
	 System.out.println("DBConfiguration class is instantiated"); 
  }
  /*
	 * <bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBuilder">
	 * <property name="dataSource" ref="dataSource">
	 */
	@Bean(name="sessionFactory") //SessionFactory - factory of session objects
	public SessionFactory sessionFactory() {
		System.out.println("Entering sessionFactory creation method");
		LocalSessionFactoryBuilder lsf=
				new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty(
				"hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		Class classes[]=new Class[]{User.class,Job.class,BlogPost.class,Notification.class,BlogComment.class,Friend.class,ProfilePicture.class};//class objects of all entities
	    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}

	@Bean(name="dataSource")
	public DataSource getDataSource() {
		System.out.println("Entering DataSource Bean creation method ");
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("org.h2.Driver");
	    dataSource.setUrl("jdbc:h2:tcp://localhost/~/Project2");
	    dataSource.setUsername("sa");
	    dataSource.setPassword("sa");
	    System.out.println("DataSource bean " +dataSource);
	    return dataSource;
	}
	
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(sessionFactory());
	}
}