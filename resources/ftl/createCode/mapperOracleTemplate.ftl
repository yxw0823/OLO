<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into "${tabletop}"(
	<#list fieldList as var>
			"${var[0]}",	
	</#list>
			"${pk}"
		) values (
	<#list fieldList as var>
				<#if var[1] == "DATE">
						 ${r"#{"}${var[0]},jdbcType=TIMESTAMP${r"}"},	
					<#else>  
						<#if var[1] == "VARCHAR2">
					    	${r"#{"}${var[0]},jdbcType=VARCHAR${r"}"},
					    <#else>
						    	<#if var[1] == "NUMBER">
						    		${r"#{"}${var[0]},jdbcType=DOUBLE${r"}"},
						    	<#else>
						    		${r"#{"}${var[0]}${r"}"},
					    		</#if> 
					    </#if>  	  
				</#if>  
	</#list>
			${r"#{"}${pk}${r"}"}
		)
	</insert>
	
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from "${tabletop}"
		where 
			"${pk}" = ${r"#{"}${pk}${r"}"}
	</delete>
	
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update  "${tabletop}"
			set 
	<#list fieldList as var>
			<if test="${var[0]} != null">
			<#if var[3] == "是">
				<#if var[1] == "DATE">
					"${var[0]}" = ${r"#{"}${var[0]},jdbcType=TIMESTAMP${r"}"},	
					<#else>  
				   
				   	
				   		<#if var[1] == "VARCHAR2">
					    		"${var[0]}" = ${r"#{"}${var[0]},jdbcType=VARCHAR${r"}"},
					    <#else>
						    	<#if var[1] == "NUMBER">
						    		"${var[0]}" = ${r"#{"}${var[0]},jdbcType=DOUBLE${r"}"} ,
						    	<#else>
						    		"${var[0]}" = ${r"#{"}${var[0]}${r"}"},
					    		</#if> 
					    </#if>  
				</#if>  
				
			</#if>
			</if>
	</#list>
			"${pk}" = ${r"#{"}${pk}${r"}"}
			where 
				"${pk}" = ${r"#{"}${pk}${r"}"}
	</update>
	
	
	<!-- 通过${pk}获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
	<#list fieldList as var>
			"${var[0]}",	
	</#list>
			"${pk}"
		from 
			"${tabletop}"
		where 
			"${pk}" = ${r"#{"}${pk}${r"}"}
	</select>
	
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<#list fieldList as var>
				a."${var[0]}",	
		</#list>
				a."${pk}"
		from 
				"${tabletop}" a
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<#list fieldList as var>
				a."${var[0]}",	
		</#list>
				a."${pk}"
		from 
				"${tabletop}" a
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from "${tabletop}"
		where 
			"${pk}" in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>