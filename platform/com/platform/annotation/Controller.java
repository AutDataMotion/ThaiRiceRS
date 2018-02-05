package com.platform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制器注解
 * 说明：标注Controller和访问路径
 * @author DHJ
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Controller {
	
	/**
	 * 控制器路径，可以配置多个路径数组
	 * @return
	 */
    String[] controllerKey();

}
