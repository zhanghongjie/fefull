package com.frame.execl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {

	/** 与excel标题头对应  */
    public String exportName();

    /** 转换格式, 如时间类型 yyyy-MM-dd HH:mm:ss */
    public String pattern() default "";

    /** 在excel中位置 */
    public int order() default 0;

    /** 是否是敏感数据 */
    public boolean isSensitive() default false;

    /** 是否合并单元格 */
    public boolean isMerged() default false;

}